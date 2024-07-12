package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2IntFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.longs.Long2IntFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.longs.Long2IntFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.longs.Long2IntMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

public final class Long2IntMaps {
	public static final Long2IntMaps.EmptyMap EMPTY_MAP = new Long2IntMaps.EmptyMap();

	private Long2IntMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Long2IntMap map) {
		ObjectSet<Entry> entries = map.long2IntEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Long2IntMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.long2IntEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Long2IntMap map) {
		final ObjectSet<Entry> entries = map.long2IntEntrySet();
		return (ObjectIterable<Entry>)(entries instanceof FastEntrySet ? new ObjectIterable<Entry>() {
			@Override
			public ObjectIterator<Entry> iterator() {
				return ((FastEntrySet)entries).fastIterator();
			}

			public void forEach(Consumer<? super Entry> consumer) {
				((FastEntrySet)entries).fastForEach(consumer);
			}
		} : entries);
	}

	public static Long2IntMap singleton(long key, int value) {
		return new Long2IntMaps.Singleton(key, value);
	}

	public static Long2IntMap singleton(Long key, Integer value) {
		return new Long2IntMaps.Singleton(key, value);
	}

	public static Long2IntMap synchronize(Long2IntMap m) {
		return new Long2IntMaps.SynchronizedMap(m);
	}

	public static Long2IntMap synchronize(Long2IntMap m, Object sync) {
		return new Long2IntMaps.SynchronizedMap(m, sync);
	}

	public static Long2IntMap unmodifiable(Long2IntMap m) {
		return new Long2IntMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Long2IntMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(int v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Long, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2IntEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public LongSet keySet() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public IntCollection values() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Long2IntMaps.EMPTY_MAP;
		}

		public boolean isEmpty() {
			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public boolean equals(Object o) {
			return !(o instanceof Map) ? false : ((Map)o).isEmpty();
		}

		@Override
		public String toString() {
			return "{}";
		}
	}

	public static class Singleton extends Long2IntFunctions.Singleton implements Long2IntMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient IntCollection values;

		protected Singleton(long key, int value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(int v) {
			return this.value == v;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return (Integer)ov == this.value;
		}

		public void putAll(Map<? extends Long, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Integer>> entrySet() {
			return this.long2IntEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public IntCollection values() {
			if (this.values == null) {
				this.values = IntSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return HashCommon.long2int(this.key) ^ this.value;
		}

		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else if (!(o instanceof Map)) {
				return false;
			} else {
				Map<?, ?> m = (Map<?, ?>)o;
				return m.size() != 1 ? false : ((java.util.Map.Entry)m.entrySet().iterator().next()).equals(this.entrySet().iterator().next());
			}
		}

		public String toString() {
			return "{" + this.key + "=>" + this.value + "}";
		}
	}

	public static class SynchronizedMap extends SynchronizedFunction implements Long2IntMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2IntMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient IntCollection values;

		protected SynchronizedMap(Long2IntMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Long2IntMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(int v) {
			synchronized (this.sync) {
				return this.map.containsValue(v);
			}
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			synchronized (this.sync) {
				return this.map.containsValue(ov);
			}
		}

		public void putAll(Map<? extends Long, ? extends Integer> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> long2IntEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.long2IntEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Integer>> entrySet() {
			return this.long2IntEntrySet();
		}

		@Override
		public LongSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = LongSets.synchronize(this.map.keySet(), this.sync);
				}

				return this.keys;
			}
		}

		@Override
		public IntCollection values() {
			synchronized (this.sync) {
				return this.values == null ? IntCollections.synchronize(this.map.values(), this.sync) : this.values;
			}
		}

		public boolean isEmpty() {
			synchronized (this.sync) {
				return this.map.isEmpty();
			}
		}

		@Override
		public int hashCode() {
			synchronized (this.sync) {
				return this.map.hashCode();
			}
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				synchronized (this.sync) {
					return this.map.equals(o);
				}
			}
		}

		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (this.sync) {
				s.defaultWriteObject();
			}
		}

		@Override
		public int getOrDefault(long key, int defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Long, ? super Integer> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Long, ? super Integer, ? extends Integer> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public int putIfAbsent(long key, int value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(long key, int value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public int replace(long key, int value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(long key, int oldValue, int newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public int computeIfAbsent(long key, LongToIntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public int computeIfAbsentNullable(long key, LongFunction<? extends Integer> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public int computeIfAbsentPartial(long key, Long2IntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public int computeIfPresent(long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public int compute(long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public int merge(long key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer getOrDefault(Object key, Integer defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Deprecated
		@Override
		public Integer replace(Long key, Integer value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Long key, Integer oldValue, Integer newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Integer putIfAbsent(Long key, Integer value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Integer computeIfAbsent(Long key, Function<? super Long, ? extends Integer> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer computeIfPresent(Long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer compute(Long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer merge(Long key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Long2IntMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2IntMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient IntCollection values;

		protected UnmodifiableMap(Long2IntMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(int v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Long, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.long2IntEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Integer>> entrySet() {
			return this.long2IntEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public IntCollection values() {
			return this.values == null ? IntCollections.unmodifiable(this.map.values()) : this.values;
		}

		public boolean isEmpty() {
			return this.map.isEmpty();
		}

		@Override
		public int hashCode() {
			return this.map.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.map.equals(o);
		}

		@Override
		public int getOrDefault(long key, int defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Long, ? super Integer> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Long, ? super Integer, ? extends Integer> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int putIfAbsent(long key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(long key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int replace(long key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(long key, int oldValue, int newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfAbsent(long key, LongToIntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfAbsentNullable(long key, LongFunction<? extends Integer> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfAbsentPartial(long key, Long2IntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfPresent(long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int compute(long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int merge(long key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer getOrDefault(Object key, Integer defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer replace(Long key, Integer value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Long key, Integer oldValue, Integer newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer putIfAbsent(Long key, Integer value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer computeIfAbsent(Long key, Function<? super Long, ? extends Integer> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer computeIfPresent(Long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer compute(Long key, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer merge(Long key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
