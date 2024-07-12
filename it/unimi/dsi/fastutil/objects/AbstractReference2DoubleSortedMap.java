package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap.Entry;
import java.util.Comparator;

public abstract class AbstractReference2DoubleSortedMap<K> extends AbstractReference2DoubleMap<K> implements Reference2DoubleSortedMap<K> {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractReference2DoubleSortedMap() {
	}

	@Override
	public ReferenceSortedSet<K> keySet() {
		return new AbstractReference2DoubleSortedMap.KeySet();
	}

	@Override
	public DoubleCollection values() {
		return new AbstractReference2DoubleSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractReferenceSortedSet<K> {
		public boolean contains(Object k) {
			return AbstractReference2DoubleSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractReference2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2DoubleSortedMap.this.clear();
		}

		public Comparator<? super K> comparator() {
			return AbstractReference2DoubleSortedMap.this.comparator();
		}

		public K first() {
			return (K)AbstractReference2DoubleSortedMap.this.firstKey();
		}

		public K last() {
			return (K)AbstractReference2DoubleSortedMap.this.lastKey();
		}

		@Override
		public ReferenceSortedSet<K> headSet(K to) {
			return AbstractReference2DoubleSortedMap.this.headMap(to).keySet();
		}

		@Override
		public ReferenceSortedSet<K> tailSet(K from) {
			return AbstractReference2DoubleSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public ReferenceSortedSet<K> subSet(K from, K to) {
			return AbstractReference2DoubleSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new AbstractReference2DoubleSortedMap.KeySetIterator<>(
				AbstractReference2DoubleSortedMap.this.reference2DoubleEntrySet().iterator(new BasicEntry<>(from, 0.0))
			);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new AbstractReference2DoubleSortedMap.KeySetIterator<>(Reference2DoubleSortedMaps.fastIterator(AbstractReference2DoubleSortedMap.this));
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

	protected class ValuesCollection extends AbstractDoubleCollection {
		@Override
		public DoubleIterator iterator() {
			return new AbstractReference2DoubleSortedMap.ValuesIterator<>(Reference2DoubleSortedMaps.fastIterator(AbstractReference2DoubleSortedMap.this));
		}

		@Override
		public boolean contains(double k) {
			return AbstractReference2DoubleSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractReference2DoubleSortedMap.this.size();
		}

		public void clear() {
			AbstractReference2DoubleSortedMap.this.clear();
		}
	}

	protected static class ValuesIterator<K> implements DoubleIterator {
		protected final ObjectBidirectionalIterator<Entry<K>> i;

		public ValuesIterator(ObjectBidirectionalIterator<Entry<K>> i) {
			this.i = i;
		}

		@Override
		public double nextDouble() {
			return ((Entry)this.i.next()).getDoubleValue();
		}

		public boolean hasNext() {
			return this.i.hasNext();
		}
	}
}
