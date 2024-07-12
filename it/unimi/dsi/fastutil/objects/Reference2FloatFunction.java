package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface Reference2FloatFunction<K> extends Function<K, Float>, ToDoubleFunction<K> {
	default double applyAsDouble(K operand) {
		return (double)this.getFloat(operand);
	}

	default float put(K key, float value) {
		throw new UnsupportedOperationException();
	}

	float getFloat(Object object);

	default float removeFloat(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(K key, Float value) {
		boolean containsKey = this.containsKey(key);
		float v = this.put(key, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		float v = this.getFloat(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Float remove(Object key) {
		return this.containsKey(key) ? this.removeFloat(key) : null;
	}

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
