package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.AbstractReference2IntMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Reference2IntFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.objects.Reference2IntFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.objects.Reference2IntFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.FastEntrySet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Reference2IntMaps {
	public static final Reference2IntMaps.EmptyMap EMPTY_MAP = new Reference2IntMaps.EmptyMap();

	private Reference2IntMaps() {
	}

	public static <K> ObjectIterator<Entry<K>> fastIterator(Reference2IntMap<K> map) {
		ObjectSet<Entry<K>> entries = map.reference2IntEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <K> void fastForEach(Reference2IntMap<K> map, Consumer<? super Entry<K>> consumer) {
		ObjectSet<Entry<K>> entries = map.reference2IntEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <K> ObjectIterable<Entry<K>> fastIterable(Reference2IntMap<K> map) {
		final ObjectSet<Entry<K>> entries = map.reference2IntEntrySet();
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

	public static <K> Reference2IntMap<K> emptyMap() {
		return EMPTY_MAP;
	}

	public static <K> Reference2IntMap<K> singleton(K key, int value) {
		return new Reference2IntMaps.Singleton<>(key, value);
	}

	public static <K> Reference2IntMap<K> singleton(K key, Integer value) {
		return new Reference2IntMaps.Singleton<>(key, value);
	}

	public static <K> Reference2IntMap<K> synchronize(Reference2IntMap<K> m) {
		return new Reference2IntMaps.SynchronizedMap<>(m);
	}

	public static <K> Reference2IntMap<K> synchronize(Reference2IntMap<K> m, Object sync) {
		return new Reference2IntMaps.SynchronizedMap<>(m, sync);
	}

	public static <K> Reference2IntMap<K> unmodifiable(Reference2IntMap<K> m) {
		return new Reference2IntMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<K> extends EmptyFunction<K> implements Reference2IntMap<K>, Serializable, Cloneable {
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

		public void putAll(Map<? extends K, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2IntEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ReferenceSet<K> keySet() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public IntCollection values() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Reference2IntMaps.EMPTY_MAP;
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

	public static class Singleton<K> extends Reference2IntFunctions.Singleton<K> implements Reference2IntMap<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient IntCollection values;

		protected Singleton(K key, int value) {
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

		public void putAll(Map<? extends K, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.reference2IntEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.singleton(this.key);
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
			return System.identityHashCode(this.key) ^ this.value;
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

	public static class SynchronizedMap<K> extends SynchronizedFunction<K> implements Reference2IntMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2IntMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient IntCollection values;

		protected SynchronizedMap(Reference2IntMap<K> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Reference2IntMap<K> m) {
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

		public void putAll(Map<? extends K, ? extends Integer> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<K>> reference2IntEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.reference2IntEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.reference2IntEntrySet();
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
		public int getOrDefault(Object key, int defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super K, ? super Integer> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public int putIfAbsent(K key, int value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(Object key, int value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public int replace(K key, int value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(K key, int oldValue, int newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIntIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public int computeIntIfAbsentPartial(K key, Reference2IntFunction<? super K> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIntIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIntIfPresent(key, remappingFunction);
			}
		}

		@Override
		public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeInt(key, remappingFunction);
			}
		}

		@Override
		public int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.mergeInt(key, value, remappingFunction);
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
		public Integer replace(K key, Integer value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(K key, Integer oldValue, Integer newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Integer putIfAbsent(K key, Integer value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
			synchronized (this.sync) {
				return (Integer)this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return (Integer)this.map.computeIfPresent(key, remappingFunction);
			}
		}

		public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return (Integer)this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<K> extends UnmodifiableFunction<K> implements Reference2IntMap<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2IntMap<K> map;
		protected transient ObjectSet<Entry<K>> entries;
		protected transient ReferenceSet<K> keys;
		protected transient IntCollection values;

		protected UnmodifiableMap(Reference2IntMap<K> m) {
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

		public void putAll(Map<? extends K, ? extends Integer> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<K>> reference2IntEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.reference2IntEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<K, Integer>> entrySet() {
			return this.reference2IntEntrySet();
		}

		@Override
		public ReferenceSet<K> keySet() {
			if (this.keys == null) {
				this.keys = ReferenceSets.unmodifiable(this.map.keySet());
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
		public int getOrDefault(Object key, int defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super K, ? super Integer> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int putIfAbsent(K key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int replace(K key, int value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(K key, int oldValue, int newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIntIfAbsentPartial(K key, Reference2IntFunction<? super K> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
		public Integer replace(K key, Integer value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(K key, Integer oldValue, Integer newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer putIfAbsent(K key, Integer value) {
			throw new UnsupportedOperationException();
		}

		public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
