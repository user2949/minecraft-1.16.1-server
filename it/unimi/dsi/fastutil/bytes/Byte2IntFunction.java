package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Byte2IntFunction extends Function<Byte, Integer>, IntUnaryOperator {
	@Deprecated
	default int applyAsInt(int operand) {
		return this.get(SafeMath.safeIntToByte(operand));
	}

	default int put(byte key, int value) {
		throw new UnsupportedOperationException();
	}

	int get(byte byte1);

	default int remove(byte key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Integer put(Byte key, Integer value) {
		byte k = key;
		boolean containsKey = this.containsKey(k);
		int v = this.put(k, value.intValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Integer get(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			int v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Integer remove(Object key) {
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

	default void defaultReturnValue(int rv) {
		throw new UnsupportedOperationException();
	}

	default int defaultReturnValue() {
		return 0;
	}
}
