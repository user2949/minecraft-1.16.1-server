package it.unimi.dsi.fastutil.chars;

import java.util.Comparator;

@FunctionalInterface
public interface CharComparator extends Comparator<Character> {
	int compare(char character1, char character2);

	@Deprecated
	default int compare(Character ok1, Character ok2) {
		return this.compare(ok1.charValue(), ok2.charValue());
	}
}
