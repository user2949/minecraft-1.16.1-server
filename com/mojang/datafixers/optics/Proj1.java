package com.mojang.datafixers.optics;

import com.mojang.datafixers.util.Pair;

public final class Proj1<F, G, F2> implements Lens<Pair<F, G>, Pair<F2, G>, F, F2> {
	public F view(Pair<F, G> pair) {
		return pair.getFirst();
	}

	public Pair<F2, G> update(F2 newValue, Pair<F, G> pair) {
		return Pair.of(newValue, pair.getSecond());
	}

	public String toString() {
		return "π1";
	}

	public boolean equals(Object obj) {
		return obj instanceof Proj1;
	}
}
