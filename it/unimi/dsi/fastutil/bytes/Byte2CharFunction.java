package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Byte2CharFunction extends Function<Byte, Character>, IntUnaryOperator {
	@Deprecated
	default int applyAsInt(int operand) {
		return this.get(SafeMath.safeIntToByte(operand));
	}

	default char put(byte key, char value) {
		throw new UnsupportedOperationException();
	}

	char get(byte byte1);

	default char remove(byte key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Character put(Byte key, Character value) {
		byte k = key;
		boolean containsKey = this.containsKey(k);
		char v = this.put(k, value.charValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Character get(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			char v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Character remove(Object key) {
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

	default void defaultReturnValue(char rv) {
		throw new UnsupportedOperationException();
	}

	default char defaultReturnValue() {
		return '\u0000';
	}
}
