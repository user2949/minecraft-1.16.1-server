package it.unimi.dsi.fastutil.ints;

public interface IntHash {
	public interface Strategy {
		int hashCode(int integer);

		boolean equals(int integer1, int integer2);
	}
}
