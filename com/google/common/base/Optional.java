package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(
	serializable = true
)
public abstract class Optional<T> implements Serializable {
	private static final long serialVersionUID = 0L;

	public static <T> Optional<T> absent() {
		return Absent.withType();
	}

	public static <T> Optional<T> of(T reference) {
		return new Present<>(Preconditions.checkNotNull(reference));
	}

	public static <T> Optional<T> fromNullable(@Nullable T nullableReference) {
		return (Optional<T>)(nullableReference == null ? absent() : new Present<>(nullableReference));
	}

	@Nullable
	public static <T> Optional<T> fromJavaUtil(@Nullable java.util.Optional<T> javaUtilOptional) {
		return javaUtilOptional == null ? null : fromNullable((T)javaUtilOptional.orElse(null));
	}

	@Nullable
	public static <T> java.util.Optional<T> toJavaUtil(@Nullable Optional<T> googleOptional) {
		return googleOptional == null ? null : googleOptional.toJavaUtil();
	}

	Optional() {
	}

	public abstract boolean isPresent();

	public abstract T get();

	public abstract T or(T object);

	public abstract Optional<T> or(Optional<? extends T> optional);

	@Beta
	public abstract T or(Supplier<? extends T> supplier);

	@Nullable
	public abstract T orNull();

	public abstract Set<T> asSet();

	public abstract <V> Optional<V> transform(Function<? super T, V> function);

	public java.util.Optional<T> toJavaUtil() {
		return java.util.Optional.ofNullable(this.orNull());
	}

	public abstract boolean equals(@Nullable Object object);

	public abstract int hashCode();

	public abstract String toString();

	@Beta
	public static <T> Iterable<T> presentInstances(Iterable<? extends Optional<? extends T>> optionals) {
		Preconditions.checkNotNull(optionals);
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return new AbstractIterator<T>() {
					private final Iterator<? extends Optional<? extends T>> iterator = Preconditions.checkNotNull(optionals.iterator());

					@Override
					protected T computeNext() {
						while (this.iterator.hasNext()) {
							Optional<? extends T> optional = (Optional<? extends T>)this.iterator.next();
							if (optional.isPresent()) {
								return (T)optional.get();
							}
						}

						return (T)this.endOfData();
					}
				};
			}
		};
	}
}
