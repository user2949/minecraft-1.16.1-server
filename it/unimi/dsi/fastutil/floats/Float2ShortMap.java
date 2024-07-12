package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public interface Float2ShortMap extends Float2ShortFunction, Map<Float, Short> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(short short1);

	@Override
	short defaultReturnValue();

	ObjectSet<Float2ShortMap.Entry> float2ShortEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Float, Short>> entrySet() {
		return this.float2ShortEntrySet();
	}

	@Deprecated
	@Override
	default Short put(Float key, Short value) {
		return Float2ShortFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Short get(Object key) {
		return Float2ShortFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Short remove(Object key) {
		return Float2ShortFunction.super.remove(key);
	}

	FloatSet keySet();

	ShortCollection values();

	@Override
	boolean containsKey(float float1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Float2ShortFunction.super.containsKey(key);
	}

	boolean containsValue(short short1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Short)value).shortValue());
	}

	default short getOrDefault(float key, short defaultValue) {
		short v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default short putIfAbsent(float key, short value) {
		short v = this.get(key);
		short drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(float key, short value) {
		short curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(float key, short oldValue, short newValue) {
		short curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default short replace(float key, short value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default short computeIfAbsent(float key, DoubleToIntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt((double)key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default short computeIfAbsentNullable(float key, DoubleFunction<? extends Short> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.get(key);
		short drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Short mappedValue = (Short)mappingFunction.apply((double)key);
			if (mappedValue == null) {
				return drv;
			} else {
				short newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default short computeIfAbsentPartial(float key, Float2ShortFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.get(key);
		short drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			short newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default short computeIfPresent(float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		short oldValue = this.get(key);
		short drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Short newValue = (Short)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				short newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default short compute(float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		short oldValue = this.get(key);
		short drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Short newValue = (Short)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			short newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default short merge(float key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		short oldValue = this.get(key);
		short drv = this.defaultReturnValue();
		short newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Short mergedValue = (Short)remappingFunction.apply(oldValue, value);
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
	default Short getOrDefault(Object key, Short defaultValue) {
		return (Short)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Short putIfAbsent(Float key, Short value) {
		return (Short)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Float key, Short oldValue, Short newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Short replace(Float key, Short value) {
		return (Short)super.replace(key, value);
	}

	@Deprecated
	default Short computeIfAbsent(Float key, Function<? super Float, ? extends Short> mappingFunction) {
		return (Short)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Short computeIfPresent(Float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Short compute(Float key, BiFunction<? super Float, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Short merge(Float key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Float, Short> {
		float getFloatKey();

		@Deprecated
		default Float getKey() {
			return this.getFloatKey();
		}

		short getShortValue();

		short setValue(short short1);

		@Deprecated
		default Short getValue() {
			return this.getShortValue();
		}

		@Deprecated
		default Short setValue(Short value) {
			return this.setValue(value.shortValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Float2ShortMap.Entry> {
		ObjectIterator<Float2ShortMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Float2ShortMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
