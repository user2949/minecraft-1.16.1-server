package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;

public interface Object2LongMap<K> extends Object2LongFunction<K>, Map<K, Long> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(long long1);

	@Override
	long defaultReturnValue();

	ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<K, Long>> entrySet() {
		return this.object2LongEntrySet();
	}

	@Deprecated
	@Override
	default Long put(K key, Long value) {
		return Object2LongFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Long get(Object key) {
		return Object2LongFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Long remove(Object key) {
		return Object2LongFunction.super.remove(key);
	}

	ObjectSet<K> keySet();

	LongCollection values();

	@Override
	boolean containsKey(Object object);

	boolean containsValue(long long1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Long)value).longValue());
	}

	default long getOrDefault(Object key, long defaultValue) {
		long v;
		return (v = this.getLong(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default long putIfAbsent(K key, long value) {
		long v = this.getLong(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(Object key, long value) {
		long curValue = this.getLong(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.removeLong(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(K key, long oldValue, long newValue) {
		long curValue = this.getLong(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default long replace(K key, long value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default long computeLongIfAbsent(K key, ToLongFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.getLong(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			long newValue = mappingFunction.applyAsLong(key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default long computeLongIfAbsentPartial(K key, Object2LongFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.getLong(key);
		long drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			long newValue = mappingFunction.getLong(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default long computeLongIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.getLong(key);
		long drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Long newValue = (Long)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.removeLong(key);
				return drv;
			} else {
				long newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default long computeLong(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.getLong(key);
		long drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Long newValue = (Long)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.removeLong(key);
			}

			return drv;
		} else {
			long newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default long mergeLong(K key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.getLong(key);
		long drv = this.defaultReturnValue();
		long newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Long mergedValue = (Long)remappingFunction.apply(oldValue, value);
			if (mergedValue == null) {
				this.removeLong(key);
				return drv;
			}

			newValue = mergedValue;
		}

		this.put(key, newValue);
		return newValue;
	}

	@Deprecated
	default Long getOrDefault(Object key, Long defaultValue) {
		return (Long)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Long putIfAbsent(K key, Long value) {
		return (Long)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(K key, Long oldValue, Long newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Long replace(K key, Long value) {
		return (Long)super.replace(key, value);
	}

	@Deprecated
	default Long merge(K key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.merge(key, value, remappingFunction);
	}

	public interface Entry<K> extends java.util.Map.Entry<K, Long> {
		long getLongValue();

		long setValue(long long1);

		@Deprecated
		default Long getValue() {
			return this.getLongValue();
		}

		@Deprecated
		default Long setValue(Long value) {
			return this.setValue(value.longValue());
		}
	}

	public interface FastEntrySet<K> extends ObjectSet<Object2LongMap.Entry<K>> {
		ObjectIterator<Object2LongMap.Entry<K>> fastIterator();

		default void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
			this.forEach(consumer);
		}
	}
}
