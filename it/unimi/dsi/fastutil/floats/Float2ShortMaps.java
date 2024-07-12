package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2ShortFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.floats.Float2ShortFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.floats.Float2ShortFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.floats.Float2ShortMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
import it.unimi.dsi.fastutil.shorts.ShortSets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Float2ShortMaps {
	public static final Float2ShortMaps.EmptyMap EMPTY_MAP = new Float2ShortMaps.EmptyMap();

	private Float2ShortMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Float2ShortMap map) {
		ObjectSet<Entry> entries = map.float2ShortEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Float2ShortMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.float2ShortEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Float2ShortMap map) {
		final ObjectSet<Entry> entries = map.float2ShortEntrySet();
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

	public static Float2ShortMap singleton(float key, short value) {
		return new Float2ShortMaps.Singleton(key, value);
	}

	public static Float2ShortMap singleton(Float key, Short value) {
		return new Float2ShortMaps.Singleton(key, value);
	}

	public static Float2ShortMap synchronize(Float2ShortMap m) {
		return new Float2ShortMaps.SynchronizedMap(m);
	}

	public static Float2ShortMap synchronize(Float2ShortMap m, Object sync) {
		return new Float2ShortMaps.SynchronizedMap(m, sync);
	}

	public static Float2ShortMap unmodifiable(Float2ShortMap m) {
		return new Float2ShortMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Float2ShortMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(short v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Float, ? extends Short> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2ShortEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public FloatSet keySet() {
			return FloatSets.EMPTY_SET;
		}

		@Override
		public ShortCollection values() {
			return ShortSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Float2ShortMaps.EMPTY_MAP;
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

	public static class Singleton extends Float2ShortFunctions.Singleton implements Float2ShortMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient ShortCollection values;

		protected Singleton(float key, short value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(short v) {
			return this.value == v;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return (Short)ov == this.value;
		}

		public void putAll(Map<? extends Float, ? extends Short> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2ShortEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Short>> entrySet() {
			return this.float2ShortEntrySet();
		}

		@Override
		public FloatSet keySet() {
			if (this.keys == null) {
				this.keys = FloatSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public ShortCollection values() {
			if (this.values == null) {
				this.values = ShortSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return HashCommon.float2int(this.key) ^ this.value;
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

	public static class SynchronizedMap extends SynchronizedFunction implements Float2ShortMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2ShortMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient ShortCollection values;

		protected SynchronizedMap(Float2ShortMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Float2ShortMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(short v) {
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

		public void putAll(Map<? extends Float, ? extends Short> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> float2ShortEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.float2ShortEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Short>> entrySet() {
			return this.float2ShortEntrySet();
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
		public ShortCollection values() {
			synchronized (this.sync) {
				return this.values == null ? ShortCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public short getOrDefault(float key, short defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Float, ? super Short> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Float, ? super Short, ? extends Short> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public short putIfAbsent(float key, short value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(float key, short value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public short replace(float key, short value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(float key, short oldValue, short newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public short computeIfAbsent(float key, DoubleToIntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public short computeIfAbsentNullable(float key, DoubleFunction<? extends Short> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public short computeIfAbsentPartial(float key, Float2ShortFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public short computeIfPresent(float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public short compute(float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public short merge(float key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Short getOrDefault(Object key, Short defaultValue) {
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
		public Short replace(Float key, Short value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Float key, Short oldValue, Short newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Short putIfAbsent(Float key, Short value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Short computeIfAbsent(Float key, Function<? super Float, ? extends Short> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Short computeIfPresent(Float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Short compute(Float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Short merge(Float key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Float2ShortMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2ShortMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient ShortCollection values;

		protected UnmodifiableMap(Float2ShortMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(short v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Float, ? extends Short> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2ShortEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.float2ShortEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Short>> entrySet() {
			return this.float2ShortEntrySet();
		}

		@Override
		public FloatSet keySet() {
			if (this.keys == null) {
				this.keys = FloatSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public ShortCollection values() {
			return this.values == null ? ShortCollections.unmodifiable(this.map.values()) : this.values;
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
		public short getOrDefault(float key, short defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Float, ? super Short> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Float, ? super Short, ? extends Short> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short putIfAbsent(float key, short value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(float key, short value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short replace(float key, short value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(float key, short oldValue, short newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeIfAbsent(float key, DoubleToIntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeIfAbsentNullable(float key, DoubleFunction<? extends Short> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeIfAbsentPartial(float key, Float2ShortFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeIfPresent(float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short compute(float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short merge(float key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short getOrDefault(Object key, Short defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short replace(Float key, Short value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Float key, Short oldValue, Short newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short putIfAbsent(Float key, Short value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short computeIfAbsent(Float key, Function<? super Float, ? extends Short> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short computeIfPresent(Float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short compute(Float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short merge(Float key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
