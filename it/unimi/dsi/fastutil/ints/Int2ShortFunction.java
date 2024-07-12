package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Int2ShortFunction extends Function<Integer, Short>, IntUnaryOperator {
	default int applyAsInt(int operand) {
		return this.get(operand);
	}

	default short put(int key, short value) {
		throw new UnsupportedOperationException();
	}

	short get(int integer);

	default short remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Short put(Integer key, Short value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		short v = this.put(k, value.shortValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Short get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			short v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Short remove(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(int key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Integer)key).intValue());
	}

	default void defaultReturnValue(short rv) {
		throw new UnsupportedOperationException();
	}

	default short defaultReturnValue() {
		return 0;
	}
}
