package com.mojang.datafixers.types.templates;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Affine;
import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.Type.TypeMatcher;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class TaggedChoice<K> implements TypeTemplate {
	private final String name;
	private final Type<K> keyType;
	private final Map<K, TypeTemplate> templates;
	private final Map<Pair<TypeFamily, Integer>, Type<?>> types = Maps.<Pair<TypeFamily, Integer>, Type<?>>newConcurrentMap();
	private final int size;

	public TaggedChoice(String name, Type<K> keyType, Map<K, TypeTemplate> templates) {
		this.name = name;
		this.keyType = keyType;
		this.templates = templates;
		this.size = templates.values().stream().mapToInt(TypeTemplate::size).max().orElse(0);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public TypeFamily apply(TypeFamily family) {
		return index -> (Type<?>)this.types
				.computeIfAbsent(
					Pair.of(family, index),
					key -> DSL.taggedChoiceType(
							this.name,
							this.keyType,
							(Map<K, ? extends Type<?>>)this.templates
								.entrySet()
								.stream()
								.map(e -> Pair.of(e.getKey(), ((TypeTemplate)e.getValue()).apply((TypeFamily)key.getFirst()).apply((Integer)key.getSecond())))
								.collect(Pair.toMap())
						)
				);
	}

	@Override
	public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> input, Type<A> aType, Type<B> bType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <A, B> Either<TypeTemplate, FieldNotFoundException> findFieldOrType(int index, @Nullable String name, Type<A> type, Type<B> resultType) {
		return Either.right(new FieldNotFoundException("Not implemented"));
	}

	@Override
	public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily family, IntFunction<RewriteResult<?, ?>> function) {
		return index -> {
			RewriteResult<Pair<K, ?>, Pair<K, ?>> result = RewriteResult.nop((TaggedChoice.TaggedChoiceType)this.apply(family).apply(index));

			for (Entry<K, TypeTemplate> entry : this.templates.entrySet()) {
				RewriteResult<?, ?> elementResult = (RewriteResult<?, ?>)((TypeTemplate)entry.getValue()).hmap(family, function).apply(index);
				result = (RewriteResult<Pair<K, ?>, Pair<K, ?>>)TaggedChoice.TaggedChoiceType.elementResult(
						entry.getKey(), (TaggedChoice.TaggedChoiceType<Object>)result.view().newType(), elementResult
					)
					.compose(result);
			}

			return result;
		};
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof TaggedChoice)) {
			return false;
		} else {
			TaggedChoice<?> other = (TaggedChoice<?>)obj;
			return Objects.equals(this.name, other.name) && Objects.equals(this.keyType, other.keyType) && Objects.equals(this.templates, other.templates);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.name, this.keyType, this.templates});
	}

	public String toString() {
		return "TaggedChoice[" + this.name + ", " + Joiner.on(", ").withKeyValueSeparator(" -> ").join(this.templates) + "]";
	}

	public static final class TaggedChoiceType<K> extends Type<Pair<K, ?>> {
		private final String name;
		private final Type<K> keyType;
		protected final Map<K, Type<?>> types;
		private final int hashCode;

		public TaggedChoiceType(String name, Type<K> keyType, Map<K, Type<?>> types) {
			this.name = name;
			this.keyType = keyType;
			this.types = types;
			this.hashCode = Objects.hash(new Object[]{name, keyType, types});
		}

		@Override
		public RewriteResult<Pair<K, ?>, ?> all(TypeRewriteRule rule, boolean recurse, boolean checkIndex) {
			Map<K, ? extends RewriteResult<?, ?>> results = (Map<K, ? extends RewriteResult<?, ?>>)this.types
				.entrySet()
				.stream()
				.map(e -> rule.rewrite((Type)e.getValue()).map(v -> Pair.of(e.getKey(), v)))
				.filter(e -> e.isPresent() && !Objects.equals(((RewriteResult)((Pair)e.get()).getSecond()).view().function(), Functions.id()))
				.map(Optional::get)
				.collect(Pair.toMap());
			if (results.isEmpty()) {
				return RewriteResult.nop(this);
			} else if (results.size() == 1) {
				Entry<K, ? extends RewriteResult<?, ?>> entry = (Entry<K, ? extends RewriteResult<?, ?>>)results.entrySet().iterator().next();
				return elementResult((K)entry.getKey(), this, (RewriteResult)entry.getValue());
			} else {
				Map<K, Type<?>> newTypes = Maps.<K, Type<?>>newHashMap(this.types);
				BitSet recData = new BitSet();

				for (Entry<K, ? extends RewriteResult<?, ?>> entry : results.entrySet()) {
					newTypes.put(entry.getKey(), ((RewriteResult)entry.getValue()).view().newType());
					recData.or(((RewriteResult)entry.getValue()).recData());
				}

				return RewriteResult.create(
					View.create(
						this,
						DSL.taggedChoiceType(this.name, this.keyType, newTypes),
						Functions.fun("TaggedChoiceTypeRewriteResult " + results.size(), new TaggedChoice.TaggedChoiceType.RewriteFunc(results))
					),
					recData
				);
			}
		}

		public static <K, FT, FR> RewriteResult<Pair<K, ?>, Pair<K, ?>> elementResult(K key, TaggedChoice.TaggedChoiceType<K> type, RewriteResult<FT, FR> result) {
			return opticView(type, result, TypedOptic.tagged(type, key, result.view().type(), result.view().newType()));
		}

		@Override
		public Optional<RewriteResult<Pair<K, ?>, ?>> one(TypeRewriteRule rule) {
			for (Entry<K, Type<?>> entry : this.types.entrySet()) {
				Optional<? extends RewriteResult<?, ?>> elementResult = rule.rewrite((Type)entry.getValue());
				if (elementResult.isPresent()) {
					return Optional.of(elementResult(entry.getKey(), this, (RewriteResult)elementResult.get()));
				}
			}

			return Optional.empty();
		}

		@Override
		public Type<?> updateMu(RecursiveTypeFamily newFamily) {
			return DSL.taggedChoiceType(
				this.name,
				this.keyType,
				(Map<K, ? extends Type<?>>)this.types.entrySet().stream().map(e -> Pair.of(e.getKey(), ((Type)e.getValue()).updateMu(newFamily))).collect(Pair.toMap())
			);
		}

		@Override
		public TypeTemplate buildTemplate() {
			return DSL.taggedChoice(
				this.name,
				this.keyType,
				(Map<K, TypeTemplate>)this.types.entrySet().stream().map(e -> Pair.of(e.getKey(), ((Type)e.getValue()).template())).collect(Pair.toMap())
			);
		}

		private <V> DataResult<? extends Encoder<Pair<K, ?>>> encoder(Pair<K, V> pair) {
			return this.getCodec(pair.getFirst()).map(c -> c.comap(p -> p.getSecond()));
		}

		@Override
		protected Codec<Pair<K, ?>> buildCodec() {
			return new KeyDispatchCodec(
					this.name, this.keyType.codec(), p -> DataResult.success(p.getFirst()), k -> this.getCodec((K)k).map(c -> c.map(v -> Pair.of(k, v))), this::encoder
				)
				.codec();
		}

		private DataResult<? extends Codec<?>> getCodec(K k) {
			return (DataResult<? extends Codec<?>>)Optional.ofNullable(this.types.get(k))
				.map(t -> DataResult.success(t.codec()))
				.orElseGet(() -> DataResult.error("Unsupported key: " + k));
		}

		@Override
		public Optional<Type<?>> findFieldTypeOpt(String name) {
			return this.types.values().stream().map(t -> t.findFieldTypeOpt(name)).filter(Optional::isPresent).findFirst().flatMap(Function.identity());
		}

		@Override
		public Optional<Pair<K, ?>> point(DynamicOps<?> ops) {
			return this.types
				.entrySet()
				.stream()
				.map(e -> ((Type)e.getValue()).point(ops).map(value -> Pair.of(e.getKey(), value)))
				.filter(Optional::isPresent)
				.findFirst()
				.flatMap(Function.identity())
				.map(p -> p);
		}

		public Optional<Typed<Pair<K, ?>>> point(DynamicOps<?> ops, K key, Object value) {
			return !this.types.containsKey(key) ? Optional.empty() : Optional.of(new Typed<>(this, ops, Pair.of(key, value)));
		}

		@Override
		public <FT, FR> Either<TypedOptic<Pair<K, ?>, ?, FT, FR>, FieldNotFoundException> findTypeInChildren(
			Type<FT> type, Type<FR> resultType, TypeMatcher<FT, FR> matcher, boolean recurse
		) {
			final Map<K, ? extends TypedOptic<?, ?, FT, FR>> optics = (Map<K, ? extends TypedOptic<?, ?, FT, FR>>)this.types
				.entrySet()
				.stream()
				.map(e -> Pair.of(e.getKey(), ((Type)e.getValue()).findType(type, resultType, matcher, recurse)))
				.filter(e -> ((Either)e.getSecond()).left().isPresent())
				.map(e -> e.mapSecond(o -> (TypedOptic)o.left().get()))
				.collect(Pair.toMap());
			if (optics.isEmpty()) {
				return Either.right(new FieldNotFoundException("Not found in any choices"));
			} else if (optics.size() == 1) {
				Entry<K, ? extends TypedOptic<?, ?, FT, FR>> entry = (Entry<K, ? extends TypedOptic<?, ?, FT, FR>>)optics.entrySet().iterator().next();
				return Either.left(this.cap(this, (K)entry.getKey(), (TypedOptic)entry.getValue()));
			} else {
				Set<TypeToken<? extends K1>> bounds = Sets.<TypeToken<? extends K1>>newHashSet();
				optics.values().forEach(o -> bounds.addAll(o.bounds()));
				Optic<?, Pair<K, ?>, Pair<K, ?>, FT, FR> optic;
				TypeToken<? extends K1> bound;
				if (TypedOptic.instanceOf(bounds, Cartesian.Mu.TYPE_TOKEN) && optics.size() == this.types.size()) {
					bound = Cartesian.Mu.TYPE_TOKEN;
					optic = new Lens<Pair<K, ?>, Pair<K, ?>, FT, FR>() {
						public FT view(Pair<K, ?> s) {
							TypedOptic<?, ?, FT, FR> optic = (TypedOptic<?, ?, FT, FR>)optics.get(s.getFirst());
							return (FT)this.capView(s, optic);
						}

						private <S, T> FT capView(Pair<K, ?> s, TypedOptic<S, T, FT, FR> optic) {
							return (FT)Optics.toLens((Optic)optic.upCast(Cartesian.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).view(s.getSecond());
						}

						public Pair<K, ?> update(FR b, Pair<K, ?> s) {
							TypedOptic<?, ?, FT, FR> optic = (TypedOptic<?, ?, FT, FR>)optics.get(s.getFirst());
							return this.capUpdate(b, s, optic);
						}

						private <S, T> Pair<K, ?> capUpdate(FR b, Pair<K, ?> s, TypedOptic<S, T, FT, FR> optic) {
							return Pair.of(
								s.getFirst(), Optics.toLens((Optic)optic.upCast(Cartesian.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).update(b, s.getSecond())
							);
						}
					};
				} else if (TypedOptic.instanceOf(bounds, AffineP.Mu.TYPE_TOKEN)) {
					bound = AffineP.Mu.TYPE_TOKEN;
					optic = new Affine<Pair<K, ?>, Pair<K, ?>, FT, FR>() {
						public Either<Pair<K, ?>, FT> preview(Pair<K, ?> s) {
							if (!optics.containsKey(s.getFirst())) {
								return Either.left(s);
							} else {
								TypedOptic<?, ?, FT, FR> optic = (TypedOptic<?, ?, FT, FR>)optics.get(s.getFirst());
								return this.capPreview(s, optic);
							}
						}

						private <S, T> Either<Pair<K, ?>, FT> capPreview(Pair<K, ?> s, TypedOptic<S, T, FT, FR> optic) {
							return Optics.toAffine((Optic)optic.upCast(AffineP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new))
								.preview(s.getSecond())
								.mapLeft(t -> Pair.of(s.getFirst(), t));
						}

						public Pair<K, ?> set(FR b, Pair<K, ?> s) {
							if (!optics.containsKey(s.getFirst())) {
								return s;
							} else {
								TypedOptic<?, ?, FT, FR> optic = (TypedOptic<?, ?, FT, FR>)optics.get(s.getFirst());
								return this.capSet(b, s, optic);
							}
						}

						private <S, T> Pair<K, ?> capSet(FR b, Pair<K, ?> s, TypedOptic<S, T, FT, FR> optic) {
							return Pair.of(
								s.getFirst(), Optics.toAffine((Optic)optic.upCast(AffineP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).set(b, s.getSecond())
							);
						}
					};
				} else {
					if (!TypedOptic.instanceOf(bounds, TraversalP.Mu.TYPE_TOKEN)) {
						throw new IllegalStateException("Could not merge TaggedChoiceType optics, unknown bound: " + Arrays.toString(bounds.toArray()));
					}

					bound = TraversalP.Mu.TYPE_TOKEN;
					optic = new Traversal<Pair<K, ?>, Pair<K, ?>, FT, FR>() {
						@Override
						public <F extends K1> FunctionType<Pair<K, ?>, App<F, Pair<K, ?>>> wander(Applicative<F, ?> applicative, FunctionType<FT, App<F, FR>> input) {
							return pair -> {
								if (!optics.containsKey(pair.getFirst())) {
									return applicative.point(pair);
								} else {
									TypedOptic<?, ?, FT, FR> optic = (TypedOptic<?, ?, FT, FR>)optics.get(pair.getFirst());
									return this.capTraversal(applicative, input, pair, optic);
								}
							};
						}

						private <S, T, F extends K1> App<F, Pair<K, ?>> capTraversal(
							Applicative<F, ?> applicative, FunctionType<FT, App<F, FR>> input, Pair<K, ?> pair, TypedOptic<S, T, FT, FR> optic
						) {
							Traversal<S, T, FT, FR> traversal = Optics.toTraversal(
								(Optic<? super TraversalP.Mu, S, T, FT, FR>)optic.upCast(TraversalP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)
							);
							return applicative.ap(value -> Pair.of(pair.getFirst(), value), traversal.wander(applicative, input).apply((S)pair.getSecond()));
						}
					};
				}

				Map<K, Type<?>> newTypes = (Map<K, Type<?>>)this.types
					.entrySet()
					.stream()
					.map(e -> Pair.of(e.getKey(), optics.containsKey(e.getKey()) ? ((TypedOptic)optics.get(e.getKey())).tType() : (Type)e.getValue()))
					.collect(Pair.toMap());
				return Either.left(new TypedOptic<>(bound, this, DSL.taggedChoiceType(this.name, this.keyType, newTypes), type, resultType, optic));
			}
		}

		private <S, T, FT, FR> TypedOptic<Pair<K, ?>, Pair<K, ?>, FT, FR> cap(TaggedChoice.TaggedChoiceType<K> choiceType, K key, TypedOptic<S, T, FT, FR> optic) {
			return TypedOptic.tagged(choiceType, key, optic.sType(), optic.tType()).compose(optic);
		}

		@Override
		public Optional<TaggedChoice.TaggedChoiceType<?>> findChoiceType(String name, int index) {
			return Objects.equals(name, this.name) ? Optional.of(this) : Optional.empty();
		}

		@Override
		public Optional<Type<?>> findCheckedType(int index) {
			return this.types.values().stream().map(type -> type.findCheckedType(index)).filter(Optional::isPresent).findFirst().flatMap(Function.identity());
		}

		@Override
		public boolean equals(Object obj, boolean ignoreRecursionPoints, boolean checkIndex) {
			if (this == obj) {
				return true;
			} else if (!(obj instanceof TaggedChoice.TaggedChoiceType)) {
				return false;
			} else {
				TaggedChoice.TaggedChoiceType<?> other = (TaggedChoice.TaggedChoiceType<?>)obj;
				if (!Objects.equals(this.name, other.name)) {
					return false;
				} else if (!this.keyType.equals(other.keyType, ignoreRecursionPoints, checkIndex)) {
					return false;
				} else if (this.types.size() != other.types.size()) {
					return false;
				} else {
					for (Entry<K, Type<?>> entry : this.types.entrySet()) {
						if (!((Type)entry.getValue()).equals(other.types.get(entry.getKey()), ignoreRecursionPoints, checkIndex)) {
							return false;
						}
					}

					return true;
				}
			}
		}

		public int hashCode() {
			return this.hashCode;
		}

		public String toString() {
			return "TaggedChoiceType[" + this.name + ", " + Joiner.on(", \n").withKeyValueSeparator(" -> ").join(this.types) + "]\n";
		}

		public String getName() {
			return this.name;
		}

		public Type<K> getKeyType() {
			return this.keyType;
		}

		public boolean hasType(K key) {
			return this.types.containsKey(key);
		}

		public Map<K, Type<?>> types() {
			return this.types;
		}

		private static final class RewriteFunc<K> implements Function<DynamicOps<?>, Function<Pair<K, ?>, Pair<K, ?>>> {
			private final Map<K, ? extends RewriteResult<?, ?>> results;

			public RewriteFunc(Map<K, ? extends RewriteResult<?, ?>> results) {
				this.results = results;
			}

			public FunctionType<Pair<K, ?>, Pair<K, ?>> apply(DynamicOps<?> ops) {
				return input -> {
					RewriteResult<?, ?> result = (RewriteResult<?, ?>)this.results.get(input.getFirst());
					return result == null ? input : this.capRuleApply(ops, input, result);
				};
			}

			private <A, B> Pair<K, B> capRuleApply(DynamicOps<?> ops, Pair<K, ?> input, RewriteResult<A, B> result) {
				return input.mapSecond(v -> ((Function)result.view().function().evalCached().apply(ops)).apply(v));
			}

			public boolean equals(Object o) {
				if (this == o) {
					return true;
				} else if (o != null && this.getClass() == o.getClass()) {
					TaggedChoice.TaggedChoiceType.RewriteFunc<?> that = (TaggedChoice.TaggedChoiceType.RewriteFunc<?>)o;
					return Objects.equals(this.results, that.results);
				} else {
					return false;
				}
			}

			public int hashCode() {
				return Objects.hash(new Object[]{this.results});
			}
		}
	}
}
