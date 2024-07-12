package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.SortedMap;

public interface Byte2ReferenceSortedMap<V> extends Byte2ReferenceMap<V>, SortedMap<Byte, V> {
	Byte2ReferenceSortedMap<V> subMap(byte byte1, byte byte2);

	Byte2ReferenceSortedMap<V> headMap(byte byte1);

	Byte2ReferenceSortedMap<V> tailMap(byte byte1);

	byte firstByteKey();

	byte lastByteKey();

	@Deprecated
	default Byte2ReferenceSortedMap<V> subMap(Byte from, Byte to) {
		return this.subMap(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default Byte2ReferenceSortedMap<V> headMap(Byte to) {
		return this.headMap(to.byteValue());
	}

	@Deprecated
	default Byte2ReferenceSortedMap<V> tailMap(Byte from) {
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
	default ObjectSortedSet<java.util.Map.Entry<Byte, V>> entrySet() {
		return this.byte2ReferenceEntrySet();
	}

	ObjectSortedSet<Entry<V>> byte2ReferenceEntrySet();

	ByteSortedSet keySet();

	@Override
	ReferenceCollection<V> values();

	ByteComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
