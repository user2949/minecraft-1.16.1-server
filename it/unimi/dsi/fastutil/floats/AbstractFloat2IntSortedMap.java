package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractFloat2IntSortedMap extends AbstractFloat2IntMap implements Float2IntSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractFloat2IntSortedMap() {
	}

	@Override
	public FloatSortedSet keySet() {
		return new AbstractFloat2IntSortedMap.KeySet();
	}

	@Override
	public IntCollection values() {
		return new AbstractFloat2IntSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractFloatSortedSet {
		@Override
		public boolean contains(float k) {
			return AbstractFloat2IntSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractFloat2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2IntSortedMap.this.clear();
		}

		@Override
		public FloatComparator comparator() {
			return AbstractFloat2IntSortedMap.this.comparator();
		}

		@Override
		public float firstFloat() {
			return AbstractFloat2IntSortedMap.this.firstFloatKey();
		}

		@Override
		public float lastFloat() {
			return AbstractFloat2IntSortedMap.this.lastFloatKey();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			return AbstractFloat2IntSortedMap.this.headMap(to).keySet();
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			return AbstractFloat2IntSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			return AbstractFloat2IntSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public FloatBidirectionalIterator iterator(float from) {
			return new AbstractFloat2IntSortedMap.KeySetIterator(AbstractFloat2IntSortedMap.this.float2IntEntrySet().iterator(new BasicEntry(from, 0)));
		}

		@Override
		public FloatBidirectionalIterator iterator() {
			return new AbstractFloat2IntSortedMap.KeySetIterator(Float2IntSortedMaps.fastIterator(AbstractFloat2IntSortedMap.this));
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

	protected class ValuesCollection extends AbstractIntCollection {
		@Override
		public IntIterator iterator() {
			return new AbstractFloat2IntSortedMap.ValuesIterator(Float2IntSortedMaps.fastIterator(AbstractFloat2IntSortedMap.this));
		}

		@Override
		public boolean contains(int k) {
			return AbstractFloat2IntSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractFloat2IntSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2IntSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements IntIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public int nextInt() {
			return ((Entry)this.i.next()).getIntValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
