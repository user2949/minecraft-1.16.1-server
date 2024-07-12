package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

@FunctionalInterface
public interface Short2DoubleFunction extends Function<Short, Double>, IntToDoubleFunction {
	@Deprecated
	default double applyAsDouble(int operand) {
		return this.get(SafeMath.safeIntToShort(operand));
	}

	default double put(short key, double value) {
		throw new UnsupportedOperationException();
	}

	double get(short short1);

	default double remove(short key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Double put(Short key, Double value) {
		short k = key;
		boolean containsKey = this.containsKey(k);
		double v = this.put(k, value.doubleValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Double get(Object key) {
		if (key == null) {
			return null;
		} else {
			short k = (Short)key;
			double v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Double remove(Object key) {
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

	default void defaultReturnValue(double rv) {
		throw new UnsupportedOperationException();
	}

	default double defaultReturnValue() {
		return 0.0;
	}
}
