package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractFloat2BooleanSortedMap extends AbstractFloat2BooleanMap implements Float2BooleanSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractFloat2BooleanSortedMap() {
	}

	@Override
	public FloatSortedSet keySet() {
		return new AbstractFloat2BooleanSortedMap.KeySet();
	}

	@Override
	public BooleanCollection values() {
		return new AbstractFloat2BooleanSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractFloatSortedSet {
		@Override
		public boolean contains(float k) {
			return AbstractFloat2BooleanSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractFloat2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2BooleanSortedMap.this.clear();
		}

		@Override
		public FloatComparator comparator() {
			return AbstractFloat2BooleanSortedMap.this.comparator();
		}

		@Override
		public float firstFloat() {
			return AbstractFloat2BooleanSortedMap.this.firstFloatKey();
		}

		@Override
		public float lastFloat() {
			return AbstractFloat2BooleanSortedMap.this.lastFloatKey();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			return AbstractFloat2BooleanSortedMap.this.headMap(to).keySet();
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			return AbstractFloat2BooleanSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			return AbstractFloat2BooleanSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public FloatBidirectionalIterator iterator(float from) {
			return new AbstractFloat2BooleanSortedMap.KeySetIterator(AbstractFloat2BooleanSortedMap.this.float2BooleanEntrySet().iterator(new BasicEntry(from, false)));
		}

		@Override
		public FloatBidirectionalIterator iterator() {
			return new AbstractFloat2BooleanSortedMap.KeySetIterator(Float2BooleanSortedMaps.fastIterator(AbstractFloat2BooleanSortedMap.this));
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

	protected class ValuesCollection extends AbstractBooleanCollection {
		@Override
		public BooleanIterator iterator() {
			return new AbstractFloat2BooleanSortedMap.ValuesIterator(Float2BooleanSortedMaps.fastIterator(AbstractFloat2BooleanSortedMap.this));
		}

		@Override
		public boolean contains(boolean k) {
			return AbstractFloat2BooleanSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractFloat2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2BooleanSortedMap.this.clear();
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
