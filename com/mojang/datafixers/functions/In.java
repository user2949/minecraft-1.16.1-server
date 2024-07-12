package com.mojang.datafixers.functions;

import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class In<A> extends PointFree<Function<A, A>> {
	protected final RecursivePointType<A> type;

	public In(RecursivePointType<A> type) {
		this.type = type;
	}

	@Override
	public String toString(int level) {
		return "In[" + this.type + "]";
	}

	public boolean equals(Object obj) {
		return this == obj ? true : obj instanceof In && Objects.equals(this.type, ((In)obj).type);
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.type});
	}

	@Override
	public Function<DynamicOps<?>, Function<A, A>> eval() {
		return ops -> Function.identity();
	}
}
