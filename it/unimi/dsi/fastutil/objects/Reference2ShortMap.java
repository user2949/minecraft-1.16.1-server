package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public interface Reference2ShortMap<K> extends Reference2ShortFunction<K>, Map<K, Short> {
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

	ObjectSet<Reference2ShortMap.Entry<K>> reference2ShortEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<K, Short>> entrySet() {
		return this.reference2ShortEntrySet();
	}

	@Deprecated
	@Override
	default Short put(K key, Short value) {
		return Reference2ShortFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Short get(Object key) {
		return Reference2ShortFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Short remove(Object key) {
		return Reference2ShortFunction.super.remove(key);
	}

	ReferenceSet<K> keySet();

	ShortCollection values();

	@Override
	boolean containsKey(Object object);

	boolean containsValue(short short1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Short)value).shortValue());
	}

	default short getOrDefault(Object key, short defaultValue) {
		short v;
		return (v = this.getShort(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default short putIfAbsent(K key, short value) {
		short v = this.getShort(key);
		short drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(Object key, short value) {
		short curValue = this.getShort(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.removeShort(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(K key, short oldValue, short newValue) {
		short curValue = this.getShort(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default short replace(K key, short value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.getShort(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default short computeShortIfAbsentPartial(K key, Reference2ShortFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.getShort(key);
		short drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			short newValue = mappingFunction.getShort(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		short oldValue = this.getShort(key);
		short drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Short newValue = (Short)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.removeShort(key);
				return drv;
			} else {
				short newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		short oldValue = this.getShort(key);
		short drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Short newValue = (Short)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.removeShort(key);
			}

			return drv;
		} else {
			short newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		short oldValue = this.getShort(key);
		short drv = this.defaultReturnValue();
		short newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Short mergedValue = (Short)remappingFunction.apply(oldValue, value);
			if (mergedValue == null) {
				this.removeShort(key);
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
	default Short putIfAbsent(K key, Short value) {
		return (Short)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(K key, Short oldValue, Short newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Short replace(K key, Short value) {
		return (Short)super.replace(key, value);
	}

	@Deprecated
	default Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.merge(key, value, remappingFunction);
	}

	public interface Entry<K> extends java.util.Map.Entry<K, Short> {
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

	public interface FastEntrySet<K> extends ObjectSet<Reference2ShortMap.Entry<K>> {
		ObjectIterator<Reference2ShortMap.Entry<K>> fastIterator();

		default void fastForEach(Consumer<? super Reference2ShortMap.Entry<K>> consumer) {
			this.forEach(consumer);
		}
	}
}
