package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;

public interface Byte2LongMap extends Byte2LongFunction, Map<Byte, Long> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(long long1);

	@Override
	long defaultReturnValue();

	ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Byte, Long>> entrySet() {
		return this.byte2LongEntrySet();
	}

	@Deprecated
	@Override
	default Long put(Byte key, Long value) {
		return Byte2LongFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Long get(Object key) {
		return Byte2LongFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Long remove(Object key) {
		return Byte2LongFunction.super.remove(key);
	}

	ByteSet keySet();

	LongCollection values();

	@Override
	boolean containsKey(byte byte1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Byte2LongFunction.super.containsKey(key);
	}

	boolean containsValue(long long1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Long)value).longValue());
	}

	default long getOrDefault(byte key, long defaultValue) {
		long v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default long putIfAbsent(byte key, long value) {
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(byte key, long value) {
		long curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(byte key, long oldValue, long newValue) {
		long curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default long replace(byte key, long value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default long computeIfAbsent(byte key, IntToLongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			long newValue = mappingFunction.applyAsLong(key);
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default long computeIfAbsentNullable(byte key, IntFunction<? extends Long> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Long mappedValue = (Long)mappingFunction.apply(key);
			if (mappedValue == null) {
				return drv;
			} else {
				long newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default long computeIfAbsentPartial(byte key, Byte2LongFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		long v = this.get(key);
		long drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			long newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default long computeIfPresent(byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.get(key);
		long drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Long newValue = (Long)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				long newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default long compute(byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.get(key);
		long drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Long newValue = (Long)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			long newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default long merge(byte key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		long oldValue = this.get(key);
		long drv = this.defaultReturnValue();
		long newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Long mergedValue = (Long)remappingFunction.apply(oldValue, value);
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
	default Long getOrDefault(Object key, Long defaultValue) {
		return (Long)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Long putIfAbsent(Byte key, Long value) {
		return (Long)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Byte key, Long oldValue, Long newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Long replace(Byte key, Long value) {
		return (Long)super.replace(key, value);
	}

	@Deprecated
	default Long computeIfAbsent(Byte key, Function<? super Byte, ? extends Long> mappingFunction) {
		return (Long)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Long computeIfPresent(Byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Long compute(Byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Long merge(Byte key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
		return (Long)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Byte, Long> {
		byte getByteKey();

		@Deprecated
		default Byte getKey() {
			return this.getByteKey();
		}

		long getLongValue();

		long setValue(long long1);

		@Deprecated
		default Long getValue() {
			return this.getLongValue();
		}

		@Deprecated
		default Long setValue(Long value) {
			return this.setValue(value.longValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Byte2LongMap.Entry> {
		ObjectIterator<Byte2LongMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Byte2LongMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
