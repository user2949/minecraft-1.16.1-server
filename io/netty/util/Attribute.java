package io.netty.util;

public interface Attribute<T> {
	AttributeKey<T> key();

	T get();

	void set(T object);

	T getAndSet(T object);

	T setIfAbsent(T object);

	@Deprecated
	T getAndRemove();

	boolean compareAndSet(T object1, T object2);

	@Deprecated
	void remove();
}
