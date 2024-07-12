package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Object2BooleanFunction<K> extends Function<K, Boolean>, Predicate<K> {
	default boolean test(K operand) {
		return this.getBoolean(operand);
	}

	default boolean put(K key, boolean value) {
		throw new UnsupportedOperationException();
	}

	boolean getBoolean(Object object);

	default boolean removeBoolean(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Boolean put(K key, Boolean value) {
		boolean containsKey = this.containsKey(key);
		boolean v = this.put(key, value.booleanValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Boolean get(Object key) {
		boolean v = this.getBoolean(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Boolean remove(Object key) {
		return this.containsKey(key) ? this.removeBoolean(key) : null;
	}

	default void defaultReturnValue(boolean rv) {
		throw new UnsupportedOperationException();
	}

	default boolean defaultReturnValue() {
		return false;
	}
}
