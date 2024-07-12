package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2FloatMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap.FastEntrySet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Byte2FloatSortedMap extends Byte2FloatMap, SortedMap<Byte, Float> {
	Byte2FloatSortedMap subMap(byte byte1, byte byte2);

	Byte2FloatSortedMap headMap(byte byte1);

	Byte2FloatSortedMap tailMap(byte byte1);

	byte firstByteKey();

	byte lastByteKey();

	@Deprecated
	default Byte2FloatSortedMap subMap(Byte from, Byte to) {
		return this.subMap(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default Byte2FloatSortedMap headMap(Byte to) {
		return this.headMap(to.byteValue());
	}

	@Deprecated
	default Byte2FloatSortedMap tailMap(Byte from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Byte, Float>> entrySet() {
		return this.byte2FloatEntrySet();
	}

	ObjectSortedSet<Entry> byte2FloatEntrySet();

	ByteSortedSet keySet();

	@Override
	FloatCollection values();

	ByteComparator comparator();

	public interface FastSortedEntrySet extends ObjectSortedSet<Entry>, FastEntrySet {
		ObjectBidirectionalIterator<Entry> fastIterator();

		ObjectBidirectionalIterator<Entry> fastIterator(Entry entry);
	}
}
