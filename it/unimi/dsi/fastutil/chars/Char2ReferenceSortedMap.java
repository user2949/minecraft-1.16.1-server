package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.SortedMap;

public interface Char2ReferenceSortedMap<V> extends Char2ReferenceMap<V>, SortedMap<Character, V> {
	Char2ReferenceSortedMap<V> subMap(char character1, char character2);

	Char2ReferenceSortedMap<V> headMap(char character);

	Char2ReferenceSortedMap<V> tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2ReferenceSortedMap<V> subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2ReferenceSortedMap<V> headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2ReferenceSortedMap<V> tailMap(Character from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Character, V>> entrySet() {
		return this.char2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> char2ReferenceEntrySet();

	CharSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	CharComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
