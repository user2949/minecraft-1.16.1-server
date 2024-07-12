package it.unimi.dsi.fastutil.doubles;

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

public interface Double2LongMap extends Double2LongFunction, Map<Double, Long> {
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

	ObjectSet<Double2LongMap.Entry> double2LongEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Double, Long>> entrySet() {
		return this.double2LongEntrySet();
	}

	@Deprecated
	@Override
	default Long put(Double key, Long value) {
		return Double2LongFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Long get(Object key) {
		return Double2LongFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Long remove(Object key) {
		return Double2LongFunction.super.remove(key);
	}

	DoubleSet keySet();

	LongCollection values();

	@Override
	boolean containsKey(double double1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Double2LongFunction.super.containsKey(key);
	}

	boolean containsValue(long long1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Long)value).longValue());
	}

	default long getOrDefault(double key, long defaultValue) {
		long v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default long putIfAbsent(double key, long value) {
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(double key, long value) {
		long curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(double key, long oldValue, long newValue) {
		long curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default long replace(double key, long value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default long computeIfAbsent(double key, DoubleToLongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			long newValue = mappingFunction.applyAsLong(key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default long computeIfAbsentNullable(double key, DoubleFunction<? extends Long> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Long mappedValue = (Long)mappingFunction.apply(key);
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

	default long computeIfAbsentPartial(double key, Double2LongFunction mappingFunction) {
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

	default long computeIfPresent(double key, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
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

	default long compute(double key, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
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

	default long merge(double key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
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
	default Long putIfAbsent(Double key, Long value) {
		return (Long)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Double key, Long oldValue, Long newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Long replace(Double key, Long value) {
		return (Long)super.replace(key, value);
	}

	@Deprecated
	default Long computeIfAbsent(Double key, Function<? super Double, ? extends Long> mappingFunction) {
		return (Long)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Long computeIfPresent(Double key, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Long compute(Double key, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Long merge(Double key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Double, Long> {
		double getDoubleKey();

		@Deprecated
		default Double getKey() {
			return this.getDoubleKey();
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

	public interface FastEntrySet extends ObjectSet<Double2LongMap.Entry> {
		ObjectIterator<Double2LongMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Double2LongMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
