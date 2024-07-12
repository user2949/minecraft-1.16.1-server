package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry;
import java.util.Comparator;

public abstract class AbstractReference2FloatSortedMap<K> extends AbstractReference2FloatMap<K> implements Reference2FloatSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractReference2FloatSortedMap() {
	}

	@Override
	public ReferenceSortedSet<K> keySet() {
		return new AbstractReference2FloatSortedMap.KeySet();
	}

	@Override
	public FloatCollection values() {
		return new AbstractReference2FloatSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractReferenceSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractReference2FloatSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractReference2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2FloatSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractReference2FloatSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractReference2FloatSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractReference2FloatSortedMap.this.lastKey();
		}

		@Override
		public ReferenceSortedSet<K> headSet(K to) {
			return AbstractReference2FloatSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ReferenceSortedSet<K> tailSet(K from) {
			return AbstractReference2FloatSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ReferenceSortedSet<K> subSet(K from, K to) {
			return AbstractReference2FloatSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractReference2FloatSortedMap.KeySetIterator<>(
				AbstractReference2FloatSortedMap.this.reference2FloatEntrySet().iterator(new BasicEntry<>(from, 0.0F))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractReference2FloatSortedMap.KeySetIterator<>(Reference2FloatSortedMaps.fastIterator(AbstractReference2FloatSortedMap.this));
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

	protected class ValuesCollection extends AbstractFloatCollection {
		@Override
		public FloatIterator iterator() {
			return new AbstractReference2FloatSortedMap.ValuesIterator<>(Reference2FloatSortedMaps.fastIterator(AbstractReference2FloatSortedMap.this));
		}

		@Override
		public boolean contains(float k) {
			return AbstractReference2FloatSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractReference2FloatSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2FloatSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements FloatIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		@Override
		public float nextFloat() {
			return ((Entry)this.i.next()).getFloatValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
