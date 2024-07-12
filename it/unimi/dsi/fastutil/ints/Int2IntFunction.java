package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Int2IntFunction extends Function<Integer, Integer>, IntUnaryOperator {
	default int applyAsInt(int operand) {
		return this.get(operand);
	}

	default int put(int key, int value) {
		throw new UnsupportedOperationException();
	}

	int get(int integer);

	default int remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Integer put(Integer key, Integer value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		int v = this.put(k, value.intValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Integer get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			int v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Integer remove(Object key) {
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

	default void defaultReturnValue(int rv) {
		throw new UnsupportedOperationException();
	}

	default int defaultReturnValue() {
		return 0;
	}
}
