package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2ReferenceFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.chars.Char2ReferenceFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.chars.Char2ReferenceFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap.FastEntrySet;
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

public final class Char2ReferenceMaps {
	public static final Char2ReferenceMaps.EmptyMap EMPTY_MAP = new Char2ReferenceMaps.EmptyMap();

	private Char2ReferenceMaps() {
	}

	public static <V> ObjectIterator<Entry<V>> fastIterator(Char2ReferenceMap<V> map) {
		ObjectSet<Entry<V>> entries = map.char2ReferenceEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static <V> void fastForEach(Char2ReferenceMap<V> map, Consumer<? super Entry<V>> consumer) {
		ObjectSet<Entry<V>> entries = map.char2ReferenceEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static <V> ObjectIterable<Entry<V>> fastIterable(Char2ReferenceMap<V> map) {
		final ObjectSet<Entry<V>> entries = map.char2ReferenceEntrySet();
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

	public static <V> Char2ReferenceMap<V> emptyMap() {
		return EMPTY_MAP;
	}

	public static <V> Char2ReferenceMap<V> singleton(char key, V value) {
		return new Char2ReferenceMaps.Singleton<>(key, value);
	}

	public static <V> Char2ReferenceMap<V> singleton(Character key, V value) {
		return new Char2ReferenceMaps.Singleton<>(key, value);
	}

	public static <V> Char2ReferenceMap<V> synchronize(Char2ReferenceMap<V> m) {
		return new Char2ReferenceMaps.SynchronizedMap<>(m);
	}

	public static <V> Char2ReferenceMap<V> synchronize(Char2ReferenceMap<V> m, Object sync) {
		return new Char2ReferenceMaps.SynchronizedMap<>(m, sync);
	}

	public static <V> Char2ReferenceMap<V> unmodifiable(Char2ReferenceMap<V> m) {
		return new Char2ReferenceMaps.UnmodifiableMap<>(m);
	}

	public static class EmptyMap<V> extends EmptyFunction<V> implements Char2ReferenceMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		public boolean containsValue(Object v) {
			return false;
		}

		public void putAll(Map<? extends Character, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> char2ReferenceEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public CharSet keySet() {
			return CharSets.EMPTY_SET;
		}

		@Override
		public ReferenceCollection<V> values() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Char2ReferenceMaps.EMPTY_MAP;
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

	public static class Singleton<V> extends Char2ReferenceFunctions.Singleton<V> implements Char2ReferenceMap<V>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient CharSet keys;
		protected transient ReferenceCollection<V> values;

		protected Singleton(char key, V value) {
			super(key, value);
		}

		public boolean containsValue(Object v) {
			return this.value == v;
		}

		public void putAll(Map<? extends Character, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> char2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry<>(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, V>> entrySet() {
			return this.char2ReferenceEntrySet();
		}

		@Override
		public CharSet keySet() {
			if (this.keys == null) {
				this.keys = CharSets.singleton(this.key);
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

	public static class SynchronizedMap<V> extends SynchronizedFunction<V> implements Char2ReferenceMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2ReferenceMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient CharSet keys;
		protected transient ReferenceCollection<V> values;

		protected SynchronizedMap(Char2ReferenceMap<V> m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Char2ReferenceMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			synchronized (this.sync) {
				return this.map.containsValue(v);
			}
		}

		public void putAll(Map<? extends Character, ? extends V> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry<V>> char2ReferenceEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.char2ReferenceEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, V>> entrySet() {
			return this.char2ReferenceEntrySet();
		}

		@Override
		public CharSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = CharSets.synchronize(this.map.keySet(), this.sync);
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
		public V getOrDefault(char key, V defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Character, ? super V> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public V putIfAbsent(char key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(char key, Object value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public V replace(char key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(char key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public V computeIfAbsentPartial(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
		public V replace(Character key, V value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Character key, V oldValue, V newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public V putIfAbsent(Character key, V value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap<V> extends UnmodifiableFunction<V> implements Char2ReferenceMap<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2ReferenceMap<V> map;
		protected transient ObjectSet<Entry<V>> entries;
		protected transient CharSet keys;
		protected transient ReferenceCollection<V> values;

		protected UnmodifiableMap(Char2ReferenceMap<V> m) {
			super(m);
			this.map = m;
		}

		public boolean containsValue(Object v) {
			return this.map.containsValue(v);
		}

		public void putAll(Map<? extends Character, ? extends V> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry<V>> char2ReferenceEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.char2ReferenceEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, V>> entrySet() {
			return this.char2ReferenceEntrySet();
		}

		@Override
		public CharSet keySet() {
			if (this.keys == null) {
				this.keys = CharSets.unmodifiable(this.map.keySet());
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
		public V getOrDefault(char key, V defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Character, ? super V> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V putIfAbsent(char key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(char key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V replace(char key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(char key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfAbsentPartial(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
		public V replace(Character key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Character key, V oldValue, V newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V putIfAbsent(Character key, V value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
