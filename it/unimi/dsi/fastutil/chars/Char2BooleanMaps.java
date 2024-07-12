package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.chars.AbstractChar2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2BooleanFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.chars.Char2BooleanFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.chars.Char2BooleanFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap.FastEntrySet;
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
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

public final class Char2BooleanMaps {
	public static final Char2BooleanMaps.EmptyMap EMPTY_MAP = new Char2BooleanMaps.EmptyMap();

	private Char2BooleanMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Char2BooleanMap map) {
		ObjectSet<Entry> entries = map.char2BooleanEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Char2BooleanMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.char2BooleanEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Char2BooleanMap map) {
		final ObjectSet<Entry> entries = map.char2BooleanEntrySet();
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

	public static Char2BooleanMap singleton(char key, boolean value) {
		return new Char2BooleanMaps.Singleton(key, value);
	}

	public static Char2BooleanMap singleton(Character key, Boolean value) {
		return new Char2BooleanMaps.Singleton(key, value);
	}

	public static Char2BooleanMap synchronize(Char2BooleanMap m) {
		return new Char2BooleanMaps.SynchronizedMap(m);
	}

	public static Char2BooleanMap synchronize(Char2BooleanMap m, Object sync) {
		return new Char2BooleanMaps.SynchronizedMap(m, sync);
	}

	public static Char2BooleanMap unmodifiable(Char2BooleanMap m) {
		return new Char2BooleanMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Char2BooleanMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(boolean v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Character, ? extends Boolean> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> char2BooleanEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public CharSet keySet() {
			return CharSets.EMPTY_SET;
		}

		@Override
		public BooleanCollection values() {
			return BooleanSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Char2BooleanMaps.EMPTY_MAP;
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

	public static class Singleton extends Char2BooleanFunctions.Singleton implements Char2BooleanMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient CharSet keys;
		protected transient BooleanCollection values;

		protected Singleton(char key, boolean value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(boolean v) {
			return this.value == v;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return (Boolean)ov == this.value;
		}

		public void putAll(Map<? extends Character, ? extends Boolean> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> char2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, Boolean>> entrySet() {
			return this.char2BooleanEntrySet();
		}

		@Override
		public CharSet keySet() {
			if (this.keys == null) {
				this.keys = CharSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public BooleanCollection values() {
			if (this.values == null) {
				this.values = BooleanSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return this.key ^ (this.value ? 1231 : 1237);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Char2BooleanMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2BooleanMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient CharSet keys;
		protected transient BooleanCollection values;

		protected SynchronizedMap(Char2BooleanMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Char2BooleanMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(boolean v) {
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

		public void putAll(Map<? extends Character, ? extends Boolean> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> char2BooleanEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.char2BooleanEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, Boolean>> entrySet() {
			return this.char2BooleanEntrySet();
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
		public BooleanCollection values() {
			synchronized (this.sync) {
				return this.values == null ? BooleanCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public boolean getOrDefault(char key, boolean defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Character, ? super Boolean> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Character, ? super Boolean, ? extends Boolean> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public boolean putIfAbsent(char key, boolean value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(char key, boolean value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public boolean replace(char key, boolean value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(char key, boolean oldValue, boolean newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public boolean computeIfAbsent(char key, IntPredicate mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public boolean computeIfAbsentNullable(char key, IntFunction<? extends Boolean> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public boolean computeIfAbsentPartial(char key, Char2BooleanFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public boolean computeIfPresent(char key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public boolean compute(char key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public boolean merge(char key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean getOrDefault(Object key, Boolean defaultValue) {
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
		public Boolean replace(Character key, Boolean value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Character key, Boolean oldValue, Boolean newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Boolean putIfAbsent(Character key, Boolean value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Boolean computeIfAbsent(Character key, Function<? super Character, ? extends Boolean> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean computeIfPresent(Character key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean compute(Character key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean merge(Character key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Char2BooleanMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2BooleanMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient CharSet keys;
		protected transient BooleanCollection values;

		protected UnmodifiableMap(Char2BooleanMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(boolean v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Character, ? extends Boolean> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> char2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.char2BooleanEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, Boolean>> entrySet() {
			return this.char2BooleanEntrySet();
		}

		@Override
		public CharSet keySet() {
			if (this.keys == null) {
				this.keys = CharSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public BooleanCollection values() {
			return this.values == null ? BooleanCollections.unmodifiable(this.map.values()) : this.values;
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
		public boolean getOrDefault(char key, boolean defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Character, ? super Boolean> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Character, ? super Boolean, ? extends Boolean> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean putIfAbsent(char key, boolean value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(char key, boolean value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(char key, boolean value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(char key, boolean oldValue, boolean newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfAbsent(char key, IntPredicate mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfAbsentNullable(char key, IntFunction<? extends Boolean> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfAbsentPartial(char key, Char2BooleanFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfPresent(char key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean compute(char key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean merge(char key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean getOrDefault(Object key, Boolean defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean replace(Character key, Boolean value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Character key, Boolean oldValue, Boolean newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean putIfAbsent(Character key, Boolean value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean computeIfAbsent(Character key, Function<? super Character, ? extends Boolean> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean computeIfPresent(Character key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean compute(Character key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean merge(Character key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
