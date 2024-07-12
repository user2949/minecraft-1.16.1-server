package net.minecraft.server;

import bpx.f;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer extends amr<uy> implements anp, cy, AutoCloseable {
	private static final Logger j = LogManager.getLogger();
	public static final File b = new File("usercache.json");
	public static final bqe c = new bqe("Demo World", bpy.SURVIVAL, false, and.NORMAL, false, new bpx(), bpn.a);
	protected final dae.a d;
	protected final dai e;
	private final ano k = new ano("server", this, v.b());
	private final List<Runnable> l = Lists.<Runnable>newArrayList();
	private amc m = new amc(v.a, this::ag);
	private ami n = amf.a;
	private final zu o;
	private final zn p;
	private final tl q = new tl();
	private final Random r = new Random();
	private final DataFixer s;
	private String t;
	private int u = -1;
	protected final gm.a f;
	private final Map<ug<bqb>, zd> v = Maps.<ug<bqb>, zd>newLinkedHashMap();
	private abp w;
	private volatile boolean x = true;
	private boolean y;
	private int z;
	protected final Proxy g;
	private boolean A;
	private boolean B;
	private boolean C;
	private boolean D;
	@Nullable
	private String E;
	private int F;
	private int G;
	public final long[] h = new long[100];
	@Nullable
	private KeyPair H;
	@Nullable
	private String I;
	private boolean J;
	private String K = "";
	private String L = "";
	private volatile boolean M;
	private long N;
	private boolean O;
	private boolean P;
	private final MinecraftSessionService Q;
	private final GameProfileRepository R;
	private final abl S;
	private long T;
	private final Thread U;
	private long V = v.b();
	private long W;
	private boolean X;
	private final aar<aap> Z;
	private final ux aa = new ux(this);
	@Nullable
	private czy ab;
	private final va ac = new va();
	private final uu ad;
	private final ads ae = new ads();
	private boolean af;
	private float ag;
	private final Executor ah;
	@Nullable
	private String ai;
	private uw aj;
	private final cva ak;
	protected final dal i;

	public static <S extends MinecraftServer> S a(Function<Thread, S> function) {
		AtomicReference<S> atomicReference2 = new AtomicReference();
		Thread thread3 = new Thread(() -> ((MinecraftServer)atomicReference2.get()).v(), "Server thread");
		thread3.setUncaughtExceptionHandler((thread, throwable) -> j.error(throwable));
		S minecraftServer4 = (S)function.apply(thread3);
		atomicReference2.set(minecraftServer4);
		thread3.start();
		return minecraftServer4;
	}

	public MinecraftServer(
		Thread thread,
		gm.a a,
		dae.a a,
		dal dal,
		aar<aap> aar,
		Proxy proxy,
		DataFixer dataFixer,
		uw uw,
		MinecraftSessionService minecraftSessionService,
		GameProfileRepository gameProfileRepository,
		abl abl,
		zn zn
	) {
		super("Server");
		this.f = a;
		this.i = dal;
		this.g = proxy;
		this.Z = aar;
		this.aj = uw;
		this.Q = minecraftSessionService;
		this.R = gameProfileRepository;
		this.S = abl;
		this.o = new zu(this);
		this.p = zn;
		this.d = a;
		this.e = a.b();
		this.s = dataFixer;
		this.ad = new uu(this, uw.a());
		this.ak = new cva(uw.h(), a, dataFixer);
		this.U = thread;
		this.ah = v.f();
	}

	private void a(daa daa) {
		dfn dfn3 = daa.a(dfn::new, "scoreboard");
		dfn3.a(this.aF());
		this.aF().a(new czp(dfn3));
	}

	protected abstract boolean d() throws IOException;

	public static void a(dae.a a) {
		if (a.c()) {
			j.info("Converting map!");
			a.a(new aed() {
				private long a = v.b();

				@Override
				public void a(mr mr) {
				}

				@Override
				public void a(int integer) {
					if (v.b() - this.a >= 1000L) {
						this.a = v.b();
						MinecraftServer.j.info("Converting... {}%", integer);
					}
				}

				@Override
				public void c(mr mr) {
				}
			});
		}
	}

	protected void l_() {
		this.q();
		this.i.a(this.getServerModName(), this.n().isPresent());
		zm zm2 = this.p.create(11);
		this.a(zm2);
		this.p();
		this.b(zm2);
	}

	protected void p() {
	}

	protected void a(zm zm) {
		dak dak3 = this.i.G();
		cix cix4 = this.i.z();
		boolean boolean5 = cix4.h();
		long long6 = cix4.b();
		long long8 = brg.a(long6);
		List<bpm> list10 = ImmutableList.of(new civ(), new ciu(), new bdl(), new axx(), new bdw(dak3));
		gh<cig> gh11 = cix4.e();
		cig cig13 = gh11.a(cig.b);
		cha cha12;
		cif cif14;
		if (cig13 == null) {
			cif14 = cif.a();
			cha12 = cix.a(new Random().nextLong());
		} else {
			cif14 = cig13.b();
			cha12 = cig13.c();
		}

		ug<cif> ug15 = (ug<cif>)this.f.a().c(cif14).orElseThrow(() -> new IllegalStateException("Unregistered dimension type: " + cif14));
		zd zd16 = new zd(this, this.ah, this.d, dak3, bqb.g, ug15, cif14, zm, cha12, boolean5, long8, list10, true);
		this.v.put(bqb.g, zd16);
		daa daa17 = zd16.s();
		this.a(daa17);
		this.ab = new czy(daa17);
		cgw cgw18 = zd16.f();
		cgw18.a(dak3.q());
		if (!dak3.o()) {
			try {
				a(zd16, dak3, cix4.d(), boolean5, true);
				dak3.c(true);
				if (boolean5) {
					this.a(this.i);
				}
			} catch (Throwable var28) {
				j j20 = j.a(var28, "Exception initializing level");

				try {
					zd16.a(j20);
				} catch (Throwable var27) {
				}

				throw new s(j20);
			}

			dak3.c(true);
		}

		this.ac().a(zd16);
		if (this.i.D() != null) {
			this.aK().a(this.i.D());
		}

		for (Entry<ug<cig>, cig> entry20 : gh11.c()) {
			ug<cig> ug21 = (ug<cig>)entry20.getKey();
			if (ug21 != cig.b) {
				ug<bqb> ug22 = ug.a(gl.ae, ug21.a());
				cif cif23 = ((cig)entry20.getValue()).b();
				ug<cif> ug24 = (ug<cif>)this.f.a().c(cif23).orElseThrow(() -> new IllegalStateException("Unregistered dimension type: " + cif23));
				cha cha25 = ((cig)entry20.getValue()).c();
				czz czz26 = new czz(this.i, dak3);
				zd zd27 = new zd(this, this.ah, this.d, czz26, ug22, ug24, cif23, zm, cha25, boolean5, long8, ImmutableList.of(), false);
				cgw18.a(new cgu.a(zd27.f()));
				this.v.put(ug22, zd27);
			}
		}
	}

	private static void a(zd zd, dak dak, boolean boolean3, boolean boolean4, boolean boolean5) {
		cha cha6 = zd.i().g();
		if (!boolean5) {
			dak.a(fu.b.b(cha6.c()));
		} else if (boolean4) {
			dak.a(fu.b.b());
		} else {
			brh brh7 = cha6.d();
			List<bre> list8 = brh7.b();
			Random random9 = new Random(zd.B());
			fu fu10 = brh7.a(0, zd.t_(), 0, 256, list8, random9);
			bph bph11 = fu10 == null ? new bph(0, 0) : new bph(fu10);
			if (fu10 == null) {
				j.warn("Unable to find spawn biome");
			}

			boolean boolean12 = false;

			for (bvr bvr14 : acx.U.b()) {
				if (brh7.d().contains(bvr14.n())) {
					boolean12 = true;
					break;
				}
			}

			dak.a(bph11.l().b(8, cha6.c(), 8));
			int integer13 = 0;
			int integer14 = 0;
			int integer15 = 0;
			int integer16 = -1;
			int integer17 = 32;

			for (int integer18 = 0; integer18 < 1024; integer18++) {
				if (integer13 > -16 && integer13 <= 16 && integer14 > -16 && integer14 <= 16) {
					fu fu19 = yy.a(zd, new bph(bph11.b + integer13, bph11.c + integer14), boolean12);
					if (fu19 != null) {
						dak.a(fu19);
						break;
					}
				}

				if (integer13 == integer14 || integer13 < 0 && integer13 == -integer14 || integer13 > 0 && integer13 == 1 - integer14) {
					int integer19 = integer15;
					integer15 = -integer16;
					integer16 = integer19;
				}

				integer13 += integer15;
				integer14 += integer16;
			}

			if (boolean3) {
				ckb<?, ?> ckb18 = ckt.T.b(cnr.k);
				ckb18.a(zd, zd.a(), cha6, zd.t, new fu(dak.a(), dak.b(), dak.c()));
			}
		}
	}

	private void a(dal dal) {
		dal.a(and.PEACEFUL);
		dal.d(true);
		dak dak3 = dal.G();
		dak3.b(false);
		dak3.a(false);
		dak3.a(1000000000);
		dak3.b(6000L);
		dak3.a(bpy.SPECTATOR);
	}

	private void b(zm zm) {
		zd zd3 = this.D();
		j.info("Preparing start region for dimension {}", zd3.W().a());
		fu fu4 = zd3.u();
		zm.a(new bph(fu4));
		zb zb5 = zd3.i();
		zb5.a().a(500);
		this.V = v.b();
		zb5.a(zi.a, new bph(fu4), 11, ael.INSTANCE);

		while (zb5.b() != 441) {
			this.V = v.b() + 10L;
			this.w();
		}

		this.V = v.b() + 10L;
		this.w();

		for (zd zd7 : this.v.values()) {
			bpw bpw8 = zd7.s().b(bpw::new, "chunks");
			if (bpw8 != null) {
				LongIterator longIterator9 = bpw8.a().iterator();

				while (longIterator9.hasNext()) {
					long long10 = longIterator9.nextLong();
					bph bph12 = new bph(long10);
					zd7.i().a(bph12, true);
				}
			}
		}

		this.V = v.b() + 10L;
		this.w();
		zm.b();
		zb5.a().a(5);
		this.ba();
	}

	protected void q() {
		File file2 = this.d.a(dac.h).toFile();
		if (file2.isFile()) {
			String string3 = this.d.a();

			try {
				this.a("level://" + URLEncoder.encode(string3, StandardCharsets.UTF_8.toString()) + "/" + "resources.zip", "");
			} catch (UnsupportedEncodingException var4) {
				j.warn("Something went wrong url encoding {}", string3);
			}
		}
	}

	public bpy r() {
		return this.i.l();
	}

	public boolean f() {
		return this.i.m();
	}

	public abstract int g();

	public abstract int h();

	public abstract boolean i();

	public boolean a(boolean boolean1, boolean boolean2, boolean boolean3) {
		boolean boolean5 = false;

		for (zd zd7 : this.F()) {
			if (!boolean1) {
				j.info("Saving chunks for level '{}'/{}", zd7, zd7.W().a());
			}

			zd7.a(null, boolean2, zd7.c && !boolean3);
			boolean5 = true;
		}

		zd zd6 = this.D();
		dak dak7 = this.i.G();
		dak7.a(zd6.f().t());
		this.i.b(this.aK().c());
		this.d.a(this.f, this.i, this.ac().q());
		return boolean5;
	}

	@Override
	public void close() {
		this.s();
	}

	protected void s() {
		j.info("Stopping server");
		if (this.ad() != null) {
			this.ad().b();
		}

		if (this.w != null) {
			j.info("Saving players");
			this.w.h();
			this.w.r();
		}

		j.info("Saving worlds");

		for (zd zd3 : this.F()) {
			if (zd3 != null) {
				zd3.c = false;
			}
		}

		this.a(false, true, false);

		for (zd zd3x : this.F()) {
			if (zd3x != null) {
				try {
					zd3x.close();
				} catch (IOException var5) {
					j.error("Exception closing the level", (Throwable)var5);
				}
			}
		}

		if (this.k.d()) {
			this.k.e();
		}

		this.aj.close();

		try {
			this.d.close();
		} catch (IOException var4) {
			j.error("Failed to unlock level {}", this.d.a(), var4);
		}
	}

	public String t() {
		return this.t;
	}

	public void a_(String string) {
		this.t = string;
	}

	public boolean u() {
		return this.x;
	}

	public void a(boolean boolean1) {
		this.x = false;
		if (boolean1) {
			try {
				this.U.join();
			} catch (InterruptedException var3) {
				j.error("Error while shutting down", (Throwable)var3);
			}
		}
	}

	protected void v() {
		try {
			if (this.d()) {
				this.V = v.b();
				this.q.a(new nd(this.E));
				this.q.a(new tl.c(u.a().getName(), u.a().getProtocolVersion()));
				this.a(this.q);

				while (this.x) {
					long long2 = v.b() - this.V;
					if (long2 > 2000L && this.V - this.N >= 15000L) {
						long long4 = long2 / 50L;
						j.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", long2, long4);
						this.V += long4 * 50L;
						this.N = this.V;
					}

					this.V += 50L;
					aml aml4 = aml.a("Server");
					this.a(aml4);
					this.n.a();
					this.n.a("tick");
					this.a(this::aY);
					this.n.b("nextTickWait");
					this.X = true;
					this.W = Math.max(v.b() + 50L, this.V);
					this.w();
					this.n.c();
					this.n.b();
					this.b(aml4);
					this.M = true;
				}
			} else {
				this.a(null);
			}
		} catch (Throwable var44) {
			j.error("Encountered an unexpected exception", var44);
			j j3;
			if (var44 instanceof s) {
				j3 = this.b(((s)var44).a());
			} else {
				j3 = this.b(new j("Exception in server tick loop", var44));
			}

			File file4 = new File(new File(this.A(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
			if (j3.a(file4)) {
				j.error("This crash report has been saved to: {}", file4.getAbsolutePath());
			} else {
				j.error("We were unable to save this crash report to disk.");
			}

			this.a(j3);
		} finally {
			try {
				this.y = true;
				this.s();
			} catch (Throwable var42) {
				j.error("Exception stopping the server", var42);
			} finally {
				this.e();
			}
		}
	}

	private boolean aY() {
		return this.bl() || v.b() < (this.X ? this.W : this.V);
	}

	protected void w() {
		this.bj();
		this.c(() -> !this.aY());
	}

	protected uy e(Runnable runnable) {
		return new uy(this.z, runnable);
	}

	protected boolean d(uy uy) {
		return uy.a() + 3 < this.z || this.aY();
	}

	@Override
	public boolean x() {
		boolean boolean2 = this.aZ();
		this.X = boolean2;
		return boolean2;
	}

	private boolean aZ() {
		if (super.x()) {
			return true;
		} else {
			if (this.aY()) {
				for (zd zd3 : this.F()) {
					if (zd3.i().d()) {
						return true;
					}
				}
			}

			return false;
		}
	}

	protected void c(uy uy) {
		this.aO().c("runTask");
		super.c(uy);
	}

	private void a(tl tl) {
		File file3 = this.c("server-icon.png");
		if (!file3.exists()) {
			file3 = this.d.f();
		}

		if (file3.isFile()) {
			ByteBuf byteBuf4 = Unpooled.buffer();

			try {
				BufferedImage bufferedImage5 = ImageIO.read(file3);
				Validate.validState(bufferedImage5.getWidth() == 64, "Must be 64 pixels wide");
				Validate.validState(bufferedImage5.getHeight() == 64, "Must be 64 pixels high");
				ImageIO.write(bufferedImage5, "PNG", new ByteBufOutputStream(byteBuf4));
				ByteBuffer byteBuffer6 = Base64.getEncoder().encode(byteBuf4.nioBuffer());
				tl.a("data:image/png;base64," + StandardCharsets.UTF_8.decode(byteBuffer6));
			} catch (Exception var9) {
				j.error("Couldn't load server icon", (Throwable)var9);
			} finally {
				byteBuf4.release();
			}
		}
	}

	public File A() {
		return new File(".");
	}

	protected void a(j j) {
	}

	protected void e() {
	}

	protected void a(BooleanSupplier booleanSupplier) {
		long long3 = v.c();
		this.z++;
		this.b(booleanSupplier);
		if (long3 - this.T >= 5000000000L) {
			this.T = long3;
			this.q.a(new tl.a(this.I(), this.H()));
			GameProfile[] arr5 = new GameProfile[Math.min(this.H(), 12)];
			int integer6 = aec.a(this.r, 0, this.H() - arr5.length);

			for (int integer7 = 0; integer7 < arr5.length; integer7++) {
				arr5[integer7] = ((ze)this.w.s().get(integer6 + integer7)).ez();
			}

			Collections.shuffle(Arrays.asList(arr5));
			this.q.b().a(arr5);
		}

		if (this.z % 6000 == 0) {
			j.debug("Autosave started");
			this.n.a("save");
			this.w.h();
			this.a(true, false, false);
			this.n.c();
			j.debug("Autosave finished");
		}

		this.n.a("snooper");
		if (!this.k.d() && this.z > 100) {
			this.k.a();
		}

		if (this.z % 6000 == 0) {
			this.k.b();
		}

		this.n.c();
		this.n.a("tallying");
		long long5 = this.h[this.z % 100] = v.c() - long3;
		this.ag = this.ag * 0.8F + (float)long5 / 1000000.0F * 0.19999999F;
		long long7 = v.c();
		this.ae.a(long7 - long3);
		this.n.c();
	}

	protected void b(BooleanSupplier booleanSupplier) {
		this.n.a("commandFunctions");
		this.az().d();
		this.n.b("levels");

		for (zd zd4 : this.F()) {
			this.n.a((Supplier<String>)(() -> zd4 + " " + zd4.W().a()));
			if (this.z % 20 == 0) {
				this.n.a("timeSync");
				this.w.a(new qj(zd4.Q(), zd4.R(), zd4.S().b(bpx.j)), zd4.W());
				this.n.c();
			}

			this.n.a("tick");

			try {
				zd4.a(booleanSupplier);
			} catch (Throwable var6) {
				j j6 = j.a(var6, "Exception ticking world");
				zd4.a(j6);
				throw new s(j6);
			}

			this.n.c();
			this.n.c();
		}

		this.n.b("connection");
		this.ad().c();
		this.n.b("players");
		this.w.d();
		if (u.d) {
			km.a.b();
		}

		this.n.b("server gui refresh");

		for (int integer3 = 0; integer3 < this.l.size(); integer3++) {
			((Runnable)this.l.get(integer3)).run();
		}

		this.n.c();
	}

	public boolean B() {
		return true;
	}

	public void b(Runnable runnable) {
		this.l.add(runnable);
	}

	protected void b(String string) {
		this.ai = string;
	}

	public File c(String string) {
		return new File(this.A(), string);
	}

	public final zd D() {
		return (zd)this.v.get(bqb.g);
	}

	@Nullable
	public zd a(ug<bqb> ug) {
		return (zd)this.v.get(ug);
	}

	public Set<ug<bqb>> E() {
		return this.v.keySet();
	}

	public Iterable<zd> F() {
		return this.v.values();
	}

	public String G() {
		return u.a().getName();
	}

	public int H() {
		return this.w.m();
	}

	public int I() {
		return this.w.n();
	}

	public String[] J() {
		return this.w.e();
	}

	public String getServerModName() {
		return "vanilla";
	}

	public j b(j j) {
		if (this.w != null) {
			j.g().a("Player Count", (l<String>)(() -> this.w.m() + " / " + this.w.n() + "; " + this.w.s()));
		}

		j.g().a("Data Packs", (l<String>)(() -> {
			StringBuilder stringBuilder2 = new StringBuilder();

			for (aap aap4 : this.Z.e()) {
				if (stringBuilder2.length() > 0) {
					stringBuilder2.append(", ");
				}

				stringBuilder2.append(aap4.e());
				if (!aap4.c().a()) {
					stringBuilder2.append(" (incompatible)");
				}
			}

			return stringBuilder2.toString();
		}));
		if (this.ai != null) {
			j.g().a("Server Id", (l<String>)(() -> this.ai));
		}

		return j;
	}

	public abstract Optional<String> n();

	@Override
	public void a(mr mr, UUID uUID) {
		j.info(mr.getString());
	}

	public KeyPair K() {
		return this.H;
	}

	public int L() {
		return this.u;
	}

	public void a(int integer) {
		this.u = integer;
	}

	public String M() {
		return this.I;
	}

	public void d(String string) {
		this.I = string;
	}

	public boolean N() {
		return this.I != null;
	}

	public void a(KeyPair keyPair) {
		this.H = keyPair;
	}

	public void a(and and, boolean boolean2) {
		if (boolean2 || !this.i.s()) {
			this.i.a(this.i.m() ? and.HARD : and);
			this.ba();
			this.ac().s().forEach(this::a);
		}
	}

	public int b(int integer) {
		return integer;
	}

	private void ba() {
		for (zd zd3 : this.F()) {
			zd3.b(this.O(), this.V());
		}
	}

	public void b(boolean boolean1) {
		this.i.d(boolean1);
		this.ac().s().forEach(this::a);
	}

	private void a(ze ze) {
		dab dab3 = ze.u().u_();
		ze.b.a(new nz(dab3.r(), dab3.s()));
	}

	protected boolean O() {
		return this.i.r() != and.PEACEFUL;
	}

	public boolean P() {
		return this.J;
	}

	public void c(boolean boolean1) {
		this.J = boolean1;
	}

	public String Q() {
		return this.K;
	}

	public String R() {
		return this.L;
	}

	public void a(String string1, String string2) {
		this.K = string1;
		this.L = string2;
	}

	@Override
	public void a(ano ano) {
		ano.a("whitelist_enabled", false);
		ano.a("whitelist_count", 0);
		if (this.w != null) {
			ano.a("players_current", this.H());
			ano.a("players_max", this.I());
			ano.a("players_seen", this.e.a().length);
		}

		ano.a("uses_auth", this.A);
		ano.a("gui_state", this.af() ? "enabled" : "disabled");
		ano.a("run_time", (v.b() - ano.g()) / 60L * 1000L);
		ano.a("avg_tick_ms", (int)(aec.a(this.h) * 1.0E-6));
		int integer3 = 0;

		for (zd zd5 : this.F()) {
			if (zd5 != null) {
				ano.a("world[" + integer3 + "][dimension]", zd5.W().a());
				ano.a("world[" + integer3 + "][mode]", this.i.l());
				ano.a("world[" + integer3 + "][difficulty]", zd5.ac());
				ano.a("world[" + integer3 + "][hardcore]", this.i.m());
				ano.a("world[" + integer3 + "][height]", this.F);
				ano.a("world[" + integer3 + "][chunks_loaded]", zd5.i().h());
				integer3++;
			}
		}

		ano.a("worlds", integer3);
	}

	public abstract boolean j();

	public boolean T() {
		return this.A;
	}

	public void d(boolean boolean1) {
		this.A = boolean1;
	}

	public boolean U() {
		return this.B;
	}

	public void e(boolean boolean1) {
		this.B = boolean1;
	}

	public boolean V() {
		return true;
	}

	public boolean W() {
		return true;
	}

	public abstract boolean k();

	public boolean X() {
		return this.C;
	}

	public void f(boolean boolean1) {
		this.C = boolean1;
	}

	public boolean Y() {
		return this.D;
	}

	public void g(boolean boolean1) {
		this.D = boolean1;
	}

	public abstract boolean l();

	public String Z() {
		return this.E;
	}

	public void e(String string) {
		this.E = string;
	}

	public int aa() {
		return this.F;
	}

	public void c(int integer) {
		this.F = integer;
	}

	public boolean ab() {
		return this.y;
	}

	public abp ac() {
		return this.w;
	}

	public void a(abp abp) {
		this.w = abp;
	}

	public abstract boolean m();

	public void a(bpy bpy) {
		this.i.a(bpy);
	}

	@Nullable
	public zu ad() {
		return this.o;
	}

	public boolean af() {
		return false;
	}

	public abstract boolean a(bpy bpy, boolean boolean2, int integer);

	public int ag() {
		return this.z;
	}

	public int ai() {
		return 16;
	}

	public boolean a(zd zd, fu fu, bec bec) {
		return false;
	}

	public void h(boolean boolean1) {
		this.P = boolean1;
	}

	public boolean aj() {
		return this.P;
	}

	public boolean ak() {
		return true;
	}

	public int am() {
		return this.G;
	}

	public void d(int integer) {
		this.G = integer;
	}

	public MinecraftSessionService an() {
		return this.Q;
	}

	public GameProfileRepository ao() {
		return this.R;
	}

	public abl ap() {
		return this.S;
	}

	public tl aq() {
		return this.q;
	}

	public void ar() {
		this.T = 0L;
	}

	public int as() {
		return 29999984;
	}

	@Override
	public boolean at() {
		return super.at() && !this.ab();
	}

	@Override
	public Thread au() {
		return this.U;
	}

	public int av() {
		return 256;
	}

	public long aw() {
		return this.V;
	}

	public DataFixer ax() {
		return this.s;
	}

	public int a(@Nullable zd zd) {
		return zd != null ? zd.S().c(bpx.q) : 10;
	}

	public us ay() {
		return this.aj.g();
	}

	public uu az() {
		return this.ad;
	}

	public CompletableFuture<Void> a(Collection<String> collection) {
		CompletableFuture<Void> completableFuture3 = CompletableFuture.supplyAsync(
				() -> (ImmutableList)collection.stream().map(this.Z::a).filter(Objects::nonNull).map(aap::d).collect(ImmutableList.toImmutableList()), this
			)
			.thenCompose(immutableList -> uw.a(immutableList, this.j() ? da.a.DEDICATED : da.a.INTEGRATED, this.h(), this.ah, this))
			.thenAcceptAsync(uw -> {
				this.aj.close();
				this.aj = uw;
				this.Z.a(collection);
				this.i.a(a(this.Z));
				uw.i();
				this.ac().h();
				this.ac().t();
				this.ad.a(this.aj.a());
				this.ak.a(this.aj.h());
			}, this);
		if (this.bf()) {
			this.c(completableFuture3::isDone);
		}

		return completableFuture3;
	}

	public static bpn a(aar<aap> aar, bpn bpn, boolean boolean3) {
		aar.a();
		if (boolean3) {
			aar.a(Collections.singleton("vanilla"));
			return new bpn(ImmutableList.of("vanilla"), ImmutableList.of());
		} else {
			Set<String> set4 = Sets.<String>newLinkedHashSet();

			for (String string6 : bpn.a()) {
				if (aar.b(string6)) {
					set4.add(string6);
				} else {
					j.warn("Missing data pack {}", string6);
				}
			}

			for (aap aap6 : aar.c()) {
				String string7 = aap6.e();
				if (!bpn.b().contains(string7) && !set4.contains(string7)) {
					j.info("Found new data pack {}, loading it automatically", string7);
					set4.add(string7);
				}
			}

			if (set4.isEmpty()) {
				j.info("No datapacks selected, forcing vanilla");
				set4.add("vanilla");
			}

			aar.a(set4);
			return a(aar);
		}
	}

	private static bpn a(aar<?> aar) {
		Collection<String> collection2 = aar.d();
		List<String> list3 = ImmutableList.copyOf(collection2);
		List<String> list4 = (List<String>)aar.b().stream().filter(string -> !collection2.contains(string)).collect(ImmutableList.toImmutableList());
		return new bpn(list3, list4);
	}

	public void a(cz cz) {
		if (this.aL()) {
			abp abp3 = cz.j().ac();
			abw abw4 = abp3.i();

			for (ze ze7 : Lists.newArrayList(abp3.s())) {
				if (!abw4.a(ze7.ez())) {
					ze7.b.b(new ne("multiplayer.disconnect.not_whitelisted"));
				}
			}
		}
	}

	public aar<aap> aA() {
		return this.Z;
	}

	public da aB() {
		return this.aj.f();
	}

	public cz aC() {
		zd zd2 = this.D();
		return new cz(this, zd2 == null ? dem.a : dem.b(zd2.u()), del.a, zd2, 4, "Server", new nd("Server"), this, null);
	}

	@Override
	public boolean a() {
		return true;
	}

	@Override
	public boolean b() {
		return true;
	}

	public bmv aD() {
		return this.aj.e();
	}

	public adh aE() {
		return this.aj.d();
	}

	public ux aF() {
		return this.aa;
	}

	public czy aG() {
		if (this.ab == null) {
			throw new NullPointerException("Called before server init");
		} else {
			return this.ab;
		}
	}

	public dax aH() {
		return this.aj.c();
	}

	public day aI() {
		return this.aj.b();
	}

	public bpx aJ() {
		return this.D().S();
	}

	public va aK() {
		return this.ac;
	}

	public boolean aL() {
		return this.af;
	}

	public void i(boolean boolean1) {
		this.af = boolean1;
	}

	public float aM() {
		return this.ag;
	}

	public int b(GameProfile gameProfile) {
		if (this.ac().h(gameProfile)) {
			abr abr3 = this.ac().k().b(gameProfile);
			if (abr3 != null) {
				return abr3.a();
			} else if (this.a(gameProfile)) {
				return 4;
			} else if (this.N()) {
				return this.ac().u() ? 4 : 0;
			} else {
				return this.g();
			}
		} else {
			return 0;
		}
	}

	public ami aO() {
		return this.n;
	}

	public abstract boolean a(GameProfile gameProfile);

	public void a(Path path) throws IOException {
		Path path3 = path.resolve("levels");

		for (Entry<ug<bqb>, zd> entry5 : this.v.entrySet()) {
			uh uh6 = ((ug)entry5.getKey()).a();
			Path path7 = path3.resolve(uh6.b()).resolve(uh6.a());
			Files.createDirectories(path7);
			((zd)entry5.getValue()).a(path7);
		}

		this.d(path.resolve("gamerules.txt"));
		this.e(path.resolve("classpath.txt"));
		this.c(path.resolve("example_crash.txt"));
		this.b(path.resolve("stats.txt"));
		this.f(path.resolve("threads.txt"));
	}

	private void b(Path path) throws IOException {
		Writer writer3 = Files.newBufferedWriter(path);
		Throwable var3 = null;

		try {
			writer3.write(String.format("pending_tasks: %d\n", this.bg()));
			writer3.write(String.format("average_tick_time: %f\n", this.aM()));
			writer3.write(String.format("tick_times: %s\n", Arrays.toString(this.h)));
			writer3.write(String.format("queue: %s\n", v.f()));
		} catch (Throwable var12) {
			var3 = var12;
			throw var12;
		} finally {
			if (writer3 != null) {
				if (var3 != null) {
					try {
						writer3.close();
					} catch (Throwable var11) {
						var3.addSuppressed(var11);
					}
				} else {
					writer3.close();
				}
			}
		}
	}

	private void c(Path path) throws IOException {
		j j3 = new j("Server dump", new Exception("dummy"));
		this.b(j3);
		Writer writer4 = Files.newBufferedWriter(path);
		Throwable var4 = null;

		try {
			writer4.write(j3.e());
		} catch (Throwable var13) {
			var4 = var13;
			throw var13;
		} finally {
			if (writer4 != null) {
				if (var4 != null) {
					try {
						writer4.close();
					} catch (Throwable var12) {
						var4.addSuppressed(var12);
					}
				} else {
					writer4.close();
				}
			}
		}
	}

	private void d(Path path) throws IOException {
		Writer writer3 = Files.newBufferedWriter(path);
		Throwable var3 = null;

		try {
			final List<String> list5 = Lists.<String>newArrayList();
			final bpx bpx6 = this.aJ();
			bpx.a(new bpx.c() {
				@Override
				public <T extends bpx.g<T>> void a(bpx.e<T> e, f<T> f) {
					list5.add(String.format("%s=%s\n", e.a(), bpx6.<T>a(e).toString()));
				}
			});

			for (String string8 : list5) {
				writer3.write(string8);
			}
		} catch (Throwable var15) {
			var3 = var15;
			throw var15;
		} finally {
			if (writer3 != null) {
				if (var3 != null) {
					try {
						writer3.close();
					} catch (Throwable var14) {
						var3.addSuppressed(var14);
					}
				} else {
					writer3.close();
				}
			}
		}
	}

	private void e(Path path) throws IOException {
		Writer writer3 = Files.newBufferedWriter(path);
		Throwable var3 = null;

		try {
			String string5 = System.getProperty("java.class.path");
			String string6 = System.getProperty("path.separator");

			for (String string8 : Splitter.on(string6).split(string5)) {
				writer3.write(string8);
				writer3.write("\n");
			}
		} catch (Throwable var15) {
			var3 = var15;
			throw var15;
		} finally {
			if (writer3 != null) {
				if (var3 != null) {
					try {
						writer3.close();
					} catch (Throwable var14) {
						var3.addSuppressed(var14);
					}
				} else {
					writer3.close();
				}
			}
		}
	}

	private void f(Path path) throws IOException {
		ThreadMXBean threadMXBean3 = ManagementFactory.getThreadMXBean();
		ThreadInfo[] arr4 = threadMXBean3.dumpAllThreads(true, true);
		Arrays.sort(arr4, Comparator.comparing(ThreadInfo::getThreadName));
		Writer writer5 = Files.newBufferedWriter(path);
		Throwable var5 = null;

		try {
			for (ThreadInfo threadInfo10 : arr4) {
				writer5.write(threadInfo10.toString());
				writer5.write(10);
			}
		} catch (Throwable var17) {
			var5 = var17;
			throw var17;
		} finally {
			if (writer5 != null) {
				if (var5 != null) {
					try {
						writer5.close();
					} catch (Throwable var16) {
						var5.addSuppressed(var16);
					}
				} else {
					writer5.close();
				}
			}
		}
	}

	private void a(@Nullable aml aml) {
		if (this.O) {
			this.O = false;
			this.m.c();
		}

		this.n = aml.a(this.m.d(), aml);
	}

	private void b(@Nullable aml aml) {
		if (aml != null) {
			aml.b();
		}

		this.n = this.m.d();
	}

	public boolean aQ() {
		return this.m.a();
	}

	public void aR() {
		this.O = true;
	}

	public amh aS() {
		amh amh2 = this.m.e();
		this.m.b();
		return amh2;
	}

	public Path a(dac dac) {
		return this.d.a(dac);
	}

	public boolean aT() {
		return true;
	}

	public cva aU() {
		return this.ak;
	}

	public dal aV() {
		return this.i;
	}
}
