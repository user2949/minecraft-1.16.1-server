package com.mojang.datafixers.kinds;

import java.util.function.Function;

public interface Traversable<T extends K1, Mu extends Traversable.Mu> extends Functor<T, Mu> {
	static <F extends K1, Mu extends Traversable.Mu> Traversable<F, Mu> unbox(App<Mu, F> proofBox) {
		return (Traversable<F, Mu>)proofBox;
	}

	<F extends K1, A, B> App<F, App<T, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<T, A> app);

	default <F extends K1, A> App<F, App<T, A>> flip(Applicative<F, ?> applicative, App<T, App<F, A>> input) {
		return this.traverse(applicative, Function.identity(), input);
	}

	public interface Mu extends Functor.Mu {
	}
}
