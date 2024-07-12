package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class Reference2ReferenceMaps {
	public static final Reference2ReferenceMaps.EmptyMap EMPTY_MAP = new Reference2ReferenceMaps.EmptyMap();

	private Reference2ReferenceMaps() {
	}

	public static <K, V> ObjectIterator<Entry<K, V>> fastIterator(Reference2ReferenceMap<K, V> map) {
		ObjectSet<Entry<K, V>> entries = map.reference2ReferenceEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K, V> void fastForEach(Reference2ReferenceMap<K, V> map, Consumer<? super Entry<K, V>> consumer) {
		ObjectSet<Entry<K, V>> entries = map.reference2ReferenceEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <K, V> ObjectIterable<Entry<K, V>> fastIterable(Reference2ReferenceMap<K, V> map) {
		final ObjectSet<Entry<K, V>> entries = map.reference2ReferenceEntrySet();
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

	public static <K, V> Reference2ReferenceMap<K, V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K, V> Reference2ReferenceMap<K, V> singleton(K key, V value) {
		return new Reference2ReferenceMaps.Singleton<>(key, value);
	}

	public static <K, V> Reference2ReferenceMap<K, V> synchronize(Reference2ReferenceMap<K, V> m) {
		return new Reference2ReferenceMaps.SynchronizedMap<>(m);
	}

	public static <K, V> Reference2ReferenceMap<K, V> synchronize(Reference2ReferenceMap<K, V> m, Object sync) {
		return new Reference2ReferenceMaps.SynchronizedMap<>(m, sync);
	}

	public static <K, V> Reference2ReferenceMap<K, V> unmodifiable(Reference2ReferenceMap<K, V> m) {
		return new Reference2ReferenceMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<K, V> extends EmptyFunction<K, V> implements Reference2ReferenceMap<K, V>, Serializable, Cloneable {
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
		public ObjectSet<Entry<K, V>> reference2ReferenceEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ReferenceSet<K> keySet() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public ReferenceCollection<V> values() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Reference2ReferenceMaps.EMPTY_MAP;
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

	public static class Singleton<K, V> extends Reference2ReferenceFunctions.Singleton<K, V> implements Reference2ReferenceMap<K, V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<K, V>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient ReferenceCollection<V> values;

		protected Singleton(K key, V value) {
			super(key, value);
		}

		public boolean containsValue(Object v) {
			return this.value == v;
		}

		public void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K, V>> reference2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Override
		public ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.reference2ReferenceEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public ReferenceCollection<V> values() {
			if (this.values == null) {
				this.values = ReferenceSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return System.identityHashCode(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
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

	public static class SynchronizedMap<K, V> extends SynchronizedFunction<K, V> implements Reference2ReferenceMap<K, V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2ReferenceMap<K, V> map;
		protected transient ObjectSet<Entry<K, V>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient ReferenceCollection<V> values;

		protected SynchronizedMap(Reference2ReferenceMap<K, V> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Reference2ReferenceMap<K, V> m) {
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
		public ObjectSet<Entry<K, V>> reference2ReferenceEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.reference2ReferenceEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Override
		public ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.reference2ReferenceEntrySet();
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
		public ReferenceCollection<V> values() {
			synchronized (this.sync) {
				return this.values == null ? ReferenceCollections.synchronize(this.map.values(), this.sync) : this.values;
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

	public static class UnmodifiableMap<K, V> extends UnmodifiableFunction<K, V> implements Reference2ReferenceMap<K, V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2ReferenceMap<K, V> map;
		protected transient ObjectSet<Entry<K, V>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient ReferenceCollection<V> values;

		protected UnmodifiableMap(Reference2ReferenceMap<K, V> m) {
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
		public ObjectSet<Entry<K, V>> reference2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.reference2ReferenceEntrySet());
			}

			return this.entries;
		}

		@Override
		public ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
			return this.reference2ReferenceEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public ReferenceCollection<V> values() {
			return this.values == null ? ReferenceCollections.unmodifiable(this.map.values()) : this.values;
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
