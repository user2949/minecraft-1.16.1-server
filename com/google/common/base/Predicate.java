package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.Nullable;

@FunctionalInterface
@GwtCompatible
public interface Predicate<T> extends java.util.function.Predicate<T> {
	@CanIgnoreReturnValue
	boolean apply(@Nullable T object);

	boolean equals(@Nullable Object object);

	default boolean test(@Nullable T input) {
		return this.apply(input);
	}
}
