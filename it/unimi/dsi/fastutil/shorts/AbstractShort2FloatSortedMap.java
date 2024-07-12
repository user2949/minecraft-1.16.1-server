package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry;

public abstract class AbstractShort2FloatSortedMap extends AbstractShort2FloatMap implements Short2FloatSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractShort2FloatSortedMap() {
	}

	@Override
	public ShortSortedSet keySet() {
		return new AbstractShort2FloatSortedMap.KeySet();
	}

	@Override
	public FloatCollection values() {
		return new AbstractShort2FloatSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractShortSortedSet {
		@Override
		public boolean contains(short k) {
			return AbstractShort2FloatSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractShort2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2FloatSortedMap.this.clear();
		}

		@Override
		public ShortComparator comparator() {
			return AbstractShort2FloatSortedMap.this.comparator();
		}

		@Override
		public short firstShort() {
			return AbstractShort2FloatSortedMap.this.firstShortKey();
		}

		@Override
		public short lastShort() {
			return AbstractShort2FloatSortedMap.this.lastShortKey();
		}

		@Override
		public ShortSortedSet headSet(short to) {
			return AbstractShort2FloatSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ShortSortedSet tailSet(short from) {
			return AbstractShort2FloatSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ShortSortedSet subSet(short from, short to) {
			return AbstractShort2FloatSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return new AbstractShort2FloatSortedMap.KeySetIterator(AbstractShort2FloatSortedMap.this.short2FloatEntrySet().iterator(new BasicEntry(from, 0.0F)));
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return new AbstractShort2FloatSortedMap.KeySetIterator(Short2FloatSortedMaps.fastIterator(AbstractShort2FloatSortedMap.this));
		}
	}

	protected static class KeySetIterator implements ShortBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public short nextShort() {
			return ((Entry)this.i.next()).getShortKey();
		}

		@Override
		public short previousShort() {
			return this.i.previous().getShortKey();
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
			return new AbstractShort2FloatSortedMap.ValuesIterator(Short2FloatSortedMaps.fastIterator(AbstractShort2FloatSortedMap.this));
		}

		@Override
		public boolean contains(float k) {
			return AbstractShort2FloatSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractShort2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2FloatSortedMap.this.clear();
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
