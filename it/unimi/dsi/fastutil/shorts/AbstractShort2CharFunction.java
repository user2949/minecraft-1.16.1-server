package it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;

public abstract class AbstractShort2CharFunction implements Short2CharFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected char defRetValue;

	protected AbstractShort2CharFunction() {
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
