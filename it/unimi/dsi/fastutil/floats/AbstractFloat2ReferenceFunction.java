package it.unimi.dsi.fastutil.floats;

import java.io.Serializable;

public abstract class AbstractFloat2ReferenceFunction<V> implements Float2ReferenceFunction<V>, Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected V defRetValue;

	protected AbstractFloat2ReferenceFunction() {
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
