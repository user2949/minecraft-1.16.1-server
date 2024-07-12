package io.netty.channel.pool;

public interface ChannelPoolMap<K, P extends ChannelPool> {
	P get(K object);

	boolean contains(K object);
}
