package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2CharMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2CharSortedMap extends Char2CharMap, SortedMap<Character, Character> {
	Char2CharSortedMap subMap(char character1, char character2);

	Char2CharSortedMap headMap(char character);

	Char2CharSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2CharSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2CharSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2CharSortedMap tailMap(Character from) {
		return this.tailMap(from.charValue());
	}

	@Deprecated
	default Character firstKey() {
		return this.firstCharKey();
	}

	@Deprecated
	default Character lastKey() {
		return this.lastCharKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Character, Character>> entrySet() {
		return this.char2CharEntrySet();
	}

	ObjectSortedSet<Entry> char2CharEntrySet();

	CharSortedSet keySet();

	@Override
	CharCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
