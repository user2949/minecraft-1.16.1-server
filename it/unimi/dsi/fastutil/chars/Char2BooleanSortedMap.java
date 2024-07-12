package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2BooleanSortedMap extends Char2BooleanMap, SortedMap<Character, Boolean> {
	Char2BooleanSortedMap subMap(char character1, char character2);

	Char2BooleanSortedMap headMap(char character);

	Char2BooleanSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2BooleanSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2BooleanSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2BooleanSortedMap tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, Boolean>> entrySet() {
		return this.char2BooleanEntrySet();
	}

	ObjectSortedSet<Entry> char2BooleanEntrySet();

	CharSortedSet keySet();

	@Override
	BooleanCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
