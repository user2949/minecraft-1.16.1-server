package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2ObjectSortedMap<K, V> extends AbstractObject2ObjectMap<K, V> implements Object2ObjectSortedMap<K, V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2ObjectSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2ObjectSortedMap.KeySet();
	}

	@Override
	public ObjectCollection<V> values() {
		return new AbstractObject2ObjectSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2ObjectSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ObjectSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2ObjectSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2ObjectSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2ObjectSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2ObjectSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2ObjectSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2ObjectSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2ObjectSortedMap.KeySetIterator<>(
				AbstractObject2ObjectSortedMap.this.object2ObjectEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2ObjectSortedMap.KeySetIterator<>(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
		}
	}

	protected static class KeySetIterator<K, V> implements ObjectBidirectionalIterator<K> {
		protected final ObjectBidirectionalIterator<Entry<K, V>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<K, V>> i) {
			this.i = i;
		}

		public K next() {
			return (K)((Entry)this.i.next()).getKey();
		}

		@Override
		public K previous() {
			return (K)this.i.previous().getKey();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return this.i.hasPrevious();
		}
	}

	protected class ValuesCollection extends AbstractObjectCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractObject2ObjectSortedMap.ValuesIterator<>(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractObject2ObjectSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ObjectSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K, V> implements ObjectIterator<V> {
		protected final ObjectBidirectionalIterator<Entry<K, V>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K, V>> i) {
			this.i = i;
		}

		public V next() {
			return (V)((Entry)this.i.next()).getValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
