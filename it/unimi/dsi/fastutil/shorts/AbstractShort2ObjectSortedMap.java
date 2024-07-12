package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry;

public abstract class AbstractShort2ObjectSortedMap<V> extends AbstractShort2ObjectMap<V> implements Short2ObjectSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractShort2ObjectSortedMap() {
	}

	@Override
	public ShortSortedSet keySet() {
		return new AbstractShort2ObjectSortedMap.KeySet();
	}

	@Override
	public ObjectCollection<V> values() {
		return new AbstractShort2ObjectSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractShortSortedSet {
		@Override
		public boolean contains(short k) {
			return AbstractShort2ObjectSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractShort2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ObjectSortedMap.this.clear();
		}

		@Override
		public ShortComparator comparator() {
			return AbstractShort2ObjectSortedMap.this.comparator();
		}

		@Override
		public short firstShort() {
			return AbstractShort2ObjectSortedMap.this.firstShortKey();
		}

		@Override
		public short lastShort() {
			return AbstractShort2ObjectSortedMap.this.lastShortKey();
		}

		@Override
		public ShortSortedSet headSet(short to) {
			return AbstractShort2ObjectSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ShortSortedSet tailSet(short from) {
			return AbstractShort2ObjectSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ShortSortedSet subSet(short from, short to) {
			return AbstractShort2ObjectSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return new AbstractShort2ObjectSortedMap.KeySetIterator<>(AbstractShort2ObjectSortedMap.this.short2ObjectEntrySet().iterator(new BasicEntry<>(from, null)));
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return new AbstractShort2ObjectSortedMap.KeySetIterator<>(Short2ObjectSortedMaps.fastIterator(AbstractShort2ObjectSortedMap.this));
		}
	}

	protected static class KeySetIterator<V> implements ShortBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry<V>> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry<V>> i) {
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

	protected class ValuesCollection extends AbstractObjectCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractShort2ObjectSortedMap.ValuesIterator<>(Short2ObjectSortedMaps.fastIterator(AbstractShort2ObjectSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractShort2ObjectSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractShort2ObjectSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ObjectSortedMap.this.clear();
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
