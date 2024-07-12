package com.mojang.datafixers.functions;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.FunctionType.Instance;
import com.mojang.datafixers.FunctionType.Instance.Mu;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

final class ProfunctorTransformer<S, T, A, B> extends PointFree<Function<Function<A, B>, Function<S, T>>> {
	protected final Optic<? super Mu, S, T, A, B> optic;
	protected final Function<App2<com.mojang.datafixers.FunctionType.Mu, A, B>, App2<com.mojang.datafixers.FunctionType.Mu, S, T>> func;
	private final Function<Function<A, B>, Function<S, T>> unwrappedFunction;

	public ProfunctorTransformer(Optic<? super Mu, S, T, A, B> optic) {
		this.optic = optic;
		this.func = optic.eval(Instance.INSTANCE);
		this.unwrappedFunction = input -> FunctionType.unbox((App2<com.mojang.datafixers.FunctionType.Mu, A, B>)this.func.apply(FunctionType.create(input)));
	}

	@Override
	public String toString(int level) {
		return "Optic[" + this.optic + "]";
	}

	@Override
	public Function<DynamicOps<?>, Function<Function<A, B>, Function<S, T>>> eval() {
		return ops -> this.unwrappedFunction;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			ProfunctorTransformer<?, ?, ?, ?> that = (ProfunctorTransformer<?, ?, ?, ?>)o;
			return Objects.equals(this.optic, that.optic);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.optic});
	}
}
