package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleFunction;

@FunctionalInterface
public interface Double2ObjectFunction<V> extends Function<Double, V>, DoubleFunction<V> {
	default V apply(double operand) {
		return this.get(operand);
	}

	default V put(double key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(double double1);

	default V remove(double key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default V put(Double key, V value) {
		double k = key;
		boolean containsKey = this.containsKey(k);
		V v = this.put(k, value);
		return containsKey ? v : null;
	}

	@Deprecated
	@Override
	default V get(Object key) {
		if (key == null) {
			return null;
		} else {
			double k = (Double)key;
			V v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	@Override
	default V remove(Object key) {
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

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
