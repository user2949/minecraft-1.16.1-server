package com.mojang.datafixers;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public interface DataFixer {
	<T> Dynamic<T> update(TypeReference typeReference, Dynamic<T> dynamic, int integer3, int integer4);

	Schema getSchema(int integer);
}
