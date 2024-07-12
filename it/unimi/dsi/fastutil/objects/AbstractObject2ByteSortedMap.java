package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ByteMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2ByteSortedMap<K> extends AbstractObject2ByteMap<K> implements Object2ByteSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2ByteSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2ByteSortedMap.KeySet();
	}

	@Override
	public ByteCollection values() {
		return new AbstractObject2ByteSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2ByteSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ByteSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2ByteSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2ByteSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2ByteSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2ByteSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2ByteSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2ByteSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2ByteSortedMap.KeySetIterator<>(AbstractObject2ByteSortedMap.this.object2ByteEntrySet().iterator(new BasicEntry<>(from, (byte)0)));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2ByteSortedMap.KeySetIterator<>(Object2ByteSortedMaps.fastIterator(AbstractObject2ByteSortedMap.this));
		}
	}

	protected static class KeySetIterator<K> implements ObjectBidirectionalIterator<K> {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		public K next() {
			return (K)((Entry)this.i.next()).getKey();
		}

		@Override
		public K previous() {
			return (K)this.i.previous().getKey();
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
			return new AbstractObject2ByteSortedMap.ValuesIterator<>(Object2ByteSortedMaps.fastIterator(AbstractObject2ByteSortedMap.this));
		}

		@Override
		public boolean contains(byte k) {
			return AbstractObject2ByteSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ByteSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements ByteIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
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
