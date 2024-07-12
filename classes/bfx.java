public class bfx extends bfr {
	private static final tq<String> b = tt.a(bfx.class, ts.d);
	private static final tq<mr> c = tt.a(bfx.class, ts.e);
	private final bpc d = new bfx.a();
	private int e;

	public bfx(aoq<? extends bfx> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfx(bqb bqb, double double2, double double3, double double4) {
		super(aoq.V, bqb, double2, double3, double4);
	}

	@Override
	protected void e() {
		super.e();
		this.Y().a(b, "");
		this.Y().a(c, nd.d);
	}

	@Override
	protected void a(le le) {
		super.a(le);
		this.d.b(le);
		this.Y().b(b, this.u().k());
		this.Y().b(c, this.u().j());
	}

	@Override
	protected void b(le le) {
		super.b(le);
		this.d.a(le);
	}

	@Override
	public bfr.a o() {
		return bfr.a.COMMAND_BLOCK;
	}

	@Override
	public cfj q() {
		return bvs.er.n();
	}

	public bpc u() {
		return this.d;
	}

	@Override
	public void a(int integer1, int integer2, int integer3, boolean boolean4) {
		if (boolean4 && this.K - this.e >= 4) {
			this.u().a(this.l);
			this.e = this.K;
		}
	}

	@Override
	public ang a(bec bec, anf anf) {
		return this.d.a(bec);
	}

	@Override
	public void a(tq<?> tq) {
		super.a(tq);
		if (c.equals(tq)) {
			try {
				this.d.b(this.Y().a(c));
			} catch (Throwable var3) {
			}
		} else if (b.equals(tq)) {
			this.d.a(this.Y().a(b));
		}
	}

	@Override
	public boolean ci() {
		return true;
	}

	public class a extends bpc {
		@Override
		public zd d() {
			return (zd)bfx.this.l;
		}

		@Override
		public void e() {
			bfx.this.Y().b(bfx.b, this.k());
			bfx.this.Y().b(bfx.c, this.j());
		}

		@Override
		public cz h() {
			return new cz(this, bfx.this.cz(), bfx.this.be(), this.d(), 2, this.l().getString(), bfx.this.d(), this.d().l(), bfx.this);
		}
	}
}
