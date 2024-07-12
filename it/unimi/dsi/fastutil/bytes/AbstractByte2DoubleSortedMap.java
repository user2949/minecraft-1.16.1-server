package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap.Entry;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractByte2DoubleSortedMap extends AbstractByte2DoubleMap implements Byte2DoubleSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2DoubleSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2DoubleSortedMap.KeySet();
	}

	@Override
	public DoubleCollection values() {
		return new AbstractByte2DoubleSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2DoubleSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2DoubleSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2DoubleSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2DoubleSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2DoubleSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2DoubleSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2DoubleSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2DoubleSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2DoubleSortedMap.KeySetIterator(AbstractByte2DoubleSortedMap.this.byte2DoubleEntrySet().iterator(new BasicEntry(from, 0.0)));
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2DoubleSortedMap.KeySetIterator(Byte2DoubleSortedMaps.fastIterator(AbstractByte2DoubleSortedMap.this));
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

	protected class ValuesCollection extends AbstractDoubleCollection {
		@Override
		public DoubleIterator iterator() {
			return new AbstractByte2DoubleSortedMap.ValuesIterator(Byte2DoubleSortedMaps.fastIterator(AbstractByte2DoubleSortedMap.this));
		}

		@Override
		public boolean contains(double k) {
			return AbstractByte2DoubleSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2DoubleSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements DoubleIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public double nextDouble() {
			return ((Entry)this.i.next()).getDoubleValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
