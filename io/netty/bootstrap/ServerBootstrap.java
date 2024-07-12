package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel> {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
	private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap();
	private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap();
	private final ServerBootstrapConfig config = new ServerBootstrapConfig(this);
	private volatile EventLoopGroup childGroup;
	private volatile ChannelHandler childHandler;

	public ServerBootstrap() {
	}

	private ServerBootstrap(ServerBootstrap bootstrap) {
		super(bootstrap);
		this.childGroup = bootstrap.childGroup;
		this.childHandler = bootstrap.childHandler;
		synchronized (bootstrap.childOptions) {
			this.childOptions.putAll(bootstrap.childOptions);
		}

		synchronized (bootstrap.childAttrs) {
			this.childAttrs.putAll(bootstrap.childAttrs);
		}
	}

	public ServerBootstrap group(EventLoopGroup group) {
		return this.group(group, group);
	}

	public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
		super.group(parentGroup);
		if (childGroup == null) {
			throw new NullPointerException("childGroup");
		} else if (this.childGroup != null) {
			throw new IllegalStateException("childGroup set already");
		} else {
			this.childGroup = childGroup;
			return this;
		}
	}

	public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value) {
		if (childOption == null) {
			throw new NullPointerException("childOption");
		} else {
			if (value == null) {
				synchronized (this.childOptions) {
					this.childOptions.remove(childOption);
				}
			} else {
				synchronized (this.childOptions) {
					this.childOptions.put(childOption, value);
				}
			}

			return this;
		}
	}

	public <T> ServerBootstrap childAttr(AttributeKey<T> childKey, T value) {
		if (childKey == null) {
			throw new NullPointerException("childKey");
		} else {
			if (value == null) {
				this.childAttrs.remove(childKey);
			} else {
				this.childAttrs.put(childKey, value);
			}

			return this;
		}
	}

	public ServerBootstrap childHandler(ChannelHandler childHandler) {
		if (childHandler == null) {
			throw new NullPointerException("childHandler");
		} else {
			this.childHandler = childHandler;
			return this;
		}
	}

	@Override
	void init(Channel channel) throws Exception {
		Map<ChannelOption<?>, Object> options = this.options0();
		synchronized (options) {
			setChannelOptions(channel, options, logger);
		}

		Map<AttributeKey<?>, Object> attrs = this.attrs0();
		synchronized (attrs) {
			for (Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
				AttributeKey<Object> key = (AttributeKey<Object>)e.getKey();
				channel.attr(key).set(e.getValue());
			}
		}

		ChannelPipeline p = channel.pipeline();
		final EventLoopGroup currentChildGroup = this.childGroup;
		final ChannelHandler currentChildHandler = this.childHandler;
		final Entry<ChannelOption<?>, Object>[] currentChildOptions;
		synchronized (this.childOptions) {
			currentChildOptions = (Entry<ChannelOption<?>, Object>[])this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
		}

		final Entry<AttributeKey<?>, Object>[] currentChildAttrs;
		synchronized (this.childAttrs) {
			currentChildAttrs = (Entry<AttributeKey<?>, Object>[])this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
		}

		p.addLast(new ChannelInitializer<Channel>() {
			@Override
			public void initChannel(Channel ch) throws Exception {
				final ChannelPipeline pipeline = ch.pipeline();
				ChannelHandler handler = ServerBootstrap.this.config.handler();
				if (handler != null) {
					pipeline.addLast(handler);
				}

				ch.eventLoop().execute(new Runnable() {
					public void run() {
						pipeline.addLast(new ServerBootstrap.ServerBootstrapAcceptor(ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
					}
				});
			}
		});
	}

	public ServerBootstrap validate() {
		super.validate();
		if (this.childHandler == null) {
			throw new IllegalStateException("childHandler not set");
		} else {
			if (this.childGroup == null) {
				logger.warn("childGroup is not set. Using parentGroup instead.");
				this.childGroup = this.config.group();
			}

			return this;
		}
	}

	private static Entry<AttributeKey<?>, Object>[] newAttrArray(int size) {
		return new Entry[size];
	}

	private static Entry<ChannelOption<?>, Object>[] newOptionArray(int size) {
		return new Entry[size];
	}

	public ServerBootstrap clone() {
		return new ServerBootstrap(this);
	}

	@Deprecated
	public EventLoopGroup childGroup() {
		return this.childGroup;
	}

	final ChannelHandler childHandler() {
		return this.childHandler;
	}

	final Map<ChannelOption<?>, Object> childOptions() {
		return copiedMap(this.childOptions);
	}

	final Map<AttributeKey<?>, Object> childAttrs() {
		return copiedMap(this.childAttrs);
	}

	public final ServerBootstrapConfig config() {
		return this.config;
	}

	private static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {
		private final EventLoopGroup childGroup;
		private final ChannelHandler childHandler;
		private final Entry<ChannelOption<?>, Object>[] childOptions;
		private final Entry<AttributeKey<?>, Object>[] childAttrs;
		private final Runnable enableAutoReadTask;

		ServerBootstrapAcceptor(
			Channel channel,
			EventLoopGroup childGroup,
			ChannelHandler childHandler,
			Entry<ChannelOption<?>, Object>[] childOptions,
			Entry<AttributeKey<?>, Object>[] childAttrs
		) {
			this.childGroup = childGroup;
			this.childHandler = childHandler;
			this.childOptions = childOptions;
			this.childAttrs = childAttrs;
			this.enableAutoReadTask = new Runnable() {
				public void run() {
					channel.config().setAutoRead(true);
				}
			};
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			final Channel child = (Channel)msg;
			child.pipeline().addLast(this.childHandler);
			AbstractBootstrap.setChannelOptions(child, this.childOptions, ServerBootstrap.logger);

			for (Entry<AttributeKey<?>, Object> e : this.childAttrs) {
				child.<Object>attr((AttributeKey<Object>)e.getKey()).set(e.getValue());
			}

			try {
				this.childGroup.register(child).addListener(new ChannelFutureListener() {
					public void operationComplete(ChannelFuture future) throws Exception {
						if (!future.isSuccess()) {
							ServerBootstrap.ServerBootstrapAcceptor.forceClose(child, future.cause());
						}
					}
				});
			} catch (Throwable var8) {
				forceClose(child, var8);
			}
		}

		private static void forceClose(Channel child, Throwable t) {
			child.unsafe().closeForcibly();
			ServerBootstrap.logger.warn("Failed to register an accepted channel: {}", child, t);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ChannelConfig config = ctx.channel().config();
			if (config.isAutoRead()) {
				config.setAutoRead(false);
				ctx.channel().eventLoop().schedule(this.enableAutoReadTask, 1L, TimeUnit.SECONDS);
			}

			ctx.fireExceptionCaught(cause);
		}
	}
}
