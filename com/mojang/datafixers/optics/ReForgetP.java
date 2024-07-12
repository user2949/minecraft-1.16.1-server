package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

interface ReForgetP<R, A, B> extends App2<ReForgetP.Mu<R>, A, B> {
	static <R, A, B> ReForgetP<R, A, B> unbox(App2<ReForgetP.Mu<R>, A, B> box) {
		return (ReForgetP<R, A, B>)box;
	}

	B run(A object1, R object2);

	public static final class Instance<R> implements AffineP<ReForgetP.Mu<R>, ReForgetP.Instance.Mu<R>>, App<ReForgetP.Instance.Mu<R>, ReForgetP.Mu<R>> {
		@Override
		public <A, B, C, D> FunctionType<App2<ReForgetP.Mu<R>, A, B>, App2<ReForgetP.Mu<R>, C, D>> dimap(Function<C, A> g, Function<B, D> h) {
			return input -> (App2<ReForgetP.Mu<R>, C, D>)Optics.reForgetP("dimap", (c, r) -> {
					A a = (A)g.apply(c);
					B b = ReForgetP.<R, A, B>unbox(input).run(a, (R)r);
					return h.apply(b);
				});
		}

		@Override
		public <A, B, C> App2<ReForgetP.Mu<R>, Either<A, C>, Either<B, C>> left(App2<ReForgetP.Mu<R>, A, B> input) {
			return Optics.reForgetP("left", (e, r) -> e.mapLeft(a -> ReForgetP.unbox(input).run((A)a, (R)r)));
		}

		@Override
		public <A, B, C> App2<ReForgetP.Mu<R>, Either<C, A>, Either<C, B>> right(App2<ReForgetP.Mu<R>, A, B> input) {
			return Optics.reForgetP("right", (e, r) -> e.mapRight(a -> ReForgetP.unbox(input).run((A)a, (R)r)));
		}

		@Override
		public <A, B, C> App2<ReForgetP.Mu<R>, Pair<A, C>, Pair<B, C>> first(App2<ReForgetP.Mu<R>, A, B> input) {
			return Optics.reForgetP("first", (p, r) -> Pair.of(ReForgetP.unbox(input).run((A)p.getFirst(), (R)r), p.getSecond()));
		}

		@Override
		public <A, B, C> App2<ReForgetP.Mu<R>, Pair<C, A>, Pair<C, B>> second(App2<ReForgetP.Mu<R>, A, B> input) {
			return Optics.reForgetP("second", (p, r) -> Pair.of(p.getFirst(), ReForgetP.unbox(input).run((A)p.getSecond(), (R)r)));
		}

		static final class Mu<R> implements AffineP.Mu {
		}
	}

	public static final class Mu<R> implements K2 {
	}
}
