package it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;

public abstract class AbstractByte2ReferenceFunction<V> implements Byte2ReferenceFunction<V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected V defRetValue;

	protected AbstractByte2ReferenceFunction() {
	}

	@Override
	public void defaultReturnValue(V rv) {
		this.defRetValue = rv;
	}

	@Override
	public V defaultReturnValue() {
		return this.defRetValue;
	}
}
