package it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;

public abstract class AbstractDouble2IntFunction implements Double2IntFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected int defRetValue;

	protected AbstractDouble2IntFunction() {
	}

	@Override
	public void defaultReturnValue(int rv) {
		this.defRetValue = rv;
	}

	@Override
	public int defaultReturnValue() {
		return this.defRetValue;
	}
}
