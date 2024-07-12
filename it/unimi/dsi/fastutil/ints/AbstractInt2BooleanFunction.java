package it.unimi.dsi.fastutil.ints;

import java.io.Serializable;

public abstract class AbstractInt2BooleanFunction implements Int2BooleanFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected boolean defRetValue;

	protected AbstractInt2BooleanFunction() {
	}

	@Override
	public void defaultReturnValue(boolean rv) {
		this.defRetValue = rv;
	}

	@Override
	public boolean defaultReturnValue() {
		return this.defRetValue;
	}
}
