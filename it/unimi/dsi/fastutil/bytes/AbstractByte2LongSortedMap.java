package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractByte2LongSortedMap extends AbstractByte2LongMap implements Byte2LongSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2LongSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2LongSortedMap.KeySet();
	}

	@Override
	public LongCollection values() {
		return new AbstractByte2LongSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2LongSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2LongSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2LongSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2LongSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2LongSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2LongSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2LongSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2LongSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2LongSortedMap.KeySetIterator(AbstractByte2LongSortedMap.this.byte2LongEntrySet().iterator(new BasicEntry(from, 0L)));
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2LongSortedMap.KeySetIterator(Byte2LongSortedMaps.fastIterator(AbstractByte2LongSortedMap.this));
		}
	}

	protected static class KeySetIterator implements ByteBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public byte nextByte() {
			return ((Entry)this.i.next()).getByteKey();
		}

		@Override
		public byte previousByte() {
			return this.i.previous().getByteKey();
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
			return new AbstractByte2LongSortedMap.ValuesIterator(Byte2LongSortedMaps.fastIterator(AbstractByte2LongSortedMap.this));
		}

		@Override
		public boolean contains(long k) {
			return AbstractByte2LongSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2LongSortedMap.this.clear();
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
