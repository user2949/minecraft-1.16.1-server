package it.unimi.dsi.fastutil.floats;

import java.io.Serializable;

public abstract class AbstractFloat2CharFunction implements Float2CharFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected char defRetValue;

	protected AbstractFloat2CharFunction() {
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
