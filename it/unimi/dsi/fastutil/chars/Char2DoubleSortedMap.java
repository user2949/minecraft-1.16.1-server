package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2DoubleMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap.FastEntrySet;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2DoubleSortedMap extends Char2DoubleMap, SortedMap<Character, Double> {
	Char2DoubleSortedMap subMap(char character1, char character2);

	Char2DoubleSortedMap headMap(char character);

	Char2DoubleSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2DoubleSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2DoubleSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2DoubleSortedMap tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, Double>> entrySet() {
		return this.char2DoubleEntrySet();
	}

	ObjectSortedSet<Entry> char2DoubleEntrySet();

	CharSortedSet keySet();

	@Override
	DoubleCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
