package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class Object2ObjectMaps {
	public static final Object2ObjectMaps.EmptyMap EMPTY_MAP = new Object2ObjectMaps.EmptyMap();

	private Object2ObjectMaps() {
	}

	public static <K, V> ObjectIterator<Entry<K, V>> fastIterator(Object2ObjectMap<K, V> map) {
		ObjectSet<Entry<K, V>> entries = map.object2ObjectEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K, V> void fastForEach(Object2ObjectMap<K, V> map, Consumer<? super Entry<K, V>> consumer) {
		ObjectSet<Entry<K, V>> entries = map.object2ObjectEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <K, V> ObjectIterable<Entry<K, V>> fastIterable(Object2ObjectMap<K, V> map) {
		final ObjectSet<Entry<K, V>> entries = map.object2ObjectEntrySet();
		return (ObjectIterable<Entry<K, V>>)(entries instanceof FastEntrySet ? new ObjectIterable<Entry<K, V>>() {
			@Override
			public ObjectIterator<Entry<K, V>> iterator() {
				return ((FastEntrySet)entries).fastIterator();
			}

			public void forEach(Consumer<? super Entry<K, V>> consumer) {
				((FastEntrySet)entries).fastForEach(consumer);
			}
		} : entries);
	}

	public static <K, V> Object2ObjectMap<K, V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K, V> Object2ObjectMap<K, V> singleton(K key, V value) {
		return new Object2ObjectMaps.Singleton<>(key, value);
	}

	public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m) {
		return new Object2ObjectMaps.SynchronizedMap<>(m);
	}

	public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m, Object sync) {
		return new Object2ObjectMaps.SynchronizedMap<>(m, sync);
	}

	public static <K, V> Object2ObjectMap<K, V> unmodifiable(Object2ObjectMap<K, V> m) {
		return new Object2ObjectMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<K, V> extends EmptyFunction<K, V> implements Object2ObjectMap<K, V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		public boolean containsValue(Object v) {
			return false;
		}

		public void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K, V>> object2ObjectEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ObjectSet<K> keySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ObjectCollection<V> values() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Object2ObjectMaps.EMPTY_MAP;
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

	public static class Singleton<K, V> extends Object2ObjectFunctions.Singleton<K, V> implements Object2ObjectMap<K, V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<K, V>> entries;
		protected transient ObjectSet<K> keys;
		protected transient ObjectCollection<V> values;

		protected Singleton(K key, V value) {
			super(key, value);
		}

		public boolean containsValue(Object v) {
			return Objects.equals(this.value, v);
		}

		public void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K, V>> object2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Override
		public ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.object2ObjectEntrySet();
		}

		@Override
		public ObjectSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSets.singleton(this.key);
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
			return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
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

	public static class SynchronizedMap<K, V> extends SynchronizedFunction<K, V> implements Object2ObjectMap<K, V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ObjectMap<K, V> map;
		protected transient ObjectSet<Entry<K, V>> entries;
		protected transient ObjectSet<K> keys;
		protected transient ObjectCollection<V> values;

		protected SynchronizedMap(Object2ObjectMap<K, V> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Object2ObjectMap<K, V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			synchronized (this.sync) {
				return this.map.containsValue(v);
			}
		}

		public void putAll(Map<? extends K, ? extends V> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<K, V>> object2ObjectEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.object2ObjectEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Override
		public ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.object2ObjectEntrySet();
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

		public V getOrDefault(Object key, V defaultValue) {
			synchronized (this.sync) {
				return (V)this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super K, ? super V> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		public V putIfAbsent(K key, V value) {
			synchronized (this.sync) {
				return (V)this.map.putIfAbsent(key, value);
			}
		}

		public boolean remove(Object key, Object value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		public V replace(K key, V value) {
			synchronized (this.sync) {
				return (V)this.map.replace(key, value);
			}
		}

		public boolean replace(K key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return (V)this.map.computeIfPresent(key, remappingFunction);
			}
		}

		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return (V)this.map.compute(key, remappingFunction);
			}
		}

		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return (V)this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<K, V> extends UnmodifiableFunction<K, V> implements Object2ObjectMap<K, V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ObjectMap<K, V> map;
		protected transient ObjectSet<Entry<K, V>> entries;
		protected transient ObjectSet<K> keys;
		protected transient ObjectCollection<V> values;

		protected UnmodifiableMap(Object2ObjectMap<K, V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			return this.map.containsValue(v);
		}

		public void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K, V>> object2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.object2ObjectEntrySet());
			}

			return this.entries;
		}

		@Override
		public ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.object2ObjectEntrySet();
		}

		@Override
		public ObjectSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ObjectSets.unmodifiable(this.map.keySet());
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

		public V getOrDefault(Object key, V defaultValue) {
			return (V)this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super K, ? super V> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException();
		}

		public V putIfAbsent(K key, V value) {
			throw new UnsupportedOperationException();
		}

		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		public V replace(K key, V value) {
			throw new UnsupportedOperationException();
		}

		public boolean replace(K key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
