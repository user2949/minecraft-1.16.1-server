package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractLong2IntSortedMap extends AbstractLong2IntMap implements Long2IntSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractLong2IntSortedMap() {
	}

	@Override
	public LongSortedSet keySet() {
		return new AbstractLong2IntSortedMap.KeySet();
	}

	@Override
	public IntCollection values() {
		return new AbstractLong2IntSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractLongSortedSet {
		@Override
		public boolean contains(long k) {
			return AbstractLong2IntSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractLong2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractLong2IntSortedMap.this.clear();
		}

		@Override
		public LongComparator comparator() {
			return AbstractLong2IntSortedMap.this.comparator();
		}

		@Override
		public long firstLong() {
			return AbstractLong2IntSortedMap.this.firstLongKey();
		}

		@Override
		public long lastLong() {
			return AbstractLong2IntSortedMap.this.lastLongKey();
		}

		@Override
		public LongSortedSet headSet(long to) {
			return AbstractLong2IntSortedMap.this.headMap(to).keySet();
		}

		@Override
		public LongSortedSet tailSet(long from) {
			return AbstractLong2IntSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public LongSortedSet subSet(long from, long to) {
			return AbstractLong2IntSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public LongBidirectionalIterator iterator(long from) {
			return new AbstractLong2IntSortedMap.KeySetIterator(AbstractLong2IntSortedMap.this.long2IntEntrySet().iterator(new BasicEntry(from, 0)));
		}

		@Override
		public LongBidirectionalIterator iterator() {
			return new AbstractLong2IntSortedMap.KeySetIterator(Long2IntSortedMaps.fastIterator(AbstractLong2IntSortedMap.this));
		}
	}

	protected static class KeySetIterator implements LongBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public long nextLong() {
			return ((Entry)this.i.next()).getLongKey();
		}

		@Override
		public long previousLong() {
			return this.i.previous().getLongKey();
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
			return new AbstractLong2IntSortedMap.ValuesIterator(Long2IntSortedMaps.fastIterator(AbstractLong2IntSortedMap.this));
		}

		@Override
		public boolean contains(int k) {
			return AbstractLong2IntSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractLong2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractLong2IntSortedMap.this.clear();
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
