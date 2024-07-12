import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

public abstract class beg extends bes {
	private static final tq<Byte> f = tt.a(beg.class, ts.a);
	private static final tq<Byte> g = tt.a(beg.class, ts.a);
	@Nullable
	private cfj an;
	protected boolean b;
	protected int c;
	public beg.a d = beg.a.DISALLOWED;
	public int e;
	private int ao;
	private double ap = 2.0;
	private int aq;
	private ack ar = this.i();
	private IntOpenHashSet as;
	private List<aom> at;

	protected beg(aoq<? extends beg> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	protected beg(aoq<? extends beg> aoq, double double2, double double3, double double4, bqb bqb) {
		this(aoq, bqb);
		this.d(double2, double3, double4);
	}

	protected beg(aoq<? extends beg> aoq, aoy aoy, bqb bqb) {
		this(aoq, aoy.cC(), aoy.cF() - 0.1F, aoy.cG(), bqb);
		this.b(aoy);
		if (aoy instanceof bec) {
			this.d = beg.a.ALLOWED;
		}
	}

	public void a(ack ack) {
		this.ar = ack;
	}

	@Override
	protected void e() {
		this.S.a(f, (byte)0);
		this.S.a(g, (byte)0);
	}

	@Override
	public void c(double double1, double double2, double double3, float float4, float float5) {
		super.c(double1, double2, double3, float4, float5);
		this.ao = 0;
	}

	@Override
	public void j() {
		super.j();
		boolean boolean2 = this.t();
		dem dem3 = this.cB();
		if (this.s == 0.0F && this.r == 0.0F) {
			float float4 = aec.a(b(dem3));
			this.p = (float)(aec.d(dem3.b, dem3.d) * 180.0F / (float)Math.PI);
			this.q = (float)(aec.d(dem3.c, (double)float4) * 180.0F / (float)Math.PI);
			this.r = this.p;
			this.s = this.q;
		}

		fu fu4 = this.cA();
		cfj cfj5 = this.l.d_(fu4);
		if (!cfj5.g() && !boolean2) {
			dfg dfg6 = cfj5.k(this.l, fu4);
			if (!dfg6.b()) {
				dem dem7 = this.cz();

				for (deg deg9 : dfg6.d()) {
					if (deg9.a(fu4).d(dem7)) {
						this.b = true;
						break;
					}
				}
			}
		}

		if (this.e > 0) {
			this.e--;
		}

		if (this.aB()) {
			this.ah();
		}

		if (this.b && !boolean2) {
			if (this.an != cfj5 && this.u()) {
				this.z();
			} else if (!this.l.v) {
				this.h();
			}

			this.c++;
		} else {
			this.c = 0;
			dem dem6 = this.cz();
			dem dem7 = dem6.e(dem3);
			dej dej8 = this.l.a(new bpj(dem6, dem7, bpj.a.COLLIDER, bpj.b.NONE, this));
			if (dej8.c() != dej.a.MISS) {
				dem7 = dej8.e();
			}

			while (!this.y) {
				dei dei9 = this.a(dem6, dem7);
				if (dei9 != null) {
					dej8 = dei9;
				}

				if (dej8 != null && dej8.c() == dej.a.ENTITY) {
					aom aom10 = ((dei)dej8).a();
					aom aom11 = this.v();
					if (aom10 instanceof bec && aom11 instanceof bec && !((bec)aom11).a((bec)aom10)) {
						dej8 = null;
						dei9 = null;
					}
				}

				if (dej8 != null && !boolean2) {
					this.a(dej8);
					this.ad = true;
				}

				if (dei9 == null || this.r() <= 0) {
					break;
				}

				dej8 = null;
			}

			dem3 = this.cB();
			double double9 = dem3.b;
			double double11 = dem3.c;
			double double13 = dem3.d;
			if (this.p()) {
				for (int integer15 = 0; integer15 < 4; integer15++) {
					this.l
						.a(
							hh.g,
							this.cC() + double9 * (double)integer15 / 4.0,
							this.cD() + double11 * (double)integer15 / 4.0,
							this.cG() + double13 * (double)integer15 / 4.0,
							-double9,
							-double11 + 0.2,
							-double13
						);
				}
			}

			double double15 = this.cC() + double9;
			double double17 = this.cD() + double11;
			double double19 = this.cG() + double13;
			float float21 = aec.a(b(dem3));
			if (boolean2) {
				this.p = (float)(aec.d(-double9, -double13) * 180.0F / (float)Math.PI);
			} else {
				this.p = (float)(aec.d(double9, double13) * 180.0F / (float)Math.PI);
			}

			this.q = (float)(aec.d(double11, (double)float21) * 180.0F / (float)Math.PI);
			this.q = e(this.s, this.q);
			this.p = e(this.r, this.p);
			float float22 = 0.99F;
			float float23 = 0.05F;
			if (this.aA()) {
				for (int integer24 = 0; integer24 < 4; integer24++) {
					float float25 = 0.25F;
					this.l.a(hh.e, double15 - double9 * 0.25, double17 - double11 * 0.25, double19 - double13 * 0.25, double9, double11, double13);
				}

				float22 = this.s();
			}

			this.e(dem3.a((double)float22));
			if (!this.aw() && !boolean2) {
				dem dem24 = this.cB();
				this.m(dem24.b, dem24.c - 0.05F, dem24.d);
			}

			this.d(double15, double17, double19);
			this.at();
		}
	}

	private boolean u() {
		return this.b && this.l.b(new deg(this.cz(), this.cz()).g(0.06));
	}

	private void z() {
		this.b = false;
		dem dem2 = this.cB();
		this.e(dem2.d((double)(this.J.nextFloat() * 0.2F), (double)(this.J.nextFloat() * 0.2F), (double)(this.J.nextFloat() * 0.2F)));
		this.ao = 0;
	}

	@Override
	public void a(apd apd, dem dem) {
		super.a(apd, dem);
		if (apd != apd.SELF && this.u()) {
			this.z();
		}
	}

	protected void h() {
		this.ao++;
		if (this.ao >= 1200) {
			this.aa();
		}
	}

	private void A() {
		if (this.at != null) {
			this.at.clear();
		}

		if (this.as != null) {
			this.as.clear();
		}
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		aom aom3 = dei.a();
		float float4 = (float)this.cB().f();
		int integer5 = aec.f(aec.a((double)float4 * this.ap, 0.0, 2.147483647E9));
		if (this.r() > 0) {
			if (this.as == null) {
				this.as = new IntOpenHashSet(5);
			}

			if (this.at == null) {
				this.at = Lists.<aom>newArrayListWithCapacity(5);
			}

			if (this.as.size() >= this.r() + 1) {
				this.aa();
				return;
			}

			this.as.add(aom3.V());
		}

		if (this.p()) {
			long long6 = (long)this.J.nextInt(integer5 / 2 + 2);
			integer5 = (int)Math.min(long6 + (long)integer5, 2147483647L);
		}

		aom aom7 = this.v();
		anw anw6;
		if (aom7 == null) {
			anw6 = anw.a(this, this);
		} else {
			anw6 = anw.a(this, aom7);
			if (aom7 instanceof aoy) {
				((aoy)aom7).z(aom3);
			}
		}

		boolean boolean8 = aom3.U() == aoq.u;
		int integer9 = aom3.ag();
		if (this.bm() && !boolean8) {
			aom3.f(5);
		}

		if (aom3.a(anw6, (float)integer5)) {
			if (boolean8) {
				return;
			}

			if (aom3 instanceof aoy) {
				aoy aoy10 = (aoy)aom3;
				if (!this.l.v && this.r() <= 0) {
					aoy10.p(aoy10.dx() + 1);
				}

				if (this.aq > 0) {
					dem dem11 = this.cB().d(1.0, 0.0, 1.0).d().a((double)this.aq * 0.6);
					if (dem11.g() > 0.0) {
						aoy10.h(dem11.b, 0.1, dem11.d);
					}
				}

				if (!this.l.v && aom7 instanceof aoy) {
					bny.a(aoy10, aom7);
					bny.b((aoy)aom7, aoy10);
				}

				this.a(aoy10);
				if (aom7 != null && aoy10 != aom7 && aoy10 instanceof bec && aom7 instanceof ze && !this.av()) {
					((ze)aom7).b.a(new oq(oq.g, 0.0F));
				}

				if (!aom3.aU() && this.at != null) {
					this.at.add(aoy10);
				}

				if (!this.l.v && aom7 instanceof ze) {
					ze ze11 = (ze)aom7;
					if (this.at != null && this.q()) {
						aa.G.a(ze11, this.at);
					} else if (!aom3.aU() && this.q()) {
						aa.G.a(ze11, Arrays.asList(aom3));
					}
				}
			}

			this.a(this.ar, 1.0F, 1.2F / (this.J.nextFloat() * 0.2F + 0.9F));
			if (this.r() <= 0) {
				this.aa();
			}
		} else {
			aom3.g(integer9);
			this.e(this.cB().a(-0.1));
			this.p += 180.0F;
			this.r += 180.0F;
			if (!this.l.v && this.cB().g() < 1.0E-7) {
				if (this.d == beg.a.ALLOWED) {
					this.a(this.m(), 0.1F);
				}

				this.aa();
			}
		}
	}

	@Override
	protected void a(deh deh) {
		this.an = this.l.d_(deh.a());
		super.a(deh);
		dem dem3 = deh.e().a(this.cC(), this.cD(), this.cG());
		this.e(dem3);
		dem dem4 = dem3.d().a(0.05F);
		this.n(this.cC() - dem4.b, this.cD() - dem4.c, this.cG() - dem4.d);
		this.a(this.k(), 1.0F, 1.2F / (this.J.nextFloat() * 0.2F + 0.9F));
		this.b = true;
		this.e = 7;
		this.a(false);
		this.b((byte)0);
		this.a(acl.W);
		this.p(false);
		this.A();
	}

	protected ack i() {
		return acl.W;
	}

	protected final ack k() {
		return this.ar;
	}

	protected void a(aoy aoy) {
	}

	@Nullable
	protected dei a(dem dem1, dem dem2) {
		return bet.a(this.l, this, dem1, dem2, this.cb().b(this.cB()).g(1.0), this::a);
	}

	@Override
	protected boolean a(aom aom) {
		return super.a(aom) && (this.as == null || !this.as.contains(aom.V()));
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("life", (short)this.ao);
		if (this.an != null) {
			le.a("inBlockState", lq.a(this.an));
		}

		le.a("shake", (byte)this.e);
		le.a("inGround", this.b);
		le.a("pickup", (byte)this.d.ordinal());
		le.a("damage", this.ap);
		le.a("crit", this.p());
		le.a("PierceLevel", this.r());
		le.a("SoundEvent", gl.ag.b(this.ar).toString());
		le.a("ShotFromCrossbow", this.q());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.ao = le.g("life");
		if (le.c("inBlockState", 10)) {
			this.an = lq.c(le.p("inBlockState"));
		}

		this.e = le.f("shake") & 255;
		this.b = le.q("inGround");
		if (le.c("damage", 99)) {
			this.ap = le.k("damage");
		}

		if (le.c("pickup", 99)) {
			this.d = beg.a.a(le.f("pickup"));
		} else if (le.c("player", 99)) {
			this.d = le.q("player") ? beg.a.ALLOWED : beg.a.DISALLOWED;
		}

		this.a(le.q("crit"));
		this.b(le.f("PierceLevel"));
		if (le.c("SoundEvent", 8)) {
			this.ar = (ack)gl.ag.b(new uh(le.l("SoundEvent"))).orElse(this.i());
		}

		this.p(le.q("ShotFromCrossbow"));
	}

	@Override
	public void b(@Nullable aom aom) {
		super.b(aom);
		if (aom instanceof bec) {
			this.d = ((bec)aom).bJ.d ? beg.a.CREATIVE_ONLY : beg.a.ALLOWED;
		}
	}

	@Override
	public void a_(bec bec) {
		if (!this.l.v && (this.b || this.t()) && this.e <= 0) {
			boolean boolean3 = this.d == beg.a.ALLOWED || this.d == beg.a.CREATIVE_ONLY && bec.bJ.d || this.t() && this.v().bR() == bec.bR();
			if (this.d == beg.a.ALLOWED && !bec.bt.e(this.m())) {
				boolean3 = false;
			}

			if (boolean3) {
				bec.a(this, 1);
				this.aa();
			}
		}
	}

	protected abstract bki m();

	@Override
	protected boolean ax() {
		return false;
	}

	public void h(double double1) {
		this.ap = double1;
	}

	public double n() {
		return this.ap;
	}

	public void a(int integer) {
		this.aq = integer;
	}

	@Override
	public boolean bH() {
		return false;
	}

	@Override
	protected float a(apj apj, aon aon) {
		return 0.13F;
	}

	public void a(boolean boolean1) {
		this.a(1, boolean1);
	}

	public void b(byte byte1) {
		this.S.b(g, byte1);
	}

	private void a(int integer, boolean boolean2) {
		byte byte4 = this.S.a(f);
		if (boolean2) {
			this.S.b(f, (byte)(byte4 | integer));
		} else {
			this.S.b(f, (byte)(byte4 & ~integer));
		}
	}

	public boolean p() {
		byte byte2 = this.S.a(f);
		return (byte2 & 1) != 0;
	}

	public boolean q() {
		byte byte2 = this.S.a(f);
		return (byte2 & 4) != 0;
	}

	public byte r() {
		return this.S.a(g);
	}

	public void a(aoy aoy, float float2) {
		int integer4 = bny.a(boa.x, aoy);
		int integer5 = bny.a(boa.y, aoy);
		this.h((double)(float2 * 2.0F) + this.J.nextGaussian() * 0.25 + (double)((float)this.l.ac().a() * 0.11F));
		if (integer4 > 0) {
			this.h(this.n() + (double)integer4 * 0.5 + 0.5);
		}

		if (integer5 > 0) {
			this.a(integer5);
		}

		if (bny.a(boa.z, aoy) > 0) {
			this.f(100);
		}
	}

	protected float s() {
		return 0.6F;
	}

	public void o(boolean boolean1) {
		this.H = boolean1;
		this.a(2, boolean1);
	}

	public boolean t() {
		return !this.l.v ? this.H : (this.S.a(f) & 2) != 0;
	}

	public void p(boolean boolean1) {
		this.a(4, boolean1);
	}

	@Override
	public ni<?> O() {
		aom aom2 = this.v();
		return new nm(this, aom2 == null ? 0 : aom2.V());
	}

	public static enum a {
		DISALLOWED,
		ALLOWED,
		CREATIVE_ONLY;

		public static beg.a a(int integer) {
			if (integer < 0 || integer > values().length) {
				integer = 0;
			}

			return values()[integer];
		}
	}
}
