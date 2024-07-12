package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;

public final class Long2ObjectMaps {
	public static final Long2ObjectMaps.EmptyMap EMPTY_MAP = new Long2ObjectMaps.EmptyMap();

	private Long2ObjectMaps() {
	}

	public static <V> ObjectIterator<Entry<V>> fastIterator(Long2ObjectMap<V> map) {
		ObjectSet<Entry<V>> entries = map.long2ObjectEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <V> void fastForEach(Long2ObjectMap<V> map, Consumer<? super Entry<V>> consumer) {
		ObjectSet<Entry<V>> entries = map.long2ObjectEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <V> ObjectIterable<Entry<V>> fastIterable(Long2ObjectMap<V> map) {
		final ObjectSet<Entry<V>> entries = map.long2ObjectEntrySet();
		return (ObjectIterable<Entry<V>>)(entries instanceof FastEntrySet ? new ObjectIterable<Entry<V>>() {
			@Override
			public ObjectIterator<Entry<V>> iterator() {
				return ((FastEntrySet)entries).fastIterator();
			}

			public void forEach(Consumer<? super Entry<V>> consumer) {
				((FastEntrySet)entries).fastForEach(consumer);
			}
		} : entries);
	}

	public static <V> Long2ObjectMap<V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <V> Long2ObjectMap<V> singleton(long key, V value) {
		return new Long2ObjectMaps.Singleton<>(key, value);
	}

	public static <V> Long2ObjectMap<V> singleton(Long key, V value) {
		return new Long2ObjectMaps.Singleton<>(key, value);
	}

	public static <V> Long2ObjectMap<V> synchronize(Long2ObjectMap<V> m) {
		return new Long2ObjectMaps.SynchronizedMap<>(m);
	}

	public static <V> Long2ObjectMap<V> synchronize(Long2ObjectMap<V> m, Object sync) {
		return new Long2ObjectMaps.SynchronizedMap<>(m, sync);
	}

	public static <V> Long2ObjectMap<V> unmodifiable(Long2ObjectMap<V> m) {
		return new Long2ObjectMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<V> extends EmptyFunction<V> implements Long2ObjectMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		public boolean containsValue(Object v) {
			return false;
		}

		public void putAll(Map<? extends Long, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> long2ObjectEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public LongSet keySet() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public ObjectCollection<V> values() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Long2ObjectMaps.EMPTY_MAP;
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

	public static class Singleton<V> extends Long2ObjectFunctions.Singleton<V> implements Long2ObjectMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient LongSet keys;
		protected transient ObjectCollection<V> values;

		protected Singleton(long key, V value) {
			super(key, value);
		}

		public boolean containsValue(Object v) {
			return Objects.equals(this.value, v);
		}

		public void putAll(Map<? extends Long, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> long2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, V>> entrySet() {
			return this.long2ObjectEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public ObjectCollection<V> values() {
			if (this.values == null) {
				this.values = ObjectSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return HashCommon.long2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
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

	public static class SynchronizedMap<V> extends SynchronizedFunction<V> implements Long2ObjectMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2ObjectMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient LongSet keys;
		protected transient ObjectCollection<V> values;

		protected SynchronizedMap(Long2ObjectMap<V> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Long2ObjectMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			synchronized (this.sync) {
				return this.map.containsValue(v);
			}
		}

		public void putAll(Map<? extends Long, ? extends V> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<V>> long2ObjectEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.long2ObjectEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, V>> entrySet() {
			return this.long2ObjectEntrySet();
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
		public ObjectCollection<V> values() {
			synchronized (this.sync) {
				return this.values == null ? ObjectCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public V getOrDefault(long key, V defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Long, ? super V> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public V putIfAbsent(long key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(long key, Object value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public V replace(long key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(long key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public V computeIfAbsentPartial(long key, Long2ObjectFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V getOrDefault(Object key, V defaultValue) {
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
		public V replace(Long key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Long key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public V putIfAbsent(Long key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Long key, Function<? super Long, ? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public V computeIfPresent(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V compute(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V merge(Long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<V> extends UnmodifiableFunction<V> implements Long2ObjectMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2ObjectMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient LongSet keys;
		protected transient ObjectCollection<V> values;

		protected UnmodifiableMap(Long2ObjectMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			return this.map.containsValue(v);
		}

		public void putAll(Map<? extends Long, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> long2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.long2ObjectEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, V>> entrySet() {
			return this.long2ObjectEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public ObjectCollection<V> values() {
			return this.values == null ? ObjectCollections.unmodifiable(this.map.values()) : this.values;
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
		public V getOrDefault(long key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Long, ? super V> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V putIfAbsent(long key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(long key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V replace(long key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(long key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsentPartial(long key, Long2ObjectFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V getOrDefault(Object key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V replace(Long key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Long key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V putIfAbsent(Long key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Long key, Function<? super Long, ? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfPresent(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V compute(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V merge(Long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
