package io.netty.channel.epoll;

import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;

final class EpollRecvByteAllocatorStreamingHandle extends EpollRecvByteAllocatorHandle {
	public EpollRecvByteAllocatorStreamingHandle(ExtendedHandle handle) {
		super(handle);
	}

	@Override
	boolean maybeMoreDataToRead() {
		return this.lastBytesRead() == this.attemptedBytesRead() || this.isReceivedRdHup();
	}
}
