package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap.Entry;
import java.util.Comparator;

public abstract class AbstractReference2ByteSortedMap<K> extends AbstractReference2ByteMap<K> implements Reference2ByteSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractReference2ByteSortedMap() {
	}

	@Override
	public ReferenceSortedSet<K> keySet() {
		return new AbstractReference2ByteSortedMap.KeySet();
	}

	@Override
	public ByteCollection values() {
		return new AbstractReference2ByteSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractReferenceSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractReference2ByteSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractReference2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2ByteSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractReference2ByteSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractReference2ByteSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractReference2ByteSortedMap.this.lastKey();
		}

		@Override
		public ReferenceSortedSet<K> headSet(K to) {
			return AbstractReference2ByteSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ReferenceSortedSet<K> tailSet(K from) {
			return AbstractReference2ByteSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ReferenceSortedSet<K> subSet(K from, K to) {
			return AbstractReference2ByteSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractReference2ByteSortedMap.KeySetIterator<>(
				AbstractReference2ByteSortedMap.this.reference2ByteEntrySet().iterator(new BasicEntry<>(from, (byte)0))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractReference2ByteSortedMap.KeySetIterator<>(Reference2ByteSortedMaps.fastIterator(AbstractReference2ByteSortedMap.this));
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
			return new AbstractReference2ByteSortedMap.ValuesIterator<>(Reference2ByteSortedMaps.fastIterator(AbstractReference2ByteSortedMap.this));
		}

		@Override
		public boolean contains(byte k) {
			return AbstractReference2ByteSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractReference2ByteSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2ByteSortedMap.this.clear();
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
