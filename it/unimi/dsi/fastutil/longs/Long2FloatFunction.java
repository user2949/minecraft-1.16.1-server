package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToDoubleFunction;

@FunctionalInterface
public interface Long2FloatFunction extends Function<Long, Float>, LongToDoubleFunction {
	default double applyAsDouble(long operand) {
		return (double)this.get(operand);
	}

	default float put(long key, float value) {
		throw new UnsupportedOperationException();
	}

	float get(long long1);

	default float remove(long key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Float put(Long key, Float value) {
		long k = key;
		boolean containsKey = this.containsKey(k);
		float v = this.put(k, value.floatValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Float get(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			float v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Float remove(Object key) {
		if (key == null) {
			return null;
		} else {
			long k = (Long)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(long key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Long)key).longValue());
	}

	default void defaultReturnValue(float rv) {
		throw new UnsupportedOperationException();
	}

	default float defaultReturnValue() {
		return 0.0F;
	}
}
