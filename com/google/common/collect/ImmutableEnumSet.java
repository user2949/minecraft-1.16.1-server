package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Spliterator;
import java.util.function.Consumer;

@GwtCompatible(
	serializable = true,
	emulated = true
)
final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E> {
	private final transient EnumSet<E> delegate;
	@LazyInit
	private transient int hashCode;

	static ImmutableSet asImmutable(EnumSet set) {
		switch (set.size()) {
			case 0:
				return ImmutableSet.of();
			case 1:
				return ImmutableSet.of(Iterables.getOnlyElement(set));
			default:
				return new ImmutableEnumSet(set);
		}
	}

	private ImmutableEnumSet(EnumSet<E> delegate) {
		this.delegate = delegate;
	}

	@Override
	boolean isPartialView() {
		return false;
	}

	@Override
	public UnmodifiableIterator<E> iterator() {
		return Iterators.unmodifiableIterator(this.delegate.iterator());
	}

	@Override
	public Spliterator<E> spliterator() {
		return this.delegate.spliterator();
	}

	public void forEach(Consumer<? super E> action) {
		this.delegate.forEach(action);
	}

	public int size() {
		return this.delegate.size();
	}

	@Override
	public boolean contains(Object object) {
		return this.delegate.contains(object);
	}

	public boolean containsAll(Collection<?> collection) {
		if (collection instanceof ImmutableEnumSet) {
			collection = ((ImmutableEnumSet)collection).delegate;
		}

		return this.delegate.containsAll(collection);
	}

	public boolean isEmpty() {
		return this.delegate.isEmpty();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else {
			if (object instanceof ImmutableEnumSet) {
				object = ((ImmutableEnumSet)object).delegate;
			}

			return this.delegate.equals(object);
		}
	}

	@Override
	boolean isHashCodeFast() {
		return true;
	}

	@Override
	public int hashCode() {
		int result = this.hashCode;
		return result == 0 ? (this.hashCode = this.delegate.hashCode()) : result;
	}

	public String toString() {
		return this.delegate.toString();
	}

	@Override
	Object writeReplace() {
		return new ImmutableEnumSet.EnumSerializedForm(this.delegate);
	}

	private static class EnumSerializedForm<E extends Enum<E>> implements Serializable {
		final EnumSet<E> delegate;
		private static final long serialVersionUID = 0L;

		EnumSerializedForm(EnumSet<E> delegate) {
			this.delegate = delegate;
		}

		Object readResolve() {
			return new ImmutableEnumSet(this.delegate.clone());
		}
	}
}
