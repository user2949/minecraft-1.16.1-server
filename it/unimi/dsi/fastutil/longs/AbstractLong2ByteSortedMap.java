package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractLong2ByteSortedMap extends AbstractLong2ByteMap implements Long2ByteSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractLong2ByteSortedMap() {
	}

	@Override
	public LongSortedSet keySet() {
		return new AbstractLong2ByteSortedMap.KeySet();
	}

	@Override
	public ByteCollection values() {
		return new AbstractLong2ByteSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractLongSortedSet {
		@Override
		public boolean contains(long k) {
			return AbstractLong2ByteSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractLong2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractLong2ByteSortedMap.this.clear();
		}

		@Override
		public LongComparator comparator() {
			return AbstractLong2ByteSortedMap.this.comparator();
		}

		@Override
		public long firstLong() {
			return AbstractLong2ByteSortedMap.this.firstLongKey();
		}

		@Override
		public long lastLong() {
			return AbstractLong2ByteSortedMap.this.lastLongKey();
		}

		@Override
		public LongSortedSet headSet(long to) {
			return AbstractLong2ByteSortedMap.this.headMap(to).keySet();
		}

		@Override
		public LongSortedSet tailSet(long from) {
			return AbstractLong2ByteSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public LongSortedSet subSet(long from, long to) {
			return AbstractLong2ByteSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public LongBidirectionalIterator iterator(long from) {
			return new AbstractLong2ByteSortedMap.KeySetIterator(AbstractLong2ByteSortedMap.this.long2ByteEntrySet().iterator(new BasicEntry(from, (byte)0)));
		}

		@Override
		public LongBidirectionalIterator iterator() {
			return new AbstractLong2ByteSortedMap.KeySetIterator(Long2ByteSortedMaps.fastIterator(AbstractLong2ByteSortedMap.this));
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

	protected class ValuesCollection extends AbstractByteCollection {
		@Override
		public ByteIterator iterator() {
			return new AbstractLong2ByteSortedMap.ValuesIterator(Long2ByteSortedMaps.fastIterator(AbstractLong2ByteSortedMap.this));
		}

		@Override
		public boolean contains(byte k) {
			return AbstractLong2ByteSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractLong2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractLong2ByteSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements ByteIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public byte nextByte() {
			return ((Entry)this.i.next()).getByteValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
