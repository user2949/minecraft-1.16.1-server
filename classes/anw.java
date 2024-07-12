import javax.annotation.Nullable;

public class anw {
	public static final anw a = new anw("inFire").l().o();
	public static final anw b = new anw("lightningBolt");
	public static final anw c = new anw("onFire").l().o();
	public static final anw d = new anw("lava").o();
	public static final anw e = new anw("hotFloor").o();
	public static final anw f = new anw("inWall").l();
	public static final anw g = new anw("cramming").l();
	public static final anw h = new anw("drown").l();
	public static final anw i = new anw("starve").l().n();
	public static final anw j = new anw("cactus");
	public static final anw k = new anw("fall").l();
	public static final anw l = new anw("flyIntoWall").l();
	public static final anw m = new anw("outOfWorld").l().m();
	public static final anw n = new anw("generic").l();
	public static final anw o = new anw("magic").l().u();
	public static final anw p = new anw("wither").l();
	public static final anw q = new anw("anvil");
	public static final anw r = new anw("fallingBlock");
	public static final anw s = new anw("dragonBreath").l();
	public static final anw t = new anw("dryout");
	public static final anw u = new anw("sweetBerryBush");
	private boolean w;
	private boolean x;
	private boolean y;
	private float z = 0.1F;
	private boolean A;
	private boolean B;
	private boolean C;
	private boolean D;
	private boolean E;
	public final String v;

	public static anw b(aoy aoy) {
		return new anx("sting", aoy);
	}

	public static anw c(aoy aoy) {
		return new anx("mob", aoy);
	}

	public static anw a(aom aom, aoy aoy) {
		return new any("mob", aom, aoy);
	}

	public static anw a(bec bec) {
		return new anx("player", bec);
	}

	public static anw a(beg beg, @Nullable aom aom) {
		return new any("arrow", beg, aom).c();
	}

	public static anw a(aom aom1, @Nullable aom aom2) {
		return new any("trident", aom1, aom2).c();
	}

	public static anw a(ben ben, @Nullable aom aom) {
		return new any("fireworks", ben, aom).e();
	}

	public static anw a(bem bem, @Nullable aom aom) {
		return aom == null ? new any("onFire", bem, bem).o().c() : new any("fireball", bem, aom).o().c();
	}

	public static anw a(bff bff, aom aom) {
		return new any("witherSkull", bff, aom).c();
	}

	public static anw b(aom aom1, @Nullable aom aom2) {
		return new any("thrown", aom1, aom2).c();
	}

	public static anw c(aom aom1, @Nullable aom aom2) {
		return new any("indirectMagic", aom1, aom2).l().u();
	}

	public static anw a(aom aom) {
		return new anx("thorns", aom).x().u();
	}

	public static anw a(@Nullable bpt bpt) {
		return d(bpt != null ? bpt.d() : null);
	}

	public static anw d(@Nullable aoy aoy) {
		return aoy != null ? new anx("explosion.player", aoy).r().e() : new anw("explosion").r().e();
	}

	public static anw a() {
		return new ans();
	}

	public String toString() {
		return "DamageSource (" + this.v + ")";
	}

	public boolean b() {
		return this.B;
	}

	public anw c() {
		this.B = true;
		return this;
	}

	public boolean d() {
		return this.E;
	}

	public anw e() {
		this.E = true;
		return this;
	}

	public boolean f() {
		return this.w;
	}

	public float g() {
		return this.z;
	}

	public boolean h() {
		return this.x;
	}

	public boolean i() {
		return this.y;
	}

	protected anw(String string) {
		this.v = string;
	}

	@Nullable
	public aom j() {
		return this.k();
	}

	@Nullable
	public aom k() {
		return null;
	}

	protected anw l() {
		this.w = true;
		this.z = 0.0F;
		return this;
	}

	protected anw m() {
		this.x = true;
		return this;
	}

	protected anw n() {
		this.y = true;
		this.z = 0.0F;
		return this;
	}

	protected anw o() {
		this.A = true;
		return this;
	}

	public mr a(aoy aoy) {
		aoy aoy3 = aoy.dv();
		String string4 = "death.attack." + this.v;
		String string5 = string4 + ".player";
		return aoy3 != null ? new ne(string5, aoy.d(), aoy3.d()) : new ne(string4, aoy.d());
	}

	public boolean p() {
		return this.A;
	}

	public String q() {
		return this.v;
	}

	public anw r() {
		this.C = true;
		return this;
	}

	public boolean s() {
		return this.C;
	}

	public boolean t() {
		return this.D;
	}

	public anw u() {
		this.D = true;
		return this;
	}

	public boolean v() {
		aom aom2 = this.k();
		return aom2 instanceof bec && ((bec)aom2).bJ.d;
	}

	@Nullable
	public dem w() {
		return null;
	}
}
