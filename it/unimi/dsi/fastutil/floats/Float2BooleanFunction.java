package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoublePredicate;

@FunctionalInterface
public interface Float2BooleanFunction extends Function<Float, Boolean>, DoublePredicate {
	@Deprecated
	default boolean test(double operand) {
		return this.get(SafeMath.safeDoubleToFloat(operand));
	}

	default boolean put(float key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean get(float float1);

	default boolean remove(float key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(Float key, Boolean value) {
		float k = key;
		boolean containsKey = this.containsKey(k);
		boolean v = this.put(k, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			boolean v = this.get(k);
			return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
		}
	}

	@Deprecated
	default Boolean remove(Object key) {
		if (key == null) {
			return null;
		} else {
			float k = (Float)key;
			return this.containsKey(k) ? this.remove(k) : null;
		}
	}

	default boolean containsKey(float key) {
		return true;
	}

	@Deprecated
	@Override
	default boolean containsKey(Object key) {
		return key == null ? false : this.containsKey(((Float)key).floatValue());
	}

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
