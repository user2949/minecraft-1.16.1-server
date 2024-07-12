package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.Size64;

public interface ByteBigList extends BigList<Byte>, ByteCollection, Size64, Comparable<BigList<? extends Byte>> {
	ByteBigListIterator iterator();

	ByteBigListIterator listIterator();

	ByteBigListIterator listIterator(long long1);

	ByteBigList subList(long long1, long long2);

	void getElements(long long1, byte[][] arr, long long3, long long4);

	void removeElements(long long1, long long2);

	void addElements(long long1, byte[][] arr);

	void addElements(long long1, byte[][] arr, long long3, long long4);

	void add(long long1, byte byte2);

	boolean addAll(long long1, ByteCollection byteCollection);

	boolean addAll(long long1, ByteBigList byteBigList);

	boolean addAll(ByteBigList byteBigList);

	byte getByte(long long1);

	byte removeByte(long long1);

	byte set(long long1, byte byte2);

	long indexOf(byte byte1);

	long lastIndexOf(byte byte1);

	@Deprecated
	void add(long long1, Byte byte2);

	@Deprecated
	Byte get(long long1);

	@Deprecated
	@Override
	long indexOf(Object object);

	@Deprecated
	@Override
	long lastIndexOf(Object object);

	@Deprecated
	Byte remove(long long1);

	@Deprecated
	Byte set(long long1, Byte byte2);
}
