package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ShortFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.objects.Object2ShortFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.objects.Object2ShortFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.FastEntrySet;
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
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Object2ShortMaps {
	public static final Object2ShortMaps.EmptyMap EMPTY_MAP = new Object2ShortMaps.EmptyMap();

	private Object2ShortMaps() {
	}

	public static <K> ObjectIterator<Entry<K>> fastIterator(Object2ShortMap<K> map) {
		ObjectSet<Entry<K>> entries = map.object2ShortEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> void fastForEach(Object2ShortMap<K> map, Consumer<? super Entry<K>> consumer) {
		ObjectSet<Entry<K>> entries = map.object2ShortEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <K> ObjectIterable<Entry<K>> fastIterable(Object2ShortMap<K> map) {
		final ObjectSet<Entry<K>> entries = map.object2ShortEntrySet();
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

	public static <K> Object2ShortMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Object2ShortMap<K> singleton(K key, short value) {
		return new Object2ShortMaps.Singleton<>(key, value);
	}

	public static <K> Object2ShortMap<K> singleton(K key, Short value) {
		return new Object2ShortMaps.Singleton<>(key, value);
	}

	public static <K> Object2ShortMap<K> synchronize(Object2ShortMap<K> m) {
		return new Object2ShortMaps.SynchronizedMap<>(m);
	}

	public static <K> Object2ShortMap<K> synchronize(Object2ShortMap<K> m, Object sync) {
		return new Object2ShortMaps.SynchronizedMap<>(m, sync);
	}

	public static <K> Object2ShortMap<K> unmodifiable(Object2ShortMap<K> m) {
		return new Object2ShortMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<K> extends EmptyFunction<K> implements Object2ShortMap<K>, Serializable, Cloneable {
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

		public void putAll(Map<? extends K, ? extends Short> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> object2ShortEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ObjectSet<K> keySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ShortCollection values() {
			return ShortSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Object2ShortMaps.EMPTY_MAP;
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

	public static class Singleton<K> extends Object2ShortFunctions.Singleton<K> implements Object2ShortMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ObjectSet<K> keys;
		protected transient ShortCollection values;

		protected Singleton(K key, short value) {
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

		public void putAll(Map<? extends K, ? extends Short> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> object2ShortEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Short>> entrySet() {
			return this.object2ShortEntrySet();
		}

		@Override
		public ObjectSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSets.singleton(this.key);
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
			return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
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

	public static class SynchronizedMap<K> extends SynchronizedFunction<K> implements Object2ShortMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ShortMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ObjectSet<K> keys;
		protected transient ShortCollection values;

		protected SynchronizedMap(Object2ShortMap<K> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Object2ShortMap<K> m) {
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

		public void putAll(Map<? extends K, ? extends Short> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<K>> object2ShortEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.object2ShortEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Short>> entrySet() {
			return this.object2ShortEntrySet();
		}

		@Override
		public ObjectSet<K> keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync);
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
		public short getOrDefault(Object key, short defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super K, ? super Short> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super K, ? super Short, ? extends Short> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public short putIfAbsent(K key, short value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(Object key, short value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public short replace(K key, short value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(K key, short oldValue, short newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeShortIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public short computeShortIfAbsentPartial(K key, Object2ShortFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeShortIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeShortIfPresent(key, remappingFunction);
			}
		}

		@Override
		public short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeShort(key, remappingFunction);
			}
		}

		@Override
		public short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.mergeShort(key, value, remappingFunction);
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
		public Short replace(K key, Short value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(K key, Short oldValue, Short newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Short putIfAbsent(K key, Short value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		public Short computeIfAbsent(K key, Function<? super K, ? extends Short> mappingFunction) {
			synchronized (this.sync) {
				return (Short)this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		public Short computeIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return (Short)this.map.computeIfPresent(key, remappingFunction);
			}
		}

		public Short compute(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return (Short)this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<K> extends UnmodifiableFunction<K> implements Object2ShortMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ShortMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ObjectSet<K> keys;
		protected transient ShortCollection values;

		protected UnmodifiableMap(Object2ShortMap<K> m) {
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

		public void putAll(Map<? extends K, ? extends Short> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> object2ShortEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.object2ShortEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Short>> entrySet() {
			return this.object2ShortEntrySet();
		}

		@Override
		public ObjectSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSets.unmodifiable(this.map.keySet());
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
		public short getOrDefault(Object key, short defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super K, ? super Short> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super K, ? super Short, ? extends Short> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short putIfAbsent(K key, short value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object key, short value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short replace(K key, short value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(K key, short oldValue, short newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeShortIfAbsentPartial(K key, Object2ShortFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
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
		public Short replace(K key, Short value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(K key, Short oldValue, Short newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short putIfAbsent(K key, Short value) {
			throw new UnsupportedOperationException();
		}

		public Short computeIfAbsent(K key, Function<? super K, ? extends Short> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Short computeIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Short compute(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
