package com.mojang.datafixers.functions;

import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class FunctionWrapper<A, B> extends PointFree<Function<A, B>> {
	private final String name;
	protected final Function<DynamicOps<?>, Function<A, B>> fun;

	FunctionWrapper(String name, Function<DynamicOps<?>, Function<A, B>> fun) {
		this.name = name;
		this.fun = fun;
	}

	@Override
	public String toString(int level) {
		return "fun[" + this.name + "]";
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			FunctionWrapper<?, ?> that = (FunctionWrapper<?, ?>)o;
			return Objects.equals(this.fun, that.fun);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.fun});
	}

	@Override
	public Function<DynamicOps<?>, Function<A, B>> eval() {
		return this.fun;
	}
}
