package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.ints.AbstractInt2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ByteFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.ints.Int2ByteFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.ints.Int2ByteFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.ints.Int2ByteMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ByteMap.FastEntrySet;
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

public final class Int2ByteMaps {
	public static final Int2ByteMaps.EmptyMap EMPTY_MAP = new Int2ByteMaps.EmptyMap();

	private Int2ByteMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Int2ByteMap map) {
		ObjectSet<Entry> entries = map.int2ByteEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Int2ByteMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.int2ByteEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Int2ByteMap map) {
		final ObjectSet<Entry> entries = map.int2ByteEntrySet();
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

	public static Int2ByteMap singleton(int key, byte value) {
		return new Int2ByteMaps.Singleton(key, value);
	}

	public static Int2ByteMap singleton(Integer key, Byte value) {
		return new Int2ByteMaps.Singleton(key, value);
	}

	public static Int2ByteMap synchronize(Int2ByteMap m) {
		return new Int2ByteMaps.SynchronizedMap(m);
	}

	public static Int2ByteMap synchronize(Int2ByteMap m, Object sync) {
		return new Int2ByteMaps.SynchronizedMap(m, sync);
	}

	public static Int2ByteMap unmodifiable(Int2ByteMap m) {
		return new Int2ByteMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Int2ByteMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(byte v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Integer, ? extends Byte> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> int2ByteEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public IntSet keySet() {
			return IntSets.EMPTY_SET;
		}

		@Override
		public ByteCollection values() {
			return ByteSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Int2ByteMaps.EMPTY_MAP;
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

	public static class Singleton extends Int2ByteFunctions.Singleton implements Int2ByteMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient IntSet keys;
		protected transient ByteCollection values;

		protected Singleton(int key, byte value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(byte v) {
			return this.value == v;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return (Byte)ov == this.value;
		}

		public void putAll(Map<? extends Integer, ? extends Byte> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> int2ByteEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, Byte>> entrySet() {
			return this.int2ByteEntrySet();
		}

		@Override
		public IntSet keySet() {
			if (this.keys == null) {
				this.keys = IntSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public ByteCollection values() {
			if (this.values == null) {
				this.values = ByteSets.singleton(this.value);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Int2ByteMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2ByteMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient IntSet keys;
		protected transient ByteCollection values;

		protected SynchronizedMap(Int2ByteMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Int2ByteMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(byte v) {
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

		public void putAll(Map<? extends Integer, ? extends Byte> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> int2ByteEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.int2ByteEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, Byte>> entrySet() {
			return this.int2ByteEntrySet();
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
		public ByteCollection values() {
			synchronized (this.sync) {
				return this.values == null ? ByteCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public byte getOrDefault(int key, byte defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Integer, ? super Byte> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Integer, ? super Byte, ? extends Byte> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public byte putIfAbsent(int key, byte value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(int key, byte value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public byte replace(int key, byte value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(int key, byte oldValue, byte newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public byte computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public byte computeIfAbsentNullable(int key, IntFunction<? extends Byte> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public byte computeIfAbsentPartial(int key, Int2ByteFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public byte computeIfPresent(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public byte compute(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public byte merge(int key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte getOrDefault(Object key, Byte defaultValue) {
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
		public Byte replace(Integer key, Byte value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Integer key, Byte oldValue, Byte newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Byte putIfAbsent(Integer key, Byte value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Byte computeIfAbsent(Integer key, Function<? super Integer, ? extends Byte> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte computeIfPresent(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte compute(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte merge(Integer key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Int2ByteMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Int2ByteMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient IntSet keys;
		protected transient ByteCollection values;

		protected UnmodifiableMap(Int2ByteMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(byte v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Integer, ? extends Byte> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> int2ByteEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.int2ByteEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Integer, Byte>> entrySet() {
			return this.int2ByteEntrySet();
		}

		@Override
		public IntSet keySet() {
			if (this.keys == null) {
				this.keys = IntSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public ByteCollection values() {
			return this.values == null ? ByteCollections.unmodifiable(this.map.values()) : this.values;
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
		public byte getOrDefault(int key, byte defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Integer, ? super Byte> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Integer, ? super Byte, ? extends Byte> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte putIfAbsent(int key, byte value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(int key, byte value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte replace(int key, byte value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(int key, byte oldValue, byte newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfAbsentNullable(int key, IntFunction<? extends Byte> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfAbsentPartial(int key, Int2ByteFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfPresent(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte compute(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte merge(int key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte getOrDefault(Object key, Byte defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte replace(Integer key, Byte value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Integer key, Byte oldValue, Byte newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte putIfAbsent(Integer key, Byte value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte computeIfAbsent(Integer key, Function<? super Integer, ? extends Byte> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte computeIfPresent(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte compute(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte merge(Integer key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
