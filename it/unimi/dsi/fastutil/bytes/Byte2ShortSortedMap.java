package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ShortMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.SortedMap;

public interface Byte2ShortSortedMap extends Byte2ShortMap, SortedMap<Byte, Short> {
	Byte2ShortSortedMap subMap(byte byte1, byte byte2);

	Byte2ShortSortedMap headMap(byte byte1);

	Byte2ShortSortedMap tailMap(byte byte1);

	byte firstByteKey();

	byte lastByteKey();

	@Deprecated
	default Byte2ShortSortedMap subMap(Byte from, Byte to) {
		return this.subMap(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default Byte2ShortSortedMap headMap(Byte to) {
		return this.headMap(to.byteValue());
	}

	@Deprecated
	default Byte2ShortSortedMap tailMap(Byte from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Byte, Short>> entrySet() {
		return this.byte2ShortEntrySet();
	}

	ObjectSortedSet<Entry> byte2ShortEntrySet();

	ByteSortedSet keySet();

	@Override
	ShortCollection values();

	ByteComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
