package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2CharMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap.FastEntrySet;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Byte2CharSortedMap extends Byte2CharMap, SortedMap<Byte, Character> {
	Byte2CharSortedMap subMap(byte byte1, byte byte2);

	Byte2CharSortedMap headMap(byte byte1);

	Byte2CharSortedMap tailMap(byte byte1);

	byte firstByteKey();

	byte lastByteKey();

	@Deprecated
	default Byte2CharSortedMap subMap(Byte from, Byte to) {
		return this.subMap(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default Byte2CharSortedMap headMap(Byte to) {
		return this.headMap(to.byteValue());
	}

	@Deprecated
	default Byte2CharSortedMap tailMap(Byte from) {
		return this.tailMap(from.byteValue());
	}

	@Deprecated
	default Byte firstKey() {
		return this.firstByteKey();
	}

	@Deprecated
	default Byte lastKey() {
		return this.lastByteKey();
	}

	@Deprecated
	default ObjectSortedSet<java.util.Map.Entry<Byte, Character>> entrySet() {
		return this.byte2CharEntrySet();
	}

	ObjectSortedSet<Entry> byte2CharEntrySet();

	ByteSortedSet keySet();

	@Override
	CharCollection values();

	ByteComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
