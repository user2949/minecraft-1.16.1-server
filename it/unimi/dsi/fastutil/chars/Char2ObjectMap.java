package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface Char2ObjectMap<V> extends Char2ObjectFunction<V>, Map<Character, V> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(V object);

	@Override
	V defaultReturnValue();

	ObjectSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Character, V>> entrySet() {
		return this.char2ObjectEntrySet();
	}

	@Deprecated
	@Override
	default V put(Character key, V value) {
		return Char2ObjectFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default V get(Object key) {
		return Char2ObjectFunction.super.get(key);
	}

	@Deprecated
	@Override
	default V remove(Object key) {
		return Char2ObjectFunction.super.remove(key);
	}

	CharSet keySet();

	ObjectCollection<V> values();

	@Override
	boolean containsKey(char character);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Char2ObjectFunction.super.containsKey(key);
	}

	default V getOrDefault(char key, V defaultValue) {
		V v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default V putIfAbsent(char key, V value) {
		V v = this.get(key);
		V drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(char key, Object value) {
		V curValue = this.get(key);
		if (Objects.equals(curValue, value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(char key, V oldValue, V newValue) {
		V curValue = this.get(key);
		if (Objects.equals(curValue, oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default V replace(char key, V value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		V v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			V newValue = (V)mappingFunction.apply(key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default V computeIfAbsentPartial(char key, Char2ObjectFunction<? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		V v = this.get(key);
		V drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			V newValue = (V)mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue = this.get(key);
		V drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			V newValue = (V)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				this.put(key, newValue);
				return newValue;
			}
		}
	}

	default V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue = this.get(key);
		V drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		V newValue = (V)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			this.put(key, newValue);
			return newValue;
		}
	}

	default V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		Objects.requireNonNull(value);
		V oldValue = this.get(key);
		V drv = this.defaultReturnValue();
		V newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			V mergedValue = (V)remappingFunction.apply(oldValue, value);
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
	default V getOrDefault(Object key, V defaultValue) {
		return (V)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default V putIfAbsent(Character key, V value) {
		return (V)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Character key, V oldValue, V newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default V replace(Character key, V value) {
		return (V)super.replace(key, value);
	}

	@Deprecated
	default V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
		return (V)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
		return (V)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
		return (V)super.compute(key, remappingFunction);
	}

	@Deprecated
	default V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return (V)super.merge(key, value, remappingFunction);
	}

	public interface Entry<V> extends java.util.Map.Entry<Character, V> {
		char getCharKey();

		@Deprecated
		default Character getKey() {
			return this.getCharKey();
		}
	}

	public interface FastEntrySet<V> extends ObjectSet<Char2ObjectMap.Entry<V>> {
		ObjectIterator<Char2ObjectMap.Entry<V>> fastIterator();

		default void fastForEach(Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
			this.forEach(consumer);
		}
	}
}
