package io.netty.channel.sctp;

import com.sun.nio.sctp.MessageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

public final class SctpMessage extends DefaultByteBufHolder {
	private final int streamIdentifier;
	private final int protocolIdentifier;
	private final boolean unordered;
	private final MessageInfo msgInfo;

	public SctpMessage(int protocolIdentifier, int streamIdentifier, ByteBuf payloadBuffer) {
		this(protocolIdentifier, streamIdentifier, false, payloadBuffer);
	}

	public SctpMessage(int protocolIdentifier, int streamIdentifier, boolean unordered, ByteBuf payloadBuffer) {
		super(payloadBuffer);
		this.protocolIdentifier = protocolIdentifier;
		this.streamIdentifier = streamIdentifier;
		this.unordered = unordered;
		this.msgInfo = null;
	}

	public SctpMessage(MessageInfo msgInfo, ByteBuf payloadBuffer) {
		super(payloadBuffer);
		if (msgInfo == null) {
			throw new NullPointerException("msgInfo");
		} else {
			this.msgInfo = msgInfo;
			this.streamIdentifier = msgInfo.streamNumber();
			this.protocolIdentifier = msgInfo.payloadProtocolID();
			this.unordered = msgInfo.isUnordered();
		}
	}

	public int streamIdentifier() {
		return this.streamIdentifier;
	}

	public int protocolIdentifier() {
		return this.protocolIdentifier;
	}

	public boolean isUnordered() {
		return this.unordered;
	}

	public MessageInfo messageInfo() {
		return this.msgInfo;
	}

	public boolean isComplete() {
		return this.msgInfo != null ? this.msgInfo.isComplete() : true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			SctpMessage sctpFrame = (SctpMessage)o;
			if (this.protocolIdentifier != sctpFrame.protocolIdentifier) {
				return false;
			} else if (this.streamIdentifier != sctpFrame.streamIdentifier) {
				return false;
			} else {
				return this.unordered != sctpFrame.unordered ? false : this.content().equals(sctpFrame.content());
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = this.streamIdentifier;
		result = 31 * result + this.protocolIdentifier;
		result = 31 * result + (this.unordered ? 1231 : 1237);
		return 31 * result + this.content().hashCode();
	}

	public SctpMessage copy() {
		return (SctpMessage)super.copy();
	}

	public SctpMessage duplicate() {
		return (SctpMessage)super.duplicate();
	}

	public SctpMessage retainedDuplicate() {
		return (SctpMessage)super.retainedDuplicate();
	}

	public SctpMessage replace(ByteBuf content) {
		return this.msgInfo == null
			? new SctpMessage(this.protocolIdentifier, this.streamIdentifier, this.unordered, content)
			: new SctpMessage(this.msgInfo, content);
	}

	public SctpMessage retain() {
		super.retain();
		return this;
	}

	public SctpMessage retain(int increment) {
		super.retain(increment);
		return this;
	}

	public SctpMessage touch() {
		super.touch();
		return this;
	}

	public SctpMessage touch(Object hint) {
		super.touch(hint);
		return this;
	}

	@Override
	public String toString() {
		return "SctpFrame{streamIdentifier="
			+ this.streamIdentifier
			+ ", protocolIdentifier="
			+ this.protocolIdentifier
			+ ", unordered="
			+ this.unordered
			+ ", data="
			+ this.contentToString()
			+ '}';
	}
}
