import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cem extends cen implements ceo {
	private static final Logger a = LogManager.getLogger();
	private long b;
	private int c;
	@Nullable
	private fu g;
	private boolean h;

	public cem() {
		super(cdm.u);
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("Age", this.b);
		if (this.g != null) {
			le.a("ExitPortal", lq.a(this.g));
		}

		if (this.h) {
			le.a("ExactTeleport", this.h);
		}

		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.b = le.i("Age");
		if (le.c("ExitPortal", 10)) {
			this.g = lq.b(le.p("ExitPortal"));
		}

		this.h = le.q("ExactTeleport");
	}

	@Override
	public void al_() {
		boolean boolean2 = this.d();
		boolean boolean3 = this.f();
		this.b++;
		if (boolean3) {
			this.c--;
		} else if (!this.d.v) {
			List<aom> list4 = this.d.a(aom.class, new deg(this.o()));
			if (!list4.isEmpty()) {
				this.a((aom)list4.get(this.d.t.nextInt(list4.size())));
			}

			if (this.b % 2400L == 0L) {
				this.h();
			}
		}

		if (boolean2 != this.d() || boolean3 != this.f()) {
			this.Z_();
		}
	}

	public boolean d() {
		return this.b < 200L;
	}

	public boolean f() {
		return this.c > 0;
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 8, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	public void h() {
		if (!this.d.v) {
			this.c = 40;
			this.d.a(this.o(), this.p().b(), 1, 0);
			this.Z_();
		}
	}

	@Override
	public boolean a_(int integer1, int integer2) {
		if (integer1 == 1) {
			this.c = 40;
			return true;
		} else {
			return super.a_(integer1, integer2);
		}
	}

	public void a(aom aom) {
		if (this.d instanceof zd && !this.f()) {
			this.c = 100;
			if (this.g == null && this.d.W() == bqb.i) {
				this.a((zd)this.d);
			}

			if (this.g != null) {
				fu fu3 = this.h ? this.g : this.k();
				aom aom4;
				if (aom instanceof bfb) {
					aom aom5 = ((bfb)aom).v();
					if (aom5 instanceof ze) {
						aa.d.a((ze)aom5, this.d.d_(this.o()));
					}

					if (aom5 != null) {
						aom4 = aom5;
						aom.aa();
					} else {
						aom4 = aom;
					}
				} else {
					aom4 = aom.cq();
				}

				aom4.l((double)fu3.u() + 0.5, (double)fu3.v(), (double)fu3.w() + 0.5);
			}

			this.h();
		}
	}

	private fu k() {
		fu fu2 = a(this.d, this.g, 5, false);
		a.debug("Best exit position for portal at {} is {}", this.g, fu2);
		return fu2.b();
	}

	private void a(zd zd) {
		dem dem3 = new dem((double)this.o().u(), 0.0, (double)this.o().w()).d();
		dem dem4 = dem3.a(1024.0);

		for (int integer5 = 16; a(zd, dem4).b() > 0 && integer5-- > 0; dem4 = dem4.e(dem3.a(-16.0))) {
			a.debug("Skipping backwards past nonempty chunk at {}", dem4);
		}

		for (int var6 = 16; a(zd, dem4).b() == 0 && var6-- > 0; dem4 = dem4.e(dem3.a(16.0))) {
			a.debug("Skipping forward past empty chunk at {}", dem4);
		}

		a.debug("Found chunk at {}", dem4);
		chj chj6 = a(zd, dem4);
		this.g = a(chj6);
		if (this.g == null) {
			this.g = new fu(dem4.b + 0.5, 75.0, dem4.d + 0.5);
			a.debug("Failed to find suitable block, settling on {}", this.g);
			ckt.B.b(cnr.k).a(zd, zd.a(), zd.i().g(), new Random(this.g.a()), this.g);
		} else {
			a.debug("Found block at {}", this.g);
		}

		this.g = a(zd, this.g, 16, true);
		a.debug("Creating portal at {}", this.g);
		this.g = this.g.b(10);
		this.a(zd, this.g);
		this.Z_();
	}

	private static fu a(bpg bpg, fu fu, int integer, boolean boolean4) {
		fu fu5 = null;

		for (int integer6 = -integer; integer6 <= integer; integer6++) {
			for (int integer7 = -integer; integer7 <= integer; integer7++) {
				if (integer6 != 0 || integer7 != 0 || boolean4) {
					for (int integer8 = 255; integer8 > (fu5 == null ? 0 : fu5.v()); integer8--) {
						fu fu9 = new fu(fu.u() + integer6, integer8, fu.w() + integer7);
						cfj cfj10 = bpg.d_(fu9);
						if (cfj10.r(bpg, fu9) && (boolean4 || !cfj10.a(bvs.z))) {
							fu5 = fu9;
							break;
						}
					}
				}
			}
		}

		return fu5 == null ? fu : fu5;
	}

	private static chj a(bqb bqb, dem dem) {
		return bqb.d(aec.c(dem.b / 16.0), aec.c(dem.d / 16.0));
	}

	@Nullable
	private static fu a(chj chj) {
		bph bph2 = chj.g();
		fu fu3 = new fu(bph2.d(), 30, bph2.e());
		int integer4 = chj.b() + 16 - 1;
		fu fu5 = new fu(bph2.f(), integer4, bph2.g());
		fu fu6 = null;
		double double7 = 0.0;

		for (fu fu10 : fu.a(fu3, fu5)) {
			cfj cfj11 = chj.d_(fu10);
			fu fu12 = fu10.b();
			fu fu13 = fu10.b(2);
			if (cfj11.a(bvs.ee) && !chj.d_(fu12).r(chj, fu12) && !chj.d_(fu13).r(chj, fu13)) {
				double double14 = fu10.a(0.0, 0.0, 0.0, true);
				if (fu6 == null || double14 < double7) {
					fu6 = fu10;
					double7 = double14;
				}
			}
		}

		return fu6;
	}

	private void a(zd zd, fu fu) {
		ckt.C.b(cnq.a(this.o(), false)).a(zd, zd.a(), zd.i().g(), new Random(), fu);
	}

	public void a(fu fu, boolean boolean2) {
		this.h = boolean2;
		this.g = fu;
	}
}
