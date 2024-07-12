package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractInt2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2LongFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.ints.Int2LongFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.ints.Int2LongFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.ints.Int2LongMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongSets;
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
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;

public final class Int2LongMaps {
	public static final Int2LongMaps.EmptyMap EMPTY_MAP = new Int2LongMaps.EmptyMap();

	private Int2LongMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Int2LongMap map) {
		ObjectSet<Entry> entries = map.int2LongEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Int2LongMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.int2LongEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Int2LongMap map) {
		final ObjectSet<Entry> entries = map.int2LongEntrySet();
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

	public static Int2LongMap singleton(int key, long value) {
		return new Int2LongMaps.Singleton(key, value);
	}

	public static Int2LongMap singleton(Integer key, Long value) {
		return new Int2LongMaps.Singleton(key, value);
	}

	public static Int2LongMap synchronize(Int2LongMap m) {
		return new Int2LongMaps.SynchronizedMap(m);
	}

	public static Int2LongMap synchronize(Int2LongMap m, Object sync) {
		return new Int2LongMaps.SynchronizedMap(m, sync);
	}

	public static Int2LongMap unmodifiable(Int2LongMap m) {
		return new Int2LongMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Int2LongMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(long v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Integer, ? extends Long> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> int2LongEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public IntSet keySet() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public LongCollection values() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Int2LongMaps.EMPTY_MAP;
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

	public static class Singleton extends Int2LongFunctions.Singleton implements Int2LongMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient IntSet keys;
		protected transient LongCollection values;

		protected Singleton(int key, long value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(long v) {
			return this.value == v;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return (Long)ov == this.value;
		}

		public void putAll(Map<? extends Integer, ? extends Long> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> int2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, Long>> entrySet() {
			return this.int2LongEntrySet();
		}

		@Override
		public IntSet keySet() {
			if (this.keys == null) {
				this.keys = IntSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public LongCollection values() {
			if (this.values == null) {
				this.values = LongSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return this.key ^ HashCommon.long2int(this.value);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Int2LongMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2LongMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient IntSet keys;
		protected transient LongCollection values;

		protected SynchronizedMap(Int2LongMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Int2LongMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(long v) {
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

		public void putAll(Map<? extends Integer, ? extends Long> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> int2LongEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.int2LongEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, Long>> entrySet() {
			return this.int2LongEntrySet();
		}

		@Override
		public IntSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = IntSets.synchronize(this.map.keySet(), this.sync);
				}

				return this.keys;
			}
		}

		@Override
		public LongCollection values() {
			synchronized (this.sync) {
				return this.values == null ? LongCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public long getOrDefault(int key, long defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Integer, ? super Long> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Integer, ? super Long, ? extends Long> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public long putIfAbsent(int key, long value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(int key, long value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public long replace(int key, long value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(int key, long oldValue, long newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public long computeIfAbsent(int key, IntToLongFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public long computeIfAbsentNullable(int key, IntFunction<? extends Long> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public long computeIfAbsentPartial(int key, Int2LongFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public long computeIfPresent(int key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public long compute(int key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public long merge(int key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long getOrDefault(Object key, Long defaultValue) {
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
		public Long replace(Integer key, Long value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Integer key, Long oldValue, Long newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Long putIfAbsent(Integer key, Long value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Long computeIfAbsent(Integer key, Function<? super Integer, ? extends Long> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long computeIfPresent(Integer key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long compute(Integer key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long merge(Integer key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Int2LongMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2LongMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient IntSet keys;
		protected transient LongCollection values;

		protected UnmodifiableMap(Int2LongMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(long v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Integer, ? extends Long> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> int2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.int2LongEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, Long>> entrySet() {
			return this.int2LongEntrySet();
		}

		@Override
		public IntSet keySet() {
			if (this.keys == null) {
				this.keys = IntSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public LongCollection values() {
			return this.values == null ? LongCollections.unmodifiable(this.map.values()) : this.values;
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
		public long getOrDefault(int key, long defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Integer, ? super Long> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Integer, ? super Long, ? extends Long> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long putIfAbsent(int key, long value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(int key, long value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long replace(int key, long value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(int key, long oldValue, long newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfAbsent(int key, IntToLongFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfAbsentNullable(int key, IntFunction<? extends Long> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfAbsentPartial(int key, Int2LongFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfPresent(int key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long compute(int key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long merge(int key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long getOrDefault(Object key, Long defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long replace(Integer key, Long value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Integer key, Long oldValue, Long newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long putIfAbsent(Integer key, Long value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long computeIfAbsent(Integer key, Function<? super Integer, ? extends Long> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long computeIfPresent(Integer key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long compute(Integer key, BiFunction<? super Integer, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long merge(Integer key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
