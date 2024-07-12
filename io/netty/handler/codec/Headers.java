package io.netty.handler.codec;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public interface Headers<K, V, T extends Headers<K, V, T>> extends Iterable<Entry<K, V>> {
	V get(K object);

	V get(K object1, V object2);

	V getAndRemove(K object);

	V getAndRemove(K object1, V object2);

	List<V> getAll(K object);

	List<V> getAllAndRemove(K object);

	Boolean getBoolean(K object);

	boolean getBoolean(K object, boolean boolean2);

	Byte getByte(K object);

	byte getByte(K object, byte byte2);

	Character getChar(K object);

	char getChar(K object, char character);

	Short getShort(K object);

	short getShort(K object, short short2);

	Integer getInt(K object);

	int getInt(K object, int integer);

	Long getLong(K object);

	long getLong(K object, long long2);

	Float getFloat(K object);

	float getFloat(K object, float float2);

	Double getDouble(K object);

	double getDouble(K object, double double2);

	Long getTimeMillis(K object);

	long getTimeMillis(K object, long long2);

	Boolean getBooleanAndRemove(K object);

	boolean getBooleanAndRemove(K object, boolean boolean2);

	Byte getByteAndRemove(K object);

	byte getByteAndRemove(K object, byte byte2);

	Character getCharAndRemove(K object);

	char getCharAndRemove(K object, char character);

	Short getShortAndRemove(K object);

	short getShortAndRemove(K object, short short2);

	Integer getIntAndRemove(K object);

	int getIntAndRemove(K object, int integer);

	Long getLongAndRemove(K object);

	long getLongAndRemove(K object, long long2);

	Float getFloatAndRemove(K object);

	float getFloatAndRemove(K object, float float2);

	Double getDoubleAndRemove(K object);

	double getDoubleAndRemove(K object, double double2);

	Long getTimeMillisAndRemove(K object);

	long getTimeMillisAndRemove(K object, long long2);

	boolean contains(K object);

	boolean contains(K object1, V object2);

	boolean containsObject(K object1, Object object2);

	boolean containsBoolean(K object, boolean boolean2);

	boolean containsByte(K object, byte byte2);

	boolean containsChar(K object, char character);

	boolean containsShort(K object, short short2);

	boolean containsInt(K object, int integer);

	boolean containsLong(K object, long long2);

	boolean containsFloat(K object, float float2);

	boolean containsDouble(K object, double double2);

	boolean containsTimeMillis(K object, long long2);

	int size();

	boolean isEmpty();

	Set<K> names();

	T add(K object1, V object2);

	T add(K object, Iterable<? extends V> iterable);

	T add(K object, V... arr);

	T addObject(K object1, Object object2);

	T addObject(K object, Iterable<?> iterable);

	T addObject(K object, Object... arr);

	T addBoolean(K object, boolean boolean2);

	T addByte(K object, byte byte2);

	T addChar(K object, char character);

	T addShort(K object, short short2);

	T addInt(K object, int integer);

	T addLong(K object, long long2);

	T addFloat(K object, float float2);

	T addDouble(K object, double double2);

	T addTimeMillis(K object, long long2);

	T add(Headers<? extends K, ? extends V, ?> headers);

	T set(K object1, V object2);

	T set(K object, Iterable<? extends V> iterable);

	T set(K object, V... arr);

	T setObject(K object1, Object object2);

	T setObject(K object, Iterable<?> iterable);

	T setObject(K object, Object... arr);

	T setBoolean(K object, boolean boolean2);

	T setByte(K object, byte byte2);

	T setChar(K object, char character);

	T setShort(K object, short short2);

	T setInt(K object, int integer);

	T setLong(K object, long long2);

	T setFloat(K object, float float2);

	T setDouble(K object, double double2);

	T setTimeMillis(K object, long long2);

	T set(Headers<? extends K, ? extends V, ?> headers);

	T setAll(Headers<? extends K, ? extends V, ?> headers);

	boolean remove(K object);

	T clear();

	Iterator<Entry<K, V>> iterator();
}
