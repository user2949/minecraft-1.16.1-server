package io.netty.channel.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

public interface NioTask<C extends SelectableChannel> {
	void channelReady(C selectableChannel, SelectionKey selectionKey) throws Exception;

	void channelUnregistered(C selectableChannel, Throwable throwable) throws Exception;
}
