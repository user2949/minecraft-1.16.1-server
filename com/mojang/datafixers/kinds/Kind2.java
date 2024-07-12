package com.mojang.datafixers.kinds;

public interface Kind2<F extends K2, Mu extends Kind2.Mu> extends App<Mu, F> {
	static <F extends K2, Proof extends Kind2.Mu> Kind2<F, Proof> unbox(App<Proof, F> proofBox) {
		return (Kind2<F, Proof>)proofBox;
	}

	public interface Mu extends K1 {
	}
}
