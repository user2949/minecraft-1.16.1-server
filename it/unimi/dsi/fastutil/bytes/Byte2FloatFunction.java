package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface Byte2FloatFunction extends Function<Byte, Float>, IntToDoubleFunction {
	@Deprecated
	default double applyAsDouble(int operand) {
		return (double)this.get(SafeMath.safeIntToByte(operand));
	}

	default float put(byte key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(byte byte1);

	default float remove(byte key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Byte key, Float value) {
		byte k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			byte k = (Byte)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
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

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
