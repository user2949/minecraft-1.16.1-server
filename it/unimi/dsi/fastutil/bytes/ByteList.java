package it.unimi.dsi.fastutil.bytes;

import java.util.List;

public interface ByteList extends List<Byte>, Comparable<List<? extends Byte>>, ByteCollection {
	ByteListIterator iterator();

	ByteListIterator listIterator();

	ByteListIterator listIterator(int integer);

	ByteList subList(int integer1, int integer2);

	void size(int integer);

	void getElements(int integer1, byte[] arr, int integer3, int integer4);

	void removeElements(int integer1, int integer2);

	void addElements(int integer, byte[] arr);

	void addElements(int integer1, byte[] arr, int integer3, int integer4);

	@Override
	boolean add(byte byte1);

	void add(int integer, byte byte2);

	@Deprecated
	default void add(int index, Byte key) {
		this.add(index, key.byteValue());
	}

	boolean addAll(int integer, ByteCollection byteCollection);

	boolean addAll(int integer, ByteList byteList);

	boolean addAll(ByteList byteList);

	byte set(int integer, byte byte2);

	byte getByte(int integer);

	int indexOf(byte byte1);

	int lastIndexOf(byte byte1);

	@Deprecated
	@Override
	default boolean contains(Object key) {
		return ByteCollection.super.contains(key);
	}

	@Deprecated
	default Byte get(int index) {
		return this.getByte(index);
	}

	@Deprecated
	default int indexOf(Object o) {
		return this.indexOf(((Byte)o).byteValue());
	}

	@Deprecated
	default int lastIndexOf(Object o) {
		return this.lastIndexOf(((Byte)o).byteValue());
	}

	@Deprecated
	@Override
	default boolean add(Byte k) {
		return this.add(k.byteValue());
	}

	byte removeByte(int integer);

	@Deprecated
	@Override
	default boolean remove(Object key) {
		return ByteCollection.super.remove(key);
	}

	@Deprecated
	default Byte remove(int index) {
		return this.removeByte(index);
	}

	@Deprecated
	default Byte set(int index, Byte k) {
		return this.set(index, k.byteValue());
	}
}
