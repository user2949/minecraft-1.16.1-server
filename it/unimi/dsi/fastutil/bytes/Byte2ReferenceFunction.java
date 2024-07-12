package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Byte2ReferenceFunction<V> extends Function<Byte, V>, IntFunction<V> {
	@Deprecated
	default V apply(int operand) {
		return this.get(SafeMath.safeIntToByte(operand));
	}

	default V put(byte key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(byte byte1);

	default V remove(byte key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default V put(Byte key, V value) {
		byte k = key;
		boolean containsKey = this.containsKey(k);
		V v = this.put(k, value);
		return containsKey ? v : null;
	}

	@Deprecated
	@Override
	default V get(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			V v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	@Override
	default V remove(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(byte key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Byte)key).byteValue());
	}

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
