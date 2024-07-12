package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2ShortMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;

public abstract class AbstractChar2ShortSortedMap extends AbstractChar2ShortMap implements Char2ShortSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractChar2ShortSortedMap() {
	}

	@Override
	public CharSortedSet keySet() {
		return new AbstractChar2ShortSortedMap.KeySet();
	}

	@Override
	public ShortCollection values() {
		return new AbstractChar2ShortSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractCharSortedSet {
		@Override
		public boolean contains(char k) {
			return AbstractChar2ShortSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractChar2ShortSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2ShortSortedMap.this.clear();
		}

		@Override
		public CharComparator comparator() {
			return AbstractChar2ShortSortedMap.this.comparator();
		}

		@Override
		public char firstChar() {
			return AbstractChar2ShortSortedMap.this.firstCharKey();
		}

		@Override
		public char lastChar() {
			return AbstractChar2ShortSortedMap.this.lastCharKey();
		}

		@Override
		public CharSortedSet headSet(char to) {
			return AbstractChar2ShortSortedMap.this.headMap(to).keySet();
		}

		@Override
		public CharSortedSet tailSet(char from) {
			return AbstractChar2ShortSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public CharSortedSet subSet(char from, char to) {
			return AbstractChar2ShortSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return new AbstractChar2ShortSortedMap.KeySetIterator(AbstractChar2ShortSortedMap.this.char2ShortEntrySet().iterator(new BasicEntry(from, (short)0)));
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return new AbstractChar2ShortSortedMap.KeySetIterator(Char2ShortSortedMaps.fastIterator(AbstractChar2ShortSortedMap.this));
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

	protected class ValuesCollection extends AbstractShortCollection {
		@Override
		public ShortIterator iterator() {
			return new AbstractChar2ShortSortedMap.ValuesIterator(Char2ShortSortedMaps.fastIterator(AbstractChar2ShortSortedMap.this));
		}

		@Override
		public boolean contains(short k) {
			return AbstractChar2ShortSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractChar2ShortSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2ShortSortedMap.this.clear();
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
