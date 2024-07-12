package io.netty.channel;

import io.netty.channel.MessageSizeEstimator.Handle;
import io.netty.util.internal.ObjectUtil;

abstract class PendingBytesTracker implements Handle {
	private final Handle estimatorHandle;

	private PendingBytesTracker(Handle estimatorHandle) {
		this.estimatorHandle = ObjectUtil.checkNotNull(estimatorHandle, "estimatorHandle");
	}

	@Override
	public final int size(Object msg) {
		return this.estimatorHandle.size(msg);
	}

	public abstract void incrementPendingOutboundBytes(long long1);

	public abstract void decrementPendingOutboundBytes(long long1);

	static PendingBytesTracker newTracker(Channel channel) {
		if (channel.pipeline() instanceof DefaultChannelPipeline) {
			return new PendingBytesTracker.DefaultChannelPipelinePendingBytesTracker((DefaultChannelPipeline)channel.pipeline());
		} else {
			ChannelOutboundBuffer buffer = channel.unsafe().outboundBuffer();
			Handle handle = channel.config().getMessageSizeEstimator().newHandle();
			return (PendingBytesTracker)(buffer == null
				? new PendingBytesTracker.NoopPendingBytesTracker(handle)
				: new PendingBytesTracker.ChannelOutboundBufferPendingBytesTracker(buffer, handle));
		}
	}

	private static final class ChannelOutboundBufferPendingBytesTracker extends PendingBytesTracker {
		private final ChannelOutboundBuffer buffer;

		ChannelOutboundBufferPendingBytesTracker(ChannelOutboundBuffer buffer, Handle estimatorHandle) {
			super(estimatorHandle);
			this.buffer = buffer;
		}

		@Override
		public void incrementPendingOutboundBytes(long bytes) {
			this.buffer.incrementPendingOutboundBytes(bytes);
		}

		@Override
		public void decrementPendingOutboundBytes(long bytes) {
			this.buffer.decrementPendingOutboundBytes(bytes);
		}
	}

	private static final class DefaultChannelPipelinePendingBytesTracker extends PendingBytesTracker {
		private final DefaultChannelPipeline pipeline;

		DefaultChannelPipelinePendingBytesTracker(DefaultChannelPipeline pipeline) {
			super(pipeline.estimatorHandle());
			this.pipeline = pipeline;
		}

		@Override
		public void incrementPendingOutboundBytes(long bytes) {
			this.pipeline.incrementPendingOutboundBytes(bytes);
		}

		@Override
		public void decrementPendingOutboundBytes(long bytes) {
			this.pipeline.decrementPendingOutboundBytes(bytes);
		}
	}

	private static final class NoopPendingBytesTracker extends PendingBytesTracker {
		NoopPendingBytesTracker(Handle estimatorHandle) {
			super(estimatorHandle);
		}

		@Override
		public void incrementPendingOutboundBytes(long bytes) {
		}

		@Override
		public void decrementPendingOutboundBytes(long bytes) {
		}
	}
}
