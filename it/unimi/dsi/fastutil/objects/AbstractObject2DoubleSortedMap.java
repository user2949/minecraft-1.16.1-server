package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2DoubleSortedMap<K> extends AbstractObject2DoubleMap<K> implements Object2DoubleSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2DoubleSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2DoubleSortedMap.KeySet();
	}

	@Override
	public DoubleCollection values() {
		return new AbstractObject2DoubleSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2DoubleSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2DoubleSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2DoubleSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2DoubleSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2DoubleSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2DoubleSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2DoubleSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2DoubleSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2DoubleSortedMap.KeySetIterator<>(AbstractObject2DoubleSortedMap.this.object2DoubleEntrySet().iterator(new BasicEntry<>(from, 0.0)));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2DoubleSortedMap.KeySetIterator<>(Object2DoubleSortedMaps.fastIterator(AbstractObject2DoubleSortedMap.this));
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

	protected class ValuesCollection extends AbstractDoubleCollection {
		@Override
		public DoubleIterator iterator() {
			return new AbstractObject2DoubleSortedMap.ValuesIterator<>(Object2DoubleSortedMaps.fastIterator(AbstractObject2DoubleSortedMap.this));
		}

		@Override
		public boolean contains(double k) {
			return AbstractObject2DoubleSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2DoubleSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements DoubleIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
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
