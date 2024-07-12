package com.mojang.datafixers.functions;

import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class Out<A> extends PointFree<Function<A, A>> {
	private final RecursivePointType<A> type;

	public Out(RecursivePointType<A> type) {
		this.type = type;
	}

	@Override
	public String toString(int level) {
		return "Out[" + this.type + "]";
	}

	public boolean equals(Object obj) {
		return this == obj ? true : obj instanceof Out && Objects.equals(this.type, ((Out)obj).type);
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.type});
	}

	@Override
	public Function<DynamicOps<?>, Function<A, A>> eval() {
		return ops -> Function.identity();
	}
}
