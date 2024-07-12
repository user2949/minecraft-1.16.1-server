import javax.annotation.Nullable;

public class cdq extends cdl {
	private boolean a;
	private boolean b;
	private boolean c;
	private boolean g;
	private final bpc h = new bpc() {
		@Override
		public void a(String string) {
			super.a(string);
			cdq.this.Z_();
		}

		@Override
		public zd d() {
			return (zd)cdq.this.d;
		}

		@Override
		public void e() {
			cfj cfj2 = cdq.this.d.d_(cdq.this.e);
			this.d().a(cdq.this.e, cfj2, cfj2, 3);
		}

		@Override
		public cz h() {
			return new cz(this, dem.a(cdq.this.e), del.a, this.d(), 2, this.l().getString(), this.l(), this.d().l(), null);
		}
	};

	public cdq() {
		super(cdm.v);
	}

	@Override
	public le a(le le) {
		super.a(le);
		this.h.a(le);
		le.a("powered", this.f());
		le.a("conditionMet", this.j());
		le.a("auto", this.g());
		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.h.b(le);
		this.a = le.q("powered");
		this.c = le.q("conditionMet");
		this.b(le.q("auto"));
	}

	@Nullable
	@Override
	public nv a() {
		if (this.l()) {
			this.c(false);
			le le2 = this.a(new le());
			return new nv(this.e, 2, le2);
		} else {
			return null;
		}
	}

	@Override
	public boolean t() {
		return true;
	}

	public bpc d() {
		return this.h;
	}

	public void a(boolean boolean1) {
		this.a = boolean1;
	}

	public boolean f() {
		return this.a;
	}

	public boolean g() {
		return this.b;
	}

	public void b(boolean boolean1) {
		boolean boolean3 = this.b;
		this.b = boolean1;
		if (!boolean3 && boolean1 && !this.a && this.d != null && this.m() != cdq.a.SEQUENCE) {
			this.y();
		}
	}

	public void h() {
		cdq.a a2 = this.m();
		if (a2 == cdq.a.AUTO && (this.a || this.b) && this.d != null) {
			this.y();
		}
	}

	private void y() {
		bvr bvr2 = this.p().b();
		if (bvr2 instanceof bwl) {
			this.k();
			this.d.G().a(this.e, bvr2, 1);
		}
	}

	public boolean j() {
		return this.c;
	}

	public boolean k() {
		this.c = true;
		if (this.x()) {
			fu fu2 = this.e.a(((fz)this.d.d_(this.e).c(bwl.a)).f());
			if (this.d.d_(fu2).b() instanceof bwl) {
				cdl cdl3 = this.d.c(fu2);
				this.c = cdl3 instanceof cdq && ((cdq)cdl3).d().i() > 0;
			} else {
				this.c = false;
			}
		}

		return this.c;
	}

	public boolean l() {
		return this.g;
	}

	public void c(boolean boolean1) {
		this.g = boolean1;
	}

	public cdq.a m() {
		cfj cfj2 = this.p();
		if (cfj2.a(bvs.er)) {
			return cdq.a.REDSTONE;
		} else if (cfj2.a(bvs.iG)) {
			return cdq.a.AUTO;
		} else {
			return cfj2.a(bvs.iH) ? cdq.a.SEQUENCE : cdq.a.REDSTONE;
		}
	}

	public boolean x() {
		cfj cfj2 = this.d.d_(this.o());
		return cfj2.b() instanceof bwl ? (Boolean)cfj2.c(bwl.b) : false;
	}

	@Override
	public void r() {
		this.s();
		super.r();
	}

	public static enum a {
		SEQUENCE,
		AUTO,
		REDSTONE;
	}
}
