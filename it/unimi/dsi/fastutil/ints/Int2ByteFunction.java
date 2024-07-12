package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Int2ByteFunction extends Function<Integer, Byte>, IntUnaryOperator {
	default int applyAsInt(int operand) {
		return this.get(operand);
	}

	default byte put(int key, byte value) {
		throw new UnsupportedOperationException();
	}

	byte get(int integer);

	default byte remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Byte put(Integer key, Byte value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		byte v = this.put(k, value.byteValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Byte get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			byte v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Byte remove(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(int key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Integer)key).intValue());
	}

	default void defaultReturnValue(byte rv) {
		throw new UnsupportedOperationException();
	}

	default byte defaultReturnValue() {
		return 0;
	}
}
