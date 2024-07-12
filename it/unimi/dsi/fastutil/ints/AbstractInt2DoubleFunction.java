package it.unimi.dsi.fastutil.ints;

import java.io.Serializable;

public abstract class AbstractInt2DoubleFunction implements Int2DoubleFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected double defRetValue;

	protected AbstractInt2DoubleFunction() {
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
