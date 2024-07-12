package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

public interface Lens<S, T, A, B> extends App2<Lens.Mu<A, B>, S, T>, Optic<Cartesian.Mu, S, T, A, B> {
	static <S, T, A, B> Lens<S, T, A, B> unbox(App2<Lens.Mu<A, B>, S, T> box) {
		return (Lens<S, T, A, B>)box;
	}

	static <S, T, A, B> Lens<S, T, A, B> unbox2(App2<Lens.Mu2<S, T>, B, A> box) {
		return ((Lens.Box)box).lens;
	}

	static <S, T, A, B> App2<Lens.Mu2<S, T>, B, A> box(Lens<S, T, A, B> lens) {
		return new Lens.Box<>(lens);
	}

	A view(S object);

	T update(B object1, S object2);

	default <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Cartesian.Mu, P> proofBox) {
		Cartesian<P, ? extends Cartesian.Mu> proof = Cartesian.unbox(proofBox);
		return a -> proof.dimap(proof.first(a), s -> Pair.of(this.view((S)s), s), pair -> this.update((B)pair.getFirst(), (S)pair.getSecond()));
	}

	public static final class Box<S, T, A, B> implements App2<Lens.Mu2<S, T>, B, A> {
		private final Lens<S, T, A, B> lens;

		public Box(Lens<S, T, A, B> lens) {
			this.lens = lens;
		}
	}

	public static final class Instance<A2, B2> implements Cartesian<Lens.Mu<A2, B2>, Cartesian.Mu> {
		@Override
		public <A, B, C, D> FunctionType<App2<Lens.Mu<A2, B2>, A, B>, App2<Lens.Mu<A2, B2>, C, D>> dimap(Function<C, A> g, Function<B, D> h) {
			return l -> Optics.lens(c -> Lens.<Object, T, A, B>unbox(l).view(g.apply(c)), (b2, c) -> h.apply(Lens.<Object, T, A, Object>unbox(l).update(b2, g.apply(c))));
		}

		@Override
		public <A, B, C> App2<Lens.Mu<A2, B2>, Pair<A, C>, Pair<B, C>> first(App2<Lens.Mu<A2, B2>, A, B> input) {
			return Optics.lens(
				pair -> Lens.unbox(input).view((A)pair.getFirst()), (b2, pair) -> Pair.of(Lens.unbox(input).update((B2)b2, (A)pair.getFirst()), pair.getSecond())
			);
		}

		@Override
		public <A, B, C> App2<Lens.Mu<A2, B2>, Pair<C, A>, Pair<C, B>> second(App2<Lens.Mu<A2, B2>, A, B> input) {
			return Optics.lens(
				pair -> Lens.unbox(input).view((A)pair.getSecond()), (b2, pair) -> Pair.of(pair.getFirst(), Lens.unbox(input).update((B2)b2, (A)pair.getSecond()))
			);
		}
	}

	public static final class Mu<A, B> implements K2 {
	}

	public static final class Mu2<S, T> implements K2 {
	}
}
