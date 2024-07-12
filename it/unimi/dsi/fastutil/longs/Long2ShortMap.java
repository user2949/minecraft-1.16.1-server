package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

public interface Long2ShortMap extends Long2ShortFunction, Map<Long, Short> {
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

	ObjectSet<Long2ShortMap.Entry> long2ShortEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Long, Short>> entrySet() {
		return this.long2ShortEntrySet();
	}

	@Deprecated
	@Override
	default Short put(Long key, Short value) {
		return Long2ShortFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Short get(Object key) {
		return Long2ShortFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Short remove(Object key) {
		return Long2ShortFunction.super.remove(key);
	}

	LongSet keySet();

	ShortCollection values();

	@Override
	boolean containsKey(long long1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Long2ShortFunction.super.containsKey(key);
	}

	boolean containsValue(short short1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Short)value).shortValue());
	}

	default short getOrDefault(long key, short defaultValue) {
		short v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default short putIfAbsent(long key, short value) {
		short v = this.get(key);
		short drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(long key, short value) {
		short curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(long key, short oldValue, short newValue) {
		short curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default short replace(long key, short value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default short computeIfAbsent(long key, LongToIntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default short computeIfAbsentNullable(long key, LongFunction<? extends Short> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		short v = this.get(key);
		short drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Short mappedValue = (Short)mappingFunction.apply(key);
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

	default short computeIfAbsentPartial(long key, Long2ShortFunction mappingFunction) {
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

	default short computeIfPresent(long key, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
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

	default short compute(long key, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
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

	default short merge(long key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
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
	default Short putIfAbsent(Long key, Short value) {
		return (Short)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Long key, Short oldValue, Short newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Short replace(Long key, Short value) {
		return (Short)super.replace(key, value);
	}

	@Deprecated
	default Short computeIfAbsent(Long key, Function<? super Long, ? extends Short> mappingFunction) {
		return (Short)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Short computeIfPresent(Long key, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Short compute(Long key, BiFunction<? super Long, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Short merge(Long key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
		return (Short)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Long, Short> {
		long getLongKey();

		@Deprecated
		default Long getKey() {
			return this.getLongKey();
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

	public interface FastEntrySet extends ObjectSet<Long2ShortMap.Entry> {
		ObjectIterator<Long2ShortMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Long2ShortMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
