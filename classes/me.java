import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.SocketAddress;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class me extends SimpleChannelInboundHandler<ni<?>> {
	private static final Logger g = LogManager.getLogger();
	public static final Marker a = MarkerManager.getMarker("NETWORK");
	public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
	public static final AttributeKey<mf> c = AttributeKey.valueOf("protocol");
	public static final ady<NioEventLoopGroup> d = new ady<>(
		() -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build())
	);
	public static final ady<EpollEventLoopGroup> e = new ady<>(
		() -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build())
	);
	public static final ady<DefaultEventLoopGroup> f = new ady<>(
		() -> new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build())
	);
	private final nj h;
	private final Queue<me.a> i = Queues.<me.a>newConcurrentLinkedQueue();
	private Channel j;
	private SocketAddress k;
	private mj l;
	private mr m;
	private boolean n;
	private boolean o;
	private int p;
	private int q;
	private float r;
	private float s;
	private int t;
	private boolean u;

	public me(nj nj) {
		this.h = nj;
	}

	@Override
	public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
		super.channelActive(channelHandlerContext);
		this.j = channelHandlerContext.channel();
		this.k = this.j.remoteAddress();

		try {
			this.a(mf.HANDSHAKING);
		} catch (Throwable var3) {
			g.fatal(var3);
		}
	}

	public void a(mf mf) {
		this.j.attr(c).set(mf);
		this.j.config().setAutoRead(true);
		g.debug("Enabled auto read");
	}

	@Override
	public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
		this.a(new ne("disconnect.endOfStream"));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
		if (throwable instanceof mk) {
			g.debug("Skipping packet due to errors", throwable.getCause());
		} else {
			boolean boolean4 = !this.u;
			this.u = true;
			if (this.j.isOpen()) {
				if (throwable instanceof TimeoutException) {
					g.debug("Timeout", throwable);
					this.a(new ne("disconnect.timeout"));
				} else {
					mr mr5 = new ne("disconnect.genericReason", "Internal Exception: " + throwable);
					if (boolean4) {
						g.debug("Failed to sent packet", throwable);
						this.a(new om(mr5), future -> this.a(mr5));
						this.k();
					} else {
						g.debug("Double fault", throwable);
						this.a(mr5);
					}
				}
			}
		}
	}

	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ni<?> ni) throws Exception {
		if (this.j.isOpen()) {
			try {
				a(ni, this.l);
			} catch (ur var4) {
			}

			this.p++;
		}
	}

	private static <T extends mj> void a(ni<T> ni, mj mj) {
		ni.a((T)mj);
	}

	public void a(mj mj) {
		Validate.notNull(mj, "packetListener");
		this.l = mj;
	}

	public void a(ni<?> ni) {
		this.a(ni, null);
	}

	public void a(ni<?> ni, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
		if (this.g()) {
			this.o();
			this.b(ni, genericFutureListener);
		} else {
			this.i.add(new me.a(ni, genericFutureListener));
		}
	}

	private void b(ni<?> ni, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
		mf mf4 = mf.a(ni);
		mf mf5 = this.j.attr(c).get();
		this.q++;
		if (mf5 != mf4) {
			g.debug("Disabled auto read");
			this.j.config().setAutoRead(false);
		}

		if (this.j.eventLoop().inEventLoop()) {
			if (mf4 != mf5) {
				this.a(mf4);
			}

			ChannelFuture channelFuture6 = this.j.writeAndFlush(ni);
			if (genericFutureListener != null) {
				channelFuture6.addListener(genericFutureListener);
			}

			channelFuture6.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		} else {
			this.j.eventLoop().execute(() -> {
				if (mf4 != mf5) {
					this.a(mf4);
				}

				ChannelFuture channelFuture6x = this.j.writeAndFlush(ni);
				if (genericFutureListener != null) {
					channelFuture6x.addListener(genericFutureListener);
				}

				channelFuture6x.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
			});
		}
	}

	private void o() {
		if (this.j != null && this.j.isOpen()) {
			synchronized (this.i) {
				me.a a3;
				while ((a3 = (me.a)this.i.poll()) != null) {
					this.b(a3.a, a3.b);
				}
			}
		}
	}

	public void a() {
		this.o();
		if (this.l instanceof zx) {
			((zx)this.l).b();
		}

		if (this.l instanceof zv) {
			((zv)this.l).b();
		}

		if (this.j != null) {
			this.j.flush();
		}

		if (this.t++ % 20 == 0) {
			this.s = this.s * 0.75F + (float)this.q * 0.25F;
			this.r = this.r * 0.75F + (float)this.p * 0.25F;
			this.q = 0;
			this.p = 0;
		}
	}

	public SocketAddress b() {
		return this.k;
	}

	public void a(mr mr) {
		if (this.j.isOpen()) {
			this.j.close().awaitUninterruptibly();
			this.m = mr;
		}
	}

	public boolean c() {
		return this.j instanceof LocalChannel || this.j instanceof LocalServerChannel;
	}

	public void a(SecretKey secretKey) {
		this.n = true;
		this.j.pipeline().addBefore("splitter", "decrypt", new ma(adn.a(2, secretKey)));
		this.j.pipeline().addBefore("prepender", "encrypt", new mb(adn.a(1, secretKey)));
	}

	public boolean g() {
		return this.j != null && this.j.isOpen();
	}

	public boolean h() {
		return this.j == null;
	}

	public mj i() {
		return this.l;
	}

	@Nullable
	public mr j() {
		return this.m;
	}

	public void k() {
		this.j.config().setAutoRead(false);
	}

	public void a(int integer) {
		if (integer >= 0) {
			if (this.j.pipeline().get("decompress") instanceof mc) {
				((mc)this.j.pipeline().get("decompress")).a(integer);
			} else {
				this.j.pipeline().addBefore("decoder", "decompress", new mc(integer));
			}

			if (this.j.pipeline().get("compress") instanceof md) {
				((md)this.j.pipeline().get("compress")).a(integer);
			} else {
				this.j.pipeline().addBefore("encoder", "compress", new md(integer));
			}
		} else {
			if (this.j.pipeline().get("decompress") instanceof mc) {
				this.j.pipeline().remove("decompress");
			}

			if (this.j.pipeline().get("compress") instanceof md) {
				this.j.pipeline().remove("compress");
			}
		}
	}

	public void l() {
		if (this.j != null && !this.j.isOpen()) {
			if (this.o) {
				g.warn("handleDisconnection() called twice");
			} else {
				this.o = true;
				if (this.j() != null) {
					this.i().a(this.j());
				} else if (this.i() != null) {
					this.i().a(new ne("multiplayer.disconnect.generic"));
				}
			}
		}
	}

	static class a {
		private final ni<?> a;
		@Nullable
		private final GenericFutureListener<? extends Future<? super Void>> b;

		public a(ni<?> ni, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
			this.a = ni;
			this.b = genericFutureListener;
		}
	}
}
