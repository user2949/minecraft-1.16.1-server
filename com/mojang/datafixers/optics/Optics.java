package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Adapter.Instance;
import com.mojang.datafixers.optics.profunctors.Profunctor.Mu;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Optics {
	public static <S, T, A, B> Adapter<S, T, A, B> toAdapter(Optic<? super Mu, S, T, A, B> optic) {
		Function<App2<com.mojang.datafixers.optics.Adapter.Mu<A, B>, A, B>, App2<com.mojang.datafixers.optics.Adapter.Mu<A, B>, S, T>> eval = optic.eval(
			new Instance<>()
		);
		return Adapter.unbox((App2<com.mojang.datafixers.optics.Adapter.Mu<A, B>, S, T>)eval.apply(adapter(Function.identity(), Function.identity())));
	}

	public static <S, T, A, B> Lens<S, T, A, B> toLens(Optic<? super com.mojang.datafixers.optics.profunctors.Cartesian.Mu, S, T, A, B> optic) {
		Function<App2<com.mojang.datafixers.optics.Lens.Mu<A, B>, A, B>, App2<com.mojang.datafixers.optics.Lens.Mu<A, B>, S, T>> eval = optic.eval(
			new com.mojang.datafixers.optics.Lens.Instance<>()
		);
		return Lens.unbox((App2<com.mojang.datafixers.optics.Lens.Mu<A, B>, S, T>)eval.apply(lens(Function.identity(), (b, a) -> b)));
	}

	public static <S, T, A, B> Prism<S, T, A, B> toPrism(Optic<? super com.mojang.datafixers.optics.profunctors.Cocartesian.Mu, S, T, A, B> optic) {
		Function<App2<com.mojang.datafixers.optics.Prism.Mu<A, B>, A, B>, App2<com.mojang.datafixers.optics.Prism.Mu<A, B>, S, T>> eval = optic.eval(
			new com.mojang.datafixers.optics.Prism.Instance<>()
		);
		return Prism.unbox((App2<com.mojang.datafixers.optics.Prism.Mu<A, B>, S, T>)eval.apply(prism(Either::right, Function.identity())));
	}

	public static <S, T, A, B> Affine<S, T, A, B> toAffine(Optic<? super com.mojang.datafixers.optics.profunctors.AffineP.Mu, S, T, A, B> optic) {
		Function<App2<com.mojang.datafixers.optics.Affine.Mu<A, B>, A, B>, App2<com.mojang.datafixers.optics.Affine.Mu<A, B>, S, T>> eval = optic.eval(
			new com.mojang.datafixers.optics.Affine.Instance<>()
		);
		return Affine.unbox((App2<com.mojang.datafixers.optics.Affine.Mu<A, B>, S, T>)eval.apply(affine(Either::right, (b, a) -> b)));
	}

	public static <S, T, A, B> Getter<S, T, A, B> toGetter(Optic<? super com.mojang.datafixers.optics.profunctors.GetterP.Mu, S, T, A, B> optic) {
		Function<App2<com.mojang.datafixers.optics.Getter.Mu<A, B>, A, B>, App2<com.mojang.datafixers.optics.Getter.Mu<A, B>, S, T>> eval = optic.eval(
			new com.mojang.datafixers.optics.Getter.Instance<>()
		);
		return Getter.unbox((App2<com.mojang.datafixers.optics.Getter.Mu<A, B>, S, T>)eval.apply(getter(Function.identity())));
	}

	public static <S, T, A, B> Traversal<S, T, A, B> toTraversal(Optic<? super com.mojang.datafixers.optics.profunctors.TraversalP.Mu, S, T, A, B> optic) {
		Function<App2<com.mojang.datafixers.optics.Traversal.Mu<A, B>, A, B>, App2<com.mojang.datafixers.optics.Traversal.Mu<A, B>, S, T>> eval = optic.eval(
			new com.mojang.datafixers.optics.Traversal.Instance<>()
		);
		return Traversal.unbox((App2<com.mojang.datafixers.optics.Traversal.Mu<A, B>, S, T>)eval.apply(new Traversal<A, B, A, B>() {
			@Override
			public <F extends K1> FunctionType<A, App<F, B>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> input) {
				return input;
			}
		}));
	}

	static <S, T, A, B, F> Lens<S, T, Pair<F, A>, B> merge(Lens<S, ?, F, ?> getter, Lens<S, T, A, B> lens) {
		return lens(s -> Pair.of(getter.view((S)s), lens.view((S)s)), lens::update);
	}

	public static <S, T> Adapter<S, T, S, T> id() {
		return new IdAdapter<>();
	}

	public static <S, T, A, B> Adapter<S, T, A, B> adapter(Function<S, A> from, Function<B, T> to) {
		return new Adapter<S, T, A, B>() {
			@Override
			public A from(S s) {
				return (A)from.apply(s);
			}

			@Override
			public T to(B b) {
				return (T)to.apply(b);
			}
		};
	}

	public static <S, T, A, B> Lens<S, T, A, B> lens(Function<S, A> view, BiFunction<B, S, T> update) {
		return new Lens<S, T, A, B>() {
			@Override
			public A view(S s) {
				return (A)view.apply(s);
			}

			@Override
			public T update(B b, S s) {
				return (T)update.apply(b, s);
			}
		};
	}

	public static <S, T, A, B> Prism<S, T, A, B> prism(Function<S, Either<T, A>> match, Function<B, T> build) {
		return new Prism<S, T, A, B>() {
			@Override
			public Either<T, A> match(S s) {
				return (Either<T, A>)match.apply(s);
			}

			@Override
			public T build(B b) {
				return (T)build.apply(b);
			}
		};
	}

	public static <S, T, A, B> Affine<S, T, A, B> affine(Function<S, Either<T, A>> preview, BiFunction<B, S, T> build) {
		return new Affine<S, T, A, B>() {
			@Override
			public Either<T, A> preview(S s) {
				return (Either<T, A>)preview.apply(s);
			}

			@Override
			public T set(B b, S s) {
				return (T)build.apply(b, s);
			}
		};
	}

	public static <S, T, A, B> Getter<S, T, A, B> getter(Function<S, A> get) {
		return get::apply;
	}

	public static <R, A, B> Forget<R, A, B> forget(Function<A, R> function) {
		return function::apply;
	}

	public static <R, A, B> ForgetOpt<R, A, B> forgetOpt(Function<A, Optional<R>> function) {
		return function::apply;
	}

	public static <R, A, B> ForgetE<R, A, B> forgetE(Function<A, Either<B, R>> function) {
		return function::apply;
	}

	public static <R, A, B> ReForget<R, A, B> reForget(Function<R, B> function) {
		return function::apply;
	}

	public static <S, T, A, B> Grate<S, T, A, B> grate(FunctionType<FunctionType<FunctionType<S, A>, B>, T> grate) {
		return grate::apply;
	}

	public static <R, A, B> ReForgetEP<R, A, B> reForgetEP(String name, Function<Either<A, Pair<A, R>>, B> function) {
		return new ReForgetEP<R, A, B>() {
			@Override
			public B run(Either<A, Pair<A, R>> e) {
				return (B)function.apply(e);
			}

			public String toString() {
				return "ReForgetEP_" + name;
			}
		};
	}

	public static <R, A, B> ReForgetE<R, A, B> reForgetE(String name, Function<Either<A, R>, B> function) {
		return new ReForgetE<R, A, B>() {
			@Override
			public B run(Either<A, R> t) {
				return (B)function.apply(t);
			}

			public String toString() {
				return "ReForgetE_" + name;
			}
		};
	}

	public static <R, A, B> ReForgetP<R, A, B> reForgetP(String name, BiFunction<A, R, B> function) {
		return new ReForgetP<R, A, B>() {
			@Override
			public B run(A a, R r) {
				return (B)function.apply(a, r);
			}

			public String toString() {
				return "ReForgetP_" + name;
			}
		};
	}

	public static <R, A, B> ReForgetC<R, A, B> reForgetC(String name, Either<Function<R, B>, BiFunction<A, R, B>> either) {
		return new ReForgetC<R, A, B>() {
			@Override
			public Either<Function<R, B>, BiFunction<A, R, B>> impl() {
				return either;
			}

			public String toString() {
				return "ReForgetC_" + name;
			}
		};
	}

	public static <I, J, X> PStore<I, J, X> pStore(Function<J, X> peek, Supplier<I> pos) {
		return new PStore<I, J, X>() {
			@Override
			public X peek(J j) {
				return (X)peek.apply(j);
			}

			@Override
			public I pos() {
				return (I)pos.get();
			}
		};
	}

	public static <A, B> Function<A, B> getFunc(App2<com.mojang.datafixers.FunctionType.Mu, A, B> box) {
		return FunctionType.unbox(box);
	}

	public static <F, G, F2> Proj1<F, G, F2> proj1() {
		return new Proj1<>();
	}

	public static <F, G, G2> Proj2<F, G, G2> proj2() {
		return new Proj2<>();
	}

	public static <F, G, F2> Inj1<F, G, F2> inj1() {
		return new Inj1<>();
	}

	public static <F, G, G2> Inj2<F, G, G2> inj2() {
		return new Inj2<>();
	}

	public static <F, G, F2, G2, A, B> Lens<Either<F, G>, Either<F2, G2>, A, B> eitherLens(Lens<F, F2, A, B> fLens, Lens<G, G2, A, B> gLens) {
		return lens(either -> either.map(fLens::view, gLens::view), (b, either) -> either.mapBoth(f -> fLens.update((B)b, (F)f), g -> gLens.update((B)b, (G)g)));
	}

	public static <F, G, F2, G2, A, B> Affine<Either<F, G>, Either<F2, G2>, A, B> eitherAffine(Affine<F, F2, A, B> fAffine, Affine<G, G2, A, B> gAffine) {
		return affine(
			either -> either.map(f -> fAffine.preview((F)f).mapLeft(Either::left), g -> gAffine.preview((G)g).mapLeft(Either::right)),
			(b, either) -> either.mapBoth(f -> fAffine.set((B)b, (F)f), g -> gAffine.set((B)b, (G)g))
		);
	}

	public static <F, G, F2, G2, A, B> Traversal<Either<F, G>, Either<F2, G2>, A, B> eitherTraversal(Traversal<F, F2, A, B> fOptic, Traversal<G, G2, A, B> gOptic) {
		return new Traversal<Either<F, G>, Either<F2, G2>, A, B>() {
			@Override
			public <FT extends K1> FunctionType<Either<F, G>, App<FT, Either<F2, G2>>> wander(Applicative<FT, ?> applicative, FunctionType<A, App<FT, B>> input) {
				return e -> e.map(
						l -> applicative.ap(Either::left, fOptic.wander(applicative, input).apply((F)l)),
						r -> applicative.ap(Either::right, gOptic.wander(applicative, input).apply((G)r))
					);
			}
		};
	}
}
