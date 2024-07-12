package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public interface Reference2ByteMap<K> extends Reference2ByteFunction<K>, Map<K, Byte> {
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

	ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet();

	@Deprecated
	default ObjectSet<java.util.Map.Entry<K, Byte>> entrySet() {
		return this.reference2ByteEntrySet();
	}

	@Deprecated
	@Override
	default Byte put(K key, Byte value) {
		return Reference2ByteFunction.super.put(key, value);
	}

	@Deprecated
	@Override
	default Byte get(Object key) {
		return Reference2ByteFunction.super.get(key);
	}

	@Deprecated
	@Override
	default Byte remove(Object key) {
		return Reference2ByteFunction.super.remove(key);
	}

	ReferenceSet<K> keySet();

	ByteCollection values();

	@Override
	boolean containsKey(Object object);

	boolean containsValue(byte byte1);

	@Deprecated
	default boolean containsValue(Object value) {
		return value == null ? false : this.containsValue(((Byte)value).byteValue());
	}

	default byte getOrDefault(Object key, byte defaultValue) {
		byte v;
		return (v = this.getByte(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
	}

	default byte putIfAbsent(K key, byte value) {
		byte v = this.getByte(key);
		byte drv = this.defaultReturnValue();
		if (v == drv && !this.containsKey(key)) {
			this.put(key, value);
			return drv;
		} else {
			return v;
		}
	}

	default boolean remove(Object key, byte value) {
		byte curValue = this.getByte(key);
		if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.removeByte(key);
			return true;
		} else {
			return false;
		}
	}

	default boolean replace(K key, byte oldValue, byte newValue) {
		byte curValue = this.getByte(key);
		if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
			this.put(key, newValue);
			return true;
		} else {
			return false;
		}
	}

	default byte replace(K key, byte value) {
		return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
	}

	default byte computeByteIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		byte v = this.getByte(key);
		if (v == this.defaultReturnValue() && !this.containsKey(key)) {
			byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(key));
			this.put(key, newValue);
			return newValue;
		} else {
			return v;
		}
	}

	default byte computeByteIfAbsentPartial(K key, Reference2ByteFunction<? super K> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		byte v = this.getByte(key);
		byte drv = this.defaultReturnValue();
		if (v != drv || this.containsKey(key)) {
			return v;
		} else if (!mappingFunction.containsKey(key)) {
			return drv;
		} else {
			byte newValue = mappingFunction.getByte(key);
			this.put(key, newValue);
			return newValue;
		}
	}

	default byte computeByteIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		byte oldValue = this.getByte(key);
		byte drv = this.defaultReturnValue();
		if (oldValue == drv && !this.containsKey(key)) {
			return drv;
		} else {
			Byte newValue = (Byte)remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				this.removeByte(key);
				return drv;
			} else {
				byte newVal = newValue;
				this.put(key, newVal);
				return newVal;
			}
		}
	}

	default byte computeByte(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		byte oldValue = this.getByte(key);
		byte drv = this.defaultReturnValue();
		boolean contained = oldValue != drv || this.containsKey(key);
		Byte newValue = (Byte)remappingFunction.apply(key, contained ? oldValue : null);
		if (newValue == null) {
			if (contained) {
				this.removeByte(key);
			}

			return drv;
		} else {
			byte newVal = newValue;
			this.put(key, newVal);
			return newVal;
		}
	}

	default byte mergeByte(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		byte oldValue = this.getByte(key);
		byte drv = this.defaultReturnValue();
		byte newValue;
		if (oldValue == drv && !this.containsKey(key)) {
			newValue = value;
		} else {
			Byte mergedValue = (Byte)remappingFunction.apply(oldValue, value);
			if (mergedValue == null) {
				this.removeByte(key);
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
	default Byte putIfAbsent(K key, Byte value) {
		return (Byte)super.putIfAbsent(key, value);
	}

	@Deprecated
	default boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}

	@Deprecated
	default boolean replace(K key, Byte oldValue, Byte newValue) {
		return super.replace(key, oldValue, newValue);
	}

	@Deprecated
	default Byte replace(K key, Byte value) {
		return (Byte)super.replace(key, value);
	}

	@Deprecated
	default Byte merge(K key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
		return (Byte)super.merge(key, value, remappingFunction);
	}

	public interface Entry<K> extends java.util.Map.Entry<K, Byte> {
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

	public interface FastEntrySet<K> extends ObjectSet<Reference2ByteMap.Entry<K>> {
		ObjectIterator<Reference2ByteMap.Entry<K>> fastIterator();

		default void fastForEach(Consumer<? super Reference2ByteMap.Entry<K>> consumer) {
			this.forEach(consumer);
		}
	}
}
