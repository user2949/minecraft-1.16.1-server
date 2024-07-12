package it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;

public abstract class AbstractByte2ByteFunction implements Byte2ByteFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected byte defRetValue;

	protected AbstractByte2ByteFunction() {
	}

	@Override
	public void defaultReturnValue(byte rv) {
		this.defRetValue = rv;
	}

	@Override
	public byte defaultReturnValue() {
		return this.defRetValue;
	}
}
