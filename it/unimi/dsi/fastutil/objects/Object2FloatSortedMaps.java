package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps.EmptyMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap.FastSortedEntrySet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Object2FloatSortedMaps {
	public static final Object2FloatSortedMaps.EmptySortedMap EMPTY_MAP = new Object2FloatSortedMaps.EmptySortedMap();

	private Object2FloatSortedMaps() {
	}

	public static <K> Comparator<? super java.util.Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
		return (x, y) -> comparator.compare(x.getKey(), y.getKey());
	}

	public static <K> ObjectBidirectionalIterator<Entry<K>> fastIterator(Object2FloatSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.object2FloatEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> ObjectBidirectionalIterable<Entry<K>> fastIterable(Object2FloatSortedMap<K> map) {
		ObjectSortedSet<Entry<K>> entries = map.object2FloatEntrySet();
		return (ObjectBidirectionalIterable<Entry<K>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <K> Object2FloatSortedMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Object2FloatSortedMap<K> singleton(K key, Float value) {
		return new Object2FloatSortedMaps.Singleton<>(key, value);
	}

	public static <K> Object2FloatSortedMap<K> singleton(K key, Float value, Comparator<? super K> comparator) {
		return new Object2FloatSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Object2FloatSortedMap<K> singleton(K key, float value) {
		return new Object2FloatSortedMaps.Singleton<>(key, value);
	}

	public static <K> Object2FloatSortedMap<K> singleton(K key, float value, Comparator<? super K> comparator) {
		return new Object2FloatSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <K> Object2FloatSortedMap<K> synchronize(Object2FloatSortedMap<K> m) {
		return new Object2FloatSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <K> Object2FloatSortedMap<K> synchronize(Object2FloatSortedMap<K> m, Object sync) {
		return new Object2FloatSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <K> Object2FloatSortedMap<K> unmodifiable(Object2FloatSortedMap<K> m) {
		return new Object2FloatSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<K> extends EmptyMap<K> implements Object2FloatSortedMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<K>> object2FloatEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public Object2FloatSortedMap<K> subMap(K from, K to) {
			return Object2FloatSortedMaps.EMPTY_MAP;
		}

		@Override
		public Object2FloatSortedMap<K> headMap(K to) {
			return Object2FloatSortedMaps.EMPTY_MAP;
		}

		@Override
		public Object2FloatSortedMap<K> tailMap(K from) {
			return Object2FloatSortedMaps.EMPTY_MAP;
		}

		public K firstKey() {
			throw new NoSuchElementException();
		}

		public K lastKey() {
			throw new NoSuchElementException();
		}
	}

	public static class Singleton<K> extends Object2FloatMaps.Singleton<K> implements Object2FloatSortedMap<K>, Serializable, Cloneable {
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
		public ObjectSortedSet<Entry<K>> object2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Object2FloatSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.object2FloatEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2FloatSortedMap<K> subMap(K from, K to) {
			return (Object2FloatSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2FloatSortedMaps.EMPTY_MAP);
		}

		@Override
		public Object2FloatSortedMap<K> headMap(K to) {
			return (Object2FloatSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Object2FloatSortedMaps.EMPTY_MAP);
		}

		@Override
		public Object2FloatSortedMap<K> tailMap(K from) {
			return (Object2FloatSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Object2FloatSortedMaps.EMPTY_MAP);
		}

		public K firstKey() {
			return this.key;
		}

		public K lastKey() {
			return this.key;
		}
	}

	public static class SynchronizedSortedMap<K> extends SynchronizedMap<K> implements Object2FloatSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2FloatSortedMap<K> sortedMap;

		protected SynchronizedSortedMap(Object2FloatSortedMap<K> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Object2FloatSortedMap<K> m) {
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
		public ObjectSortedSet<Entry<K>> object2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2FloatEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.object2FloatEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2FloatSortedMap<K> subMap(K from, K to) {
			return new Object2FloatSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Object2FloatSortedMap<K> headMap(K to) {
			return new Object2FloatSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Object2FloatSortedMap<K> tailMap(K from) {
			return new Object2FloatSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
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

	public static class UnmodifiableSortedMap<K> extends UnmodifiableMap<K> implements Object2FloatSortedMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2FloatSortedMap<K> sortedMap;

		protected UnmodifiableSortedMap(Object2FloatSortedMap<K> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public Comparator<? super K> comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<K>> object2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2FloatEntrySet());
			}

			return (ObjectSortedSet<Entry<K>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.object2FloatEntrySet();
		}

		@Override
		public ObjectSortedSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (ObjectSortedSet<K>)this.keys;
		}

		@Override
		public Object2FloatSortedMap<K> subMap(K from, K to) {
			return new Object2FloatSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Object2FloatSortedMap<K> headMap(K to) {
			return new Object2FloatSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Object2FloatSortedMap<K> tailMap(K from) {
			return new Object2FloatSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		public K firstKey() {
			return (K)this.sortedMap.firstKey();
		}

		public K lastKey() {
			return (K)this.sortedMap.lastKey();
		}
	}
}
