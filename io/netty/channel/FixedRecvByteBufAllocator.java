package io.netty.channel;

import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle;
import io.netty.channel.RecvByteBufAllocator.Handle;

public class FixedRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
	private final int bufferSize;

	public FixedRecvByteBufAllocator(int bufferSize) {
		if (bufferSize <= 0) {
			throw new IllegalArgumentException("bufferSize must greater than 0: " + bufferSize);
		} else {
			this.bufferSize = bufferSize;
		}
	}

	@Override
	public Handle newHandle() {
		return new FixedRecvByteBufAllocator.HandleImpl(this.bufferSize);
	}

	public FixedRecvByteBufAllocator respectMaybeMoreData(boolean respectMaybeMoreData) {
		super.respectMaybeMoreData(respectMaybeMoreData);
		return this;
	}

	private final class HandleImpl extends MaxMessageHandle {
		private final int bufferSize;

		public HandleImpl(int bufferSize) {
			super(FixedRecvByteBufAllocator.this);
			this.bufferSize = bufferSize;
		}

		@Override
		public int guess() {
			return this.bufferSize;
		}
	}
}
