package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry;
import java.util.Comparator;

public abstract class AbstractReference2IntSortedMap<K> extends AbstractReference2IntMap<K> implements Reference2IntSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractReference2IntSortedMap() {
	}

	@Override
	public ReferenceSortedSet<K> keySet() {
		return new AbstractReference2IntSortedMap.KeySet();
	}

	@Override
	public IntCollection values() {
		return new AbstractReference2IntSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractReferenceSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractReference2IntSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractReference2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2IntSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractReference2IntSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractReference2IntSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractReference2IntSortedMap.this.lastKey();
		}

		@Override
		public ReferenceSortedSet<K> headSet(K to) {
			return AbstractReference2IntSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ReferenceSortedSet<K> tailSet(K from) {
			return AbstractReference2IntSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ReferenceSortedSet<K> subSet(K from, K to) {
			return AbstractReference2IntSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractReference2IntSortedMap.KeySetIterator<>(AbstractReference2IntSortedMap.this.reference2IntEntrySet().iterator(new BasicEntry<>(from, 0)));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractReference2IntSortedMap.KeySetIterator<>(Reference2IntSortedMaps.fastIterator(AbstractReference2IntSortedMap.this));
		}
	}

	protected static class KeySetIterator<K> implements ObjectBidirectionalIterator<K> {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<K>> i) {
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

	protected class ValuesCollection extends AbstractIntCollection {
		@Override
		public IntIterator iterator() {
			return new AbstractReference2IntSortedMap.ValuesIterator<>(Reference2IntSortedMaps.fastIterator(AbstractReference2IntSortedMap.this));
		}

		@Override
		public boolean contains(int k) {
			return AbstractReference2IntSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractReference2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2IntSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements IntIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		@Override
		public int nextInt() {
			return ((Entry)this.i.next()).getIntValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
