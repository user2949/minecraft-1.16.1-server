package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2CharMap.Entry;
import java.util.Comparator;

public abstract class AbstractObject2CharSortedMap<K> extends AbstractObject2CharMap<K> implements Object2CharSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractObject2CharSortedMap() {
	}

	@Override
	public ObjectSortedSet<K> keySet() {
		return new AbstractObject2CharSortedMap.KeySet();
	}

	@Override
	public CharCollection values() {
		return new AbstractObject2CharSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractObjectSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractObject2CharSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractObject2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2CharSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractObject2CharSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractObject2CharSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractObject2CharSortedMap.this.lastKey();
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return AbstractObject2CharSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return AbstractObject2CharSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return AbstractObject2CharSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractObject2CharSortedMap.KeySetIterator<>(AbstractObject2CharSortedMap.this.object2CharEntrySet().iterator(new BasicEntry<>(from, '\u0000')));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractObject2CharSortedMap.KeySetIterator<>(Object2CharSortedMaps.fastIterator(AbstractObject2CharSortedMap.this));
		}
	}

	protected static class KeySetIterator<K> implements ObjectBidirectionalIterator<K> {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		public K next() {
			return (K)((Entry)this.i.next()).getKey();
		}

		@Override
		public K previous() {
			return (K)this.i.previous().getKey();
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
			return new AbstractObject2CharSortedMap.ValuesIterator<>(Object2CharSortedMaps.fastIterator(AbstractObject2CharSortedMap.this));
		}

		@Override
		public boolean contains(char k) {
			return AbstractObject2CharSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractObject2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractObject2CharSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements CharIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
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
