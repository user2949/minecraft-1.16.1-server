package it.unimi.dsi.fastutil.doubles;

public interface DoubleHash {
	public interface Strategy {
		int hashCode(double double1);

		boolean equals(double double1, double double2);
	}
}
