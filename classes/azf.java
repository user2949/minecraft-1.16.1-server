import javax.annotation.Nullable;

public class azf extends ayi implements apn, bcf {
	private static final tq<Byte> b = tt.a(azf.class, ts.a);

	public azf(aoq<? extends azf> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.br.a(1, new avh(this, 1.25, 20, 10.0F));
		this.br.a(2, new avw(this, 1.0, 1.0000001E-5F));
		this.br.a(3, new auo(this, bec.class, 6.0F));
		this.br.a(4, new ave(this));
		this.bs.a(1, new awc(this, aoz.class, 10, true, false, aoy -> aoy instanceof bbt));
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 4.0).a(apx.d, 0.2F);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)16);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("Pumpkin", this.eL());
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.e("Pumpkin")) {
			this.t(le.q("Pumpkin"));
		}
	}

	@Override
	public boolean dN() {
		return true;
	}

	@Override
	public void k() {
		super.k();
		if (!this.l.v) {
			int integer2 = aec.c(this.cC());
			int integer3 = aec.c(this.cD());
			int integer4 = aec.c(this.cG());
			if (this.l.v(new fu(integer2, 0, integer4)).b(new fu(integer2, integer3, integer4)) > 1.0F) {
				this.a(anw.c, 1.0F);
			}

			if (!this.l.S().b(bpx.b)) {
				return;
			}

			cfj cfj5 = bvs.cC.n();

			for (int integer6 = 0; integer6 < 4; integer6++) {
				integer2 = aec.c(this.cC() + (double)((float)(integer6 % 2 * 2 - 1) * 0.25F));
				integer3 = aec.c(this.cD());
				integer4 = aec.c(this.cG() + (double)((float)(integer6 / 2 % 2 * 2 - 1) * 0.25F));
				fu fu7 = new fu(integer2, integer3, integer4);
				if (this.l.d_(fu7).g() && this.l.v(fu7).b(fu7) < 0.8F && cfj5.a((bqd)this.l, fu7)) {
					this.l.a(fu7, cfj5);
				}
			}
		}
	}

	@Override
	public void a(aoy aoy, float float2) {
		bew bew4 = new bew(this.l, this);
		double double5 = aoy.cF() - 1.1F;
		double double7 = aoy.cC() - this.cC();
		double double9 = double5 - bew4.cD();
		double double11 = aoy.cG() - this.cG();
		float float13 = aec.a(double7 * double7 + double11 * double11) * 0.2F;
		bew4.c(double7, double9 + (double)float13, double11, 1.6F, 12.0F);
		this.a(acl.oo, 1.0F, 0.4F / (this.cX().nextFloat() * 0.4F + 0.8F));
		this.l.c(bew4);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 1.7F;
	}

	@Override
	protected ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.ng && this.L_()) {
			this.a(acm.PLAYERS);
			if (!this.l.v) {
				bki4.a(1, bec, becx -> becx.d(anf));
			}

			return ang.a(this.l.v);
		} else {
			return ang.PASS;
		}
	}

	@Override
	public void a(acm acm) {
		this.l.a(null, this, acl.op, acm, 1.0F, 1.0F);
		if (!this.l.s_()) {
			this.t(false);
			this.a(new bki(bkk.dj), 1.7F);
		}
	}

	@Override
	public boolean L_() {
		return this.aU() && this.eL();
	}

	public boolean eL() {
		return (this.S.a(b) & 16) != 0;
	}

	public void t(boolean boolean1) {
		byte byte3 = this.S.a(b);
		if (boolean1) {
			this.S.b(b, (byte)(byte3 | 16));
		} else {
			this.S.b(b, (byte)(byte3 & -17));
		}
	}

	@Nullable
	@Override
	protected ack I() {
		return acl.ol;
	}

	@Nullable
	@Override
	protected ack e(anw anw) {
		return acl.on;
	}

	@Nullable
	@Override
	protected ack dp() {
		return acl.om;
	}
}
