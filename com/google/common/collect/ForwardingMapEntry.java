package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMapEntry<K, V> extends ForwardingObject implements Entry<K, V> {
	protected ForwardingMapEntry() {
	}

	protected abstract Entry<K, V> delegate();

	public K getKey() {
		return (K)this.delegate().getKey();
	}

	public V getValue() {
		return (V)this.delegate().getValue();
	}

	public V setValue(V value) {
		return (V)this.delegate().setValue(value);
	}

	public boolean equals(@Nullable Object object) {
		return this.delegate().equals(object);
	}

	public int hashCode() {
		return this.delegate().hashCode();
	}

	protected boolean standardEquals(@Nullable Object object) {
		if (!(object instanceof Entry)) {
			return false;
		} else {
			Entry<?, ?> that = (Entry<?, ?>)object;
			return Objects.equal(this.getKey(), that.getKey()) && Objects.equal(this.getValue(), that.getValue());
		}
	}

	protected int standardHashCode() {
		K k = this.getKey();
		V v = this.getValue();
		return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
	}

	@Beta
	protected String standardToString() {
		return this.getKey() + "=" + this.getValue();
	}
}
