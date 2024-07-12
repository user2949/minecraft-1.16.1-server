package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractInt2DoubleSortedMap extends AbstractInt2DoubleMap implements Int2DoubleSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractInt2DoubleSortedMap() {
	}

	@Override
	public IntSortedSet keySet() {
		return new AbstractInt2DoubleSortedMap.KeySet();
	}

	@Override
	public DoubleCollection values() {
		return new AbstractInt2DoubleSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractIntSortedSet {
		@Override
		public boolean contains(int k) {
			return AbstractInt2DoubleSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractInt2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2DoubleSortedMap.this.clear();
		}

		@Override
		public IntComparator comparator() {
			return AbstractInt2DoubleSortedMap.this.comparator();
		}

		@Override
		public int firstInt() {
			return AbstractInt2DoubleSortedMap.this.firstIntKey();
		}

		@Override
		public int lastInt() {
			return AbstractInt2DoubleSortedMap.this.lastIntKey();
		}

		@Override
		public IntSortedSet headSet(int to) {
			return AbstractInt2DoubleSortedMap.this.headMap(to).keySet();
		}

		@Override
		public IntSortedSet tailSet(int from) {
			return AbstractInt2DoubleSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public IntSortedSet subSet(int from, int to) {
			return AbstractInt2DoubleSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public IntBidirectionalIterator iterator(int from) {
			return new AbstractInt2DoubleSortedMap.KeySetIterator(AbstractInt2DoubleSortedMap.this.int2DoubleEntrySet().iterator(new BasicEntry(from, 0.0)));
		}

		@Override
		public IntBidirectionalIterator iterator() {
			return new AbstractInt2DoubleSortedMap.KeySetIterator(Int2DoubleSortedMaps.fastIterator(AbstractInt2DoubleSortedMap.this));
		}
	}

	protected static class KeySetIterator implements IntBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public int nextInt() {
			return ((Entry)this.i.next()).getIntKey();
		}

		@Override
		public int previousInt() {
			return this.i.previous().getIntKey();
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
			return new AbstractInt2DoubleSortedMap.ValuesIterator(Int2DoubleSortedMaps.fastIterator(AbstractInt2DoubleSortedMap.this));
		}

		@Override
		public boolean contains(double k) {
			return AbstractInt2DoubleSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractInt2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractInt2DoubleSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements DoubleIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
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
