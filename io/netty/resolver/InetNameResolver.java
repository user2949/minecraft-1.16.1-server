package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public abstract class InetNameResolver extends SimpleNameResolver<InetAddress> {
	private volatile AddressResolver<InetSocketAddress> addressResolver;

	protected InetNameResolver(EventExecutor executor) {
		super(executor);
	}

	public AddressResolver<InetSocketAddress> asAddressResolver() {
		AddressResolver<InetSocketAddress> result = this.addressResolver;
		if (result == null) {
			synchronized (this) {
				result = this.addressResolver;
				if (result == null) {
					this.addressResolver = result = new InetSocketAddressResolver(this.executor(), this);
				}
			}
		}

		return result;
	}
}
