import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class daj implements dak, dal {
	private static final Logger a = LogManager.getLogger();
	private bqe b;
	private final cix c;
	private final Lifecycle d;
	private int e;
	private int f;
	private int g;
	private long h;
	private long i;
	@Nullable
	private final DataFixer j;
	private final int k;
	private boolean l;
	@Nullable
	private le m;
	private final int n;
	private int o;
	private boolean p;
	private int q;
	private boolean r;
	private int s;
	private boolean t;
	private boolean u;
	private cgw.c v;
	private le w;
	@Nullable
	private le x;
	private int y;
	private int z;
	@Nullable
	private UUID A;
	private final Set<String> B;
	private boolean C;
	private final ded<MinecraftServer> D;

	private daj(
		@Nullable DataFixer dataFixer,
		int integer2,
		@Nullable le le3,
		boolean boolean4,
		int integer5,
		int integer6,
		int integer7,
		long long8,
		long long9,
		int integer10,
		int integer11,
		int integer12,
		boolean boolean13,
		int integer14,
		boolean boolean15,
		boolean boolean16,
		boolean boolean17,
		cgw.c c,
		int integer19,
		int integer20,
		@Nullable UUID uUID,
		LinkedHashSet<String> linkedHashSet,
		ded<MinecraftServer> ded,
		@Nullable le le24,
		le le25,
		bqe bqe,
		cix cix,
		Lifecycle lifecycle
	) {
		this.j = dataFixer;
		this.C = boolean4;
		this.e = integer5;
		this.f = integer6;
		this.g = integer7;
		this.h = long8;
		this.i = long9;
		this.n = integer10;
		this.o = integer11;
		this.q = integer12;
		this.p = boolean13;
		this.s = integer14;
		this.r = boolean15;
		this.t = boolean16;
		this.u = boolean17;
		this.v = c;
		this.y = integer19;
		this.z = integer20;
		this.A = uUID;
		this.B = linkedHashSet;
		this.m = le3;
		this.k = integer2;
		this.D = ded;
		this.x = le24;
		this.w = le25;
		this.b = bqe;
		this.c = cix;
		this.d = lifecycle;
	}

	public daj(bqe bqe, cix cix, Lifecycle lifecycle) {
		this(
			null,
			u.a().getWorldVersion(),
			null,
			false,
			0,
			0,
			0,
			0L,
			0L,
			19133,
			0,
			0,
			false,
			0,
			false,
			false,
			false,
			cgw.b,
			0,
			0,
			null,
			Sets.newLinkedHashSet(),
			new ded<>(dec.a),
			null,
			new le(),
			bqe.h(),
			cix,
			lifecycle
		);
	}

	public static daj a(Dynamic<lu> dynamic, DataFixer dataFixer, int integer, @Nullable le le, bqe bqe, dag dag, cix cix, Lifecycle lifecycle) {
		long long9 = dynamic.get("Time").asLong(0L);
		le le11 = (le)dynamic.get("DragonFight")
			.result()
			.map(Dynamic::getValue)
			.orElseGet(() -> dynamic.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue());
		return new daj(
			dataFixer,
			integer,
			le,
			dynamic.get("WasModded").asBoolean(false),
			dynamic.get("SpawnX").asInt(0),
			dynamic.get("SpawnY").asInt(0),
			dynamic.get("SpawnZ").asInt(0),
			long9,
			dynamic.get("DayTime").asLong(long9),
			dag.a(),
			dynamic.get("clearWeatherTime").asInt(0),
			dynamic.get("rainTime").asInt(0),
			dynamic.get("raining").asBoolean(false),
			dynamic.get("thunderTime").asInt(0),
			dynamic.get("thundering").asBoolean(false),
			dynamic.get("initialized").asBoolean(true),
			dynamic.get("DifficultyLocked").asBoolean(false),
			cgw.c.a(dynamic, cgw.b),
			dynamic.get("WanderingTraderSpawnDelay").asInt(0),
			dynamic.get("WanderingTraderSpawnChance").asInt(0),
			(UUID)dynamic.get("WanderingTraderId").read(gp.a).result().orElse(null),
			(LinkedHashSet<String>)dynamic.get("ServerBrands")
				.asStream()
				.flatMap(dynamicx -> v.a(dynamicx.asString().result()))
				.collect(Collectors.toCollection(Sets::newLinkedHashSet)),
			new ded<>(dec.a, dynamic.get("ScheduledEvents").asStream()),
			(le)dynamic.get("CustomBossEvents").orElseEmptyMap().getValue(),
			le11,
			bqe,
			cix,
			lifecycle
		);
	}

	@Override
	public le a(gm gm, @Nullable le le) {
		this.I();
		if (le == null) {
			le = this.m;
		}

		le le4 = new le();
		this.a(gm, le4, le);
		return le4;
	}

	private void a(gm gm, le le2, @Nullable le le3) {
		lk lk5 = new lk();
		this.B.stream().map(lt::a).forEach(lk5::add);
		le2.a("ServerBrands", lk5);
		le2.a("WasModded", this.C);
		le le6 = new le();
		le6.a("Name", u.a().getName());
		le6.b("Id", u.a().getWorldVersion());
		le6.a("Snapshot", !u.a().isStable());
		le2.a("Version", le6);
		le2.b("DataVersion", u.a().getWorldVersion());
		uf<lu> uf7 = uf.a(lp.a, gm);
		cix.a.encodeStart(uf7, this.c).resultOrPartial(v.a("WorldGenSettings: ", a::error)).ifPresent(lu -> le2.a("WorldGenSettings", lu));
		le2.b("GameType", this.b.b().a());
		le2.b("SpawnX", this.e);
		le2.b("SpawnY", this.f);
		le2.b("SpawnZ", this.g);
		le2.a("Time", this.h);
		le2.a("DayTime", this.i);
		le2.a("LastPlayed", v.d());
		le2.a("LevelName", this.b.a());
		le2.b("version", 19133);
		le2.b("clearWeatherTime", this.o);
		le2.b("rainTime", this.q);
		le2.a("raining", this.p);
		le2.b("thunderTime", this.s);
		le2.a("thundering", this.r);
		le2.a("hardcore", this.b.c());
		le2.a("allowCommands", this.b.e());
		le2.a("initialized", this.t);
		this.v.a(le2);
		le2.a("Difficulty", (byte)this.b.d().a());
		le2.a("DifficultyLocked", this.u);
		le2.a("GameRules", this.b.f().a());
		le2.a("DragonFight", this.w);
		if (le3 != null) {
			le2.a("Player", le3);
		}

		bpn.b.encodeStart(lp.a, this.b.g()).result().ifPresent(lu -> le2.a("DataPacks", lu));
		if (this.x != null) {
			le2.a("CustomBossEvents", this.x);
		}

		le2.a("ScheduledEvents", this.D.b());
		le2.b("WanderingTraderSpawnDelay", this.y);
		le2.b("WanderingTraderSpawnChance", this.z);
		if (this.A != null) {
			le2.a("WanderingTraderId", this.A);
		}
	}

	@Override
	public int a() {
		return this.e;
	}

	@Override
	public int b() {
		return this.f;
	}

	@Override
	public int c() {
		return this.g;
	}

	@Override
	public long d() {
		return this.h;
	}

	@Override
	public long e() {
		return this.i;
	}

	private void I() {
		if (!this.l && this.m != null) {
			if (this.k < u.a().getWorldVersion()) {
				if (this.j == null) {
					throw (NullPointerException)v.c(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
				}

				this.m = lq.a(this.j, aeo.PLAYER, this.m, this.k);
			}

			this.l = true;
		}
	}

	@Override
	public le x() {
		this.I();
		return this.m;
	}

	@Override
	public void b(int integer) {
		this.e = integer;
	}

	@Override
	public void c(int integer) {
		this.f = integer;
	}

	@Override
	public void d(int integer) {
		this.g = integer;
	}

	@Override
	public void a(long long1) {
		this.h = long1;
	}

	@Override
	public void b(long long1) {
		this.i = long1;
	}

	@Override
	public void a(fu fu) {
		this.e = fu.u();
		this.f = fu.v();
		this.g = fu.w();
	}

	@Override
	public String f() {
		return this.b.a();
	}

	@Override
	public int y() {
		return this.n;
	}

	@Override
	public int g() {
		return this.o;
	}

	@Override
	public void a(int integer) {
		this.o = integer;
	}

	@Override
	public boolean h() {
		return this.r;
	}

	@Override
	public void a(boolean boolean1) {
		this.r = boolean1;
	}

	@Override
	public int i() {
		return this.s;
	}

	@Override
	public void e(int integer) {
		this.s = integer;
	}

	@Override
	public boolean j() {
		return this.p;
	}

	@Override
	public void b(boolean boolean1) {
		this.p = boolean1;
	}

	@Override
	public int k() {
		return this.q;
	}

	@Override
	public void f(int integer) {
		this.q = integer;
	}

	@Override
	public bpy l() {
		return this.b.b();
	}

	@Override
	public void a(bpy bpy) {
		this.b = this.b.a(bpy);
	}

	@Override
	public boolean m() {
		return this.b.c();
	}

	@Override
	public boolean n() {
		return this.b.e();
	}

	@Override
	public boolean o() {
		return this.t;
	}

	@Override
	public void c(boolean boolean1) {
		this.t = boolean1;
	}

	@Override
	public bpx p() {
		return this.b.f();
	}

	@Override
	public cgw.c q() {
		return this.v;
	}

	@Override
	public void a(cgw.c c) {
		this.v = c;
	}

	@Override
	public and r() {
		return this.b.d();
	}

	@Override
	public void a(and and) {
		this.b = this.b.a(and);
	}

	@Override
	public boolean s() {
		return this.u;
	}

	@Override
	public void d(boolean boolean1) {
		this.u = boolean1;
	}

	@Override
	public ded<MinecraftServer> t() {
		return this.D;
	}

	@Override
	public void a(k k) {
		dak.super.a(k);
		dal.super.a(k);
	}

	@Override
	public cix z() {
		return this.c;
	}

	@Override
	public le B() {
		return this.w;
	}

	@Override
	public void a(le le) {
		this.w = le;
	}

	@Override
	public bpn C() {
		return this.b.g();
	}

	@Override
	public void a(bpn bpn) {
		this.b = this.b.a(bpn);
	}

	@Nullable
	@Override
	public le D() {
		return this.x;
	}

	@Override
	public void b(@Nullable le le) {
		this.x = le;
	}

	@Override
	public int u() {
		return this.y;
	}

	@Override
	public void g(int integer) {
		this.y = integer;
	}

	@Override
	public int v() {
		return this.z;
	}

	@Override
	public void h(int integer) {
		this.z = integer;
	}

	@Override
	public void a(UUID uUID) {
		this.A = uUID;
	}

	@Override
	public void a(String string, boolean boolean2) {
		this.B.add(string);
		this.C |= boolean2;
	}

	@Override
	public boolean E() {
		return this.C;
	}

	@Override
	public Set<String> F() {
		return ImmutableSet.copyOf(this.B);
	}

	@Override
	public dak G() {
		return this;
	}
}
