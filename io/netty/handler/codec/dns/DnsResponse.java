package io.netty.handler.codec.dns;

public interface DnsResponse extends DnsMessage {
	boolean isAuthoritativeAnswer();

	DnsResponse setAuthoritativeAnswer(boolean boolean1);

	boolean isTruncated();

	DnsResponse setTruncated(boolean boolean1);

	boolean isRecursionAvailable();

	DnsResponse setRecursionAvailable(boolean boolean1);

	DnsResponseCode code();

	DnsResponse setCode(DnsResponseCode dnsResponseCode);

	DnsResponse setId(int integer);

	DnsResponse setOpCode(DnsOpCode dnsOpCode);

	DnsResponse setRecursionDesired(boolean boolean1);

	DnsResponse setZ(int integer);

	DnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

	DnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

	DnsResponse addRecord(DnsSection dnsSection, int integer, DnsRecord dnsRecord);

	DnsResponse clear(DnsSection dnsSection);

	DnsResponse clear();

	DnsResponse touch();

	DnsResponse touch(Object object);

	DnsResponse retain();

	DnsResponse retain(int integer);
}
