package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractFloat2LongSortedMap extends AbstractFloat2LongMap implements Float2LongSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractFloat2LongSortedMap() {
	}

	@Override
	public FloatSortedSet keySet() {
		return new AbstractFloat2LongSortedMap.KeySet();
	}

	@Override
	public LongCollection values() {
		return new AbstractFloat2LongSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractFloatSortedSet {
		@Override
		public boolean contains(float k) {
			return AbstractFloat2LongSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractFloat2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2LongSortedMap.this.clear();
		}

		@Override
		public FloatComparator comparator() {
			return AbstractFloat2LongSortedMap.this.comparator();
		}

		@Override
		public float firstFloat() {
			return AbstractFloat2LongSortedMap.this.firstFloatKey();
		}

		@Override
		public float lastFloat() {
			return AbstractFloat2LongSortedMap.this.lastFloatKey();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			return AbstractFloat2LongSortedMap.this.headMap(to).keySet();
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			return AbstractFloat2LongSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			return AbstractFloat2LongSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public FloatBidirectionalIterator iterator(float from) {
			return new AbstractFloat2LongSortedMap.KeySetIterator(AbstractFloat2LongSortedMap.this.float2LongEntrySet().iterator(new BasicEntry(from, 0L)));
		}

		@Override
		public FloatBidirectionalIterator iterator() {
			return new AbstractFloat2LongSortedMap.KeySetIterator(Float2LongSortedMaps.fastIterator(AbstractFloat2LongSortedMap.this));
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

	protected class ValuesCollection extends AbstractLongCollection {
		@Override
		public LongIterator iterator() {
			return new AbstractFloat2LongSortedMap.ValuesIterator(Float2LongSortedMaps.fastIterator(AbstractFloat2LongSortedMap.this));
		}

		@Override
		public boolean contains(long k) {
			return AbstractFloat2LongSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractFloat2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2LongSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements LongIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public long nextLong() {
			return ((Entry)this.i.next()).getLongValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
