package com.mojang.datafixers.types.templates;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.Profunctor.Mu;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Const implements TypeTemplate {
	private final Type<?> type;

	public Const(Type<?> type) {
		this.type = type;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public TypeFamily apply(TypeFamily family) {
		return new TypeFamily() {
			@Override
			public Type<?> apply(int index) {
				return Const.this.type;
			}
		};
	}

	@Override
	public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> input, Type<A> aType, Type<B> bType) {
		if (Objects.equals(this.type, aType)) {
			return TypeFamily.familyOptic(i -> new OpticParts<>(ImmutableSet.of(Mu.TYPE_TOKEN), Optics.id()));
		} else {
			TypedOptic<?, ?, A, B> ignoreOptic = this.makeIgnoreOptic(this.type, aType, bType);
			return TypeFamily.familyOptic(i -> new OpticParts<>(ignoreOptic.bounds(), ignoreOptic.optic()));
		}
	}

	private <T, A, B> TypedOptic<T, T, A, B> makeIgnoreOptic(Type<T> type, Type<A> aType, Type<B> bType) {
		return new TypedOptic<>(com.mojang.datafixers.optics.profunctors.AffineP.Mu.TYPE_TOKEN, type, type, aType, bType, Optics.affine(Either::left, (b, t) -> t));
	}

	@Override
	public <FT, FR> Either<TypeTemplate, FieldNotFoundException> findFieldOrType(int index, @Nullable String name, Type<FT> type, Type<FR> resultType) {
		return DSL.fieldFinder(name, type).findType(this.type, resultType, false).mapLeft(field -> new Const(field.tType()));
	}

	@Override
	public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily family, IntFunction<RewriteResult<?, ?>> function) {
		return i -> RewriteResult.nop(this.type);
	}

	public boolean equals(Object obj) {
		return obj instanceof Const && Objects.equals(this.type, ((Const)obj).type);
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.type});
	}

	public String toString() {
		return "Const[" + this.type + "]";
	}

	public Type<?> type() {
		return this.type;
	}

	public static final class PrimitiveType<A> extends Type<A> {
		private final Codec<A> codec;

		public PrimitiveType(Codec<A> codec) {
			this.codec = codec;
		}

		@Override
		public boolean equals(Object o, boolean ignoreRecursionPoints, boolean checkIndex) {
			return this == o;
		}

		@Override
		public TypeTemplate buildTemplate() {
			return DSL.constType(this);
		}

		@Override
		protected Codec<A> buildCodec() {
			return this.codec;
		}

		public String toString() {
			return this.codec.toString();
		}
	}
}
