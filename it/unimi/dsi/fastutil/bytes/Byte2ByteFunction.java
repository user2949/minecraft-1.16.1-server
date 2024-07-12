package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Byte2ByteFunction extends Function<Byte, Byte>, IntUnaryOperator {
	@Deprecated
	default int applyAsInt(int operand) {
		return this.get(SafeMath.safeIntToByte(operand));
	}

	default byte put(byte key, byte value) {
		throw new UnsupportedOperationException();
	}

	byte get(byte byte1);

	default byte remove(byte key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Byte put(Byte key, Byte value) {
		byte k = key;
		boolean containsKey = this.containsKey(k);
		byte v = this.put(k, value.byteValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Byte get(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			byte v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Byte remove(Object key) {
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

	default void defaultReturnValue(byte rv) {
		throw new UnsupportedOperationException();
	}

	default byte defaultReturnValue() {
		return 0;
	}
}
