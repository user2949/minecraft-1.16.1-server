package io.netty.handler.codec.dns;

public interface DnsQuestion extends DnsRecord {
	@Override
	long timeToLive();
}
