package io.netty.util;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectCleaner;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Recycler<T> {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
	private static final Recycler.Handle NOOP_HANDLE = new Recycler.Handle() {
		@Override
		public void recycle(Object object) {
		}
	};
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
	private static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
	private static final int DEFAULT_INITIAL_MAX_CAPACITY_PER_THREAD = 4096;
	private static final int DEFAULT_MAX_CAPACITY_PER_THREAD;
	private static final int INITIAL_CAPACITY;
	private static final int MAX_SHARED_CAPACITY_FACTOR;
	private static final int MAX_DELAYED_QUEUES_PER_THREAD;
	private static final int LINK_CAPACITY;
	private static final int RATIO;
	private final int maxCapacityPerThread;
	private final int maxSharedCapacityFactor;
	private final int ratioMask;
	private final int maxDelayedQueuesPerThread;
	private final FastThreadLocal<Recycler.Stack<T>> threadLocal = new FastThreadLocal<Recycler.Stack<T>>() {
		protected Recycler.Stack<T> initialValue() {
			return new Recycler.Stack<>(
				Recycler.this,
				Thread.currentThread(),
				Recycler.this.maxCapacityPerThread,
				Recycler.this.maxSharedCapacityFactor,
				Recycler.this.ratioMask,
				Recycler.this.maxDelayedQueuesPerThread
			);
		}

		protected void onRemoval(Recycler.Stack<T> value) {
			if (value.threadRef.get() == Thread.currentThread() && Recycler.DELAYED_RECYCLED.isSet()) {
				Recycler.DELAYED_RECYCLED.get().remove(value);
			}
		}
	};
	private static final FastThreadLocal<Map<Recycler.Stack<?>, Recycler.WeakOrderQueue>> DELAYED_RECYCLED;

	protected Recycler() {
		this(DEFAULT_MAX_CAPACITY_PER_THREAD);
	}

	protected Recycler(int maxCapacityPerThread) {
		this(maxCapacityPerThread, MAX_SHARED_CAPACITY_FACTOR);
	}

	protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor) {
		this(maxCapacityPerThread, maxSharedCapacityFactor, RATIO, MAX_DELAYED_QUEUES_PER_THREAD);
	}

	protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor, int ratio, int maxDelayedQueuesPerThread) {
		this.ratioMask = MathUtil.safeFindNextPositivePowerOfTwo(ratio) - 1;
		if (maxCapacityPerThread <= 0) {
			this.maxCapacityPerThread = 0;
			this.maxSharedCapacityFactor = 1;
			this.maxDelayedQueuesPerThread = 0;
		} else {
			this.maxCapacityPerThread = maxCapacityPerThread;
			this.maxSharedCapacityFactor = Math.max(1, maxSharedCapacityFactor);
			this.maxDelayedQueuesPerThread = Math.max(0, maxDelayedQueuesPerThread);
		}
	}

	public final T get() {
		if (this.maxCapacityPerThread == 0) {
			return this.newObject(NOOP_HANDLE);
		} else {
			Recycler.Stack<T> stack = this.threadLocal.get();
			Recycler.DefaultHandle<T> handle = stack.pop();
			if (handle == null) {
				handle = stack.newHandle();
				handle.value = this.newObject(handle);
			}

			return (T)handle.value;
		}
	}

	@Deprecated
	public final boolean recycle(T o, Recycler.Handle<T> handle) {
		if (handle == NOOP_HANDLE) {
			return false;
		} else {
			Recycler.DefaultHandle<T> h = (Recycler.DefaultHandle<T>)handle;
			if (h.stack.parent != this) {
				return false;
			} else {
				h.recycle(o);
				return true;
			}
		}
	}

	final int threadLocalCapacity() {
		return this.threadLocal.get().elements.length;
	}

	final int threadLocalSize() {
		return this.threadLocal.get().size;
	}

	protected abstract T newObject(Recycler.Handle<T> handle);

	static {
		int maxCapacityPerThread = SystemPropertyUtil.getInt(
			"io.netty.recycler.maxCapacityPerThread", SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", 4096)
		);
		if (maxCapacityPerThread < 0) {
			maxCapacityPerThread = 4096;
		}

		DEFAULT_MAX_CAPACITY_PER_THREAD = maxCapacityPerThread;
		MAX_SHARED_CAPACITY_FACTOR = Math.max(2, SystemPropertyUtil.getInt("io.netty.recycler.maxSharedCapacityFactor", 2));
		MAX_DELAYED_QUEUES_PER_THREAD = Math.max(0, SystemPropertyUtil.getInt("io.netty.recycler.maxDelayedQueuesPerThread", NettyRuntime.availableProcessors() * 2));
		LINK_CAPACITY = MathUtil.safeFindNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("io.netty.recycler.linkCapacity", 16), 16));
		RATIO = MathUtil.safeFindNextPositivePowerOfTwo(SystemPropertyUtil.getInt("io.netty.recycler.ratio", 8));
		if (logger.isDebugEnabled()) {
			if (DEFAULT_MAX_CAPACITY_PER_THREAD == 0) {
				logger.debug("-Dio.netty.recycler.maxCapacityPerThread: disabled");
				logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: disabled");
				logger.debug("-Dio.netty.recycler.linkCapacity: disabled");
				logger.debug("-Dio.netty.recycler.ratio: disabled");
			} else {
				logger.debug("-Dio.netty.recycler.maxCapacityPerThread: {}", DEFAULT_MAX_CAPACITY_PER_THREAD);
				logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: {}", MAX_SHARED_CAPACITY_FACTOR);
				logger.debug("-Dio.netty.recycler.linkCapacity: {}", LINK_CAPACITY);
				logger.debug("-Dio.netty.recycler.ratio: {}", RATIO);
			}
		}

		INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY_PER_THREAD, 256);
		DELAYED_RECYCLED = new FastThreadLocal<Map<Recycler.Stack<?>, Recycler.WeakOrderQueue>>() {
			protected Map<Recycler.Stack<?>, Recycler.WeakOrderQueue> initialValue() {
				return new WeakHashMap();
			}
		};
	}

	static final class DefaultHandle<T> implements Recycler.Handle<T> {
		private int lastRecycledId;
		private int recycleId;
		boolean hasBeenRecycled;
		private Recycler.Stack<?> stack;
		private Object value;

		DefaultHandle(Recycler.Stack<?> stack) {
			this.stack = stack;
		}

		@Override
		public void recycle(Object object) {
			if (object != this.value) {
				throw new IllegalArgumentException("object does not belong to handle");
			} else {
				this.stack.push(this);
			}
		}
	}

	public interface Handle<T> {
		void recycle(T object);
	}

	static final class Stack<T> {
		final Recycler<T> parent;
		final WeakReference<Thread> threadRef;
		final AtomicInteger availableSharedCapacity;
		final int maxDelayedQueues;
		private final int maxCapacity;
		private final int ratioMask;
		private Recycler.DefaultHandle<?>[] elements;
		private int size;
		private int handleRecycleCount = -1;
		private Recycler.WeakOrderQueue cursor;
		private Recycler.WeakOrderQueue prev;
		private volatile Recycler.WeakOrderQueue head;

		Stack(Recycler<T> parent, Thread thread, int maxCapacity, int maxSharedCapacityFactor, int ratioMask, int maxDelayedQueues) {
			this.parent = parent;
			this.threadRef = new WeakReference(thread);
			this.maxCapacity = maxCapacity;
			this.availableSharedCapacity = new AtomicInteger(Math.max(maxCapacity / maxSharedCapacityFactor, Recycler.LINK_CAPACITY));
			this.elements = new Recycler.DefaultHandle[Math.min(Recycler.INITIAL_CAPACITY, maxCapacity)];
			this.ratioMask = ratioMask;
			this.maxDelayedQueues = maxDelayedQueues;
		}

		synchronized void setHead(Recycler.WeakOrderQueue queue) {
			queue.setNext(this.head);
			this.head = queue;
		}

		int increaseCapacity(int expectedCapacity) {
			int newCapacity = this.elements.length;
			int maxCapacity = this.maxCapacity;

			do {
				newCapacity <<= 1;
			} while (newCapacity < expectedCapacity && newCapacity < maxCapacity);

			newCapacity = Math.min(newCapacity, maxCapacity);
			if (newCapacity != this.elements.length) {
				this.elements = (Recycler.DefaultHandle<?>[])Arrays.copyOf(this.elements, newCapacity);
			}

			return newCapacity;
		}

		Recycler.DefaultHandle<T> pop() {
			int size = this.size;
			if (size == 0) {
				if (!this.scavenge()) {
					return null;
				}

				size = this.size;
			}

			Recycler.DefaultHandle ret = this.elements[--size];
			this.elements[size] = null;
			if (ret.lastRecycledId != ret.recycleId) {
				throw new IllegalStateException("recycled multiple times");
			} else {
				ret.recycleId = 0;
				ret.lastRecycledId = 0;
				this.size = size;
				return ret;
			}
		}

		boolean scavenge() {
			if (this.scavengeSome()) {
				return true;
			} else {
				this.prev = null;
				this.cursor = this.head;
				return false;
			}
		}

		boolean scavengeSome() {
			Recycler.WeakOrderQueue cursor = this.cursor;
			Recycler.WeakOrderQueue prev;
			if (cursor == null) {
				prev = null;
				cursor = this.head;
				if (cursor == null) {
					return false;
				}
			} else {
				prev = this.prev;
			}

			boolean success = false;

			Recycler.WeakOrderQueue next;
			do {
				if (cursor.transfer(this)) {
					success = true;
					break;
				}

				next = cursor.next;
				if (cursor.owner.get() == null) {
					if (cursor.hasFinalData()) {
						while (cursor.transfer(this)) {
							success = true;
						}
					}

					if (prev != null) {
						prev.setNext(next);
					}
				} else {
					prev = cursor;
				}

				cursor = next;
			} while (next != null && !success);

			this.prev = prev;
			this.cursor = cursor;
			return success;
		}

		void push(Recycler.DefaultHandle<?> item) {
			Thread currentThread = Thread.currentThread();
			if (this.threadRef.get() == currentThread) {
				this.pushNow(item);
			} else {
				this.pushLater(item, currentThread);
			}
		}

		private void pushNow(Recycler.DefaultHandle<?> item) {
			if ((item.recycleId | item.lastRecycledId) != 0) {
				throw new IllegalStateException("recycled already");
			} else {
				item.recycleId = item.lastRecycledId = Recycler.OWN_THREAD_ID;
				int size = this.size;
				if (size < this.maxCapacity && !this.dropHandle(item)) {
					if (size == this.elements.length) {
						this.elements = (Recycler.DefaultHandle<?>[])Arrays.copyOf(this.elements, Math.min(size << 1, this.maxCapacity));
					}

					this.elements[size] = item;
					this.size = size + 1;
				}
			}
		}

		private void pushLater(Recycler.DefaultHandle<?> item, Thread thread) {
			Map<Recycler.Stack<?>, Recycler.WeakOrderQueue> delayedRecycled = Recycler.DELAYED_RECYCLED.get();
			Recycler.WeakOrderQueue queue = (Recycler.WeakOrderQueue)delayedRecycled.get(this);
			if (queue == null) {
				if (delayedRecycled.size() >= this.maxDelayedQueues) {
					delayedRecycled.put(this, Recycler.WeakOrderQueue.DUMMY);
					return;
				}

				if ((queue = Recycler.WeakOrderQueue.allocate(this, thread)) == null) {
					return;
				}

				delayedRecycled.put(this, queue);
			} else if (queue == Recycler.WeakOrderQueue.DUMMY) {
				return;
			}

			queue.add(item);
		}

		boolean dropHandle(Recycler.DefaultHandle<?> handle) {
			if (!handle.hasBeenRecycled) {
				if ((++this.handleRecycleCount & this.ratioMask) != 0) {
					return true;
				}

				handle.hasBeenRecycled = true;
			}

			return false;
		}

		Recycler.DefaultHandle<T> newHandle() {
			return new Recycler.DefaultHandle<>(this);
		}
	}

	private static final class WeakOrderQueue {
		static final Recycler.WeakOrderQueue DUMMY = new Recycler.WeakOrderQueue();
		private final Recycler.WeakOrderQueue.Head head;
		private Recycler.WeakOrderQueue.Link tail;
		private Recycler.WeakOrderQueue next;
		private final WeakReference<Thread> owner;
		private final int id = Recycler.ID_GENERATOR.getAndIncrement();

		private WeakOrderQueue() {
			this.owner = null;
			this.head = new Recycler.WeakOrderQueue.Head(null);
		}

		private WeakOrderQueue(Recycler.Stack<?> stack, Thread thread) {
			this.tail = new Recycler.WeakOrderQueue.Link();
			this.head = new Recycler.WeakOrderQueue.Head(stack.availableSharedCapacity);
			this.head.link = this.tail;
			this.owner = new WeakReference(thread);
		}

		static Recycler.WeakOrderQueue newQueue(Recycler.Stack<?> stack, Thread thread) {
			Recycler.WeakOrderQueue queue = new Recycler.WeakOrderQueue(stack, thread);
			stack.setHead(queue);
			Recycler.WeakOrderQueue.Head head = queue.head;
			ObjectCleaner.register(queue, head);
			return queue;
		}

		private void setNext(Recycler.WeakOrderQueue next) {
			assert next != this;

			this.next = next;
		}

		static Recycler.WeakOrderQueue allocate(Recycler.Stack<?> stack, Thread thread) {
			return Recycler.WeakOrderQueue.Head.reserveSpace(stack.availableSharedCapacity, Recycler.LINK_CAPACITY) ? newQueue(stack, thread) : null;
		}

		void add(Recycler.DefaultHandle<?> handle) {
			handle.lastRecycledId = this.id;
			Recycler.WeakOrderQueue.Link tail = this.tail;
			int writeIndex;
			if ((writeIndex = tail.get()) == Recycler.LINK_CAPACITY) {
				if (!this.head.reserveSpace(Recycler.LINK_CAPACITY)) {
					return;
				}

				this.tail = tail = tail.next = new Recycler.WeakOrderQueue.Link();
				writeIndex = tail.get();
			}

			tail.elements[writeIndex] = handle;
			handle.stack = null;
			tail.lazySet(writeIndex + 1);
		}

		boolean hasFinalData() {
			return this.tail.readIndex != this.tail.get();
		}

		boolean transfer(Recycler.Stack<?> dst) {
			Recycler.WeakOrderQueue.Link head = this.head.link;
			if (head == null) {
				return false;
			} else {
				if (head.readIndex == Recycler.LINK_CAPACITY) {
					if (head.next == null) {
						return false;
					}

					this.head.link = head = head.next;
				}

				int srcStart = head.readIndex;
				int srcEnd = head.get();
				int srcSize = srcEnd - srcStart;
				if (srcSize == 0) {
					return false;
				} else {
					int dstSize = dst.size;
					int expectedCapacity = dstSize + srcSize;
					if (expectedCapacity > dst.elements.length) {
						int actualCapacity = dst.increaseCapacity(expectedCapacity);
						srcEnd = Math.min(srcStart + actualCapacity - dstSize, srcEnd);
					}

					if (srcStart != srcEnd) {
						Recycler.DefaultHandle[] srcElems = head.elements;
						Recycler.DefaultHandle[] dstElems = dst.elements;
						int newDstSize = dstSize;

						for (int i = srcStart; i < srcEnd; i++) {
							Recycler.DefaultHandle element = srcElems[i];
							if (element.recycleId == 0) {
								element.recycleId = element.lastRecycledId;
							} else if (element.recycleId != element.lastRecycledId) {
								throw new IllegalStateException("recycled already");
							}

							srcElems[i] = null;
							if (!dst.dropHandle(element)) {
								element.stack = dst;
								dstElems[newDstSize++] = element;
							}
						}

						if (srcEnd == Recycler.LINK_CAPACITY && head.next != null) {
							this.head.reclaimSpace(Recycler.LINK_CAPACITY);
							this.head.link = head.next;
						}

						head.readIndex = srcEnd;
						if (dst.size == newDstSize) {
							return false;
						} else {
							dst.size = newDstSize;
							return true;
						}
					} else {
						return false;
					}
				}
			}
		}

		static final class Head implements Runnable {
			private final AtomicInteger availableSharedCapacity;
			Recycler.WeakOrderQueue.Link link;

			Head(AtomicInteger availableSharedCapacity) {
				this.availableSharedCapacity = availableSharedCapacity;
			}

			public void run() {
				for (Recycler.WeakOrderQueue.Link head = this.link; head != null; head = head.next) {
					this.reclaimSpace(Recycler.LINK_CAPACITY);
				}
			}

			void reclaimSpace(int space) {
				assert space >= 0;

				this.availableSharedCapacity.addAndGet(space);
			}

			boolean reserveSpace(int space) {
				return reserveSpace(this.availableSharedCapacity, space);
			}

			static boolean reserveSpace(AtomicInteger availableSharedCapacity, int space) {
				assert space >= 0;

				int available;
				do {
					available = availableSharedCapacity.get();
					if (available < space) {
						return false;
					}
				} while (!availableSharedCapacity.compareAndSet(available, available - space));

				return true;
			}
		}

		static final class Link extends AtomicInteger {
			private final Recycler.DefaultHandle<?>[] elements = new Recycler.DefaultHandle[Recycler.LINK_CAPACITY];
			private int readIndex;
			Recycler.WeakOrderQueue.Link next;
		}
	}
}
