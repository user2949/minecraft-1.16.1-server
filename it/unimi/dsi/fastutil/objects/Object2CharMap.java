package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public interface Object2CharMap<K> extends Object2CharFunction<K>, Map<K, Character> {
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

	ObjectSet<Object2CharMap.Entry<K>> object2CharEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<K, Character>> entrySet() {
		return this.object2CharEntrySet();
	}

	@Deprecated
	@Override
	default Character put(K key, Character value) {
		return Object2CharFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Character get(Object key) {
		return Object2CharFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Character remove(Object key) {
		return Object2CharFunction.super.remove(key);
	}

	ObjectSet<K> keySet();

	CharCollection values();

	@Override
	boolean containsKey(Object object);

	boolean containsValue(char character);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Character)value).charValue());
	}

	default char getOrDefault(Object key, char defaultValue) {
		char v;
		return (v = this.getChar(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default char putIfAbsent(K key, char value) {
		char v = this.getChar(key);
		char drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(Object key, char value) {
		char curValue = this.getChar(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.removeChar(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(K key, char oldValue, char newValue) {
		char curValue = this.getChar(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default char replace(K key, char value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default char computeCharIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		char v = this.getChar(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default char computeCharIfAbsentPartial(K key, Object2CharFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		char v = this.getChar(key);
		char drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			char newValue = mappingFunction.getChar(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default char computeCharIfPresent(K key, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		char oldValue = this.getChar(key);
		char drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Character newValue = (Character)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.removeChar(key);
				return drv;
			} else {
				char newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default char computeChar(K key, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		char oldValue = this.getChar(key);
		char drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Character newValue = (Character)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.removeChar(key);
			}

			return drv;
		} else {
			char newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default char mergeChar(K key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		char oldValue = this.getChar(key);
		char drv = this.defaultReturnValue();
		char newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Character mergedValue = (Character)remappingFunction.apply(oldValue, value);
			if (mergedValue == null) {
				this.removeChar(key);
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
	default Character putIfAbsent(K key, Character value) {
		return (Character)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(K key, Character oldValue, Character newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Character replace(K key, Character value) {
		return (Character)super.replace(key, value);
	}

	@Deprecated
	default Character merge(K key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
		return (Character)super.merge(key, value, remappingFunction);
	}

	public interface Entry<K> extends java.util.Map.Entry<K, Character> {
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

	public interface FastEntrySet<K> extends ObjectSet<Object2CharMap.Entry<K>> {
		ObjectIterator<Object2CharMap.Entry<K>> fastIterator();

		default void fastForEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
			this.forEach(consumer);
		}
	}
}
