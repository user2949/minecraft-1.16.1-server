package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2LongFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.floats.Float2LongFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.floats.Float2LongFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.floats.Float2LongMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2LongMap.FastEntrySet;
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
import java.util.function.DoubleFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

public final class Float2LongMaps {
	public static final Float2LongMaps.EmptyMap EMPTY_MAP = new Float2LongMaps.EmptyMap();

	private Float2LongMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Float2LongMap map) {
		ObjectSet<Entry> entries = map.float2LongEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Float2LongMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.float2LongEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Float2LongMap map) {
		final ObjectSet<Entry> entries = map.float2LongEntrySet();
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

	public static Float2LongMap singleton(float key, long value) {
		return new Float2LongMaps.Singleton(key, value);
	}

	public static Float2LongMap singleton(Float key, Long value) {
		return new Float2LongMaps.Singleton(key, value);
	}

	public static Float2LongMap synchronize(Float2LongMap m) {
		return new Float2LongMaps.SynchronizedMap(m);
	}

	public static Float2LongMap synchronize(Float2LongMap m, Object sync) {
		return new Float2LongMaps.SynchronizedMap(m, sync);
	}

	public static Float2LongMap unmodifiable(Float2LongMap m) {
		return new Float2LongMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Float2LongMap, Serializable, Cloneable {
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

		public void putAll(Map<? extends Float, ? extends Long> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2LongEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public FloatSet keySet() {
			return FloatSets.EMPTY_SET;
		}

		@Override
		public LongCollection values() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Float2LongMaps.EMPTY_MAP;
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

	public static class Singleton extends Float2LongFunctions.Singleton implements Float2LongMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient LongCollection values;

		protected Singleton(float key, long value) {
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

		public void putAll(Map<? extends Float, ? extends Long> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Long>> entrySet() {
			return this.float2LongEntrySet();
		}

		@Override
		public FloatSet keySet() {
			if (this.keys == null) {
				this.keys = FloatSets.singleton(this.key);
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
			return HashCommon.float2int(this.key) ^ HashCommon.long2int(this.value);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Float2LongMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2LongMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient LongCollection values;

		protected SynchronizedMap(Float2LongMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Float2LongMap m) {
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

		public void putAll(Map<? extends Float, ? extends Long> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> float2LongEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.float2LongEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Long>> entrySet() {
			return this.float2LongEntrySet();
		}

		@Override
		public FloatSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = FloatSets.synchronize(this.map.keySet(), this.sync);
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
		public long getOrDefault(float key, long defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Float, ? super Long> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Float, ? super Long, ? extends Long> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public long putIfAbsent(float key, long value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(float key, long value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public long replace(float key, long value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(float key, long oldValue, long newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public long computeIfAbsent(float key, DoubleToLongFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public long computeIfAbsentNullable(float key, DoubleFunction<? extends Long> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public long computeIfAbsentPartial(float key, Float2LongFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public long computeIfPresent(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public long compute(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public long merge(float key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
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
		public Long replace(Float key, Long value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Float key, Long oldValue, Long newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Long putIfAbsent(Float key, Long value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Long computeIfAbsent(Float key, Function<? super Float, ? extends Long> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long computeIfPresent(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long compute(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Long merge(Float key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Float2LongMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2LongMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient LongCollection values;

		protected UnmodifiableMap(Float2LongMap m) {
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

		public void putAll(Map<? extends Float, ? extends Long> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2LongEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.float2LongEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Long>> entrySet() {
			return this.float2LongEntrySet();
		}

		@Override
		public FloatSet keySet() {
			if (this.keys == null) {
				this.keys = FloatSets.unmodifiable(this.map.keySet());
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
		public long getOrDefault(float key, long defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Float, ? super Long> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Float, ? super Long, ? extends Long> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long putIfAbsent(float key, long value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(float key, long value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long replace(float key, long value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(float key, long oldValue, long newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfAbsent(float key, DoubleToLongFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfAbsentNullable(float key, DoubleFunction<? extends Long> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfAbsentPartial(float key, Float2LongFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long computeIfPresent(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long compute(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long merge(float key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
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
		public Long replace(Float key, Long value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Float key, Long oldValue, Long newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long putIfAbsent(Float key, Long value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long computeIfAbsent(Float key, Function<? super Float, ? extends Long> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long computeIfPresent(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long compute(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long merge(Float key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
