import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zx implements tc {
	private static final AtomicInteger b = new AtomicInteger(0);
	private static final Logger c = LogManager.getLogger();
	private static final Random d = new Random();
	private final byte[] e = new byte[4];
	private final MinecraftServer f;
	public final me a;
	private zx.a g = zx.a.HELLO;
	private int h;
	private GameProfile i;
	private final String j = "";
	private SecretKey k;
	private ze l;

	public zx(MinecraftServer minecraftServer, me me) {
		this.f = minecraftServer;
		this.a = me;
		d.nextBytes(this.e);
	}

	public void b() {
		if (this.g == zx.a.READY_TO_ACCEPT) {
			this.c();
		} else if (this.g == zx.a.DELAY_ACCEPT) {
			ze ze2 = this.f.ac().a(this.i.getId());
			if (ze2 == null) {
				this.g = zx.a.READY_TO_ACCEPT;
				this.f.ac().a(this.a, this.l);
				this.l = null;
			}
		}

		if (this.h++ == 600) {
			this.b(new ne("multiplayer.disconnect.slow_login"));
		}
	}

	@Override
	public me a() {
		return this.a;
	}

	public void b(mr mr) {
		try {
			c.info("Disconnecting {}: {}", this.d(), mr.getString());
			this.a.a(new tb(mr));
			this.a.a(mr);
		} catch (Exception var3) {
			c.error("Error whilst disconnecting player", (Throwable)var3);
		}
	}

	public void c() {
		if (!this.i.isComplete()) {
			this.i = this.a(this.i);
		}

		mr mr2 = this.f.ac().a(this.a.b(), this.i);
		if (mr2 != null) {
			this.b(mr2);
		} else {
			this.g = zx.a.ACCEPTED;
			if (this.f.av() >= 0 && !this.a.c()) {
				this.a.a(new ta(this.f.av()), channelFuture -> this.a.a(this.f.av()));
			}

			this.a.a(new sy(this.i));
			ze ze3 = this.f.ac().a(this.i.getId());
			if (ze3 != null) {
				this.g = zx.a.DELAY_ACCEPT;
				this.l = this.f.ac().g(this.i);
			} else {
				this.f.ac().a(this.a, this.f.ac().g(this.i));
			}
		}
	}

	@Override
	public void a(mr mr) {
		c.info("{} lost connection: {}", this.d(), mr.getString());
	}

	public String d() {
		return this.i != null ? this.i + " (" + this.a.b() + ")" : String.valueOf(this.a.b());
	}

	@Override
	public void a(te te) {
		Validate.validState(this.g == zx.a.HELLO, "Unexpected hello packet");
		this.i = te.b();
		if (this.f.T() && !this.a.c()) {
			this.g = zx.a.KEY;
			this.a.a(new sz("", this.f.K().getPublic(), this.e));
		} else {
			this.g = zx.a.READY_TO_ACCEPT;
		}
	}

	@Override
	public void a(tf tf) {
		Validate.validState(this.g == zx.a.KEY, "Unexpected key packet");
		PrivateKey privateKey3 = this.f.K().getPrivate();
		if (!Arrays.equals(this.e, tf.b(privateKey3))) {
			throw new IllegalStateException("Invalid nonce!");
		} else {
			this.k = tf.a(privateKey3);
			this.g = zx.a.AUTHENTICATING;
			this.a.a(this.k);
			Thread thread4 = new Thread("User Authenticator #" + b.incrementAndGet()) {
				public void run() {
					GameProfile gameProfile2 = zx.this.i;

					try {
						String string3 = new BigInteger(adn.a("", zx.this.f.K().getPublic(), zx.this.k)).toString(16);
						zx.this.i = zx.this.f.an().hasJoinedServer(new GameProfile(null, gameProfile2.getName()), string3, this.a());
						if (zx.this.i != null) {
							zx.c.info("UUID of player {} is {}", zx.this.i.getName(), zx.this.i.getId());
							zx.this.g = zx.a.READY_TO_ACCEPT;
						} else if (zx.this.f.N()) {
							zx.c.warn("Failed to verify username but will let them in anyway!");
							zx.this.i = zx.this.a(gameProfile2);
							zx.this.g = zx.a.READY_TO_ACCEPT;
						} else {
							zx.this.b(new ne("multiplayer.disconnect.unverified_username"));
							zx.c.error("Username '{}' tried to join with an invalid session", gameProfile2.getName());
						}
					} catch (AuthenticationUnavailableException var3) {
						if (zx.this.f.N()) {
							zx.c.warn("Authentication servers are down but will let them in anyway!");
							zx.this.i = zx.this.a(gameProfile2);
							zx.this.g = zx.a.READY_TO_ACCEPT;
						} else {
							zx.this.b(new ne("multiplayer.disconnect.authservers_down"));
							zx.c.error("Couldn't verify username because servers are unavailable");
						}
					}
				}

				@Nullable
				private InetAddress a() {
					SocketAddress socketAddress2 = zx.this.a.b();
					return zx.this.f.U() && socketAddress2 instanceof InetSocketAddress ? ((InetSocketAddress)socketAddress2).getAddress() : null;
				}
			};
			thread4.setUncaughtExceptionHandler(new m(c));
			thread4.start();
		}
	}

	@Override
	public void a(td td) {
		this.b(new ne("multiplayer.disconnect.unexpected_query_response"));
	}

	protected GameProfile a(GameProfile gameProfile) {
		UUID uUID3 = bec.c(gameProfile.getName());
		return new GameProfile(uUID3, gameProfile.getName());
	}

	static enum a {
		HELLO,
		KEY,
		AUTHENTICATING,
		NEGOTIATING,
		READY_TO_ACCEPT,
		DELAY_ACCEPT,
		ACCEPTED;
	}
}
