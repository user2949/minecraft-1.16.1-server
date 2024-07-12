package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Short2ByteFunction extends Function<Short, Byte>, IntUnaryOperator {
	@Deprecated
	default int applyAsInt(int operand) {
		return this.get(SafeMath.safeIntToShort(operand));
	}

	default byte put(short key, byte value) {
		throw new UnsupportedOperationException();
	}

	byte get(short short1);

	default byte remove(short key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Byte put(Short key, Byte value) {
		short k = key;
		boolean containsKey = this.containsKey(k);
		byte v = this.put(k, value.byteValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Byte get(Object key) {
		if (key == null) {
			return null;
		} else {
			short k = (Short)key;
			byte v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Byte remove(Object key) {
		if (key == null) {
			return null;
		} else {
			short k = (Short)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(short key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Short)key).shortValue());
	}

	default void defaultReturnValue(byte rv) {
		throw new UnsupportedOperationException();
	}

	default byte defaultReturnValue() {
		return 0;
	}
}
