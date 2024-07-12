package it.unimi.dsi.fastutil.chars;

import java.util.SortedSet;

public interface CharSortedSet extends CharSet, SortedSet<Character>, CharBidirectionalIterable {
	CharBidirectionalIterator iterator(char character);

	@Override
	CharBidirectionalIterator iterator();

	CharSortedSet subSet(char character1, char character2);

	CharSortedSet headSet(char character);

	CharSortedSet tailSet(char character);

	CharComparator comparator();

	char firstChar();

	char lastChar();

	@Deprecated
	default CharSortedSet subSet(Character from, Character to) {
		return this.subSet(from.charValue(), to.charValue());
	}

	@Deprecated
	default CharSortedSet headSet(Character to) {
		return this.headSet(to.charValue());
	}

	@Deprecated
	default CharSortedSet tailSet(Character from) {
		return this.tailSet(from.charValue());
	}

	@Deprecated
	default Character first() {
		return this.firstChar();
	}

	@Deprecated
	default Character last() {
		return this.lastChar();
	}
}
