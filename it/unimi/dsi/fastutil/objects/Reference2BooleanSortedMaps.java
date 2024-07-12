package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Reference2BooleanSortedMaps {
	public static final Reference2BooleanSortedMaps.EmptySortedMap EMPTY_MAP = new Reference2BooleanSortedMaps.EmptySortedMap();

	private Reference2BooleanSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Reference2BooleanSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2BooleanEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Reference2BooleanSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.reference2BooleanEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Reference2BooleanSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2BooleanSortedMap<K> singleton(K key, Boolean value) {
		return new Reference2BooleanSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2BooleanSortedMap<K> singleton(K key, Boolean value, Comparator<? super K> comparator) {
		return new Reference2BooleanSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2BooleanSortedMap<K> singleton(K key, boolean value) {
		return new Reference2BooleanSortedMaps.Singleton<>(key, value);
	}

	public static <K> Reference2BooleanSortedMap<K> singleton(K key, boolean value, Comparator<? super K> comparator) {
		return new Reference2BooleanSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Reference2BooleanSortedMap<K> synchronize(Reference2BooleanSortedMap<K> m) {
		return new Reference2BooleanSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Reference2BooleanSortedMap<K> synchronize(Reference2BooleanSortedMap<K> m, Object sync) {
		return new Reference2BooleanSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Reference2BooleanSortedMap<K> unmodifiable(Reference2BooleanSortedMap<K> m) {
		return new Reference2BooleanSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Reference2BooleanSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2BooleanEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Boolean>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			return ReferenceSortedSets.EMPTY_SET;
		}

		@Override
		public Reference2BooleanSortedMap<K> subMap(K from, K to) {
			return Reference2BooleanSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2BooleanSortedMap<K> headMap(K to) {
			return Reference2BooleanSortedMaps.EMPTY_MAP;
		}

		@Override
		public Reference2BooleanSortedMap<K> tailMap(K from) {
			return Reference2BooleanSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Reference2BooleanMaps.Singleton<K> implements Reference2BooleanSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Comparator<? super K> comparator;

		protected Singleton(K key, boolean value, Comparator<? super K> comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(K key, boolean value) {
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
		public ObjectSortedSet<Entry<K>> reference2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Reference2BooleanSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Boolean>> entrySet() {
			return this.reference2BooleanEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2BooleanSortedMap<K> subMap(K from, K to) {
			return (Reference2BooleanSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Reference2BooleanSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2BooleanSortedMap<K> headMap(K to) {
			return (Reference2BooleanSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Reference2BooleanSortedMaps.EMPTY_MAP);
		}

		@Override
		public Reference2BooleanSortedMap<K> tailMap(K from) {
			return (Reference2BooleanSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Reference2BooleanSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Reference2BooleanSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2BooleanSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Reference2BooleanSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Reference2BooleanSortedMap<K> m) {
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
		public ObjectSortedSet<Entry<K>> reference2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2BooleanEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Boolean>> entrySet() {
			return this.reference2BooleanEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2BooleanSortedMap<K> subMap(K from, K to) {
			return new Reference2BooleanSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Reference2BooleanSortedMap<K> headMap(K to) {
			return new Reference2BooleanSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Reference2BooleanSortedMap<K> tailMap(K from) {
			return new Reference2BooleanSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Reference2BooleanSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2BooleanSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Reference2BooleanSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> reference2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2BooleanEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Boolean>> entrySet() {
			return this.reference2BooleanEntrySet();
		}

		@Override
		public ReferenceSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ReferenceSortedSet<K>)this.keys;
		}

		@Override
		public Reference2BooleanSortedMap<K> subMap(K from, K to) {
			return new Reference2BooleanSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Reference2BooleanSortedMap<K> headMap(K to) {
			return new Reference2BooleanSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Reference2BooleanSortedMap<K> tailMap(K from) {
			return new Reference2BooleanSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
