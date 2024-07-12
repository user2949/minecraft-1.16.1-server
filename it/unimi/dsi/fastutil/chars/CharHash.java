package it.unimi.dsi.fastutil.chars;

public interface CharHash {
	public interface Strategy {
		int hashCode(char character);

		boolean equals(char character1, char character2);
	}
}
