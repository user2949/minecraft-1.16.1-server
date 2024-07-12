package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public interface Float2ReferenceMap<V> extends Float2ReferenceFunction<V>, Map<Float, V> {
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

	ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Float, V>> entrySet() {
		return this.float2ReferenceEntrySet();
	}

	@Deprecated
	@Override
	default V put(Float key, V value) {
		return Float2ReferenceFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default V get(Object key) {
		return Float2ReferenceFunction.super.get(key);
	}

	@Deprecated
	@Override
	default V remove(Object key) {
		return Float2ReferenceFunction.super.remove(key);
	}

	FloatSet keySet();

	ReferenceCollection<V> values();

	@Override
	boolean containsKey(float float1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Float2ReferenceFunction.super.containsKey(key);
	}

	default V getOrDefault(float key, V defaultValue) {
		V v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default V putIfAbsent(float key, V value) {
		V v = this.get(key);
		V drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(float key, Object value) {
		V curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(float key, V oldValue, V newValue) {
		V curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default V replace(float key, V value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default V computeIfAbsent(float key, DoubleFunction<? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		V v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			V newValue = (V)mappingFunction.apply((double)key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default V computeIfAbsentPartial(float key, Float2ReferenceFunction<? extends V> mappingFunction) {
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

	default V computeIfPresent(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
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

	default V compute(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
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

	default V merge(float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
	default V putIfAbsent(Float key, V value) {
		return (V)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Float key, V oldValue, V newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default V replace(Float key, V value) {
		return (V)super.replace(key, value);
	}

	@Deprecated
	default V computeIfAbsent(Float key, Function<? super Float, ? extends V> mappingFunction) {
		return (V)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default V computeIfPresent(Float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
		return (V)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default V compute(Float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
		return (V)super.compute(key, remappingFunction);
	}

	@Deprecated
	default V merge(Float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return (V)super.merge(key, value, remappingFunction);
	}

	public interface Entry<V> extends java.util.Map.Entry<Float, V> {
		float getFloatKey();

		@Deprecated
		default Float getKey() {
			return this.getFloatKey();
		}
	}

	public interface FastEntrySet<V> extends ObjectSet<Float2ReferenceMap.Entry<V>> {
		ObjectIterator<Float2ReferenceMap.Entry<V>> fastIterator();

		default void fastForEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
			this.forEach(consumer);
		}
	}
}
