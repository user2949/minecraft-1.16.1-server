package io.netty.handler.codec.dns;

public interface DnsQuery extends DnsMessage {
	DnsQuery setId(int integer);

	DnsQuery setOpCode(DnsOpCode dnsOpCode);

	DnsQuery setRecursionDesired(boolean boolean1);

	DnsQuery setZ(int integer);

	DnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

	DnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

	DnsQuery addRecord(DnsSection dnsSection, int integer, DnsRecord dnsRecord);

	DnsQuery clear(DnsSection dnsSection);

	DnsQuery clear();

	DnsQuery touch();

	DnsQuery touch(Object object);

	DnsQuery retain();

	DnsQuery retain(int integer);
}
