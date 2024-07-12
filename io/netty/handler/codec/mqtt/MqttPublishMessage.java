package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.IllegalReferenceCountException;

public class MqttPublishMessage extends MqttMessage implements ByteBufHolder {
	public MqttPublishMessage(MqttFixedHeader mqttFixedHeader, MqttPublishVariableHeader variableHeader, ByteBuf payload) {
		super(mqttFixedHeader, variableHeader, payload);
	}

	public MqttPublishVariableHeader variableHeader() {
		return (MqttPublishVariableHeader)super.variableHeader();
	}

	public ByteBuf payload() {
		return this.content();
	}

	@Override
	public ByteBuf content() {
		ByteBuf data = (ByteBuf)super.payload();
		if (data.refCnt() <= 0) {
			throw new IllegalReferenceCountException(data.refCnt());
		} else {
			return data;
		}
	}

	public MqttPublishMessage copy() {
		return this.replace(this.content().copy());
	}

	public MqttPublishMessage duplicate() {
		return this.replace(this.content().duplicate());
	}

	public MqttPublishMessage retainedDuplicate() {
		return this.replace(this.content().retainedDuplicate());
	}

	public MqttPublishMessage replace(ByteBuf content) {
		return new MqttPublishMessage(this.fixedHeader(), this.variableHeader(), content);
	}

	@Override
	public int refCnt() {
		return this.content().refCnt();
	}

	public MqttPublishMessage retain() {
		this.content().retain();
		return this;
	}

	public MqttPublishMessage retain(int increment) {
		this.content().retain(increment);
		return this;
	}

	public MqttPublishMessage touch() {
		this.content().touch();
		return this;
	}

	public MqttPublishMessage touch(Object hint) {
		this.content().touch(hint);
		return this;
	}

	@Override
	public boolean release() {
		return this.content().release();
	}

	@Override
	public boolean release(int decrement) {
		return this.content().release(decrement);
	}
}
