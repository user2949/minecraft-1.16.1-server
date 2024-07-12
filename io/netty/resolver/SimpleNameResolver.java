package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public abstract class SimpleNameResolver<T> implements NameResolver<T> {
	private final EventExecutor executor;

	protected SimpleNameResolver(EventExecutor executor) {
		this.executor = ObjectUtil.checkNotNull(executor, "executor");
	}

	protected EventExecutor executor() {
		return this.executor;
	}

	@Override
	public final Future<T> resolve(String inetHost) {
		Promise<T> promise = this.executor().newPromise();
		return this.resolve(inetHost, promise);
	}

	@Override
	public Future<T> resolve(String inetHost, Promise<T> promise) {
		ObjectUtil.checkNotNull(promise, "promise");

		try {
			this.doResolve(inetHost, promise);
			return promise;
		} catch (Exception var4) {
			return promise.setFailure(var4);
		}
	}

	@Override
	public final Future<List<T>> resolveAll(String inetHost) {
		Promise<List<T>> promise = this.executor().newPromise();
		return this.resolveAll(inetHost, promise);
	}

	@Override
	public Future<List<T>> resolveAll(String inetHost, Promise<List<T>> promise) {
		ObjectUtil.checkNotNull(promise, "promise");

		try {
			this.doResolveAll(inetHost, promise);
			return promise;
		} catch (Exception var4) {
			return promise.setFailure(var4);
		}
	}

	protected abstract void doResolve(String string, Promise<T> promise) throws Exception;

	protected abstract void doResolveAll(String string, Promise<List<T>> promise) throws Exception;

	@Override
	public void close() {
	}
}
