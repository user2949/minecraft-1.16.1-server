package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

public interface Float2LongMap extends Float2LongFunction, Map<Float, Long> {
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

	ObjectSet<Float2LongMap.Entry> float2LongEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Float, Long>> entrySet() {
		return this.float2LongEntrySet();
	}

	@Deprecated
	@Override
	default Long put(Float key, Long value) {
		return Float2LongFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Long get(Object key) {
		return Float2LongFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Long remove(Object key) {
		return Float2LongFunction.super.remove(key);
	}

	FloatSet keySet();

	LongCollection values();

	@Override
	boolean containsKey(float float1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Float2LongFunction.super.containsKey(key);
	}

	boolean containsValue(long long1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Long)value).longValue());
	}

	default long getOrDefault(float key, long defaultValue) {
		long v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default long putIfAbsent(float key, long value) {
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(float key, long value) {
		long curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(float key, long oldValue, long newValue) {
		long curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default long replace(float key, long value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default long computeIfAbsent(float key, DoubleToLongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			long newValue = mappingFunction.applyAsLong((double)key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default long computeIfAbsentNullable(float key, DoubleFunction<? extends Long> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Long mappedValue = (Long)mappingFunction.apply((double)key);
			if (mappedValue == null) {
				return drv;
			} else {
				long newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default long computeIfAbsentPartial(float key, Float2LongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			long newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default long computeIfPresent(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.get(key);
		long drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Long newValue = (Long)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				long newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default long compute(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.get(key);
		long drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Long newValue = (Long)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			long newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default long merge(float key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.get(key);
		long drv = this.defaultReturnValue();
		long newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Long mergedValue = (Long)remappingFunction.apply(oldValue, value);
			if (mergedValue == null) {
				this.remove(key);
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
	default Long putIfAbsent(Float key, Long value) {
		return (Long)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Float key, Long oldValue, Long newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Long replace(Float key, Long value) {
		return (Long)super.replace(key, value);
	}

	@Deprecated
	default Long computeIfAbsent(Float key, Function<? super Float, ? extends Long> mappingFunction) {
		return (Long)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Long computeIfPresent(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Long compute(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Long merge(Float key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Float, Long> {
		float getFloatKey();

		@Deprecated
		default Float getKey() {
			return this.getFloatKey();
		}

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

	public interface FastEntrySet extends ObjectSet<Float2LongMap.Entry> {
		ObjectIterator<Float2LongMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Float2LongMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
