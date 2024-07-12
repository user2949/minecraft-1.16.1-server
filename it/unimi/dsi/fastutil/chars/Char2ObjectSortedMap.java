package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap.Entry;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Char2ObjectSortedMap<V> extends Char2ObjectMap<V>, SortedMap<Character, V> {
	Char2ObjectSortedMap<V> subMap(char character1, char character2);

	Char2ObjectSortedMap<V> headMap(char character);

	Char2ObjectSortedMap<V> tailMap(char character);

	char firstCharKey();

	char lastCharKey();

	@Deprecated
	default Char2ObjectSortedMap<V> subMap(Character from, Character to) {
		return this.subMap(from.charValue(), to.charValue());
	}

	@Deprecated
	default Char2ObjectSortedMap<V> headMap(Character to) {
		return this.headMap(to.charValue());
	}

	@Deprecated
	default Char2ObjectSortedMap<V> tailMap(Character from) {
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
		return this.char2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> char2ObjectEntrySet();

	CharSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	CharComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
