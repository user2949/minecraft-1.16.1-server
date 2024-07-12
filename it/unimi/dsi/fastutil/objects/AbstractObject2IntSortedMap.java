package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2IntSortedMap<K> extends AbstractObject2IntMap<K> implements Object2IntSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2IntSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2IntSortedMap.KeySet();
	}

	@Override
	public IntCollection values() {
		return new AbstractObject2IntSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2IntSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2IntSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2IntSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2IntSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2IntSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2IntSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2IntSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2IntSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2IntSortedMap.KeySetIterator<>(AbstractObject2IntSortedMap.this.object2IntEntrySet().iterator(new BasicEntry<>(from, 0)));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2IntSortedMap.KeySetIterator<>(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
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

	protected class ValuesCollection extends AbstractIntCollection {
		@Override
		public IntIterator iterator() {
			return new AbstractObject2IntSortedMap.ValuesIterator<>(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
		}

		@Override
		public boolean contains(int k) {
			return AbstractObject2IntSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2IntSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements IntIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
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
