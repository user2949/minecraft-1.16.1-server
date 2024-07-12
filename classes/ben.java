import java.util.OptionalInt;
import javax.annotation.Nullable;

public class ben extends bes {
	private static final tq<bki> b = tt.a(ben.class, ts.g);
	private static final tq<OptionalInt> c = tt.a(ben.class, ts.r);
	private static final tq<Boolean> d = tt.a(ben.class, ts.i);
	private int e;
	private int f;
	private aoy g;

	public ben(aoq<? extends ben> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public ben(bqb bqb, double double2, double double3, double double4, bki bki) {
		super(aoq.B, bqb);
		this.e = 0;
		this.d(double2, double3, double4);
		int integer10 = 1;
		if (!bki.a() && bki.n()) {
			this.S.b(b, bki.i());
			integer10 += bki.a("Fireworks").f("Flight");
		}

		this.m(this.J.nextGaussian() * 0.001, 0.05, this.J.nextGaussian() * 0.001);
		this.f = 10 * integer10 + this.J.nextInt(6) + this.J.nextInt(7);
	}

	public ben(bqb bqb, @Nullable aom aom, double double3, double double4, double double5, bki bki) {
		this(bqb, double3, double4, double5, bki);
		this.b(aom);
	}

	public ben(bqb bqb, bki bki, aoy aoy) {
		this(bqb, aoy, aoy.cC(), aoy.cD(), aoy.cG(), bki);
		this.S.b(c, OptionalInt.of(aoy.V()));
		this.g = aoy;
	}

	public ben(bqb bqb, bki bki, double double3, double double4, double double5, boolean boolean6) {
		this(bqb, double3, double4, double5, bki);
		this.S.b(d, boolean6);
	}

	public ben(bqb bqb, bki bki, aom aom, double double4, double double5, double double6, boolean boolean7) {
		this(bqb, bki, double4, double5, double6, boolean7);
		this.b(aom);
	}

	@Override
	protected void e() {
		this.S.a(b, bki.b);
		this.S.a(c, OptionalInt.empty());
		this.S.a(d, false);
	}

	@Override
	public void j() {
		super.j();
		if (this.n()) {
			if (this.g == null) {
				this.S.a(c).ifPresent(integer -> {
					aom aom3 = this.l.a(integer);
					if (aom3 instanceof aoy) {
						this.g = (aoy)aom3;
					}
				});
			}

			if (this.g != null) {
				if (this.g.ee()) {
					dem dem2 = this.g.bd();
					double double3 = 1.5;
					double double5 = 0.1;
					dem dem7 = this.g.cB();
					this.g.e(dem7.b(dem2.b * 0.1 + (dem2.b * 1.5 - dem7.b) * 0.5, dem2.c * 0.1 + (dem2.c * 1.5 - dem7.c) * 0.5, dem2.d * 0.1 + (dem2.d * 1.5 - dem7.d) * 0.5));
				}

				this.d(this.g.cC(), this.g.cD(), this.g.cG());
				this.e(this.g.cB());
			}
		} else {
			if (!this.h()) {
				this.e(this.cB().d(1.15, 1.0, 1.15).b(0.0, 0.04, 0.0));
			}

			dem dem2 = this.cB();
			this.a(apd.SELF, dem2);
			this.e(dem2);
		}

		dej dej2 = bet.a(this, this::a, bpj.a.COLLIDER);
		if (!this.H) {
			this.a(dej2);
			this.ad = true;
		}

		this.x();
		if (this.e == 0 && !this.av()) {
			this.l.a(null, this.cC(), this.cD(), this.cG(), acl.ee, acm.AMBIENT, 3.0F, 1.0F);
		}

		this.e++;
		if (this.l.v && this.e % 2 < 2) {
			this.l.a(hh.y, this.cC(), this.cD() - 0.3, this.cG(), this.J.nextGaussian() * 0.05, -this.cB().c * 0.5, this.J.nextGaussian() * 0.05);
		}

		if (!this.l.v && this.e > this.f) {
			this.i();
		}
	}

	private void i() {
		this.l.a(this, (byte)17);
		this.m();
		this.aa();
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		if (!this.l.v) {
			this.i();
		}
	}

	@Override
	protected void a(deh deh) {
		fu fu3 = new fu(deh.a());
		this.l.d_(fu3).a(this.l, fu3, this);
		if (!this.l.s_() && this.k()) {
			this.i();
		}

		super.a(deh);
	}

	private boolean k() {
		bki bki2 = this.S.a(b);
		le le3 = bki2.a() ? null : bki2.b("Fireworks");
		lk lk4 = le3 != null ? le3.d("Explosions", 10) : null;
		return lk4 != null && !lk4.isEmpty();
	}

	private void m() {
		float float2 = 0.0F;
		bki bki3 = this.S.a(b);
		le le4 = bki3.a() ? null : bki3.b("Fireworks");
		lk lk5 = le4 != null ? le4.d("Explosions", 10) : null;
		if (lk5 != null && !lk5.isEmpty()) {
			float2 = 5.0F + (float)(lk5.size() * 2);
		}

		if (float2 > 0.0F) {
			if (this.g != null) {
				this.g.a(anw.a(this, this.v()), 5.0F + (float)(lk5.size() * 2));
			}

			double double6 = 5.0;
			dem dem8 = this.cz();

			for (aoy aoy11 : this.l.a(aoy.class, this.cb().g(5.0))) {
				if (aoy11 != this.g && !(this.h(aoy11) > 25.0)) {
					boolean boolean12 = false;

					for (int integer13 = 0; integer13 < 2; integer13++) {
						dem dem14 = new dem(aoy11.cC(), aoy11.e(0.5 * (double)integer13), aoy11.cG());
						dej dej15 = this.l.a(new bpj(dem8, dem14, bpj.a.COLLIDER, bpj.b.NONE, this));
						if (dej15.c() == dej.a.MISS) {
							boolean12 = true;
							break;
						}
					}

					if (boolean12) {
						float float13 = float2 * (float)Math.sqrt((5.0 - (double)this.g(aoy11)) / 5.0);
						aoy11.a(anw.a(this, this.v()), float13);
					}
				}
			}
		}
	}

	private boolean n() {
		return this.S.a(c).isPresent();
	}

	@Override
	public boolean h() {
		return this.S.a(d);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Life", this.e);
		le.b("LifeTime", this.f);
		bki bki3 = this.S.a(b);
		if (!bki3.a()) {
			le.a("FireworksItem", bki3.b(new le()));
		}

		le.a("ShotAtAngle", this.S.a(d));
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.e = le.h("Life");
		this.f = le.h("LifeTime");
		bki bki3 = bki.a(le.p("FireworksItem"));
		if (!bki3.a()) {
			this.S.b(b, bki3);
		}

		if (le.e("ShotAtAngle")) {
			this.S.b(d, le.q("ShotAtAngle"));
		}
	}

	@Override
	public boolean bH() {
		return false;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
