import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class ayz extends ayk implements ape {
	private static final tq<Boolean> bv = tt.a(ayz.class, ts.i);
	private float bw;
	private float bx;
	private int by;
	private static final adx bz = aej.a(20, 39);
	private int bA;
	private UUID bB;

	public ayz(aoq<? extends ayz> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	public aok a(aok aok) {
		return aoq.ak.a(this.l);
	}

	@Override
	public boolean k(bki bki) {
		return false;
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(1, new ayz.c());
		this.br.a(1, new ayz.d());
		this.br.a(4, new auf(this, 1.25));
		this.br.a(5, new avf(this, 1.0));
		this.br.a(6, new auo(this, bec.class, 6.0F));
		this.br.a(7, new ave(this));
		this.bs.a(1, new ayz.b());
		this.bs.a(2, new ayz.a());
		this.bs.a(3, new awc(this, bec.class, 10, true, false, this::b));
		this.bs.a(4, new awc(this, ays.class, 10, true, true, null));
		this.bs.a(5, new awi<>(this, false));
	}

	public static apw.a eL() {
		return aoz.p().a(apx.a, 30.0).a(apx.b, 20.0).a(apx.d, 0.25).a(apx.f, 6.0);
	}

	public static boolean c(aoq<ayz> aoq, bqc bqc, apb apb, fu fu, Random random) {
		bre bre6 = bqc.v(fu);
		return bre6 != brk.l && bre6 != brk.Z ? b(aoq, bqc, apb, fu, random) : bqc.b(fu, 0) > 8 && bqc.d_(fu.c()).a(bvs.cD);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.a((zd)this.l, le);
	}

	@Override
	public void b(le le) {
		super.b(le);
		this.c(le);
	}

	@Override
	public void H_() {
		this.a_(bz.a(this.J));
	}

	@Override
	public void a_(int integer) {
		this.bA = integer;
	}

	@Override
	public int F_() {
		return this.bA;
	}

	@Override
	public void a(@Nullable UUID uUID) {
		this.bB = uUID;
	}

	@Override
	public UUID G_() {
		return this.bB;
	}

	@Override
	protected ack I() {
		return this.x_() ? acl.lB : acl.lA;
	}

	@Override
	protected ack e(anw anw) {
		return acl.lD;
	}

	@Override
	protected ack dp() {
		return acl.lC;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.lE, 0.15F, 1.0F);
	}

	protected void eM() {
		if (this.by <= 0) {
			this.a(acl.lF, 1.0F, this.dG());
			this.by = 40;
		}
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, false);
	}

	@Override
	public void j() {
		super.j();
		if (this.l.v) {
			if (this.bx != this.bw) {
				this.y_();
			}

			this.bw = this.bx;
			if (this.eN()) {
				this.bx = aec.a(this.bx + 1.0F, 0.0F, 6.0F);
			} else {
				this.bx = aec.a(this.bx - 1.0F, 0.0F, 6.0F);
			}
		}

		if (this.by > 0) {
			this.by--;
		}

		if (!this.l.v) {
			this.a((zd)this.l, true);
		}
	}

	@Override
	public aon a(apj apj) {
		if (this.bx > 0.0F) {
			float float3 = this.bx / 6.0F;
			float float4 = 1.0F + float3;
			return super.a(apj).a(1.0F, float4);
		} else {
			return super.a(apj);
		}
	}

	@Override
	public boolean B(aom aom) {
		boolean boolean3 = aom.a(anw.c(this), (float)((int)this.b(apx.f)));
		if (boolean3) {
			this.a(this, aom);
		}

		return boolean3;
	}

	public boolean eN() {
		return this.S.a(bv);
	}

	public void t(boolean boolean1) {
		this.S.b(bv, boolean1);
	}

	@Override
	protected float dL() {
		return 0.98F;
	}

	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(1.0F);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	class a extends awc<bec> {
		public a() {
			super(ayz.this, bec.class, 20, true, true, null);
		}

		@Override
		public boolean a() {
			if (ayz.this.x_()) {
				return false;
			} else {
				if (super.a()) {
					for (ayz ayz4 : ayz.this.l.a(ayz.class, ayz.this.cb().c(8.0, 4.0, 8.0))) {
						if (ayz4.x_()) {
							return true;
						}
					}
				}

				return false;
			}
		}

		@Override
		protected double k() {
			return super.k() * 0.5;
		}
	}

	class b extends awb {
		public b() {
			super(ayz.this);
		}

		@Override
		public void c() {
			super.c();
			if (ayz.this.x_()) {
				this.g();
				this.d();
			}
		}

		@Override
		protected void a(aoz aoz, aoy aoy) {
			if (aoz instanceof ayz && !aoz.x_()) {
				super.a(aoz, aoy);
			}
		}
	}

	class c extends auq {
		public c() {
			super(ayz.this, 1.25, true);
		}

		@Override
		protected void a(aoy aoy, double double2) {
			double double5 = this.a(aoy);
			if (double2 <= double5 && this.h()) {
				this.g();
				this.a.B(aoy);
				ayz.this.t(false);
			} else if (double2 <= double5 * 2.0) {
				if (this.h()) {
					ayz.this.t(false);
					this.g();
				}

				if (this.j() <= 10) {
					ayz.this.t(true);
					ayz.this.eM();
				}
			} else {
				this.g();
				ayz.this.t(false);
			}
		}

		@Override
		public void d() {
			ayz.this.t(false);
			super.d();
		}

		@Override
		protected double a(aoy aoy) {
			return (double)(4.0F + aoy.cx());
		}
	}

	class d extends avb {
		public d() {
			super(ayz.this, 2.0);
		}

		@Override
		public boolean a() {
			return !ayz.this.x_() && !ayz.this.bm() ? false : super.a();
		}
	}
}
