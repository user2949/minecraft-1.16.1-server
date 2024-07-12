package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Closed;
import java.util.function.Function;

interface Grate<S, T, A, B> extends App2<Grate.Mu<A, B>, S, T>, Optic<Closed.Mu, S, T, A, B> {
	static <S, T, A, B> Grate<S, T, A, B> unbox(App2<Grate.Mu<A, B>, S, T> box) {
		return (Grate<S, T, A, B>)box;
	}

	T grate(FunctionType<FunctionType<S, A>, B> functionType);

	default <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Closed.Mu, P> proof) {
		Closed<P, ?> ops = Closed.unbox(proof);
		return input -> ops.dimap(ops.closed(input), s -> f -> f.apply(s), this::grate);
	}

	public static final class Instance<A2, B2> implements Closed<Grate.Mu<A2, B2>, Closed.Mu> {
		@Override
		public <A, B, C, D> FunctionType<App2<Grate.Mu<A2, B2>, A, B>, App2<Grate.Mu<A2, B2>, C, D>> dimap(Function<C, A> g, Function<B, D> h) {
			return input -> Optics.grate(f -> (T)h.apply(Grate.unbox(input).grate(fa -> (B)f.apply(FunctionType.create(fa.compose(g))))));
		}

		@Override
		public <A, B, X> App2<Grate.Mu<A2, B2>, FunctionType<X, A>, FunctionType<X, B>> closed(App2<Grate.Mu<A2, B2>, A, B> input) {
			FunctionType<FunctionType<FunctionType<FunctionType<X, A>, A>, B>, FunctionType<X, B>> func = f1 -> x -> f1.apply(f2 -> f2.apply((X)x));
			return Optics.grate(func).eval(this).apply(Grate.unbox(input));
		}
	}

	public static final class Mu<A, B> implements K2 {
	}
}
