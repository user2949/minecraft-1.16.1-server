package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Short2ReferenceFunction<V> extends Function<Short, V>, IntFunction<V> {
	@Deprecated
	default V apply(int operand) {
		return this.get(SafeMath.safeIntToShort(operand));
	}

	default V put(short key, V value) {
		throw new UnsupportedOperationException();
	}

	V get(short short1);

	default V remove(short key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default V put(Short key, V value) {
		short k = key;
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
			short k = (Short)key;
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

	default void defaultReturnValue(V rv) {
		throw new UnsupportedOperationException();
	}

	default V defaultReturnValue() {
		return null;
	}
}
