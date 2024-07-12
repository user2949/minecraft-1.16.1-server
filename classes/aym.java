import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class aym extends apq {
	private static final bmr by = bmr.a(bkk.ml, bkk.mm);
	private static final tq<Integer> bz = tt.a(aym.class, ts.b);
	private static final tq<Boolean> bA = tt.a(aym.class, ts.i);
	private static final tq<Boolean> bB = tt.a(aym.class, ts.i);
	private static final tq<Integer> bC = tt.a(aym.class, ts.b);
	public static final Map<Integer, uh> bx = v.a(Maps.<Integer, uh>newHashMap(), hashMap -> {
		hashMap.put(0, new uh("textures/entity/cat/tabby.png"));
		hashMap.put(1, new uh("textures/entity/cat/black.png"));
		hashMap.put(2, new uh("textures/entity/cat/red.png"));
		hashMap.put(3, new uh("textures/entity/cat/siamese.png"));
		hashMap.put(4, new uh("textures/entity/cat/british_shorthair.png"));
		hashMap.put(5, new uh("textures/entity/cat/calico.png"));
		hashMap.put(6, new uh("textures/entity/cat/persian.png"));
		hashMap.put(7, new uh("textures/entity/cat/ragdoll.png"));
		hashMap.put(8, new uh("textures/entity/cat/white.png"));
		hashMap.put(9, new uh("textures/entity/cat/jellie.png"));
		hashMap.put(10, new uh("textures/entity/cat/all_black.png"));
	});
	private aym.a<bec> bD;
	private avr bE;
	private float bF;
	private float bG;
	private float bH;
	private float bI;
	private float bJ;
	private float bK;

	public aym(aoq<? extends aym> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public uh eV() {
		return (uh)bx.getOrDefault(this.eW(), bx.get(0));
	}

	@Override
	protected void o() {
		this.bE = new aym.c(this, 0.6, by, true);
		this.br.a(1, new aua(this));
		this.br.a(1, new avn(this));
		this.br.a(2, new aym.b(this));
		this.br.a(3, this.bE);
		this.br.a(5, new atu(this, 1.1, 8));
		this.br.a(6, new aue(this, 1.0, 10.0F, 5.0F, false));
		this.br.a(7, new atv(this, 0.8));
		this.br.a(8, new aum(this, 0.3F));
		this.br.a(9, new auy(this));
		this.br.a(10, new att(this, 0.8));
		this.br.a(11, new avw(this, 0.8, 1.0000001E-5F));
		this.br.a(12, new auo(this, bec.class, 10.0F));
		this.bs.a(1, new awf(this, azb.class, false, null));
		this.bs.a(1, new awf(this, azi.class, false, azi.bv));
	}

	public int eW() {
		return this.S.a(bz);
	}

	public void t(int integer) {
		if (integer < 0 || integer >= 11) {
			integer = this.J.nextInt(10);
		}

		this.S.b(bz, integer);
	}

	public void x(boolean boolean1) {
		this.S.b(bA, boolean1);
	}

	public boolean eX() {
		return this.S.a(bA);
	}

	public void y(boolean boolean1) {
		this.S.b(bB, boolean1);
	}

	public boolean eY() {
		return this.S.a(bB);
	}

	public bje eZ() {
		return bje.a(this.S.a(bC));
	}

	public void a(bje bje) {
		this.S.b(bC, bje.b());
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bz, 1);
		this.S.a(bA, false);
		this.S.a(bB, false);
		this.S.a(bC, bje.RED.b());
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("CatType", this.eW());
		le.a("CollarColor", (byte)this.eZ().b());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.h("CatType"));
		if (le.c("CollarColor", 99)) {
			this.a(bje.a(le.h("CollarColor")));
		}
	}

	@Override
	public void N() {
		if (this.u().b()) {
			double double2 = this.u().c();
			if (double2 == 0.6) {
				this.b(apj.CROUCHING);
				this.g(false);
			} else if (double2 == 1.33) {
				this.b(apj.STANDING);
				this.g(true);
			} else {
				this.b(apj.STANDING);
				this.g(false);
			}
		} else {
			this.b(apj.STANDING);
			this.g(false);
		}
	}

	@Nullable
	@Override
	protected ack I() {
		if (this.eL()) {
			if (this.eT()) {
				return acl.bx;
			} else {
				return this.J.nextInt(4) == 0 ? acl.by : acl.bq;
			}
		} else {
			return acl.br;
		}
	}

	@Override
	public int D() {
		return 120;
	}

	public void fa() {
		this.a(acl.bu, this.dF(), this.dG());
	}

	@Override
	protected ack e(anw anw) {
		return acl.bw;
	}

	@Override
	protected ack dp() {
		return acl.bs;
	}

	public static apw.a fb() {
		return aoz.p().a(apx.a, 10.0).a(apx.d, 0.3F).a(apx.f, 3.0);
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected void a(bec bec, bki bki) {
		if (this.k(bki)) {
			this.a(acl.bt, 1.0F, 1.0F);
		}

		super.a(bec, bki);
	}

	private float fc() {
		return (float)this.b(apx.f);
	}

	@Override
	public boolean B(aom aom) {
		return aom.a(anw.c(this), this.fc());
	}

	@Override
	public void j() {
		super.j();
		if (this.bE != null && this.bE.h() && !this.eL() && this.K % 100 == 0) {
			this.a(acl.bv, 1.0F, 1.0F);
		}

		this.fd();
	}

	private void fd() {
		if ((this.eX() || this.eY()) && this.K % 5 == 0) {
			this.a(acl.bx, 0.6F + 0.4F * (this.J.nextFloat() - this.J.nextFloat()), 1.0F);
		}

		this.fe();
		this.ff();
	}

	private void fe() {
		this.bG = this.bF;
		this.bI = this.bH;
		if (this.eX()) {
			this.bF = Math.min(1.0F, this.bF + 0.15F);
			this.bH = Math.min(1.0F, this.bH + 0.08F);
		} else {
			this.bF = Math.max(0.0F, this.bF - 0.22F);
			this.bH = Math.max(0.0F, this.bH - 0.13F);
		}
	}

	private void ff() {
		this.bK = this.bJ;
		if (this.eY()) {
			this.bJ = Math.min(1.0F, this.bJ + 0.1F);
		} else {
			this.bJ = Math.max(0.0F, this.bJ - 0.13F);
		}
	}

	public aym a(aok aok) {
		aym aym3 = aoq.h.a(this.l);
		if (aok instanceof aym) {
			if (this.J.nextBoolean()) {
				aym3.t(this.eW());
			} else {
				aym3.t(((aym)aok).eW());
			}

			if (this.eL()) {
				aym3.b(this.B_());
				aym3.u(true);
				if (this.J.nextBoolean()) {
					aym3.a(this.eZ());
				} else {
					aym3.a(((aym)aok).eZ());
				}
			}
		}

		return aym3;
	}

	@Override
	public boolean a(ayk ayk) {
		if (!this.eL()) {
			return false;
		} else if (!(ayk instanceof aym)) {
			return false;
		} else {
			aym aym3 = (aym)ayk;
			return aym3.eL() && super.a(ayk);
		}
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo = super.a(bqc, ane, apb, apo, le);
		if (bqc.aa() > 0.9F) {
			this.t(this.J.nextInt(11));
		} else {
			this.t(this.J.nextInt(10));
		}

		bqb bqb7 = bqc.n();
		if (bqb7 instanceof zd && ((zd)bqb7).a().a(this.cA(), true, cml.j).e()) {
			this.t(10);
			this.et();
		}

		return apo;
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		bke bke5 = bki4.b();
		if (this.l.v) {
			if (this.eL() && this.j(bec)) {
				return ang.SUCCESS;
			} else {
				return !this.k(bki4) || !(this.dj() < this.dw()) && this.eL() ? ang.PASS : ang.SUCCESS;
			}
		} else {
			if (this.eL()) {
				if (this.j(bec)) {
					if (!(bke5 instanceof bjf)) {
						if (bke5.s() && this.k(bki4) && this.dj() < this.dw()) {
							this.a(bec, bki4);
							this.b((float)bke5.t().a());
							return ang.CONSUME;
						}

						ang ang6 = super.b(bec, anf);
						if (!ang6.a() || this.x_()) {
							this.w(!this.eP());
						}

						return ang6;
					}

					bje bje6 = ((bjf)bke5).d();
					if (bje6 != this.eZ()) {
						this.a(bje6);
						if (!bec.bJ.d) {
							bki4.g(1);
						}

						this.et();
						return ang.CONSUME;
					}
				}
			} else if (this.k(bki4)) {
				this.a(bec, bki4);
				if (this.J.nextInt(3) == 0) {
					this.f(bec);
					this.w(true);
					this.l.a(this, (byte)7);
				} else {
					this.l.a(this, (byte)6);
				}

				this.et();
				return ang.CONSUME;
			}

			ang ang6 = super.b(bec, anf);
			if (ang6.a()) {
				this.et();
			}

			return ang6;
		}
	}

	@Override
	public boolean k(bki bki) {
		return by.a(bki);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.5F;
	}

	@Override
	public boolean h(double double1) {
		return !this.eL() && this.K > 2400;
	}

	@Override
	protected void eM() {
		if (this.bD == null) {
			this.bD = new aym.a<>(this, bec.class, 16.0F, 0.8, 1.33);
		}

		this.br.a(this.bD);
		if (!this.eL()) {
			this.br.a(4, this.bD);
		}
	}

	static class a<T extends aoy> extends ato<T> {
		private final aym i;

		public a(aym aym, Class<T> class2, float float3, double double4, double double5) {
			super(aym, class2, float3, double4, double5, aop.e::test);
			this.i = aym;
		}

		@Override
		public boolean a() {
			return !this.i.eL() && super.a();
		}

		@Override
		public boolean b() {
			return !this.i.eL() && super.b();
		}
	}

	static class b extends aug {
		private final aym a;
		private bec b;
		private fu c;
		private int d;

		public b(aym aym) {
			this.a = aym;
		}

		@Override
		public boolean a() {
			if (!this.a.eL()) {
				return false;
			} else if (this.a.eP()) {
				return false;
			} else {
				aoy aoy2 = this.a.eO();
				if (aoy2 instanceof bec) {
					this.b = (bec)aoy2;
					if (!aoy2.el()) {
						return false;
					}

					if (this.a.h(this.b) > 100.0) {
						return false;
					}

					fu fu3 = this.b.cA();
					cfj cfj4 = this.a.l.d_(fu3);
					if (cfj4.b().a(acx.K)) {
						this.c = (fu)cfj4.d(bvm.aq).map(fz -> fu3.a(fz.f())).orElseGet(() -> new fu(fu3));
						return !this.g();
					}
				}

				return false;
			}
		}

		private boolean g() {
			for (aym aym4 : this.a.l.a(aym.class, new deg(this.c).g(2.0))) {
				if (aym4 != this.a && (aym4.eX() || aym4.eY())) {
					return true;
				}
			}

			return false;
		}

		@Override
		public boolean b() {
			return this.a.eL() && !this.a.eP() && this.b != null && this.b.el() && this.c != null && !this.g();
		}

		@Override
		public void c() {
			if (this.c != null) {
				this.a.v(false);
				this.a.x().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), 1.1F);
			}
		}

		@Override
		public void d() {
			this.a.x(false);
			float float2 = this.a.l.f(1.0F);
			if (this.b.eB() >= 100 && (double)float2 > 0.77 && (double)float2 < 0.8 && (double)this.a.l.v_().nextFloat() < 0.7) {
				this.h();
			}

			this.d = 0;
			this.a.y(false);
			this.a.x().o();
		}

		private void h() {
			Random random2 = this.a.cX();
			fu.a a3 = new fu.a();
			a3.g(this.a.cA());
			this.a.a((double)(a3.u() + random2.nextInt(11) - 5), (double)(a3.v() + random2.nextInt(5) - 2), (double)(a3.w() + random2.nextInt(11) - 5), false);
			a3.g(this.a.cA());
			daw daw4 = this.a.l.l().aH().a(dao.ak);
			dat.a a5 = new dat.a((zd)this.a.l).a(dda.f, a3).a(dda.a, this.a).a(random2);

			for (bki bki8 : daw4.a(a5.a(dcz.g))) {
				this.a
					.l
					.c(
						new bbg(
							this.a.l,
							(double)a3.u() - (double)aec.a(this.a.aH * (float) (Math.PI / 180.0)),
							(double)a3.v(),
							(double)a3.w() + (double)aec.b(this.a.aH * (float) (Math.PI / 180.0)),
							bki8
						)
					);
			}
		}

		@Override
		public void e() {
			if (this.b != null && this.c != null) {
				this.a.v(false);
				this.a.x().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), 1.1F);
				if (this.a.h(this.b) < 2.5) {
					this.d++;
					if (this.d > 16) {
						this.a.x(true);
						this.a.y(false);
					} else {
						this.a.a(this.b, 45.0F, 45.0F);
						this.a.y(true);
					}
				} else {
					this.a.x(false);
				}
			}
		}
	}

	static class c extends avr {
		@Nullable
		private bec c;
		private final aym d;

		public c(aym aym, double double2, bmr bmr, boolean boolean4) {
			super(aym, double2, bmr, boolean4);
			this.d = aym;
		}

		@Override
		public void e() {
			super.e();
			if (this.c == null && this.a.cX().nextInt(600) == 0) {
				this.c = this.b;
			} else if (this.a.cX().nextInt(500) == 0) {
				this.c = null;
			}
		}

		@Override
		protected boolean g() {
			return this.c != null && this.c.equals(this.b) ? false : super.g();
		}

		@Override
		public boolean a() {
			return super.a() && !this.d.eL();
		}
	}
}
