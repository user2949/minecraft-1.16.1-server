package com.mojang.datafixers.optics;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface Optic<Proof extends K1, S, T, A, B> {
	<P extends K2> Function<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Proof, P> app);

	default <Proof2 extends Proof, A1, B1> Optic<Proof2, S, T, A1, B1> compose(Optic<? super Proof2, A, B, A1, B1> optic) {
		return new Optic.CompositionOptic<>(this, optic);
	}

	default <Proof2 extends K1, A1, B1> Optic<?, S, T, A1, B1> composeUnchecked(Optic<?, A, B, A1, B1> optic) {
		return new Optic.CompositionOptic<>(this, (Optic<? super Proof, A, B, A1, B1>)optic);
	}

	default <Proof2 extends K1> Optional<Optic<? super Proof2, S, T, A, B>> upCast(Set<TypeToken<? extends K1>> proofBounds, TypeToken<Proof2> proof) {
		return proofBounds.stream().allMatch(bound -> bound.isSupertypeOf(proof)) ? Optional.of(this) : Optional.empty();
	}

	public static final class CompositionOptic<Proof extends K1, S, T, A, B, A1, B1> implements Optic<Proof, S, T, A1, B1> {
		protected final Optic<? super Proof, S, T, A, B> outer;
		protected final Optic<? super Proof, A, B, A1, B1> inner;

		public CompositionOptic(Optic<? super Proof, S, T, A, B> outer, Optic<? super Proof, A, B, A1, B1> inner) {
			this.outer = outer;
			this.inner = inner;
		}

		@Override
		public <P extends K2> Function<App2<P, A1, B1>, App2<P, S, T>> eval(App<? extends Proof, P> proof) {
			return this.outer.eval(proof).compose(this.inner.eval(proof));
		}

		public String toString() {
			return "(" + this.outer + " ◦ " + this.inner + ")";
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				Optic.CompositionOptic<?, ?, ?, ?, ?, ?, ?> that = (Optic.CompositionOptic<?, ?, ?, ?, ?, ?, ?>)o;
				return Objects.equals(this.outer, that.outer) && Objects.equals(this.inner, that.inner);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.outer, this.inner});
		}

		public Optic<? super Proof, S, T, A, B> outer() {
			return this.outer;
		}

		public Optic<? super Proof, A, B, A1, B1> inner() {
			return this.inner;
		}
	}
}
