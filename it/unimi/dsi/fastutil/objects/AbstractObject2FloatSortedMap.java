package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2FloatMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2FloatSortedMap<K> extends AbstractObject2FloatMap<K> implements Object2FloatSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2FloatSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2FloatSortedMap.KeySet();
	}

	@Override
	public FloatCollection values() {
		return new AbstractObject2FloatSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2FloatSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2FloatSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2FloatSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2FloatSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2FloatSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2FloatSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2FloatSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2FloatSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2FloatSortedMap.KeySetIterator<>(AbstractObject2FloatSortedMap.this.object2FloatEntrySet().iterator(new BasicEntry<>(from, 0.0F)));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2FloatSortedMap.KeySetIterator<>(Object2FloatSortedMaps.fastIterator(AbstractObject2FloatSortedMap.this));
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

	protected class ValuesCollection extends AbstractFloatCollection {
		@Override
		public FloatIterator iterator() {
			return new AbstractObject2FloatSortedMap.ValuesIterator<>(Object2FloatSortedMaps.fastIterator(AbstractObject2FloatSortedMap.this));
		}

		@Override
		public boolean contains(float k) {
			return AbstractObject2FloatSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2FloatSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements FloatIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		@Override
		public float nextFloat() {
			return ((Entry)this.i.next()).getFloatValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
