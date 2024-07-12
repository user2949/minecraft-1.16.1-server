import java.util.Collection;

public class bbn extends bcb {
	private static final tq<Integer> b = tt.a(bbn.class, ts.b);
	private static final tq<Boolean> c = tt.a(bbn.class, ts.i);
	private static final tq<Boolean> d = tt.a(bbn.class, ts.i);
	private int bv;
	private int bw;
	private int bx = 30;
	private int by = 3;
	private int bz;

	public bbn(aoq<? extends bbn> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.br.a(1, new aua(this));
		this.br.a(2, new avp(this));
		this.br.a(3, new ato(this, ayv.class, 6.0F, 1.0, 1.2));
		this.br.a(3, new ato(this, aym.class, 6.0F, 1.0, 1.2));
		this.br.a(4, new auq(this, 1.0, false));
		this.br.a(5, new avw(this, 0.8));
		this.br.a(6, new auo(this, bec.class, 8.0F));
		this.br.a(6, new ave(this));
		this.bs.a(1, new awc(this, bec.class, true));
		this.bs.a(2, new awb(this));
	}

	public static apw.a m() {
		return bcb.eS().a(apx.d, 0.25);
	}

	@Override
	public int bL() {
		return this.A() == null ? 3 : 3 + (int)(this.dj() - 1.0F);
	}

	@Override
	public boolean b(float float1, float float2) {
		boolean boolean4 = super.b(float1, float2);
		this.bw = (int)((float)this.bw + float1 * 1.5F);
		if (this.bw > this.bx - 5) {
			this.bw = this.bx - 5;
		}

		return boolean4;
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, -1);
		this.S.a(c, false);
		this.S.a(d, false);
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.S.a(c)) {
			le.a("powered", true);
		}

		le.a("Fuse", (short)this.bx);
		le.a("ExplosionRadius", (byte)this.by);
		le.a("ignited", this.eM());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.S.b(c, le.q("powered"));
		if (le.c("Fuse", 99)) {
			this.bx = le.g("Fuse");
		}

		if (le.c("ExplosionRadius", 99)) {
			this.by = le.f("ExplosionRadius");
		}

		if (le.q("ignited")) {
			this.eN();
		}
	}

	@Override
	public void j() {
		if (this.aU()) {
			this.bv = this.bw;
			if (this.eM()) {
				this.a(1);
			}

			int integer2 = this.eL();
			if (integer2 > 0 && this.bw == 0) {
				this.a(acl.cp, 1.0F, 0.5F);
			}

			this.bw += integer2;
			if (this.bw < 0) {
				this.bw = 0;
			}

			if (this.bw >= this.bx) {
				this.bw = this.bx;
				this.eQ();
			}
		}

		super.j();
	}

	@Override
	protected ack e(anw anw) {
		return acl.co;
	}

	@Override
	protected ack dp() {
		return acl.cn;
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);
		aom aom5 = anw.k();
		if (aom5 != this && aom5 instanceof bbn) {
			bbn bbn6 = (bbn)aom5;
			if (bbn6.eO()) {
				bbn6.eP();
				this.a(bkk.ph);
			}
		}
	}

	@Override
	public boolean B(aom aom) {
		return true;
	}

	public boolean T_() {
		return this.S.a(c);
	}

	public int eL() {
		return this.S.a(b);
	}

	public void a(int integer) {
		this.S.b(b, integer);
	}

	@Override
	public void a(aox aox) {
		super.a(aox);
		this.S.b(c, true);
	}

	@Override
	protected ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.kd) {
			this.l.a(bec, this.cC(), this.cD(), this.cG(), acl.eo, this.ct(), 1.0F, this.J.nextFloat() * 0.4F + 0.8F);
			if (!this.l.v) {
				this.eN();
				bki4.a(1, bec, becx -> becx.d(anf));
			}

			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	private void eQ() {
		if (!this.l.v) {
			bpt.a a2 = this.l.S().b(bpx.b) ? bpt.a.DESTROY : bpt.a.NONE;
			float float3 = this.T_() ? 2.0F : 1.0F;
			this.aO = true;
			this.l.a(this, this.cC(), this.cD(), this.cG(), (float)this.by * float3, a2);
			this.aa();
			this.eT();
		}
	}

	private void eT() {
		Collection<aog> collection2 = this.dg();
		if (!collection2.isEmpty()) {
			aol aol3 = new aol(this.l, this.cC(), this.cD(), this.cG());
			aol3.a(2.5F);
			aol3.b(-0.5F);
			aol3.d(10);
			aol3.b(aol3.m() / 2);
			aol3.c(-aol3.g() / (float)aol3.m());

			for (aog aog5 : collection2) {
				aol3.a(new aog(aog5));
			}

			this.l.c(aol3);
		}
	}

	public boolean eM() {
		return this.S.a(d);
	}

	public void eN() {
		this.S.b(d, true);
	}

	public boolean eO() {
		return this.T_() && this.bz < 1;
	}

	public void eP() {
		this.bz++;
	}
}
