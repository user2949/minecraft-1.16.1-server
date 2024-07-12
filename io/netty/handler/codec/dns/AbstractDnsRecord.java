package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public abstract class AbstractDnsRecord implements DnsRecord {
	private final String name;
	private final DnsRecordType type;
	private final short dnsClass;
	private final long timeToLive;
	private int hashCode;

	protected AbstractDnsRecord(String name, DnsRecordType type, long timeToLive) {
		this(name, type, 1, timeToLive);
	}

	protected AbstractDnsRecord(String name, DnsRecordType type, int dnsClass, long timeToLive) {
		if (timeToLive < 0L) {
			throw new IllegalArgumentException("timeToLive: " + timeToLive + " (expected: >= 0)");
		} else {
			this.name = appendTrailingDot(IDN.toASCII(ObjectUtil.checkNotNull(name, "name")));
			this.type = ObjectUtil.checkNotNull(type, "type");
			this.dnsClass = (short)dnsClass;
			this.timeToLive = timeToLive;
		}
	}

	private static String appendTrailingDot(String name) {
		return name.length() > 0 && name.charAt(name.length() - 1) != '.' ? name + '.' : name;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public DnsRecordType type() {
		return this.type;
	}

	@Override
	public int dnsClass() {
		return this.dnsClass & 65535;
	}

	@Override
	public long timeToLive() {
		return this.timeToLive;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof DnsRecord)) {
			return false;
		} else {
			DnsRecord that = (DnsRecord)obj;
			int hashCode = this.hashCode;
			return hashCode != 0 && hashCode != that.hashCode()
				? false
				: this.type().intValue() == that.type().intValue() && this.dnsClass() == that.dnsClass() && this.name().equals(that.name());
		}
	}

	public int hashCode() {
		int hashCode = this.hashCode;
		return hashCode != 0 ? hashCode : (this.hashCode = this.name.hashCode() * 31 + this.type().intValue() * 31 + this.dnsClass());
	}

	public String toString() {
		StringBuilder buf = new StringBuilder(64);
		buf.append(StringUtil.simpleClassName(this)).append('(').append(this.name()).append(' ').append(this.timeToLive()).append(' ');
		DnsMessageUtil.appendRecordClass(buf, this.dnsClass()).append(' ').append(this.type().name()).append(')');
		return buf.toString();
	}
}
