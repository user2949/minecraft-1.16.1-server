package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;

public abstract class AbstractByte2ReferenceSortedMap<V> extends AbstractByte2ReferenceMap<V> implements Byte2ReferenceSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2ReferenceSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2ReferenceSortedMap.KeySet();
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractByte2ReferenceSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2ReferenceSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2ReferenceSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2ReferenceSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2ReferenceSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2ReferenceSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2ReferenceSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2ReferenceSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2ReferenceSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2ReferenceSortedMap.KeySetIterator<>(
				AbstractByte2ReferenceSortedMap.this.byte2ReferenceEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2ReferenceSortedMap.KeySetIterator<>(Byte2ReferenceSortedMaps.fastIterator(AbstractByte2ReferenceSortedMap.this));
		}
	}

	protected static class KeySetIterator<V> implements ByteBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<V>> i) {
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

	protected class ValuesCollection extends AbstractReferenceCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractByte2ReferenceSortedMap.ValuesIterator<>(Byte2ReferenceSortedMaps.fastIterator(AbstractByte2ReferenceSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractByte2ReferenceSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2ReferenceSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<V> implements ObjectIterator<V> {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<V>> i) {
			this.i = i;
		}

		public V next() {
			return (V)((Entry)this.i.next()).getValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
