package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToIntFunction;

@FunctionalInterface
public interface Double2CharFunction extends Function<Double, Character>, DoubleToIntFunction {
	default int applyAsInt(double operand) {
		return this.get(operand);
	}

	default char put(double key, char value) {
		throw new UnsupportedOperationException();
	}

	char get(double double1);

	default char remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Character put(Double key, Character value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		char v = this.put(k, value.charValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Character get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			char v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Character remove(Object key) {
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

	default void defaultReturnValue(char rv) {
		throw new UnsupportedOperationException();
	}

	default char defaultReturnValue() {
		return '\u0000';
	}
}
