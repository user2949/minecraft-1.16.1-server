package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2LongMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2LongMap.FastEntrySet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2LongSortedMap extends Char2LongMap, SortedMap<Character, Long> {
	Char2LongSortedMap subMap(char character1, char character2);

	Char2LongSortedMap headMap(char character);

	Char2LongSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2LongSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2LongSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2LongSortedMap tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, Long>> entrySet() {
		return this.char2LongEntrySet();
	}

	ObjectSortedSet<Entry> char2LongEntrySet();

	CharSortedSet keySet();

	@Override
	LongCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
