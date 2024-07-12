package io.netty.resolver;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import java.io.Closeable;
import java.net.SocketAddress;
import java.util.List;

public interface AddressResolver<T extends SocketAddress> extends Closeable {
	boolean isSupported(SocketAddress socketAddress);

	boolean isResolved(SocketAddress socketAddress);

	Future<T> resolve(SocketAddress socketAddress);

	Future<T> resolve(SocketAddress socketAddress, Promise<T> promise);

	Future<List<T>> resolveAll(SocketAddress socketAddress);

	Future<List<T>> resolveAll(SocketAddress socketAddress, Promise<List<T>> promise);

	void close();
}
