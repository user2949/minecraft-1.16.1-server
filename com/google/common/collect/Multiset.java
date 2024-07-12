package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multiset<E> extends Collection<E> {
	int size();

	int count(@Nullable @CompatibleWith("E") Object object);

	@CanIgnoreReturnValue
	int add(@Nullable E object, int integer);

	@CanIgnoreReturnValue
	int remove(@Nullable @CompatibleWith("E") Object object, int integer);

	@CanIgnoreReturnValue
	int setCount(E object, int integer);

	@CanIgnoreReturnValue
	boolean setCount(E object, int integer2, int integer3);

	Set<E> elementSet();

	Set<Multiset.Entry<E>> entrySet();

	@Beta
	default void forEachEntry(ObjIntConsumer<? super E> action) {
		Preconditions.checkNotNull(action);
		this.entrySet().forEach(entry -> action.accept(entry.getElement(), entry.getCount()));
	}

	boolean equals(@Nullable Object object);

	int hashCode();

	String toString();

	Iterator<E> iterator();

	boolean contains(@Nullable Object object);

	boolean containsAll(Collection<?> collection);

	@CanIgnoreReturnValue
	boolean add(E object);

	@CanIgnoreReturnValue
	boolean remove(@Nullable Object object);

	@CanIgnoreReturnValue
	boolean removeAll(Collection<?> collection);

	@CanIgnoreReturnValue
	boolean retainAll(Collection<?> collection);

	default void forEach(Consumer<? super E> action) {
		Preconditions.checkNotNull(action);
		this.entrySet().forEach(entry -> {
			E elem = (E)entry.getElement();
			int count = entry.getCount();

			for (int i = 0; i < count; i++) {
				action.accept(elem);
			}
		});
	}

	default Spliterator<E> spliterator() {
		return Multisets.spliteratorImpl(this);
	}

	public interface Entry<E> {
		E getElement();

		int getCount();

		boolean equals(Object object);

		int hashCode();

		String toString();
	}
}
