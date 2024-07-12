package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2ObjectFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.shorts.Short2ObjectFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.shorts.Short2ObjectFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Short2ObjectMaps {
	public static final Short2ObjectMaps.EmptyMap EMPTY_MAP = new Short2ObjectMaps.EmptyMap();

	private Short2ObjectMaps() {
	}

	public static <V> ObjectIterator<Entry<V>> fastIterator(Short2ObjectMap<V> map) {
		ObjectSet<Entry<V>> entries = map.short2ObjectEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <V> void fastForEach(Short2ObjectMap<V> map, Consumer<? super Entry<V>> consumer) {
		ObjectSet<Entry<V>> entries = map.short2ObjectEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <V> ObjectIterable<Entry<V>> fastIterable(Short2ObjectMap<V> map) {
		final ObjectSet<Entry<V>> entries = map.short2ObjectEntrySet();
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

	public static <V> Short2ObjectMap<V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <V> Short2ObjectMap<V> singleton(short key, V value) {
		return new Short2ObjectMaps.Singleton<>(key, value);
	}

	public static <V> Short2ObjectMap<V> singleton(Short key, V value) {
		return new Short2ObjectMaps.Singleton<>(key, value);
	}

	public static <V> Short2ObjectMap<V> synchronize(Short2ObjectMap<V> m) {
		return new Short2ObjectMaps.SynchronizedMap<>(m);
	}

	public static <V> Short2ObjectMap<V> synchronize(Short2ObjectMap<V> m, Object sync) {
		return new Short2ObjectMaps.SynchronizedMap<>(m, sync);
	}

	public static <V> Short2ObjectMap<V> unmodifiable(Short2ObjectMap<V> m) {
		return new Short2ObjectMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<V> extends EmptyFunction<V> implements Short2ObjectMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		public boolean containsValue(Object v) {
			return false;
		}

		public void putAll(Map<? extends Short, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> short2ObjectEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ShortSet keySet() {
			return ShortSets.EMPTY_SET;
		}

		@Override
		public ObjectCollection<V> values() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Short2ObjectMaps.EMPTY_MAP;
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

	public static class Singleton<V> extends Short2ObjectFunctions.Singleton<V> implements Short2ObjectMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient ShortSet keys;
		protected transient ObjectCollection<V> values;

		protected Singleton(short key, V value) {
			super(key, value);
		}

		public boolean containsValue(Object v) {
			return Objects.equals(this.value, v);
		}

		public void putAll(Map<? extends Short, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> short2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Short, V>> entrySet() {
			return this.short2ObjectEntrySet();
		}

		@Override
		public ShortSet keySet() {
			if (this.keys == null) {
				this.keys = ShortSets.singleton(this.key);
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
			return this.key ^ (this.value == null ? 0 : this.value.hashCode());
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

	public static class SynchronizedMap<V> extends SynchronizedFunction<V> implements Short2ObjectMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Short2ObjectMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient ShortSet keys;
		protected transient ObjectCollection<V> values;

		protected SynchronizedMap(Short2ObjectMap<V> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Short2ObjectMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			synchronized (this.sync) {
				return this.map.containsValue(v);
			}
		}

		public void putAll(Map<? extends Short, ? extends V> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<V>> short2ObjectEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.short2ObjectEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Short, V>> entrySet() {
			return this.short2ObjectEntrySet();
		}

		@Override
		public ShortSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = ShortSets.synchronize(this.map.keySet(), this.sync);
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
		public V getOrDefault(short key, V defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Short, ? super V> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Short, ? super V, ? extends V> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public V putIfAbsent(short key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(short key, Object value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public V replace(short key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(short key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public V computeIfAbsent(short key, IntFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public V computeIfAbsentPartial(short key, Short2ObjectFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public V computeIfPresent(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public V compute(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public V merge(short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
		public V replace(Short key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Short key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public V putIfAbsent(Short key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Short key, Function<? super Short, ? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public V computeIfPresent(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V compute(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V merge(Short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<V> extends UnmodifiableFunction<V> implements Short2ObjectMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Short2ObjectMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient ShortSet keys;
		protected transient ObjectCollection<V> values;

		protected UnmodifiableMap(Short2ObjectMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			return this.map.containsValue(v);
		}

		public void putAll(Map<? extends Short, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> short2ObjectEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.short2ObjectEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Short, V>> entrySet() {
			return this.short2ObjectEntrySet();
		}

		@Override
		public ShortSet keySet() {
			if (this.keys == null) {
				this.keys = ShortSets.unmodifiable(this.map.keySet());
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
		public V getOrDefault(short key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Short, ? super V> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Short, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V putIfAbsent(short key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(short key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V replace(short key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(short key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsent(short key, IntFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsentPartial(short key, Short2ObjectFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfPresent(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V compute(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V merge(short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
		public V replace(Short key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Short key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V putIfAbsent(Short key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Short key, Function<? super Short, ? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfPresent(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V compute(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V merge(Short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
