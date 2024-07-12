package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import java.util.Set;

public interface ChannelGroup extends Set<Channel>, Comparable<ChannelGroup> {
	String name();

	Channel find(ChannelId channelId);

	ChannelGroupFuture write(Object object);

	ChannelGroupFuture write(Object object, ChannelMatcher channelMatcher);

	ChannelGroupFuture write(Object object, ChannelMatcher channelMatcher, boolean boolean3);

	ChannelGroup flush();

	ChannelGroup flush(ChannelMatcher channelMatcher);

	ChannelGroupFuture writeAndFlush(Object object);

	@Deprecated
	ChannelGroupFuture flushAndWrite(Object object);

	ChannelGroupFuture writeAndFlush(Object object, ChannelMatcher channelMatcher);

	ChannelGroupFuture writeAndFlush(Object object, ChannelMatcher channelMatcher, boolean boolean3);

	@Deprecated
	ChannelGroupFuture flushAndWrite(Object object, ChannelMatcher channelMatcher);

	ChannelGroupFuture disconnect();

	ChannelGroupFuture disconnect(ChannelMatcher channelMatcher);

	ChannelGroupFuture close();

	ChannelGroupFuture close(ChannelMatcher channelMatcher);

	@Deprecated
	ChannelGroupFuture deregister();

	@Deprecated
	ChannelGroupFuture deregister(ChannelMatcher channelMatcher);

	ChannelGroupFuture newCloseFuture();

	ChannelGroupFuture newCloseFuture(ChannelMatcher channelMatcher);
}
