package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.SocketUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class DefaultNameResolver extends InetNameResolver {
	public DefaultNameResolver(EventExecutor executor) {
		super(executor);
	}

	@Override
	protected void doResolve(String inetHost, Promise<InetAddress> promise) throws Exception {
		try {
			promise.setSuccess(SocketUtils.addressByName(inetHost));
		} catch (UnknownHostException var4) {
			promise.setFailure(var4);
		}
	}

	@Override
	protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise) throws Exception {
		try {
			promise.setSuccess(Arrays.asList(SocketUtils.allAddressesByName(inetHost)));
		} catch (UnknownHostException var4) {
			promise.setFailure(var4);
		}
	}
}
