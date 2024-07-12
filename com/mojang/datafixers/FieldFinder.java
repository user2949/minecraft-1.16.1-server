package com.mojang.datafixers;

import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Proj1;
import com.mojang.datafixers.optics.profunctors.Profunctor.Mu;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.Continue;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.Type.TypeMatcher;
import com.mojang.datafixers.types.templates.Tag.TagType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;

public final class FieldFinder<FT> implements OpticFinder<FT> {
	@Nullable
	private final String name;
	private final Type<FT> type;

	public FieldFinder(@Nullable String name, Type<FT> type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Type<FT> type() {
		return this.type;
	}

	@Override
	public <A, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findType(Type<A> containerType, Type<FR> resultType, boolean recurse) {
		return containerType.findTypeCached(this.type, resultType, new FieldFinder.Matcher<>(this.name, this.type, resultType), recurse);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof FieldFinder)) {
			return false;
		} else {
			FieldFinder<?> that = (FieldFinder<?>)o;
			return Objects.equals(this.name, that.name) && Objects.equals(this.type, that.type);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.name, this.type});
	}

	private static final class Matcher<FT, FR> implements TypeMatcher<FT, FR> {
		private final Type<FR> resultType;
		@Nullable
		private final String name;
		private final Type<FT> type;

		public Matcher(@Nullable String name, Type<FT> type, Type<FR> resultType) {
			this.resultType = resultType;
			this.name = name;
			this.type = type;
		}

		@Override
		public <S> Either<TypedOptic<S, ?, FT, FR>, FieldNotFoundException> match(Type<S> targetType) {
			if (this.name == null && this.type.equals(targetType, true, false)) {
				return Either.left(new TypedOptic<>(Mu.TYPE_TOKEN, targetType, this.resultType, (Type<FT>)targetType, this.resultType, Optics.id()));
			} else if (targetType instanceof TagType) {
				TagType<S> tagType = (TagType<S>)targetType;
				if (!Objects.equals(tagType.name(), this.name)) {
					return Either.right(new FieldNotFoundException(String.format("Not found: \"%s\" (in type: %s)", this.name, targetType)));
				} else {
					return !Objects.equals(this.type, tagType.element())
						? Either.right(
							new FieldNotFoundException(String.format("Type error for field \"%s\": expected type: %s, actual type: %s)", this.name, this.type, tagType.element()))
						)
						: Either.left(new TypedOptic<>(Mu.TYPE_TOKEN, tagType, DSL.field(tagType.name(), this.resultType), this.type, this.resultType, Optics.id()));
				}
			} else {
				if (targetType instanceof TaggedChoiceType) {
					TaggedChoiceType<FT> choiceType = (TaggedChoiceType<FT>)targetType;
					if (Objects.equals(this.name, choiceType.getName())) {
						if (!Objects.equals(this.type, choiceType.getKeyType())) {
							return Either.right(
								new FieldNotFoundException(
									String.format("Type error for field \"%s\": expected type: %s, actual type: %s)", this.name, this.type, choiceType.getKeyType())
								)
							);
						}

						if (!Objects.equals(this.type, this.resultType)) {
							return Either.right(new FieldNotFoundException("TaggedChoiceType key type change is unsupported."));
						}

						return Either.left(this.capChoice(choiceType));
					}
				}

				return Either.right(new Continue());
			}
		}

		private <V> TypedOptic<Pair<FT, V>, ?, FT, FT> capChoice(Type<?> choiceType) {
			return new TypedOptic<>(
				com.mojang.datafixers.optics.profunctors.Cartesian.Mu.TYPE_TOKEN, (Type<Pair<FT, V>>)choiceType, choiceType, this.type, this.type, new Proj1<>()
			);
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				FieldFinder.Matcher<?, ?> matcher = (FieldFinder.Matcher<?, ?>)o;
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
