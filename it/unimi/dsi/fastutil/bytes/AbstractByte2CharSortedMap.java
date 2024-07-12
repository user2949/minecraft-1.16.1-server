package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap.Entry;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractByte2CharSortedMap extends AbstractByte2CharMap implements Byte2CharSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2CharSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2CharSortedMap.KeySet();
	}

	@Override
	public CharCollection values() {
		return new AbstractByte2CharSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2CharSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2CharSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2CharSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2CharSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2CharSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2CharSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2CharSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2CharSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2CharSortedMap.KeySetIterator(AbstractByte2CharSortedMap.this.byte2CharEntrySet().iterator(new BasicEntry(from, '\u0000')));
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2CharSortedMap.KeySetIterator(Byte2CharSortedMaps.fastIterator(AbstractByte2CharSortedMap.this));
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

	protected class ValuesCollection extends AbstractCharCollection {
		@Override
		public CharIterator iterator() {
			return new AbstractByte2CharSortedMap.ValuesIterator(Byte2CharSortedMaps.fastIterator(AbstractByte2CharSortedMap.this));
		}

		@Override
		public boolean contains(char k) {
			return AbstractByte2CharSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2CharSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements CharIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public char nextChar() {
			return ((Entry)this.i.next()).getCharValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
