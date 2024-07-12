package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsRecord;
import java.net.InetAddress;
import java.util.List;

public interface DnsCache {
	void clear();

	boolean clear(String string);

	List<? extends DnsCacheEntry> get(String string, DnsRecord[] arr);

	DnsCacheEntry cache(String string, DnsRecord[] arr, InetAddress inetAddress, long long4, EventLoop eventLoop);

	DnsCacheEntry cache(String string, DnsRecord[] arr, Throwable throwable, EventLoop eventLoop);
}
