package com.mojang.datafixers.types.templates;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.Type.TypeMatcher;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Check implements TypeTemplate {
	private final String name;
	private final int index;
	private final TypeTemplate element;

	public Check(String name, int index, TypeTemplate element) {
		this.name = name;
		this.index = index;
		this.element = element;
	}

	@Override
	public int size() {
		return Math.max(this.index + 1, this.element.size());
	}

	@Override
	public TypeFamily apply(TypeFamily family) {
		return new TypeFamily() {
			@Override
			public Type<?> apply(int index) {
				if (index < 0) {
					throw new IndexOutOfBoundsException();
				} else {
					return new Check.CheckType<>(Check.this.name, index, Check.this.index, Check.this.element.apply(family).apply(index));
				}
			}
		};
	}

	@Override
	public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> input, Type<A> aType, Type<B> bType) {
		return TypeFamily.familyOptic(i -> this.element.applyO(input, aType, bType).apply(i));
	}

	@Override
	public <FT, FR> Either<TypeTemplate, FieldNotFoundException> findFieldOrType(int index, @Nullable String name, Type<FT> type, Type<FR> resultType) {
		return index == this.index ? this.element.findFieldOrType(index, name, type, resultType) : Either.right(new FieldNotFoundException("Not a matching index"));
	}

	@Override
	public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily family, IntFunction<RewriteResult<?, ?>> function) {
		return index -> {
			RewriteResult<?, ?> elementResult = (RewriteResult<?, ?>)this.element.hmap(family, function).apply(index);
			return this.cap(family, index, elementResult);
		};
	}

	private <A> RewriteResult<?, ?> cap(TypeFamily family, int index, RewriteResult<A, ?> elementResult) {
		return Check.CheckType.fix((Check.CheckType<?>)this.apply(family).apply(index), elementResult);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Check)) {
			return false;
		} else {
			Check that = (Check)obj;
			return Objects.equals(this.name, that.name) && this.index == that.index && Objects.equals(this.element, that.element);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.name, this.index, this.element});
	}

	public String toString() {
		return "Tag[" + this.name + ", " + this.index + ": " + this.element + "]";
	}

	public static final class CheckType<A> extends Type<A> {
		private final String name;
		private final int index;
		private final int expectedIndex;
		private final Type<A> delegate;

		public CheckType(String name, int index, int expectedIndex, Type<A> delegate) {
			this.name = name;
			this.index = index;
			this.expectedIndex = expectedIndex;
			this.delegate = delegate;
		}

		@Override
		protected Codec<A> buildCodec() {
			return Codec.of(this.delegate.codec(), this::read);
		}

		private <T> DataResult<Pair<A, T>> read(DynamicOps<T> ops, T input) {
			return this.index != this.expectedIndex
				? DataResult.error("Index mismatch: " + this.index + " != " + this.expectedIndex)
				: this.delegate.codec().decode(ops, input);
		}

		public static <A, B> RewriteResult<A, ?> fix(Check.CheckType<A> type, RewriteResult<A, B> instance) {
			return Objects.equals(instance.view().function(), Functions.id())
				? RewriteResult.nop(type)
				: opticView(type, instance, wrapOptic(type, TypedOptic.adapter(instance.view().type(), instance.view().newType())));
		}

		@Override
		public RewriteResult<A, ?> all(TypeRewriteRule rule, boolean recurse, boolean checkIndex) {
			return checkIndex && this.index != this.expectedIndex ? RewriteResult.nop(this) : fix(this, this.delegate.rewriteOrNop(rule));
		}

		@Override
		public Optional<RewriteResult<A, ?>> everywhere(TypeRewriteRule rule, PointFreeRule optimizationRule, boolean recurse, boolean checkIndex) {
			return checkIndex && this.index != this.expectedIndex ? Optional.empty() : super.everywhere(rule, optimizationRule, recurse, checkIndex);
		}

		@Override
		public Optional<RewriteResult<A, ?>> one(TypeRewriteRule rule) {
			return rule.rewrite(this.delegate).map(view -> fix(this, view));
		}

		@Override
		public Type<?> updateMu(RecursiveTypeFamily newFamily) {
			return new Check.CheckType(this.name, this.index, this.expectedIndex, (Type<A>)this.delegate.updateMu(newFamily));
		}

		@Override
		public TypeTemplate buildTemplate() {
			return DSL.check(this.name, this.expectedIndex, this.delegate.template());
		}

		@Override
		public Optional<TaggedChoiceType<?>> findChoiceType(String name, int index) {
			return index == this.expectedIndex ? this.delegate.findChoiceType(name, index) : Optional.empty();
		}

		@Override
		public Optional<Type<?>> findCheckedType(int index) {
			return index == this.expectedIndex ? Optional.of(this.delegate) : Optional.empty();
		}

		@Override
		public Optional<Type<?>> findFieldTypeOpt(String name) {
			return this.index == this.expectedIndex ? this.delegate.findFieldTypeOpt(name) : Optional.empty();
		}

		@Override
		public Optional<A> point(DynamicOps<?> ops) {
			return this.index == this.expectedIndex ? this.delegate.point(ops) : Optional.empty();
		}

		@Override
		public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findTypeInChildren(
			Type<FT> type, Type<FR> resultType, TypeMatcher<FT, FR> matcher, boolean recurse
		) {
			return this.index != this.expectedIndex
				? Either.right(new FieldNotFoundException("Incorrect index in CheckType"))
				: this.delegate.findType(type, resultType, matcher, recurse).mapLeft(optic -> wrapOptic(this, optic));
		}

		protected static <A, B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(Check.CheckType<A> type, TypedOptic<A, B, FT, FR> optic) {
			return new TypedOptic<>(
				optic.bounds(), type, new Check.CheckType<>(type.name, type.index, type.expectedIndex, optic.tType()), optic.aType(), optic.bType(), optic.optic()
			);
		}

		public String toString() {
			return "TypeTag[" + this.index + "~" + this.expectedIndex + "][" + this.name + ": " + this.delegate + "]";
		}

		@Override
		public boolean equals(Object obj, boolean ignoreRecursionPoints, boolean checkIndex) {
			if (!(obj instanceof Check.CheckType)) {
				return false;
			} else {
				Check.CheckType<?> type = (Check.CheckType<?>)obj;
				if (this.index == type.index && this.expectedIndex == type.expectedIndex) {
					if (!checkIndex) {
						return true;
					}

					if (this.delegate.equals(type.delegate, ignoreRecursionPoints, checkIndex)) {
						return true;
					}
				}

				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.index, this.expectedIndex, this.delegate});
		}
	}
}
