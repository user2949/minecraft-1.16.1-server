package com.mojang.datafixers.kinds;

import com.mojang.datafixers.FunctionType.ReaderMu;

public interface Representable<T extends K1, C, Mu extends Representable.Mu> extends Functor<T, Mu> {
	static <F extends K1, C, Mu extends Representable.Mu> Representable<F, C, Mu> unbox(App<Mu, F> proofBox) {
		return (Representable<F, C, Mu>)proofBox;
	}

	<A> App<ReaderMu<C>, A> to(App<T, A> app);

	<A> App<T, A> from(App<ReaderMu<C>, A> app);

	public interface Mu extends Functor.Mu {
	}
}
