package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunctions.EmptyFunction;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunctions.SynchronizedFunction;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunctions.UnmodifiableFunction;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap.Entry;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap.FastEntrySet;
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

public final class Short2BooleanMaps {
	public static final Short2BooleanMaps.EmptyMap EMPTY_MAP = new Short2BooleanMaps.EmptyMap();

	private Short2BooleanMaps() {
	}

	public static ObjectIterator<Entry> fastIterator(Short2BooleanMap map) {
		ObjectSet<Entry> entries = map.short2BooleanEntrySet();
		return entries instanceof FastEntrySet ? ((FastEntrySet)entries).fastIterator() : entries.iterator();
	}

	public static void fastForEach(Short2BooleanMap map, Consumer<? super Entry> consumer) {
		ObjectSet<Entry> entries = map.short2BooleanEntrySet();
		if (entries instanceof FastEntrySet) {
			((FastEntrySet)entries).fastForEach(consumer);
		} else {
			entries.forEach(consumer);
		}
	}

	public static ObjectIterable<Entry> fastIterable(Short2BooleanMap map) {
		final ObjectSet<Entry> entries = map.short2BooleanEntrySet();
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

	public static Short2BooleanMap singleton(short key, boolean value) {
		return new Short2BooleanMaps.Singleton(key, value);
	}

	public static Short2BooleanMap singleton(Short key, Boolean value) {
		return new Short2BooleanMaps.Singleton(key, value);
	}

	public static Short2BooleanMap synchronize(Short2BooleanMap m) {
		return new Short2BooleanMaps.SynchronizedMap(m);
	}

	public static Short2BooleanMap synchronize(Short2BooleanMap m, Object sync) {
		return new Short2BooleanMaps.SynchronizedMap(m, sync);
	}

	public static Short2BooleanMap unmodifiable(Short2BooleanMap m) {
		return new Short2BooleanMaps.UnmodifiableMap(m);
	}

	public static class EmptyMap extends EmptyFunction implements Short2BooleanMap, Serializable, Cloneable {
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

		public void putAll(Map<? extends Short, ? extends Boolean> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> short2BooleanEntrySet() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public ShortSet keySet() {
			return ShortSets.EMPTY_SET;
		}

		@Override
		public BooleanCollection values() {
			return BooleanSets.EMPTY_SET;
		}

		@Override
		public Object clone() {
			return Short2BooleanMaps.EMPTY_MAP;
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

	public static class Singleton extends Short2BooleanFunctions.Singleton implements Short2BooleanMap, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected transient ObjectSet<Entry> entries;
		protected transient ShortSet keys;
		protected transient BooleanCollection values;

		protected Singleton(short key, boolean value) {
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

		public void putAll(Map<? extends Short, ? extends Boolean> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> short2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.singleton(new BasicEntry(this.key, this.value));
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Short, Boolean>> entrySet() {
			return this.short2BooleanEntrySet();
		}

		@Override
		public ShortSet keySet() {
			if (this.keys == null) {
				this.keys = ShortSets.singleton(this.key);
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

	public static class SynchronizedMap extends SynchronizedFunction implements Short2BooleanMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Short2BooleanMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient ShortSet keys;
		protected transient BooleanCollection values;

		protected SynchronizedMap(Short2BooleanMap m, Object sync) {
			super(m, sync);
			this.map = m;
		}

		protected SynchronizedMap(Short2BooleanMap m) {
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

		public void putAll(Map<? extends Short, ? extends Boolean> m) {
			synchronized (this.sync) {
				this.map.putAll(m);
			}
		}

		@Override
		public ObjectSet<Entry> short2BooleanEntrySet() {
			synchronized (this.sync) {
				if (this.entries == null) {
					this.entries = ObjectSets.synchronize(this.map.short2BooleanEntrySet(), this.sync);
				}

				return this.entries;
			}
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Short, Boolean>> entrySet() {
			return this.short2BooleanEntrySet();
		}

		@Override
		public ShortSet keySet() {
			synchronized (this.sync) {
				if (this.keys == null) {
					this.keys = ShortSets.synchronize(this.map.keySet(), this.sync);
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
		public boolean getOrDefault(short key, boolean defaultValue) {
			synchronized (this.sync) {
				return this.map.getOrDefault(key, defaultValue);
			}
		}

		public void forEach(BiConsumer<? super Short, ? super Boolean> action) {
			synchronized (this.sync) {
				this.map.forEach(action);
			}
		}

		public void replaceAll(BiFunction<? super Short, ? super Boolean, ? extends Boolean> function) {
			synchronized (this.sync) {
				this.map.replaceAll(function);
			}
		}

		@Override
		public boolean putIfAbsent(short key, boolean value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Override
		public boolean remove(short key, boolean value) {
			synchronized (this.sync) {
				return this.map.remove(key, value);
			}
		}

		@Override
		public boolean replace(short key, boolean value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Override
		public boolean replace(short key, boolean oldValue, boolean newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Override
		public boolean computeIfAbsent(short key, IntPredicate mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Override
		public boolean computeIfAbsentNullable(short key, IntFunction<? extends Boolean> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentNullable(key, mappingFunction);
			}
		}

		@Override
		public boolean computeIfAbsentPartial(short key, Short2BooleanFunction mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsentPartial(key, mappingFunction);
			}
		}

		@Override
		public boolean computeIfPresent(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Override
		public boolean compute(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Override
		public boolean merge(short key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
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
		public Boolean replace(Short key, Boolean value) {
			synchronized (this.sync) {
				return this.map.replace(key, value);
			}
		}

		@Deprecated
		@Override
		public boolean replace(Short key, Boolean oldValue, Boolean newValue) {
			synchronized (this.sync) {
				return this.map.replace(key, oldValue, newValue);
			}
		}

		@Deprecated
		@Override
		public Boolean putIfAbsent(Short key, Boolean value) {
			synchronized (this.sync) {
				return this.map.putIfAbsent(key, value);
			}
		}

		@Deprecated
		@Override
		public Boolean computeIfAbsent(Short key, Function<? super Short, ? extends Boolean> mappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfAbsent(key, mappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean computeIfPresent(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.computeIfPresent(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean compute(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.compute(key, remappingFunction);
			}
		}

		@Deprecated
		@Override
		public Boolean merge(Short key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
			synchronized (this.sync) {
				return this.map.merge(key, value, remappingFunction);
			}
		}
	}

	public static class UnmodifiableMap extends UnmodifiableFunction implements Short2BooleanMap, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Short2BooleanMap map;
		protected transient ObjectSet<Entry> entries;
		protected transient ShortSet keys;
		protected transient BooleanCollection values;

		protected UnmodifiableMap(Short2BooleanMap m) {
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

		public void putAll(Map<? extends Short, ? extends Boolean> m) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectSet<Entry> short2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = ObjectSets.unmodifiable(this.map.short2BooleanEntrySet());
			}

			return this.entries;
		}

		@Deprecated
		@Override
		public ObjectSet<java.util.Map.Entry<Short, Boolean>> entrySet() {
			return this.short2BooleanEntrySet();
		}

		@Override
		public ShortSet keySet() {
			if (this.keys == null) {
				this.keys = ShortSets.unmodifiable(this.map.keySet());
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
		public boolean getOrDefault(short key, boolean defaultValue) {
			return this.map.getOrDefault(key, defaultValue);
		}

		public void forEach(BiConsumer<? super Short, ? super Boolean> action) {
			this.map.forEach(action);
		}

		public void replaceAll(BiFunction<? super Short, ? super Boolean, ? extends Boolean> function) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean putIfAbsent(short key, boolean value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(short key, boolean value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(short key, boolean value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean replace(short key, boolean oldValue, boolean newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfAbsent(short key, IntPredicate mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfAbsentNullable(short key, IntFunction<? extends Boolean> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfAbsentPartial(short key, Short2BooleanFunction mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean computeIfPresent(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean compute(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean merge(short key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
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
		public Boolean replace(Short key, Boolean value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public boolean replace(Short key, Boolean oldValue, Boolean newValue) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean putIfAbsent(Short key, Boolean value) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean computeIfAbsent(Short key, Function<? super Short, ? extends Boolean> mappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean computeIfPresent(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean compute(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean merge(Short key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
