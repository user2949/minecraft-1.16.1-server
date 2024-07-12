package com.mojang.datafixers;

import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.Continue;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.Type.TypeMatcher;
import com.mojang.datafixers.types.templates.Tag.TagType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import java.util.Objects;

final class NamedChoiceFinder<FT> implements OpticFinder<FT> {
	private final String name;
	private final Type<FT> type;

	public NamedChoiceFinder(String name, Type<FT> type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Type<FT> type() {
		return this.type;
	}

	@Override
	public <A, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findType(Type<A> containerType, Type<FR> resultType, boolean recurse) {
		return containerType.findTypeCached(this.type, resultType, new NamedChoiceFinder.Matcher<>(this.name, this.type, resultType), recurse);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof NamedChoiceFinder)) {
			return false;
		} else {
			NamedChoiceFinder<?> that = (NamedChoiceFinder<?>)o;
			return Objects.equals(this.name, that.name) && Objects.equals(this.type, that.type);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.name, this.type});
	}

	private static class Matcher<FT, FR> implements TypeMatcher<FT, FR> {
		private final Type<FR> resultType;
		private final String name;
		private final Type<FT> type;

		public Matcher(String name, Type<FT> type, Type<FR> resultType) {
			this.resultType = resultType;
			this.name = name;
			this.type = type;
		}

		@Override
		public <S> Either<TypedOptic<S, ?, FT, FR>, FieldNotFoundException> match(Type<S> targetType) {
			if (targetType instanceof TaggedChoiceType) {
				TaggedChoiceType<?> choiceType = (TaggedChoiceType<?>)targetType;
				Type<?> elementType = (Type<?>)choiceType.types().get(this.name);
				if (elementType != null) {
					return !Objects.equals(this.type, elementType)
						? Either.right(
							new FieldNotFoundException(String.format("Type error for choice type \"%s\": expected type: %s, actual type: %s)", this.name, targetType, elementType))
						)
						: Either.left(TypedOptic.tagged(choiceType, this.name, this.type, this.resultType));
				} else {
					return Either.right(new Continue());
				}
			} else {
				return targetType instanceof TagType ? Either.right(new FieldNotFoundException("in tag")) : Either.right(new Continue());
			}
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				NamedChoiceFinder.Matcher<?, ?> matcher = (NamedChoiceFinder.Matcher<?, ?>)o;
				return Objects.equals(this.resultType, matcher.resultType) && Objects.equals(this.name, matcher.name) && Objects.equals(this.type, matcher.type);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.resultType, this.name, this.type});
		}
	}
}
