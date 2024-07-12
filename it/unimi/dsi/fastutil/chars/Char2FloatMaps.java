package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2FloatMap.BasicEntry;
import it.unimi.dsi.fastutil.chars.Char2FloatFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.chars.Char2FloatFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.chars.Char2FloatFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSets;
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
import java.util.function.IntToDoubleFunction;

public final class Char2FloatMaps {
	public static final Char2FloatMaps.EmptyMap EMPTY_MAP = new Char2FloatMaps.EmptyMap();

	private Char2FloatMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Char2FloatMap map) {
		ObjectSet<Entry> entries = map.char2FloatEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Char2FloatMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.char2FloatEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Char2FloatMap map) {
		final ObjectSet<Entry> entries = map.char2FloatEntrySet();
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

	public static Char2FloatMap singleton(char key, float value) {
		return new Char2FloatMaps.Singleton(key, value);
	}

	public static Char2FloatMap singleton(Character key, Float value) {
		return new Char2FloatMaps.Singleton(key, value);
	}

	public static Char2FloatMap synchronize(Char2FloatMap m) {
		return new Char2FloatMaps.SynchronizedMap(m);
	}

	public static Char2FloatMap synchronize(Char2FloatMap m, Object sync) {
		return new Char2FloatMaps.SynchronizedMap(m, sync);
	}

	public static Char2FloatMap unmodifiable(Char2FloatMap m) {
		return new Char2FloatMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Char2FloatMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyMap() {
		}

		@Override
		public boolean containsValue(float v) {
			return false;
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return false;
		}

		public void putAll(Map<? extends Character, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> char2FloatEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public CharSet keySet() {
			return CharSets.EMPTY_SET;
		}

		@Override
		public FloatCollection values() {
			return FloatSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Char2FloatMaps.EMPTY_MAP;
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

	public static class Singleton extends Char2FloatFunctions.Singleton implements Char2FloatMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient CharSet keys;
		protected transient FloatCollection values;

		protected Singleton(char key, float value) {
			super(key, value);
		}

		@Override
		public boolean containsValue(float v) {
			return Float.floatToIntBits(this.value) == Float.floatToIntBits(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return Float.floatToIntBits((Float)ov) == Float.floatToIntBits(this.value);
		}

		public void putAll(Map<? extends Character, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> char2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, Float>> entrySet() {
			return this.char2FloatEntrySet();
		}

		@Override
		public CharSet keySet() {
			if (this.keys == null) {
				this.keys = CharSets.singleton(this.key);
			}

			return this.keys;
		}

		@Override
		public FloatCollection values() {
			if (this.values == null) {
				this.values = FloatSets.singleton(this.value);
			}

			return this.values;
		}

		public boolean isEmpty() {
			return false;
		}

		public int hashCode() {
			return this.key ^ HashCommon.float2int(this.value);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Char2FloatMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2FloatMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient CharSet keys;
		protected transient FloatCollection values;

		protected SynchronizedMap(Char2FloatMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Char2FloatMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(float v) {
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

		public void putAll(Map<? extends Character, ? extends Float> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> char2FloatEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.char2FloatEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, Float>> entrySet() {
			return this.char2FloatEntrySet();
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
		public FloatCollection values() {
			synchronized (this.sync) {
				return this.values == null ? FloatCollections.synchronize(this.map.values(), this.sync) : this.values;
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
		public float getOrDefault(char key, float defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Character, ? super Float> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Character, ? super Float, ? extends Float> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public float putIfAbsent(char key, float value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(char key, float value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public float replace(char key, float value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(char key, float oldValue, float newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public float computeIfAbsent(char key, IntToDoubleFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public float computeIfAbsentNullable(char key, IntFunction<? extends Float> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public float computeIfAbsentPartial(char key, Char2FloatFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public float computeIfPresent(char key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public float compute(char key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public float merge(char key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float getOrDefault(Object key, Float defaultValue) {
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
		public Float replace(Character key, Float value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Character key, Float oldValue, Float newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Float putIfAbsent(Character key, Float value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Float computeIfAbsent(Character key, Function<? super Character, ? extends Float> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float computeIfPresent(Character key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float compute(Character key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Float merge(Character key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Char2FloatMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2FloatMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient CharSet keys;
		protected transient FloatCollection values;

		protected UnmodifiableMap(Char2FloatMap m) {
			super(m);
			this.map = m;
		}

		@Override
		public boolean containsValue(float v) {
			return this.map.containsValue(v);
		}

		@Deprecated
		@Override
		public boolean containsValue(Object ov) {
			return this.map.containsValue(ov);
		}

		public void putAll(Map<? extends Character, ? extends Float> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> char2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.char2FloatEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Character, Float>> entrySet() {
			return this.char2FloatEntrySet();
		}

		@Override
		public CharSet keySet() {
			if (this.keys == null) {
				this.keys = CharSets.unmodifiable(this.map.keySet());
			}

			return this.keys;
		}

		@Override
		public FloatCollection values() {
			return this.values == null ? FloatCollections.unmodifiable(this.map.values()) : this.values;
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
		public float getOrDefault(char key, float defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Character, ? super Float> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Character, ? super Float, ? extends Float> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float putIfAbsent(char key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(char key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float replace(char key, float value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(char key, float oldValue, float newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfAbsent(char key, IntToDoubleFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfAbsentNullable(char key, IntFunction<? extends Float> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfAbsentPartial(char key, Char2FloatFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float computeIfPresent(char key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float compute(char key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public float merge(char key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float getOrDefault(Object key, Float defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		@Deprecated
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float replace(Character key, Float value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Character key, Float oldValue, Float newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float putIfAbsent(Character key, Float value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float computeIfAbsent(Character key, Function<? super Character, ? extends Float> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float computeIfPresent(Character key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float compute(Character key, BiFunction<? super Character, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Float merge(Character key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
