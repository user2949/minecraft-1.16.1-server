package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2IntMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Reference2IntSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Reference2IntSortedMaps {
	public static final Reference2IntSortedMaps.EmptySortedMap EMPTY_MAP = new Reference2IntSortedMaps.EmptySortedMap();

	private Reference2IntSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Reference2IntSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2IntEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Reference2IntSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2IntEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Reference2IntSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2IntSortedMap<K> singleton(K key, Integer value) {
		return new Reference2IntSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2IntSortedMap<K> singleton(K key, Integer value, Comparator<? super K> comparator) {
		return new Reference2IntSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2IntSortedMap<K> singleton(K key, int value) {
		return new Reference2IntSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2IntSortedMap<K> singleton(K key, int value, Comparator<? super K> comparator) {
		return new Reference2IntSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2IntSortedMap<K> synchronize(Reference2IntSortedMap<K> m) {
		return new Reference2IntSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Reference2IntSortedMap<K> synchronize(Reference2IntSortedMap<K> m, Object sync) {
		return new Reference2IntSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Reference2IntSortedMap<K> unmodifiable(Reference2IntSortedMap<K> m) {
		return new Reference2IntSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Reference2IntSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2IntEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			return ReferenceSortedSets.EMPTY_SET;
		}

		@Override
		public Reference2IntSortedMap<K> subMap(K from, K to) {
			return Reference2IntSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2IntSortedMap<K> headMap(K to) {
			return Reference2IntSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2IntSortedMap<K> tailMap(K from) {
			return Reference2IntSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Reference2IntMaps.Singleton<K> implements Reference2IntSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Comparator<? super K> comparator;

		protected Singleton(K key, int value, Comparator<? super K> comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(K key, int value) {
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
		public ObjectSortedSet<Entry<K>> reference2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Reference2IntSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.reference2IntEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2IntSortedMap<K> subMap(K from, K to) {
			return (Reference2IntSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Reference2IntSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2IntSortedMap<K> headMap(K to) {
			return (Reference2IntSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Reference2IntSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2IntSortedMap<K> tailMap(K from) {
			return (Reference2IntSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Reference2IntSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Reference2IntSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2IntSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Reference2IntSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Reference2IntSortedMap<K> m) {
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
		public ObjectSortedSet<Entry<K>> reference2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2IntEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.reference2IntEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2IntSortedMap<K> subMap(K from, K to) {
			return new Reference2IntSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Reference2IntSortedMap<K> headMap(K to) {
			return new Reference2IntSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Reference2IntSortedMap<K> tailMap(K from) {
			return new Reference2IntSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Reference2IntSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2IntSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Reference2IntSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2IntEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.reference2IntEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2IntSortedMap<K> subMap(K from, K to) {
			return new Reference2IntSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Reference2IntSortedMap<K> headMap(K to) {
			return new Reference2IntSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Reference2IntSortedMap<K> tailMap(K from) {
			return new Reference2IntSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
