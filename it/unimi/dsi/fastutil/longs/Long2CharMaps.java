package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.longs.AbstractLong2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2CharFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.longs.Long2CharFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.longs.Long2CharFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.longs.Long2CharMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

public final class Long2CharMaps {
	public static final Long2CharMaps.EmptyMap EMPTY_MAP = new Long2CharMaps.EmptyMap();

	private Long2CharMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Long2CharMap map) {
		ObjectSet<Entry> entries = map.long2CharEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Long2CharMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.long2CharEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Long2CharMap map) {
		final ObjectSet<Entry> entries = map.long2CharEntrySet();
		return (ObjectIterable<Entry>)(entries instanceof FastEntrySet ? new ObjectIterable<Entry>() {
			@Override
			public ObjectIterator<Entry> iterator() {
				return ((FastEntrySet)entries).fastIterator();
			}

			public void forEach(Consumer<? super Entry> consumer) {
				((FastEntrySet)entries).fastForEach(consumer);
			}
		} : entries);
	}

	public static Long2CharMap singleton(long key, char value) {
		return new Long2CharMaps.Singleton(key, value);
	}

	public static Long2CharMap singleton(Long key, Character value) {
		return new Long2CharMaps.Singleton(key, value);
	}

	public static Long2CharMap synchronize(Long2CharMap m) {
		return new Long2CharMaps.SynchronizedMap(m);
	}

	public static Long2CharMap synchronize(Long2CharMap m, Object sync) {
		return new Long2CharMaps.SynchronizedMap(m, sync);
	}

	public static Long2CharMap unmodifiable(Long2CharMap m) {
		return new Long2CharMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Long2CharMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(char v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Long, ? extends Character> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2CharEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public LongSet keySet() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public CharCollection values() {
			return CharSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Long2CharMaps.EMPTY_MAP;
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

	public static class Singleton extends Long2CharFunctions.Singleton implements Long2CharMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient CharCollection values;

		protected Singleton(long key, char value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(char v) {
			return this.value == v;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return (Character)ov == this.value;
		}

		public void putAll(Map<? extends Long, ? extends Character> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2CharEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Character>> entrySet() {
			return this.long2CharEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public CharCollection values() {
			if (this.values == null) {
				this.values = CharSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return HashCommon.long2int(this.key) ^ this.value;
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

	public static class SynchronizedMap extends SynchronizedFunction implements Long2CharMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2CharMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient CharCollection values;

		protected SynchronizedMap(Long2CharMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Long2CharMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(char v) {
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

		public void putAll(Map<? extends Long, ? extends Character> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> long2CharEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.long2CharEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Character>> entrySet() {
			return this.long2CharEntrySet();
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
		public CharCollection values() {
			synchronized (this.sync) {
				return this.values == null ? CharCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public char getOrDefault(long key, char defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Long, ? super Character> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Long, ? super Character, ? extends Character> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public char putIfAbsent(long key, char value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(long key, char value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public char replace(long key, char value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(long key, char oldValue, char newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public char computeIfAbsent(long key, LongToIntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public char computeIfAbsentNullable(long key, LongFunction<? extends Character> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public char computeIfAbsentPartial(long key, Long2CharFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public char computeIfPresent(long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public char compute(long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public char merge(long key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character getOrDefault(Object key, Character defaultValue) {
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
		public Character replace(Long key, Character value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Long key, Character oldValue, Character newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Character putIfAbsent(Long key, Character value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Character computeIfAbsent(Long key, Function<? super Long, ? extends Character> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character computeIfPresent(Long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character compute(Long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character merge(Long key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Long2CharMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2CharMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient CharCollection values;

		protected UnmodifiableMap(Long2CharMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(char v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Long, ? extends Character> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2CharEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.long2CharEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Character>> entrySet() {
			return this.long2CharEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public CharCollection values() {
			return this.values == null ? CharCollections.unmodifiable(this.map.values()) : this.values;
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
		public char getOrDefault(long key, char defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Long, ? super Character> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Long, ? super Character, ? extends Character> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char putIfAbsent(long key, char value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(long key, char value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char replace(long key, char value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(long key, char oldValue, char newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfAbsent(long key, LongToIntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfAbsentNullable(long key, LongFunction<? extends Character> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfAbsentPartial(long key, Long2CharFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfPresent(long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char compute(long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char merge(long key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character getOrDefault(Object key, Character defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character replace(Long key, Character value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Long key, Character oldValue, Character newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character putIfAbsent(Long key, Character value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character computeIfAbsent(Long key, Function<? super Long, ? extends Character> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character computeIfPresent(Long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character compute(Long key, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character merge(Long key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
