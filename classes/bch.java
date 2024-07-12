import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

public class bch extends ayi implements bbt {
	private static final UUID bw = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
	private static final apv bx = new apv(bw, "Covered armor bonus", 20.0, apv.a.ADDITION);
	protected static final tq<fz> b = tt.a(bch.class, ts.n);
	protected static final tq<Optional<fu>> c = tt.a(bch.class, ts.m);
	protected static final tq<Byte> d = tt.a(bch.class, ts.a);
	protected static final tq<Byte> bv = tt.a(bch.class, ts.a);
	private float by;
	private float bz;
	private fu bA = null;
	private int bB;

	public bch(aoq<? extends bch> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
	}

	@Override
	protected void o() {
		this.br.a(1, new auo(this, bec.class, 8.0F));
		this.br.a(4, new bch.a());
		this.br.a(7, new bch.e());
		this.br.a(8, new ave(this));
		this.bs.a(1, new awb(this).a());
		this.bs.a(2, new bch.d(this));
		this.bs.a(3, new bch.c(this));
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	protected ack I() {
		return acl.mV;
	}

	@Override
	public void F() {
		if (!this.eU()) {
			super.F();
		}
	}

	@Override
	protected ack dp() {
		return acl.nb;
	}

	@Override
	protected ack e(anw anw) {
		return this.eU() ? acl.nd : acl.nc;
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, fz.DOWN);
		this.S.a(c, Optional.empty());
		this.S.a(d, (byte)0);
		this.S.a(bv, (byte)16);
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 30.0);
	}

	@Override
	protected atg r() {
		return new bch.b(this);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.S.b(b, fz.a(le.f("AttachFace")));
		this.S.b(d, le.f("Peek"));
		this.S.b(bv, le.f("Color"));
		if (le.e("APX")) {
			int integer3 = le.h("APX");
			int integer4 = le.h("APY");
			int integer5 = le.h("APZ");
			this.S.b(c, Optional.of(new fu(integer3, integer4, integer5)));
		} else {
			this.S.b(c, Optional.empty());
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("AttachFace", (byte)this.S.a(b).c());
		le.a("Peek", this.S.a(d));
		le.a("Color", this.S.a(bv));
		fu fu3 = this.eN();
		if (fu3 != null) {
			le.b("APX", fu3.u());
			le.b("APY", fu3.v());
			le.b("APZ", fu3.w());
		}
	}

	@Override
	public void j() {
		super.j();
		fu fu2 = (fu)this.S.a(c).orElse(null);
		if (fu2 == null && !this.l.v) {
			fu2 = this.cA();
			this.S.b(c, Optional.of(fu2));
		}

		if (this.bn()) {
			fu2 = null;
			float float3 = this.cs().p;
			this.p = float3;
			this.aH = float3;
			this.aI = float3;
			this.bB = 0;
		} else if (!this.l.v) {
			cfj cfj3 = this.l.d_(fu2);
			if (!cfj3.g()) {
				if (cfj3.a(bvs.bo)) {
					fz fz4 = cfj3.c(cfc.a);
					if (this.l.w(fu2.a(fz4))) {
						fu2 = fu2.a(fz4);
						this.S.b(c, Optional.of(fu2));
					} else {
						this.eL();
					}
				} else if (cfj3.a(bvs.aX)) {
					fz fz4 = cfj3.c(cfd.a);
					if (this.l.w(fu2.a(fz4))) {
						fu2 = fu2.a(fz4);
						this.S.b(c, Optional.of(fu2));
					} else {
						this.eL();
					}
				} else {
					this.eL();
				}
			}

			fz fz4 = this.eM();
			if (!this.a(fu2, fz4)) {
				fz fz5 = this.g(fu2);
				if (fz5 != null) {
					this.S.b(b, fz5);
				} else {
					this.eL();
				}
			}
		}

		float float3 = (float)this.eO() * 0.01F;
		this.by = this.bz;
		if (this.bz > float3) {
			this.bz = aec.a(this.bz - 0.05F, float3, 1.0F);
		} else if (this.bz < float3) {
			this.bz = aec.a(this.bz + 0.05F, 0.0F, float3);
		}

		if (fu2 != null) {
			if (this.l.v) {
				if (this.bB > 0 && this.bA != null) {
					this.bB--;
				} else {
					this.bA = fu2;
				}
			}

			this.f((double)fu2.u() + 0.5, (double)fu2.v(), (double)fu2.w() + 0.5);
			double double4 = 0.5 - (double)aec.a((0.5F + this.bz) * (float) Math.PI) * 0.5;
			double double6 = 0.5 - (double)aec.a((0.5F + this.by) * (float) Math.PI) * 0.5;
			fz fz8 = this.eM().f();
			this.a(
				new deg(this.cC() - 0.5, this.cD(), this.cG() - 0.5, this.cC() + 0.5, this.cD() + 1.0, this.cG() + 0.5)
					.b((double)fz8.i() * double4, (double)fz8.j() * double4, (double)fz8.k() * double4)
			);
			double double9 = double4 - double6;
			if (double9 > 0.0) {
				List<aom> list11 = this.l.a(this, this.cb());
				if (!list11.isEmpty()) {
					for (aom aom13 : list11) {
						if (!(aom13 instanceof bch) && !aom13.H) {
							aom13.a(apd.SHULKER, new dem(double9 * (double)fz8.i(), double9 * (double)fz8.j(), double9 * (double)fz8.k()));
						}
					}
				}
			}
		}
	}

	@Override
	public void a(apd apd, dem dem) {
		if (apd == apd.SHULKER_BOX) {
			this.eL();
		} else {
			super.a(apd, dem);
		}
	}

	@Override
	public void d(double double1, double double2, double double3) {
		super.d(double1, double2, double3);
		if (this.S != null && this.K != 0) {
			Optional<fu> optional8 = this.S.a(c);
			Optional<fu> optional9 = Optional.of(new fu(double1, double2, double3));
			if (!optional9.equals(optional8)) {
				this.S.b(c, optional9);
				this.S.b(d, (byte)0);
				this.ad = true;
			}
		}
	}

	@Nullable
	protected fz g(fu fu) {
		for (fz fz6 : fz.values()) {
			if (this.a(fu, fz6)) {
				return fz6;
			}
		}

		return null;
	}

	private boolean a(fu fu, fz fz) {
		return this.l.a(fu.a(fz), this, fz.f()) && this.l.a_(this, anl.a(fu, fz.f()));
	}

	protected boolean eL() {
		if (!this.eE() && this.aU()) {
			fu fu2 = this.cA();

			for (int integer3 = 0; integer3 < 5; integer3++) {
				fu fu4 = fu2.b(8 - this.J.nextInt(17), 8 - this.J.nextInt(17), 8 - this.J.nextInt(17));
				if (fu4.v() > 0 && this.l.w(fu4) && this.l.f().a(fu4) && this.l.a_(this, new deg(fu4))) {
					fz fz5 = this.g(fu4);
					if (fz5 != null) {
						this.S.b(b, fz5);
						this.a(acl.ng, 1.0F, 1.0F);
						this.S.b(c, Optional.of(fu4));
						this.S.b(d, (byte)0);
						this.i(null);
						return true;
					}
				}
			}

			return false;
		} else {
			return true;
		}
	}

	@Override
	public void k() {
		super.k();
		this.e(dem.a);
		if (!this.eE()) {
			this.aI = 0.0F;
			this.aH = 0.0F;
		}
	}

	@Override
	public void a(tq<?> tq) {
		if (c.equals(tq) && this.l.v && !this.bn()) {
			fu fu3 = this.eN();
			if (fu3 != null) {
				if (this.bA == null) {
					this.bA = fu3;
				} else {
					this.bB = 6;
				}

				this.f((double)fu3.u() + 0.5, (double)fu3.v(), (double)fu3.w() + 0.5);
			}
		}

		super.a(tq);
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.eU()) {
			aom aom4 = anw.j();
			if (aom4 instanceof beg) {
				return false;
			}
		}

		if (super.a(anw, float2)) {
			if ((double)this.dj() < (double)this.dw() * 0.5 && this.J.nextInt(4) == 0) {
				this.eL();
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean eU() {
		return this.eO() == 0;
	}

	@Nullable
	@Override
	public deg ay() {
		return this.aU() ? this.cb() : null;
	}

	public fz eM() {
		return this.S.a(b);
	}

	@Nullable
	public fu eN() {
		return (fu)this.S.a(c).orElse(null);
	}

	public void h(@Nullable fu fu) {
		this.S.b(c, Optional.ofNullable(fu));
	}

	public int eO() {
		return this.S.a(d);
	}

	public void a(int integer) {
		if (!this.l.v) {
			this.a(apx.i).d(bx);
			if (integer == 0) {
				this.a(apx.i).c(bx);
				this.a(acl.na, 1.0F, 1.0F);
			} else {
				this.a(acl.ne, 1.0F, 1.0F);
			}
		}

		this.S.b(d, (byte)integer);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.5F;
	}

	@Override
	public int eo() {
		return 180;
	}

	@Override
	public int ep() {
		return 180;
	}

	@Override
	public void i(aom aom) {
	}

	@Override
	public float bc() {
		return 0.0F;
	}

	class a extends aug {
		private int b;

		public a() {
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			aoy aoy2 = bch.this.A();
			return aoy2 != null && aoy2.aU() ? bch.this.l.ac() != and.PEACEFUL : false;
		}

		@Override
		public void c() {
			this.b = 20;
			bch.this.a(100);
		}

		@Override
		public void d() {
			bch.this.a(0);
		}

		@Override
		public void e() {
			if (bch.this.l.ac() != and.PEACEFUL) {
				this.b--;
				aoy aoy2 = bch.this.A();
				bch.this.t().a(aoy2, 180.0F, 180.0F);
				double double3 = bch.this.h(aoy2);
				if (double3 < 400.0) {
					if (this.b <= 0) {
						this.b = 20 + bch.this.J.nextInt(10) * 20 / 2;
						bch.this.l.c(new beu(bch.this.l, bch.this, aoy2, bch.this.eM().n()));
						bch.this.a(acl.nf, 2.0F, (bch.this.J.nextFloat() - bch.this.J.nextFloat()) * 0.2F + 1.0F);
					}
				} else {
					bch.this.i(null);
				}

				super.e();
			}
		}
	}

	class b extends atg {
		public b(aoz aoz) {
			super(aoz);
		}

		@Override
		public void a() {
		}
	}

	static class c extends awc<aoy> {
		public c(bch bch) {
			super(bch, aoy.class, 10, true, false, aoy -> aoy instanceof bbt);
		}

		@Override
		public boolean a() {
			return this.e.bC() == null ? false : super.a();
		}

		@Override
		protected deg a(double double1) {
			fz fz4 = ((bch)this.e).eM();
			if (fz4.n() == fz.a.X) {
				return this.e.cb().c(4.0, double1, double1);
			} else {
				return fz4.n() == fz.a.Z ? this.e.cb().c(double1, double1, 4.0) : this.e.cb().c(double1, 4.0, double1);
			}
		}
	}

	class d extends awc<bec> {
		public d(bch bch2) {
			super(bch2, bec.class, true);
		}

		@Override
		public boolean a() {
			return bch.this.l.ac() == and.PEACEFUL ? false : super.a();
		}

		@Override
		protected deg a(double double1) {
			fz fz4 = ((bch)this.e).eM();
			if (fz4.n() == fz.a.X) {
				return this.e.cb().c(4.0, double1, double1);
			} else {
				return fz4.n() == fz.a.Z ? this.e.cb().c(double1, double1, 4.0) : this.e.cb().c(double1, 4.0, double1);
			}
		}
	}

	class e extends aug {
		private int b;

		private e() {
		}

		@Override
		public boolean a() {
			return bch.this.A() == null && bch.this.J.nextInt(40) == 0;
		}

		@Override
		public boolean b() {
			return bch.this.A() == null && this.b > 0;
		}

		@Override
		public void c() {
			this.b = 20 * (1 + bch.this.J.nextInt(3));
			bch.this.a(30);
		}

		@Override
		public void d() {
			if (bch.this.A() == null) {
				bch.this.a(0);
			}
		}

		@Override
		public void e() {
			this.b--;
		}
	}
}
