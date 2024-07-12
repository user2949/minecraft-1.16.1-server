package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public abstract class AbstractChar2ObjectSortedMap<V> extends AbstractChar2ObjectMap<V> implements Char2ObjectSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractChar2ObjectSortedMap() {
	}

	@Override
	public CharSortedSet keySet() {
		return new AbstractChar2ObjectSortedMap.KeySet();
	}

	@Override
	public ObjectCollection<V> values() {
		return new AbstractChar2ObjectSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractCharSortedSet {
		@Override
		public boolean contains(char k) {
			return AbstractChar2ObjectSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractChar2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2ObjectSortedMap.this.clear();
		}

		@Override
		public CharComparator comparator() {
			return AbstractChar2ObjectSortedMap.this.comparator();
		}

		@Override
		public char firstChar() {
			return AbstractChar2ObjectSortedMap.this.firstCharKey();
		}

		@Override
		public char lastChar() {
			return AbstractChar2ObjectSortedMap.this.lastCharKey();
		}

		@Override
		public CharSortedSet headSet(char to) {
			return AbstractChar2ObjectSortedMap.this.headMap(to).keySet();
		}

		@Override
		public CharSortedSet tailSet(char from) {
			return AbstractChar2ObjectSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public CharSortedSet subSet(char from, char to) {
			return AbstractChar2ObjectSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return new AbstractChar2ObjectSortedMap.KeySetIterator<>(AbstractChar2ObjectSortedMap.this.char2ObjectEntrySet().iterator(new BasicEntry<>(from, null)));
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return new AbstractChar2ObjectSortedMap.KeySetIterator<>(Char2ObjectSortedMaps.fastIterator(AbstractChar2ObjectSortedMap.this));
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

	protected class ValuesCollection extends AbstractObjectCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractChar2ObjectSortedMap.ValuesIterator<>(Char2ObjectSortedMaps.fastIterator(AbstractChar2ObjectSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractChar2ObjectSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractChar2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractChar2ObjectSortedMap.this.clear();
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
