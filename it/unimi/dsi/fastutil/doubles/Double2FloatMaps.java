package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap.Entry;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSets;
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
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public final class Double2FloatMaps {
	public static final Double2FloatMaps.EmptyMap EMPTY_MAP = new Double2FloatMaps.EmptyMap();

	private Double2FloatMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Double2FloatMap map) {
		ObjectSet<Entry> entries = map.double2FloatEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Double2FloatMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.double2FloatEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Double2FloatMap map) {
		final ObjectSet<Entry> entries = map.double2FloatEntrySet();
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

	public static Double2FloatMap singleton(double key, float value) {
		return new Double2FloatMaps.Singleton(key, value);
	}

	public static Double2FloatMap singleton(Double key, Float value) {
		return new Double2FloatMaps.Singleton(key, value);
	}

	public static Double2FloatMap synchronize(Double2FloatMap m) {
		return new Double2FloatMaps.SynchronizedMap(m);
	}

	public static Double2FloatMap synchronize(Double2FloatMap m, Object sync) {
		return new Double2FloatMaps.SynchronizedMap(m, sync);
	}

	public static Double2FloatMap unmodifiable(Double2FloatMap m) {
		return new Double2FloatMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Double2FloatMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(float v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Double, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> double2FloatEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public DoubleSet keySet() {
			return DoubleSets.EMPTY_SET;
		}

		@Override
		public FloatCollection values() {
			return FloatSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Double2FloatMaps.EMPTY_MAP;
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

	public static class Singleton extends Double2FloatFunctions.Singleton implements Double2FloatMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient DoubleSet keys;
		protected transient FloatCollection values;

		protected Singleton(double key, float value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(float v) {
			return Float.floatToIntBits(this.value) == Float.floatToIntBits(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return Float.floatToIntBits((Float)ov) == Float.floatToIntBits(this.value);
		}

		public void putAll(Map<? extends Double, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> double2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Double, Float>> entrySet() {
			return this.double2FloatEntrySet();
		}

		@Override
		public DoubleSet keySet() {
			if (this.keys == null) {
				this.keys = DoubleSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public FloatCollection values() {
			if (this.values == null) {
				this.values = FloatSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return HashCommon.double2int(this.key) ^ HashCommon.float2int(this.value);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Double2FloatMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Double2FloatMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient DoubleSet keys;
		protected transient FloatCollection values;

		protected SynchronizedMap(Double2FloatMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Double2FloatMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(float v) {
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

		public void putAll(Map<? extends Double, ? extends Float> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> double2FloatEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.double2FloatEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Double, Float>> entrySet() {
			return this.double2FloatEntrySet();
		}

		@Override
		public DoubleSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync);
				}

				return this.keys;
			}
		}

		@Override
		public FloatCollection values() {
			synchronized (this.sync) {
				return this.values == null ? FloatCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public float getOrDefault(double key, float defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Double, ? super Float> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Double, ? super Float, ? extends Float> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public float putIfAbsent(double key, float value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(double key, float value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public float replace(double key, float value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(double key, float oldValue, float newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public float computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public float computeIfAbsentNullable(double key, DoubleFunction<? extends Float> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public float computeIfAbsentPartial(double key, Double2FloatFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public float computeIfPresent(double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public float compute(double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public float merge(double key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float getOrDefault(Object key, Float defaultValue) {
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
		public Float replace(Double key, Float value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Double key, Float oldValue, Float newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Float putIfAbsent(Double key, Float value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Float computeIfAbsent(Double key, Function<? super Double, ? extends Float> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float computeIfPresent(Double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float compute(Double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float merge(Double key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Double2FloatMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Double2FloatMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient DoubleSet keys;
		protected transient FloatCollection values;

		protected UnmodifiableMap(Double2FloatMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(float v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Double, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> double2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.double2FloatEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Double, Float>> entrySet() {
			return this.double2FloatEntrySet();
		}

		@Override
		public DoubleSet keySet() {
			if (this.keys == null) {
				this.keys = DoubleSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public FloatCollection values() {
			return this.values == null ? FloatCollections.unmodifiable(this.map.values()) : this.values;
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
		public float getOrDefault(double key, float defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Double, ? super Float> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Double, ? super Float, ? extends Float> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float putIfAbsent(double key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(double key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float replace(double key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(double key, float oldValue, float newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfAbsentNullable(double key, DoubleFunction<? extends Float> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfAbsentPartial(double key, Double2FloatFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfPresent(double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float compute(double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float merge(double key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float getOrDefault(Object key, Float defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float replace(Double key, Float value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Double key, Float oldValue, Float newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float putIfAbsent(Double key, Float value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float computeIfAbsent(Double key, Function<? super Double, ? extends Float> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float computeIfPresent(Double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float compute(Double key, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float merge(Double key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
