package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2CharMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractFloat2CharSortedMap extends AbstractFloat2CharMap implements Float2CharSortedMap {
	private static final long serialVersionUID = -1773560792952436569L;

	protected AbstractFloat2CharSortedMap() {
	}

	@Override
	public FloatSortedSet keySet() {
		return new AbstractFloat2CharSortedMap.KeySet();
	}

	@Override
	public CharCollection values() {
		return new AbstractFloat2CharSortedMap.ValuesCollection();
	}

	protected class KeySet extends AbstractFloatSortedSet {
		@Override
		public boolean contains(float k) {
			return AbstractFloat2CharSortedMap.this.containsKey(k);
		}

		public int size() {
			return AbstractFloat2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2CharSortedMap.this.clear();
		}

		@Override
		public FloatComparator comparator() {
			return AbstractFloat2CharSortedMap.this.comparator();
		}

		@Override
		public float firstFloat() {
			return AbstractFloat2CharSortedMap.this.firstFloatKey();
		}

		@Override
		public float lastFloat() {
			return AbstractFloat2CharSortedMap.this.lastFloatKey();
		}

		@Override
		public FloatSortedSet headSet(float to) {
			return AbstractFloat2CharSortedMap.this.headMap(to).keySet();
		}

		@Override
		public FloatSortedSet tailSet(float from) {
			return AbstractFloat2CharSortedMap.this.tailMap(from).keySet();
		}

		@Override
		public FloatSortedSet subSet(float from, float to) {
			return AbstractFloat2CharSortedMap.this.subMap(from, to).keySet();
		}

		@Override
		public FloatBidirectionalIterator iterator(float from) {
			return new AbstractFloat2CharSortedMap.KeySetIterator(AbstractFloat2CharSortedMap.this.float2CharEntrySet().iterator(new BasicEntry(from, '\u0000')));
		}

		@Override
		public FloatBidirectionalIterator iterator() {
			return new AbstractFloat2CharSortedMap.KeySetIterator(Float2CharSortedMaps.fastIterator(AbstractFloat2CharSortedMap.this));
		}
	}

	protected static class KeySetIterator implements FloatBidirectionalIterator {
		protected final ObjectBidirectionalIterator<Entry> i;

		public KeySetIterator(ObjectBidirectionalIterator<Entry> i) {
			this.i = i;
		}

		@Override
		public float nextFloat() {
			return ((Entry)this.i.next()).getFloatKey();
		}

		@Override
		public float previousFloat() {
			return this.i.previous().getFloatKey();
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
			return new AbstractFloat2CharSortedMap.ValuesIterator(Float2CharSortedMaps.fastIterator(AbstractFloat2CharSortedMap.this));
		}

		@Override
		public boolean contains(char k) {
			return AbstractFloat2CharSortedMap.this.containsValue(k);
		}

		public int size() {
			return AbstractFloat2CharSortedMap.this.size();
		}

		public void clear() {
			AbstractFloat2CharSortedMap.this.clear();
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
