package io.netty.handler.codec.http.cookie;

public interface Cookie extends Comparable<Cookie> {
	long UNDEFINED_MAX_AGE = Long.MIN_VALUE;

	String name();

	String value();

	void setValue(String string);

	boolean wrap();

	void setWrap(boolean boolean1);

	String domain();

	void setDomain(String string);

	String path();

	void setPath(String string);

	long maxAge();

	void setMaxAge(long long1);

	boolean isSecure();

	void setSecure(boolean boolean1);

	boolean isHttpOnly();

	void setHttpOnly(boolean boolean1);
}
