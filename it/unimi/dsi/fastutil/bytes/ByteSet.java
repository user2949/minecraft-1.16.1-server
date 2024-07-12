package it.unimi.dsi.fastutil.bytes;

import java.util.Set;

public interface ByteSet extends ByteCollection, Set<Byte> {
	@Override
	ByteIterator iterator();

	boolean remove(byte byte1);

	@Deprecated
	@Override
	default boolean remove(Object o) {
		return ByteCollection.super.remove(o);
	}

	@Deprecated
	@Override
	default boolean add(Byte o) {
		return ByteCollection.super.add(o);
	}

	@Deprecated
	@Override
	default boolean contains(Object o) {
		return ByteCollection.super.contains(o);
	}

	@Deprecated
	@Override
	default boolean rem(byte k) {
		return this.remove(k);
	}
}
