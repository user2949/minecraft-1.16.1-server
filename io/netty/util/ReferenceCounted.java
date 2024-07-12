package io.netty.util;

public interface ReferenceCounted {
	int refCnt();

	ReferenceCounted retain();

	ReferenceCounted retain(int integer);

	ReferenceCounted touch();

	ReferenceCounted touch(Object object);

	boolean release();

	boolean release(int integer);
}
