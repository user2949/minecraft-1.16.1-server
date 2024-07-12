import java.util.Random;
import javax.annotation.Nullable;

public class avk extends auu {
	private final bvr g;
	private final aoz h;
	private int i;

	public avk(bvr bvr, apg apg, double double3, int integer) {
		super(apg, double3, 24, integer);
		this.g = bvr;
		this.h = apg;
	}

	@Override
	public boolean a() {
		if (!this.h.l.S().b(bpx.b)) {
			return false;
		} else if (this.c > 0) {
			this.c--;
			return false;
		} else if (this.m()) {
			this.c = 20;
			return true;
		} else {
			this.c = this.a(this.a);
			return false;
		}
	}

	private boolean m() {
		return this.e != null && this.a((bqd)this.a.l, this.e) ? true : this.l();
	}

	@Override
	public void d() {
		super.d();
		this.h.C = 1.0F;
	}

	@Override
	public void c() {
		super.c();
		this.i = 0;
	}

	public void a(bqc bqc, fu fu) {
	}

	public void a(bqb bqb, fu fu) {
	}

	@Override
	public void e() {
		super.e();
		bqb bqb2 = this.h.l;
		fu fu3 = this.h.cA();
		fu fu4 = this.a(fu3, bqb2);
		Random random5 = this.h.cX();
		if (this.k() && fu4 != null) {
			if (this.i > 0) {
				dem dem6 = this.h.cB();
				this.h.m(dem6.b, 0.3, dem6.d);
				if (!bqb2.v) {
					double double7 = 0.08;
					((zd)bqb2)
						.a(
							new he(hh.I, new bki(bkk.mg)),
							(double)fu4.u() + 0.5,
							(double)fu4.v() + 0.7,
							(double)fu4.w() + 0.5,
							3,
							((double)random5.nextFloat() - 0.5) * 0.08,
							((double)random5.nextFloat() - 0.5) * 0.08,
							((double)random5.nextFloat() - 0.5) * 0.08,
							0.15F
						);
				}
			}

			if (this.i % 2 == 0) {
				dem dem6 = this.h.cB();
				this.h.m(dem6.b, -0.3, dem6.d);
				if (this.i % 6 == 0) {
					this.a((bqc)bqb2, this.e);
				}
			}

			if (this.i > 60) {
				bqb2.a(fu4, false);
				if (!bqb2.v) {
					for (int integer6 = 0; integer6 < 20; integer6++) {
						double double7 = random5.nextGaussian() * 0.02;
						double double9 = random5.nextGaussian() * 0.02;
						double double11 = random5.nextGaussian() * 0.02;
						((zd)bqb2).a(hh.P, (double)fu4.u() + 0.5, (double)fu4.v(), (double)fu4.w() + 0.5, 1, double7, double9, double11, 0.15F);
					}

					this.a(bqb2, fu4);
				}
			}

			this.i++;
		}
	}

	@Nullable
	private fu a(fu fu, bpg bpg) {
		if (bpg.d_(fu).a(this.g)) {
			return fu;
		} else {
			fu[] arr4 = new fu[]{fu.c(), fu.f(), fu.g(), fu.d(), fu.e(), fu.c().c()};

			for (fu fu8 : arr4) {
				if (bpg.d_(fu8).a(this.g)) {
					return fu8;
				}
			}

			return null;
		}
	}

	@Override
	protected boolean a(bqd bqd, fu fu) {
		cgy cgy4 = bqd.a(fu.u() >> 4, fu.w() >> 4, chc.m, false);
		return cgy4 == null ? false : cgy4.d_(fu).a(this.g) && cgy4.d_(fu.b()).g() && cgy4.d_(fu.b(2)).g();
	}
}
