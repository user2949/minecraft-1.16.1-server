package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.util.AttributeMap;
import java.net.SocketAddress;

public interface Channel extends AttributeMap, ChannelOutboundInvoker, Comparable<Channel> {
	ChannelId id();

	EventLoop eventLoop();

	Channel parent();

	ChannelConfig config();

	boolean isOpen();

	boolean isRegistered();

	boolean isActive();

	ChannelMetadata metadata();

	SocketAddress localAddress();

	SocketAddress remoteAddress();

	ChannelFuture closeFuture();

	boolean isWritable();

	long bytesBeforeUnwritable();

	long bytesBeforeWritable();

	Channel.Unsafe unsafe();

	ChannelPipeline pipeline();

	ByteBufAllocator alloc();

	Channel read();

	Channel flush();

	public interface Unsafe {
		Handle recvBufAllocHandle();

		SocketAddress localAddress();

		SocketAddress remoteAddress();

		void register(EventLoop eventLoop, ChannelPromise channelPromise);

		void bind(SocketAddress socketAddress, ChannelPromise channelPromise);

		void connect(SocketAddress socketAddress1, SocketAddress socketAddress2, ChannelPromise channelPromise);

		void disconnect(ChannelPromise channelPromise);

		void close(ChannelPromise channelPromise);

		void closeForcibly();

		void deregister(ChannelPromise channelPromise);

		void beginRead();

		void write(Object object, ChannelPromise channelPromise);

		void flush();

		ChannelPromise voidPromise();

		ChannelOutboundBuffer outboundBuffer();
	}
}
