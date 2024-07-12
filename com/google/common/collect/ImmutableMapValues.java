package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Spliterator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
final class ImmutableMapValues<K, V> extends ImmutableCollection<V> {
	@Weak
	private final ImmutableMap<K, V> map;

	ImmutableMapValues(ImmutableMap<K, V> map) {
		this.map = map;
	}

	public int size() {
		return this.map.size();
	}

	@Override
	public UnmodifiableIterator<V> iterator() {
		return new UnmodifiableIterator<V>() {
			final UnmodifiableIterator<Entry<K, V>> entryItr = ImmutableMapValues.this.map.entrySet().iterator();

			public boolean hasNext() {
				return this.entryItr.hasNext();
			}

			public V next() {
				return (V)((Entry)this.entryItr.next()).getValue();
			}
		};
	}

	@Override
	public Spliterator<V> spliterator() {
		return CollectSpliterators.map(this.map.entrySet().spliterator(), Entry::getValue);
	}

	@Override
	public boolean contains(@Nullable Object object) {
		return object != null && Iterators.contains(this.iterator(), object);
	}

	@Override
	boolean isPartialView() {
		return true;
	}

	@Override
	public ImmutableList<V> asList() {
		final ImmutableList<Entry<K, V>> entryList = this.map.entrySet().asList();
		return new ImmutableAsList<V>() {
			public V get(int index) {
				return (V)((Entry)entryList.get(index)).getValue();
			}

			@Override
			ImmutableCollection<V> delegateCollection() {
				return ImmutableMapValues.this;
			}
		};
	}

	@GwtIncompatible
	public void forEach(Consumer<? super V> action) {
		Preconditions.checkNotNull(action);
		this.map.forEach((k, v) -> action.accept(v));
	}

	@GwtIncompatible
	@Override
	Object writeReplace() {
		return new ImmutableMapValues.SerializedForm<>(this.map);
	}

	@GwtIncompatible
	private static class SerializedForm<V> implements Serializable {
		final ImmutableMap<?, V> map;
		private static final long serialVersionUID = 0L;

		SerializedForm(ImmutableMap<?, V> map) {
			this.map = map;
		}

		Object readResolve() {
			return this.map.values();
		}
	}
}
