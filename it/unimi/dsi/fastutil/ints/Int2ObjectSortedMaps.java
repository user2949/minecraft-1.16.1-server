package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps.EmptyMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps.SynchronizedMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps.UnmodifiableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class Int2ObjectSortedMaps {
	public static final Int2ObjectSortedMaps.EmptySortedMap EMPTY_MAP = new Int2ObjectSortedMaps.EmptySortedMap();

	private Int2ObjectSortedMaps() {
	}

	public static Comparator<? super java.util.Map.Entry<Integer, ?>> entryComparator(IntComparator comparator) {
		return (x, y) -> comparator.compare(((Integer)x.getKey()).intValue(), ((Integer)y.getKey()).intValue());
	}

	public static <V> ObjectBidirectionalIterator<Entry<V>> fastIterator(Int2ObjectSortedMap<V> map) {
		ObjectSortedSet<Entry<V>> entries = map.int2ObjectEntrySet();
		return entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <V> ObjectBidirectionalIterable<Entry<V>> fastIterable(Int2ObjectSortedMap<V> map) {
		ObjectSortedSet<Entry<V>> entries = map.int2ObjectEntrySet();
		return (ObjectBidirectionalIterable<Entry<V>>)(entries instanceof FastSortedEntrySet ? ((FastSortedEntrySet)entries)::fastIterator : entries);
	}

	public static <V> Int2ObjectSortedMap<V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <V> Int2ObjectSortedMap<V> singleton(Integer key, V value) {
		return new Int2ObjectSortedMaps.Singleton<>(key, value);
	}

	public static <V> Int2ObjectSortedMap<V> singleton(Integer key, V value, IntComparator comparator) {
		return new Int2ObjectSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <V> Int2ObjectSortedMap<V> singleton(int key, V value) {
		return new Int2ObjectSortedMaps.Singleton<>(key, value);
	}

	public static <V> Int2ObjectSortedMap<V> singleton(int key, V value, IntComparator comparator) {
		return new Int2ObjectSortedMaps.Singleton<>(key, value, comparator);
	}

	public static <V> Int2ObjectSortedMap<V> synchronize(Int2ObjectSortedMap<V> m) {
		return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(m);
	}

	public static <V> Int2ObjectSortedMap<V> synchronize(Int2ObjectSortedMap<V> m, Object sync) {
		return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(m, sync);
	}

	public static <V> Int2ObjectSortedMap<V> unmodifiable(Int2ObjectSortedMap<V> m) {
		return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(m);
	}

	public static class EmptySortedMap<V> extends EmptyMap<V> implements Int2ObjectSortedMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySortedMap() {
		}

		@Override
		public IntComparator comparator() {
			return null;
		}

		@Override
		public ObjectSortedSet<Entry<V>> int2ObjectEntrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public IntSortedSet keySet() {
			return IntSortedSets.EMPTY_SET;
		}

		@Override
		public Int2ObjectSortedMap<V> subMap(int from, int to) {
			return Int2ObjectSortedMaps.EMPTY_MAP;
		}

		@Override
		public Int2ObjectSortedMap<V> headMap(int to) {
			return Int2ObjectSortedMaps.EMPTY_MAP;
		}

		@Override
		public Int2ObjectSortedMap<V> tailMap(int from) {
			return Int2ObjectSortedMaps.EMPTY_MAP;
		}

		@Override
		public int firstIntKey() {
			throw new NoSuchElementException();
		}

		@Override
		public int lastIntKey() {
			throw new NoSuchElementException();
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> headMap(Integer oto) {
			return this.headMap(oto.intValue());
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> tailMap(Integer ofrom) {
			return this.tailMap(ofrom.intValue());
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> subMap(Integer ofrom, Integer oto) {
			return this.subMap(ofrom.intValue(), oto.intValue());
		}

		@Deprecated
		@Override
		public Integer firstKey() {
			return this.firstIntKey();
		}

		@Deprecated
		@Override
		public Integer lastKey() {
			return this.lastIntKey();
		}
	}

	public static class Singleton<V> extends Int2ObjectMaps.Singleton<V> implements Int2ObjectSortedMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final IntComparator comparator;

		protected Singleton(int key, V value, IntComparator comparator) {
			super(key, value);
			this.comparator = comparator;
		}

		protected Singleton(int key, V value) {
			this(key, value, null);
		}

		final int compare(int k1, int k2) {
			return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
		}

		@Override
		public IntComparator comparator() {
			return this.comparator;
		}

		@Override
		public ObjectSortedSet<Entry<V>> int2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.singleton(new BasicEntry<>(this.key, this.value), Int2ObjectSortedMaps.entryComparator(this.comparator));
			}

			return (ObjectSortedSet<Entry<V>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return this.int2ObjectEntrySet();
		}

		@Override
		public IntSortedSet keySet() {
			if (this.keys == null) {
				this.keys = IntSortedSets.singleton(this.key, this.comparator);
			}

			return (IntSortedSet)this.keys;
		}

		@Override
		public Int2ObjectSortedMap<V> subMap(int from, int to) {
			return (Int2ObjectSortedMap<V>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Int2ObjectSortedMaps.EMPTY_MAP);
		}

		@Override
		public Int2ObjectSortedMap<V> headMap(int to) {
			return (Int2ObjectSortedMap<V>)(this.compare(this.key, to) < 0 ? this : Int2ObjectSortedMaps.EMPTY_MAP);
		}

		@Override
		public Int2ObjectSortedMap<V> tailMap(int from) {
			return (Int2ObjectSortedMap<V>)(this.compare(from, this.key) <= 0 ? this : Int2ObjectSortedMaps.EMPTY_MAP);
		}

		@Override
		public int firstIntKey() {
			return this.key;
		}

		@Override
		public int lastIntKey() {
			return this.key;
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> headMap(Integer oto) {
			return this.headMap(oto.intValue());
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> tailMap(Integer ofrom) {
			return this.tailMap(ofrom.intValue());
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> subMap(Integer ofrom, Integer oto) {
			return this.subMap(ofrom.intValue(), oto.intValue());
		}

		@Deprecated
		@Override
		public Integer firstKey() {
			return this.firstIntKey();
		}

		@Deprecated
		@Override
		public Integer lastKey() {
			return this.lastIntKey();
		}
	}

	public static class SynchronizedSortedMap<V> extends SynchronizedMap<V> implements Int2ObjectSortedMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2ObjectSortedMap<V> sortedMap;

		protected SynchronizedSortedMap(Int2ObjectSortedMap<V> m, Object sync) {
			super(m, sync);
			this.sortedMap = m;
		}

		protected SynchronizedSortedMap(Int2ObjectSortedMap<V> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public IntComparator comparator() {
			synchronized (this.sync) {
				return this.sortedMap.comparator();
			}
		}

		@Override
		public ObjectSortedSet<Entry<V>> int2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.synchronize(this.sortedMap.int2ObjectEntrySet(), this.sync);
			}

			return (ObjectSortedSet<Entry<V>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return this.int2ObjectEntrySet();
		}

		@Override
		public IntSortedSet keySet() {
			if (this.keys == null) {
				this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
			}

			return (IntSortedSet)this.keys;
		}

		@Override
		public Int2ObjectSortedMap<V> subMap(int from, int to) {
			return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Override
		public Int2ObjectSortedMap<V> headMap(int to) {
			return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Override
		public Int2ObjectSortedMap<V> tailMap(int from) {
			return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
		}

		@Override
		public int firstIntKey() {
			synchronized (this.sync) {
				return this.sortedMap.firstIntKey();
			}
		}

		@Override
		public int lastIntKey() {
			synchronized (this.sync) {
				return this.sortedMap.lastIntKey();
			}
		}

		@Deprecated
		@Override
		public Integer firstKey() {
			synchronized (this.sync) {
				return this.sortedMap.firstKey();
			}
		}

		@Deprecated
		@Override
		public Integer lastKey() {
			synchronized (this.sync) {
				return this.sortedMap.lastKey();
			}
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
			return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.subMap(from, to), this.sync);
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> headMap(Integer to) {
			return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.headMap(to), this.sync);
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> tailMap(Integer from) {
			return new Int2ObjectSortedMaps.SynchronizedSortedMap<>(this.sortedMap.tailMap(from), this.sync);
		}
	}

	public static class UnmodifiableSortedMap<V> extends UnmodifiableMap<V> implements Int2ObjectSortedMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2ObjectSortedMap<V> sortedMap;

		protected UnmodifiableSortedMap(Int2ObjectSortedMap<V> m) {
			super(m);
			this.sortedMap = m;
		}

		@Override
		public IntComparator comparator() {
			return this.sortedMap.comparator();
		}

		@Override
		public ObjectSortedSet<Entry<V>> int2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.int2ObjectEntrySet());
			}

			return (ObjectSortedSet<Entry<V>>)this.entries;
		}

		@Deprecated
		@Override
		public ObjectSortedSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return this.int2ObjectEntrySet();
		}

		@Override
		public IntSortedSet keySet() {
			if (this.keys == null) {
				this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet());
			}

			return (IntSortedSet)this.keys;
		}

		@Override
		public Int2ObjectSortedMap<V> subMap(int from, int to) {
			return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Override
		public Int2ObjectSortedMap<V> headMap(int to) {
			return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Override
		public Int2ObjectSortedMap<V> tailMap(int from) {
			return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}

		@Override
		public int firstIntKey() {
			return this.sortedMap.firstIntKey();
		}

		@Override
		public int lastIntKey() {
			return this.sortedMap.lastIntKey();
		}

		@Deprecated
		@Override
		public Integer firstKey() {
			return this.sortedMap.firstKey();
		}

		@Deprecated
		@Override
		public Integer lastKey() {
			return this.sortedMap.lastKey();
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
			return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.subMap(from, to));
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> headMap(Integer to) {
			return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.headMap(to));
		}

		@Deprecated
		@Override
		public Int2ObjectSortedMap<V> tailMap(Integer from) {
			return new Int2ObjectSortedMaps.UnmodifiableSortedMap<>(this.sortedMap.tailMap(from));
		}
	}
}
