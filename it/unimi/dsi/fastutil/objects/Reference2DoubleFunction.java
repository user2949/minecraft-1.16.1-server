package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface Reference2DoubleFunction<K> extends Function<K, Double>, ToDoubleFunction<K> {
	default double applyAsDouble(K operand) {
		return this.getDouble(operand);
	}

	default double put(K key, double value) {
		throw new UnsupportedOperationException();
	}

	double getDouble(Object object);

	default double removeDouble(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Double put(K key, Double value) {
		boolean containsKey = this.containsKey(key);
		double v = this.put(key, value.doubleValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Double get(Object key) {
		double v = this.getDouble(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Double remove(Object key) {
		return this.containsKey(key) ? this.removeDouble(key) : null;
	}

	default void defaultReturnValue(double rv) {
		throw new UnsupportedOperationException();
	}

	default double defaultReturnValue() {
		return 0.0;
	}
}
