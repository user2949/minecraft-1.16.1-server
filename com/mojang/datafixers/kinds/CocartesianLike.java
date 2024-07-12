package com.mojang.datafixers.kinds;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Either.Instance;
import java.util.function.Function;

public interface CocartesianLike<T extends K1, C, Mu extends CocartesianLike.Mu> extends Functor<T, Mu>, Traversable<T, Mu> {
	static <F extends K1, C, Mu extends CocartesianLike.Mu> CocartesianLike<F, C, Mu> unbox(App<Mu, F> proofBox) {
		return (CocartesianLike<F, C, Mu>)proofBox;
	}

	<A> App<Either.Mu<C>, A> to(App<T, A> app);

	<A> App<T, A> from(App<Either.Mu<C>, A> app);

	@Override
	default <F extends K1, A, B> App<F, App<T, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<T, A> input) {
		return applicative.map(this::from, new Instance<C>().traverse(applicative, function, this.to(input)));
	}

	public interface Mu extends Functor.Mu, Traversable.Mu {
	}
}
