package it.unimi.dsi.fastutil.chars;

import java.io.Serializable;

public abstract class AbstractChar2ByteFunction implements Char2ByteFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected byte defRetValue;

	protected AbstractChar2ByteFunction() {
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
