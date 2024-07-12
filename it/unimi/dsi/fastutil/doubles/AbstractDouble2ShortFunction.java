package it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;

public abstract class AbstractDouble2ShortFunction implements Double2ShortFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected short defRetValue;

	protected AbstractDouble2ShortFunction() {
	}

	@Override
	public void defaultReturnValue(short rv) {
		this.defRetValue = rv;
	}

	@Override
	public short defaultReturnValue() {
		return this.defRetValue;
	}
}
