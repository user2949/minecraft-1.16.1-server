package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Double2ShortFunction extends Function<Double, Short>, DoubleToIntFunction {
	default int applyAsInt(double operand) {
		return this.get(operand);
	}

	default short put(double key, short value) {
		throw new UnsupportedOperationException();
	}

	short get(double double1);

	default short remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Short put(Double key, Short value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		short v = this.put(k, value.shortValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Short get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			short v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Short remove(Object key) {
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

	default void defaultReturnValue(short rv) {
		throw new UnsupportedOperationException();
	}

	default short defaultReturnValue() {
		return 0;
	}
}
