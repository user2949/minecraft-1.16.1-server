package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;

public abstract class AbstractChar2ReferenceSortedMap<V> extends AbstractChar2ReferenceMap<V> implements Char2ReferenceSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractChar2ReferenceSortedMap() {
	}

	@Override
	public CharSortedSet keySet() {
		return new AbstractChar2ReferenceSortedMap.KeySet();
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractChar2ReferenceSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractCharSortedSet {
		@Override
		public boolean contains(char k) {
			return AbstractChar2ReferenceSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractChar2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2ReferenceSortedMap.this.clear();
		}

		@Override
		public CharComparator comparator() {
			return AbstractChar2ReferenceSortedMap.this.comparator();
		}

		@Override
		public char firstChar() {
			return AbstractChar2ReferenceSortedMap.this.firstCharKey();
		}

		@Override
		public char lastChar() {
			return AbstractChar2ReferenceSortedMap.this.lastCharKey();
		}

		@Override
		public CharSortedSet headSet(char to) {
			return AbstractChar2ReferenceSortedMap.this.headMap(to).keySet();
		}

		@Override
		public CharSortedSet tailSet(char from) {
			return AbstractChar2ReferenceSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public CharSortedSet subSet(char from, char to) {
			return AbstractChar2ReferenceSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return new AbstractChar2ReferenceSortedMap.KeySetIterator<>(
				AbstractChar2ReferenceSortedMap.this.char2ReferenceEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return new AbstractChar2ReferenceSortedMap.KeySetIterator<>(Char2ReferenceSortedMaps.fastIterator(AbstractChar2ReferenceSortedMap.this));
		}
	}

	protected static class KeySetIterator<V> implements CharBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<V>> i) {
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

	protected class ValuesCollection extends AbstractReferenceCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractChar2ReferenceSortedMap.ValuesIterator<>(Char2ReferenceSortedMaps.fastIterator(AbstractChar2ReferenceSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractChar2ReferenceSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractChar2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2ReferenceSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<V> implements ObjectIterator<V> {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<V>> i) {
			this.i = i;
		}

		public V next() {
			return (V)((Entry)this.i.next()).getValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
