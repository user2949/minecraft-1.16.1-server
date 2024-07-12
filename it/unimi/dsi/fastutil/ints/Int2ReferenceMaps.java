package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ReferenceFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.ints.Int2ReferenceFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.ints.Int2ReferenceFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceCollections;
import it.unimi.dsi.fastutil.objects.ReferenceSets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Int2ReferenceMaps {
	public static final Int2ReferenceMaps.EmptyMap EMPTY_MAP = new Int2ReferenceMaps.EmptyMap();

	private Int2ReferenceMaps() {
	}

	public static <V> ObjectIterator<Entry<V>> fastIterator(Int2ReferenceMap<V> map) {
		ObjectSet<Entry<V>> entries = map.int2ReferenceEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <V> void fastForEach(Int2ReferenceMap<V> map, Consumer<? super Entry<V>> consumer) {
		ObjectSet<Entry<V>> entries = map.int2ReferenceEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <V> ObjectIterable<Entry<V>> fastIterable(Int2ReferenceMap<V> map) {
		final ObjectSet<Entry<V>> entries = map.int2ReferenceEntrySet();
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

	public static <V> Int2ReferenceMap<V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <V> Int2ReferenceMap<V> singleton(int key, V value) {
		return new Int2ReferenceMaps.Singleton<>(key, value);
	}

	public static <V> Int2ReferenceMap<V> singleton(Integer key, V value) {
		return new Int2ReferenceMaps.Singleton<>(key, value);
	}

	public static <V> Int2ReferenceMap<V> synchronize(Int2ReferenceMap<V> m) {
		return new Int2ReferenceMaps.SynchronizedMap<>(m);
	}

	public static <V> Int2ReferenceMap<V> synchronize(Int2ReferenceMap<V> m, Object sync) {
		return new Int2ReferenceMaps.SynchronizedMap<>(m, sync);
	}

	public static <V> Int2ReferenceMap<V> unmodifiable(Int2ReferenceMap<V> m) {
		return new Int2ReferenceMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<V> extends EmptyFunction<V> implements Int2ReferenceMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		public boolean containsValue(Object v) {
			return false;
		}

		public void putAll(Map<? extends Integer, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> int2ReferenceEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public IntSet keySet() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public ReferenceCollection<V> values() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Int2ReferenceMaps.EMPTY_MAP;
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

	public static class Singleton<V> extends Int2ReferenceFunctions.Singleton<V> implements Int2ReferenceMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient IntSet keys;
		protected transient ReferenceCollection<V> values;

		protected Singleton(int key, V value) {
			super(key, value);
		}

		public boolean containsValue(Object v) {
			return this.value == v;
		}

		public void putAll(Map<? extends Integer, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> int2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return this.int2ReferenceEntrySet();
		}

		@Override
		public IntSet keySet() {
			if (this.keys == null) {
				this.keys = IntSets.singleton(this.key);
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
			return this.key ^ (this.value == null ? 0 : System.identityHashCode(this.value));
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

	public static class SynchronizedMap<V> extends SynchronizedFunction<V> implements Int2ReferenceMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2ReferenceMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient IntSet keys;
		protected transient ReferenceCollection<V> values;

		protected SynchronizedMap(Int2ReferenceMap<V> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Int2ReferenceMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			synchronized (this.sync) {
				return this.map.containsValue(v);
			}
		}

		public void putAll(Map<? extends Integer, ? extends V> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<V>> int2ReferenceEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.int2ReferenceEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return this.int2ReferenceEntrySet();
		}

		@Override
		public IntSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = IntSets.synchronize(this.map.keySet(), this.sync);
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

		@Override
		public V getOrDefault(int key, V defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Integer, ? super V> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public V putIfAbsent(int key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(int key, Object value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public V replace(int key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(int key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public V computeIfAbsentPartial(int key, Int2ReferenceFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
		public V replace(Integer key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Integer key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public V putIfAbsent(Integer key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<V> extends UnmodifiableFunction<V> implements Int2ReferenceMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2ReferenceMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient IntSet keys;
		protected transient ReferenceCollection<V> values;

		protected UnmodifiableMap(Int2ReferenceMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			return this.map.containsValue(v);
		}

		public void putAll(Map<? extends Integer, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> int2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.int2ReferenceEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, V>> entrySet() {
			return this.int2ReferenceEntrySet();
		}

		@Override
		public IntSet keySet() {
			if (this.keys == null) {
				this.keys = IntSets.unmodifiable(this.map.keySet());
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

		@Override
		public V getOrDefault(int key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Integer, ? super V> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V putIfAbsent(int key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(int key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V replace(int key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(int key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsentPartial(int key, Int2ReferenceFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
		public V replace(Integer key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Integer key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V putIfAbsent(Integer key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
