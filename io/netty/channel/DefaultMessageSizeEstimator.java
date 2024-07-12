package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.MessageSizeEstimator.Handle;

public final class DefaultMessageSizeEstimator implements MessageSizeEstimator {
	public static final MessageSizeEstimator DEFAULT = new DefaultMessageSizeEstimator(8);
	private final Handle handle;

	public DefaultMessageSizeEstimator(int unknownSize) {
		if (unknownSize < 0) {
			throw new IllegalArgumentException("unknownSize: " + unknownSize + " (expected: >= 0)");
		} else {
			this.handle = new DefaultMessageSizeEstimator.HandleImpl(unknownSize);
		}
	}

	@Override
	public Handle newHandle() {
		return this.handle;
	}

	private static final class HandleImpl implements Handle {
		private final int unknownSize;

		private HandleImpl(int unknownSize) {
			this.unknownSize = unknownSize;
		}

		@Override
		public int size(Object msg) {
			if (msg instanceof ByteBuf) {
				return ((ByteBuf)msg).readableBytes();
			} else if (msg instanceof ByteBufHolder) {
				return ((ByteBufHolder)msg).content().readableBytes();
			} else {
				return msg instanceof FileRegion ? 0 : this.unknownSize;
			}
		}
	}
}
