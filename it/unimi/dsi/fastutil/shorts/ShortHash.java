package it.unimi.dsi.fastutil.shorts;

public interface ShortHash {
	public interface Strategy {
		int hashCode(short short1);

		boolean equals(short short1, short short2);
	}
}
