package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet.Indexed;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Spliterator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
final class ImmutableMapKeySet<K, V> extends Indexed<K> {
	@Weak
	private final ImmutableMap<K, V> map;

	ImmutableMapKeySet(ImmutableMap<K, V> map) {
		this.map = map;
	}

	public int size() {
		return this.map.size();
	}

	@Override
	public UnmodifiableIterator<K> iterator() {
		return this.map.keyIterator();
	}

	@Override
	public Spliterator<K> spliterator() {
		return this.map.keySpliterator();
	}

	@Override
	public boolean contains(@Nullable Object object) {
		return this.map.containsKey(object);
	}

	@Override
	K get(int index) {
		return (K)((Entry)this.map.entrySet().asList().get(index)).getKey();
	}

	@Override
	public void forEach(Consumer<? super K> action) {
		Preconditions.checkNotNull(action);
		this.map.forEach((k, v) -> action.accept(k));
	}

	@Override
	boolean isPartialView() {
		return true;
	}

	@GwtIncompatible
	@Override
	Object writeReplace() {
		return new ImmutableMapKeySet.KeySetSerializedForm<>(this.map);
	}

	@GwtIncompatible
	private static class KeySetSerializedForm<K> implements Serializable {
		final ImmutableMap<K, ?> map;
		private static final long serialVersionUID = 0L;

		KeySetSerializedForm(ImmutableMap<K, ?> map) {
			this.map = map;
		}

		Object readResolve() {
			return this.map.keySet();
		}
	}
}
