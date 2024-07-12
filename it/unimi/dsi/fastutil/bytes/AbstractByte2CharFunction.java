package it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;

public abstract class AbstractByte2CharFunction implements Byte2CharFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected char defRetValue;

	protected AbstractByte2CharFunction() {
	}

	@Override
	public void defaultReturnValue(char rv) {
		this.defRetValue = rv;
	}

	@Override
	public char defaultReturnValue() {
		return this.defRetValue;
	}
}
