package it.unimi.dsi.fastutil.floats;

public interface FloatHash {
	public interface Strategy {
		int hashCode(float float1);

		boolean equals(float float1, float float2);
	}
}
