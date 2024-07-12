package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

public interface Reference2FloatMap<K> extends Reference2FloatFunction<K>, Map<K, Float> {
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

	ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<K, Float>> entrySet() {
		return this.reference2FloatEntrySet();
	}

	@Deprecated
	@Override
	default Float put(K key, Float value) {
		return Reference2FloatFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Float get(Object key) {
		return Reference2FloatFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Float remove(Object key) {
		return Reference2FloatFunction.super.remove(key);
	}

	ReferenceSet<K> keySet();

	FloatCollection values();

	@Override
	boolean containsKey(Object object);

	boolean containsValue(float float1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Float)value).floatValue());
	}

	default float getOrDefault(Object key, float defaultValue) {
		float v;
		return (v = this.getFloat(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default float putIfAbsent(K key, float value) {
		float v = this.getFloat(key);
		float drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(Object key, float value) {
		float curValue = this.getFloat(key);
		if (Float.floatToIntBits(curValue) == Float.floatToIntBits(value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.removeFloat(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(K key, float oldValue, float newValue) {
		float curValue = this.getFloat(key);
		if (Float.floatToIntBits(curValue) == Float.floatToIntBits(oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default float replace(K key, float value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		float v = this.getFloat(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		float v = this.getFloat(key);
		float drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			float newValue = mappingFunction.getFloat(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		float oldValue = this.getFloat(key);
		float drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Float newValue = (Float)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.removeFloat(key);
				return drv;
			} else {
				float newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		float oldValue = this.getFloat(key);
		float drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Float newValue = (Float)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.removeFloat(key);
			}

			return drv;
		} else {
			float newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		float oldValue = this.getFloat(key);
		float drv = this.defaultReturnValue();
		float newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Float mergedValue = (Float)remappingFunction.apply(oldValue, value);
			if (mergedValue == null) {
				this.removeFloat(key);
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
	default Float putIfAbsent(K key, Float value) {
		return (Float)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(K key, Float oldValue, Float newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Float replace(K key, Float value) {
		return (Float)super.replace(key, value);
	}

	@Deprecated
	default Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
		return (Float)super.merge(key, value, remappingFunction);
	}

	public interface Entry<K> extends java.util.Map.Entry<K, Float> {
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

	public interface FastEntrySet<K> extends ObjectSet<Reference2FloatMap.Entry<K>> {
		ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator();

		default void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
			this.forEach(consumer);
		}
	}
}
