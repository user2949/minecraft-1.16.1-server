package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

public interface Long2ByteMap extends Long2ByteFunction, Map<Long, Byte> {
	@Override
	int size();

	@Override
	default void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	void defaultReturnValue(byte byte1);

	@Override
	byte defaultReturnValue();

	ObjectSet<Long2ByteMap.Entry> long2ByteEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Long, Byte>> entrySet() {
		return this.long2ByteEntrySet();
	}

	@Deprecated
	@Override
	default Byte put(Long key, Byte value) {
		return Long2ByteFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Byte get(Object key) {
		return Long2ByteFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Byte remove(Object key) {
		return Long2ByteFunction.super.remove(key);
	}

	LongSet keySet();

	ByteCollection values();

	@Override
	boolean containsKey(long long1);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Long2ByteFunction.super.containsKey(key);
	}

	boolean containsValue(byte byte1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Byte)value).byteValue());
	}

	default byte getOrDefault(long key, byte defaultValue) {
		byte v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default byte putIfAbsent(long key, byte value) {
		byte v = this.get(key);
		byte drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(long key, byte value) {
		byte curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(long key, byte oldValue, byte newValue) {
		byte curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default byte replace(long key, byte value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default byte computeIfAbsent(long key, LongToIntFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		byte v = this.get(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default byte computeIfAbsentNullable(long key, LongFunction<? extends Byte> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		byte v = this.get(key);
		byte drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			Byte mappedValue = (Byte)mappingFunction.apply(key);
			if (mappedValue == null) {
				return drv;
			} else {
				byte newValue = mappedValue;
				this.put(key, newValue);
				return newValue;
			}
		} else {
			return v;
		}
	}

	default byte computeIfAbsentPartial(long key, Long2ByteFunction mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		byte v = this.get(key);
		byte drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			byte newValue = mappingFunction.get(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default byte computeIfPresent(long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		byte oldValue = this.get(key);
		byte drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Byte newValue = (Byte)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.remove(key);
				return drv;
			} else {
				byte newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default byte compute(long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		byte oldValue = this.get(key);
		byte drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Byte newValue = (Byte)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.remove(key);
			}

			return drv;
		} else {
			byte newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default byte merge(long key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		byte oldValue = this.get(key);
		byte drv = this.defaultReturnValue();
		byte newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Byte mergedValue = (Byte)remappingFunction.apply(oldValue, value);
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
	default Byte getOrDefault(Object key, Byte defaultValue) {
		return (Byte)super.getOrDefault(key, defaultValue);
	}

	@Deprecated
	default Byte putIfAbsent(Long key, Byte value) {
		return (Byte)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Long key, Byte oldValue, Byte newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Byte replace(Long key, Byte value) {
		return (Byte)super.replace(key, value);
	}

	@Deprecated
	default Byte computeIfAbsent(Long key, Function<? super Long, ? extends Byte> mappingFunction) {
		return (Byte)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Byte computeIfPresent(Long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Byte compute(Long key, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Byte merge(Long key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Long, Byte> {
		long getLongKey();

		@Deprecated
		default Long getKey() {
			return this.getLongKey();
		}

		byte getByteValue();

		byte setValue(byte byte1);

		@Deprecated
		default Byte getValue() {
			return this.getByteValue();
		}

		@Deprecated
		default Byte setValue(Byte value) {
			return this.setValue(value.byteValue());
		}
	}

	public interface FastEntrySet extends ObjectSet<Long2ByteMap.Entry> {
		ObjectIterator<Long2ByteMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Long2ByteMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}
