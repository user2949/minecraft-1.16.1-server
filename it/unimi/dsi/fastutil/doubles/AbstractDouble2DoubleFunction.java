package it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;

public abstract class AbstractDouble2DoubleFunction implements Double2DoubleFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected double defRetValue;

	protected AbstractDouble2DoubleFunction() {
	}

	@Override
	public void defaultReturnValue(double rv) {
		this.defRetValue = rv;
	}

	@Override
	public double defaultReturnValue() {
		return this.defRetValue;
	}
}
