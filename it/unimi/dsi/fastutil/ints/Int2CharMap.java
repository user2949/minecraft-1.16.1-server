package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface Int2CharMap extends Int2CharFunction, Map<Integer, Character> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(char character);

	@Override
	char defaultReturnValue();

	ObjectSet<Int2CharMap.Entry> int2CharEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Integer, Character>> entrySet() {
		return this.int2CharEntrySet();
	}

	@Deprecated
	@Override
	default Character put(Integer key, Character value) {
		return Int2CharFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Character get(Object key) {
		return Int2CharFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Character remove(Object key) {
		return Int2CharFunction.super.remove(key);
	}

	IntSet keySet();

	CharCollection values();

	@Override
	boolean containsKey(int integer);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Int2CharFunction.super.containsKey(key);
	}

	boolean containsValue(char character);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Character)value).charValue());
	}

	default char getOrDefault(int key, char defaultValue) {
		char v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default char putIfAbsent(int key, char value) {
		char v = this.get(key);
		char drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(int key, char value) {
		char curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(int key, char oldValue, char newValue) {
		char curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default char replace(int key, char value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default char computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		char v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default char computeIfAbsentNullable(int key, IntFunction<? extends Character> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		char v = this.get(key);
		char drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Character mappedValue = (Character)mappingFunction.apply(key);
			if (mappedValue == null) {
				return drv;
			} else {
				char newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default char computeIfAbsentPartial(int key, Int2CharFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		char v = this.get(key);
		char drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			char newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default char computeIfPresent(int key, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		char oldValue = this.get(key);
		char drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Character newValue = (Character)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				char newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default char compute(int key, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		char oldValue = this.get(key);
		char drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Character newValue = (Character)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			char newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default char merge(int key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		char oldValue = this.get(key);
		char drv = this.defaultReturnValue();
		char newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Character mergedValue = (Character)remappingFunction.apply(oldValue, value);
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
	default Character getOrDefault(Object key, Character defaultValue) {
		return (Character)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Character putIfAbsent(Integer key, Character value) {
		return (Character)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Integer key, Character oldValue, Character newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Character replace(Integer key, Character value) {
		return (Character)super.replace(key, value);
	}

	@Deprecated
	default Character computeIfAbsent(Integer key, Function<? super Integer, ? extends Character> mappingFunction) {
		return (Character)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Character computeIfPresent(Integer key, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Character compute(Integer key, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Character merge(Integer key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Integer, Character> {
		int getIntKey();

		@Deprecated
		default Integer getKey() {
			return this.getIntKey();
		}

		char getCharValue();

		char setValue(char character);

		@Deprecated
		default Character getValue() {
			return this.getCharValue();
		}

		@Deprecated
		default Character setValue(Character value) {
			return this.setValue(value.charValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Int2CharMap.Entry> {
		ObjectIterator<Int2CharMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Int2CharMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
