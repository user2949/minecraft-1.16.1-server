package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.chars.Char2ByteMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2ByteMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2ByteSortedMap extends Char2ByteMap, SortedMap<Character, Byte> {
	Char2ByteSortedMap subMap(char character1, char character2);

	Char2ByteSortedMap headMap(char character);

	Char2ByteSortedMap tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2ByteSortedMap subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2ByteSortedMap headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2ByteSortedMap tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, Byte>> entrySet() {
		return this.char2ByteEntrySet();
	}

	ObjectSortedSet<Entry> char2ByteEntrySet();

	CharSortedSet keySet();

	@Override
	ByteCollection values();

	CharComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
