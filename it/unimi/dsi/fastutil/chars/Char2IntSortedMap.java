package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2IntMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2IntMap.FastEntrySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2IntSortedMap extends Char2IntMap, SortedMap<Character, Integer> {
	Char2IntSortedMap subMap(char character1, char character2);

	Char2IntSortedMap headMap(char character);

	Char2IntSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2IntSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2IntSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2IntSortedMap tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, Integer>> entrySet() {
		return this.char2IntEntrySet();
	}

	ObjectSortedSet<Entry> char2IntEntrySet();

	CharSortedSet keySet();

	@Override
	IntCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
