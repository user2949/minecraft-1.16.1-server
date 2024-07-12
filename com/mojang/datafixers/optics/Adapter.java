package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import java.util.function.Function;

public interface Adapter<S, T, A, B> extends App2<Adapter.Mu<A, B>, S, T>, Optic<Profunctor.Mu, S, T, A, B> {
	static <S, T, A, B> Adapter<S, T, A, B> unbox(App2<Adapter.Mu<A, B>, S, T> box) {
		return (Adapter<S, T, A, B>)box;
	}

	A from(S object);

	T to(B object);

	default <P extends K2> FunctionType<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Profunctor.Mu, P> proofBox) {
		Profunctor<P, ? extends Profunctor.Mu> proof = Profunctor.unbox(proofBox);
		return a -> proof.dimap(a, this::from, this::to);
	}

	public static final class Instance<A2, B2> implements Profunctor<Adapter.Mu<A2, B2>, Profunctor.Mu> {
		@Override
		public <A, B, C, D> FunctionType<App2<Adapter.Mu<A2, B2>, A, B>, App2<Adapter.Mu<A2, B2>, C, D>> dimap(Function<C, A> g, Function<B, D> h) {
			return a -> Optics.adapter(c -> Adapter.<Object, T, A, B>unbox(a).from(g.apply(c)), b2 -> h.apply(Adapter.<S, T, A, Object>unbox(a).to(b2)));
		}
	}

	public static final class Mu<A, B> implements K2 {
	}
}
