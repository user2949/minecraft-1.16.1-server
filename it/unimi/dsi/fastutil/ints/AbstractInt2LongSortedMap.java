package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractInt2LongSortedMap extends AbstractInt2LongMap implements Int2LongSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractInt2LongSortedMap() {
	}

	@Override
	public IntSortedSet keySet() {
		return new AbstractInt2LongSortedMap.KeySet();
	}

	@Override
	public LongCollection values() {
		return new AbstractInt2LongSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractIntSortedSet {
		@Override
		public boolean contains(int k) {
			return AbstractInt2LongSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractInt2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2LongSortedMap.this.clear();
		}

		@Override
		public IntComparator comparator() {
			return AbstractInt2LongSortedMap.this.comparator();
		}

		@Override
		public int firstInt() {
			return AbstractInt2LongSortedMap.this.firstIntKey();
		}

		@Override
		public int lastInt() {
			return AbstractInt2LongSortedMap.this.lastIntKey();
		}

		@Override
		public IntSortedSet headSet(int to) {
			return AbstractInt2LongSortedMap.this.headMap(to).keySet();
		}

		@Override
		public IntSortedSet tailSet(int from) {
			return AbstractInt2LongSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public IntSortedSet subSet(int from, int to) {
			return AbstractInt2LongSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public IntBidirectionalIterator iterator(int from) {
			return new AbstractInt2LongSortedMap.KeySetIterator(AbstractInt2LongSortedMap.this.int2LongEntrySet().iterator(new BasicEntry(from, 0L)));
		}

		@Override
		public IntBidirectionalIterator iterator() {
			return new AbstractInt2LongSortedMap.KeySetIterator(Int2LongSortedMaps.fastIterator(AbstractInt2LongSortedMap.this));
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

	protected class ValuesCollection extends AbstractLongCollection {
		@Override
		public LongIterator iterator() {
			return new AbstractInt2LongSortedMap.ValuesIterator(Int2LongSortedMaps.fastIterator(AbstractInt2LongSortedMap.this));
		}

		@Override
		public boolean contains(long k) {
			return AbstractInt2LongSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractInt2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2LongSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements LongIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public long nextLong() {
			return ((Entry)this.i.next()).getLongValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
