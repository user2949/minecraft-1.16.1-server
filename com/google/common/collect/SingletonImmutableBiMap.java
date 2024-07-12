package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible(
	serializable = true,
	emulated = true
)
final class SingletonImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
	final transient K singleKey;
	final transient V singleValue;
	@LazyInit
	@RetainedWith
	transient ImmutableBiMap<V, K> inverse;

	SingletonImmutableBiMap(K singleKey, V singleValue) {
		CollectPreconditions.checkEntryNotNull(singleKey, singleValue);
		this.singleKey = singleKey;
		this.singleValue = singleValue;
	}

	private SingletonImmutableBiMap(K singleKey, V singleValue, ImmutableBiMap<V, K> inverse) {
		this.singleKey = singleKey;
		this.singleValue = singleValue;
		this.inverse = inverse;
	}

	@Override
	public V get(@Nullable Object key) {
		return this.singleKey.equals(key) ? this.singleValue : null;
	}

	public int size() {
		return 1;
	}

	public void forEach(BiConsumer<? super K, ? super V> action) {
		Preconditions.checkNotNull(action).accept(this.singleKey, this.singleValue);
	}

	@Override
	public boolean containsKey(@Nullable Object key) {
		return this.singleKey.equals(key);
	}

	@Override
	public boolean containsValue(@Nullable Object value) {
		return this.singleValue.equals(value);
	}

	@Override
	boolean isPartialView() {
		return false;
	}

	@Override
	ImmutableSet<Entry<K, V>> createEntrySet() {
		return ImmutableSet.of(Maps.immutableEntry(this.singleKey, this.singleValue));
	}

	@Override
	ImmutableSet<K> createKeySet() {
		return ImmutableSet.of(this.singleKey);
	}

	@Override
	public ImmutableBiMap<V, K> inverse() {
		ImmutableBiMap<V, K> result = this.inverse;
		return result == null ? (this.inverse = new SingletonImmutableBiMap<>(this.singleValue, this.singleKey, this)) : result;
	}
}
