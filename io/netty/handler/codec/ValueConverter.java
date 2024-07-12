package io.netty.handler.codec;

public interface ValueConverter<T> {
	T convertObject(Object object);

	T convertBoolean(boolean boolean1);

	boolean convertToBoolean(T object);

	T convertByte(byte byte1);

	byte convertToByte(T object);

	T convertChar(char character);

	char convertToChar(T object);

	T convertShort(short short1);

	short convertToShort(T object);

	T convertInt(int integer);

	int convertToInt(T object);

	T convertLong(long long1);

	long convertToLong(T object);

	T convertTimeMillis(long long1);

	long convertToTimeMillis(T object);

	T convertFloat(float float1);

	float convertToFloat(T object);

	T convertDouble(double double1);

	double convertToDouble(T object);
}
