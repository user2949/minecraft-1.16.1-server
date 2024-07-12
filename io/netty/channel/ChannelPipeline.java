package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {
	ChannelPipeline addFirst(String string, ChannelHandler channelHandler);

	ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, String string, ChannelHandler channelHandler);

	ChannelPipeline addLast(String string, ChannelHandler channelHandler);

	ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, String string, ChannelHandler channelHandler);

	ChannelPipeline addBefore(String string1, String string2, ChannelHandler channelHandler);

	ChannelPipeline addBefore(EventExecutorGroup eventExecutorGroup, String string2, String string3, ChannelHandler channelHandler);

	ChannelPipeline addAfter(String string1, String string2, ChannelHandler channelHandler);

	ChannelPipeline addAfter(EventExecutorGroup eventExecutorGroup, String string2, String string3, ChannelHandler channelHandler);

	ChannelPipeline addFirst(ChannelHandler... arr);

	ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, ChannelHandler... arr);

	ChannelPipeline addLast(ChannelHandler... arr);

	ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, ChannelHandler... arr);

	ChannelPipeline remove(ChannelHandler channelHandler);

	ChannelHandler remove(String string);

	<T extends ChannelHandler> T remove(Class<T> class1);

	ChannelHandler removeFirst();

	ChannelHandler removeLast();

	ChannelPipeline replace(ChannelHandler channelHandler1, String string, ChannelHandler channelHandler3);

	ChannelHandler replace(String string1, String string2, ChannelHandler channelHandler);

	<T extends ChannelHandler> T replace(Class<T> class1, String string, ChannelHandler channelHandler);

	ChannelHandler first();

	ChannelHandlerContext firstContext();

	ChannelHandler last();

	ChannelHandlerContext lastContext();

	ChannelHandler get(String string);

	<T extends ChannelHandler> T get(Class<T> class1);

	ChannelHandlerContext context(ChannelHandler channelHandler);

	ChannelHandlerContext context(String string);

	ChannelHandlerContext context(Class<? extends ChannelHandler> class1);

	Channel channel();

	List<String> names();

	Map<String, ChannelHandler> toMap();

	ChannelPipeline fireChannelRegistered();

	ChannelPipeline fireChannelUnregistered();

	ChannelPipeline fireChannelActive();

	ChannelPipeline fireChannelInactive();

	ChannelPipeline fireExceptionCaught(Throwable throwable);

	ChannelPipeline fireUserEventTriggered(Object object);

	ChannelPipeline fireChannelRead(Object object);

	ChannelPipeline fireChannelReadComplete();

	ChannelPipeline fireChannelWritabilityChanged();

	ChannelPipeline flush();
}
