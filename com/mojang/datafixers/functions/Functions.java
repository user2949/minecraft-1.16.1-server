package com.mojang.datafixers.functions;

import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.FunctionType.Instance.Mu;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.Algebra;
import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;

public abstract class Functions {
	private static final Id<?> ID = new Id();

	public static <A, B, C> PointFree<Function<A, C>> comp(Type<B> middleType, PointFree<Function<B, C>> f1, PointFree<Function<A, B>> f2) {
		if (Objects.equals(f1, id())) {
			return (PointFree<Function<A, C>>)f2;
		} else {
			return (PointFree<Function<A, C>>)(Objects.equals(f2, id()) ? f1 : new Comp<>(middleType, f1, f2));
		}
	}

	public static <A, B> PointFree<Function<A, B>> fun(String name, Function<DynamicOps<?>, Function<A, B>> fun) {
		return new FunctionWrapper<>(name, fun);
	}

	public static <A, B> PointFree<B> app(PointFree<Function<A, B>> fun, PointFree<A> arg, Type<A> argType) {
		return new Apply<>(fun, arg, argType);
	}

	public static <S, T, A, B> PointFree<Function<Function<A, B>, Function<S, T>>> profunctorTransformer(Optic<? super Mu, S, T, A, B> lens) {
		return new ProfunctorTransformer<>(lens);
	}

	public static <A> Bang<A> bang() {
		return new Bang<>();
	}

	public static <A> PointFree<Function<A, A>> in(RecursivePointType<A> type) {
		return new In<>(type);
	}

	public static <A> PointFree<Function<A, A>> out(RecursivePointType<A> type) {
		return new Out<>(type);
	}

	public static <A, B> PointFree<Function<A, B>> fold(RecursivePointType<A> aType, RewriteResult<?, B> function, Algebra algebra, int index) {
		return new Fold<>(aType, function, algebra, index);
	}

	public static <A> PointFree<Function<A, A>> id() {
		return (PointFree<Function<A, A>>)ID;
	}
}
