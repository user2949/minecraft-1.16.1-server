package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntPredicate;

@FunctionalInterface
public interface Byte2BooleanFunction extends Function<Byte, Boolean>, IntPredicate {
	@Deprecated
	default boolean test(int operand) {
		return this.get(SafeMath.safeIntToByte(operand));
	}

	default boolean put(byte key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean get(byte byte1);

	default boolean remove(byte key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(Byte key, Boolean value) {
		byte k = key;
		boolean containsKey = this.containsKey(k);
		boolean v = this.put(k, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			boolean v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Boolean remove(Object key) {
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

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
