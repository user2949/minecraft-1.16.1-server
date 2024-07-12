package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E> {
	protected ForwardingSet() {
	}

	protected abstract Set<E> delegate();

	public boolean equals(@Nullable Object object) {
		return object == this || this.delegate().equals(object);
	}

	public int hashCode() {
		return this.delegate().hashCode();
	}

	@Override
	protected boolean standardRemoveAll(Collection<?> collection) {
		return Sets.removeAllImpl(this, Preconditions.checkNotNull(collection));
	}

	protected boolean standardEquals(@Nullable Object object) {
		return Sets.equalsImpl(this, object);
	}

	protected int standardHashCode() {
		return Sets.hashCodeImpl(this);
	}
}
