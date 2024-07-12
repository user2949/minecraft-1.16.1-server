package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap.BasicEntry;
import it.unimi.dsi.fastutil.longs.Long2ByteFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.longs.Long2ByteFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.longs.Long2ByteFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.FastEntrySet;
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

public final class Long2ByteMaps {
	public static final Long2ByteMaps.EmptyMap EMPTY_MAP = new Long2ByteMaps.EmptyMap();

	private Long2ByteMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Long2ByteMap map) {
		ObjectSet<Entry> entries = map.long2ByteEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Long2ByteMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.long2ByteEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Long2ByteMap map) {
		final ObjectSet<Entry> entries = map.long2ByteEntrySet();
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

	public static Long2ByteMap singleton(long key, byte value) {
		return new Long2ByteMaps.Singleton(key, value);
	}

	public static Long2ByteMap singleton(Long key, Byte value) {
		return new Long2ByteMaps.Singleton(key, value);
	}

	public static Long2ByteMap synchronize(Long2ByteMap m) {
		return new Long2ByteMaps.SynchronizedMap(m);
	}

	public static Long2ByteMap synchronize(Long2ByteMap m, Object sync) {
		return new Long2ByteMaps.SynchronizedMap(m, sync);
	}

	public static Long2ByteMap unmodifiable(Long2ByteMap m) {
		return new Long2ByteMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Long2ByteMap, Serializable, Cloneable {
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

		public void putAll(Map<? extends Long, ? extends Byte> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2ByteEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public LongSet keySet() {
			return LongSets.EMPTY_SET;
		}

		@Override
		public ByteCollection values() {
			return ByteSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Long2ByteMaps.EMPTY_MAP;
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

	public static class Singleton extends Long2ByteFunctions.Singleton implements Long2ByteMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient ByteCollection values;

		protected Singleton(long key, byte value) {
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

		public void putAll(Map<? extends Long, ? extends Byte> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2ByteEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Byte>> entrySet() {
			return this.long2ByteEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.singleton(this.key);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Long2ByteMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2ByteMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient ByteCollection values;

		protected SynchronizedMap(Long2ByteMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Long2ByteMap m) {
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

		public void putAll(Map<? extends Long, ? extends Byte> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> long2ByteEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.long2ByteEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Byte>> entrySet() {
			return this.long2ByteEntrySet();
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
		public byte getOrDefault(long key, byte defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Long, ? super Byte> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Long, ? super Byte, ? extends Byte> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public byte putIfAbsent(long key, byte value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(long key, byte value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public byte replace(long key, byte value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(long key, byte oldValue, byte newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public byte computeIfAbsent(long key, LongToIntFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public byte computeIfAbsentNullable(long key, LongFunction<? extends Byte> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public byte computeIfAbsentPartial(long key, Long2ByteFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public byte computeIfPresent(long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public byte compute(long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public byte merge(long key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
		public Byte replace(Long key, Byte value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Long key, Byte oldValue, Byte newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Byte putIfAbsent(Long key, Byte value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Byte computeIfAbsent(Long key, Function<? super Long, ? extends Byte> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte computeIfPresent(Long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte compute(Long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Byte merge(Long key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Long2ByteMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2ByteMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient LongSet keys;
		protected transient ByteCollection values;

		protected UnmodifiableMap(Long2ByteMap m) {
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

		public void putAll(Map<? extends Long, ? extends Byte> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> long2ByteEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.long2ByteEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Long, Byte>> entrySet() {
			return this.long2ByteEntrySet();
		}

		@Override
		public LongSet keySet() {
			if (this.keys == null) {
				this.keys = LongSets.unmodifiable(this.map.keySet());
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
		public byte getOrDefault(long key, byte defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Long, ? super Byte> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Long, ? super Byte, ? extends Byte> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte putIfAbsent(long key, byte value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(long key, byte value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte replace(long key, byte value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(long key, byte oldValue, byte newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfAbsent(long key, LongToIntFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfAbsentNullable(long key, LongFunction<? extends Byte> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfAbsentPartial(long key, Long2ByteFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte computeIfPresent(long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte compute(long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte merge(long key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
		public Byte replace(Long key, Byte value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Long key, Byte oldValue, Byte newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte putIfAbsent(Long key, Byte value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte computeIfAbsent(Long key, Function<? super Long, ? extends Byte> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte computeIfPresent(Long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte compute(Long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte merge(Long key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
