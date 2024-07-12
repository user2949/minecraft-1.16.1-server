package it.unimi.dsi.fastutil.bytes;

import java.util.Comparator;

@FunctionalInterface
public interface ByteComparator extends Comparator<Byte> {
	int compare(byte byte1, byte byte2);

	@Deprecated
	default int compare(Byte ok1, Byte ok2) {
		return this.compare(ok1.byteValue(), ok2.byteValue());
	}
}
