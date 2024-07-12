package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundRobinInetAddressResolver extends InetNameResolver {
	private final NameResolver<InetAddress> nameResolver;

	public RoundRobinInetAddressResolver(EventExecutor executor, NameResolver<InetAddress> nameResolver) {
		super(executor);
		this.nameResolver = nameResolver;
	}

	@Override
	protected void doResolve(String inetHost, Promise<InetAddress> promise) throws Exception {
		this.nameResolver.resolveAll(inetHost).addListener(new FutureListener<List<InetAddress>>() {
			@Override
			public void operationComplete(Future<List<InetAddress>> future) throws Exception {
				if (future.isSuccess()) {
					List<InetAddress> inetAddresses = future.getNow();
					int numAddresses = inetAddresses.size();
					if (numAddresses > 0) {
						promise.setSuccess((InetAddress)inetAddresses.get(RoundRobinInetAddressResolver.randomIndex(numAddresses)));
					} else {
						promise.setFailure(new UnknownHostException(inetHost));
					}
				} else {
					promise.setFailure(future.cause());
				}
			}
		});
	}

	@Override
	protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise) throws Exception {
		this.nameResolver.resolveAll(inetHost).addListener(new FutureListener<List<InetAddress>>() {
			@Override
			public void operationComplete(Future<List<InetAddress>> future) throws Exception {
				if (future.isSuccess()) {
					List<InetAddress> inetAddresses = future.getNow();
					if (!inetAddresses.isEmpty()) {
						List<InetAddress> result = new ArrayList(inetAddresses);
						Collections.rotate(result, RoundRobinInetAddressResolver.randomIndex(inetAddresses.size()));
						promise.setSuccess(result);
					} else {
						promise.setSuccess(inetAddresses);
					}
				} else {
					promise.setFailure(future.cause());
				}
			}
		});
	}

	private static int randomIndex(int numAddresses) {
		return numAddresses == 1 ? 0 : PlatformDependent.threadLocalRandom().nextInt(numAddresses);
	}
}
