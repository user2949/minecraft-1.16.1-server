package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConfigurationFactoryData;
import org.apache.logging.log4j.core.appender.FileManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;

public class RollingFileManager extends FileManager {
	private static RollingFileManager.RollingFileManagerFactory factory = new RollingFileManager.RollingFileManagerFactory();
	private static final int MAX_TRIES = 3;
	private static final int MIN_DURATION = 100;
	protected long size;
	private long initialTime;
	private final PatternProcessor patternProcessor;
	private final Semaphore semaphore = new Semaphore(1);
	private final Log4jThreadFactory threadFactory = Log4jThreadFactory.createThreadFactory("RollingFileManager");
	private volatile TriggeringPolicy triggeringPolicy;
	private volatile RolloverStrategy rolloverStrategy;
	private volatile boolean renameEmptyFiles = false;
	private volatile boolean initialized = false;
	private volatile String fileName;
	private FileExtension fileExtension;
	private ExecutorService asyncExecutor = new ThreadPoolExecutor(
		0, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new RollingFileManager.EmptyQueue(), this.threadFactory
	);
	private static final AtomicReferenceFieldUpdater<RollingFileManager, TriggeringPolicy> triggeringPolicyUpdater = AtomicReferenceFieldUpdater.newUpdater(
		RollingFileManager.class, TriggeringPolicy.class, "triggeringPolicy"
	);
	private static final AtomicReferenceFieldUpdater<RollingFileManager, RolloverStrategy> rolloverStrategyUpdater = AtomicReferenceFieldUpdater.newUpdater(
		RollingFileManager.class, RolloverStrategy.class, "rolloverStrategy"
	);

	@Deprecated
	protected RollingFileManager(
		String fileName,
		String pattern,
		OutputStream os,
		boolean append,
		long size,
		long time,
		TriggeringPolicy triggeringPolicy,
		RolloverStrategy rolloverStrategy,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		int bufferSize,
		boolean writeHeader
	) {
		this(
			fileName,
			pattern,
			os,
			append,
			size,
			time,
			triggeringPolicy,
			rolloverStrategy,
			advertiseURI,
			layout,
			writeHeader,
			ByteBuffer.wrap(new byte[Constants.ENCODER_BYTE_BUFFER_SIZE])
		);
	}

	@Deprecated
	protected RollingFileManager(
		String fileName,
		String pattern,
		OutputStream os,
		boolean append,
		long size,
		long time,
		TriggeringPolicy triggeringPolicy,
		RolloverStrategy rolloverStrategy,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		boolean writeHeader,
		ByteBuffer buffer
	) {
		super(fileName, os, append, false, advertiseURI, layout, writeHeader, buffer);
		this.size = size;
		this.initialTime = time;
		this.triggeringPolicy = triggeringPolicy;
		this.rolloverStrategy = rolloverStrategy;
		this.patternProcessor = new PatternProcessor(pattern);
		this.patternProcessor.setPrevFileTime(time);
		this.fileName = fileName;
		this.fileExtension = FileExtension.lookupForFile(pattern);
	}

	protected RollingFileManager(
		LoggerContext loggerContext,
		String fileName,
		String pattern,
		OutputStream os,
		boolean append,
		boolean createOnDemand,
		long size,
		long time,
		TriggeringPolicy triggeringPolicy,
		RolloverStrategy rolloverStrategy,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		boolean writeHeader,
		ByteBuffer buffer
	) {
		super(loggerContext, fileName, os, append, false, createOnDemand, advertiseURI, layout, writeHeader, buffer);
		this.size = size;
		this.initialTime = time;
		this.triggeringPolicy = triggeringPolicy;
		this.rolloverStrategy = rolloverStrategy;
		this.patternProcessor = new PatternProcessor(pattern);
		this.patternProcessor.setPrevFileTime(time);
		this.fileName = fileName;
		this.fileExtension = FileExtension.lookupForFile(pattern);
	}

	public void initialize() {
		if (!this.initialized) {
			LOGGER.debug("Initializing triggering policy {}", this.triggeringPolicy);
			this.initialized = true;
			this.triggeringPolicy.initialize(this);
			if (this.triggeringPolicy instanceof LifeCycle) {
				((LifeCycle)this.triggeringPolicy).start();
			}
		}
	}

	public static RollingFileManager getFileManager(
		String fileName,
		String pattern,
		boolean append,
		boolean bufferedIO,
		TriggeringPolicy policy,
		RolloverStrategy strategy,
		String advertiseURI,
		Layout<? extends Serializable> layout,
		int bufferSize,
		boolean immediateFlush,
		boolean createOnDemand,
		Configuration configuration
	) {
		String name = fileName == null ? pattern : fileName;
		return (RollingFileManager)getManager(
			name,
			new RollingFileManager.FactoryData(
				fileName, pattern, append, bufferedIO, policy, strategy, advertiseURI, layout, bufferSize, immediateFlush, createOnDemand, configuration
			),
			factory
		);
	}

	@Override
	public String getFileName() {
		if (this.rolloverStrategy instanceof DirectFileRolloverStrategy) {
			this.fileName = ((DirectFileRolloverStrategy)this.rolloverStrategy).getCurrentFileName(this);
		}

		return this.fileName;
	}

	public FileExtension getFileExtension() {
		return this.fileExtension;
	}

	@Override
	protected synchronized void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
		super.write(bytes, offset, length, immediateFlush);
	}

	@Override
	protected synchronized void writeToDestination(byte[] bytes, int offset, int length) {
		this.size += (long)length;
		super.writeToDestination(bytes, offset, length);
	}

	public boolean isRenameEmptyFiles() {
		return this.renameEmptyFiles;
	}

	public void setRenameEmptyFiles(boolean renameEmptyFiles) {
		this.renameEmptyFiles = renameEmptyFiles;
	}

	public long getFileSize() {
		return this.size + (long)this.byteBuffer.position();
	}

	public long getFileTime() {
		return this.initialTime;
	}

	public synchronized void checkRollover(LogEvent event) {
		if (this.triggeringPolicy.isTriggeringEvent(event)) {
			this.rollover();
		}
	}

	@Override
	public boolean releaseSub(long timeout, TimeUnit timeUnit) {
		LOGGER.debug("Shutting down RollingFileManager {}" + this.getName());
		boolean stopped = true;
		if (this.triggeringPolicy instanceof LifeCycle2) {
			stopped &= ((LifeCycle2)this.triggeringPolicy).stop(timeout, timeUnit);
		} else if (this.triggeringPolicy instanceof LifeCycle) {
			((LifeCycle)this.triggeringPolicy).stop();
			stopped &= true;
		}

		boolean status = super.releaseSub(timeout, timeUnit) && stopped;
		this.asyncExecutor.shutdown();

		try {
			long millis = timeUnit.toMillis(timeout);
			long waitInterval = 100L < millis ? millis : 100L;

			for (int count = 1; count <= 3 && !this.asyncExecutor.isTerminated(); count++) {
				this.asyncExecutor.awaitTermination(waitInterval * (long)count, TimeUnit.MILLISECONDS);
			}

			if (this.asyncExecutor.isTerminated()) {
				LOGGER.debug("All asynchronous threads have terminated");
			} else {
				this.asyncExecutor.shutdownNow();

				try {
					this.asyncExecutor.awaitTermination(timeout, timeUnit);
					if (this.asyncExecutor.isTerminated()) {
						LOGGER.debug("All asynchronous threads have terminated");
					} else {
						LOGGER.debug("RollingFileManager shutting down but some asynchronous services may not have completed");
					}
				} catch (InterruptedException var12) {
					LOGGER.warn("RollingFileManager stopped but some asynchronous services may not have completed.");
				}
			}
		} catch (InterruptedException var13) {
			this.asyncExecutor.shutdownNow();

			try {
				this.asyncExecutor.awaitTermination(timeout, timeUnit);
				if (this.asyncExecutor.isTerminated()) {
					LOGGER.debug("All asynchronous threads have terminated");
				}
			} catch (InterruptedException var11) {
				LOGGER.warn("RollingFileManager stopped but some asynchronous services may not have completed.");
			}

			Thread.currentThread().interrupt();
		}

		LOGGER.debug("RollingFileManager shutdown completed with status {}", status);
		return status;
	}

	public synchronized void rollover() {
		if (this.hasOutputStream()) {
			if (this.rollover(this.rolloverStrategy)) {
				try {
					this.size = 0L;
					this.initialTime = System.currentTimeMillis();
					this.createFileAfterRollover();
				} catch (IOException var2) {
					this.logError("Failed to create file after rollover", var2);
				}
			}
		}
	}

	protected void createFileAfterRollover() throws IOException {
		this.setOutputStream(this.createOutputStream());
	}

	public PatternProcessor getPatternProcessor() {
		return this.patternProcessor;
	}

	public void setTriggeringPolicy(TriggeringPolicy triggeringPolicy) {
		triggeringPolicy.initialize(this);
		TriggeringPolicy policy = this.triggeringPolicy;
		int count = 0;
		boolean policyUpdated = false;

		while (!(policyUpdated = triggeringPolicyUpdater.compareAndSet(this, this.triggeringPolicy, triggeringPolicy)) && ++count < 3) {
		}

		if (policyUpdated) {
			if (triggeringPolicy instanceof LifeCycle) {
				((LifeCycle)triggeringPolicy).start();
			}

			if (policy instanceof LifeCycle) {
				((LifeCycle)policy).stop();
			}
		} else if (triggeringPolicy instanceof LifeCycle) {
			((LifeCycle)triggeringPolicy).stop();
		}
	}

	public void setRolloverStrategy(RolloverStrategy rolloverStrategy) {
		rolloverStrategyUpdater.compareAndSet(this, this.rolloverStrategy, rolloverStrategy);
	}

	public <T extends TriggeringPolicy> T getTriggeringPolicy() {
		return (T)this.triggeringPolicy;
	}

	public RolloverStrategy getRolloverStrategy() {
		return this.rolloverStrategy;
	}

	private boolean rollover(RolloverStrategy strategy) {
		boolean releaseRequired = false;

		try {
			this.semaphore.acquire();
			releaseRequired = true;
		} catch (InterruptedException var11) {
			this.logError("Thread interrupted while attempting to check rollover", var11);
			return false;
		}

		boolean success = true;

		boolean ex;
		try {
			RolloverDescription descriptor = strategy.rollover(this);
			if (descriptor == null) {
				return false;
			}

			this.writeFooter();
			this.closeOutputStream();
			if (descriptor.getSynchronous() != null) {
				LOGGER.debug("RollingFileManager executing synchronous {}", descriptor.getSynchronous());

				try {
					success = descriptor.getSynchronous().execute();
				} catch (Exception var10) {
					success = false;
					this.logError("Caught error in synchronous task", var10);
				}
			}

			if (success && descriptor.getAsynchronous() != null) {
				LOGGER.debug("RollingFileManager executing async {}", descriptor.getAsynchronous());
				this.asyncExecutor.execute(new RollingFileManager.AsyncAction(descriptor.getAsynchronous(), this));
				releaseRequired = false;
			}

			ex = true;
		} finally {
			if (releaseRequired) {
				this.semaphore.release();
			}
		}

		return ex;
	}

	@Override
	public void updateData(Object data) {
		RollingFileManager.FactoryData factoryData = (RollingFileManager.FactoryData)data;
		this.setRolloverStrategy(factoryData.getRolloverStrategy());
		this.setTriggeringPolicy(factoryData.getTriggeringPolicy());
	}

	private static class AsyncAction extends AbstractAction {
		private final Action action;
		private final RollingFileManager manager;

		public AsyncAction(Action act, RollingFileManager manager) {
			this.action = act;
			this.manager = manager;
		}

		@Override
		public boolean execute() throws IOException {
			boolean var1;
			try {
				var1 = this.action.execute();
			} finally {
				this.manager.semaphore.release();
			}

			return var1;
		}

		@Override
		public void close() {
			this.action.close();
		}

		@Override
		public boolean isComplete() {
			return this.action.isComplete();
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString());
			builder.append("[action=");
			builder.append(this.action);
			builder.append(", manager=");
			builder.append(this.manager);
			builder.append(", isComplete()=");
			builder.append(this.isComplete());
			builder.append(", isInterrupted()=");
			builder.append(this.isInterrupted());
			builder.append("]");
			return builder.toString();
		}
	}

	private static class EmptyQueue extends ArrayBlockingQueue<Runnable> {
		EmptyQueue() {
			super(1);
		}

		public int remainingCapacity() {
			return 0;
		}

		public boolean add(Runnable runnable) {
			throw new IllegalStateException("Queue is full");
		}

		public void put(Runnable runnable) throws InterruptedException {
			throw new InterruptedException("Unable to insert into queue");
		}

		public boolean offer(Runnable runnable, long timeout, TimeUnit timeUnit) throws InterruptedException {
			Thread.sleep(timeUnit.toMillis(timeout));
			return false;
		}

		public boolean addAll(Collection<? extends Runnable> collection) {
			if (collection.size() > 0) {
				throw new IllegalArgumentException("Too many items in collection");
			} else {
				return false;
			}
		}
	}

	private static class FactoryData extends ConfigurationFactoryData {
		private final String fileName;
		private final String pattern;
		private final boolean append;
		private final boolean bufferedIO;
		private final int bufferSize;
		private final boolean immediateFlush;
		private final boolean createOnDemand;
		private final TriggeringPolicy policy;
		private final RolloverStrategy strategy;
		private final String advertiseURI;
		private final Layout<? extends Serializable> layout;

		public FactoryData(
			String fileName,
			String pattern,
			boolean append,
			boolean bufferedIO,
			TriggeringPolicy policy,
			RolloverStrategy strategy,
			String advertiseURI,
			Layout<? extends Serializable> layout,
			int bufferSize,
			boolean immediateFlush,
			boolean createOnDemand,
			Configuration configuration
		) {
			super(configuration);
			this.fileName = fileName;
			this.pattern = pattern;
			this.append = append;
			this.bufferedIO = bufferedIO;
			this.bufferSize = bufferSize;
			this.policy = policy;
			this.strategy = strategy;
			this.advertiseURI = advertiseURI;
			this.layout = layout;
			this.immediateFlush = immediateFlush;
			this.createOnDemand = createOnDemand;
		}

		public TriggeringPolicy getTriggeringPolicy() {
			return this.policy;
		}

		public RolloverStrategy getRolloverStrategy() {
			return this.strategy;
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString());
			builder.append("[pattern=");
			builder.append(this.pattern);
			builder.append(", append=");
			builder.append(this.append);
			builder.append(", bufferedIO=");
			builder.append(this.bufferedIO);
			builder.append(", bufferSize=");
			builder.append(this.bufferSize);
			builder.append(", policy=");
			builder.append(this.policy);
			builder.append(", strategy=");
			builder.append(this.strategy);
			builder.append(", advertiseURI=");
			builder.append(this.advertiseURI);
			builder.append(", layout=");
			builder.append(this.layout);
			builder.append("]");
			return builder.toString();
		}
	}

	private static class RollingFileManagerFactory implements ManagerFactory<RollingFileManager, RollingFileManager.FactoryData> {
		private RollingFileManagerFactory() {
		}

		public RollingFileManager createManager(String name, RollingFileManager.FactoryData data) {
			long size = 0L;
			boolean writeHeader = !data.append;
			File file = null;
			if (data.fileName != null) {
				file = new File(data.fileName);
				writeHeader = !data.append || !file.exists();

				try {
					FileUtils.makeParentDirs(file);
					boolean created = data.createOnDemand ? false : file.createNewFile();
					RollingFileManager.LOGGER.trace("New file '{}' created = {}", name, created);
				} catch (IOException var12) {
					RollingFileManager.LOGGER.error("Unable to create file " + name, (Throwable)var12);
					return null;
				}

				size = data.append ? file.length() : 0L;
			}

			try {
				int actualSize = data.bufferedIO ? data.bufferSize : Constants.ENCODER_BYTE_BUFFER_SIZE;
				ByteBuffer buffer = ByteBuffer.wrap(new byte[actualSize]);
				OutputStream os = !data.createOnDemand && data.fileName != null ? new FileOutputStream(data.fileName, data.append) : null;
				long time = !data.createOnDemand && file != null ? file.lastModified() : System.currentTimeMillis();
				return new RollingFileManager(
					data.getLoggerContext(),
					data.fileName,
					data.pattern,
					os,
					data.append,
					data.createOnDemand,
					size,
					time,
					data.policy,
					data.strategy,
					data.advertiseURI,
					data.layout,
					writeHeader,
					buffer
				);
			} catch (IOException var13) {
				RollingFileManager.LOGGER.error("RollingFileManager (" + name + ") " + var13, (Throwable)var13);
				return null;
			}
		}
	}
}
