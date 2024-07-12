package io.netty.handler.codec.http;

import java.util.Set;

@Deprecated
public interface Cookie extends io.netty.handler.codec.http.cookie.Cookie {
	@Deprecated
	String getName();

	@Deprecated
	String getValue();

	@Deprecated
	String getDomain();

	@Deprecated
	String getPath();

	@Deprecated
	String getComment();

	@Deprecated
	String comment();

	@Deprecated
	void setComment(String string);

	@Deprecated
	long getMaxAge();

	@Deprecated
	@Override
	long maxAge();

	@Deprecated
	@Override
	void setMaxAge(long long1);

	@Deprecated
	int getVersion();

	@Deprecated
	int version();

	@Deprecated
	void setVersion(int integer);

	@Deprecated
	String getCommentUrl();

	@Deprecated
	String commentUrl();

	@Deprecated
	void setCommentUrl(String string);

	@Deprecated
	boolean isDiscard();

	@Deprecated
	void setDiscard(boolean boolean1);

	@Deprecated
	Set<Integer> getPorts();

	@Deprecated
	Set<Integer> ports();

	@Deprecated
	void setPorts(int... arr);

	@Deprecated
	void setPorts(Iterable<Integer> iterable);
}
