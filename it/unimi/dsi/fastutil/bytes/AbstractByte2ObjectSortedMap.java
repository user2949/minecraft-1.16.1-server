package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public abstract class AbstractByte2ObjectSortedMap<V> extends AbstractByte2ObjectMap<V> implements Byte2ObjectSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractByte2ObjectSortedMap() {
	}

	@Override
	public ByteSortedSet keySet() {
		return new AbstractByte2ObjectSortedMap.KeySet();
	}

	@Override
	public ObjectCollection<V> values() {
		return new AbstractByte2ObjectSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractByteSortedSet {
		@Override
		public boolean contains(byte k) {
			return AbstractByte2ObjectSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractByte2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2ObjectSortedMap.this.clear();
		}

		@Override
		public ByteComparator comparator() {
			return AbstractByte2ObjectSortedMap.this.comparator();
		}

		@Override
		public byte firstByte() {
			return AbstractByte2ObjectSortedMap.this.firstByteKey();
		}

		@Override
		public byte lastByte() {
			return AbstractByte2ObjectSortedMap.this.lastByteKey();
		}

		@Override
		public ByteSortedSet headSet(byte to) {
			return AbstractByte2ObjectSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ByteSortedSet tailSet(byte from) {
			return AbstractByte2ObjectSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ByteSortedSet subSet(byte from, byte to) {
			return AbstractByte2ObjectSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ByteBidirectionalIterator iterator(byte from) {
			return new AbstractByte2ObjectSortedMap.KeySetIterator<>(AbstractByte2ObjectSortedMap.this.byte2ObjectEntrySet().iterator(new BasicEntry<>(from, null)));
		}

		@Override
		public ByteBidirectionalIterator iterator() {
			return new AbstractByte2ObjectSortedMap.KeySetIterator<>(Byte2ObjectSortedMaps.fastIterator(AbstractByte2ObjectSortedMap.this));
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

	protected class ValuesCollection extends AbstractObjectCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractByte2ObjectSortedMap.ValuesIterator<>(Byte2ObjectSortedMaps.fastIterator(AbstractByte2ObjectSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractByte2ObjectSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractByte2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractByte2ObjectSortedMap.this.clear();
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
