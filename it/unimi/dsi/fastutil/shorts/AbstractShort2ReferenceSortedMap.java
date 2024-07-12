package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap.Entry;

public abstract class AbstractShort2ReferenceSortedMap<V> extends AbstractShort2ReferenceMap<V> implements Short2ReferenceSortedMap<V> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractShort2ReferenceSortedMap() {
	}

	@Override
	public ShortSortedSet keySet() {
		return new AbstractShort2ReferenceSortedMap.KeySet();
	}

	@Override
	public ReferenceCollection<V> values() {
		return new AbstractShort2ReferenceSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractShortSortedSet {
		@Override
		public boolean contains(short k) {
			return AbstractShort2ReferenceSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractShort2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ReferenceSortedMap.this.clear();
		}

		@Override
		public ShortComparator comparator() {
			return AbstractShort2ReferenceSortedMap.this.comparator();
		}

		@Override
		public short firstShort() {
			return AbstractShort2ReferenceSortedMap.this.firstShortKey();
		}

		@Override
		public short lastShort() {
			return AbstractShort2ReferenceSortedMap.this.lastShortKey();
		}

		@Override
		public ShortSortedSet headSet(short to) {
			return AbstractShort2ReferenceSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ShortSortedSet tailSet(short from) {
			return AbstractShort2ReferenceSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ShortSortedSet subSet(short from, short to) {
			return AbstractShort2ReferenceSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return new AbstractShort2ReferenceSortedMap.KeySetIterator<>(
				AbstractShort2ReferenceSortedMap.this.short2ReferenceEntrySet().iterator(new BasicEntry<>(from, null))
			);
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return new AbstractShort2ReferenceSortedMap.KeySetIterator<>(Short2ReferenceSortedMaps.fastIterator(AbstractShort2ReferenceSortedMap.this));
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

	protected class ValuesCollection extends AbstractReferenceCollection<V> {
		@Override
		public ObjectIterator<V> iterator() {
			return new AbstractShort2ReferenceSortedMap.ValuesIterator<>(Short2ReferenceSortedMaps.fastIterator(AbstractShort2ReferenceSortedMap.this));
		}

		public boolean contains(Object k) {
			return AbstractShort2ReferenceSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractShort2ReferenceSortedMap.this.size();
		}

		public void clear() {
			AbstractShort2ReferenceSortedMap.this.clear();
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
