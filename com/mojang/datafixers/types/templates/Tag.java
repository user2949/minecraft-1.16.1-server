package com.mojang.datafixers.types.templates;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.Type.TypeMatcher;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class Tag implements TypeTemplate {
	private final String name;
	private final TypeTemplate element;

	public Tag(String name, TypeTemplate element) {
		this.name = name;
		this.element = element;
	}

	@Override
	public int size() {
		return this.element.size();
	}

	@Override
	public TypeFamily apply(TypeFamily family) {
		return new TypeFamily() {
			@Override
			public Type<?> apply(int index) {
				return DSL.field(Tag.this.name, Tag.this.element.apply(family).apply(index));
			}
		};
	}

	@Override
	public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> input, Type<A> aType, Type<B> bType) {
		return TypeFamily.familyOptic(i -> this.element.applyO(input, aType, bType).apply(i));
	}

	@Override
	public <FT, FR> Either<TypeTemplate, FieldNotFoundException> findFieldOrType(int index, @Nullable String name, Type<FT> type, Type<FR> resultType) {
		if (!Objects.equals(name, this.name)) {
			return Either.right(new FieldNotFoundException("Names don't match"));
		} else if (this.element instanceof Const) {
			Const c = (Const)this.element;
			return Objects.equals(type, c.type()) ? Either.left(new Tag(name, new Const(resultType))) : Either.right(new FieldNotFoundException("don't match"));
		} else if (Objects.equals(type, resultType)) {
			return Either.left(this);
		} else {
			if (type instanceof RecursivePointType
				&& this.element instanceof RecursivePoint
				&& ((RecursivePoint)this.element).index() == ((RecursivePointType)type).index()) {
				if (!(resultType instanceof RecursivePointType)) {
					return Either.left(DSL.constType(resultType));
				}

				if (((RecursivePointType)resultType).index() == ((RecursivePoint)this.element).index()) {
					return Either.left(this);
				}
			}

			return Either.right(new FieldNotFoundException("Recursive field"));
		}
	}

	@Override
	public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily family, IntFunction<RewriteResult<?, ?>> function) {
		return this.element.hmap(family, function);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Tag)) {
			return false;
		} else {
			Tag that = (Tag)obj;
			return Objects.equals(this.name, that.name) && Objects.equals(this.element, that.element);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.name, this.element});
	}

	public String toString() {
		return "NameTag[" + this.name + ": " + this.element + "]";
	}

	public static final class TagType<A> extends Type<A> {
		protected final String name;
		protected final Type<A> element;

		public TagType(String name, Type<A> element) {
			this.name = name;
			this.element = element;
		}

		@Override
		public RewriteResult<A, ?> all(TypeRewriteRule rule, boolean recurse, boolean checkIndex) {
			RewriteResult<A, ?> elementView = this.element.rewriteOrNop(rule);
			return RewriteResult.create(this.cap(elementView.view()), elementView.recData());
		}

		private <B> View<A, ?> cap(View<A, B> instance) {
			return Objects.equals(instance.function(), Functions.id())
				? View.nopView(this)
				: View.create(this, DSL.field(this.name, instance.newType()), instance.function());
		}

		@Override
		public Optional<RewriteResult<A, ?>> one(TypeRewriteRule rule) {
			Optional<RewriteResult<A, ?>> view = rule.rewrite(this.element);
			return view.map(instance -> RewriteResult.create(this.cap(instance.view()), instance.recData()));
		}

		@Override
		public Type<?> updateMu(RecursiveTypeFamily newFamily) {
			return DSL.field(this.name, this.element.updateMu(newFamily));
		}

		@Override
		public TypeTemplate buildTemplate() {
			return DSL.field(this.name, this.element.template());
		}

		@Override
		protected Codec<A> buildCodec() {
			return this.element.codec().fieldOf(this.name).codec();
		}

		public String toString() {
			return "Tag[\"" + this.name + "\", " + this.element + "]";
		}

		@Override
		public boolean equals(Object o, boolean ignoreRecursionPoints, boolean checkIndex) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				Tag.TagType<?> tagType = (Tag.TagType<?>)o;
				return Objects.equals(this.name, tagType.name) && this.element.equals(tagType.element, ignoreRecursionPoints, checkIndex);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.name, this.element});
		}

		@Override
		public Optional<Type<?>> findFieldTypeOpt(String name) {
			return Objects.equals(name, this.name) ? Optional.of(this.element) : Optional.empty();
		}

		@Override
		public Optional<A> point(DynamicOps<?> ops) {
			return this.element.point(ops);
		}

		@Override
		public <FT, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findTypeInChildren(
			Type<FT> type, Type<FR> resultType, TypeMatcher<FT, FR> matcher, boolean recurse
		) {
			return this.element.findType(type, resultType, matcher, recurse).mapLeft(this::wrapOptic);
		}

		private <B, FT, FR> TypedOptic<A, B, FT, FR> wrapOptic(TypedOptic<A, B, FT, FR> optic) {
			return new TypedOptic<>(
				optic.bounds(), DSL.field(this.name, optic.sType()), DSL.field(this.name, optic.tType()), optic.aType(), optic.bType(), optic.optic()
			);
		}

		public String name() {
			return this.name;
		}

		public Type<A> element() {
			return this.element;
		}
	}
}
