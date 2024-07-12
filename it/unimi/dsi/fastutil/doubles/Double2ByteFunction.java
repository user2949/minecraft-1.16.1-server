package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Double2ByteFunction extends Function<Double, Byte>, DoubleToIntFunction {
	default int applyAsInt(double operand) {
		return this.get(operand);
	}

	default byte put(double key, byte value) {
		throw new UnsupportedOperationException();
	}

	byte get(double double1);

	default byte remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Byte put(Double key, Byte value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		byte v = this.put(k, value.byteValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Byte get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			byte v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Byte remove(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(double key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Double)key).doubleValue());
	}

	default void defaultReturnValue(byte rv) {
		throw new UnsupportedOperationException();
	}

	default byte defaultReturnValue() {
		return 0;
	}
}
