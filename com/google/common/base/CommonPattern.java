package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class CommonPattern {
	abstract CommonMatcher matcher(CharSequence charSequence);

	abstract String pattern();

	abstract int flags();

	public abstract String toString();

	public abstract int hashCode();

	public abstract boolean equals(Object object);
}
