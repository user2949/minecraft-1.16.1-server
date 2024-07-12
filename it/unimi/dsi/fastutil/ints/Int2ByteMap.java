package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface Int2ByteMap extends Int2ByteFunction, Map<Integer, Byte> {
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

	ObjectSet<Int2ByteMap.Entry> int2ByteEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<Integer, Byte>> entrySet() {
		return this.int2ByteEntrySet();
	}

	@Deprecated
	@Override
	default Byte put(Integer key, Byte value) {
		return Int2ByteFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Byte get(Object key) {
		return Int2ByteFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Byte remove(Object key) {
		return Int2ByteFunction.super.remove(key);
	}

	IntSet keySet();

	ByteCollection values();

	@Override
	boolean containsKey(int integer);

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return Int2ByteFunction.super.containsKey(key);
	}

	boolean containsValue(byte byte1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Byte)value).byteValue());
	}

	default byte getOrDefault(int key, byte defaultValue) {
		byte v;
		return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default byte putIfAbsent(int key, byte value) {
		byte v = this.get(key);
		byte drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(int key, byte value) {
		byte curValue = this.get(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.remove(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(int key, byte oldValue, byte newValue) {
		byte curValue = this.get(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default byte replace(int key, byte value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default byte computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
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

	default byte computeIfAbsentNullable(int key, IntFunction<? extends Byte> mappingFunction) {
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

	default byte computeIfAbsentPartial(int key, Int2ByteFunction mappingFunction) {
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

	default byte computeIfPresent(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
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

	default byte compute(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
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

	default byte merge(int key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
	default Byte putIfAbsent(Integer key, Byte value) {
		return (Byte)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(Integer key, Byte oldValue, Byte newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Byte replace(Integer key, Byte value) {
		return (Byte)super.replace(key, value);
	}

	@Deprecated
	default Byte computeIfAbsent(Integer key, Function<? super Integer, ? extends Byte> mappingFunction) {
		return (Byte)super.computeIfAbsent(key, mappingFunction);
	}

	@Deprecated
	default Byte computeIfPresent(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.computeIfPresent(key, remappingFunction);
	}

	@Deprecated
	default Byte compute(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.compute(key, remappingFunction);
	}

	@Deprecated
	default Byte merge(Integer key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.merge(key, value, remappingFunction);
	}

	public interface Entry extends java.util.Map.Entry<Integer, Byte> {
		int getIntKey();

		@Deprecated
		default Integer getKey() {
			return this.getIntKey();
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

	public interface FastEntrySet extends ObjectSet<Int2ByteMap.Entry> {
		ObjectIterator<Int2ByteMap.Entry> fastIterator();

		default void fastForEach(Consumer<? super Int2ByteMap.Entry> consumer) {
			this.forEach(consumer);
		}
	}
}