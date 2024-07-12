package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;

public abstract class AbstractInt2ReferenceSortedMap<V> extends AbstractInt2ReferenceMap<V> implements Int2ReferenceSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractInt2ReferenceSortedMap() {
	}

	@Override
	public IntSortedSet keySet() {
		return new AbstractInt2ReferenceSortedMap.KeySet();
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractInt2ReferenceSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractIntSortedSet {
		@Override
		public boolean contains(int k) {
			return AbstractInt2ReferenceSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractInt2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2ReferenceSortedMap.this.clear();
		}

		@Override
		public IntComparator comparator() {
			return AbstractInt2ReferenceSortedMap.this.comparator();
		}

		@Override
		public int firstInt() {
			return AbstractInt2ReferenceSortedMap.this.firstIntKey();
		}

		@Override
		public int lastInt() {
			return AbstractInt2ReferenceSortedMap.this.lastIntKey();
		}

		@Override
		public IntSortedSet headSet(int to) {
			return AbstractInt2ReferenceSortedMap.this.headMap(to).keySet();
		}

		@Override
		public IntSortedSet tailSet(int from) {
			return AbstractInt2ReferenceSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public IntSortedSet subSet(int from, int to) {
			return AbstractInt2ReferenceSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public IntBidirectionalIterator iterator(int from) {
			return new AbstractInt2ReferenceSortedMap.KeySetIterator<>(
				AbstractInt2ReferenceSortedMap.this.int2ReferenceEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public IntBidirectionalIterator iterator() {
			return new AbstractInt2ReferenceSortedMap.KeySetIterator<>(Int2ReferenceSortedMaps.fastIterator(AbstractInt2ReferenceSortedMap.this));
		}
	}

	protected static class KeySetIterator<V> implements IntBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<V>> i) {
			this.i = i;
		}

		@Override
		public int nextInt() {
			return ((Entry)this.i.next()).getIntKey();
		}

		@Override
		public int previousInt() {
			return this.i.previous().getIntKey();
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
			return new AbstractInt2ReferenceSortedMap.ValuesIterator<>(Int2ReferenceSortedMaps.fastIterator(AbstractInt2ReferenceSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractInt2ReferenceSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractInt2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2ReferenceSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<V> implements ObjectIterator<V> {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<V>> i) {
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
