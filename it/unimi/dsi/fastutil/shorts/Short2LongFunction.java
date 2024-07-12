package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToLongFunction;

@FunctionalInterface
public interface Short2LongFunction extends Function<Short, Long>, IntToLongFunction {
	@Deprecated
	default long applyAsLong(int operand) {
		return this.get(SafeMath.safeIntToShort(operand));
	}

	default long put(short key, long value) {
		throw new UnsupportedOperationException();
	}

	long get(short short1);

	default long remove(short key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Long put(Short key, Long value) {
		short k = key;
		boolean containsKey = this.containsKey(k);
		long v = this.put(k, value.longValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Long get(Object key) {
		if (key == null) {
			return null;
		} else {
			short k = (Short)key;
			long v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Long remove(Object key) {
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

	default void defaultReturnValue(long rv) {
		throw new UnsupportedOperationException();
	}

	default long defaultReturnValue() {
		return 0L;
	}
}
