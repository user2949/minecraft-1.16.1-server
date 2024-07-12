package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Reference2FloatSortedMaps {
	public static final Reference2FloatSortedMaps.EmptySortedMap EMPTY_MAP = new Reference2FloatSortedMaps.EmptySortedMap();

	private Reference2FloatSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Reference2FloatSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2FloatEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Reference2FloatSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2FloatEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Reference2FloatSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2FloatSortedMap<K> singleton(K key, Float value) {
		return new Reference2FloatSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2FloatSortedMap<K> singleton(K key, Float value, Comparator<? super K> comparator) {
		return new Reference2FloatSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2FloatSortedMap<K> singleton(K key, float value) {
		return new Reference2FloatSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2FloatSortedMap<K> singleton(K key, float value, Comparator<? super K> comparator) {
		return new Reference2FloatSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2FloatSortedMap<K> synchronize(Reference2FloatSortedMap<K> m) {
		return new Reference2FloatSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Reference2FloatSortedMap<K> synchronize(Reference2FloatSortedMap<K> m, Object sync) {
		return new Reference2FloatSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Reference2FloatSortedMap<K> unmodifiable(Reference2FloatSortedMap<K> m) {
		return new Reference2FloatSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Reference2FloatSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2FloatEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			return ReferenceSortedSets.EMPTY_SET;
		}

		@Override
		public Reference2FloatSortedMap<K> subMap(K from, K to) {
			return Reference2FloatSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2FloatSortedMap<K> headMap(K to) {
			return Reference2FloatSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2FloatSortedMap<K> tailMap(K from) {
			return Reference2FloatSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Reference2FloatMaps.Singleton<K> implements Reference2FloatSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Comparator<? super K> comparator;

		protected Singleton(K key, float value, Comparator<? super K> comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(K key, float value) {
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
		public ObjectSortedSet<Entry<K>> reference2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Reference2FloatSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.reference2FloatEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2FloatSortedMap<K> subMap(K from, K to) {
			return (Reference2FloatSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Reference2FloatSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2FloatSortedMap<K> headMap(K to) {
			return (Reference2FloatSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Reference2FloatSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2FloatSortedMap<K> tailMap(K from) {
			return (Reference2FloatSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Reference2FloatSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Reference2FloatSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2FloatSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Reference2FloatSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Reference2FloatSortedMap<K> m) {
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
		public ObjectSortedSet<Entry<K>> reference2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2FloatEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.reference2FloatEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2FloatSortedMap<K> subMap(K from, K to) {
			return new Reference2FloatSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Reference2FloatSortedMap<K> headMap(K to) {
			return new Reference2FloatSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Reference2FloatSortedMap<K> tailMap(K from) {
			return new Reference2FloatSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Reference2FloatSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2FloatSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Reference2FloatSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2FloatEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.reference2FloatEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2FloatSortedMap<K> subMap(K from, K to) {
			return new Reference2FloatSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Reference2FloatSortedMap<K> headMap(K to) {
			return new Reference2FloatSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Reference2FloatSortedMap<K> tailMap(K from) {
			return new Reference2FloatSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
