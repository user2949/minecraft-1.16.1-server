import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class aom implements ank, cy {
	protected static final Logger h = LogManager.getLogger();
	private static final AtomicInteger b = new AtomicInteger();
	private static final List<bki> c = Collections.emptyList();
	private static final deg d = new deg(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	private static double e = 1.0;
	private final aoq<?> f;
	private int g = b.incrementAndGet();
	public boolean i;
	private final List<aom> an = Lists.<aom>newArrayList();
	protected int j;
	@Nullable
	private aom ao;
	public boolean k;
	public bqb l;
	public double m;
	public double n;
	public double o;
	private dem ap;
	private fu aq;
	private dem ar = dem.a;
	public float p;
	public float q;
	public float r;
	public float s;
	private deg as = d;
	protected boolean t;
	public boolean u;
	public boolean v;
	public boolean w;
	protected dem x = dem.a;
	public boolean y;
	public float z;
	public float A;
	public float B;
	public float C;
	private float at = 1.0F;
	private float au = 1.0F;
	public double D;
	public double E;
	public double F;
	public float G;
	public boolean H;
	public float I;
	protected final Random J = new Random();
	public int K;
	private int av = -this.cu();
	protected boolean L;
	protected Object2DoubleMap<adf<cwz>> M = new Object2DoubleArrayMap<>(2);
	protected boolean N;
	@Nullable
	protected adf<cwz> O;
	protected boolean P;
	public int Q;
	protected boolean R = true;
	protected final tt S;
	protected static final tq<Byte> T = tt.a(aom.class, ts.a);
	private static final tq<Integer> aw = tt.a(aom.class, ts.b);
	private static final tq<Optional<mr>> ax = tt.a(aom.class, ts.f);
	private static final tq<Boolean> ay = tt.a(aom.class, ts.i);
	private static final tq<Boolean> az = tt.a(aom.class, ts.i);
	private static final tq<Boolean> aA = tt.a(aom.class, ts.i);
	protected static final tq<apj> U = tt.a(aom.class, ts.s);
	public boolean V;
	public int W;
	public int X;
	public int Y;
	private boolean aB;
	public long Z;
	public long aa;
	public long ab;
	public boolean ac;
	public boolean ad;
	public int ae;
	protected boolean af;
	protected int ag;
	protected fu ah;
	protected dem ai;
	protected fz aj;
	private boolean aC;
	protected UUID ak = aec.a(this.J);
	protected String al = this.ak.toString();
	protected boolean am;
	private final Set<String> aD = Sets.<String>newHashSet();
	private boolean aE;
	private final double[] aF = new double[]{0.0, 0.0, 0.0};
	private long aG;
	private aon aH;
	private float aI;

	public aom(aoq<?> aoq, bqb bqb) {
		this.f = aoq;
		this.l = bqb;
		this.aH = aoq.l();
		this.ap = dem.a;
		this.aq = fu.b;
		this.d(0.0, 0.0, 0.0);
		this.S = new tt(this);
		this.S.a(T, (byte)0);
		this.S.a(aw, this.bD());
		this.S.a(ay, false);
		this.S.a(ax, Optional.empty());
		this.S.a(az, false);
		this.S.a(aA, false);
		this.S.a(U, apj.STANDING);
		this.e();
		this.aI = this.a(apj.STANDING, this.aH);
	}

	public boolean a_() {
		return false;
	}

	public final void T() {
		if (this.bo()) {
			this.ba();
		}

		if (this.bn()) {
			this.l();
		}
	}

	public void c(double double1, double double2, double double3) {
		this.Z = pa.a(double1);
		this.aa = pa.a(double2);
		this.ab = pa.a(double3);
	}

	public aoq<?> U() {
		return this.f;
	}

	public int V() {
		return this.g;
	}

	public void e(int integer) {
		this.g = integer;
	}

	public Set<String> W() {
		return this.aD;
	}

	public boolean a(String string) {
		return this.aD.size() >= 1024 ? false : this.aD.add(string);
	}

	public boolean b(String string) {
		return this.aD.remove(string);
	}

	public void X() {
		this.aa();
	}

	protected abstract void e();

	public tt Y() {
		return this.S;
	}

	public boolean equals(Object object) {
		return object instanceof aom ? ((aom)object).g == this.g : false;
	}

	public int hashCode() {
		return this.g;
	}

	public void aa() {
		this.y = true;
	}

	public void b(apj apj) {
		this.S.b(U, apj);
	}

	public apj ab() {
		return this.S.a(U);
	}

	public boolean a(aom aom, double double2) {
		double double5 = aom.ap.b - this.ap.b;
		double double7 = aom.ap.c - this.ap.c;
		double double9 = aom.ap.d - this.ap.d;
		return double5 * double5 + double7 * double7 + double9 * double9 < double2 * double2;
	}

	protected void a(float float1, float float2) {
		this.p = float1 % 360.0F;
		this.q = float2 % 360.0F;
	}

	public void d(double double1, double double2, double double3) {
		this.n(double1, double2, double3);
		float float8 = this.aH.a / 2.0F;
		float float9 = this.aH.b;
		this.a(new deg(double1 - (double)float8, double2, double3 - (double)float8, double1 + (double)float8, double2 + (double)float9, double3 + (double)float8));
	}

	protected void ac() {
		this.d(this.ap.b, this.ap.c, this.ap.d);
	}

	public void j() {
		if (!this.l.v) {
			this.b(6, this.bA());
		}

		this.ad();
	}

	public void ad() {
		this.l.X().a("entityBaseTick");
		if (this.bn() && this.cs().y) {
			this.l();
		}

		if (this.j > 0) {
			this.j--;
		}

		this.z = this.A;
		this.s = this.q;
		this.r = this.p;
		this.bg();
		if (this.aK()) {
			this.aL();
		}

		this.aG();
		this.n();
		this.aF();
		if (this.l.v) {
			this.ah();
		} else if (this.av > 0) {
			if (this.az()) {
				this.g(this.av - 4);
				if (this.av < 0) {
					this.ah();
				}
			} else {
				if (this.av % 20 == 0) {
					this.a(anw.c, 1.0F);
				}

				this.g(this.av - 1);
			}
		}

		if (this.aN()) {
			this.af();
			this.C *= 0.5F;
		}

		if (this.cD() < -64.0) {
			this.ai();
		}

		if (!this.l.v) {
			this.b(0, this.av > 0);
		}

		this.R = false;
		this.l.X().c();
	}

	protected void E() {
		if (this.ae > 0) {
			this.ae--;
		}
	}

	public int ae() {
		return 1;
	}

	protected void af() {
		if (!this.az()) {
			this.f(15);
			this.a(anw.d, 4.0F);
		}
	}

	public void f(int integer) {
		int integer3 = integer * 20;
		if (this instanceof aoy) {
			integer3 = boj.a((aoy)this, integer3);
		}

		if (this.av < integer3) {
			this.g(integer3);
		}
	}

	public void g(int integer) {
		this.av = integer;
	}

	public int ag() {
		return this.av;
	}

	public void ah() {
		this.g(0);
	}

	protected void ai() {
		this.aa();
	}

	public boolean e(double double1, double double2, double double3) {
		return this.b(this.cb().d(double1, double2, double3));
	}

	private boolean b(deg deg) {
		return this.l.a_(this, deg) && !this.l.d(deg);
	}

	public void c(boolean boolean1) {
		this.t = boolean1;
	}

	public boolean aj() {
		return this.t;
	}

	public void a(apd apd, dem dem) {
		if (this.H) {
			this.a(this.cb().c(dem));
			this.ap();
		} else {
			if (apd == apd.PISTON) {
				dem = this.a(dem);
				if (dem.equals(dem.a)) {
					return;
				}
			}

			this.l.X().a("move");
			if (this.x.g() > 1.0E-7) {
				dem = dem.h(this.x);
				this.x = dem.a;
				this.e(dem.a);
			}

			dem = this.a(dem, apd);
			dem dem4 = this.f(dem);
			if (dem4.g() > 1.0E-7) {
				this.a(this.cb().c(dem4));
				this.ap();
			}

			this.l.X().c();
			this.l.X().a("rest");
			this.u = !aec.b(dem.b, dem4.b) || !aec.b(dem.d, dem4.d);
			this.v = dem.c != dem4.c;
			this.t = this.v && dem.c < 0.0;
			fu fu5 = this.ak();
			cfj cfj6 = this.l.d_(fu5);
			this.a(dem4.c, this.t, cfj6, fu5);
			dem dem7 = this.cB();
			if (dem.b != dem4.b) {
				this.m(0.0, dem7.c, dem7.d);
			}

			if (dem.d != dem4.d) {
				this.m(dem7.b, dem7.c, 0.0);
			}

			bvr bvr8 = cfj6.b();
			if (dem.c != dem4.c) {
				bvr8.a(this.l, this);
			}

			if (this.t && !this.br()) {
				bvr8.a(this.l, fu5, this);
			}

			if (this.ax() && !this.bn()) {
				double double9 = dem4.b;
				double double11 = dem4.c;
				double double13 = dem4.d;
				if (!bvr8.a(acx.as)) {
					double11 = 0.0;
				}

				this.A = (float)((double)this.A + (double)aec.a(b(dem4)) * 0.6);
				this.B = (float)((double)this.B + (double)aec.a(double9 * double9 + double11 * double11 + double13 * double13) * 0.6);
				if (this.B > this.at && !cfj6.g()) {
					this.at = this.ao();
					if (this.aA()) {
						aom aom15 = this.bo() && this.cl() != null ? this.cl() : this;
						float float16 = aom15 == this ? 0.35F : 0.4F;
						dem dem17 = aom15.cB();
						float float18 = aec.a(dem17.b * dem17.b * 0.2F + dem17.c * dem17.c + dem17.d * dem17.d * 0.2F) * float16;
						if (float18 > 1.0F) {
							float18 = 1.0F;
						}

						this.d(float18);
					} else {
						this.a(fu5, cfj6);
					}
				} else if (this.B > this.au && this.au() && cfj6.g()) {
					this.au = this.e(this.B);
				}
			}

			try {
				this.P = false;
				this.at();
			} catch (Throwable var18) {
				j j10 = j.a(var18, "Checking entity block collision");
				k k11 = j10.a("Entity being checked for collision");
				this.a(k11);
				throw new s(j10);
			}

			float float9 = this.am();
			this.e(this.cB().d((double)float9, 1.0, (double)float9));
			if (this.l.c(this.cb().h(0.001)).noneMatch(cfj -> cfj.a(acx.am) || cfj.a(bvs.B)) && this.av <= 0) {
				this.g(-this.cu());
			}

			if (this.aC() && this.bm()) {
				this.a(acl.eM, 0.7F, 1.6F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
				this.g(-this.cu());
			}

			this.l.X().c();
		}
	}

	protected fu ak() {
		int integer2 = aec.c(this.ap.b);
		int integer3 = aec.c(this.ap.c - 0.2F);
		int integer4 = aec.c(this.ap.d);
		fu fu5 = new fu(integer2, integer3, integer4);
		if (this.l.d_(fu5).g()) {
			fu fu6 = fu5.c();
			cfj cfj7 = this.l.d_(fu6);
			bvr bvr8 = cfj7.b();
			if (bvr8.a(acx.L) || bvr8.a(acx.E) || bvr8 instanceof bxu) {
				return fu6;
			}
		}

		return fu5;
	}

	protected float al() {
		float float2 = this.l.d_(this.cA()).b().l();
		float float3 = this.l.d_(this.an()).b().l();
		return (double)float2 == 1.0 ? float3 : float2;
	}

	protected float am() {
		bvr bvr2 = this.l.d_(this.cA()).b();
		float float3 = bvr2.k();
		if (bvr2 != bvs.A && bvr2 != bvs.lc) {
			return (double)float3 == 1.0 ? this.l.d_(this.an()).b().k() : float3;
		} else {
			return float3;
		}
	}

	protected fu an() {
		return new fu(this.ap.b, this.cb().b - 0.5000001, this.ap.d);
	}

	protected dem a(dem dem, apd apd) {
		return dem;
	}

	protected dem a(dem dem) {
		if (dem.g() <= 1.0E-7) {
			return dem;
		} else {
			long long3 = this.l.Q();
			if (long3 != this.aG) {
				Arrays.fill(this.aF, 0.0);
				this.aG = long3;
			}

			if (dem.b != 0.0) {
				double double5 = this.a(fz.a.X, dem.b);
				return Math.abs(double5) <= 1.0E-5F ? dem.a : new dem(double5, 0.0, 0.0);
			} else if (dem.c != 0.0) {
				double double5 = this.a(fz.a.Y, dem.c);
				return Math.abs(double5) <= 1.0E-5F ? dem.a : new dem(0.0, double5, 0.0);
			} else if (dem.d != 0.0) {
				double double5 = this.a(fz.a.Z, dem.d);
				return Math.abs(double5) <= 1.0E-5F ? dem.a : new dem(0.0, 0.0, double5);
			} else {
				return dem.a;
			}
		}
	}

	private double a(fz.a a, double double2) {
		int integer5 = a.ordinal();
		double double6 = aec.a(double2 + this.aF[integer5], -0.51, 0.51);
		double2 = double6 - this.aF[integer5];
		this.aF[integer5] = double6;
		return double2;
	}

	private dem f(dem dem) {
		deg deg3 = this.cb();
		der der4 = der.a(this);
		dfg dfg5 = this.l.f().c();
		Stream<dfg> stream6 = dfd.c(dfg5, dfd.a(deg3.h(1.0E-7)), deq.i) ? Stream.empty() : Stream.of(dfg5);
		Stream<dfg> stream7 = this.l.c(this, deg3.b(dem), aom -> true);
		aee<dfg> aee8 = new aee<>(Stream.concat(stream7, stream6));
		dem dem9 = dem.g() == 0.0 ? dem : a(this, dem, deg3, this.l, der4, aee8);
		boolean boolean10 = dem.b != dem9.b;
		boolean boolean11 = dem.c != dem9.c;
		boolean boolean12 = dem.d != dem9.d;
		boolean boolean13 = this.t || boolean11 && dem.c < 0.0;
		if (this.G > 0.0F && boolean13 && (boolean10 || boolean12)) {
			dem dem14 = a(this, new dem(dem.b, (double)this.G, dem.d), deg3, this.l, der4, aee8);
			dem dem15 = a(this, new dem(0.0, (double)this.G, 0.0), deg3.b(dem.b, 0.0, dem.d), this.l, der4, aee8);
			if (dem15.c < (double)this.G) {
				dem dem16 = a(this, new dem(dem.b, 0.0, dem.d), deg3.c(dem15), this.l, der4, aee8).e(dem15);
				if (b(dem16) > b(dem14)) {
					dem14 = dem16;
				}
			}

			if (b(dem14) > b(dem9)) {
				return dem14.e(a(this, new dem(0.0, -dem14.c + dem.c, 0.0), deg3.c(dem14), this.l, der4, aee8));
			}
		}

		return dem9;
	}

	public static double b(dem dem) {
		return dem.b * dem.b + dem.d * dem.d;
	}

	public static dem a(@Nullable aom aom, dem dem, deg deg, bqb bqb, der der, aee<dfg> aee) {
		boolean boolean7 = dem.b == 0.0;
		boolean boolean8 = dem.c == 0.0;
		boolean boolean9 = dem.d == 0.0;
		if ((!boolean7 || !boolean8) && (!boolean7 || !boolean9) && (!boolean8 || !boolean9)) {
			aee<dfg> aee10 = new aee<>(Stream.concat(aee.a(), bqb.b(aom, deg.b(dem))));
			return a(dem, deg, aee10);
		} else {
			return a(dem, deg, bqb, der, aee);
		}
	}

	public static dem a(dem dem, deg deg, aee<dfg> aee) {
		double double4 = dem.b;
		double double6 = dem.c;
		double double8 = dem.d;
		if (double6 != 0.0) {
			double6 = dfd.a(fz.a.Y, deg, aee.a(), double6);
			if (double6 != 0.0) {
				deg = deg.d(0.0, double6, 0.0);
			}
		}

		boolean boolean10 = Math.abs(double4) < Math.abs(double8);
		if (boolean10 && double8 != 0.0) {
			double8 = dfd.a(fz.a.Z, deg, aee.a(), double8);
			if (double8 != 0.0) {
				deg = deg.d(0.0, 0.0, double8);
			}
		}

		if (double4 != 0.0) {
			double4 = dfd.a(fz.a.X, deg, aee.a(), double4);
			if (!boolean10 && double4 != 0.0) {
				deg = deg.d(double4, 0.0, 0.0);
			}
		}

		if (!boolean10 && double8 != 0.0) {
			double8 = dfd.a(fz.a.Z, deg, aee.a(), double8);
		}

		return new dem(double4, double6, double8);
	}

	public static dem a(dem dem, deg deg, bqd bqd, der der, aee<dfg> aee) {
		double double6 = dem.b;
		double double8 = dem.c;
		double double10 = dem.d;
		if (double8 != 0.0) {
			double8 = dfd.a(fz.a.Y, deg, bqd, double8, der, aee.a());
			if (double8 != 0.0) {
				deg = deg.d(0.0, double8, 0.0);
			}
		}

		boolean boolean12 = Math.abs(double6) < Math.abs(double10);
		if (boolean12 && double10 != 0.0) {
			double10 = dfd.a(fz.a.Z, deg, bqd, double10, der, aee.a());
			if (double10 != 0.0) {
				deg = deg.d(0.0, 0.0, double10);
			}
		}

		if (double6 != 0.0) {
			double6 = dfd.a(fz.a.X, deg, bqd, double6, der, aee.a());
			if (!boolean12 && double6 != 0.0) {
				deg = deg.d(double6, 0.0, 0.0);
			}
		}

		if (!boolean12 && double10 != 0.0) {
			double10 = dfd.a(fz.a.Z, deg, bqd, double10, der, aee.a());
		}

		return new dem(double6, double8, double10);
	}

	protected float ao() {
		return (float)((int)this.B + 1);
	}

	public void ap() {
		deg deg2 = this.cb();
		this.n((deg2.a + deg2.d) / 2.0, deg2.b, (deg2.c + deg2.f) / 2.0);
	}

	protected ack aq() {
		return acl.eQ;
	}

	protected ack ar() {
		return acl.eP;
	}

	protected ack as() {
		return acl.eP;
	}

	protected void at() {
		deg deg2 = this.cb();
		fu fu3 = new fu(deg2.a + 0.001, deg2.b + 0.001, deg2.c + 0.001);
		fu fu4 = new fu(deg2.d - 0.001, deg2.e - 0.001, deg2.f - 0.001);
		fu.a a5 = new fu.a();
		if (this.l.a(fu3, fu4)) {
			for (int integer6 = fu3.u(); integer6 <= fu4.u(); integer6++) {
				for (int integer7 = fu3.v(); integer7 <= fu4.v(); integer7++) {
					for (int integer8 = fu3.w(); integer8 <= fu4.w(); integer8++) {
						a5.d(integer6, integer7, integer8);
						cfj cfj9 = this.l.d_(a5);

						try {
							cfj9.a(this.l, a5, this);
							this.a(cfj9);
						} catch (Throwable var12) {
							j j11 = j.a(var12, "Colliding entity with block");
							k k12 = j11.a("Block being collided with");
							k.a(k12, a5, cfj9);
							throw new s(j11);
						}
					}
				}
			}
		}
	}

	protected void a(cfj cfj) {
	}

	protected void a(fu fu, cfj cfj) {
		if (!cfj.c().a()) {
			cfj cfj4 = this.l.d_(fu.b());
			cbh cbh5 = cfj4.a(bvs.cC) ? cfj4.o() : cfj.o();
			this.a(cbh5.d(), cbh5.a() * 0.15F, cbh5.b());
		}
	}

	protected void d(float float1) {
		this.a(this.aq(), float1, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
	}

	protected float e(float float1) {
		return 0.0F;
	}

	protected boolean au() {
		return false;
	}

	public void a(ack ack, float float2, float float3) {
		if (!this.av()) {
			this.l.a(null, this.cC(), this.cD(), this.cG(), ack, this.ct(), float2, float3);
		}
	}

	public boolean av() {
		return this.S.a(az);
	}

	public void d(boolean boolean1) {
		this.S.b(az, boolean1);
	}

	public boolean aw() {
		return this.S.a(aA);
	}

	public void e(boolean boolean1) {
		this.S.b(aA, boolean1);
	}

	protected boolean ax() {
		return true;
	}

	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
		if (boolean2) {
			if (this.C > 0.0F) {
				cfj.b().a(this.l, fu, this, this.C);
			}

			this.C = 0.0F;
		} else if (double1 < 0.0) {
			this.C = (float)((double)this.C - double1);
		}
	}

	@Nullable
	public deg ay() {
		return null;
	}

	public boolean az() {
		return this.U().c();
	}

	public boolean b(float float1, float float2) {
		if (this.bo()) {
			for (aom aom5 : this.cm()) {
				aom5.b(float1, float2);
			}
		}

		return false;
	}

	public boolean aA() {
		return this.L;
	}

	private boolean i() {
		fu fu2 = this.cA();
		return this.l.t(fu2) || this.l.t(fu2.a(0.0, (double)this.aH.b, 0.0));
	}

	private boolean k() {
		return this.l.d_(this.cA()).a(bvs.lc);
	}

	public boolean aB() {
		return this.aA() || this.i();
	}

	public boolean aC() {
		return this.aA() || this.i() || this.k();
	}

	public boolean aD() {
		return this.aA() || this.k();
	}

	public boolean aE() {
		return this.N && this.aA();
	}

	public void aF() {
		if (this.bx()) {
			this.h(this.bw() && this.aA() && !this.bn());
		} else {
			this.h(this.bw() && this.aE() && !this.bn());
		}
	}

	protected boolean aG() {
		this.M.clear();
		this.aH();
		if (this.aA()) {
			return true;
		} else {
			double double2 = this.l.m().f() ? 0.007 : 0.0023333333333333335;
			return this.a(acz.b, double2);
		}
	}

	void aH() {
		if (this.cs() instanceof bft) {
			this.L = false;
		} else if (this.a(acz.a, 0.014)) {
			if (!this.L && !this.R) {
				this.aI();
			}

			this.C = 0.0F;
			this.L = true;
			this.ah();
		} else {
			this.L = false;
		}
	}

	private void n() {
		this.N = this.a(acz.a);
		this.O = null;
		double double2 = this.cF() - 0.11111111F;
		dem dem4 = new dem(this.cC(), double2, this.cG());
		aom aom5 = this.cs();
		if (aom5 instanceof bft) {
			bft bft6 = (bft)aom5;
			if (!bft6.aE() && bft6.cb().d(dem4)) {
				return;
			}
		}

		fu fu6 = new fu(dem4);
		cxa cxa7 = this.l.b(fu6);

		for (adf<cwz> adf9 : acz.c()) {
			if (cxa7.a(adf9)) {
				double double10 = (double)((float)fu6.v() + cxa7.a((bpg)this.l, fu6));
				if (double10 > double2) {
					this.O = adf9;
				}

				return;
			}
		}
	}

	protected void aI() {
		aom aom2 = this.bo() && this.cl() != null ? this.cl() : this;
		float float3 = aom2 == this ? 0.2F : 0.9F;
		dem dem4 = aom2.cB();
		float float5 = aec.a(dem4.b * dem4.b * 0.2F + dem4.c * dem4.c + dem4.d * dem4.d * 0.2F) * float3;
		if (float5 > 1.0F) {
			float5 = 1.0F;
		}

		if ((double)float5 < 0.25) {
			this.a(this.ar(), float5, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
		} else {
			this.a(this.as(), float5, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
		}

		float float6 = (float)aec.c(this.cD());

		for (int integer7 = 0; (float)integer7 < 1.0F + this.aH.a * 20.0F; integer7++) {
			double double8 = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aH.a;
			double double10 = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aH.a;
			this.l.a(hh.e, this.cC() + double8, (double)(float6 + 1.0F), this.cG() + double10, dem4.b, dem4.c - this.J.nextDouble() * 0.2F, dem4.d);
		}

		for (int integer7 = 0; (float)integer7 < 1.0F + this.aH.a * 20.0F; integer7++) {
			double double8 = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aH.a;
			double double10 = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aH.a;
			this.l.a(hh.Z, this.cC() + double8, (double)(float6 + 1.0F), this.cG() + double10, dem4.b, dem4.c, dem4.d);
		}
	}

	protected cfj aJ() {
		return this.l.d_(this.ak());
	}

	public boolean aK() {
		return this.bw() && !this.aA() && !this.a_() && !this.bv() && !this.aN() && this.aU();
	}

	protected void aL() {
		int integer2 = aec.c(this.cC());
		int integer3 = aec.c(this.cD() - 0.2F);
		int integer4 = aec.c(this.cG());
		fu fu5 = new fu(integer2, integer3, integer4);
		cfj cfj6 = this.l.d_(fu5);
		if (cfj6.h() != cak.INVISIBLE) {
			dem dem7 = this.cB();
			this.l
				.a(
					new hc(hh.d, cfj6),
					this.cC() + (this.J.nextDouble() - 0.5) * (double)this.aH.a,
					this.cD() + 0.1,
					this.cG() + (this.J.nextDouble() - 0.5) * (double)this.aH.a,
					dem7.b * -4.0,
					1.5,
					dem7.d * -4.0
				);
		}
	}

	public boolean a(adf<cwz> adf) {
		return this.O == adf;
	}

	public void aM() {
		this.P = true;
	}

	public boolean aN() {
		return this.P;
	}

	public void a(float float1, dem dem) {
		dem dem4 = a(dem, float1, this.p);
		this.e(this.cB().e(dem4));
	}

	private static dem a(dem dem, float float2, float float3) {
		double double4 = dem.g();
		if (double4 < 1.0E-7) {
			return dem.a;
		} else {
			dem dem6 = (double4 > 1.0 ? dem.d() : dem).a((double)float2);
			float float7 = aec.a(float3 * (float) (Math.PI / 180.0));
			float float8 = aec.b(float3 * (float) (Math.PI / 180.0));
			return new dem(dem6.b * (double)float8 - dem6.d * (double)float7, dem6.c, dem6.d * (double)float8 + dem6.b * (double)float7);
		}
	}

	public float aO() {
		fu.a a2 = new fu.a(this.cC(), 0.0, this.cG());
		if (this.l.C(a2)) {
			a2.p(aec.c(this.cF()));
			return this.l.y(a2);
		} else {
			return 0.0F;
		}
	}

	public void a_(bqb bqb) {
		this.l = bqb;
	}

	public void a(double double1, double double2, double double3, float float4, float float5) {
		double double10 = aec.a(double1, -3.0E7, 3.0E7);
		double double12 = aec.a(double3, -3.0E7, 3.0E7);
		this.m = double10;
		this.n = double2;
		this.o = double12;
		this.d(double10, double2, double12);
		this.p = float4 % 360.0F;
		this.q = aec.a(float5, -90.0F, 90.0F) % 360.0F;
		this.r = this.p;
		this.s = this.q;
	}

	public void c(dem dem) {
		this.b(dem.b, dem.c, dem.d);
	}

	public void b(double double1, double double2, double double3) {
		this.b(double1, double2, double3, this.p, this.q);
	}

	public void a(fu fu, float float2, float float3) {
		this.b((double)fu.u() + 0.5, (double)fu.v(), (double)fu.w() + 0.5, float2, float3);
	}

	public void b(double double1, double double2, double double3, float float4, float float5) {
		this.f(double1, double2, double3);
		this.p = float4;
		this.q = float5;
		this.ac();
	}

	public void f(double double1, double double2, double double3) {
		this.n(double1, double2, double3);
		this.m = double1;
		this.n = double2;
		this.o = double3;
		this.D = double1;
		this.E = double2;
		this.F = double3;
	}

	public float g(aom aom) {
		float float3 = (float)(this.cC() - aom.cC());
		float float4 = (float)(this.cD() - aom.cD());
		float float5 = (float)(this.cG() - aom.cG());
		return aec.c(float3 * float3 + float4 * float4 + float5 * float5);
	}

	public double g(double double1, double double2, double double3) {
		double double8 = this.cC() - double1;
		double double10 = this.cD() - double2;
		double double12 = this.cG() - double3;
		return double8 * double8 + double10 * double10 + double12 * double12;
	}

	public double h(aom aom) {
		return this.d(aom.cz());
	}

	public double d(dem dem) {
		double double3 = this.cC() - dem.b;
		double double5 = this.cD() - dem.c;
		double double7 = this.cG() - dem.d;
		return double3 * double3 + double5 * double5 + double7 * double7;
	}

	public void a_(bec bec) {
	}

	public void i(aom aom) {
		if (!this.x(aom)) {
			if (!aom.H && !this.H) {
				double double3 = aom.cC() - this.cC();
				double double5 = aom.cG() - this.cG();
				double double7 = aec.a(double3, double5);
				if (double7 >= 0.01F) {
					double7 = (double)aec.a(double7);
					double3 /= double7;
					double5 /= double7;
					double double9 = 1.0 / double7;
					if (double9 > 1.0) {
						double9 = 1.0;
					}

					double3 *= double9;
					double5 *= double9;
					double3 *= 0.05F;
					double5 *= 0.05F;
					double3 *= (double)(1.0F - this.I);
					double5 *= (double)(1.0F - this.I);
					if (!this.bo()) {
						this.h(-double3, 0.0, -double5);
					}

					if (!aom.bo()) {
						aom.h(double3, 0.0, double5);
					}
				}
			}
		}
	}

	public void h(double double1, double double2, double double3) {
		this.e(this.cB().b(double1, double2, double3));
		this.ad = true;
	}

	protected void aP() {
		this.w = true;
	}

	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			this.aP();
			return false;
		}
	}

	public final dem f(float float1) {
		return this.c(this.g(float1), this.h(float1));
	}

	public float g(float float1) {
		return float1 == 1.0F ? this.q : aec.g(float1, this.s, this.q);
	}

	public float h(float float1) {
		return float1 == 1.0F ? this.p : aec.g(float1, this.r, this.p);
	}

	protected final dem c(float float1, float float2) {
		float float4 = float1 * (float) (Math.PI / 180.0);
		float float5 = -float2 * (float) (Math.PI / 180.0);
		float float6 = aec.b(float5);
		float float7 = aec.a(float5);
		float float8 = aec.b(float4);
		float float9 = aec.a(float4);
		return new dem((double)(float7 * float8), (double)(-float9), (double)(float6 * float8));
	}

	public final dem i(float float1) {
		return this.d(this.g(float1), this.h(float1));
	}

	protected final dem d(float float1, float float2) {
		return this.c(float1 - 90.0F, float2);
	}

	public final dem j(float float1) {
		if (float1 == 1.0F) {
			return new dem(this.cC(), this.cF(), this.cG());
		} else {
			double double3 = aec.d((double)float1, this.m, this.cC());
			double double5 = aec.d((double)float1, this.n, this.cD()) + (double)this.cd();
			double double7 = aec.d((double)float1, this.o, this.cG());
			return new dem(double3, double5, double7);
		}
	}

	public dej a(double double1, float float2, boolean boolean3) {
		dem dem6 = this.j(float2);
		dem dem7 = this.f(float2);
		dem dem8 = dem6.b(dem7.b * double1, dem7.c * double1, dem7.d * double1);
		return this.l.a(new bpj(dem6, dem8, bpj.a.OUTLINE, boolean3 ? bpj.b.ANY : bpj.b.NONE, this));
	}

	public boolean aQ() {
		return false;
	}

	public boolean aR() {
		return false;
	}

	public void a(aom aom, int integer, anw anw) {
		if (aom instanceof ze) {
			aa.c.a((ze)aom, this, anw);
		}
	}

	public boolean a_(le le) {
		String string3 = this.aT();
		if (!this.y && string3 != null) {
			le.a("id", string3);
			this.e(le);
			return true;
		} else {
			return false;
		}
	}

	public boolean d(le le) {
		return this.bn() ? false : this.a_(le);
	}

	public le e(le le) {
		try {
			if (this.ao != null) {
				le.a("Pos", this.a(this.ao.cC(), this.ao.cD(), this.ao.cG()));
			} else {
				le.a("Pos", this.a(this.cC(), this.cD(), this.cG()));
			}

			dem dem3 = this.cB();
			le.a("Motion", this.a(dem3.b, dem3.c, dem3.d));
			le.a("Rotation", this.a(this.p, this.q));
			le.a("FallDistance", this.C);
			le.a("Fire", (short)this.av);
			le.a("Air", (short)this.bE());
			le.a("OnGround", this.t);
			le.a("Invulnerable", this.aC);
			le.b("PortalCooldown", this.ae);
			le.a("UUID", this.bR());
			mr mr4 = this.R();
			if (mr4 != null) {
				le.a("CustomName", mr.a.a(mr4));
			}

			if (this.bW()) {
				le.a("CustomNameVisible", this.bW());
			}

			if (this.av()) {
				le.a("Silent", this.av());
			}

			if (this.aw()) {
				le.a("NoGravity", this.aw());
			}

			if (this.am) {
				le.a("Glowing", this.am);
			}

			if (!this.aD.isEmpty()) {
				lk lk5 = new lk();

				for (String string7 : this.aD) {
					lk5.add(lt.a(string7));
				}

				le.a("Tags", lk5);
			}

			this.b(le);
			if (this.bo()) {
				lk lk5 = new lk();

				for (aom aom7 : this.cm()) {
					le le8 = new le();
					if (aom7.a_(le8)) {
						lk5.add(le8);
					}
				}

				if (!lk5.isEmpty()) {
					le.a("Passengers", lk5);
				}
			}

			return le;
		} catch (Throwable var8) {
			j j4 = j.a(var8, "Saving entity NBT");
			k k5 = j4.a("Entity being saved");
			this.a(k5);
			throw new s(j4);
		}
	}

	public void f(le le) {
		try {
			lk lk3 = le.d("Pos", 6);
			lk lk4 = le.d("Motion", 6);
			lk lk5 = le.d("Rotation", 5);
			double double6 = lk4.h(0);
			double double8 = lk4.h(1);
			double double10 = lk4.h(2);
			this.m(Math.abs(double6) > 10.0 ? 0.0 : double6, Math.abs(double8) > 10.0 ? 0.0 : double8, Math.abs(double10) > 10.0 ? 0.0 : double10);
			this.f(lk3.h(0), lk3.h(1), lk3.h(2));
			this.p = lk5.i(0);
			this.q = lk5.i(1);
			this.r = this.p;
			this.s = this.q;
			this.k(this.p);
			this.l(this.p);
			this.C = le.j("FallDistance");
			this.av = le.g("Fire");
			this.j(le.g("Air"));
			this.t = le.q("OnGround");
			this.aC = le.q("Invulnerable");
			this.ae = le.h("PortalCooldown");
			if (le.b("UUID")) {
				this.ak = le.a("UUID");
				this.al = this.ak.toString();
			}

			if (!Double.isFinite(this.cC()) || !Double.isFinite(this.cD()) || !Double.isFinite(this.cG())) {
				throw new IllegalStateException("Entity has invalid position");
			} else if (Double.isFinite((double)this.p) && Double.isFinite((double)this.q)) {
				this.ac();
				this.a(this.p, this.q);
				if (le.c("CustomName", 8)) {
					String string12 = le.l("CustomName");

					try {
						this.a(mr.a.a(string12));
					} catch (Exception var14) {
						h.warn("Failed to parse entity custom name {}", string12, var14);
					}
				}

				this.n(le.q("CustomNameVisible"));
				this.d(le.q("Silent"));
				this.e(le.q("NoGravity"));
				this.i(le.q("Glowing"));
				if (le.c("Tags", 9)) {
					this.aD.clear();
					lk lk12 = le.d("Tags", 8);
					int integer13 = Math.min(lk12.size(), 1024);

					for (int integer14 = 0; integer14 < integer13; integer14++) {
						this.aD.add(lk12.j(integer14));
					}
				}

				this.a(le);
				if (this.aS()) {
					this.ac();
				}
			} else {
				throw new IllegalStateException("Entity has invalid rotation");
			}
		} catch (Throwable var15) {
			j j4 = j.a(var15, "Loading entity NBT");
			k k5 = j4.a("Entity being loaded");
			this.a(k5);
			throw new s(j4);
		}
	}

	protected boolean aS() {
		return true;
	}

	@Nullable
	protected final String aT() {
		aoq<?> aoq2 = this.U();
		uh uh3 = aoq.a(aoq2);
		return aoq2.a() && uh3 != null ? uh3.toString() : null;
	}

	protected abstract void a(le le);

	protected abstract void b(le le);

	protected lk a(double... arr) {
		lk lk3 = new lk();

		for (double double7 : arr) {
			lk3.add(lf.a(double7));
		}

		return lk3;
	}

	protected lk a(float... arr) {
		lk lk3 = new lk();

		for (float float7 : arr) {
			lk3.add(lh.a(float7));
		}

		return lk3;
	}

	@Nullable
	public bbg a(bqa bqa) {
		return this.a(bqa, 0);
	}

	@Nullable
	public bbg a(bqa bqa, int integer) {
		return this.a(new bki(bqa), (float)integer);
	}

	@Nullable
	public bbg a(bki bki) {
		return this.a(bki, 0.0F);
	}

	@Nullable
	public bbg a(bki bki, float float2) {
		if (bki.a()) {
			return null;
		} else if (this.l.v) {
			return null;
		} else {
			bbg bbg4 = new bbg(this.l, this.cC(), this.cD() + (double)float2, this.cG(), bki);
			bbg4.m();
			this.l.c(bbg4);
			return bbg4;
		}
	}

	public boolean aU() {
		return !this.y;
	}

	public boolean aV() {
		if (this.H) {
			return false;
		} else {
			float float2 = 0.1F;
			float float3 = this.aH.a * 0.8F;
			deg deg4 = deg.g((double)float3, 0.1F, (double)float3).d(this.cC(), this.cF(), this.cG());
			return this.l.a(this, deg4, (cfj, fu) -> cfj.o(this.l, fu)).findAny().isPresent();
		}
	}

	public ang a(bec bec, anf anf) {
		return ang.PASS;
	}

	@Nullable
	public deg j(aom aom) {
		return null;
	}

	public void aW() {
		this.e(dem.a);
		this.j();
		if (this.bn()) {
			this.cs().k(this);
		}
	}

	public void k(aom aom) {
		this.a(aom, aom::d);
	}

	private void a(aom aom, aom.a a) {
		if (this.w(aom)) {
			double double4 = this.cD() + this.aY() + aom.aX();
			a.accept(aom, this.cC(), double4, this.cG());
		}
	}

	public double aX() {
		return 0.0;
	}

	public double aY() {
		return (double)this.aH.b * 0.75;
	}

	public boolean m(aom aom) {
		return this.a(aom, false);
	}

	public boolean a(aom aom, boolean boolean2) {
		for (aom aom4 = aom; aom4.ao != null; aom4 = aom4.ao) {
			if (aom4.ao == this) {
				return false;
			}
		}

		if (boolean2 || this.n(aom) && aom.q(this)) {
			if (this.bn()) {
				this.l();
			}

			this.b(apj.STANDING);
			this.ao = aom;
			this.ao.o(this);
			return true;
		} else {
			return false;
		}
	}

	protected boolean n(aom aom) {
		return !this.bq() && this.j <= 0;
	}

	protected boolean c(apj apj) {
		return this.l.a_(this, this.d(apj).h(1.0E-7));
	}

	public void ba() {
		for (int integer2 = this.an.size() - 1; integer2 >= 0; integer2--) {
			((aom)this.an.get(integer2)).l();
		}
	}

	public void bb() {
		if (this.ao != null) {
			aom aom2 = this.ao;
			this.ao = null;
			aom2.p(this);
		}
	}

	public void l() {
		this.bb();
	}

	protected void o(aom aom) {
		if (aom.cs() != this) {
			throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
		} else {
			if (!this.l.v && aom instanceof bec && !(this.cl() instanceof bec)) {
				this.an.add(0, aom);
			} else {
				this.an.add(aom);
			}
		}
	}

	protected void p(aom aom) {
		if (aom.cs() == this) {
			throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
		} else {
			this.an.remove(aom);
			aom.j = 60;
		}
	}

	protected boolean q(aom aom) {
		return this.cm().size() < 1;
	}

	public float bc() {
		return 0.0F;
	}

	public dem bd() {
		return this.c(this.q, this.p);
	}

	public del be() {
		return new del(this.q, this.p);
	}

	public void d(fu fu) {
		if (this.ae > 0) {
			this.ae = this.bh();
		} else {
			if (!this.l.v && !fu.equals(this.ah)) {
				this.ah = new fu(fu);
				cfo.b b3 = bzm.c(this.l, this.ah);
				double double4 = b3.b().n() == fz.a.X ? (double)b3.a().w() : (double)b3.a().u();
				double double6 = aec.a(
					Math.abs(aec.c((b3.b().n() == fz.a.X ? this.cG() : this.cC()) - (double)(b3.b().g().e() == fz.b.NEGATIVE ? 1 : 0), double4, double4 - (double)b3.d())),
					0.0,
					1.0
				);
				double double8 = aec.a(aec.c(this.cD() - 1.0, (double)b3.a().v(), (double)(b3.a().v() - b3.e())), 0.0, 1.0);
				this.ai = new dem(double6, double8, 0.0);
				this.aj = b3.b();
			}

			this.af = true;
		}
	}

	protected void bg() {
		if (this.l instanceof zd) {
			int integer2 = this.ae();
			zd zd3 = (zd)this.l;
			if (this.af) {
				MinecraftServer minecraftServer4 = zd3.l();
				ug<bqb> ug5 = this.l.W() == bqb.h ? bqb.g : bqb.h;
				zd zd6 = minecraftServer4.a(ug5);
				if (zd6 != null && minecraftServer4.B() && !this.bn() && this.ag++ >= integer2) {
					this.l.X().a("portal");
					this.ag = integer2;
					this.ae = this.bh();
					this.a(zd6);
					this.l.X().c();
				}

				this.af = false;
			} else {
				if (this.ag > 0) {
					this.ag -= 4;
				}

				if (this.ag < 0) {
					this.ag = 0;
				}
			}

			this.E();
		}
	}

	public int bh() {
		return 300;
	}

	public Iterable<bki> bj() {
		return c;
	}

	public Iterable<bki> bk() {
		return c;
	}

	public Iterable<bki> bl() {
		return Iterables.concat(this.bj(), this.bk());
	}

	public void a(aor aor, bki bki) {
	}

	public boolean bm() {
		boolean boolean2 = this.l != null && this.l.v;
		return !this.az() && (this.av > 0 || boolean2 && this.i(0));
	}

	public boolean bn() {
		return this.cs() != null;
	}

	public boolean bo() {
		return !this.cm().isEmpty();
	}

	public boolean bp() {
		return true;
	}

	public void f(boolean boolean1) {
		this.b(1, boolean1);
	}

	public boolean bq() {
		return this.i(1);
	}

	public boolean br() {
		return this.bq();
	}

	public boolean bs() {
		return this.bq();
	}

	public boolean bt() {
		return this.bq();
	}

	public boolean bu() {
		return this.bq();
	}

	public boolean bv() {
		return this.ab() == apj.CROUCHING;
	}

	public boolean bw() {
		return this.i(3);
	}

	public void g(boolean boolean1) {
		this.b(3, boolean1);
	}

	public boolean bx() {
		return this.i(4);
	}

	public boolean by() {
		return this.ab() == apj.SWIMMING;
	}

	public void h(boolean boolean1) {
		this.b(4, boolean1);
	}

	public boolean bA() {
		return this.am || this.l.v && this.i(6);
	}

	public void i(boolean boolean1) {
		this.am = boolean1;
		if (!this.l.v) {
			this.b(6, this.am);
		}
	}

	public boolean bB() {
		return this.i(5);
	}

	@Nullable
	public dfo bC() {
		return this.l.D().i(this.bT());
	}

	public boolean r(aom aom) {
		return this.a(aom.bC());
	}

	public boolean a(dfo dfo) {
		return this.bC() != null ? this.bC().a(dfo) : false;
	}

	public void j(boolean boolean1) {
		this.b(5, boolean1);
	}

	protected boolean i(int integer) {
		return (this.S.a(T) & 1 << integer) != 0;
	}

	protected void b(int integer, boolean boolean2) {
		byte byte4 = this.S.a(T);
		if (boolean2) {
			this.S.b(T, (byte)(byte4 | 1 << integer));
		} else {
			this.S.b(T, (byte)(byte4 & ~(1 << integer)));
		}
	}

	public int bD() {
		return 300;
	}

	public int bE() {
		return this.S.a(aw);
	}

	public void j(int integer) {
		this.S.b(aw, integer);
	}

	public void a(aox aox) {
		this.g(this.av + 1);
		if (this.av == 0) {
			this.f(8);
		}

		this.a(anw.b, 5.0F);
	}

	public void k(boolean boolean1) {
		dem dem3 = this.cB();
		double double4;
		if (boolean1) {
			double4 = Math.max(-0.9, dem3.c - 0.03);
		} else {
			double4 = Math.min(1.8, dem3.c + 0.1);
		}

		this.m(dem3.b, double4, dem3.d);
	}

	public void l(boolean boolean1) {
		dem dem3 = this.cB();
		double double4;
		if (boolean1) {
			double4 = Math.max(-0.3, dem3.c - 0.03);
		} else {
			double4 = Math.min(0.7, dem3.c + 0.06);
		}

		this.m(dem3.b, double4, dem3.d);
		this.C = 0.0F;
	}

	public void a_(aoy aoy) {
	}

	protected void k(double double1, double double2, double double3) {
		fu fu8 = new fu(double1, double2, double3);
		dem dem9 = new dem(double1 - (double)fu8.u(), double2 - (double)fu8.v(), double3 - (double)fu8.w());
		fu.a a10 = new fu.a();
		fz fz11 = fz.UP;
		double double12 = Double.MAX_VALUE;

		for (fz fz17 : new fz[]{fz.NORTH, fz.SOUTH, fz.WEST, fz.EAST, fz.UP}) {
			a10.a(fu8, fz17);
			if (!this.l.d_(a10).r(this.l, a10)) {
				double double18 = dem9.a(fz17.n());
				double double20 = fz17.e() == fz.b.POSITIVE ? 1.0 - double18 : double18;
				if (double20 < double12) {
					double12 = double20;
					fz11 = fz17;
				}
			}
		}

		float float14 = this.J.nextFloat() * 0.2F + 0.1F;
		float float15 = (float)fz11.e().a();
		dem dem16 = this.cB().a(0.75);
		if (fz11.n() == fz.a.X) {
			this.m((double)(float15 * float14), dem16.c, dem16.d);
		} else if (fz11.n() == fz.a.Y) {
			this.m(dem16.b, (double)(float15 * float14), dem16.d);
		} else if (fz11.n() == fz.a.Z) {
			this.m(dem16.b, dem16.c, (double)(float15 * float14));
		}
	}

	public void a(cfj cfj, dem dem) {
		this.C = 0.0F;
		this.x = dem;
	}

	private static mr b(mr mr) {
		mx mx2 = mr.f().a(mr.c().a(null));

		for (mr mr4 : mr.b()) {
			mx2.a(b(mr4));
		}

		return mx2;
	}

	@Override
	public mr P() {
		mr mr2 = this.R();
		return mr2 != null ? b(mr2) : this.bF();
	}

	protected mr bF() {
		return this.f.g();
	}

	public boolean s(aom aom) {
		return this == aom;
	}

	public float bG() {
		return 0.0F;
	}

	public void k(float float1) {
	}

	public void l(float float1) {
	}

	public boolean bH() {
		return true;
	}

	public boolean t(aom aom) {
		return false;
	}

	public String toString() {
		return String.format(
			Locale.ROOT,
			"%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
			this.getClass().getSimpleName(),
			this.P().getString(),
			this.g,
			this.l == null ? "~NULL~" : this.l.toString(),
			this.cC(),
			this.cD(),
			this.cG()
		);
	}

	public boolean b(anw anw) {
		return this.aC && anw != anw.m && !anw.v();
	}

	public boolean bI() {
		return this.aC;
	}

	public void m(boolean boolean1) {
		this.aC = boolean1;
	}

	public void u(aom aom) {
		this.b(aom.cC(), aom.cD(), aom.cG(), aom.p, aom.q);
	}

	public void v(aom aom) {
		le le3 = aom.e(new le());
		le3.r("Dimension");
		this.f(le3);
		this.ae = aom.ae;
		this.ah = aom.ah;
		this.ai = aom.ai;
		this.aj = aom.aj;
	}

	@Nullable
	public aom a(zd zd) {
		if (this.l instanceof zd && !this.y) {
			this.l.X().a("changeDimension");
			this.T();
			this.l.X().a("reposition");
			dem dem4 = this.cB();
			float float5 = 0.0F;
			fu fu3;
			if (this.l.W() == bqb.i && zd.W() == bqb.g) {
				fu3 = zd.a(cio.a.MOTION_BLOCKING_NO_LEAVES, zd.u());
			} else if (zd.W() == bqb.i) {
				fu3 = zd.a;
			} else {
				double double6 = this.cC();
				double double8 = this.cG();
				cif cif10 = this.l.m();
				cif cif11 = zd.m();
				double double12 = 8.0;
				if (!cif10.h() && cif11.h()) {
					double6 /= 8.0;
					double8 /= 8.0;
				} else if (cif10.h() && !cif11.h()) {
					double6 *= 8.0;
					double8 *= 8.0;
				}

				double double14 = Math.min(-2.9999872E7, zd.f().e() + 16.0);
				double double16 = Math.min(-2.9999872E7, zd.f().f() + 16.0);
				double double18 = Math.min(2.9999872E7, zd.f().g() - 16.0);
				double double20 = Math.min(2.9999872E7, zd.f().h() - 16.0);
				double6 = aec.a(double6, double14, double18);
				double8 = aec.a(double8, double16, double20);
				dem dem22 = this.bN();
				fu3 = new fu(double6, this.cD(), double8);
				cfo.c c23 = zd.q_().a(fu3, dem4, this.bO(), dem22.b, dem22.c, this instanceof bec);
				if (c23 == null) {
					return null;
				}

				fu3 = new fu(c23.a);
				dem4 = c23.b;
				float5 = (float)c23.c;
			}

			this.l.X().b("reloading");
			aom aom6 = this.U().a(zd);
			if (aom6 != null) {
				aom6.v(this);
				aom6.a(fu3, aom6.p + float5, aom6.q);
				aom6.e(dem4);
				zd.e(aom6);
				if (zd.W() == bqb.i) {
					zd.a(zd);
				}
			}

			this.bJ();
			this.l.X().c();
			((zd)this.l).p_();
			zd.p_();
			this.l.X().c();
			return aom6;
		} else {
			return null;
		}
	}

	protected void bJ() {
		this.y = true;
	}

	public boolean bK() {
		return true;
	}

	public float a(bpt bpt, bpg bpg, fu fu, cfj cfj, cxa cxa, float float6) {
		return float6;
	}

	public boolean a(bpt bpt, bpg bpg, fu fu, cfj cfj, float float5) {
		return true;
	}

	public int bL() {
		return 3;
	}

	public dem bN() {
		return this.ai;
	}

	public fz bO() {
		return this.aj;
	}

	public boolean bP() {
		return false;
	}

	public void a(k k) {
		k.a("Entity Type", (l<String>)(() -> aoq.a(this.U()) + " (" + this.getClass().getCanonicalName() + ")"));
		k.a("Entity ID", this.g);
		k.a("Entity Name", (l<String>)(() -> this.P().getString()));
		k.a("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.cC(), this.cD(), this.cG()));
		k.a("Entity's Block location", k.a(aec.c(this.cC()), aec.c(this.cD()), aec.c(this.cG())));
		dem dem3 = this.cB();
		k.a("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", dem3.b, dem3.c, dem3.d));
		k.a("Entity's Passengers", (l<String>)(() -> this.cm().toString()));
		k.a("Entity's Vehicle", (l<String>)(() -> this.cs().toString()));
	}

	public void a_(UUID uUID) {
		this.ak = uUID;
		this.al = this.ak.toString();
	}

	public UUID bR() {
		return this.ak;
	}

	public String bS() {
		return this.al;
	}

	public String bT() {
		return this.al;
	}

	public boolean bU() {
		return true;
	}

	@Override
	public mr d() {
		return dfk.a(this.bC(), this.P()).a(nb -> nb.a(this.ca()).a(this.bS()));
	}

	public void a(@Nullable mr mr) {
		this.S.b(ax, Optional.ofNullable(mr));
	}

	@Nullable
	@Override
	public mr R() {
		return (mr)this.S.a(ax).orElse(null);
	}

	@Override
	public boolean Q() {
		return this.S.a(ax).isPresent();
	}

	public void n(boolean boolean1) {
		this.S.b(ay, boolean1);
	}

	public boolean bW() {
		return this.S.a(ay);
	}

	public final void l(double double1, double double2, double double3) {
		if (this.l instanceof zd) {
			bph bph8 = new bph(new fu(double1, double2, double3));
			((zd)this.l).i().a(zi.g, bph8, 0, this.V());
			this.l.d(bph8.b, bph8.c);
			this.a(double1, double2, double3);
		}
	}

	public void a(double double1, double double2, double double3) {
		if (this.l instanceof zd) {
			zd zd8 = (zd)this.l;
			this.b(double1, double2, double3, this.p, this.q);
			this.co().forEach(aom -> {
				zd8.b(aom);
				aom.aE = true;

				for (aom aom4 : aom.an) {
					aom.a(aom4, aom::b);
				}
			});
		}
	}

	public void a(tq<?> tq) {
		if (U.equals(tq)) {
			this.y_();
		}
	}

	public void y_() {
		aon aon2 = this.aH;
		apj apj3 = this.ab();
		aon aon4 = this.a(apj3);
		this.aH = aon4;
		this.aI = this.a(apj3, aon4);
		if (aon4.a < aon2.a) {
			double double5 = (double)aon4.a / 2.0;
			this.a(new deg(this.cC() - double5, this.cD(), this.cG() - double5, this.cC() + double5, this.cD() + (double)aon4.b, this.cG() + double5));
		} else {
			deg deg5 = this.cb();
			this.a(new deg(deg5.a, deg5.b, deg5.c, deg5.a + (double)aon4.a, deg5.b + (double)aon4.b, deg5.c + (double)aon4.a));
			if (aon4.a > aon2.a && !this.R && !this.l.v) {
				float float6 = aon2.a - aon4.a;
				this.a(apd.SELF, new dem((double)float6, 0.0, (double)float6));
			}
		}
	}

	public fz bY() {
		return fz.a((double)this.p);
	}

	public fz bZ() {
		return this.bY();
	}

	protected mv ca() {
		return new mv(mv.a.c, new mv.b(this.U(), this.bR(), this.P()));
	}

	public boolean a(ze ze) {
		return true;
	}

	public deg cb() {
		return this.as;
	}

	protected deg d(apj apj) {
		aon aon3 = this.a(apj);
		float float4 = aon3.a / 2.0F;
		dem dem5 = new dem(this.cC() - (double)float4, this.cD(), this.cG() - (double)float4);
		dem dem6 = new dem(this.cC() + (double)float4, this.cD() + (double)aon3.b, this.cG() + (double)float4);
		return new deg(dem5, dem6);
	}

	public void a(deg deg) {
		this.as = deg;
	}

	protected float a(apj apj, aon aon) {
		return aon.b * 0.85F;
	}

	public final float cd() {
		return this.aI;
	}

	public boolean a_(int integer, bki bki) {
		return false;
	}

	@Override
	public void a(mr mr, UUID uUID) {
	}

	public bqb cf() {
		return this.l;
	}

	@Nullable
	public MinecraftServer cg() {
		return this.l.l();
	}

	public ang a(bec bec, dem dem, anf anf) {
		return ang.PASS;
	}

	public boolean ch() {
		return false;
	}

	public void a(aoy aoy, aom aom) {
		if (aom instanceof aoy) {
			bny.a((aoy)aom, aoy);
		}

		bny.b(aoy, aom);
	}

	public void b(ze ze) {
	}

	public void c(ze ze) {
	}

	public float a(cap cap) {
		float float3 = aec.g(this.p);
		switch (cap) {
			case CLOCKWISE_180:
				return float3 + 180.0F;
			case COUNTERCLOCKWISE_90:
				return float3 + 270.0F;
			case CLOCKWISE_90:
				return float3 + 90.0F;
			default:
				return float3;
		}
	}

	public float a(bzj bzj) {
		float float3 = aec.g(this.p);
		switch (bzj) {
			case LEFT_RIGHT:
				return -float3;
			case FRONT_BACK:
				return 180.0F - float3;
			default:
				return float3;
		}
	}

	public boolean ci() {
		return false;
	}

	public boolean cj() {
		boolean boolean2 = this.aE;
		this.aE = false;
		return boolean2;
	}

	public boolean ck() {
		boolean boolean2 = this.aB;
		this.aB = false;
		return boolean2;
	}

	@Nullable
	public aom cl() {
		return null;
	}

	public List<aom> cm() {
		return (List<aom>)(this.an.isEmpty() ? Collections.emptyList() : Lists.<aom>newArrayList(this.an));
	}

	public boolean w(aom aom) {
		for (aom aom4 : this.cm()) {
			if (aom4.equals(aom)) {
				return true;
			}
		}

		return false;
	}

	public boolean a(Class<? extends aom> class1) {
		for (aom aom4 : this.cm()) {
			if (class1.isAssignableFrom(aom4.getClass())) {
				return true;
			}
		}

		return false;
	}

	public Collection<aom> cn() {
		Set<aom> set2 = Sets.<aom>newHashSet();

		for (aom aom4 : this.cm()) {
			set2.add(aom4);
			aom4.a(false, set2);
		}

		return set2;
	}

	public Stream<aom> co() {
		return Stream.concat(Stream.of(this), this.an.stream().flatMap(aom::co));
	}

	public boolean cp() {
		Set<aom> set2 = Sets.<aom>newHashSet();
		this.a(true, set2);
		return set2.size() == 1;
	}

	private void a(boolean boolean1, Set<aom> set) {
		for (aom aom5 : this.cm()) {
			if (!boolean1 || ze.class.isAssignableFrom(aom5.getClass())) {
				set.add(aom5);
			}

			aom5.a(boolean1, set);
		}
	}

	public aom cq() {
		aom aom2 = this;

		while (aom2.bn()) {
			aom2 = aom2.cs();
		}

		return aom2;
	}

	public boolean x(aom aom) {
		return this.cq() == aom.cq();
	}

	public boolean cr() {
		aom aom2 = this.cl();
		return aom2 instanceof bec ? ((bec)aom2).ey() : !this.l.v;
	}

	protected static dem a(double double1, double double2, float float3) {
		double double6 = (double1 + double2 + 1.0E-5F) / 2.0;
		float float8 = -aec.a(float3 * (float) (Math.PI / 180.0));
		float float9 = aec.b(float3 * (float) (Math.PI / 180.0));
		float float10 = Math.max(Math.abs(float8), Math.abs(float9));
		return new dem((double)float8 * double6 / (double)float10, 0.0, (double)float9 * double6 / (double)float10);
	}

	public dem c(aoy aoy) {
		return new dem(this.cC(), this.cb().e, this.cG());
	}

	@Nullable
	public aom cs() {
		return this.ao;
	}

	public cxf z_() {
		return cxf.NORMAL;
	}

	public acm ct() {
		return acm.NEUTRAL;
	}

	protected int cu() {
		return 1;
	}

	public cz cv() {
		return new cz(this, this.cz(), this.be(), this.l instanceof zd ? (zd)this.l : null, this.y(), this.P().getString(), this.d(), this.l.l(), this);
	}

	protected int y() {
		return 0;
	}

	public boolean k(int integer) {
		return this.y() >= integer;
	}

	@Override
	public boolean a() {
		return this.l.S().b(bpx.n);
	}

	@Override
	public boolean b() {
		return true;
	}

	@Override
	public boolean S_() {
		return true;
	}

	public void a(dg.a a, dem dem) {
		dem dem4 = a.a(this);
		double double5 = dem.b - dem4.b;
		double double7 = dem.c - dem4.c;
		double double9 = dem.d - dem4.d;
		double double11 = (double)aec.a(double5 * double5 + double9 * double9);
		this.q = aec.g((float)(-(aec.d(double7, double11) * 180.0F / (float)Math.PI)));
		this.p = aec.g((float)(aec.d(double9, double5) * 180.0F / (float)Math.PI) - 90.0F);
		this.k(this.p);
		this.s = this.q;
		this.r = this.p;
	}

	public boolean a(adf<cwz> adf, double double2) {
		deg deg5 = this.cb().h(0.001);
		int integer6 = aec.c(deg5.a);
		int integer7 = aec.f(deg5.d);
		int integer8 = aec.c(deg5.b);
		int integer9 = aec.f(deg5.e);
		int integer10 = aec.c(deg5.c);
		int integer11 = aec.f(deg5.f);
		if (!this.l.a(integer6, integer8, integer10, integer7, integer9, integer11)) {
			return false;
		} else {
			double double12 = 0.0;
			boolean boolean14 = this.bU();
			boolean boolean15 = false;
			dem dem16 = dem.a;
			int integer17 = 0;
			fu.a a18 = new fu.a();

			for (int integer19 = integer6; integer19 < integer7; integer19++) {
				for (int integer20 = integer8; integer20 < integer9; integer20++) {
					for (int integer21 = integer10; integer21 < integer11; integer21++) {
						a18.d(integer19, integer20, integer21);
						cxa cxa22 = this.l.b(a18);
						if (cxa22.a(adf)) {
							double double23 = (double)((float)integer20 + cxa22.a((bpg)this.l, a18));
							if (double23 >= deg5.b) {
								boolean15 = true;
								double12 = Math.max(double23 - deg5.b, double12);
								if (boolean14) {
									dem dem25 = cxa22.c(this.l, a18);
									if (double12 < 0.4) {
										dem25 = dem25.a(double12);
									}

									dem16 = dem16.e(dem25);
									integer17++;
								}
							}
						}
					}
				}
			}

			if (dem16.f() > 0.0) {
				if (integer17 > 0) {
					dem16 = dem16.a(1.0 / (double)integer17);
				}

				if (!(this instanceof bec)) {
					dem16 = dem16.d();
				}

				dem dem19 = this.cB();
				dem16 = dem16.a(double2 * 1.0);
				double double20 = 0.003;
				if (Math.abs(dem19.b) < 0.003 && Math.abs(dem19.d) < 0.003 && dem16.f() < 0.0045000000000000005) {
					dem16 = dem16.d().a(0.0045000000000000005);
				}

				this.e(this.cB().e(dem16));
			}

			this.M.put(adf, double12);
			return boolean15;
		}
	}

	public double b(adf<cwz> adf) {
		return this.M.getDouble(adf);
	}

	public double cw() {
		return (double)this.cd() < 0.4 ? 0.0 : 0.4;
	}

	public final float cx() {
		return this.aH.a;
	}

	public final float cy() {
		return this.aH.b;
	}

	public abstract ni<?> O();

	public aon a(apj apj) {
		return this.f.l();
	}

	public dem cz() {
		return this.ap;
	}

	public fu cA() {
		return this.aq;
	}

	public dem cB() {
		return this.ar;
	}

	public void e(dem dem) {
		this.ar = dem;
	}

	public void m(double double1, double double2, double double3) {
		this.e(new dem(double1, double2, double3));
	}

	public final double cC() {
		return this.ap.b;
	}

	public double c(double double1) {
		return this.ap.b + (double)this.cx() * double1;
	}

	public double d(double double1) {
		return this.c((2.0 * this.J.nextDouble() - 1.0) * double1);
	}

	public final double cD() {
		return this.ap.c;
	}

	public double e(double double1) {
		return this.ap.c + (double)this.cy() * double1;
	}

	public double cE() {
		return this.e(this.J.nextDouble());
	}

	public double cF() {
		return this.ap.c + (double)this.aI;
	}

	public final double cG() {
		return this.ap.d;
	}

	public double f(double double1) {
		return this.ap.d + (double)this.cx() * double1;
	}

	public double g(double double1) {
		return this.f((2.0 * this.J.nextDouble() - 1.0) * double1);
	}

	public void n(double double1, double double2, double double3) {
		if (this.ap.b != double1 || this.ap.c != double2 || this.ap.d != double3) {
			this.ap = new dem(double1, double2, double3);
			int integer8 = aec.c(double1);
			int integer9 = aec.c(double2);
			int integer10 = aec.c(double3);
			if (integer8 != this.aq.u() || integer9 != this.aq.v() || integer10 != this.aq.w()) {
				this.aq = new fu(integer8, integer9, integer10);
			}

			this.aB = true;
		}
	}

	public void cH() {
	}

	@FunctionalInterface
	public interface a {
		void accept(aom aom, double double2, double double3, double double4);
	}
}
