import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;

public abstract class bbk extends bcb implements bcf {
	private final avi<bbk> b = new avi<>(this, 1.0, 20, 15.0F);
	private final auq c = new auq(this, 1.2, false) {
		@Override
		public void d() {
			super.d();
			bbk.this.s(false);
		}

		@Override
		public void c() {
			super.c();
			bbk.this.s(true);
		}
	};

	protected bbk(aoq<? extends bbk> aoq, bqb bqb) {
		super(aoq, bqb);
		this.eM();
	}

	@Override
	protected void o() {
		this.br.a(2, new avl(this));
		this.br.a(3, new atz(this, 1.0));
		this.br.a(3, new ato(this, azk.class, 6.0F, 1.0, 1.2));
		this.br.a(5, new avw(this, 1.0));
		this.br.a(6, new auo(this, bec.class, 8.0F));
		this.br.a(6, new ave(this));
		this.bs.a(1, new awb(this));
		this.bs.a(2, new awc(this, bec.class, true));
		this.bs.a(3, new awc(this, ayt.class, true));
		this.bs.a(3, new awc(this, azi.class, 10, true, false, azi.bv));
	}

	public static apw.a m() {
		return bcb.eS().a(apx.d, 0.25);
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(this.eL(), 0.15F, 1.0F);
	}

	abstract ack eL();

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	public void k() {
		boolean boolean2 = this.eH();
		if (boolean2) {
			bki bki3 = this.b(aor.HEAD);
			if (!bki3.a()) {
				if (bki3.e()) {
					bki3.b(bki3.g() + this.J.nextInt(2));
					if (bki3.g() >= bki3.h()) {
						this.c(aor.HEAD);
						this.a(aor.HEAD, bki.b);
					}
				}

				boolean2 = false;
			}

			if (boolean2) {
				this.f(8);
			}
		}

		super.k();
	}

	@Override
	public void aW() {
		super.aW();
		if (this.cs() instanceof apg) {
			apg apg2 = (apg)this.cs();
			this.aH = apg2.aH;
		}
	}

	@Override
	protected void a(ane ane) {
		super.a(ane);
		this.a(aor.MAINHAND, new bki(bkk.kf));
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo = super.a(bqc, ane, apb, apo, le);
		this.a(ane);
		this.b(ane);
		this.eM();
		this.p(this.J.nextFloat() < 0.55F * ane.d());
		if (this.b(aor.HEAD).a()) {
			LocalDate localDate7 = LocalDate.now();
			int integer8 = localDate7.get(ChronoField.DAY_OF_MONTH);
			int integer9 = localDate7.get(ChronoField.MONTH_OF_YEAR);
			if (integer9 == 10 && integer8 == 31 && this.J.nextFloat() < 0.25F) {
				this.a(aor.HEAD, new bki(this.J.nextFloat() < 0.1F ? bvs.cV : bvs.cU));
				this.bu[aor.HEAD.b()] = 0.0F;
			}
		}

		return apo;
	}

	public void eM() {
		if (this.l != null && !this.l.v) {
			this.br.a(this.c);
			this.br.a(this.b);
			bki bki2 = this.b(bet.a(this, bkk.kf));
			if (bki2.b() == bkk.kf) {
				int integer3 = 20;
				if (this.l.ac() != and.HARD) {
					integer3 = 40;
				}

				this.b.a(integer3);
				this.br.a(4, this.b);
			} else {
				this.br.a(4, this.c);
			}
		}
	}

	@Override
	public void a(aoy aoy, float float2) {
		bki bki4 = this.f(this.b(bet.a(this, bkk.kf)));
		beg beg5 = this.b(bki4, float2);
		double double6 = aoy.cC() - this.cC();
		double double8 = aoy.e(0.3333333333333333) - beg5.cD();
		double double10 = aoy.cG() - this.cG();
		double double12 = (double)aec.a(double6 * double6 + double10 * double10);
		beg5.c(double6, double8 + double12 * 0.2F, double10, 1.6F, (float)(14 - this.l.ac().a() * 4));
		this.a(acl.nw, 1.0F, 1.0F / (this.cX().nextFloat() * 0.4F + 0.8F));
		this.l.c(beg5);
	}

	protected beg b(bki bki, float float2) {
		return bet.a(this, bki, float2);
	}

	@Override
	public boolean a(bkv bkv) {
		return bkv == bkk.kf;
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.eM();
	}

	@Override
	public void a(aor aor, bki bki) {
		super.a(aor, bki);
		if (!this.l.v) {
			this.eM();
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 1.74F;
	}

	@Override
	public double aX() {
		return -0.6;
	}
}
