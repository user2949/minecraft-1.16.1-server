package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Comparator;

public abstract class AbstractObject2ShortSortedMap<K> extends AbstractObject2ShortMap<K> implements Object2ShortSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2ShortSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2ShortSortedMap.KeySet();
	}

	@Override
	public ShortCollection values() {
		return new AbstractObject2ShortSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2ShortSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2ShortSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ShortSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2ShortSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2ShortSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2ShortSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2ShortSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2ShortSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2ShortSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2ShortSortedMap.KeySetIterator<>(
				AbstractObject2ShortSortedMap.this.object2ShortEntrySet().iterator(new BasicEntry<>(from, (short)0))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2ShortSortedMap.KeySetIterator<>(Object2ShortSortedMaps.fastIterator(AbstractObject2ShortSortedMap.this));
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

	protected class ValuesCollection extends AbstractShortCollection {
		@Override
		public ShortIterator iterator() {
			return new AbstractObject2ShortSortedMap.ValuesIterator<>(Object2ShortSortedMaps.fastIterator(AbstractObject2ShortSortedMap.this));
		}

		@Override
		public boolean contains(short k) {
			return AbstractObject2ShortSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2ShortSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2ShortSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements ShortIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		@Override
		public short nextShort() {
			return ((Entry)this.i.next()).getShortValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
