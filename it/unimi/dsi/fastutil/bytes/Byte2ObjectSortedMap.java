package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap.Entry;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap.FastEntrySet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Byte2ObjectSortedMap<V> extends Byte2ObjectMap<V>, SortedMap<Byte, V> {
	Byte2ObjectSortedMap<V> subMap(byte byte1, byte byte2);

	Byte2ObjectSortedMap<V> headMap(byte byte1);

	Byte2ObjectSortedMap<V> tailMap(byte byte1);

	byte firstByteKey();

	byte lastByteKey();

	@Deprecated
	default Byte2ObjectSortedMap<V> subMap(Byte from, Byte to) {
		return this.subMap(from.byteValue(), to.byteValue());
	}

	@Deprecated
	default Byte2ObjectSortedMap<V> headMap(Byte to) {
		return this.headMap(to.byteValue());
	}

	@Deprecated
	default Byte2ObjectSortedMap<V> tailMap(Byte from) {
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
		return this.byte2ObjectEntrySet();
	}

	ObjectSortedSet<Entry<V>> byte2ObjectEntrySet();

	ByteSortedSet keySet();

	@Override
	ObjectCollection<V> values();

	ByteComparator comparator();

	public interface FastSortedEntrySet<V> extends ObjectSortedSet<Entry<V>>, FastEntrySet<V> {
		ObjectBidirectionalIterator<Entry<V>> fastIterator();

		ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> entry);
	}
}
