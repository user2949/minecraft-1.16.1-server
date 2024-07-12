package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

interface ReForgetEP<R, A, B> extends App2<ReForgetEP.Mu<R>, A, B> {
	static <R, A, B> ReForgetEP<R, A, B> unbox(App2<ReForgetEP.Mu<R>, A, B> box) {
		return (ReForgetEP<R, A, B>)box;
	}

	B run(Either<A, Pair<A, R>> either);

	public static final class Instance<R> implements AffineP<ReForgetEP.Mu<R>, ReForgetEP.Instance.Mu<R>>, App<ReForgetEP.Instance.Mu<R>, ReForgetEP.Mu<R>> {
		@Override
		public <A, B, C, D> FunctionType<App2<ReForgetEP.Mu<R>, A, B>, App2<ReForgetEP.Mu<R>, C, D>> dimap(Function<C, A> g, Function<B, D> h) {
			return input -> (App2<ReForgetEP.Mu<R>, C, D>)Optics.reForgetEP("dimap", e -> {
					Either<A, Pair<A, R>> either = (Either<A, Pair<A, R>>)e.mapBoth(g, p -> Pair.of(g.apply(p.getFirst()), p.getSecond()));
					B b = ReForgetEP.<R, A, B>unbox(input).run(either);
					return h.apply(b);
				});
		}

		@Override
		public <A, B, C> App2<ReForgetEP.Mu<R>, Either<A, C>, Either<B, C>> left(App2<ReForgetEP.Mu<R>, A, B> input) {
			ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
			return Optics.reForgetEP(
				"left",
				e -> e.map(
						e2 -> e2.mapLeft(a -> reForgetEP.run(Either.left((A)a))),
						p -> ((Either)p.getFirst()).mapLeft(a -> reForgetEP.run(Either.right(Pair.of((A)a, (R)p.getSecond()))))
					)
			);
		}

		@Override
		public <A, B, C> App2<ReForgetEP.Mu<R>, Either<C, A>, Either<C, B>> right(App2<ReForgetEP.Mu<R>, A, B> input) {
			ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
			return Optics.reForgetEP(
				"right",
				e -> e.map(
						e2 -> e2.mapRight(a -> reForgetEP.run(Either.left((A)a))),
						p -> ((Either)p.getFirst()).mapRight(a -> reForgetEP.run(Either.right(Pair.of((A)a, (R)p.getSecond()))))
					)
			);
		}

		@Override
		public <A, B, C> App2<ReForgetEP.Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<ReForgetEP.Mu<R>, A, B> input) {
			ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
			return Optics.reForgetEP(
				"first",
				e -> e.map(
						p -> Pair.of(reForgetEP.run(Either.left((A)p.getFirst())), p.getSecond()),
						p -> Pair.of(reForgetEP.run(Either.right(Pair.of((A)((Pair)p.getFirst()).getFirst(), (R)p.getSecond()))), ((Pair)p.getFirst()).getSecond())
					)
			);
		}

		@Override
		public <A, B, C> App2<ReForgetEP.Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<ReForgetEP.Mu<R>, A, B> input) {
			ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
			return Optics.reForgetEP(
				"second",
				e -> e.map(
						p -> Pair.of(p.getFirst(), reForgetEP.run(Either.left((A)p.getSecond()))),
						p -> Pair.of(((Pair)p.getFirst()).getFirst(), reForgetEP.run(Either.right(Pair.of((A)((Pair)p.getFirst()).getSecond(), (R)p.getSecond()))))
					)
			);
		}

		static final class Mu<R> implements AffineP.Mu {
		}
	}

	public static final class Mu<R> implements K2 {
	}
}
