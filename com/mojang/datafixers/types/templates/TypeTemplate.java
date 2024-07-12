package com.mojang.datafixers.types.templates;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.util.Either;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public interface TypeTemplate {
	int size();

	TypeFamily apply(TypeFamily typeFamily);

	default Type<?> toSimpleType() {
		return this.apply(new TypeFamily() {
			@Override
			public Type<?> apply(int index) {
				return DSL.emptyPartType();
			}
		}).apply(-1);
	}

	<A, B> Either<TypeTemplate, FieldNotFoundException> findFieldOrType(int integer, @Nullable String string, Type<A> type3, Type<B> type4);

	IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction);

	<A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type2, Type<B> type3);
}
