package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface Int2IntMap extends Int2IntFunction, Map<Integer, Integer> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(int integer);

	@Override
	int defaultReturnValue();

	ObjectSet<Int2IntMap.Entry> int2IntEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Integer, Integer>> entrySet() {
		return this.int2IntEntrySet();
	}

	@Deprecated
	@Override
	default Integer put(Integer key, Integer value) {
		return Int2IntFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Integer get(Object key) {
		return Int2IntFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Integer remove(Object key) {
		return Int2IntFunction.super.remove(key);
	}

	IntSet keySet();

	IntCollection values();

	@Override
	boolean containsKey(int integer);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Int2IntFunction.super.containsKey(key);
	}

	boolean containsValue(int integer);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Integer)value).intValue());
	}

	default int getOrDefault(int key, int defaultValue) {
		int v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default int putIfAbsent(int key, int value) {
		int v = this.get(key);
		int drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(int key, int value) {
		int curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(int key, int oldValue, int newValue) {
		int curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default int replace(int key, int value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default int computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			int newValue = mappingFunction.applyAsInt(key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default int computeIfAbsentNullable(int key, IntFunction<? extends Integer> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int v = this.get(key);
		int drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Integer mappedValue = (Integer)mappingFunction.apply(key);
			if (mappedValue == null) {
				return drv;
			} else {
				int newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default int computeIfAbsentPartial(int key, Int2IntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		int v = this.get(key);
		int drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			int newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default int computeIfPresent(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int oldValue = this.get(key);
		int drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Integer newValue = (Integer)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				int newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default int compute(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int oldValue = this.get(key);
		int drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Integer newValue = (Integer)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			int newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default int merge(int key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		int oldValue = this.get(key);
		int drv = this.defaultReturnValue();
		int newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Integer mergedValue = (Integer)remappingFunction.apply(oldValue, value);
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
	default Integer getOrDefault(Object key, Integer defaultValue) {
		return (Integer)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Integer putIfAbsent(Integer key, Integer value) {
		return (Integer)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Integer key, Integer oldValue, Integer newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Integer replace(Integer key, Integer value) {
		return (Integer)super.replace(key, value);
	}

	@Deprecated
	default Integer computeIfAbsent(Integer key, Function<? super Integer, ? extends Integer> mappingFunction) {
		return (Integer)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Integer computeIfPresent(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		return (Integer)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Integer compute(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		return (Integer)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Integer merge(Integer key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
		return (Integer)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Integer, Integer> {
		int getIntKey();

		@Deprecated
		default Integer getKey() {
			return this.getIntKey();
		}

		int getIntValue();

		int setValue(int integer);

		@Deprecated
		default Integer getValue() {
			return this.getIntValue();
		}

		@Deprecated
		default Integer setValue(Integer value) {
			return this.setValue(value.intValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Int2IntMap.Entry> {
		ObjectIterator<Int2IntMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
