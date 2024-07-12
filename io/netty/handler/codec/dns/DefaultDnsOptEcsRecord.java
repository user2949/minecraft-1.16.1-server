package io.netty.handler.codec.dns;

import io.netty.channel.socket.InternetProtocolFamily;
import java.util.Arrays;

public final class DefaultDnsOptEcsRecord extends AbstractDnsOptPseudoRrRecord implements DnsOptEcsRecord {
	private final int srcPrefixLength;
	private final byte[] address;

	public DefaultDnsOptEcsRecord(int maxPayloadSize, int extendedRcode, int version, int srcPrefixLength, byte[] address) {
		super(maxPayloadSize, extendedRcode, version);
		this.srcPrefixLength = srcPrefixLength;
		this.address = (byte[])verifyAddress(address).clone();
	}

	public DefaultDnsOptEcsRecord(int maxPayloadSize, int srcPrefixLength, byte[] address) {
		this(maxPayloadSize, 0, 0, srcPrefixLength, address);
	}

	public DefaultDnsOptEcsRecord(int maxPayloadSize, InternetProtocolFamily protocolFamily) {
		this(maxPayloadSize, 0, 0, 0, protocolFamily.localhost().getAddress());
	}

	private static byte[] verifyAddress(byte[] bytes) {
		if (bytes.length != 4 && bytes.length != 16) {
			throw new IllegalArgumentException("bytes.length must either 4 or 16");
		} else {
			return bytes;
		}
	}

	@Override
	public int sourcePrefixLength() {
		return this.srcPrefixLength;
	}

	@Override
	public int scopePrefixLength() {
		return 0;
	}

	@Override
	public byte[] address() {
		return (byte[])this.address.clone();
	}

	@Override
	public String toString() {
		StringBuilder sb = this.toStringBuilder();
		sb.setLength(sb.length() - 1);
		return sb.append(" address:")
			.append(Arrays.toString(this.address))
			.append(" sourcePrefixLength:")
			.append(this.sourcePrefixLength())
			.append(" scopePrefixLength:")
			.append(this.scopePrefixLength())
			.append(')')
			.toString();
	}
}
