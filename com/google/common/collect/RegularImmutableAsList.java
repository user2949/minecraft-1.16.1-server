package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.function.Consumer;

@GwtCompatible(
	emulated = true
)
class RegularImmutableAsList<E> extends ImmutableAsList<E> {
	private final ImmutableCollection<E> delegate;
	private final ImmutableList<? extends E> delegateList;

	RegularImmutableAsList(ImmutableCollection<E> delegate, ImmutableList<? extends E> delegateList) {
		this.delegate = delegate;
		this.delegateList = delegateList;
	}

	RegularImmutableAsList(ImmutableCollection<E> delegate, Object[] array) {
		this(delegate, ImmutableList.asImmutableList(array));
	}

	@Override
	ImmutableCollection<E> delegateCollection() {
		return this.delegate;
	}

	ImmutableList<? extends E> delegateList() {
		return this.delegateList;
	}

	@Override
	public UnmodifiableListIterator<E> listIterator(int index) {
		return (UnmodifiableListIterator<E>)this.delegateList.listIterator(index);
	}

	@GwtIncompatible
	@Override
	public void forEach(Consumer<? super E> action) {
		this.delegateList.forEach(action);
	}

	@GwtIncompatible
	@Override
	int copyIntoArray(Object[] dst, int offset) {
		return this.delegateList.copyIntoArray(dst, offset);
	}

	public E get(int index) {
		return (E)this.delegateList.get(index);
	}
}
