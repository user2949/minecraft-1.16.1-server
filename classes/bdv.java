import bdt.f;
import java.util.EnumSet;
import javax.annotation.Nullable;

public class bdv extends bdk {
	@Nullable
	private fu bw;
	private int bx;

	public bdv(aoq<? extends bdv> aoq, bqb bqb) {
		super(aoq, bqb);
		this.k = true;
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(0, new avu<>(this, bmd.a(new bki(bkk.nv), bme.h), acl.qn, bdv -> !this.l.J() && !bdv.bB()));
		this.br.a(0, new avu<>(this, new bki(bkk.lT), acl.qs, bdv -> this.l.J() && bdv.bB()));
		this.br.a(1, new avs(this));
		this.br.a(1, new ato(this, bcu.class, 8.0F, 0.5, 0.5));
		this.br.a(1, new ato(this, bbu.class, 12.0F, 0.5, 0.5));
		this.br.a(1, new ato(this, bcq.class, 8.0F, 0.5, 0.5));
		this.br.a(1, new ato(this, bcp.class, 8.0F, 0.5, 0.5));
		this.br.a(1, new ato(this, bce.class, 15.0F, 0.5, 0.5));
		this.br.a(1, new ato(this, bbz.class, 12.0F, 0.5, 0.5));
		this.br.a(1, new ato(this, bct.class, 10.0F, 0.5, 0.5));
		this.br.a(1, new avb(this, 0.5));
		this.br.a(1, new aup(this));
		this.br.a(2, new bdv.a(this, 2.0, 0.35));
		this.br.a(4, new auv(this, 0.35));
		this.br.a(8, new avw(this, 0.35));
		this.br.a(9, new auj(this, bec.class, 3.0F, 1.0F));
		this.br.a(10, new auo(this, aoz.class, 8.0F));
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		return null;
	}

	@Override
	public boolean eQ() {
		return false;
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() != bkk.oF && this.aU() && !this.eO() && !this.x_()) {
			if (anf == anf.MAIN_HAND) {
				bec.a(acu.R);
			}

			if (this.eP().isEmpty()) {
				return ang.a(this.l.v);
			} else {
				if (!this.l.v) {
					this.f(bec);
					this.a(bec, this.d(), 1);
				}

				return ang.a(this.l.v);
			}
		} else {
			return super.b(bec, anf);
		}
	}

	@Override
	protected void eW() {
		f[] arr2 = bdt.b.get(1);
		f[] arr3 = bdt.b.get(2);
		if (arr2 != null && arr3 != null) {
			bpa bpa4 = this.eP();
			this.a(bpa4, arr2, 5);
			int integer5 = this.J.nextInt(arr3.length);
			f f6 = arr3[integer5];
			boz boz7 = f6.a(this, this.J);
			if (boz7 != null) {
				bpa4.add(boz7);
			}
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("DespawnDelay", this.bx);
		if (this.bw != null) {
			le.a("WanderTarget", lq.a(this.bw));
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("DespawnDelay", 99)) {
			this.bx = le.h("DespawnDelay");
		}

		if (le.e("WanderTarget")) {
			this.bw = lq.b(le.p("WanderTarget"));
		}

		this.c_(Math.max(0, this.i()));
	}

	@Override
	public boolean h(double double1) {
		return false;
	}

	@Override
	protected void b(boz boz) {
		if (boz.s()) {
			int integer3 = 3 + this.J.nextInt(4);
			this.l.c(new aos(this.l, this.cC(), this.cD() + 0.5, this.cG(), integer3));
		}
	}

	@Override
	protected ack I() {
		return this.eO() ? acl.qt : acl.ql;
	}

	@Override
	protected ack e(anw anw) {
		return acl.qq;
	}

	@Override
	protected ack dp() {
		return acl.qm;
	}

	@Override
	protected ack c(bki bki) {
		bke bke3 = bki.b();
		return bke3 == bkk.lT ? acl.qo : acl.qp;
	}

	@Override
	protected ack t(boolean boolean1) {
		return boolean1 ? acl.qu : acl.qr;
	}

	@Override
	public ack eR() {
		return acl.qu;
	}

	public void u(int integer) {
		this.bx = integer;
	}

	public int eX() {
		return this.bx;
	}

	@Override
	public void k() {
		super.k();
		if (!this.l.v) {
			this.eY();
		}
	}

	private void eY() {
		if (this.bx > 0 && !this.eO() && --this.bx == 0) {
			this.aa();
		}
	}

	public void g(@Nullable fu fu) {
		this.bw = fu;
	}

	@Nullable
	private fu eZ() {
		return this.bw;
	}

	class a extends aug {
		final bdv a;
		final double b;
		final double c;

		a(bdv bdv2, double double3, double double4) {
			this.a = bdv2;
			this.b = double3;
			this.c = double4;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public void d() {
			this.a.g(null);
			bdv.this.bq.o();
		}

		@Override
		public boolean a() {
			fu fu2 = this.a.eZ();
			return fu2 != null && this.a(fu2, this.b);
		}

		@Override
		public void e() {
			fu fu2 = this.a.eZ();
			if (fu2 != null && bdv.this.bq.m()) {
				if (this.a(fu2, 10.0)) {
					dem dem3 = new dem((double)fu2.u() - this.a.cC(), (double)fu2.v() - this.a.cD(), (double)fu2.w() - this.a.cG()).d();
					dem dem4 = dem3.a(10.0).b(this.a.cC(), this.a.cD(), this.a.cG());
					bdv.this.bq.a(dem4.b, dem4.c, dem4.d, this.c);
				} else {
					bdv.this.bq.a((double)fu2.u(), (double)fu2.v(), (double)fu2.w(), this.c);
				}
			}
		}

		private boolean a(fu fu, double double2) {
			return !fu.a(this.a.cz(), double2);
		}
	}
}
