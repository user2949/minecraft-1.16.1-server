package com.mojang.datafixers.types.templates;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.functions.Functions;
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
import com.mojang.serialization.Lifecycle;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Hook implements TypeTemplate {
	private final TypeTemplate element;
	private final Hook.HookFunction preRead;
	private final Hook.HookFunction postWrite;

	public Hook(TypeTemplate element, Hook.HookFunction preRead, Hook.HookFunction postWrite) {
		this.element = element;
		this.preRead = preRead;
		this.postWrite = postWrite;
	}

	@Override
	public int size() {
		return this.element.size();
	}

	@Override
	public TypeFamily apply(TypeFamily family) {
		return index -> DSL.hook(this.element.apply(family).apply(index), this.preRead, this.postWrite);
	}

	@Override
	public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> input, Type<A> aType, Type<B> bType) {
		return TypeFamily.familyOptic(i -> this.element.applyO(input, aType, bType).apply(i));
	}

	@Override
	public <FT, FR> Either<TypeTemplate, FieldNotFoundException> findFieldOrType(int index, @Nullable String name, Type<FT> type, Type<FR> resultType) {
		return this.element.findFieldOrType(index, name, type, resultType);
	}

	@Override
	public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily family, IntFunction<RewriteResult<?, ?>> function) {
		return index -> {
			RewriteResult<?, ?> elementResult = (RewriteResult<?, ?>)this.element.hmap(family, function).apply(index);
			return this.cap(family, index, elementResult);
		};
	}

	private <A> RewriteResult<A, ?> cap(TypeFamily family, int index, RewriteResult<A, ?> elementResult) {
		return Hook.HookType.fix((Hook.HookType<A>)this.apply(family).apply(index), elementResult);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Hook)) {
			return false;
		} else {
			Hook that = (Hook)obj;
			return Objects.equals(this.element, that.element) && Objects.equals(this.preRead, that.preRead) && Objects.equals(this.postWrite, that.postWrite);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.element, this.preRead, this.postWrite});
	}

	public String toString() {
		return "Hook[" + this.element + ", " + this.preRead + ", " + this.postWrite + "]";
	}

	public interface HookFunction {
		Hook.HookFunction IDENTITY = new Hook.HookFunction() {
			@Override
			public <T> T apply(DynamicOps<T> ops, T value) {
				return value;
			}
		};

		<T> T apply(DynamicOps<T> dynamicOps, T object);
	}

	public static final class HookType<A> extends Type<A> {
		private final Type<A> delegate;
		private final Hook.HookFunction preRead;
		private final Hook.HookFunction postWrite;

		public HookType(Type<A> delegate, Hook.HookFunction preRead, Hook.HookFunction postWrite) {
			this.delegate = delegate;
			this.preRead = preRead;
			this.postWrite = postWrite;
		}

		@Override
		protected Codec<A> buildCodec() {
			return new Codec<A>() {
				@Override
				public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
					return HookType.this.delegate.codec().decode(ops, HookType.this.preRead.apply(ops, input)).setLifecycle(Lifecycle.experimental());
				}

				@Override
				public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
					return HookType.this.delegate
						.codec()
						.encode(input, ops, prefix)
						.<T>map(v -> HookType.this.postWrite.apply(ops, (T)v))
						.setLifecycle(Lifecycle.experimental());
				}
			};
		}

		@Override
		public RewriteResult<A, ?> all(TypeRewriteRule rule, boolean recurse, boolean checkIndex) {
			return fix(this, this.delegate.rewriteOrNop(rule));
		}

		@Override
		public Optional<RewriteResult<A, ?>> one(TypeRewriteRule rule) {
			return rule.rewrite(this.delegate).map(view -> fix(this, view));
		}

		@Override
		public Type<?> updateMu(RecursiveTypeFamily newFamily) {
			return new Hook.HookType((Type<A>)this.delegate.updateMu(newFamily), this.preRead, this.postWrite);
		}

		@Override
		public TypeTemplate buildTemplate() {
			return DSL.hook(this.delegate.template(), this.preRead, this.postWrite);
		}

		@Override
		public Optional<TaggedChoiceType<?>> findChoiceType(String name, int index) {
			return this.delegate.findChoiceType(name, index);
		}

		@Override
		public Optional<Type<?>> findCheckedType(int index) {
			return this.delegate.findCheckedType(index);
		}

		@Override
		public Optional<Type<?>> findFieldTypeOpt(String name) {
			return this.delegate.findFieldTypeOpt(name);
		}

		@Override
		public Optional<A> point(DynamicOps<?> ops) {
			return this.delegate.point(ops);
		}

		@Override
		public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findTypeInChildren(
			Type<FT> type, Type<FR> resultType, TypeMatcher<FT, FR> matcher, boolean recurse
		) {
			return this.delegate.findType(type, resultType, matcher, recurse).mapLeft(optic -> wrapOptic(optic, this.preRead, this.postWrite));
		}

		public static <A, B> RewriteResult<A, ?> fix(Hook.HookType<A> type, RewriteResult<A, B> instance) {
			return Objects.equals(instance.view().function(), Functions.id())
				? RewriteResult.nop(type)
				: opticView(type, instance, wrapOptic(TypedOptic.adapter(instance.view().type(), instance.view().newType()), type.preRead, type.postWrite));
		}

		protected static <A, B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(TypedOptic<A, B, FT, FR> optic, Hook.HookFunction preRead, Hook.HookFunction postWrite) {
			return new TypedOptic<>(
				optic.bounds(), DSL.hook(optic.sType(), preRead, postWrite), DSL.hook(optic.tType(), preRead, postWrite), optic.aType(), optic.bType(), optic.optic()
			);
		}

		public String toString() {
			return "HookType[" + this.delegate + ", " + this.preRead + ", " + this.postWrite + "]";
		}

		@Override
		public boolean equals(Object obj, boolean ignoreRecursionPoints, boolean checkIndex) {
			if (!(obj instanceof Hook.HookType)) {
				return false;
			} else {
				Hook.HookType<?> type = (Hook.HookType<?>)obj;
				return this.delegate.equals(type.delegate, ignoreRecursionPoints, checkIndex)
					&& Objects.equals(this.preRead, type.preRead)
					&& Objects.equals(this.postWrite, type.postWrite);
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.delegate, this.preRead, this.postWrite});
		}
	}
}
