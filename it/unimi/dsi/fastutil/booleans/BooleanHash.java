package it.unimi.dsi.fastutil.booleans;

public interface BooleanHash {
	public interface Strategy {
		int hashCode(boolean boolean1);

		boolean equals(boolean boolean1, boolean boolean2);
	}
}
