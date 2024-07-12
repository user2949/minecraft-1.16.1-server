package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Float2ByteFunction extends Function<Float, Byte>, DoubleToIntFunction {
	@Deprecated
	default int applyAsInt(double operand) {
		return this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default byte put(float key, byte value) {
		throw new UnsupportedOperationException();
	}

	byte get(float float1);

	default byte remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Byte put(Float key, Byte value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		byte v = this.put(k, value.byteValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Byte get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			byte v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Byte remove(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(float key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Float)key).floatValue());
	}

	default void defaultReturnValue(byte rv) {
		throw new UnsupportedOperationException();
	}

	default byte defaultReturnValue() {
		return 0;
	}
}
