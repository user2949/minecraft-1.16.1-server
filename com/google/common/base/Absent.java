package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
final class Absent<T> extends Optional<T> {
	static final Absent<Object> INSTANCE = new Absent<>();
	private static final long serialVersionUID = 0L;

	static <T> Optional<T> withType() {
		return (Optional<T>)INSTANCE;
	}

	private Absent() {
	}

	@Override
	public boolean isPresent() {
		return false;
	}

	@Override
	public T get() {
		throw new IllegalStateException("Optional.get() cannot be called on an absent value");
	}

	@Override
	public T or(T defaultValue) {
		return Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
	}

	@Override
	public Optional<T> or(Optional<? extends T> secondChoice) {
		return Preconditions.checkNotNull((Optional<T>)secondChoice);
	}

	@Override
	public T or(Supplier<? extends T> supplier) {
		return Preconditions.checkNotNull((T)supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
	}

	@Nullable
	@Override
	public T orNull() {
		return null;
	}

	@Override
	public Set<T> asSet() {
		return Collections.emptySet();
	}

	@Override
	public <V> Optional<V> transform(Function<? super T, V> function) {
		Preconditions.checkNotNull(function);
		return Optional.absent();
	}

	@Override
	public boolean equals(@Nullable Object object) {
		return object == this;
	}

	@Override
	public int hashCode() {
		return 2040732332;
	}

	@Override
	public String toString() {
		return "Optional.absent()";
	}

	private Object readResolve() {
		return INSTANCE;
	}
}
