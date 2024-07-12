package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractByte2IntSortedMap extends AbstractByte2IntMap implements Byte2IntSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2IntSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2IntSortedMap.KeySet();
	}

	@Override
	public IntCollection values() {
		return new AbstractByte2IntSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2IntSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2IntSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2IntSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2IntSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2IntSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2IntSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2IntSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2IntSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2IntSortedMap.KeySetIterator(AbstractByte2IntSortedMap.this.byte2IntEntrySet().iterator(new BasicEntry(from, 0)));
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2IntSortedMap.KeySetIterator(Byte2IntSortedMaps.fastIterator(AbstractByte2IntSortedMap.this));
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

	protected class ValuesCollection extends AbstractIntCollection {
		@Override
		public IntIterator iterator() {
			return new AbstractByte2IntSortedMap.ValuesIterator(Byte2IntSortedMaps.fastIterator(AbstractByte2IntSortedMap.this));
		}

		@Override
		public boolean contains(int k) {
			return AbstractByte2IntSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2IntSortedMap.this.clear();
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
