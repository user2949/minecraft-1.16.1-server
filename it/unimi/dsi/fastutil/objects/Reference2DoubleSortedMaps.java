package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Reference2DoubleSortedMaps {
	public static final Reference2DoubleSortedMaps.EmptySortedMap EMPTY_MAP = new Reference2DoubleSortedMaps.EmptySortedMap();

	private Reference2DoubleSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Reference2DoubleSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2DoubleEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Reference2DoubleSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2DoubleEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Reference2DoubleSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2DoubleSortedMap<K> singleton(K key, Double value) {
		return new Reference2DoubleSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2DoubleSortedMap<K> singleton(K key, Double value, Comparator<? super K> comparator) {
		return new Reference2DoubleSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2DoubleSortedMap<K> singleton(K key, double value) {
		return new Reference2DoubleSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2DoubleSortedMap<K> singleton(K key, double value, Comparator<? super K> comparator) {
		return new Reference2DoubleSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2DoubleSortedMap<K> synchronize(Reference2DoubleSortedMap<K> m) {
		return new Reference2DoubleSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Reference2DoubleSortedMap<K> synchronize(Reference2DoubleSortedMap<K> m, Object sync) {
		return new Reference2DoubleSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Reference2DoubleSortedMap<K> unmodifiable(Reference2DoubleSortedMap<K> m) {
		return new Reference2DoubleSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Reference2DoubleSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2DoubleEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Double>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			return ReferenceSortedSets.EMPTY_SET;
		}

		@Override
		public Reference2DoubleSortedMap<K> subMap(K from, K to) {
			return Reference2DoubleSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2DoubleSortedMap<K> headMap(K to) {
			return Reference2DoubleSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2DoubleSortedMap<K> tailMap(K from) {
			return Reference2DoubleSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Reference2DoubleMaps.Singleton<K> implements Reference2DoubleSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Comparator<? super K> comparator;

		protected Singleton(K key, double value, Comparator<? super K> comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(K key, double value) {
			this(key, value, null);
		}

		final int compare(K k1, K k2) {
			return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.comparator;
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2DoubleEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Reference2DoubleSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Double>> entrySet() {
			return this.reference2DoubleEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2DoubleSortedMap<K> subMap(K from, K to) {
			return (Reference2DoubleSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Reference2DoubleSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2DoubleSortedMap<K> headMap(K to) {
			return (Reference2DoubleSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Reference2DoubleSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2DoubleSortedMap<K> tailMap(K from) {
			return (Reference2DoubleSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Reference2DoubleSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Reference2DoubleSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2DoubleSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Reference2DoubleSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Reference2DoubleSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			synchronized (this.sync) {
				return this.sortedMap.comparator();
			}
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2DoubleEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2DoubleEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Double>> entrySet() {
			return this.reference2DoubleEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2DoubleSortedMap<K> subMap(K from, K to) {
			return new Reference2DoubleSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Reference2DoubleSortedMap<K> headMap(K to) {
			return new Reference2DoubleSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Reference2DoubleSortedMap<K> tailMap(K from) {
			return new Reference2DoubleSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
		}

		public K firstKey() {
			synchronized (this.sync) {
				return (K)this.sortedMap.firstKey();
			}
		}

		public K lastKey() {
			synchronized (this.sync) {
				return (K)this.sortedMap.lastKey();
			}
		}
	}

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Reference2DoubleSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2DoubleSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Reference2DoubleSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2DoubleEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2DoubleEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Double>> entrySet() {
			return this.reference2DoubleEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2DoubleSortedMap<K> subMap(K from, K to) {
			return new Reference2DoubleSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Reference2DoubleSortedMap<K> headMap(K to) {
			return new Reference2DoubleSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Reference2DoubleSortedMap<K> tailMap(K from) {
			return new Reference2DoubleSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
