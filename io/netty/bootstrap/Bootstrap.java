package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrap.PendingRegistrationPromise;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.DefaultAddressResolverGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Map.Entry;

public class Bootstrap extends AbstractBootstrap<Bootstrap, Channel> {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
	private static final AddressResolverGroup<?> DEFAULT_RESOLVER = DefaultAddressResolverGroup.INSTANCE;
	private final BootstrapConfig config = new BootstrapConfig(this);
	private volatile AddressResolverGroup<SocketAddress> resolver = (AddressResolverGroup<SocketAddress>)DEFAULT_RESOLVER;
	private volatile SocketAddress remoteAddress;

	public Bootstrap() {
	}

	private Bootstrap(Bootstrap bootstrap) {
		super(bootstrap);
		this.resolver = bootstrap.resolver;
		this.remoteAddress = bootstrap.remoteAddress;
	}

	public Bootstrap resolver(AddressResolverGroup<?> resolver) {
		this.resolver = (AddressResolverGroup<SocketAddress>)(resolver == null ? DEFAULT_RESOLVER : resolver);
		return this;
	}

	public Bootstrap remoteAddress(SocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
		return this;
	}

	public Bootstrap remoteAddress(String inetHost, int inetPort) {
		this.remoteAddress = InetSocketAddress.createUnresolved(inetHost, inetPort);
		return this;
	}

	public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
		this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
		return this;
	}

	public ChannelFuture connect() {
		this.validate();
		SocketAddress remoteAddress = this.remoteAddress;
		if (remoteAddress == null) {
			throw new IllegalStateException("remoteAddress not set");
		} else {
			return this.doResolveAndConnect(remoteAddress, this.config.localAddress());
		}
	}

	public ChannelFuture connect(String inetHost, int inetPort) {
		return this.connect(InetSocketAddress.createUnresolved(inetHost, inetPort));
	}

	public ChannelFuture connect(InetAddress inetHost, int inetPort) {
		return this.connect(new InetSocketAddress(inetHost, inetPort));
	}

	public ChannelFuture connect(SocketAddress remoteAddress) {
		if (remoteAddress == null) {
			throw new NullPointerException("remoteAddress");
		} else {
			this.validate();
			return this.doResolveAndConnect(remoteAddress, this.config.localAddress());
		}
	}

	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
		if (remoteAddress == null) {
			throw new NullPointerException("remoteAddress");
		} else {
			this.validate();
			return this.doResolveAndConnect(remoteAddress, localAddress);
		}
	}

	private ChannelFuture doResolveAndConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
		ChannelFuture regFuture = this.initAndRegister();
		final Channel channel = regFuture.channel();
		if (regFuture.isDone()) {
			return !regFuture.isSuccess() ? regFuture : this.doResolveAndConnect0(channel, remoteAddress, localAddress, channel.newPromise());
		} else {
			final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
			regFuture.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture future) throws Exception {
					Throwable cause = future.cause();
					if (cause != null) {
						promise.setFailure(cause);
					} else {
						promise.registered();
						Bootstrap.this.doResolveAndConnect0(channel, remoteAddress, localAddress, promise);
					}
				}
			});
			return promise;
		}
	}

	private ChannelFuture doResolveAndConnect0(Channel channel, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
		try {
			EventLoop eventLoop = channel.eventLoop();
			AddressResolver<SocketAddress> resolver = this.resolver.getResolver(eventLoop);
			if (!resolver.isSupported(remoteAddress) || resolver.isResolved(remoteAddress)) {
				doConnect(remoteAddress, localAddress, promise);
				return promise;
			}

			Future<SocketAddress> resolveFuture = resolver.resolve(remoteAddress);
			if (resolveFuture.isDone()) {
				Throwable resolveFailureCause = resolveFuture.cause();
				if (resolveFailureCause != null) {
					channel.close();
					promise.setFailure(resolveFailureCause);
				} else {
					doConnect(resolveFuture.getNow(), localAddress, promise);
				}

				return promise;
			}

			resolveFuture.addListener(new FutureListener<SocketAddress>() {
				@Override
				public void operationComplete(Future<SocketAddress> future) throws Exception {
					if (future.cause() != null) {
						channel.close();
						promise.setFailure(future.cause());
					} else {
						Bootstrap.doConnect(future.getNow(), localAddress, promise);
					}
				}
			});
		} catch (Throwable var9) {
			promise.tryFailure(var9);
		}

		return promise;
	}

	private static void doConnect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise connectPromise) {
		final Channel channel = connectPromise.channel();
		channel.eventLoop().execute(new Runnable() {
			public void run() {
				if (localAddress == null) {
					channel.connect(remoteAddress, connectPromise);
				} else {
					channel.connect(remoteAddress, localAddress, connectPromise);
				}

				connectPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			}
		});
	}

	@Override
	void init(Channel channel) throws Exception {
		ChannelPipeline p = channel.pipeline();
		p.addLast(this.config.handler());
		Map<ChannelOption<?>, Object> options = this.options0();
		synchronized (options) {
			setChannelOptions(channel, options, logger);
		}

		Map<AttributeKey<?>, Object> attrs = this.attrs0();
		synchronized (attrs) {
			for (Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
				channel.<Object>attr((AttributeKey<Object>)e.getKey()).set(e.getValue());
			}
		}
	}

	public Bootstrap validate() {
		super.validate();
		if (this.config.handler() == null) {
			throw new IllegalStateException("handler not set");
		} else {
			return this;
		}
	}

	public Bootstrap clone() {
		return new Bootstrap(this);
	}

	public Bootstrap clone(EventLoopGroup group) {
		Bootstrap bs = new Bootstrap(this);
		bs.group = group;
		return bs;
	}

	public final BootstrapConfig config() {
		return this.config;
	}

	final SocketAddress remoteAddress() {
		return this.remoteAddress;
	}

	final AddressResolverGroup<?> resolver() {
		return this.resolver;
	}
}
