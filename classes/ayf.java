import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;

public class ayf extends aye {
	private static final tq<Byte> b = tt.a(ayf.class, ts.a);
	private static final axs c = new axs().a(4.0).b();
	private fu d;

	public ayf(aoq<? extends ayf> aoq, bqb bqb) {
		super(aoq, bqb);
		this.t(true);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)0);
	}

	@Override
	protected float dF() {
		return 0.1F;
	}

	@Override
	protected float dG() {
		return super.dG() * 0.95F;
	}

	@Nullable
	@Override
	public ack I() {
		return this.eJ() && this.J.nextInt(4) != 0 ? null : acl.ap;
	}

	@Override
	protected ack e(anw anw) {
		return acl.ar;
	}

	@Override
	protected ack dp() {
		return acl.aq;
	}

	@Override
	public boolean aR() {
		return false;
	}

	@Override
	protected void C(aom aom) {
	}

	@Override
	protected void dP() {
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 6.0);
	}

	public boolean eJ() {
		return (this.S.a(b) & 1) != 0;
	}

	public void t(boolean boolean1) {
		byte byte3 = this.S.a(b);
		if (boolean1) {
			this.S.b(b, (byte)(byte3 | 1));
		} else {
			this.S.b(b, (byte)(byte3 & -2));
		}
	}

	@Override
	public void j() {
		super.j();
		if (this.eJ()) {
			this.e(dem.a);
			this.n(this.cC(), (double)aec.c(this.cD()) + 1.0 - (double)this.cy(), this.cG());
		} else {
			this.e(this.cB().d(1.0, 0.6, 1.0));
		}
	}

	@Override
	protected void N() {
		super.N();
		fu fu2 = this.cA();
		fu fu3 = fu2.b();
		if (this.eJ()) {
			boolean boolean4 = this.av();
			if (this.l.d_(fu3).g(this.l, fu2)) {
				if (this.J.nextInt(200) == 0) {
					this.aJ = (float)this.J.nextInt(360);
				}

				if (this.l.a(c, this) != null) {
					this.t(false);
					if (!boolean4) {
						this.l.a(null, 1025, fu2, 0);
					}
				}
			} else {
				this.t(false);
				if (!boolean4) {
					this.l.a(null, 1025, fu2, 0);
				}
			}
		} else {
			if (this.d != null && (!this.l.w(this.d) || this.d.v() < 1)) {
				this.d = null;
			}

			if (this.d == null || this.J.nextInt(30) == 0 || this.d.a(this.cz(), 2.0)) {
				this.d = new fu(
					this.cC() + (double)this.J.nextInt(7) - (double)this.J.nextInt(7),
					this.cD() + (double)this.J.nextInt(6) - 2.0,
					this.cG() + (double)this.J.nextInt(7) - (double)this.J.nextInt(7)
				);
			}

			double double4 = (double)this.d.u() + 0.5 - this.cC();
			double double6 = (double)this.d.v() + 0.1 - this.cD();
			double double8 = (double)this.d.w() + 0.5 - this.cG();
			dem dem10 = this.cB();
			dem dem11 = dem10.b(
				(Math.signum(double4) * 0.5 - dem10.b) * 0.1F, (Math.signum(double6) * 0.7F - dem10.c) * 0.1F, (Math.signum(double8) * 0.5 - dem10.d) * 0.1F
			);
			this.e(dem11);
			float float12 = (float)(aec.d(dem11.d, dem11.b) * 180.0F / (float)Math.PI) - 90.0F;
			float float13 = aec.g(float12 - this.p);
			this.ba = 0.5F;
			this.p += float13;
			if (this.J.nextInt(100) == 0 && this.l.d_(fu3).g(this.l, fu3)) {
				this.t(true);
			}
		}
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
	}

	@Override
	public boolean bP() {
		return true;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			if (!this.l.v && this.eJ()) {
				this.t(false);
			}

			return super.a(anw, float2);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.S.b(b, le.f("BatFlags"));
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("BatFlags", this.S.a(b));
	}

	public static boolean b(aoq<ayf> aoq, bqc bqc, apb apb, fu fu, Random random) {
		if (fu.v() >= bqc.t_()) {
			return false;
		} else {
			int integer6 = bqc.B(fu);
			int integer7 = 4;
			if (eK()) {
				integer7 = 7;
			} else if (random.nextBoolean()) {
				return false;
			}

			return integer6 > random.nextInt(integer7) ? false : a(aoq, bqc, apb, fu, random);
		}
	}

	private static boolean eK() {
		LocalDate localDate1 = LocalDate.now();
		int integer2 = localDate1.get(ChronoField.DAY_OF_MONTH);
		int integer3 = localDate1.get(ChronoField.MONTH_OF_YEAR);
		return integer3 == 10 && integer2 >= 20 || integer3 == 11 && integer2 <= 3;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b / 2.0F;
	}
}
