package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;

public abstract class AbstractDouble2ReferenceSortedMap<V> extends AbstractDouble2ReferenceMap<V> implements Double2ReferenceSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractDouble2ReferenceSortedMap() {
	}

	@Override
	public DoubleSortedSet keySet() {
		return new AbstractDouble2ReferenceSortedMap.KeySet();
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractDouble2ReferenceSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractDoubleSortedSet {
		@Override
		public boolean contains(double k) {
			return AbstractDouble2ReferenceSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractDouble2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractDouble2ReferenceSortedMap.this.clear();
		}

		@Override
		public DoubleComparator comparator() {
			return AbstractDouble2ReferenceSortedMap.this.comparator();
		}

		@Override
		public double firstDouble() {
			return AbstractDouble2ReferenceSortedMap.this.firstDoubleKey();
		}

		@Override
		public double lastDouble() {
			return AbstractDouble2ReferenceSortedMap.this.lastDoubleKey();
		}

		@Override
		public DoubleSortedSet headSet(double to) {
			return AbstractDouble2ReferenceSortedMap.this.headMap(to).keySet();
		}

		@Override
		public DoubleSortedSet tailSet(double from) {
			return AbstractDouble2ReferenceSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public DoubleSortedSet subSet(double from, double to) {
			return AbstractDouble2ReferenceSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public DoubleBidirectionalIterator iterator(double from) {
			return new AbstractDouble2ReferenceSortedMap.KeySetIterator<>(
				AbstractDouble2ReferenceSortedMap.this.double2ReferenceEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public DoubleBidirectionalIterator iterator() {
			return new AbstractDouble2ReferenceSortedMap.KeySetIterator<>(Double2ReferenceSortedMaps.fastIterator(AbstractDouble2ReferenceSortedMap.this));
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

	protected class ValuesCollection extends AbstractReferenceCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractDouble2ReferenceSortedMap.ValuesIterator<>(Double2ReferenceSortedMaps.fastIterator(AbstractDouble2ReferenceSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractDouble2ReferenceSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractDouble2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractDouble2ReferenceSortedMap.this.clear();
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
