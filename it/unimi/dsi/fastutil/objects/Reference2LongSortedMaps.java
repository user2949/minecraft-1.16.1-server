package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2LongMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Reference2LongMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Reference2LongMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Reference2LongSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Reference2LongSortedMaps {
	public static final Reference2LongSortedMaps.EmptySortedMap EMPTY_MAP = new Reference2LongSortedMaps.EmptySortedMap();

	private Reference2LongSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Reference2LongSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2LongEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Reference2LongSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2LongEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Reference2LongSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2LongSortedMap<K> singleton(K key, Long value) {
		return new Reference2LongSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2LongSortedMap<K> singleton(K key, Long value, Comparator<? super K> comparator) {
		return new Reference2LongSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2LongSortedMap<K> singleton(K key, long value) {
		return new Reference2LongSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2LongSortedMap<K> singleton(K key, long value, Comparator<? super K> comparator) {
		return new Reference2LongSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2LongSortedMap<K> synchronize(Reference2LongSortedMap<K> m) {
		return new Reference2LongSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Reference2LongSortedMap<K> synchronize(Reference2LongSortedMap<K> m, Object sync) {
		return new Reference2LongSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Reference2LongSortedMap<K> unmodifiable(Reference2LongSortedMap<K> m) {
		return new Reference2LongSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Reference2LongSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2LongEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Long>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			return ReferenceSortedSets.EMPTY_SET;
		}

		@Override
		public Reference2LongSortedMap<K> subMap(K from, K to) {
			return Reference2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2LongSortedMap<K> headMap(K to) {
			return Reference2LongSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2LongSortedMap<K> tailMap(K from) {
			return Reference2LongSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Reference2LongMaps.Singleton<K> implements Reference2LongSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Comparator<? super K> comparator;

		protected Singleton(K key, long value, Comparator<? super K> comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(K key, long value) {
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
		public ObjectSortedSet<Entry<K>> reference2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Reference2LongSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Long>> entrySet() {
			return this.reference2LongEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2LongSortedMap<K> subMap(K from, K to) {
			return (Reference2LongSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Reference2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2LongSortedMap<K> headMap(K to) {
			return (Reference2LongSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Reference2LongSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2LongSortedMap<K> tailMap(K from) {
			return (Reference2LongSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Reference2LongSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Reference2LongSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2LongSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Reference2LongSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Reference2LongSortedMap<K> m) {
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
		public ObjectSortedSet<Entry<K>> reference2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2LongEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Long>> entrySet() {
			return this.reference2LongEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2LongSortedMap<K> subMap(K from, K to) {
			return new Reference2LongSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Reference2LongSortedMap<K> headMap(K to) {
			return new Reference2LongSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Reference2LongSortedMap<K> tailMap(K from) {
			return new Reference2LongSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Reference2LongSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2LongSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Reference2LongSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2LongEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Long>> entrySet() {
			return this.reference2LongEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2LongSortedMap<K> subMap(K from, K to) {
			return new Reference2LongSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Reference2LongSortedMap<K> headMap(K to) {
			return new Reference2LongSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Reference2LongSortedMap<K> tailMap(K from) {
			return new Reference2LongSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
