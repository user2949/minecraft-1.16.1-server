package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap.Entry;

public abstract class AbstractShort2BooleanSortedMap extends AbstractShort2BooleanMap implements Short2BooleanSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractShort2BooleanSortedMap() {
	}

	@Override
	public ShortSortedSet keySet() {
		return new AbstractShort2BooleanSortedMap.KeySet();
	}

	@Override
	public BooleanCollection values() {
		return new AbstractShort2BooleanSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractShortSortedSet {
		@Override
		public boolean contains(short k) {
			return AbstractShort2BooleanSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractShort2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2BooleanSortedMap.this.clear();
		}

		@Override
		public ShortComparator comparator() {
			return AbstractShort2BooleanSortedMap.this.comparator();
		}

		@Override
		public short firstShort() {
			return AbstractShort2BooleanSortedMap.this.firstShortKey();
		}

		@Override
		public short lastShort() {
			return AbstractShort2BooleanSortedMap.this.lastShortKey();
		}

		@Override
		public ShortSortedSet headSet(short to) {
			return AbstractShort2BooleanSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ShortSortedSet tailSet(short from) {
			return AbstractShort2BooleanSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ShortSortedSet subSet(short from, short to) {
			return AbstractShort2BooleanSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return new AbstractShort2BooleanSortedMap.KeySetIterator(AbstractShort2BooleanSortedMap.this.short2BooleanEntrySet().iterator(new BasicEntry(from, false)));
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return new AbstractShort2BooleanSortedMap.KeySetIterator(Short2BooleanSortedMaps.fastIterator(AbstractShort2BooleanSortedMap.this));
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

	protected class ValuesCollection extends AbstractBooleanCollection {
		@Override
		public BooleanIterator iterator() {
			return new AbstractShort2BooleanSortedMap.ValuesIterator(Short2BooleanSortedMaps.fastIterator(AbstractShort2BooleanSortedMap.this));
		}

		@Override
		public boolean contains(boolean k) {
			return AbstractShort2BooleanSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractShort2BooleanSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2BooleanSortedMap.this.clear();
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
