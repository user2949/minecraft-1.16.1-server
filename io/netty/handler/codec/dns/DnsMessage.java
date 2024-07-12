package io.netty.handler.codec.dns;

import io.netty.util.ReferenceCounted;

public interface DnsMessage extends ReferenceCounted {
	int id();

	DnsMessage setId(int integer);

	DnsOpCode opCode();

	DnsMessage setOpCode(DnsOpCode dnsOpCode);

	boolean isRecursionDesired();

	DnsMessage setRecursionDesired(boolean boolean1);

	int z();

	DnsMessage setZ(int integer);

	int count(DnsSection dnsSection);

	int count();

	<T extends DnsRecord> T recordAt(DnsSection dnsSection);

	<T extends DnsRecord> T recordAt(DnsSection dnsSection, int integer);

	DnsMessage setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

	<T extends DnsRecord> T setRecord(DnsSection dnsSection, int integer, DnsRecord dnsRecord);

	DnsMessage addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

	DnsMessage addRecord(DnsSection dnsSection, int integer, DnsRecord dnsRecord);

	<T extends DnsRecord> T removeRecord(DnsSection dnsSection, int integer);

	DnsMessage clear(DnsSection dnsSection);

	DnsMessage clear();

	DnsMessage touch();

	DnsMessage touch(Object object);

	DnsMessage retain();

	DnsMessage retain(int integer);
}
