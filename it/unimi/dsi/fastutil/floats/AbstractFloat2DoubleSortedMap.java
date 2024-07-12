package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractFloat2DoubleSortedMap extends AbstractFloat2DoubleMap implements Float2DoubleSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractFloat2DoubleSortedMap() {
	}

	@Override
	public FloatSortedSet keySet() {
		return new AbstractFloat2DoubleSortedMap.KeySet();
	}

	@Override
	public DoubleCollection values() {
		return new AbstractFloat2DoubleSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractFloatSortedSet {
		@Override
		public boolean contains(float k) {
			return AbstractFloat2DoubleSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractFloat2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2DoubleSortedMap.this.clear();
		}

		@Override
		public FloatComparator comparator() {
			return AbstractFloat2DoubleSortedMap.this.comparator();
		}

		@Override
		public float firstFloat() {
			return AbstractFloat2DoubleSortedMap.this.firstFloatKey();
		}

		@Override
		public float lastFloat() {
			return AbstractFloat2DoubleSortedMap.this.lastFloatKey();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			return AbstractFloat2DoubleSortedMap.this.headMap(to).keySet();
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			return AbstractFloat2DoubleSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			return AbstractFloat2DoubleSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public FloatBidirectionalIterator iterator(float from) {
			return new AbstractFloat2DoubleSortedMap.KeySetIterator(AbstractFloat2DoubleSortedMap.this.float2DoubleEntrySet().iterator(new BasicEntry(from, 0.0)));
		}

		@Override
		public FloatBidirectionalIterator iterator() {
			return new AbstractFloat2DoubleSortedMap.KeySetIterator(Float2DoubleSortedMaps.fastIterator(AbstractFloat2DoubleSortedMap.this));
		}
	}

	protected static class KeySetIterator implements FloatBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public float nextFloat() {
			return ((Entry)this.i.next()).getFloatKey();
		}

		@Override
		public float previousFloat() {
			return this.i.previous().getFloatKey();
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
			return new AbstractFloat2DoubleSortedMap.ValuesIterator(Float2DoubleSortedMaps.fastIterator(AbstractFloat2DoubleSortedMap.this));
		}

		@Override
		public boolean contains(double k) {
			return AbstractFloat2DoubleSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractFloat2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2DoubleSortedMap.this.clear();
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
