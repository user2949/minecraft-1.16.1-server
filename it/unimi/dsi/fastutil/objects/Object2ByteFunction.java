package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Object2ByteFunction<K> extends Function<K, Byte>, ToIntFunction<K> {
	default int applyAsInt(K operand) {
		return this.getByte(operand);
	}

	default byte put(K key, byte value) {
		throw new UnsupportedOperationException();
	}

	byte getByte(Object object);

	default byte removeByte(Object key) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	default Byte put(K key, Byte value) {
		boolean containsKey = this.containsKey(key);
		byte v = this.put(key, value.byteValue());
		return containsKey ? v : null;
	}

	@Deprecated
	default Byte get(Object key) {
		byte v = this.getByte(key);
		return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
	}

	@Deprecated
	default Byte remove(Object key) {
		return this.containsKey(key) ? this.removeByte(key) : null;
	}

	default void defaultReturnValue(byte rv) {
		throw new UnsupportedOperationException();
	}

	default byte defaultReturnValue() {
		return 0;
	}
}
