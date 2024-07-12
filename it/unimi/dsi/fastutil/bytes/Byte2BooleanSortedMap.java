package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Byte2BooleanSortedMap extends Byte2BooleanMap, SortedMap<Byte, Boolean> {
	Byte2BooleanSortedMap subMap(byte byte1, byte byte2);

	Byte2BooleanSortedMap headMap(byte byte1);

	Byte2BooleanSortedMap tailMap(byte byte1);

	byte firstByteKey();

	byte lastByteKey();

	@Deprecated
	default Byte2BooleanSortedMap subMap(Byte from, Byte to) {
		return this.subMap(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default Byte2BooleanSortedMap headMap(Byte to) {
		return this.headMap(to.byteValue());
	}

	@Deprecated
	default Byte2BooleanSortedMap tailMap(Byte from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Byte, Boolean>> entrySet() {
		return this.byte2BooleanEntrySet();
	}

	ObjectSortedSet<Entry> byte2BooleanEntrySet();

	ByteSortedSet keySet();

	@Override
	BooleanCollection values();

	ByteComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
