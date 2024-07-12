package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2FloatMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2FloatSortedMap extends Char2FloatMap, SortedMap<Character, Float> {
	Char2FloatSortedMap subMap(char character1, char character2);

	Char2FloatSortedMap headMap(char character);

	Char2FloatSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2FloatSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2FloatSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2FloatSortedMap tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, Float>> entrySet() {
		return this.char2FloatEntrySet();
	}

	ObjectSortedSet<Entry> char2FloatEntrySet();

	CharSortedSet keySet();

	@Override
	FloatCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
