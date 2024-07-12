package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps.KeySet;
import com.google.common.collect.Multimaps.Keys;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
	private transient Collection<Entry<K, V>> entries;
	private transient Set<K> keySet;
	private transient Multiset<K> keys;
	private transient Collection<V> values;
	private transient Map<K, Collection<V>> asMap;

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean containsValue(@Nullable Object value) {
		for (Collection<V> collection : this.asMap().values()) {
			if (collection.contains(value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
		Collection<V> collection = (Collection<V>)this.asMap().get(key);
		return collection != null && collection.contains(value);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean remove(@Nullable Object key, @Nullable Object value) {
		Collection<V> collection = (Collection<V>)this.asMap().get(key);
		return collection != null && collection.remove(value);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean put(@Nullable K key, @Nullable V value) {
		return this.get(key).add(value);
	}

	@CanIgnoreReturnValue
	@Override
	public boolean putAll(@Nullable K key, Iterable<? extends V> values) {
		Preconditions.checkNotNull(values);
		if (values instanceof Collection) {
			Collection<? extends V> valueCollection = (Collection<? extends V>)values;
			return !valueCollection.isEmpty() && this.get(key).addAll(valueCollection);
		} else {
			Iterator<? extends V> valueItr = values.iterator();
			return valueItr.hasNext() && Iterators.addAll(this.get(key), valueItr);
		}
	}

	@CanIgnoreReturnValue
	@Override
	public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
		boolean changed = false;

		for (Entry<? extends K, ? extends V> entry : multimap.entries()) {
			changed |= this.put((K)entry.getKey(), (V)entry.getValue());
		}

		return changed;
	}

	@CanIgnoreReturnValue
	@Override
	public Collection<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
		Preconditions.checkNotNull(values);
		Collection<V> result = this.removeAll(key);
		this.putAll(key, values);
		return result;
	}

	@Override
	public Collection<Entry<K, V>> entries() {
		Collection<Entry<K, V>> result = this.entries;
		return result == null ? (this.entries = this.createEntries()) : result;
	}

	Collection<Entry<K, V>> createEntries() {
		return (Collection<Entry<K, V>>)(this instanceof SetMultimap ? new AbstractMultimap.EntrySet() : new AbstractMultimap.Entries());
	}

	abstract Iterator<Entry<K, V>> entryIterator();

	Spliterator<Entry<K, V>> entrySpliterator() {
		return Spliterators.spliterator(this.entryIterator(), (long)this.size(), this instanceof SetMultimap ? 1 : 0);
	}

	@Override
	public Set<K> keySet() {
		Set<K> result = this.keySet;
		return result == null ? (this.keySet = this.createKeySet()) : result;
	}

	Set<K> createKeySet() {
		return new KeySet(this.asMap());
	}

	@Override
	public Multiset<K> keys() {
		Multiset<K> result = this.keys;
		return result == null ? (this.keys = this.createKeys()) : result;
	}

	Multiset<K> createKeys() {
		return new Keys<>(this);
	}

	@Override
	public Collection<V> values() {
		Collection<V> result = this.values;
		return result == null ? (this.values = this.createValues()) : result;
	}

	Collection<V> createValues() {
		return new AbstractMultimap.Values();
	}

	Iterator<V> valueIterator() {
		return Maps.valueIterator(this.entries().iterator());
	}

	Spliterator<V> valueSpliterator() {
		return Spliterators.spliterator(this.valueIterator(), (long)this.size(), 0);
	}

	@Override
	public Map<K, Collection<V>> asMap() {
		Map<K, Collection<V>> result = this.asMap;
		return result == null ? (this.asMap = this.createAsMap()) : result;
	}

	abstract Map<K, Collection<V>> createAsMap();

	@Override
	public boolean equals(@Nullable Object object) {
		return Multimaps.equalsImpl(this, object);
	}

	@Override
	public int hashCode() {
		return this.asMap().hashCode();
	}

	public String toString() {
		return this.asMap().toString();
	}

	private class Entries extends Multimaps.Entries<K, V> {
		private Entries() {
		}

		@Override
		Multimap<K, V> multimap() {
			return AbstractMultimap.this;
		}

		public Iterator<Entry<K, V>> iterator() {
			return AbstractMultimap.this.entryIterator();
		}

		public Spliterator<Entry<K, V>> spliterator() {
			return AbstractMultimap.this.entrySpliterator();
		}
	}

	private class EntrySet extends AbstractMultimap<K, V>.Entries implements Set<Entry<K, V>> {
		private EntrySet() {
		}

		public int hashCode() {
			return Sets.hashCodeImpl(this);
		}

		public boolean equals(@Nullable Object obj) {
			return Sets.equalsImpl(this, obj);
		}
	}

	class Values extends AbstractCollection<V> {
		public Iterator<V> iterator() {
			return AbstractMultimap.this.valueIterator();
		}

		public Spliterator<V> spliterator() {
			return AbstractMultimap.this.valueSpliterator();
		}

		public int size() {
			return AbstractMultimap.this.size();
		}

		public boolean contains(@Nullable Object o) {
			return AbstractMultimap.this.containsValue(o);
		}

		public void clear() {
			AbstractMultimap.this.clear();
		}
	}
}
