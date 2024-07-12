package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;

public interface Long2ReferenceMap<V> extends Long2ReferenceFunction<V>, Map<Long, V> {
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

	ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Long, V>> entrySet() {
		return this.long2ReferenceEntrySet();
	}

	@Deprecated
	@Override
	default V put(Long key, V value) {
		return Long2ReferenceFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default V get(Object key) {
		return Long2ReferenceFunction.super.get(key);
	}

	@Deprecated
	@Override
	default V remove(Object key) {
		return Long2ReferenceFunction.super.remove(key);
	}

	LongSet keySet();

	ReferenceCollection<V> values();

	@Override
	boolean containsKey(long long1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Long2ReferenceFunction.super.containsKey(key);
	}

	default V getOrDefault(long key, V defaultValue) {
		V v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default V putIfAbsent(long key, V value) {
		V v = this.get(key);
		V drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(long key, Object value) {
		V curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(long key, V oldValue, V newValue) {
		V curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default V replace(long key, V value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {
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

	default V computeIfAbsentPartial(long key, Long2ReferenceFunction<? extends V> mappingFunction) {
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

	default V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
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

	default V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
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

	default V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
	default V putIfAbsent(Long key, V value) {
		return (V)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Long key, V oldValue, V newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default V replace(Long key, V value) {
		return (V)super.replace(key, value);
	}

	@Deprecated
	default V computeIfAbsent(Long key, Function<? super Long, ? extends V> mappingFunction) {
		return (V)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default V computeIfPresent(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
		return (V)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default V compute(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
		return (V)super.compute(key, remappingFunction);
	}

	@Deprecated
	default V merge(Long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return (V)super.merge(key, value, remappingFunction);
	}

	public interface Entry<V> extends java.util.Map.Entry<Long, V> {
		long getLongKey();

		@Deprecated
		default Long getKey() {
			return this.getLongKey();
		}
	}

	public interface FastEntrySet<V> extends ObjectSet<Long2ReferenceMap.Entry<V>> {
		ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator();

		default void fastForEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
			this.forEach(consumer);
		}
	}
}
