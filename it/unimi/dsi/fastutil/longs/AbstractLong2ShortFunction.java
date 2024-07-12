package it.unimi.dsi.fastutil.longs;

import java.io.Serializable;

public abstract class AbstractLong2ShortFunction implements Long2ShortFunction, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected short defRetValue;

	protected AbstractLong2ShortFunction() {
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
