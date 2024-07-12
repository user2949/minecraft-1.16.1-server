package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Object2IntSortedMaps {
	public static final Object2IntSortedMaps.EmptySortedMap EMPTY_MAP = new Object2IntSortedMaps.EmptySortedMap();

	private Object2IntSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Object2IntSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.object2IntEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Object2IntSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.object2IntEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Object2IntSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Object2IntSortedMap<K> singleton(K key, Integer value) {
		return new Object2IntSortedMaps.Singleton<>(key, value);
	}

	public static <K> Object2IntSortedMap<K> singleton(K key, Integer value, Comparator<? super K> comparator) {
		return new Object2IntSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Object2IntSortedMap<K> singleton(K key, int value) {
		return new Object2IntSortedMaps.Singleton<>(key, value);
	}

	public static <K> Object2IntSortedMap<K> singleton(K key, int value, Comparator<? super K> comparator) {
		return new Object2IntSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m) {
		return new Object2IntSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m, Object sync) {
		return new Object2IntSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Object2IntSortedMap<K> unmodifiable(Object2IntSortedMap<K> m) {
		return new Object2IntSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Object2IntSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> object2IntEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public Object2IntSortedMap<K> subMap(K from, K to) {
			return Object2IntSortedMaps.EMPTY_MAP;
		}

		@Override
		public Object2IntSortedMap<K> headMap(K to) {
			return Object2IntSortedMaps.EMPTY_MAP;
		}

		@Override
		public Object2IntSortedMap<K> tailMap(K from) {
			return Object2IntSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Object2IntMaps.Singleton<K> implements Object2IntSortedMap<K>, Serializable, Cloneable {
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
		public ObjectSortedSet<Entry<K>> object2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Object2IntSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.object2IntEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2IntSortedMap<K> subMap(K from, K to) {
			return (Object2IntSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
		}

		@Override
		public Object2IntSortedMap<K> headMap(K to) {
			return (Object2IntSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
		}

		@Override
		public Object2IntSortedMap<K> tailMap(K from) {
			return (Object2IntSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Object2IntSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2IntSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Object2IntSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Object2IntSortedMap<K> m) {
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
		public ObjectSortedSet<Entry<K>> object2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2IntEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.object2IntEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2IntSortedMap<K> subMap(K from, K to) {
			return new Object2IntSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Object2IntSortedMap<K> headMap(K to) {
			return new Object2IntSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Object2IntSortedMap<K> tailMap(K from) {
			return new Object2IntSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Object2IntSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2IntSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Object2IntSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> object2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2IntEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.object2IntEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2IntSortedMap<K> subMap(K from, K to) {
			return new Object2IntSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Object2IntSortedMap<K> headMap(K to) {
			return new Object2IntSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Object2IntSortedMap<K> tailMap(K from) {
			return new Object2IntSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
