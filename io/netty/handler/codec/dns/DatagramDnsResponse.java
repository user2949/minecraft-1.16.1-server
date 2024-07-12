package io.netty.handler.codec.dns;

import io.netty.channel.AddressedEnvelope;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class DatagramDnsResponse extends DefaultDnsResponse implements AddressedEnvelope<DatagramDnsResponse, InetSocketAddress> {
	private final InetSocketAddress sender;
	private final InetSocketAddress recipient;

	public DatagramDnsResponse(InetSocketAddress sender, InetSocketAddress recipient, int id) {
		this(sender, recipient, id, DnsOpCode.QUERY, DnsResponseCode.NOERROR);
	}

	public DatagramDnsResponse(InetSocketAddress sender, InetSocketAddress recipient, int id, DnsOpCode opCode) {
		this(sender, recipient, id, opCode, DnsResponseCode.NOERROR);
	}

	public DatagramDnsResponse(InetSocketAddress sender, InetSocketAddress recipient, int id, DnsOpCode opCode, DnsResponseCode responseCode) {
		super(id, opCode, responseCode);
		if (recipient == null && sender == null) {
			throw new NullPointerException("recipient and sender");
		} else {
			this.sender = sender;
			this.recipient = recipient;
		}
	}

	public DatagramDnsResponse content() {
		return this;
	}

	public InetSocketAddress sender() {
		return this.sender;
	}

	public InetSocketAddress recipient() {
		return this.recipient;
	}

	public DatagramDnsResponse setAuthoritativeAnswer(boolean authoritativeAnswer) {
		return (DatagramDnsResponse)super.setAuthoritativeAnswer(authoritativeAnswer);
	}

	public DatagramDnsResponse setTruncated(boolean truncated) {
		return (DatagramDnsResponse)super.setTruncated(truncated);
	}

	public DatagramDnsResponse setRecursionAvailable(boolean recursionAvailable) {
		return (DatagramDnsResponse)super.setRecursionAvailable(recursionAvailable);
	}

	public DatagramDnsResponse setCode(DnsResponseCode code) {
		return (DatagramDnsResponse)super.setCode(code);
	}

	public DatagramDnsResponse setId(int id) {
		return (DatagramDnsResponse)super.setId(id);
	}

	public DatagramDnsResponse setOpCode(DnsOpCode opCode) {
		return (DatagramDnsResponse)super.setOpCode(opCode);
	}

	public DatagramDnsResponse setRecursionDesired(boolean recursionDesired) {
		return (DatagramDnsResponse)super.setRecursionDesired(recursionDesired);
	}

	public DatagramDnsResponse setZ(int z) {
		return (DatagramDnsResponse)super.setZ(z);
	}

	public DatagramDnsResponse setRecord(DnsSection section, DnsRecord record) {
		return (DatagramDnsResponse)super.setRecord(section, record);
	}

	public DatagramDnsResponse addRecord(DnsSection section, DnsRecord record) {
		return (DatagramDnsResponse)super.addRecord(section, record);
	}

	public DatagramDnsResponse addRecord(DnsSection section, int index, DnsRecord record) {
		return (DatagramDnsResponse)super.addRecord(section, index, record);
	}

	public DatagramDnsResponse clear(DnsSection section) {
		return (DatagramDnsResponse)super.clear(section);
	}

	public DatagramDnsResponse clear() {
		return (DatagramDnsResponse)super.clear();
	}

	public DatagramDnsResponse touch() {
		return (DatagramDnsResponse)super.touch();
	}

	public DatagramDnsResponse touch(Object hint) {
		return (DatagramDnsResponse)super.touch(hint);
	}

	public DatagramDnsResponse retain() {
		return (DatagramDnsResponse)super.retain();
	}

	public DatagramDnsResponse retain(int increment) {
		return (DatagramDnsResponse)super.retain(increment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!super.equals(obj)) {
			return false;
		} else if (!(obj instanceof AddressedEnvelope)) {
			return false;
		} else {
			AddressedEnvelope<?, SocketAddress> that = (AddressedEnvelope<?, SocketAddress>)obj;
			if (this.sender() == null) {
				if (that.sender() != null) {
					return false;
				}
			} else if (!this.sender().equals(that.sender())) {
				return false;
			}

			if (this.recipient() == null) {
				if (that.recipient() != null) {
					return false;
				}
			} else if (!this.recipient().equals(that.recipient())) {
				return false;
			}

			return true;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		if (this.sender() != null) {
			hashCode = hashCode * 31 + this.sender().hashCode();
		}

		if (this.recipient() != null) {
			hashCode = hashCode * 31 + this.recipient().hashCode();
		}

		return hashCode;
	}
}
