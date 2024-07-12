package io.netty.util;

public interface ResourceLeakTracker<T> {
	void record();

	void record(Object object);

	boolean close(T object);
}
