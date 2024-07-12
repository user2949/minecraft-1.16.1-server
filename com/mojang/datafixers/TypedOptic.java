package com.mojang.datafixers;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Inj1;
import com.mojang.datafixers.optics.Inj2;
import com.mojang.datafixers.optics.InjTagged;
import com.mojang.datafixers.optics.ListTraversal;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Proj1;
import com.mojang.datafixers.optics.Proj2;
import com.mojang.datafixers.optics.profunctors.Profunctor.Mu;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class TypedOptic<S, T, A, B> {
	protected final Set<TypeToken<? extends K1>> proofBounds;
	protected final Type<S> sType;
	protected final Type<T> tType;
	protected final Type<A> aType;
	protected final Type<B> bType;
	private final Optic<?, S, T, A, B> optic;

	public TypedOptic(TypeToken<? extends K1> proofBound, Type<S> sType, Type<T> tType, Type<A> aType, Type<B> bType, Optic<?, S, T, A, B> optic) {
		this(ImmutableSet.of(proofBound), sType, tType, aType, bType, optic);
	}

	public TypedOptic(Set<TypeToken<? extends K1>> proofBounds, Type<S> sType, Type<T> tType, Type<A> aType, Type<B> bType, Optic<?, S, T, A, B> optic) {
		this.proofBounds = proofBounds;
		this.sType = sType;
		this.tType = tType;
		this.aType = aType;
		this.bType = bType;
		this.optic = optic;
	}

	public <P extends K2, Proof2 extends K1> App2<P, S, T> apply(TypeToken<Proof2> token, App<Proof2, P> proof, App2<P, A, B> argument) {
		return (App2<P, S, T>)((Optic)this.upCast(token).orElseThrow(() -> new IllegalArgumentException("Couldn't upcast"))).eval(proof).apply(argument);
	}

	public Optic<?, S, T, A, B> optic() {
		return this.optic;
	}

	public Set<TypeToken<? extends K1>> bounds() {
		return this.proofBounds;
	}

	public Type<S> sType() {
		return this.sType;
	}

	public Type<T> tType() {
		return this.tType;
	}

	public Type<A> aType() {
		return this.aType;
	}

	public Type<B> bType() {
		return this.bType;
	}

	public <A1, B1> TypedOptic<S, T, A1, B1> compose(TypedOptic<A, B, A1, B1> other) {
		Builder<TypeToken<? extends K1>> builder = ImmutableSet.builder();
		builder.addAll(this.proofBounds);
		builder.addAll(other.proofBounds);
		return new TypedOptic<>(builder.build(), this.sType, this.tType, other.aType, other.bType, this.optic().composeUnchecked(other.optic()));
	}

	public <Proof2 extends K1> Optional<Optic<? super Proof2, S, T, A, B>> upCast(TypeToken<Proof2> proof) {
		return instanceOf(this.proofBounds, proof) ? Optional.of(this.optic) : Optional.empty();
	}

	public static <Proof2 extends K1> boolean instanceOf(Collection<TypeToken<? extends K1>> bounds, TypeToken<Proof2> proof) {
		return bounds.stream().allMatch(bound -> bound.isSupertypeOf(proof));
	}

	public static <S, T> TypedOptic<S, T, S, T> adapter(Type<S> sType, Type<T> tType) {
		return new TypedOptic<>(Mu.TYPE_TOKEN, sType, tType, sType, tType, Optics.id());
	}

	public static <F, G, F2> TypedOptic<Pair<F, G>, Pair<F2, G>, F, F2> proj1(Type<F> fType, Type<G> gType, Type<F2> newType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.Cartesian.Mu.TYPE_TOKEN, DSL.and(fType, gType), DSL.and(newType, gType), fType, newType, new Proj1<>()
		);
	}

	public static <F, G, G2> TypedOptic<Pair<F, G>, Pair<F, G2>, G, G2> proj2(Type<F> fType, Type<G> gType, Type<G2> newType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.Cartesian.Mu.TYPE_TOKEN, DSL.and(fType, gType), DSL.and(fType, newType), gType, newType, new Proj2<>()
		);
	}

	public static <F, G, F2> TypedOptic<Either<F, G>, Either<F2, G>, F, F2> inj1(Type<F> fType, Type<G> gType, Type<F2> newType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.Cocartesian.Mu.TYPE_TOKEN, DSL.or(fType, gType), DSL.or(newType, gType), fType, newType, new Inj1<>()
		);
	}

	public static <F, G, G2> TypedOptic<Either<F, G>, Either<F, G2>, G, G2> inj2(Type<F> fType, Type<G> gType, Type<G2> newType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.Cocartesian.Mu.TYPE_TOKEN, DSL.or(fType, gType), DSL.or(fType, newType), gType, newType, new Inj2<>()
		);
	}

	public static <K, V, K2> TypedOptic<List<Pair<K, V>>, List<Pair<K2, V>>, K, K2> compoundListKeys(Type<K> aType, Type<K2> bType, Type<V> valueType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.TraversalP.Mu.TYPE_TOKEN,
			DSL.compoundList(aType, valueType),
			DSL.compoundList(bType, valueType),
			aType,
			bType,
			new ListTraversal<Pair<F, G>, Pair<F2, G>>().compose(Optics.proj1())
		);
	}

	public static <K, V, V2> TypedOptic<List<Pair<K, V>>, List<Pair<K, V2>>, V, V2> compoundListElements(Type<K> keyType, Type<V> aType, Type<V2> bType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.TraversalP.Mu.TYPE_TOKEN,
			DSL.compoundList(keyType, aType),
			DSL.compoundList(keyType, bType),
			aType,
			bType,
			new ListTraversal<Pair<F, G>, Pair<F, G2>>().compose(Optics.proj2())
		);
	}

	public static <A, B> TypedOptic<List<A>, List<B>, A, B> list(Type<A> aType, Type<B> bType) {
		return new TypedOptic<>(
			com.mojang.datafixers.optics.profunctors.TraversalP.Mu.TYPE_TOKEN, DSL.list(aType), DSL.list(bType), aType, bType, new ListTraversal<>()
		);
	}

	public static <K, A, B> TypedOptic<Pair<K, ?>, Pair<K, ?>, A, B> tagged(TaggedChoiceType<K> sType, K key, Type<A> aType, Type<B> bType) {
		if (!Objects.equals(sType.types().get(key), aType)) {
			throw new IllegalArgumentException("Focused type doesn't match.");
		} else {
			Map<K, Type<?>> newTypes = Maps.<K, Type<?>>newHashMap(sType.types());
			newTypes.put(key, bType);
			Type<Pair<K, ?>> pairType = DSL.taggedChoiceType(sType.getName(), sType.getKeyType(), newTypes);
			return new TypedOptic<>(com.mojang.datafixers.optics.profunctors.Cocartesian.Mu.TYPE_TOKEN, sType, pairType, aType, bType, new InjTagged<>(key));
		}
	}
}
