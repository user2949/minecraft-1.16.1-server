package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.List;

public final class CompositeNameResolver<T> extends SimpleNameResolver<T> {
	private final NameResolver<T>[] resolvers;

	public CompositeNameResolver(EventExecutor executor, NameResolver<T>... resolvers) {
		super(executor);
		ObjectUtil.checkNotNull(resolvers, "resolvers");

		for (int i = 0; i < resolvers.length; i++) {
			if (resolvers[i] == null) {
				throw new NullPointerException("resolvers[" + i + ']');
			}
		}

		if (resolvers.length < 2) {
			throw new IllegalArgumentException("resolvers: " + Arrays.asList(resolvers) + " (expected: at least 2 resolvers)");
		} else {
			this.resolvers = (NameResolver<T>[])resolvers.clone();
		}
	}

	@Override
	protected void doResolve(String inetHost, Promise<T> promise) throws Exception {
		this.doResolveRec(inetHost, promise, 0, null);
	}

	private void doResolveRec(String inetHost, Promise<T> promise, int resolverIndex, Throwable lastFailure) throws Exception {
		if (resolverIndex >= this.resolvers.length) {
			promise.setFailure(lastFailure);
		} else {
			NameResolver<T> resolver = this.resolvers[resolverIndex];
			resolver.resolve(inetHost).addListener(new FutureListener<T>() {
				@Override
				public void operationComplete(Future<T> future) throws Exception {
					if (future.isSuccess()) {
						promise.setSuccess(future.getNow());
					} else {
						CompositeNameResolver.this.doResolveRec(inetHost, promise, resolverIndex + 1, future.cause());
					}
				}
			});
		}
	}

	@Override
	protected void doResolveAll(String inetHost, Promise<List<T>> promise) throws Exception {
		this.doResolveAllRec(inetHost, promise, 0, null);
	}

	private void doResolveAllRec(String inetHost, Promise<List<T>> promise, int resolverIndex, Throwable lastFailure) throws Exception {
		if (resolverIndex >= this.resolvers.length) {
			promise.setFailure(lastFailure);
		} else {
			NameResolver<T> resolver = this.resolvers[resolverIndex];
			resolver.resolveAll(inetHost).addListener(new FutureListener<List<T>>() {
				@Override
				public void operationComplete(Future<List<T>> future) throws Exception {
					if (future.isSuccess()) {
						promise.setSuccess(future.getNow());
					} else {
						CompositeNameResolver.this.doResolveAllRec(inetHost, promise, resolverIndex + 1, future.cause());
					}
				}
			});
		}
	}
}
