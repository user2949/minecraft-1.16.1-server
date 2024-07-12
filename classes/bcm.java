import java.util.Random;
import javax.annotation.Nullable;

public class bcm extends bcb {
	private static final tq<Byte> b = tt.a(bcm.class, ts.a);

	public bcm(aoq<? extends bcm> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.br.a(1, new aua(this));
		this.br.a(3, new aum(this, 0.4F));
		this.br.a(4, new bcm.a(this));
		this.br.a(5, new avw(this, 0.8));
		this.br.a(6, new auo(this, bec.class, 8.0F));
		this.br.a(6, new ave(this));
		this.bs.a(1, new awb(this));
		this.bs.a(2, new bcm.c(this, bec.class));
		this.bs.a(3, new bcm.c(this, ayt.class));
	}

	@Override
	public double aY() {
		return (double)(this.cy() * 0.5F);
	}

	@Override
	protected awv b(bqb bqb) {
		return new aww(this, bqb);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)0);
	}

	@Override
	public void j() {
		super.j();
		if (!this.l.v) {
			this.t(this.u);
		}
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.a, 16.0).a(apx.d, 0.3F);
	}

	@Override
	protected ack I() {
		return acl.ot;
	}

	@Override
	protected ack e(anw anw) {
		return acl.ov;
	}

	@Override
	protected ack dp() {
		return acl.ou;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.ow, 0.15F, 1.0F);
	}

	@Override
	public boolean c_() {
		return this.eM();
	}

	@Override
	public void a(cfj cfj, dem dem) {
		if (!cfj.a(bvs.aQ)) {
			super.a(cfj, dem);
		}
	}

	@Override
	public apc dB() {
		return apc.c;
	}

	@Override
	public boolean d(aog aog) {
		return aog.a() == aoi.s ? false : super.d(aog);
	}

	public boolean eM() {
		return (this.S.a(b) & 1) != 0;
	}

	public void t(boolean boolean1) {
		byte byte3 = this.S.a(b);
		if (boolean1) {
			byte3 = (byte)(byte3 | 1);
		} else {
			byte3 = (byte)(byte3 & -2);
		}

		this.S.b(b, byte3);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo = super.a(bqc, ane, apb, apo, le);
		if (bqc.v_().nextInt(100) == 0) {
			bcj bcj7 = aoq.au.a(this.l);
			bcj7.b(this.cC(), this.cD(), this.cG(), this.p, 0.0F);
			bcj7.a(bqc, ane, apb, null, null);
			bcj7.m(this);
			bqc.c(bcj7);
		}

		if (apo == null) {
			apo = new bcm.b();
			if (bqc.ac() == and.HARD && bqc.v_().nextFloat() < 0.1F * ane.d()) {
				((bcm.b)apo).a(bqc.v_());
			}
		}

		if (apo instanceof bcm.b) {
			aoe aoe7 = ((bcm.b)apo).a;
			if (aoe7 != null) {
				this.c(new aog(aoe7, Integer.MAX_VALUE));
			}
		}

		return apo;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.65F;
	}

	static class a extends auq {
		public a(bcm bcm) {
			super(bcm, 1.0, true);
		}

		@Override
		public boolean a() {
			return super.a() && !this.a.bo();
		}

		@Override
		public boolean b() {
			float float2 = this.a.aO();
			if (float2 >= 0.5F && this.a.cX().nextInt(100) == 0) {
				this.a.i(null);
				return false;
			} else {
				return super.b();
			}
		}

		@Override
		protected double a(aoy aoy) {
			return (double)(4.0F + aoy.cx());
		}
	}

	public static class b implements apo {
		public aoe a;

		public void a(Random random) {
			int integer3 = random.nextInt(5);
			if (integer3 <= 1) {
				this.a = aoi.a;
			} else if (integer3 <= 2) {
				this.a = aoi.e;
			} else if (integer3 <= 3) {
				this.a = aoi.j;
			} else if (integer3 <= 4) {
				this.a = aoi.n;
			}
		}
	}

	static class c<T extends aoy> extends awc<T> {
		public c(bcm bcm, Class<T> class2) {
			super(bcm, class2, true);
		}

		@Override
		public boolean a() {
			float float2 = this.e.aO();
			return float2 >= 0.5F ? false : super.a();
		}
	}
}
