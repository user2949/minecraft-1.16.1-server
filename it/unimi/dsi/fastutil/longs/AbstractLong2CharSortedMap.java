package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractLong2CharSortedMap extends AbstractLong2CharMap implements Long2CharSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractLong2CharSortedMap() {
	}

	@Override
	public LongSortedSet keySet() {
		return new AbstractLong2CharSortedMap.KeySet();
	}

	@Override
	public CharCollection values() {
		return new AbstractLong2CharSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractLongSortedSet {
		@Override
		public boolean contains(long k) {
			return AbstractLong2CharSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractLong2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractLong2CharSortedMap.this.clear();
		}

		@Override
		public LongComparator comparator() {
			return AbstractLong2CharSortedMap.this.comparator();
		}

		@Override
		public long firstLong() {
			return AbstractLong2CharSortedMap.this.firstLongKey();
		}

		@Override
		public long lastLong() {
			return AbstractLong2CharSortedMap.this.lastLongKey();
		}

		@Override
		public LongSortedSet headSet(long to) {
			return AbstractLong2CharSortedMap.this.headMap(to).keySet();
		}

		@Override
		public LongSortedSet tailSet(long from) {
			return AbstractLong2CharSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public LongSortedSet subSet(long from, long to) {
			return AbstractLong2CharSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public LongBidirectionalIterator iterator(long from) {
			return new AbstractLong2CharSortedMap.KeySetIterator(AbstractLong2CharSortedMap.this.long2CharEntrySet().iterator(new BasicEntry(from, '\u0000')));
		}

		@Override
		public LongBidirectionalIterator iterator() {
			return new AbstractLong2CharSortedMap.KeySetIterator(Long2CharSortedMaps.fastIterator(AbstractLong2CharSortedMap.this));
		}
	}

	protected static class KeySetIterator implements LongBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public long nextLong() {
			return ((Entry)this.i.next()).getLongKey();
		}

		@Override
		public long previousLong() {
			return this.i.previous().getLongKey();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return this.i.hasPrevious();
		}
	}

	protected class ValuesCollection extends AbstractCharCollection {
		@Override
		public CharIterator iterator() {
			return new AbstractLong2CharSortedMap.ValuesIterator(Long2CharSortedMaps.fastIterator(AbstractLong2CharSortedMap.this));
		}

		@Override
		public boolean contains(char k) {
			return AbstractLong2CharSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractLong2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractLong2CharSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator implements CharIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public char nextChar() {
			return ((Entry)this.i.next()).getCharValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
