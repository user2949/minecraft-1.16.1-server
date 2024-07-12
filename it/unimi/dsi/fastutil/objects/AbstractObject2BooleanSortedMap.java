package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2BooleanSortedMap<K> extends AbstractObject2BooleanMap<K> implements Object2BooleanSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2BooleanSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2BooleanSortedMap.KeySet();
	}

	@Override
	public BooleanCollection values() {
		return new AbstractObject2BooleanSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2BooleanSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2BooleanSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2BooleanSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2BooleanSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2BooleanSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2BooleanSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2BooleanSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2BooleanSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2BooleanSortedMap.KeySetIterator<>(
				AbstractObject2BooleanSortedMap.this.object2BooleanEntrySet().iterator(new BasicEntry<>(from, false))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2BooleanSortedMap.KeySetIterator<>(Object2BooleanSortedMaps.fastIterator(AbstractObject2BooleanSortedMap.this));
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

	protected class ValuesCollection extends AbstractBooleanCollection {
		@Override
		public BooleanIterator iterator() {
			return new AbstractObject2BooleanSortedMap.ValuesIterator<>(Object2BooleanSortedMaps.fastIterator(AbstractObject2BooleanSortedMap.this));
		}

		@Override
		public boolean contains(boolean k) {
			return AbstractObject2BooleanSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2BooleanSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements BooleanIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
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
