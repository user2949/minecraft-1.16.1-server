package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.IteratorBasedImmutableMap;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Spliterator;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible(
	serializable = true,
	emulated = true
)
final class ImmutableEnumMap<K extends Enum<K>, V> extends IteratorBasedImmutableMap<K, V> {
	private final transient EnumMap<K, V> delegate;

	static <K extends Enum<K>, V> ImmutableMap<K, V> asImmutable(EnumMap<K, V> map) {
		switch (map.size()) {
			case 0:
				return ImmutableMap.of();
			case 1:
				Entry<K, V> entry = Iterables.getOnlyElement(map.entrySet());
				return ImmutableMap.of((K)entry.getKey(), (V)entry.getValue());
			default:
				return new ImmutableEnumMap<>(map);
		}
	}

	private ImmutableEnumMap(EnumMap<K, V> delegate) {
		this.delegate = delegate;
		Preconditions.checkArgument(!delegate.isEmpty());
	}

	@Override
	UnmodifiableIterator<K> keyIterator() {
		return Iterators.unmodifiableIterator(this.delegate.keySet().iterator());
	}

	@Override
	Spliterator<K> keySpliterator() {
		return this.delegate.keySet().spliterator();
	}

	public int size() {
		return this.delegate.size();
	}

	@Override
	public boolean containsKey(@Nullable Object key) {
		return this.delegate.containsKey(key);
	}

	@Override
	public V get(Object key) {
		return (V)this.delegate.get(key);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else {
			if (object instanceof ImmutableEnumMap) {
				object = ((ImmutableEnumMap)object).delegate;
			}

			return this.delegate.equals(object);
		}
	}

	@Override
	UnmodifiableIterator<Entry<K, V>> entryIterator() {
		return Maps.unmodifiableEntryIterator(this.delegate.entrySet().iterator());
	}

	@Override
	Spliterator<Entry<K, V>> entrySpliterator() {
		return CollectSpliterators.map(this.delegate.entrySet().spliterator(), Maps::unmodifiableEntry);
	}

	public void forEach(BiConsumer<? super K, ? super V> action) {
		this.delegate.forEach(action);
	}

	@Override
	boolean isPartialView() {
		return false;
	}

	@Override
	Object writeReplace() {
		return new ImmutableEnumMap.EnumSerializedForm(this.delegate);
	}

	private static class EnumSerializedForm<K extends Enum<K>, V> implements Serializable {
		final EnumMap<K, V> delegate;
		private static final long serialVersionUID = 0L;

		EnumSerializedForm(EnumMap<K, V> delegate) {
			this.delegate = delegate;
		}

		Object readResolve() {
			return new ImmutableEnumMap(this.delegate);
		}
	}
}