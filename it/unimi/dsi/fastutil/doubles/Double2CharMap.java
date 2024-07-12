package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public interface Double2CharMap extends Double2CharFunction, Map<Double, Character> {
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

	ObjectSet<Double2CharMap.Entry> double2CharEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Double, Character>> entrySet() {
		return this.double2CharEntrySet();
	}

	@Deprecated
	@Override
	default Character put(Double key, Character value) {
		return Double2CharFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Character get(Object key) {
		return Double2CharFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Character remove(Object key) {
		return Double2CharFunction.super.remove(key);
	}

	DoubleSet keySet();

	CharCollection values();

	@Override
	boolean containsKey(double double1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Double2CharFunction.super.containsKey(key);
	}

	boolean containsValue(char character);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Character)value).charValue());
	}

	default char getOrDefault(double key, char defaultValue) {
		char v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default char putIfAbsent(double key, char value) {
		char v = this.get(key);
		char drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(double key, char value) {
		char curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(double key, char oldValue, char newValue) {
		char curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default char replace(double key, char value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default char computeIfAbsent(double key, DoubleToIntFunction mappingFunction) {
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

	default char computeIfAbsentNullable(double key, DoubleFunction<? extends Character> mappingFunction) {
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

	default char computeIfAbsentPartial(double key, Double2CharFunction mappingFunction) {
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

	default char computeIfPresent(double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
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

	default char compute(double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
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

	default char merge(double key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
	default Character putIfAbsent(Double key, Character value) {
		return (Character)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Double key, Character oldValue, Character newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Character replace(Double key, Character value) {
		return (Character)super.replace(key, value);
	}

	@Deprecated
	default Character computeIfAbsent(Double key, Function<? super Double, ? extends Character> mappingFunction) {
		return (Character)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Character computeIfPresent(Double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Character compute(Double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Character merge(Double key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Double, Character> {
		double getDoubleKey();

		@Deprecated
		default Double getKey() {
			return this.getDoubleKey();
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

	public interface FastEntrySet extends ObjectSet<Double2CharMap.Entry> {
		ObjectIterator<Double2CharMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
