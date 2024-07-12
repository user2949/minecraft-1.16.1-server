package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2ReferenceSortedMap<K, V> extends AbstractObject2ReferenceMap<K, V> implements Object2ReferenceSortedMap<K, V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2ReferenceSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2ReferenceSortedMap.KeySet();
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractObject2ReferenceSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2ReferenceSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ReferenceSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2ReferenceSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2ReferenceSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2ReferenceSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2ReferenceSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2ReferenceSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2ReferenceSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2ReferenceSortedMap.KeySetIterator<>(
				AbstractObject2ReferenceSortedMap.this.object2ReferenceEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2ReferenceSortedMap.KeySetIterator<>(Object2ReferenceSortedMaps.fastIterator(AbstractObject2ReferenceSortedMap.this));
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

	protected class ValuesCollection extends AbstractReferenceCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractObject2ReferenceSortedMap.ValuesIterator<>(Object2ReferenceSortedMaps.fastIterator(AbstractObject2ReferenceSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractObject2ReferenceSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ReferenceSortedMap.this.clear();
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
