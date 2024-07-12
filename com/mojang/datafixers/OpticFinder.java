package com.mojang.datafixers;

import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.util.Either;
import javax.annotation.Nullable;

public interface OpticFinder<FT> {
	Type<FT> type();

	<A, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findType(Type<A> type1, Type<FR> type2, boolean boolean3);

	default <A> Either<TypedOptic<A, ?, FT, FT>, FieldNotFoundException> findType(Type<A> containerType, boolean recurse) {
		return this.findType(containerType, this.type(), recurse);
	}

	default <GT> OpticFinder<FT> inField(@Nullable String name, Type<GT> type) {
		final OpticFinder<FT> outer = this;
		return new OpticFinder<FT>() {
			@Override
			public Type<FT> type() {
				return outer.type();
			}

			@Override
			public <A, FR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> findType(Type<A> containerType, Type<FR> resultType, boolean recurse) {
				Either<TypedOptic<GT, ?, FT, FR>, FieldNotFoundException> secondOptic = outer.findType(type, resultType, recurse);
				return secondOptic.map(l -> this.cap(containerType, l, recurse), Either::right);
			}

			private <A, FR, GR> Either<TypedOptic<A, ?, FT, FR>, FieldNotFoundException> cap(Type<A> containterType, TypedOptic<GT, GR, FT, FR> l1, boolean recurse) {
				Either<TypedOptic<A, ?, GT, GR>, FieldNotFoundException> first = DSL.fieldFinder(name, type).findType(containterType, l1.tType(), recurse);
				return first.mapLeft(l -> l.compose(l1));
			}
		};
	}
}
