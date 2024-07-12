package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoublePredicate;

@FunctionalInterface
public interface Double2BooleanFunction extends Function<Double, Boolean>, DoublePredicate {
	default boolean test(double operand) {
		return this.get(operand);
	}

	default boolean put(double key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean get(double double1);

	default boolean remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(Double key, Boolean value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		boolean v = this.put(k, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			boolean v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Boolean remove(Object key) {
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

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
