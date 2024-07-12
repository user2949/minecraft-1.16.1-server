import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bwe extends byp implements bly {
	public static final cgd a = byp.aq;
	@Nullable
	private cfo b;
	@Nullable
	private cfo c;
	@Nullable
	private cfo d;
	@Nullable
	private cfo e;
	private static final Predicate<cfj> f = cfj -> cfj != null && (cfj.a(bvs.cU) || cfj.a(bvs.cV));

	protected bwe(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH));
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			this.a(bqb, fu);
		}
	}

	public boolean a(bqd bqd, fu fu) {
		return this.c().a(bqd, fu) != null || this.e().a(bqd, fu) != null;
	}

	private void a(bqb bqb, fu fu) {
		cfo.b b4 = this.d().a(bqb, fu);
		if (b4 != null) {
			for (int integer5 = 0; integer5 < this.d().b(); integer5++) {
				cfn cfn6 = b4.a(0, integer5, 0);
				bqb.a(cfn6.d(), bvs.a.n(), 2);
				bqb.c(2001, cfn6.d(), bvr.i(cfn6.a()));
			}

			azf azf5 = aoq.ay.a(bqb);
			fu fu6 = b4.a(0, 2, 0).d();
			azf5.b((double)fu6.u() + 0.5, (double)fu6.v() + 0.05, (double)fu6.w() + 0.5, 0.0F, 0.0F);
			bqb.c(azf5);

			for (ze ze8 : bqb.a(ze.class, azf5.cb().g(5.0))) {
				aa.n.a(ze8, azf5);
			}

			for (int integer7 = 0; integer7 < this.d().b(); integer7++) {
				cfn cfn8 = b4.a(0, integer7, 0);
				bqb.a(cfn8.d(), bvs.a);
			}
		} else {
			b4 = this.t().a(bqb, fu);
			if (b4 != null) {
				for (int integer5 = 0; integer5 < this.t().c(); integer5++) {
					for (int integer6 = 0; integer6 < this.t().b(); integer6++) {
						cfn cfn7 = b4.a(integer5, integer6, 0);
						bqb.a(cfn7.d(), bvs.a.n(), 2);
						bqb.c(2001, cfn7.d(), bvr.i(cfn7.a()));
					}
				}

				fu fu5 = b4.a(1, 2, 0).d();
				ayt ayt6 = aoq.K.a(bqb);
				ayt6.u(true);
				ayt6.b((double)fu5.u() + 0.5, (double)fu5.v() + 0.05, (double)fu5.w() + 0.5, 0.0F, 0.0F);
				bqb.c(ayt6);

				for (ze ze8 : bqb.a(ze.class, ayt6.cb().g(5.0))) {
					aa.n.a(ze8, ayt6);
				}

				for (int integer7 = 0; integer7 < this.t().c(); integer7++) {
					for (int integer8 = 0; integer8 < this.t().b(); integer8++) {
						cfn cfn9 = b4.a(integer7, integer8, 0);
						bqb.a(cfn9.d(), bvs.a);
					}
				}
			}
		}
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.f().f());
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwe.a);
	}

	private cfo c() {
		if (this.b == null) {
			this.b = cfp.a().a(" ", "#", "#").a('#', cfn.a(cft.a(bvs.cE))).b();
		}

		return this.b;
	}

	private cfo d() {
		if (this.c == null) {
			this.c = cfp.a().a("^", "#", "#").a('^', cfn.a(f)).a('#', cfn.a(cft.a(bvs.cE))).b();
		}

		return this.c;
	}

	private cfo e() {
		if (this.d == null) {
			this.d = cfp.a().a("~ ~", "###", "~#~").a('#', cfn.a(cft.a(bvs.bF))).a('~', cfn.a(cfr.a(cxd.a))).b();
		}

		return this.d;
	}

	private cfo t() {
		if (this.e == null) {
			this.e = cfp.a().a("~^~", "###", "~#~").a('^', cfn.a(f)).a('#', cfn.a(cft.a(bvs.bF))).a('~', cfn.a(cfr.a(cxd.a))).b();
		}

		return this.e;
	}
}
