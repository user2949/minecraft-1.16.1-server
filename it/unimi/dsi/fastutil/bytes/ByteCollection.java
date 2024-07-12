package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Collection;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public interface ByteCollection extends Collection<Byte>, ByteIterable {
	@Override
	ByteIterator iterator();

	boolean add(byte byte1);

	boolean contains(byte byte1);

	boolean rem(byte byte1);

	@Deprecated
	default boolean add(Byte key) {
		return this.add(key.byteValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Byte)key).byteValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Byte)key);
	}

	byte[] toByteArray();

	@Deprecated
	byte[] toByteArray(byte[] arr);

	byte[] toArray(byte[] arr);

	boolean addAll(ByteCollection byteCollection);

	boolean containsAll(ByteCollection byteCollection);

	boolean removeAll(ByteCollection byteCollection);

	@Deprecated
	default boolean removeIf(Predicate<? super Byte> filter) {
		return this.removeIf((IntPredicate)(key -> filter.test(SafeMath.safeIntToByte(key))));
	}

	default boolean removeIf(IntPredicate filter) {
		boolean removed = false;
		ByteIterator each = this.iterator();

		while (each.hasNext()) {
			if (filter.test(each.nextByte())) {
				each.remove();
				removed = true;
			}
		}

		return removed;
	}

	boolean retainAll(ByteCollection byteCollection);
}
