package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Reference2ShortFunction<K> extends Function<K, Short>, ToIntFunction<K> {
	default int applyAsInt(K operand) {
		return this.getShort(operand);
	}

	default short put(K key, short value) {
		throw new UnsupportedOperationException();
	}

	short getShort(Object object);

	default short removeShort(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Short put(K key, Short value) {
		boolean containsKey = this.containsKey(key);
		short v = this.put(key, value.shortValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Short get(Object key) {
		short v = this.getShort(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Short remove(Object key) {
		return this.containsKey(key) ? this.removeShort(key) : null;
	}

	default void defaultReturnValue(short rv) {
		throw new UnsupportedOperationException();
	}

	default short defaultReturnValue() {
		return 0;
	}
}
