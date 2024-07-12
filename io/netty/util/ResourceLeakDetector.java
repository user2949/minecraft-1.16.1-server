package io.netty.util;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ResourceLeakDetector<T> {
	private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
	private static final String PROP_LEVEL = "io.netty.leakDetection.level";
	private static final ResourceLeakDetector.Level DEFAULT_LEVEL = ResourceLeakDetector.Level.SIMPLE;
	private static final String PROP_TARGET_RECORDS = "io.netty.leakDetection.targetRecords";
	private static final int DEFAULT_TARGET_RECORDS = 4;
	private static final int TARGET_RECORDS;
	private static ResourceLeakDetector.Level level;
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
	static final int DEFAULT_SAMPLING_INTERVAL = 128;
	private final ConcurrentMap<ResourceLeakDetector.DefaultResourceLeak<?>, ResourceLeakDetector.LeakEntry> allLeaks = PlatformDependent.newConcurrentHashMap();
	private final ReferenceQueue<Object> refQueue = new ReferenceQueue();
	private final ConcurrentMap<String, Boolean> reportedLeaks = PlatformDependent.newConcurrentHashMap();
	private final String resourceType;
	private final int samplingInterval;
	private static final AtomicReference<String[]> excludedMethods;

	@Deprecated
	public static void setEnabled(boolean enabled) {
		setLevel(enabled ? ResourceLeakDetector.Level.SIMPLE : ResourceLeakDetector.Level.DISABLED);
	}

	public static boolean isEnabled() {
		return getLevel().ordinal() > ResourceLeakDetector.Level.DISABLED.ordinal();
	}

	public static void setLevel(ResourceLeakDetector.Level level) {
		if (level == null) {
			throw new NullPointerException("level");
		} else {
			ResourceLeakDetector.level = level;
		}
	}

	public static ResourceLeakDetector.Level getLevel() {
		return level;
	}

	@Deprecated
	public ResourceLeakDetector(Class<?> resourceType) {
		this(StringUtil.simpleClassName(resourceType));
	}

	@Deprecated
	public ResourceLeakDetector(String resourceType) {
		this(resourceType, 128, Long.MAX_VALUE);
	}

	@Deprecated
	public ResourceLeakDetector(Class<?> resourceType, int samplingInterval, long maxActive) {
		this(resourceType, samplingInterval);
	}

	public ResourceLeakDetector(Class<?> resourceType, int samplingInterval) {
		this(StringUtil.simpleClassName(resourceType), samplingInterval, Long.MAX_VALUE);
	}

	@Deprecated
	public ResourceLeakDetector(String resourceType, int samplingInterval, long maxActive) {
		if (resourceType == null) {
			throw new NullPointerException("resourceType");
		} else {
			this.resourceType = resourceType;
			this.samplingInterval = samplingInterval;
		}
	}

	@Deprecated
	public final ResourceLeak open(T obj) {
		return this.track0(obj);
	}

	public final ResourceLeakTracker<T> track(T obj) {
		return this.track0(obj);
	}

	private ResourceLeakDetector.DefaultResourceLeak track0(T obj) {
		ResourceLeakDetector.Level level = ResourceLeakDetector.level;
		if (level == ResourceLeakDetector.Level.DISABLED) {
			return null;
		} else if (level.ordinal() < ResourceLeakDetector.Level.PARANOID.ordinal()) {
			if (PlatformDependent.threadLocalRandom().nextInt(this.samplingInterval) == 0) {
				this.reportLeak();
				return new ResourceLeakDetector.DefaultResourceLeak(obj, this.refQueue, this.allLeaks);
			} else {
				return null;
			}
		} else {
			this.reportLeak();
			return new ResourceLeakDetector.DefaultResourceLeak(obj, this.refQueue, this.allLeaks);
		}
	}

	private void clearRefQueue() {
		while (true) {
			ResourceLeakDetector.DefaultResourceLeak ref = (ResourceLeakDetector.DefaultResourceLeak)this.refQueue.poll();
			if (ref == null) {
				return;
			}

			ref.dispose();
		}
	}

	private void reportLeak() {
		if (!logger.isErrorEnabled()) {
			this.clearRefQueue();
		} else {
			while (true) {
				ResourceLeakDetector.DefaultResourceLeak ref = (ResourceLeakDetector.DefaultResourceLeak)this.refQueue.poll();
				if (ref == null) {
					return;
				}

				if (ref.dispose()) {
					String records = ref.toString();
					if (this.reportedLeaks.putIfAbsent(records, Boolean.TRUE) == null) {
						if (records.isEmpty()) {
							this.reportUntracedLeak(this.resourceType);
						} else {
							this.reportTracedLeak(this.resourceType, records);
						}
					}
				}
			}
		}
	}

	protected void reportTracedLeak(String resourceType, String records) {
		logger.error(
			"LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}",
			resourceType,
			records
		);
	}

	protected void reportUntracedLeak(String resourceType) {
		logger.error(
			"LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.",
			resourceType,
			"io.netty.leakDetection.level",
			ResourceLeakDetector.Level.ADVANCED.name().toLowerCase(),
			StringUtil.simpleClassName(this)
		);
	}

	@Deprecated
	protected void reportInstancesLeak(String resourceType) {
	}

	public static void addExclusions(Class clz, String... methodNames) {
		Set<String> nameSet = new HashSet(Arrays.asList(methodNames));

		for (Method method : clz.getDeclaredMethods()) {
			if (nameSet.remove(method.getName()) && nameSet.isEmpty()) {
				break;
			}
		}

		if (!nameSet.isEmpty()) {
			throw new IllegalArgumentException("Can't find '" + nameSet + "' in " + clz.getName());
		} else {
			String[] oldMethods;
			String[] newMethods;
			do {
				oldMethods = (String[])excludedMethods.get();
				newMethods = (String[])Arrays.copyOf(oldMethods, oldMethods.length + 2 * methodNames.length);

				for (int i = 0; i < methodNames.length; i++) {
					newMethods[oldMethods.length + i * 2] = clz.getName();
					newMethods[oldMethods.length + i * 2 + 1] = methodNames[i];
				}
			} while (!excludedMethods.compareAndSet(oldMethods, newMethods));
		}
	}

	static {
		boolean disabled;
		if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
			disabled = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
			logger.debug("-Dio.netty.noResourceLeakDetection: {}", disabled);
			logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", "io.netty.leakDetection.level", DEFAULT_LEVEL.name().toLowerCase());
		} else {
			disabled = false;
		}

		ResourceLeakDetector.Level defaultLevel = disabled ? ResourceLeakDetector.Level.DISABLED : DEFAULT_LEVEL;
		String levelStr = SystemPropertyUtil.get("io.netty.leakDetectionLevel", defaultLevel.name());
		levelStr = SystemPropertyUtil.get("io.netty.leakDetection.level", levelStr);
		ResourceLeakDetector.Level level = ResourceLeakDetector.Level.parseLevel(levelStr);
		TARGET_RECORDS = SystemPropertyUtil.getInt("io.netty.leakDetection.targetRecords", 4);
		ResourceLeakDetector.level = level;
		if (logger.isDebugEnabled()) {
			logger.debug("-D{}: {}", "io.netty.leakDetection.level", level.name().toLowerCase());
			logger.debug("-D{}: {}", "io.netty.leakDetection.targetRecords", TARGET_RECORDS);
		}

		excludedMethods = new AtomicReference(EmptyArrays.EMPTY_STRINGS);
	}

	private static final class DefaultResourceLeak<T> extends WeakReference<Object> implements ResourceLeakTracker<T>, ResourceLeak {
		private static final AtomicReferenceFieldUpdater<ResourceLeakDetector.DefaultResourceLeak<?>, ResourceLeakDetector.Record> headUpdater = AtomicReferenceFieldUpdater.newUpdater(
			ResourceLeakDetector.DefaultResourceLeak.class, ResourceLeakDetector.Record.class, "head"
		);
		private static final AtomicIntegerFieldUpdater<ResourceLeakDetector.DefaultResourceLeak<?>> droppedRecordsUpdater = AtomicIntegerFieldUpdater.newUpdater(
			ResourceLeakDetector.DefaultResourceLeak.class, "droppedRecords"
		);
		private volatile ResourceLeakDetector.Record head;
		private volatile int droppedRecords;
		private final ConcurrentMap<ResourceLeakDetector.DefaultResourceLeak<?>, ResourceLeakDetector.LeakEntry> allLeaks;
		private final int trackedHash;

		DefaultResourceLeak(
			Object referent, ReferenceQueue<Object> refQueue, ConcurrentMap<ResourceLeakDetector.DefaultResourceLeak<?>, ResourceLeakDetector.LeakEntry> allLeaks
		) {
			super(referent, refQueue);

			assert referent != null;

			this.trackedHash = System.identityHashCode(referent);
			allLeaks.put(this, ResourceLeakDetector.LeakEntry.INSTANCE);
			headUpdater.set(this, new ResourceLeakDetector.Record(ResourceLeakDetector.Record.BOTTOM));
			this.allLeaks = allLeaks;
		}

		@Override
		public void record() {
			this.record0(null);
		}

		@Override
		public void record(Object hint) {
			this.record0(hint);
		}

		private void record0(Object hint) {
			if (ResourceLeakDetector.TARGET_RECORDS > 0) {
				ResourceLeakDetector.Record oldHead;
				ResourceLeakDetector.Record newHead;
				boolean dropped;
				do {
					ResourceLeakDetector.Record prevHead;
					if ((prevHead = oldHead = (ResourceLeakDetector.Record)headUpdater.get(this)) == null) {
						return;
					}

					int numElements = oldHead.pos + 1;
					if (numElements >= ResourceLeakDetector.TARGET_RECORDS) {
						int backOffFactor = Math.min(numElements - ResourceLeakDetector.TARGET_RECORDS, 30);
						if (dropped = PlatformDependent.threadLocalRandom().nextInt(1 << backOffFactor) != 0) {
							prevHead = oldHead.next;
						}
					} else {
						dropped = false;
					}

					newHead = hint != null ? new ResourceLeakDetector.Record(prevHead, hint) : new ResourceLeakDetector.Record(prevHead);
				} while (!headUpdater.compareAndSet(this, oldHead, newHead));

				if (dropped) {
					droppedRecordsUpdater.incrementAndGet(this);
				}
			}
		}

		boolean dispose() {
			this.clear();
			return this.allLeaks.remove(this, ResourceLeakDetector.LeakEntry.INSTANCE);
		}

		@Override
		public boolean close() {
			if (this.allLeaks.remove(this, ResourceLeakDetector.LeakEntry.INSTANCE)) {
				this.clear();
				headUpdater.set(this, null);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean close(T trackedObject) {
			assert this.trackedHash == System.identityHashCode(trackedObject);

			return this.close() && trackedObject != null;
		}

		public String toString() {
			ResourceLeakDetector.Record oldHead = (ResourceLeakDetector.Record)headUpdater.getAndSet(this, null);
			if (oldHead == null) {
				return "";
			} else {
				int dropped = droppedRecordsUpdater.get(this);
				int duped = 0;
				int present = oldHead.pos + 1;
				StringBuilder buf = new StringBuilder(present * 2048).append(StringUtil.NEWLINE);
				buf.append("Recent access records: ").append(StringUtil.NEWLINE);
				int i = 1;

				for (Set<String> seen = new HashSet(present); oldHead != ResourceLeakDetector.Record.BOTTOM; oldHead = oldHead.next) {
					String s = oldHead.toString();
					if (seen.add(s)) {
						if (oldHead.next == ResourceLeakDetector.Record.BOTTOM) {
							buf.append("Created at:").append(StringUtil.NEWLINE).append(s);
						} else {
							buf.append('#').append(i++).append(':').append(StringUtil.NEWLINE).append(s);
						}
					} else {
						duped++;
					}
				}

				if (duped > 0) {
					buf.append(": ").append(dropped).append(" leak records were discarded because they were duplicates").append(StringUtil.NEWLINE);
				}

				if (dropped > 0) {
					buf.append(": ")
						.append(dropped)
						.append(" leak records were discarded because the leak record count is targeted to ")
						.append(ResourceLeakDetector.TARGET_RECORDS)
						.append(". Use system property ")
						.append("io.netty.leakDetection.targetRecords")
						.append(" to increase the limit.")
						.append(StringUtil.NEWLINE);
				}

				buf.setLength(buf.length() - StringUtil.NEWLINE.length());
				return buf.toString();
			}
		}
	}

	private static final class LeakEntry {
		static final ResourceLeakDetector.LeakEntry INSTANCE = new ResourceLeakDetector.LeakEntry();
		private static final int HASH = System.identityHashCode(INSTANCE);

		public int hashCode() {
			return HASH;
		}

		public boolean equals(Object obj) {
			return obj == this;
		}
	}

	public static enum Level {
		DISABLED,
		SIMPLE,
		ADVANCED,
		PARANOID;

		static ResourceLeakDetector.Level parseLevel(String levelStr) {
			String trimmedLevelStr = levelStr.trim();

			for (ResourceLeakDetector.Level l : values()) {
				if (trimmedLevelStr.equalsIgnoreCase(l.name()) || trimmedLevelStr.equals(String.valueOf(l.ordinal()))) {
					return l;
				}
			}

			return ResourceLeakDetector.DEFAULT_LEVEL;
		}
	}

	private static final class Record extends Throwable {
		private static final long serialVersionUID = 6065153674892850720L;
		private static final ResourceLeakDetector.Record BOTTOM = new ResourceLeakDetector.Record();
		private final String hintString;
		private final ResourceLeakDetector.Record next;
		private final int pos;

		Record(ResourceLeakDetector.Record next, Object hint) {
			this.hintString = hint instanceof ResourceLeakHint ? ((ResourceLeakHint)hint).toHintString() : hint.toString();
			this.next = next;
			this.pos = next.pos + 1;
		}

		Record(ResourceLeakDetector.Record next) {
			this.hintString = null;
			this.next = next;
			this.pos = next.pos + 1;
		}

		private Record() {
			this.hintString = null;
			this.next = null;
			this.pos = -1;
		}

		public String toString() {
			StringBuilder buf = new StringBuilder(2048);
			if (this.hintString != null) {
				buf.append("\tHint: ").append(this.hintString).append(StringUtil.NEWLINE);
			}

			StackTraceElement[] array = this.getStackTrace();

			label30:
			for (int i = 3; i < array.length; i++) {
				StackTraceElement element = array[i];
				String[] exclusions = (String[])ResourceLeakDetector.excludedMethods.get();

				for (int k = 0; k < exclusions.length; k += 2) {
					if (exclusions[k].equals(element.getClassName()) && exclusions[k + 1].equals(element.getMethodName())) {
						continue label30;
					}
				}

				buf.append('\t');
				buf.append(element.toString());
				buf.append(StringUtil.NEWLINE);
			}

			return buf.toString();
		}
	}
}
