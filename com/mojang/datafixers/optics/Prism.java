package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.datafixers.util.Either;
import java.util.function.Function;

public interface Prism<S, T, A, B> extends App2<Prism.Mu<A, B>, S, T>, Optic<Cocartesian.Mu, S, T, A, B> {
	static <S, T, A, B> Prism<S, T, A, B> unbox(App2<Prism.Mu<A, B>, S, T> box) {
		return (Prism<S, T, A, B>)box;
	}

	Either<T, A> match(S object);

	T build(B object);

	default <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Cocartesian.Mu, P> proof) {
		Cocartesian<P, ? extends Cocartesian.Mu> cocartesian = Cocartesian.unbox(proof);
		return input -> cocartesian.dimap(cocartesian.right(input), this::match, a -> a.map(Function.identity(), this::build));
	}

	public static final class Instance<A2, B2> implements Cocartesian<Prism.Mu<A2, B2>, Cocartesian.Mu> {
		@Override
		public <A, B, C, D> FunctionType<App2<Prism.Mu<A2, B2>, A, B>, App2<Prism.Mu<A2, B2>, C, D>> dimap(Function<C, A> g, Function<B, D> h) {
			return prismBox -> Optics.prism(
					c -> Prism.<Object, T, A, B>unbox(prismBox).match(g.apply(c)).mapLeft(h), b -> h.apply(Prism.<S, T, A, Object>unbox(prismBox).build(b))
				);
		}

		@Override
		public <A, B, C> App2<Prism.Mu<A2, B2>, Either<A, C>, Either<B, C>> left(App2<Prism.Mu<A2, B2>, A, B> input) {
			Prism<A, B, A2, B2> prism = Prism.unbox(input);
			return Optics.prism(
				either -> either.map(a -> prism.match((A)a).mapLeft(Either::left), c -> Either.left(Either.right(c))), b -> Either.left(prism.build((B2)b))
			);
		}

		@Override
		public <A, B, C> App2<Prism.Mu<A2, B2>, Either<C, A>, Either<C, B>> right(App2<Prism.Mu<A2, B2>, A, B> input) {
			Prism<A, B, A2, B2> prism = Prism.unbox(input);
			return Optics.prism(
				either -> either.map(c -> Either.left(Either.left(c)), a -> prism.match((A)a).mapLeft(Either::right)), b -> Either.right(prism.build((B2)b))
			);
		}
	}

	public static final class Mu<A, B> implements K2 {
	}
}
