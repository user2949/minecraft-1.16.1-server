package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.util.UncheckedBooleanSupplier;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class DefaultMaxBytesRecvByteBufAllocator implements MaxBytesRecvByteBufAllocator {
	private volatile int maxBytesPerRead;
	private volatile int maxBytesPerIndividualRead;

	public DefaultMaxBytesRecvByteBufAllocator() {
		this(65536, 65536);
	}

	public DefaultMaxBytesRecvByteBufAllocator(int maxBytesPerRead, int maxBytesPerIndividualRead) {
		checkMaxBytesPerReadPair(maxBytesPerRead, maxBytesPerIndividualRead);
		this.maxBytesPerRead = maxBytesPerRead;
		this.maxBytesPerIndividualRead = maxBytesPerIndividualRead;
	}

	@Override
	public Handle newHandle() {
		return new DefaultMaxBytesRecvByteBufAllocator.HandleImpl();
	}

	@Override
	public int maxBytesPerRead() {
		return this.maxBytesPerRead;
	}

	public DefaultMaxBytesRecvByteBufAllocator maxBytesPerRead(int maxBytesPerRead) {
		if (maxBytesPerRead <= 0) {
			throw new IllegalArgumentException("maxBytesPerRead: " + maxBytesPerRead + " (expected: > 0)");
		} else {
			synchronized (this) {
				int maxBytesPerIndividualRead = this.maxBytesPerIndividualRead();
				if (maxBytesPerRead < maxBytesPerIndividualRead) {
					throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + maxBytesPerIndividualRead + "): " + maxBytesPerRead);
				} else {
					this.maxBytesPerRead = maxBytesPerRead;
					return this;
				}
			}
		}
	}

	@Override
	public int maxBytesPerIndividualRead() {
		return this.maxBytesPerIndividualRead;
	}

	public DefaultMaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int maxBytesPerIndividualRead) {
		if (maxBytesPerIndividualRead <= 0) {
			throw new IllegalArgumentException("maxBytesPerIndividualRead: " + maxBytesPerIndividualRead + " (expected: > 0)");
		} else {
			synchronized (this) {
				int maxBytesPerRead = this.maxBytesPerRead();
				if (maxBytesPerIndividualRead > maxBytesPerRead) {
					throw new IllegalArgumentException(
						"maxBytesPerIndividualRead cannot be greater than maxBytesPerRead (" + maxBytesPerRead + "): " + maxBytesPerIndividualRead
					);
				} else {
					this.maxBytesPerIndividualRead = maxBytesPerIndividualRead;
					return this;
				}
			}
		}
	}

	@Override
	public synchronized Entry<Integer, Integer> maxBytesPerReadPair() {
		return new SimpleEntry(this.maxBytesPerRead, this.maxBytesPerIndividualRead);
	}

	private static void checkMaxBytesPerReadPair(int maxBytesPerRead, int maxBytesPerIndividualRead) {
		if (maxBytesPerRead <= 0) {
			throw new IllegalArgumentException("maxBytesPerRead: " + maxBytesPerRead + " (expected: > 0)");
		} else if (maxBytesPerIndividualRead <= 0) {
			throw new IllegalArgumentException("maxBytesPerIndividualRead: " + maxBytesPerIndividualRead + " (expected: > 0)");
		} else if (maxBytesPerRead < maxBytesPerIndividualRead) {
			throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + maxBytesPerIndividualRead + "): " + maxBytesPerRead);
		}
	}

	public DefaultMaxBytesRecvByteBufAllocator maxBytesPerReadPair(int maxBytesPerRead, int maxBytesPerIndividualRead) {
		checkMaxBytesPerReadPair(maxBytesPerRead, maxBytesPerIndividualRead);
		synchronized (this) {
			this.maxBytesPerRead = maxBytesPerRead;
			this.maxBytesPerIndividualRead = maxBytesPerIndividualRead;
			return this;
		}
	}

	private final class HandleImpl implements ExtendedHandle {
		private int individualReadMax;
		private int bytesToRead;
		private int lastBytesRead;
		private int attemptBytesRead;
		private final UncheckedBooleanSupplier defaultMaybeMoreSupplier = new UncheckedBooleanSupplier() {
			@Override
			public boolean get() {
				return HandleImpl.this.attemptBytesRead == HandleImpl.this.lastBytesRead;
			}
		};

		private HandleImpl() {
		}

		@Override
		public ByteBuf allocate(ByteBufAllocator alloc) {
			return alloc.ioBuffer(this.guess());
		}

		@Override
		public int guess() {
			return Math.min(this.individualReadMax, this.bytesToRead);
		}

		@Override
		public void reset(ChannelConfig config) {
			this.bytesToRead = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerRead();
			this.individualReadMax = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerIndividualRead();
		}

		@Override
		public void incMessagesRead(int amt) {
		}

		@Override
		public void lastBytesRead(int bytes) {
			this.lastBytesRead = bytes;
			this.bytesToRead -= bytes;
		}

		@Override
		public int lastBytesRead() {
			return this.lastBytesRead;
		}

		@Override
		public boolean continueReading() {
			return this.continueReading(this.defaultMaybeMoreSupplier);
		}

		@Override
		public boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
			return this.bytesToRead > 0 && maybeMoreDataSupplier.get();
		}

		@Override
		public void readComplete() {
		}

		@Override
		public void attemptedBytesRead(int bytes) {
			this.attemptBytesRead = bytes;
		}

		@Override
		public int attemptedBytesRead() {
			return this.attemptBytesRead;
		}
	}
}
