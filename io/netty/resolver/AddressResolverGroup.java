package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.net.SocketAddress;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class AddressResolverGroup<T extends SocketAddress> implements Closeable {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
	private final Map<EventExecutor, AddressResolver<T>> resolvers = new IdentityHashMap();

	protected AddressResolverGroup() {
	}

	public AddressResolver<T> getResolver(EventExecutor executor) {
		if (executor == null) {
			throw new NullPointerException("executor");
		} else if (executor.isShuttingDown()) {
			throw new IllegalStateException("executor not accepting a task");
		} else {
			synchronized (this.resolvers) {
				AddressResolver<T> r = (AddressResolver<T>)this.resolvers.get(executor);
				if (r == null) {
					final AddressResolver<T> newResolver;
					try {
						newResolver = this.newResolver(executor);
					} catch (Exception var7) {
						throw new IllegalStateException("failed to create a new resolver", var7);
					}

					this.resolvers.put(executor, newResolver);
					executor.terminationFuture().addListener(new FutureListener<Object>() {
						@Override
						public void operationComplete(Future<Object> future) throws Exception {
							synchronized (AddressResolverGroup.this.resolvers) {
								AddressResolverGroup.this.resolvers.remove(executor);
							}

							newResolver.close();
						}
					});
					r = newResolver;
				}

				return r;
			}
		}
	}

	protected abstract AddressResolver<T> newResolver(EventExecutor eventExecutor) throws Exception;

	public void close() {
		AddressResolver<T>[] rArray;
		synchronized (this.resolvers) {
			rArray = (AddressResolver<T>[])this.resolvers.values().toArray(new AddressResolver[this.resolvers.size()]);
			this.resolvers.clear();
		}

		for (AddressResolver<T> r : rArray) {
			try {
				r.close();
			} catch (Throwable var7) {
				logger.warn("Failed to close a resolver:", var7);
			}
		}
	}
}
