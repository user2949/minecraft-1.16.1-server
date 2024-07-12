package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public interface Double2DoubleMap extends Double2DoubleFunction, Map<Double, Double> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(double double1);

	@Override
	double defaultReturnValue();

	ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Double, Double>> entrySet() {
		return this.double2DoubleEntrySet();
	}

	@Deprecated
	@Override
	default Double put(Double key, Double value) {
		return Double2DoubleFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Double get(Object key) {
		return Double2DoubleFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Double remove(Object key) {
		return Double2DoubleFunction.super.remove(key);
	}

	DoubleSet keySet();

	DoubleCollection values();

	@Override
	boolean containsKey(double double1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Double2DoubleFunction.super.containsKey(key);
	}

	boolean containsValue(double double1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Double)value).doubleValue());
	}

	default double getOrDefault(double key, double defaultValue) {
		double v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default double putIfAbsent(double key, double value) {
		double v = this.get(key);
		double drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(double key, double value) {
		double curValue = this.get(key);
		if (Double.doubleToLongBits(curValue) == Double.doubleToLongBits(value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(double key, double oldValue, double newValue) {
		double curValue = this.get(key);
		if (Double.doubleToLongBits(curValue) == Double.doubleToLongBits(oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default double replace(double key, double value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default double computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		double v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			double newValue = mappingFunction.applyAsDouble(key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default double computeIfAbsentNullable(double key, DoubleFunction<? extends Double> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		double v = this.get(key);
		double drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Double mappedValue = (Double)mappingFunction.apply(key);
			if (mappedValue == null) {
				return drv;
			} else {
				double newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default double computeIfAbsentPartial(double key, Double2DoubleFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		double v = this.get(key);
		double drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			double newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default double computeIfPresent(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		double oldValue = this.get(key);
		double drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Double newValue = (Double)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				double newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default double compute(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		double oldValue = this.get(key);
		double drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Double newValue = (Double)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			double newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default double merge(double key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		double oldValue = this.get(key);
		double drv = this.defaultReturnValue();
		double newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Double mergedValue = (Double)remappingFunction.apply(oldValue, value);
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
	default Double getOrDefault(Object key, Double defaultValue) {
		return (Double)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Double putIfAbsent(Double key, Double value) {
		return (Double)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Double key, Double oldValue, Double newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Double replace(Double key, Double value) {
		return (Double)super.replace(key, value);
	}

	@Deprecated
	default Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> mappingFunction) {
		return (Double)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		return (Double)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		return (Double)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
		return (Double)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Double, Double> {
		double getDoubleKey();

		@Deprecated
		default Double getKey() {
			return this.getDoubleKey();
		}

		double getDoubleValue();

		double setValue(double double1);

		@Deprecated
		default Double getValue() {
			return this.getDoubleValue();
		}

		@Deprecated
		default Double setValue(Double value) {
			return this.setValue(value.doubleValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Double2DoubleMap.Entry> {
		ObjectIterator<Double2DoubleMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
