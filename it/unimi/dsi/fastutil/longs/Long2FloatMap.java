package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToDoubleFunction;

public interface Long2FloatMap extends Long2FloatFunction, Map<Long, Float> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(float float1);

	@Override
	float defaultReturnValue();

	ObjectSet<Long2FloatMap.Entry> long2FloatEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Long, Float>> entrySet() {
		return this.long2FloatEntrySet();
	}

	@Deprecated
	@Override
	default Float put(Long key, Float value) {
		return Long2FloatFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Float get(Object key) {
		return Long2FloatFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Float remove(Object key) {
		return Long2FloatFunction.super.remove(key);
	}

	LongSet keySet();

	FloatCollection values();

	@Override
	boolean containsKey(long long1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Long2FloatFunction.super.containsKey(key);
	}

	boolean containsValue(float float1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Float)value).floatValue());
	}

	default float getOrDefault(long key, float defaultValue) {
		float v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default float putIfAbsent(long key, float value) {
		float v = this.get(key);
		float drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(long key, float value) {
		float curValue = this.get(key);
		if (Float.floatToIntBits(curValue) == Float.floatToIntBits(value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(long key, float oldValue, float newValue) {
		float curValue = this.get(key);
		if (Float.floatToIntBits(curValue) == Float.floatToIntBits(oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default float replace(long key, float value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default float computeIfAbsent(long key, LongToDoubleFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		float v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default float computeIfAbsentNullable(long key, LongFunction<? extends Float> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		float v = this.get(key);
		float drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Float mappedValue = (Float)mappingFunction.apply(key);
			if (mappedValue == null) {
				return drv;
			} else {
				float newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default float computeIfAbsentPartial(long key, Long2FloatFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		float v = this.get(key);
		float drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			float newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default float computeIfPresent(long key, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		float oldValue = this.get(key);
		float drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Float newValue = (Float)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				float newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default float compute(long key, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		float oldValue = this.get(key);
		float drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Float newValue = (Float)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			float newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default float merge(long key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		float oldValue = this.get(key);
		float drv = this.defaultReturnValue();
		float newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Float mergedValue = (Float)remappingFunction.apply(oldValue, value);
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
	default Float getOrDefault(Object key, Float defaultValue) {
		return (Float)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Float putIfAbsent(Long key, Float value) {
		return (Float)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Long key, Float oldValue, Float newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Float replace(Long key, Float value) {
		return (Float)super.replace(key, value);
	}

	@Deprecated
	default Float computeIfAbsent(Long key, Function<? super Long, ? extends Float> mappingFunction) {
		return (Float)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Float computeIfPresent(Long key, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
		return (Float)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Float compute(Long key, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
		return (Float)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Float merge(Long key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
		return (Float)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Long, Float> {
		long getLongKey();

		@Deprecated
		default Long getKey() {
			return this.getLongKey();
		}

		float getFloatValue();

		float setValue(float float1);

		@Deprecated
		default Float getValue() {
			return this.getFloatValue();
		}

		@Deprecated
		default Float setValue(Float value) {
			return this.setValue(value.floatValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Long2FloatMap.Entry> {
		ObjectIterator<Long2FloatMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Long2FloatMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
