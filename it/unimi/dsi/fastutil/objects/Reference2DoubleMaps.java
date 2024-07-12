package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2DoubleFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.objects.Reference2DoubleFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.objects.Reference2DoubleFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public final class Reference2DoubleMaps {
	public static final Reference2DoubleMaps.EmptyMap EMPTY_MAP = new Reference2DoubleMaps.EmptyMap();

	private Reference2DoubleMaps() {
	}

	public static <K> ObjectIterator<Entry<K>> fastIterator(Reference2DoubleMap<K> map) {
		ObjectSet<Entry<K>> entries = map.reference2DoubleEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> void fastForEach(Reference2DoubleMap<K> map, Consumer<? super Entry<K>> consumer) {
		ObjectSet<Entry<K>> entries = map.reference2DoubleEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <K> ObjectIterable<Entry<K>> fastIterable(Reference2DoubleMap<K> map) {
		final ObjectSet<Entry<K>> entries = map.reference2DoubleEntrySet();
		return (ObjectIterable<Entry<K>>)(entries instanceof FastEntrySet ? new ObjectIterable<Entry<K>>() {
			@Override
			public ObjectIterator<Entry<K>> iterator() {
				return ((FastEntrySet)entries).fastIterator();
			}

			public void forEach(Consumer<? super Entry<K>> consumer) {
				((FastEntrySet)entries).fastForEach(consumer);
			}
		} : entries);
	}

	public static <K> Reference2DoubleMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2DoubleMap<K> singleton(K key, double value) {
		return new Reference2DoubleMaps.Singleton<>(key, value);
	}

	public static <K> Reference2DoubleMap<K> singleton(K key, Double value) {
		return new Reference2DoubleMaps.Singleton<>(key, value);
	}

	public static <K> Reference2DoubleMap<K> synchronize(Reference2DoubleMap<K> m) {
		return new Reference2DoubleMaps.SynchronizedMap<>(m);
	}

	public static <K> Reference2DoubleMap<K> synchronize(Reference2DoubleMap<K> m, Object sync) {
		return new Reference2DoubleMaps.SynchronizedMap<>(m, sync);
	}

	public static <K> Reference2DoubleMap<K> unmodifiable(Reference2DoubleMap<K> m) {
		return new Reference2DoubleMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<K> extends EmptyFunction<K> implements Reference2DoubleMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(double v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends K, ? extends Double> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2DoubleEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ReferenceSet<K> keySet() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public DoubleCollection values() {
			return DoubleSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Reference2DoubleMaps.EMPTY_MAP;
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

	public static class Singleton<K> extends Reference2DoubleFunctions.Singleton<K> implements Reference2DoubleMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient DoubleCollection values;

		protected Singleton(K key, double value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(double v) {
			return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return Double.doubleToLongBits((Double)ov) == Double.doubleToLongBits(this.value);
		}

		public void putAll(Map<? extends K, ? extends Double> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2DoubleEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Double>> entrySet() {
			return this.reference2DoubleEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public DoubleCollection values() {
			if (this.values == null) {
				this.values = DoubleSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return System.identityHashCode(this.key) ^ HashCommon.double2int(this.value);
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

	public static class SynchronizedMap<K> extends SynchronizedFunction<K> implements Reference2DoubleMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2DoubleMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient DoubleCollection values;

		protected SynchronizedMap(Reference2DoubleMap<K> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Reference2DoubleMap<K> m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(double v) {
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

		public void putAll(Map<? extends K, ? extends Double> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<K>> reference2DoubleEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.reference2DoubleEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Double>> entrySet() {
			return this.reference2DoubleEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync);
				}

				return this.keys;
			}
		}

		@Override
		public DoubleCollection values() {
			synchronized (this.sync) {
				return this.values == null ? DoubleCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public double getOrDefault(Object key, double defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super K, ? super Double> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public double putIfAbsent(K key, double value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(Object key, double value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public double replace(K key, double value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(K key, double oldValue, double newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeDoubleIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public double computeDoubleIfAbsentPartial(K key, Reference2DoubleFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeDoubleIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeDoubleIfPresent(key, remappingFunction);
			}
		}

		@Override
		public double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeDouble(key, remappingFunction);
			}
		}

		@Override
		public double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
			synchronized (this.sync) {
				return this.map.mergeDouble(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Double getOrDefault(Object key, Double defaultValue) {
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
		public Double replace(K key, Double value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(K key, Double oldValue, Double newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Double putIfAbsent(K key, Double value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		public Double computeIfAbsent(K key, Function<? super K, ? extends Double> mappingFunction) {
			synchronized (this.sync) {
				return (Double)this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		public Double computeIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			synchronized (this.sync) {
				return (Double)this.map.computeIfPresent(key, remappingFunction);
			}
		}

		public Double compute(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			synchronized (this.sync) {
				return (Double)this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Double merge(K key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<K> extends UnmodifiableFunction<K> implements Reference2DoubleMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2DoubleMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient DoubleCollection values;

		protected UnmodifiableMap(Reference2DoubleMap<K> m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(double v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends K, ? extends Double> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2DoubleEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.reference2DoubleEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Double>> entrySet() {
			return this.reference2DoubleEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public DoubleCollection values() {
			return this.values == null ? DoubleCollections.unmodifiable(this.map.values()) : this.values;
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
		public double getOrDefault(Object key, double defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super K, ? super Double> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double putIfAbsent(K key, double value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object key, double value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double replace(K key, double value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(K key, double oldValue, double newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double computeDoubleIfAbsentPartial(K key, Reference2DoubleFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double getOrDefault(Object key, Double defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double replace(K key, Double value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(K key, Double oldValue, Double newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double putIfAbsent(K key, Double value) {
			throw new UnsupportedOperationException();
		}

		public Double computeIfAbsent(K key, Function<? super K, ? extends Double> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Double computeIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Double compute(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double merge(K key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
