package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.floats.Float2IntFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.floats.Float2IntFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.floats.Float2IntFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.floats.Float2IntMap.Entry;
import it.unimi.dsi.fastutil.floats.Float2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntSets;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Float2IntMaps {
	public static final Float2IntMaps.EmptyMap EMPTY_MAP = new Float2IntMaps.EmptyMap();

	private Float2IntMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Float2IntMap map) {
		ObjectSet<Entry> entries = map.float2IntEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Float2IntMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.float2IntEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Float2IntMap map) {
		final ObjectSet<Entry> entries = map.float2IntEntrySet();
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

	public static Float2IntMap singleton(float key, int value) {
		return new Float2IntMaps.Singleton(key, value);
	}

	public static Float2IntMap singleton(Float key, Integer value) {
		return new Float2IntMaps.Singleton(key, value);
	}

	public static Float2IntMap synchronize(Float2IntMap m) {
		return new Float2IntMaps.SynchronizedMap(m);
	}

	public static Float2IntMap synchronize(Float2IntMap m, Object sync) {
		return new Float2IntMaps.SynchronizedMap(m, sync);
	}

	public static Float2IntMap unmodifiable(Float2IntMap m) {
		return new Float2IntMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Float2IntMap, Serializable, Cloneable {
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

		public void putAll(Map<? extends Float, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2IntEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public FloatSet keySet() {
			return FloatSets.EMPTY_SET;
		}

		@Override
		public IntCollection values() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Float2IntMaps.EMPTY_MAP;
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

	public static class Singleton extends Float2IntFunctions.Singleton implements Float2IntMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient IntCollection values;

		protected Singleton(float key, int value) {
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

		public void putAll(Map<? extends Float, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Integer>> entrySet() {
			return this.float2IntEntrySet();
		}

		@Override
		public FloatSet keySet() {
			if (this.keys == null) {
				this.keys = FloatSets.singleton(this.key);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Float2IntMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2IntMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient IntCollection values;

		protected SynchronizedMap(Float2IntMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Float2IntMap m) {
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

		public void putAll(Map<? extends Float, ? extends Integer> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> float2IntEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.float2IntEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Integer>> entrySet() {
			return this.float2IntEntrySet();
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
		public int getOrDefault(float key, int defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Float, ? super Integer> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Float, ? super Integer, ? extends Integer> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public int putIfAbsent(float key, int value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(float key, int value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public int replace(float key, int value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(float key, int oldValue, int newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public int computeIfAbsent(float key, DoubleToIntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public int computeIfAbsentNullable(float key, DoubleFunction<? extends Integer> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public int computeIfAbsentPartial(float key, Float2IntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public int computeIfPresent(float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public int compute(float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public int merge(float key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
		public Integer replace(Float key, Integer value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Float key, Integer oldValue, Integer newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Integer putIfAbsent(Float key, Integer value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Integer computeIfAbsent(Float key, Function<? super Float, ? extends Integer> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer computeIfPresent(Float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer compute(Float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer merge(Float key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Float2IntMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2IntMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient FloatSet keys;
		protected transient IntCollection values;

		protected UnmodifiableMap(Float2IntMap m) {
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

		public void putAll(Map<? extends Float, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> float2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.float2IntEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Float, Integer>> entrySet() {
			return this.float2IntEntrySet();
		}

		@Override
		public FloatSet keySet() {
			if (this.keys == null) {
				this.keys = FloatSets.unmodifiable(this.map.keySet());
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
		public int getOrDefault(float key, int defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Float, ? super Integer> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Float, ? super Integer, ? extends Integer> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int putIfAbsent(float key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(float key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int replace(float key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(float key, int oldValue, int newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfAbsent(float key, DoubleToIntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfAbsentNullable(float key, DoubleFunction<? extends Integer> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfAbsentPartial(float key, Float2IntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIfPresent(float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int compute(float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int merge(float key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
		public Integer replace(Float key, Integer value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Float key, Integer oldValue, Integer newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer putIfAbsent(Float key, Integer value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer computeIfAbsent(Float key, Function<? super Float, ? extends Integer> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer computeIfPresent(Float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer compute(Float key, BiFunction<? super Float, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer merge(Float key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
