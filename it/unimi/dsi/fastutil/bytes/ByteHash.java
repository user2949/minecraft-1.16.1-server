package it.unimi.dsi.fastutil.bytes;

public interface ByteHash {
	public interface Strategy {
		int hashCode(byte byte1);

		boolean equals(byte byte1, byte byte2);
	}
}
