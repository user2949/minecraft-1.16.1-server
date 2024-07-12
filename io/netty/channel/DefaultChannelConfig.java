package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.ObjectUtil;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultChannelConfig implements ChannelConfig {
	private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
	private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
	private static final AtomicIntegerFieldUpdater<DefaultChannelConfig> AUTOREAD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(
		DefaultChannelConfig.class, "autoRead"
	);
	private static final AtomicReferenceFieldUpdater<DefaultChannelConfig, WriteBufferWaterMark> WATERMARK_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
		DefaultChannelConfig.class, WriteBufferWaterMark.class, "writeBufferWaterMark"
	);
	protected final Channel channel;
	private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
	private volatile RecvByteBufAllocator rcvBufAllocator;
	private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
	private volatile int connectTimeoutMillis = 30000;
	private volatile int writeSpinCount = 16;
	private volatile int autoRead = 1;
	private volatile boolean autoClose = true;
	private volatile WriteBufferWaterMark writeBufferWaterMark = WriteBufferWaterMark.DEFAULT;
	private volatile boolean pinEventExecutor = true;

	public DefaultChannelConfig(Channel channel) {
		this(channel, new AdaptiveRecvByteBufAllocator());
	}

	protected DefaultChannelConfig(Channel channel, RecvByteBufAllocator allocator) {
		this.setRecvByteBufAllocator(allocator, channel.metadata());
		this.channel = channel;
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(
			null,
			ChannelOption.CONNECT_TIMEOUT_MILLIS,
			ChannelOption.MAX_MESSAGES_PER_READ,
			ChannelOption.WRITE_SPIN_COUNT,
			ChannelOption.ALLOCATOR,
			ChannelOption.AUTO_READ,
			ChannelOption.AUTO_CLOSE,
			ChannelOption.RCVBUF_ALLOCATOR,
			ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,
			ChannelOption.WRITE_BUFFER_LOW_WATER_MARK,
			ChannelOption.WRITE_BUFFER_WATER_MARK,
			ChannelOption.MESSAGE_SIZE_ESTIMATOR,
			ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP
		);
	}

	protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
		if (result == null) {
			result = new IdentityHashMap();
		}

		for (ChannelOption<?> o : options) {
			result.put(o, this.getOption(o));
		}

		return result;
	}

	@Override
	public boolean setOptions(Map<ChannelOption<?>, ?> options) {
		if (options == null) {
			throw new NullPointerException("options");
		} else {
			boolean setAllOptions = true;

			for (Entry<ChannelOption<?>, ?> e : options.entrySet()) {
				if (!this.setOption((ChannelOption<Object>)e.getKey(), e.getValue())) {
					setAllOptions = false;
				}
			}

			return setAllOptions;
		}
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		if (option == null) {
			throw new NullPointerException("option");
		} else if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
			return (T)this.getConnectTimeoutMillis();
		} else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
			return (T)this.getMaxMessagesPerRead();
		} else if (option == ChannelOption.WRITE_SPIN_COUNT) {
			return (T)this.getWriteSpinCount();
		} else if (option == ChannelOption.ALLOCATOR) {
			return (T)this.getAllocator();
		} else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
			return this.getRecvByteBufAllocator();
		} else if (option == ChannelOption.AUTO_READ) {
			return (T)this.isAutoRead();
		} else if (option == ChannelOption.AUTO_CLOSE) {
			return (T)this.isAutoClose();
		} else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
			return (T)this.getWriteBufferHighWaterMark();
		} else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
			return (T)this.getWriteBufferLowWaterMark();
		} else if (option == ChannelOption.WRITE_BUFFER_WATER_MARK) {
			return (T)this.getWriteBufferWaterMark();
		} else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
			return (T)this.getMessageSizeEstimator();
		} else {
			return (T)(option == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP ? this.getPinEventExecutorPerGroup() : null);
		}
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
			this.setConnectTimeoutMillis((Integer)value);
		} else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
			this.setMaxMessagesPerRead((Integer)value);
		} else if (option == ChannelOption.WRITE_SPIN_COUNT) {
			this.setWriteSpinCount((Integer)value);
		} else if (option == ChannelOption.ALLOCATOR) {
			this.setAllocator((ByteBufAllocator)value);
		} else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
			this.setRecvByteBufAllocator((RecvByteBufAllocator)value);
		} else if (option == ChannelOption.AUTO_READ) {
			this.setAutoRead((Boolean)value);
		} else if (option == ChannelOption.AUTO_CLOSE) {
			this.setAutoClose((Boolean)value);
		} else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
			this.setWriteBufferHighWaterMark((Integer)value);
		} else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
			this.setWriteBufferLowWaterMark((Integer)value);
		} else if (option == ChannelOption.WRITE_BUFFER_WATER_MARK) {
			this.setWriteBufferWaterMark((WriteBufferWaterMark)value);
		} else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
			this.setMessageSizeEstimator((MessageSizeEstimator)value);
		} else {
			if (option != ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
				return false;
			}

			this.setPinEventExecutorPerGroup((Boolean)value);
		}

		return true;
	}

	protected <T> void validate(ChannelOption<T> option, T value) {
		if (option == null) {
			throw new NullPointerException("option");
		} else {
			option.validate(value);
		}
	}

	@Override
	public int getConnectTimeoutMillis() {
		return this.connectTimeoutMillis;
	}

	@Override
	public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		if (connectTimeoutMillis < 0) {
			throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", connectTimeoutMillis));
		} else {
			this.connectTimeoutMillis = connectTimeoutMillis;
			return this;
		}
	}

	@Deprecated
	@Override
	public int getMaxMessagesPerRead() {
		try {
			MaxMessagesRecvByteBufAllocator allocator = this.getRecvByteBufAllocator();
			return allocator.maxMessagesPerRead();
		} catch (ClassCastException var2) {
			throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", var2);
		}
	}

	@Deprecated
	@Override
	public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		try {
			MaxMessagesRecvByteBufAllocator allocator = this.getRecvByteBufAllocator();
			allocator.maxMessagesPerRead(maxMessagesPerRead);
			return this;
		} catch (ClassCastException var3) {
			throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", var3);
		}
	}

	@Override
	public int getWriteSpinCount() {
		return this.writeSpinCount;
	}

	@Override
	public ChannelConfig setWriteSpinCount(int writeSpinCount) {
		if (writeSpinCount <= 0) {
			throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
		} else {
			if (writeSpinCount == Integer.MAX_VALUE) {
				writeSpinCount--;
			}

			this.writeSpinCount = writeSpinCount;
			return this;
		}
	}

	@Override
	public ByteBufAllocator getAllocator() {
		return this.allocator;
	}

	@Override
	public ChannelConfig setAllocator(ByteBufAllocator allocator) {
		if (allocator == null) {
			throw new NullPointerException("allocator");
		} else {
			this.allocator = allocator;
			return this;
		}
	}

	@Override
	public <T extends RecvByteBufAllocator> T getRecvByteBufAllocator() {
		return (T)this.rcvBufAllocator;
	}

	@Override
	public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		this.rcvBufAllocator = ObjectUtil.checkNotNull(allocator, "allocator");
		return this;
	}

	private void setRecvByteBufAllocator(RecvByteBufAllocator allocator, ChannelMetadata metadata) {
		if (allocator instanceof MaxMessagesRecvByteBufAllocator) {
			((MaxMessagesRecvByteBufAllocator)allocator).maxMessagesPerRead(metadata.defaultMaxMessagesPerRead());
		} else if (allocator == null) {
			throw new NullPointerException("allocator");
		}

		this.setRecvByteBufAllocator(allocator);
	}

	@Override
	public boolean isAutoRead() {
		return this.autoRead == 1;
	}

	@Override
	public ChannelConfig setAutoRead(boolean autoRead) {
		boolean oldAutoRead = AUTOREAD_UPDATER.getAndSet(this, autoRead ? 1 : 0) == 1;
		if (autoRead && !oldAutoRead) {
			this.channel.read();
		} else if (!autoRead && oldAutoRead) {
			this.autoReadCleared();
		}

		return this;
	}

	protected void autoReadCleared() {
	}

	@Override
	public boolean isAutoClose() {
		return this.autoClose;
	}

	@Override
	public ChannelConfig setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
		return this;
	}

	@Override
	public int getWriteBufferHighWaterMark() {
		return this.writeBufferWaterMark.high();
	}

	@Override
	public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		if (writeBufferHighWaterMark < 0) {
			throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
		} else {
			WriteBufferWaterMark waterMark;
			do {
				waterMark = this.writeBufferWaterMark;
				if (writeBufferHighWaterMark < waterMark.low()) {
					throw new IllegalArgumentException(
						"writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + waterMark.low() + "): " + writeBufferHighWaterMark
					);
				}
			} while (!WATERMARK_UPDATER.compareAndSet(this, waterMark, new WriteBufferWaterMark(waterMark.low(), writeBufferHighWaterMark, false)));

			return this;
		}
	}

	@Override
	public int getWriteBufferLowWaterMark() {
		return this.writeBufferWaterMark.low();
	}

	@Override
	public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		if (writeBufferLowWaterMark < 0) {
			throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
		} else {
			WriteBufferWaterMark waterMark;
			do {
				waterMark = this.writeBufferWaterMark;
				if (writeBufferLowWaterMark > waterMark.high()) {
					throw new IllegalArgumentException(
						"writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + waterMark.high() + "): " + writeBufferLowWaterMark
					);
				}
			} while (!WATERMARK_UPDATER.compareAndSet(this, waterMark, new WriteBufferWaterMark(writeBufferLowWaterMark, waterMark.high(), false)));

			return this;
		}
	}

	@Override
	public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		this.writeBufferWaterMark = ObjectUtil.checkNotNull(writeBufferWaterMark, "writeBufferWaterMark");
		return this;
	}

	@Override
	public WriteBufferWaterMark getWriteBufferWaterMark() {
		return this.writeBufferWaterMark;
	}

	@Override
	public MessageSizeEstimator getMessageSizeEstimator() {
		return this.msgSizeEstimator;
	}

	@Override
	public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		if (estimator == null) {
			throw new NullPointerException("estimator");
		} else {
			this.msgSizeEstimator = estimator;
			return this;
		}
	}

	private ChannelConfig setPinEventExecutorPerGroup(boolean pinEventExecutor) {
		this.pinEventExecutor = pinEventExecutor;
		return this;
	}

	private boolean getPinEventExecutorPerGroup() {
		return this.pinEventExecutor;
	}
}
