package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2LongMap.Entry;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractChar2LongSortedMap extends AbstractChar2LongMap implements Char2LongSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractChar2LongSortedMap() {
	}

	@Override
	public CharSortedSet keySet() {
		return new AbstractChar2LongSortedMap.KeySet();
	}

	@Override
	public LongCollection values() {
		return new AbstractChar2LongSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractCharSortedSet {
		@Override
		public boolean contains(char k) {
			return AbstractChar2LongSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractChar2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2LongSortedMap.this.clear();
		}

		@Override
		public CharComparator comparator() {
			return AbstractChar2LongSortedMap.this.comparator();
		}

		@Override
		public char firstChar() {
			return AbstractChar2LongSortedMap.this.firstCharKey();
		}

		@Override
		public char lastChar() {
			return AbstractChar2LongSortedMap.this.lastCharKey();
		}

		@Override
		public CharSortedSet headSet(char to) {
			return AbstractChar2LongSortedMap.this.headMap(to).keySet();
		}

		@Override
		public CharSortedSet tailSet(char from) {
			return AbstractChar2LongSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public CharSortedSet subSet(char from, char to) {
			return AbstractChar2LongSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return new AbstractChar2LongSortedMap.KeySetIterator(AbstractChar2LongSortedMap.this.char2LongEntrySet().iterator(new BasicEntry(from, 0L)));
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return new AbstractChar2LongSortedMap.KeySetIterator(Char2LongSortedMaps.fastIterator(AbstractChar2LongSortedMap.this));
		}
	}

	protected static class KeySetIterator implements CharBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public char nextChar() {
			return ((Entry)this.i.next()).getCharKey();
		}

		@Override
		public char previousChar() {
			return this.i.previous().getCharKey();
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
			return new AbstractChar2LongSortedMap.ValuesIterator(Char2LongSortedMaps.fastIterator(AbstractChar2LongSortedMap.this));
		}

		@Override
		public boolean contains(long k) {
			return AbstractChar2LongSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractChar2LongSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2LongSortedMap.this.clear();
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
