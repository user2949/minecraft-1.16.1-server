package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractDouble2BooleanSortedMap extends AbstractDouble2BooleanMap implements Double2BooleanSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractDouble2BooleanSortedMap() {
	}

	@Override
	public DoubleSortedSet keySet() {
		return new AbstractDouble2BooleanSortedMap.KeySet();
	}

	@Override
	public BooleanCollection values() {
		return new AbstractDouble2BooleanSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractDoubleSortedSet {
		@Override
		public boolean contains(double k) {
			return AbstractDouble2BooleanSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractDouble2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractDouble2BooleanSortedMap.this.clear();
		}

		@Override
		public DoubleComparator comparator() {
			return AbstractDouble2BooleanSortedMap.this.comparator();
		}

		@Override
		public double firstDouble() {
			return AbstractDouble2BooleanSortedMap.this.firstDoubleKey();
		}

		@Override
		public double lastDouble() {
			return AbstractDouble2BooleanSortedMap.this.lastDoubleKey();
		}

		@Override
		public DoubleSortedSet headSet(double to) {
			return AbstractDouble2BooleanSortedMap.this.headMap(to).keySet();
		}

		@Override
		public DoubleSortedSet tailSet(double from) {
			return AbstractDouble2BooleanSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public DoubleSortedSet subSet(double from, double to) {
			return AbstractDouble2BooleanSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public DoubleBidirectionalIterator iterator(double from) {
			return new AbstractDouble2BooleanSortedMap.KeySetIterator(
				AbstractDouble2BooleanSortedMap.this.double2BooleanEntrySet().iterator(new BasicEntry(from, false))
			);
		}

		@Override
		public DoubleBidirectionalIterator iterator() {
			return new AbstractDouble2BooleanSortedMap.KeySetIterator(Double2BooleanSortedMaps.fastIterator(AbstractDouble2BooleanSortedMap.this));
		}
	}

	protected static class KeySetIterator implements DoubleBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
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

	protected class ValuesCollection extends AbstractBooleanCollection {
		@Override
		public BooleanIterator iterator() {
			return new AbstractDouble2BooleanSortedMap.ValuesIterator(Double2BooleanSortedMaps.fastIterator(AbstractDouble2BooleanSortedMap.this));
		}

		@Override
		public boolean contains(boolean k) {
			return AbstractDouble2BooleanSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractDouble2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractDouble2BooleanSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements BooleanIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
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
