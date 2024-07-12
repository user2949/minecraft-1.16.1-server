package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntPredicate;

@FunctionalInterface
public interface Int2BooleanFunction extends Function<Integer, Boolean>, IntPredicate {
	default boolean test(int operand) {
		return this.get(operand);
	}

	default boolean put(int key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean get(int integer);

	default boolean remove(int key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(Integer key, Boolean value) {
		int k = key;
		boolean containsKey = this.containsKey(k);
		boolean v = this.put(k, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		if (key == null) {
			return null;
		} else {
			int k = (Integer)key;
			boolean v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Boolean remove(Object key) {
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

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
