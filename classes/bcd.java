import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

public class bcd extends aot implements bbt {
	private static final tq<Integer> b = tt.a(bcd.class, ts.b);
	private dem c = dem.a;
	private fu d = fu.b;
	private bcd.a bv = bcd.a.CIRCLE;

	public bcd(aoq<? extends bcd> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
		this.bo = new bcd.g(this);
		this.g = new bcd.f(this);
	}

	@Override
	protected atg r() {
		return new bcd.d(this);
	}

	@Override
	protected void o() {
		this.br.a(1, new bcd.c());
		this.br.a(2, new bcd.i());
		this.br.a(3, new bcd.e());
		this.bs.a(1, new bcd.b());
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, 0);
	}

	public void a(int integer) {
		this.S.b(b, aec.a(integer, 0, 64));
	}

	private void eK() {
		this.y_();
		this.a(apx.f).a((double)(6 + this.m()));
	}

	public int m() {
		return this.S.a(b);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.35F;
	}

	@Override
	public void a(tq<?> tq) {
		if (b.equals(tq)) {
			this.eK();
		}

		super.a(tq);
	}

	@Override
	protected boolean L() {
		return true;
	}

	@Override
	public void j() {
		super.j();
		if (this.l.v) {
			float float2 = aec.b((float)(this.V() * 3 + this.K) * 0.13F + (float) Math.PI);
			float float3 = aec.b((float)(this.V() * 3 + this.K + 1) * 0.13F + (float) Math.PI);
			if (float2 > 0.0F && float3 <= 0.0F) {
				this.l.a(this.cC(), this.cD(), this.cG(), acl.kJ, this.ct(), 0.95F + this.J.nextFloat() * 0.05F, 0.95F + this.J.nextFloat() * 0.05F, false);
			}

			int integer4 = this.m();
			float float5 = aec.b(this.p * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)integer4);
			float float6 = aec.a(this.p * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)integer4);
			float float7 = (0.3F + float2 * 0.45F) * ((float)integer4 * 0.2F + 1.0F);
			this.l.a(hh.N, this.cC() + (double)float5, this.cD() + (double)float7, this.cG() + (double)float6, 0.0, 0.0, 0.0);
			this.l.a(hh.N, this.cC() - (double)float5, this.cD() + (double)float7, this.cG() - (double)float6, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void k() {
		if (this.aU() && this.eH()) {
			this.f(8);
		}

		super.k();
	}

	@Override
	protected void N() {
		super.N();
	}

	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.d = this.cA().b(5);
		this.a(0);
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.e("AX")) {
			this.d = new fu(le.h("AX"), le.h("AY"), le.h("AZ"));
		}

		this.a(le.h("Size"));
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("AX", this.d.u());
		le.b("AY", this.d.v());
		le.b("AZ", this.d.w());
		le.b("Size", this.m());
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	protected ack I() {
		return acl.kG;
	}

	@Override
	protected ack e(anw anw) {
		return acl.kK;
	}

	@Override
	protected ack dp() {
		return acl.kI;
	}

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	protected float dF() {
		return 1.0F;
	}

	@Override
	public boolean a(aoq<?> aoq) {
		return true;
	}

	@Override
	public aon a(apj apj) {
		int integer3 = this.m();
		aon aon4 = super.a(apj);
		float float5 = (aon4.a + 0.2F * (float)integer3) / aon4.a;
		return aon4.a(float5);
	}

	static enum a {
		CIRCLE,
		SWOOP;
	}

	class b extends aug {
		private final axs b = new axs().a(64.0);
		private int c = 20;

		private b() {
		}

		@Override
		public boolean a() {
			if (this.c > 0) {
				this.c--;
				return false;
			} else {
				this.c = 60;
				List<bec> list2 = bcd.this.l.a(this.b, bcd.this, bcd.this.cb().c(16.0, 64.0, 16.0));
				if (!list2.isEmpty()) {
					list2.sort(Comparator.comparing(aom::cD).reversed());

					for (bec bec4 : list2) {
						if (bcd.this.a(bec4, axs.a)) {
							bcd.this.i(bec4);
							return true;
						}
					}
				}

				return false;
			}
		}

		@Override
		public boolean b() {
			aoy aoy2 = bcd.this.A();
			return aoy2 != null ? bcd.this.a(aoy2, axs.a) : false;
		}
	}

	class c extends aug {
		private int b;

		private c() {
		}

		@Override
		public boolean a() {
			aoy aoy2 = bcd.this.A();
			return aoy2 != null ? bcd.this.a(bcd.this.A(), axs.a) : false;
		}

		@Override
		public void c() {
			this.b = 10;
			bcd.this.bv = bcd.a.CIRCLE;
			this.g();
		}

		@Override
		public void d() {
			bcd.this.d = bcd.this.l.a(cio.a.MOTION_BLOCKING, bcd.this.d).b(10 + bcd.this.J.nextInt(20));
		}

		@Override
		public void e() {
			if (bcd.this.bv == bcd.a.CIRCLE) {
				this.b--;
				if (this.b <= 0) {
					bcd.this.bv = bcd.a.SWOOP;
					this.g();
					this.b = (8 + bcd.this.J.nextInt(4)) * 20;
					bcd.this.a(acl.kL, 10.0F, 0.95F + bcd.this.J.nextFloat() * 0.1F);
				}
			}
		}

		private void g() {
			bcd.this.d = bcd.this.A().cA().b(20 + bcd.this.J.nextInt(20));
			if (bcd.this.d.v() < bcd.this.l.t_()) {
				bcd.this.d = new fu(bcd.this.d.u(), bcd.this.l.t_() + 1, bcd.this.d.w());
			}
		}
	}

	class d extends atg {
		public d(aoz aoz) {
			super(aoz);
		}

		@Override
		public void a() {
			bcd.this.aJ = bcd.this.aH;
			bcd.this.aH = bcd.this.p;
		}
	}

	class e extends bcd.h {
		private float c;
		private float d;
		private float e;
		private float f;

		private e() {
		}

		@Override
		public boolean a() {
			return bcd.this.A() == null || bcd.this.bv == bcd.a.CIRCLE;
		}

		@Override
		public void c() {
			this.d = 5.0F + bcd.this.J.nextFloat() * 10.0F;
			this.e = -4.0F + bcd.this.J.nextFloat() * 9.0F;
			this.f = bcd.this.J.nextBoolean() ? 1.0F : -1.0F;
			this.h();
		}

		@Override
		public void e() {
			if (bcd.this.J.nextInt(350) == 0) {
				this.e = -4.0F + bcd.this.J.nextFloat() * 9.0F;
			}

			if (bcd.this.J.nextInt(250) == 0) {
				this.d++;
				if (this.d > 15.0F) {
					this.d = 5.0F;
					this.f = -this.f;
				}
			}

			if (bcd.this.J.nextInt(450) == 0) {
				this.c = bcd.this.J.nextFloat() * 2.0F * (float) Math.PI;
				this.h();
			}

			if (this.g()) {
				this.h();
			}

			if (bcd.this.c.c < bcd.this.cD() && !bcd.this.l.w(bcd.this.cA().c(1))) {
				this.e = Math.max(1.0F, this.e);
				this.h();
			}

			if (bcd.this.c.c > bcd.this.cD() && !bcd.this.l.w(bcd.this.cA().b(1))) {
				this.e = Math.min(-1.0F, this.e);
				this.h();
			}
		}

		private void h() {
			if (fu.b.equals(bcd.this.d)) {
				bcd.this.d = bcd.this.cA();
			}

			this.c = this.c + this.f * 15.0F * (float) (Math.PI / 180.0);
			bcd.this.c = dem.b(bcd.this.d).b((double)(this.d * aec.b(this.c)), (double)(-4.0F + this.e), (double)(this.d * aec.a(this.c)));
		}
	}

	class f extends atl {
		public f(aoz aoz) {
			super(aoz);
		}

		@Override
		public void a() {
		}
	}

	class g extends atm {
		private float j = 0.1F;

		public g(aoz aoz) {
			super(aoz);
		}

		@Override
		public void a() {
			if (bcd.this.u) {
				bcd.this.p += 180.0F;
				this.j = 0.1F;
			}

			float float2 = (float)(bcd.this.c.b - bcd.this.cC());
			float float3 = (float)(bcd.this.c.c - bcd.this.cD());
			float float4 = (float)(bcd.this.c.d - bcd.this.cG());
			double double5 = (double)aec.c(float2 * float2 + float4 * float4);
			double double7 = 1.0 - (double)aec.e(float3 * 0.7F) / double5;
			float2 = (float)((double)float2 * double7);
			float4 = (float)((double)float4 * double7);
			double5 = (double)aec.c(float2 * float2 + float4 * float4);
			double double9 = (double)aec.c(float2 * float2 + float4 * float4 + float3 * float3);
			float float11 = bcd.this.p;
			float float12 = (float)aec.d((double)float4, (double)float2);
			float float13 = aec.g(bcd.this.p + 90.0F);
			float float14 = aec.g(float12 * (180.0F / (float)Math.PI));
			bcd.this.p = aec.d(float13, float14, 4.0F) - 90.0F;
			bcd.this.aH = bcd.this.p;
			if (aec.d(float11, bcd.this.p) < 3.0F) {
				this.j = aec.c(this.j, 1.8F, 0.005F * (1.8F / this.j));
			} else {
				this.j = aec.c(this.j, 0.2F, 0.025F);
			}

			float float15 = (float)(-(aec.d((double)(-float3), double5) * 180.0F / (float)Math.PI));
			bcd.this.q = float15;
			float float16 = bcd.this.p + 90.0F;
			double double17 = (double)(this.j * aec.b(float16 * (float) (Math.PI / 180.0))) * Math.abs((double)float2 / double9);
			double double19 = (double)(this.j * aec.a(float16 * (float) (Math.PI / 180.0))) * Math.abs((double)float4 / double9);
			double double21 = (double)(this.j * aec.a(float15 * (float) (Math.PI / 180.0))) * Math.abs((double)float3 / double9);
			dem dem23 = bcd.this.cB();
			bcd.this.e(dem23.e(new dem(double17, double21, double19).d(dem23).a(0.2)));
		}
	}

	abstract class h extends aug {
		public h() {
			this.a(EnumSet.of(aug.a.MOVE));
		}

		protected boolean g() {
			return bcd.this.c.c(bcd.this.cC(), bcd.this.cD(), bcd.this.cG()) < 4.0;
		}
	}

	class i extends bcd.h {
		private i() {
		}

		@Override
		public boolean a() {
			return bcd.this.A() != null && bcd.this.bv == bcd.a.SWOOP;
		}

		@Override
		public boolean b() {
			aoy aoy2 = bcd.this.A();
			if (aoy2 == null) {
				return false;
			} else if (!aoy2.aU()) {
				return false;
			} else if (!(aoy2 instanceof bec) || !((bec)aoy2).a_() && !((bec)aoy2).b_()) {
				if (!this.a()) {
					return false;
				} else {
					if (bcd.this.K % 20 == 0) {
						List<aym> list3 = bcd.this.l.a(aym.class, bcd.this.cb().g(16.0), aop.a);
						if (!list3.isEmpty()) {
							for (aym aym5 : list3) {
								aym5.fa();
							}

							return false;
						}
					}

					return true;
				}
			} else {
				return false;
			}
		}

		@Override
		public void c() {
		}

		@Override
		public void d() {
			bcd.this.i(null);
			bcd.this.bv = bcd.a.CIRCLE;
		}

		@Override
		public void e() {
			aoy aoy2 = bcd.this.A();
			bcd.this.c = new dem(aoy2.cC(), aoy2.e(0.5), aoy2.cG());
			if (bcd.this.cb().g(0.2F).c(aoy2.cb())) {
				bcd.this.B(aoy2);
				bcd.this.bv = bcd.a.CIRCLE;
				if (!bcd.this.av()) {
					bcd.this.l.c(1039, bcd.this.cA(), 0);
				}
			} else if (bcd.this.u || bcd.this.au > 0) {
				bcd.this.bv = bcd.a.CIRCLE;
			}
		}
	}
}
