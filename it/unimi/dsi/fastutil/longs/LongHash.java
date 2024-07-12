package it.unimi.dsi.fastutil.longs;

public interface LongHash {
	public interface Strategy {
		int hashCode(long long1);

		boolean equals(long long1, long long2);
	}
}
