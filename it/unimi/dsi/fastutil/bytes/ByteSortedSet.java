package it.unimi.dsi.fastutil.bytes;

import java.util.SortedSet;

public interface ByteSortedSet extends ByteSet, SortedSet<Byte>, ByteBidirectionalIterable {
	ByteBidirectionalIterator iterator(byte byte1);

	@Override
	ByteBidirectionalIterator iterator();

	ByteSortedSet subSet(byte byte1, byte byte2);

	ByteSortedSet headSet(byte byte1);

	ByteSortedSet tailSet(byte byte1);

	ByteComparator comparator();

	byte firstByte();

	byte lastByte();

	@Deprecated
	default ByteSortedSet subSet(Byte from, Byte to) {
		return this.subSet(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default ByteSortedSet headSet(Byte to) {
		return this.headSet(to.byteValue());
	}

	@Deprecated
	default ByteSortedSet tailSet(Byte from) {
		return this.tailSet(from.byteValue());
	}

	@Deprecated
	default Byte first() {
		return this.firstByte();
	}

	@Deprecated
	default Byte last() {
		return this.lastByte();
	}
}
