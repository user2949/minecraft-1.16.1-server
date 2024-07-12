package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractFloat2FloatSortedMap extends AbstractFloat2FloatMap implements Float2FloatSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractFloat2FloatSortedMap() {
	}

	@Override
	public FloatSortedSet keySet() {
		return new AbstractFloat2FloatSortedMap.KeySet();
	}

	@Override
	public FloatCollection values() {
		return new AbstractFloat2FloatSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractFloatSortedSet {
		@Override
		public boolean contains(float k) {
			return AbstractFloat2FloatSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractFloat2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2FloatSortedMap.this.clear();
		}

		@Override
		public FloatComparator comparator() {
			return AbstractFloat2FloatSortedMap.this.comparator();
		}

		@Override
		public float firstFloat() {
			return AbstractFloat2FloatSortedMap.this.firstFloatKey();
		}

		@Override
		public float lastFloat() {
			return AbstractFloat2FloatSortedMap.this.lastFloatKey();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			return AbstractFloat2FloatSortedMap.this.headMap(to).keySet();
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			return AbstractFloat2FloatSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			return AbstractFloat2FloatSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public FloatBidirectionalIterator iterator(float from) {
			return new AbstractFloat2FloatSortedMap.KeySetIterator(AbstractFloat2FloatSortedMap.this.float2FloatEntrySet().iterator(new BasicEntry(from, 0.0F)));
		}

		@Override
		public FloatBidirectionalIterator iterator() {
			return new AbstractFloat2FloatSortedMap.KeySetIterator(Float2FloatSortedMaps.fastIterator(AbstractFloat2FloatSortedMap.this));
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

	protected class ValuesCollection extends AbstractFloatCollection {
		@Override
		public FloatIterator iterator() {
			return new AbstractFloat2FloatSortedMap.ValuesIterator(Float2FloatSortedMaps.fastIterator(AbstractFloat2FloatSortedMap.this));
		}

		@Override
		public boolean contains(float k) {
			return AbstractFloat2FloatSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractFloat2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2FloatSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements FloatIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
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
