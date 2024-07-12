import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zu {
	private static final Logger d = LogManager.getLogger();
	public static final ady<NioEventLoopGroup> a = new ady<>(
		() -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build())
	);
	public static final ady<EpollEventLoopGroup> b = new ady<>(
		() -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build())
	);
	private final MinecraftServer e;
	public volatile boolean c;
	private final List<ChannelFuture> f = Collections.synchronizedList(Lists.newArrayList());
	private final List<me> g = Collections.synchronizedList(Lists.newArrayList());

	public zu(MinecraftServer minecraftServer) {
		this.e = minecraftServer;
		this.c = true;
	}

	public void a(@Nullable InetAddress inetAddress, int integer) throws IOException {
		synchronized (this.f) {
			Class<? extends ServerSocketChannel> class5;
			ady<? extends EventLoopGroup> ady6;
			if (Epoll.isAvailable() && this.e.k()) {
				class5 = EpollServerSocketChannel.class;
				ady6 = b;
				d.info("Using epoll channel type");
			} else {
				class5 = NioServerSocketChannel.class;
				ady6 = a;
				d.info("Using default channel type");
			}

			this.f
				.add(
					new ServerBootstrap()
						.channel(class5)
						.childHandler(
							new ChannelInitializer<Channel>() {
								@Override
								protected void initChannel(Channel channel) throws Exception {
									try {
										channel.config().setOption(ChannelOption.TCP_NODELAY, true);
									} catch (ChannelException var3) {
									}
				
									channel.pipeline()
										.addLast("timeout", new ReadTimeoutHandler(30))
										.addLast("legacy_query", new zs(zu.this))
										.addLast("splitter", new ml())
										.addLast("decoder", new mh(nj.SERVERBOUND))
										.addLast("prepender", new mm())
										.addLast("encoder", new mi(nj.CLIENTBOUND));
									me me3 = new me(nj.SERVERBOUND);
									zu.this.g.add(me3);
									channel.pipeline().addLast("packet_handler", me3);
									me3.a(new zw(zu.this.e, me3));
								}
							}
						)
						.group(ady6.a())
						.localAddress(inetAddress, integer)
						.bind()
						.syncUninterruptibly()
				);
		}
	}

	public void b() {
		this.c = false;

		for (ChannelFuture channelFuture3 : this.f) {
			try {
				channelFuture3.channel().close().sync();
			} catch (InterruptedException var4) {
				d.error("Interrupted whilst closing channel");
			}
		}
	}

	public void c() {
		synchronized (this.g) {
			Iterator<me> iterator3 = this.g.iterator();

			while (iterator3.hasNext()) {
				me me4 = (me)iterator3.next();
				if (!me4.h()) {
					if (me4.g()) {
						try {
							me4.a();
						} catch (Exception var7) {
							if (me4.c()) {
								throw new s(j.a(var7, "Ticking memory connection"));
							}

							d.warn("Failed to handle packet for {}", me4.b(), var7);
							mr mr6 = new nd("Internal server error");
							me4.a(new om(mr6), future -> me4.a(mr6));
							me4.k();
						}
					} else {
						iterator3.remove();
						me4.l();
					}
				}
			}
		}
	}

	public MinecraftServer d() {
		return this.e;
	}
}
