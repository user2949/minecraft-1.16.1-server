package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Object2ObjectSortedMaps {
	public static final Object2ObjectSortedMaps.EmptySortedMap EMPTY_MAP = new Object2ObjectSortedMaps.EmptySortedMap();

	private Object2ObjectSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K, V> ObjectBidirectionalIterator<Entry<K, V>> fastIterator(Object2ObjectSortedMap<K, V> map) {
		ObjectSortedSet<Entry<K, V>> entries = map.object2ObjectEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K, V> ObjectBidirectionalIterable<Entry<K, V>> fastIterable(Object2ObjectSortedMap<K, V> map) {
		ObjectSortedSet<Entry<K, V>> entries = map.object2ObjectEntrySet();
		return (ObjectBidirectionalIterable<Entry<K, V>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K, V> Object2ObjectSortedMap<K, V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K, V> Object2ObjectSortedMap<K, V> singleton(K key, V value) {
		return new Object2ObjectSortedMaps.Singleton<>(key, value);
	}

	public static <K, V> Object2ObjectSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
		return new Object2ObjectSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> m) {
		return new Object2ObjectSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> m, Object sync) {
		return new Object2ObjectSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K, V> Object2ObjectSortedMap<K, V> unmodifiable(Object2ObjectSortedMap<K, V> m) {
		return new Object2ObjectSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K, V> extends EmptyMap<K, V> implements Object2ObjectSortedMap<K, V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K, V>> object2ObjectEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
			return Object2ObjectSortedMaps.EMPTY_MAP;
		}

		@Override
		public Object2ObjectSortedMap<K, V> headMap(K to) {
			return Object2ObjectSortedMaps.EMPTY_MAP;
		}

		@Override
		public Object2ObjectSortedMap<K, V> tailMap(K from) {
			return Object2ObjectSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K, V> extends Object2ObjectMaps.Singleton<K, V> implements Object2ObjectSortedMap<K, V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Comparator<? super K> comparator;

		protected Singleton(K key, V value, Comparator<? super K> comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(K key, V value) {
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
		public ObjectSortedSet<Entry<K, V>> object2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(
					new BasicEntry<>(this.key, this.value), (Comparator<? super BasicEntry<K, V>>)Object2ObjectSortedMaps.entryComparator(this.comparator)
				);
			}

			return (ObjectSortedSet<Entry<K, V>>)this.entries;
		}

		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.object2ObjectEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
			return (Object2ObjectSortedMap<K, V>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
		}

		@Override
		public Object2ObjectSortedMap<K, V> headMap(K to) {
			return (Object2ObjectSortedMap<K, V>)(this.compare(this.key, to) < 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
		}

		@Override
		public Object2ObjectSortedMap<K, V> tailMap(K from) {
			return (Object2ObjectSortedMap<K, V>)(this.compare(from, this.key) <= 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements Object2ObjectSortedMap<K, V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ObjectSortedMap<K, V> sortedMap;

		protected SynchronizedSortedMap(Object2ObjectSortedMap<K, V> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Object2ObjectSortedMap<K, V> m) {
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
		public ObjectSortedSet<Entry<K, V>> object2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2ObjectEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K, V>>)this.entries;
		}

		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.object2ObjectEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
			return new Object2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Object2ObjectSortedMap<K, V> headMap(K to) {
			return new Object2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Object2ObjectSortedMap<K, V> tailMap(K from) {
			return new Object2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V> implements Object2ObjectSortedMap<K, V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ObjectSortedMap<K, V> sortedMap;

		protected UnmodifiableSortedMap(Object2ObjectSortedMap<K, V> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K, V>> object2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2ObjectEntrySet());
			}

			return (ObjectSortedSet<Entry<K, V>>)this.entries;
		}

		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.object2ObjectEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
			return new Object2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Object2ObjectSortedMap<K, V> headMap(K to) {
			return new Object2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Object2ObjectSortedMap<K, V> tailMap(K from) {
			return new Object2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
