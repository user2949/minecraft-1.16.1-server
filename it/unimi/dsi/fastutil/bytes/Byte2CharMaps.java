package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2CharMap.BasicEntry;
import it.unimi.dsi.fastutil.bytes.Byte2CharFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.bytes.Byte2CharFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.bytes.Byte2CharFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
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
import java.util.function.IntUnaryOperator;

public final class Byte2CharMaps {
	public static final Byte2CharMaps.EmptyMap EMPTY_MAP = new Byte2CharMaps.EmptyMap();

	private Byte2CharMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Byte2CharMap map) {
		ObjectSet<Entry> entries = map.byte2CharEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Byte2CharMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.byte2CharEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Byte2CharMap map) {
		final ObjectSet<Entry> entries = map.byte2CharEntrySet();
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

	public static Byte2CharMap singleton(byte key, char value) {
		return new Byte2CharMaps.Singleton(key, value);
	}

	public static Byte2CharMap singleton(Byte key, Character value) {
		return new Byte2CharMaps.Singleton(key, value);
	}

	public static Byte2CharMap synchronize(Byte2CharMap m) {
		return new Byte2CharMaps.SynchronizedMap(m);
	}

	public static Byte2CharMap synchronize(Byte2CharMap m, Object sync) {
		return new Byte2CharMaps.SynchronizedMap(m, sync);
	}

	public static Byte2CharMap unmodifiable(Byte2CharMap m) {
		return new Byte2CharMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Byte2CharMap, Serializable, Cloneable {
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

		public void putAll(Map<? extends Byte, ? extends Character> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> byte2CharEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ByteSet keySet() {
			return ByteSets.EMPTY_SET;
		}

		@Override
		public CharCollection values() {
			return CharSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Byte2CharMaps.EMPTY_MAP;
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

	public static class Singleton extends Byte2CharFunctions.Singleton implements Byte2CharMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient ByteSet keys;
		protected transient CharCollection values;

		protected Singleton(byte key, char value) {
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

		public void putAll(Map<? extends Byte, ? extends Character> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> byte2CharEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Byte, Character>> entrySet() {
			return this.byte2CharEntrySet();
		}

		@Override
		public ByteSet keySet() {
			if (this.keys == null) {
				this.keys = ByteSets.singleton(this.key);
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
			return this.key ^ this.value;
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

	public static class SynchronizedMap extends SynchronizedFunction implements Byte2CharMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Byte2CharMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient ByteSet keys;
		protected transient CharCollection values;

		protected SynchronizedMap(Byte2CharMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Byte2CharMap m) {
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

		public void putAll(Map<? extends Byte, ? extends Character> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> byte2CharEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.byte2CharEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Byte, Character>> entrySet() {
			return this.byte2CharEntrySet();
		}

		@Override
		public ByteSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = ByteSets.synchronize(this.map.keySet(), this.sync);
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
		public char getOrDefault(byte key, char defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Byte, ? super Character> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Byte, ? super Character, ? extends Character> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public char putIfAbsent(byte key, char value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(byte key, char value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public char replace(byte key, char value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(byte key, char oldValue, char newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public char computeIfAbsent(byte key, IntUnaryOperator mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public char computeIfAbsentNullable(byte key, IntFunction<? extends Character> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public char computeIfAbsentPartial(byte key, Byte2CharFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public char computeIfPresent(byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public char compute(byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public char merge(byte key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
		public Character replace(Byte key, Character value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Byte key, Character oldValue, Character newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Character putIfAbsent(Byte key, Character value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Character computeIfAbsent(Byte key, Function<? super Byte, ? extends Character> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character computeIfPresent(Byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character compute(Byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Character merge(Byte key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Byte2CharMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Byte2CharMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient ByteSet keys;
		protected transient CharCollection values;

		protected UnmodifiableMap(Byte2CharMap m) {
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

		public void putAll(Map<? extends Byte, ? extends Character> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> byte2CharEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.byte2CharEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Byte, Character>> entrySet() {
			return this.byte2CharEntrySet();
		}

		@Override
		public ByteSet keySet() {
			if (this.keys == null) {
				this.keys = ByteSets.unmodifiable(this.map.keySet());
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
		public char getOrDefault(byte key, char defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Byte, ? super Character> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Byte, ? super Character, ? extends Character> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char putIfAbsent(byte key, char value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(byte key, char value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char replace(byte key, char value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(byte key, char oldValue, char newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfAbsent(byte key, IntUnaryOperator mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfAbsentNullable(byte key, IntFunction<? extends Character> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfAbsentPartial(byte key, Byte2CharFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char computeIfPresent(byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char compute(byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char merge(byte key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
		public Character replace(Byte key, Character value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Byte key, Character oldValue, Character newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character putIfAbsent(Byte key, Character value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character computeIfAbsent(Byte key, Function<? super Byte, ? extends Character> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character computeIfPresent(Byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character compute(Byte key, BiFunction<? super Byte, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character merge(Byte key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
