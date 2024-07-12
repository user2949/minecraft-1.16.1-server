package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap.Entry;

public abstract class AbstractShort2ByteSortedMap extends AbstractShort2ByteMap implements Short2ByteSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractShort2ByteSortedMap() {
	}

	@Override
	public ShortSortedSet keySet() {
		return new AbstractShort2ByteSortedMap.KeySet();
	}

	@Override
	public ByteCollection values() {
		return new AbstractShort2ByteSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractShortSortedSet {
		@Override
		public boolean contains(short k) {
			return AbstractShort2ByteSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractShort2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ByteSortedMap.this.clear();
		}

		@Override
		public ShortComparator comparator() {
			return AbstractShort2ByteSortedMap.this.comparator();
		}

		@Override
		public short firstShort() {
			return AbstractShort2ByteSortedMap.this.firstShortKey();
		}

		@Override
		public short lastShort() {
			return AbstractShort2ByteSortedMap.this.lastShortKey();
		}

		@Override
		public ShortSortedSet headSet(short to) {
			return AbstractShort2ByteSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ShortSortedSet tailSet(short from) {
			return AbstractShort2ByteSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ShortSortedSet subSet(short from, short to) {
			return AbstractShort2ByteSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return new AbstractShort2ByteSortedMap.KeySetIterator(AbstractShort2ByteSortedMap.this.short2ByteEntrySet().iterator(new BasicEntry(from, (byte)0)));
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return new AbstractShort2ByteSortedMap.KeySetIterator(Short2ByteSortedMaps.fastIterator(AbstractShort2ByteSortedMap.this));
		}
	}

	protected static class KeySetIterator implements ShortBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public short nextShort() {
			return ((Entry)this.i.next()).getShortKey();
		}

		@Override
		public short previousShort() {
			return this.i.previous().getShortKey();
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
			return new AbstractShort2ByteSortedMap.ValuesIterator(Short2ByteSortedMaps.fastIterator(AbstractShort2ByteSortedMap.this));
		}

		@Override
		public boolean contains(byte k) {
			return AbstractShort2ByteSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractShort2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ByteSortedMap.this.clear();
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
