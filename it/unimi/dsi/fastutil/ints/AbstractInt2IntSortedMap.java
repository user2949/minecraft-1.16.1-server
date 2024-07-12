package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractInt2IntSortedMap extends AbstractInt2IntMap implements Int2IntSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractInt2IntSortedMap() {
	}

	@Override
	public IntSortedSet keySet() {
		return new AbstractInt2IntSortedMap.KeySet();
	}

	@Override
	public IntCollection values() {
		return new AbstractInt2IntSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractIntSortedSet {
		@Override
		public boolean contains(int k) {
			return AbstractInt2IntSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractInt2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2IntSortedMap.this.clear();
		}

		@Override
		public IntComparator comparator() {
			return AbstractInt2IntSortedMap.this.comparator();
		}

		@Override
		public int firstInt() {
			return AbstractInt2IntSortedMap.this.firstIntKey();
		}

		@Override
		public int lastInt() {
			return AbstractInt2IntSortedMap.this.lastIntKey();
		}

		@Override
		public IntSortedSet headSet(int to) {
			return AbstractInt2IntSortedMap.this.headMap(to).keySet();
		}

		@Override
		public IntSortedSet tailSet(int from) {
			return AbstractInt2IntSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public IntSortedSet subSet(int from, int to) {
			return AbstractInt2IntSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public IntBidirectionalIterator iterator(int from) {
			return new AbstractInt2IntSortedMap.KeySetIterator(AbstractInt2IntSortedMap.this.int2IntEntrySet().iterator(new BasicEntry(from, 0)));
		}

		@Override
		public IntBidirectionalIterator iterator() {
			return new AbstractInt2IntSortedMap.KeySetIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
		}
	}

	protected static class KeySetIterator implements IntBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
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

	protected class ValuesCollection extends AbstractIntCollection {
		@Override
		public IntIterator iterator() {
			return new AbstractInt2IntSortedMap.ValuesIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
		}

		@Override
		public boolean contains(int k) {
			return AbstractInt2IntSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractInt2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2IntSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements IntIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
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
