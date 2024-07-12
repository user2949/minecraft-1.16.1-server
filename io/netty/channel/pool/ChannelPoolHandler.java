package io.netty.channel.pool;

import io.netty.channel.Channel;

public interface ChannelPoolHandler {
	void channelReleased(Channel channel) throws Exception;

	void channelAcquired(Channel channel) throws Exception;

	void channelCreated(Channel channel) throws Exception;
}
