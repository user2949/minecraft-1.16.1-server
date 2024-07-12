package io.netty.handler.codec.http2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class Http2StreamChannelBootstrap {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http2StreamChannelBootstrap.class);
	private final Map<ChannelOption<?>, Object> options = new LinkedHashMap();
	private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap();
	private final Channel channel;
	private volatile ChannelHandler handler;

	public Http2StreamChannelBootstrap(Channel channel) {
		this.channel = ObjectUtil.checkNotNull(channel, "channel");
	}

	public <T> Http2StreamChannelBootstrap option(ChannelOption<T> option, T value) {
		if (option == null) {
			throw new NullPointerException("option");
		} else {
			if (value == null) {
				synchronized (this.options) {
					this.options.remove(option);
				}
			} else {
				synchronized (this.options) {
					this.options.put(option, value);
				}
			}

			return this;
		}
	}

	public <T> Http2StreamChannelBootstrap attr(AttributeKey<T> key, T value) {
		if (key == null) {
			throw new NullPointerException("key");
		} else {
			if (value == null) {
				synchronized (this.attrs) {
					this.attrs.remove(key);
				}
			} else {
				synchronized (this.attrs) {
					this.attrs.put(key, value);
				}
			}

			return this;
		}
	}

	public Http2StreamChannelBootstrap handler(ChannelHandler handler) {
		this.handler = ObjectUtil.checkNotNull(handler, "handler");
		return this;
	}

	public Future<Http2StreamChannel> open() {
		return this.open(this.channel.eventLoop().newPromise());
	}

	public Future<Http2StreamChannel> open(Promise<Http2StreamChannel> promise) {
		final ChannelHandlerContext ctx = this.channel.pipeline().context(Http2MultiplexCodec.class);
		if (ctx == null) {
			if (this.channel.isActive()) {
				promise.setFailure(
					new IllegalStateException(StringUtil.simpleClassName(Http2MultiplexCodec.class) + " must be in the ChannelPipeline of Channel " + this.channel)
				);
			} else {
				promise.setFailure(new ClosedChannelException());
			}
		} else {
			EventExecutor executor = ctx.executor();
			if (executor.inEventLoop()) {
				this.open0(ctx, promise);
			} else {
				executor.execute(new Runnable() {
					public void run() {
						Http2StreamChannelBootstrap.this.open0(ctx, promise);
					}
				});
			}
		}

		return promise;
	}

	public void open0(ChannelHandlerContext ctx, Promise<Http2StreamChannel> promise) {
		assert ctx.executor().inEventLoop();

		final Http2StreamChannel streamChannel = ((Http2MultiplexCodec)ctx.handler()).newOutboundStream();

		try {
			this.init(streamChannel);
		} catch (Exception var5) {
			streamChannel.unsafe().closeForcibly();
			promise.setFailure(var5);
			return;
		}

		ChannelFuture future = ctx.channel().eventLoop().register(streamChannel);
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					promise.setSuccess(streamChannel);
				} else if (future.isCancelled()) {
					promise.cancel(false);
				} else {
					if (streamChannel.isRegistered()) {
						streamChannel.close();
					} else {
						streamChannel.unsafe().closeForcibly();
					}

					promise.setFailure(future.cause());
				}
			}
		});
	}

	private void init(Channel channel) throws Exception {
		ChannelPipeline p = channel.pipeline();
		ChannelHandler handler = this.handler;
		if (handler != null) {
			p.addLast(handler);
		}

		synchronized (this.options) {
			setChannelOptions(channel, this.options, logger);
		}

		synchronized (this.attrs) {
			for (Entry<AttributeKey<?>, Object> e : this.attrs.entrySet()) {
				channel.<Object>attr((AttributeKey<Object>)e.getKey()).set(e.getValue());
			}
		}
	}

	private static void setChannelOptions(Channel channel, Map<ChannelOption<?>, Object> options, InternalLogger logger) {
		for (Entry<ChannelOption<?>, Object> e : options.entrySet()) {
			setChannelOption(channel, (ChannelOption<?>)e.getKey(), e.getValue(), logger);
		}
	}

	private static void setChannelOption(Channel channel, ChannelOption<?> option, Object value, InternalLogger logger) {
		try {
			if (!channel.config().setOption(option, value)) {
				logger.warn("Unknown channel option '{}' for channel '{}'", option, channel);
			}
		} catch (Throwable var5) {
			logger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", option, value, channel, var5);
		}
	}
}
