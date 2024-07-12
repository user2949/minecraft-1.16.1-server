package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractByte2BooleanSortedMap extends AbstractByte2BooleanMap implements Byte2BooleanSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2BooleanSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2BooleanSortedMap.KeySet();
	}

	@Override
	public BooleanCollection values() {
		return new AbstractByte2BooleanSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2BooleanSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2BooleanSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2BooleanSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2BooleanSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2BooleanSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2BooleanSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2BooleanSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2BooleanSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2BooleanSortedMap.KeySetIterator(AbstractByte2BooleanSortedMap.this.byte2BooleanEntrySet().iterator(new BasicEntry(from, false)));
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2BooleanSortedMap.KeySetIterator(Byte2BooleanSortedMaps.fastIterator(AbstractByte2BooleanSortedMap.this));
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

	protected class ValuesCollection extends AbstractBooleanCollection {
		@Override
		public BooleanIterator iterator() {
			return new AbstractByte2BooleanSortedMap.ValuesIterator(Byte2BooleanSortedMaps.fastIterator(AbstractByte2BooleanSortedMap.this));
		}

		@Override
		public boolean contains(boolean k) {
			return AbstractByte2BooleanSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2BooleanSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements BooleanIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public boolean nextBoolean() {
			return ((Entry)this.i.next()).getBooleanValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
