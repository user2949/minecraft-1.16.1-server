package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap.Entry;

public abstract class AbstractShort2ShortSortedMap extends AbstractShort2ShortMap implements Short2ShortSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractShort2ShortSortedMap() {
	}

	@Override
	public ShortSortedSet keySet() {
		return new AbstractShort2ShortSortedMap.KeySet();
	}

	@Override
	public ShortCollection values() {
		return new AbstractShort2ShortSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractShortSortedSet {
		@Override
		public boolean contains(short k) {
			return AbstractShort2ShortSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractShort2ShortSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ShortSortedMap.this.clear();
		}

		@Override
		public ShortComparator comparator() {
			return AbstractShort2ShortSortedMap.this.comparator();
		}

		@Override
		public short firstShort() {
			return AbstractShort2ShortSortedMap.this.firstShortKey();
		}

		@Override
		public short lastShort() {
			return AbstractShort2ShortSortedMap.this.lastShortKey();
		}

		@Override
		public ShortSortedSet headSet(short to) {
			return AbstractShort2ShortSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ShortSortedSet tailSet(short from) {
			return AbstractShort2ShortSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ShortSortedSet subSet(short from, short to) {
			return AbstractShort2ShortSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return new AbstractShort2ShortSortedMap.KeySetIterator(AbstractShort2ShortSortedMap.this.short2ShortEntrySet().iterator(new BasicEntry(from, (short)0)));
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return new AbstractShort2ShortSortedMap.KeySetIterator(Short2ShortSortedMaps.fastIterator(AbstractShort2ShortSortedMap.this));
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

	protected class ValuesCollection extends AbstractShortCollection {
		@Override
		public ShortIterator iterator() {
			return new AbstractShort2ShortSortedMap.ValuesIterator(Short2ShortSortedMaps.fastIterator(AbstractShort2ShortSortedMap.this));
		}

		@Override
		public boolean contains(short k) {
			return AbstractShort2ShortSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractShort2ShortSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ShortSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements ShortIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public short nextShort() {
			return ((Entry)this.i.next()).getShortValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
