package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSets;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2FloatFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.objects.Reference2FloatFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.objects.Reference2FloatFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public final class Reference2FloatMaps {
	public static final Reference2FloatMaps.EmptyMap EMPTY_MAP = new Reference2FloatMaps.EmptyMap();

	private Reference2FloatMaps() {
	}

	public static <K> ObjectIterator<Entry<K>> fastIterator(Reference2FloatMap<K> map) {
		ObjectSet<Entry<K>> entries = map.reference2FloatEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> void fastForEach(Reference2FloatMap<K> map, Consumer<? super Entry<K>> consumer) {
		ObjectSet<Entry<K>> entries = map.reference2FloatEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <K> ObjectIterable<Entry<K>> fastIterable(Reference2FloatMap<K> map) {
		final ObjectSet<Entry<K>> entries = map.reference2FloatEntrySet();
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

	public static <K> Reference2FloatMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2FloatMap<K> singleton(K key, float value) {
		return new Reference2FloatMaps.Singleton<>(key, value);
	}

	public static <K> Reference2FloatMap<K> singleton(K key, Float value) {
		return new Reference2FloatMaps.Singleton<>(key, value);
	}

	public static <K> Reference2FloatMap<K> synchronize(Reference2FloatMap<K> m) {
		return new Reference2FloatMaps.SynchronizedMap<>(m);
	}

	public static <K> Reference2FloatMap<K> synchronize(Reference2FloatMap<K> m, Object sync) {
		return new Reference2FloatMaps.SynchronizedMap<>(m, sync);
	}

	public static <K> Reference2FloatMap<K> unmodifiable(Reference2FloatMap<K> m) {
		return new Reference2FloatMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<K> extends EmptyFunction<K> implements Reference2FloatMap<K>, Serializable, Cloneable {
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

		public void putAll(Map<? extends K, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2FloatEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ReferenceSet<K> keySet() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public FloatCollection values() {
			return FloatSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Reference2FloatMaps.EMPTY_MAP;
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

	public static class Singleton<K> extends Reference2FloatFunctions.Singleton<K> implements Reference2FloatMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient FloatCollection values;

		protected Singleton(K key, float value) {
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

		public void putAll(Map<? extends K, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.reference2FloatEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.singleton(this.key);
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
			return System.identityHashCode(this.key) ^ HashCommon.float2int(this.value);
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

	public static class SynchronizedMap<K> extends SynchronizedFunction<K> implements Reference2FloatMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2FloatMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient FloatCollection values;

		protected SynchronizedMap(Reference2FloatMap<K> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Reference2FloatMap<K> m) {
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

		public void putAll(Map<? extends K, ? extends Float> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<K>> reference2FloatEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.reference2FloatEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.reference2FloatEntrySet();
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
		public float getOrDefault(Object key, float defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super K, ? super Float> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super K, ? super Float, ? extends Float> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public float putIfAbsent(K key, float value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(Object key, float value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public float replace(K key, float value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(K key, float oldValue, float newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeFloatIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeFloatIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeFloatIfPresent(key, remappingFunction);
			}
		}

		@Override
		public float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeFloat(key, remappingFunction);
			}
		}

		@Override
		public float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.mergeFloat(key, value, remappingFunction);
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
		public Float replace(K key, Float value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(K key, Float oldValue, Float newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Float putIfAbsent(K key, Float value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		public Float computeIfAbsent(K key, Function<? super K, ? extends Float> mappingFunction) {
			synchronized (this.sync) {
				return (Float)this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		public Float computeIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return (Float)this.map.computeIfPresent(key, remappingFunction);
			}
		}

		public Float compute(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return (Float)this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<K> extends UnmodifiableFunction<K> implements Reference2FloatMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2FloatMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient FloatCollection values;

		protected UnmodifiableMap(Reference2FloatMap<K> m) {
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

		public void putAll(Map<? extends K, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.reference2FloatEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Float>> entrySet() {
			return this.reference2FloatEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.unmodifiable(this.map.keySet());
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
		public float getOrDefault(Object key, float defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super K, ? super Float> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super K, ? super Float, ? extends Float> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float putIfAbsent(K key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float replace(K key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(K key, float oldValue, float newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
		public Float replace(K key, Float value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(K key, Float oldValue, Float newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float putIfAbsent(K key, Float value) {
			throw new UnsupportedOperationException();
		}

		public Float computeIfAbsent(K key, Function<? super K, ? extends Float> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Float computeIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Float compute(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
