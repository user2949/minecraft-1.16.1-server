package com.mojang.datafixers.optics;

import com.mojang.datafixers.util.Either;

public final class Inj1<F, G, F2> implements Prism<Either<F, G>, Either<F2, G>, F, F2> {
	public Either<Either<F2, G>, F> match(Either<F, G> either) {
		return either.map(Either::right, g -> Either.left(Either.right(g)));
	}

	public Either<F2, G> build(F2 f2) {
		return Either.left(f2);
	}

	public String toString() {
		return "inj1";
	}

	public boolean equals(Object obj) {
		return obj instanceof Inj1;
	}
}
