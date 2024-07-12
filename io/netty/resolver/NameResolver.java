package io.netty.resolver;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import java.io.Closeable;
import java.util.List;

public interface NameResolver<T> extends Closeable {
	Future<T> resolve(String string);

	Future<T> resolve(String string, Promise<T> promise);

	Future<List<T>> resolveAll(String string);

	Future<List<T>> resolveAll(String string, Promise<List<T>> promise);

	void close();
}
