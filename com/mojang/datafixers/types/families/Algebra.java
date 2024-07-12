package com.mojang.datafixers.types.families;

import com.mojang.datafixers.RewriteResult;

public interface Algebra {
	RewriteResult<?, ?> apply(int integer);

	String toString(int integer);
}
