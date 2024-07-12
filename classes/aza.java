import java.util.List;
import java.util.function.Predicate;

public class aza extends ayh {
	private static final tq<Integer> b = tt.a(aza.class, ts.b);
	private int c;
	private int d;
	private static final Predicate<aoy> bv = aoy -> {
		if (aoy == null) {
			return false;
		} else {
			return !(aoy instanceof bec) || !aoy.a_() && !((bec)aoy).b_() ? aoy.dB() != apc.e : false;
		}
	};

	public aza(aoq<? extends aza> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, 0);
	}

	public int eO() {
		return this.S.a(b);
	}

	public void b(int integer) {
		this.S.b(b, integer);
	}

	@Override
	public void a(tq<?> tq) {
		if (b.equals(tq)) {
			this.y_();
		}

		super.a(tq);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("PuffState", this.eO());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.b(le.h("PuffState"));
	}

	@Override
	protected bki eL() {
		return new bki(bkk.lU);
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(1, new aza.a(this));
	}

	@Override
	public void j() {
		if (!this.l.v && this.aU() && this.dR()) {
			if (this.c > 0) {
				if (this.eO() == 0) {
					this.a(acl.lL, this.dF(), this.dG());
					this.b(1);
				} else if (this.c > 40 && this.eO() == 1) {
					this.a(acl.lL, this.dF(), this.dG());
					this.b(2);
				}

				this.c++;
			} else if (this.eO() != 0) {
				if (this.d > 60 && this.eO() == 2) {
					this.a(acl.lK, this.dF(), this.dG());
					this.b(1);
				} else if (this.d > 100 && this.eO() == 1) {
					this.a(acl.lK, this.dF(), this.dG());
					this.b(0);
				}

				this.d++;
			}
		}

		super.j();
	}

	@Override
	public void k() {
		super.k();
		if (this.aU() && this.eO() > 0) {
			for (aoz aoz4 : this.l.a(aoz.class, this.cb().g(0.3), bv)) {
				if (aoz4.aU()) {
					this.a(aoz4);
				}
			}
		}
	}

	private void a(aoz aoz) {
		int integer3 = this.eO();
		if (aoz.a(anw.c(this), (float)(1 + integer3))) {
			aoz.c(new aog(aoi.s, 60 * integer3, 0));
			this.a(acl.lP, 1.0F, 1.0F);
		}
	}

	@Override
	public void a_(bec bec) {
		int integer3 = this.eO();
		if (bec instanceof ze && integer3 > 0 && bec.a(anw.c(this), (float)(1 + integer3))) {
			if (!this.av()) {
				((ze)bec).b.a(new oq(oq.j, 0.0F));
			}

			bec.c(new aog(aoi.s, 60 * integer3, 0));
		}
	}

	@Override
	protected ack I() {
		return acl.lJ;
	}

	@Override
	protected ack dp() {
		return acl.lM;
	}

	@Override
	protected ack e(anw anw) {
		return acl.lO;
	}

	@Override
	protected ack eN() {
		return acl.lN;
	}

	@Override
	public aon a(apj apj) {
		return super.a(apj).a(s(this.eO()));
	}

	private static float s(int integer) {
		switch (integer) {
			case 0:
				return 0.5F;
			case 1:
				return 0.7F;
			default:
				return 1.0F;
		}
	}

	static class a extends aug {
		private final aza a;

		public a(aza aza) {
			this.a = aza;
		}

		@Override
		public boolean a() {
			List<aoy> list2 = this.a.l.a(aoy.class, this.a.cb().g(2.0), aza.bv);
			return !list2.isEmpty();
		}

		@Override
		public void c() {
			this.a.c = 1;
			this.a.d = 0;
		}

		@Override
		public void d() {
			this.a.c = 0;
		}

		@Override
		public boolean b() {
			List<aoy> list2 = this.a.l.a(aoy.class, this.a.cb().g(2.0), aza.bv);
			return !list2.isEmpty();
		}
	}
}
