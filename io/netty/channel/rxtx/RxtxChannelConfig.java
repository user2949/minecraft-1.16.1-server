package io.netty.channel.rxtx;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

@Deprecated
public interface RxtxChannelConfig extends ChannelConfig {
	RxtxChannelConfig setBaudrate(int integer);

	RxtxChannelConfig setStopbits(RxtxChannelConfig.Stopbits stopbits);

	RxtxChannelConfig setDatabits(RxtxChannelConfig.Databits databits);

	RxtxChannelConfig setParitybit(RxtxChannelConfig.Paritybit paritybit);

	int getBaudrate();

	RxtxChannelConfig.Stopbits getStopbits();

	RxtxChannelConfig.Databits getDatabits();

	RxtxChannelConfig.Paritybit getParitybit();

	boolean isDtr();

	RxtxChannelConfig setDtr(boolean boolean1);

	boolean isRts();

	RxtxChannelConfig setRts(boolean boolean1);

	int getWaitTimeMillis();

	RxtxChannelConfig setWaitTimeMillis(int integer);

	RxtxChannelConfig setReadTimeout(int integer);

	int getReadTimeout();

	RxtxChannelConfig setConnectTimeoutMillis(int integer);

	@Deprecated
	RxtxChannelConfig setMaxMessagesPerRead(int integer);

	RxtxChannelConfig setWriteSpinCount(int integer);

	RxtxChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

	RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

	RxtxChannelConfig setAutoRead(boolean boolean1);

	RxtxChannelConfig setAutoClose(boolean boolean1);

	RxtxChannelConfig setWriteBufferHighWaterMark(int integer);

	RxtxChannelConfig setWriteBufferLowWaterMark(int integer);

	RxtxChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

	RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

	public static enum Databits {
		DATABITS_5(5),
		DATABITS_6(6),
		DATABITS_7(7),
		DATABITS_8(8);

		private final int value;

		private Databits(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}

		public static RxtxChannelConfig.Databits valueOf(int value) {
			for (RxtxChannelConfig.Databits databit : values()) {
				if (databit.value == value) {
					return databit;
				}
			}

			throw new IllegalArgumentException("unknown " + RxtxChannelConfig.Databits.class.getSimpleName() + " value: " + value);
		}
	}

	public static enum Paritybit {
		NONE(0),
		ODD(1),
		EVEN(2),
		MARK(3),
		SPACE(4);

		private final int value;

		private Paritybit(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}

		public static RxtxChannelConfig.Paritybit valueOf(int value) {
			for (RxtxChannelConfig.Paritybit paritybit : values()) {
				if (paritybit.value == value) {
					return paritybit;
				}
			}

			throw new IllegalArgumentException("unknown " + RxtxChannelConfig.Paritybit.class.getSimpleName() + " value: " + value);
		}
	}

	public static enum Stopbits {
		STOPBITS_1(1),
		STOPBITS_2(2),
		STOPBITS_1_5(3);

		private final int value;

		private Stopbits(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}

		public static RxtxChannelConfig.Stopbits valueOf(int value) {
			for (RxtxChannelConfig.Stopbits stopbit : values()) {
				if (stopbit.value == value) {
					return stopbit;
				}
			}

			throw new IllegalArgumentException("unknown " + RxtxChannelConfig.Stopbits.class.getSimpleName() + " value: " + value);
		}
	}
}
