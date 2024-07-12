package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public abstract class AbstractDouble2ObjectSortedMap<V> extends AbstractDouble2ObjectMap<V> implements Double2ObjectSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractDouble2ObjectSortedMap() {
	}

	@Override
	public DoubleSortedSet keySet() {
		return new AbstractDouble2ObjectSortedMap.KeySet();
	}

	@Override
	public ObjectCollection<V> values() {
		return new AbstractDouble2ObjectSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractDoubleSortedSet {
		@Override
		public boolean contains(double k) {
			return AbstractDouble2ObjectSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractDouble2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractDouble2ObjectSortedMap.this.clear();
		}

		@Override
		public DoubleComparator comparator() {
			return AbstractDouble2ObjectSortedMap.this.comparator();
		}

		@Override
		public double firstDouble() {
			return AbstractDouble2ObjectSortedMap.this.firstDoubleKey();
		}

		@Override
		public double lastDouble() {
			return AbstractDouble2ObjectSortedMap.this.lastDoubleKey();
		}

		@Override
		public DoubleSortedSet headSet(double to) {
			return AbstractDouble2ObjectSortedMap.this.headMap(to).keySet();
		}

		@Override
		public DoubleSortedSet tailSet(double from) {
			return AbstractDouble2ObjectSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public DoubleSortedSet subSet(double from, double to) {
			return AbstractDouble2ObjectSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public DoubleBidirectionalIterator iterator(double from) {
			return new AbstractDouble2ObjectSortedMap.KeySetIterator<>(
				AbstractDouble2ObjectSortedMap.this.double2ObjectEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public DoubleBidirectionalIterator iterator() {
			return new AbstractDouble2ObjectSortedMap.KeySetIterator<>(Double2ObjectSortedMaps.fastIterator(AbstractDouble2ObjectSortedMap.this));
		}
	}

	protected static class KeySetIterator<V> implements DoubleBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<V>> i) {
			this.i = i;
		}

		@Override
		public double nextDouble() {
			return ((Entry)this.i.next()).getDoubleKey();
		}

		@Override
		public double previousDouble() {
			return this.i.previous().getDoubleKey();
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
			return new AbstractDouble2ObjectSortedMap.ValuesIterator<>(Double2ObjectSortedMaps.fastIterator(AbstractDouble2ObjectSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractDouble2ObjectSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractDouble2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractDouble2ObjectSortedMap.this.clear();
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
