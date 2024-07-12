import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class azm extends ayk implements anb, api, apm {
	private static final Predicate<aoy> bD = aoy -> aoy instanceof azm && ((azm)aoy).fc();
	private static final axs bE = new axs().a(16.0).a().b().c().a(bD);
	private static final bmr bF = bmr.a(bkk.kW, bkk.mM, bvs.gA.h(), bkk.ke, bkk.pc, bkk.lA, bkk.lB);
	private static final tq<Byte> bG = tt.a(azm.class, ts.a);
	private static final tq<Optional<UUID>> bH = tt.a(azm.class, ts.o);
	private int bI;
	private int bJ;
	private int bK;
	public int bv;
	public int bw;
	protected boolean bx;
	protected anm by;
	protected int bz;
	protected float bA;
	private boolean bL;
	private float bM;
	private float bN;
	private float bO;
	private float bP;
	private float bQ;
	private float bR;
	protected boolean bB = true;
	protected int bC;

	protected azm(aoq<? extends azm> aoq, bqb bqb) {
		super(aoq, bqb);
		this.G = 1.0F;
		this.fe();
	}

	@Override
	protected void o() {
		this.br.a(1, new avb(this, 1.2));
		this.br.a(1, new avm(this, 1.2));
		this.br.a(2, new att(this, 1.0, azm.class));
		this.br.a(4, new auf(this, 1.0));
		this.br.a(6, new avw(this, 0.7));
		this.br.a(7, new auo(this, bec.class, 6.0F));
		this.br.a(8, new ave(this));
		this.eW();
	}

	protected void eW() {
		this.br.a(0, new aua(this));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bG, (byte)0);
		this.S.a(bH, Optional.empty());
	}

	protected boolean t(int integer) {
		return (this.S.a(bG) & integer) != 0;
	}

	protected void d(int integer, boolean boolean2) {
		byte byte4 = this.S.a(bG);
		if (boolean2) {
			this.S.b(bG, (byte)(byte4 | integer));
		} else {
			this.S.b(bG, (byte)(byte4 & ~integer));
		}
	}

	public boolean eX() {
		return this.t(2);
	}

	@Nullable
	public UUID eY() {
		return (UUID)this.S.a(bH).orElse(null);
	}

	public void b(@Nullable UUID uUID) {
		this.S.b(bH, Optional.ofNullable(uUID));
	}

	public boolean eZ() {
		return this.bx;
	}

	public void u(boolean boolean1) {
		this.d(2, boolean1);
	}

	public void v(boolean boolean1) {
		this.bx = boolean1;
	}

	@Override
	protected void t(float float1) {
		if (float1 > 6.0F && this.fa()) {
			this.x(false);
		}
	}

	public boolean fa() {
		return this.t(16);
	}

	public boolean fb() {
		return this.t(32);
	}

	public boolean fc() {
		return this.t(8);
	}

	public void w(boolean boolean1) {
		this.d(8, boolean1);
	}

	@Override
	public boolean M_() {
		return this.aU() && !this.x_() && this.eX();
	}

	@Override
	public void a(@Nullable acm acm) {
		this.by.a(0, new bki(bkk.lO));
		if (acm != null) {
			this.l.a(null, this, acl.ga, acm, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean N_() {
		return this.t(4);
	}

	public int fd() {
		return this.bz;
	}

	public void u(int integer) {
		this.bz = integer;
	}

	public int v(int integer) {
		int integer3 = aec.a(this.fd() + integer, 0, this.fk());
		this.u(integer3);
		return integer3;
	}

	@Override
	public boolean aR() {
		return !this.bo();
	}

	private void eM() {
		this.eP();
		if (!this.av()) {
			ack ack2 = this.fh();
			if (ack2 != null) {
				this.l.a(null, this.cC(), this.cD(), this.cG(), ack2, this.ct(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
			}
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		if (float1 > 1.0F) {
			this.a(acl.fZ, 0.4F, 1.0F);
		}

		int integer4 = this.e(float1, float2);
		if (integer4 <= 0) {
			return false;
		} else {
			this.a(anw.k, (float)integer4);
			if (this.bo()) {
				for (aom aom6 : this.cn()) {
					aom6.a(anw.k, (float)integer4);
				}
			}

			this.ds();
			return true;
		}
	}

	@Override
	protected int e(float float1, float float2) {
		return aec.f((float1 * 0.5F - 3.0F) * float2);
	}

	protected int eO() {
		return 2;
	}

	protected void fe() {
		anm anm2 = this.by;
		this.by = new anm(this.eO());
		if (anm2 != null) {
			anm2.b(this);
			int integer3 = Math.min(anm2.ab_(), this.by.ab_());

			for (int integer4 = 0; integer4 < integer3; integer4++) {
				bki bki5 = anm2.a(integer4);
				if (!bki5.a()) {
					this.by.a(integer4, bki5.i());
				}
			}
		}

		this.by.a(this);
		this.ff();
	}

	protected void ff() {
		if (!this.l.v) {
			this.d(4, !this.by.a(0).a());
		}
	}

	@Override
	public void a(amz amz) {
		boolean boolean3 = this.N_();
		this.ff();
		if (this.K > 20 && !boolean3 && this.N_()) {
			this.a(acl.ga, 0.5F, 1.0F);
		}
	}

	public double fg() {
		return this.b(apx.m);
	}

	@Nullable
	protected ack fh() {
		return null;
	}

	@Nullable
	@Override
	protected ack dp() {
		return null;
	}

	@Nullable
	@Override
	protected ack e(anw anw) {
		if (this.J.nextInt(3) == 0) {
			this.eV();
		}

		return null;
	}

	@Nullable
	@Override
	protected ack I() {
		if (this.J.nextInt(10) == 0 && !this.dH()) {
			this.eV();
		}

		return null;
	}

	@Nullable
	protected ack fi() {
		this.eV();
		return null;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		if (!cfj.c().a()) {
			cfj cfj4 = this.l.d_(fu.b());
			cbh cbh5 = cfj.o();
			if (cfj4.a(bvs.cC)) {
				cbh5 = cfj4.o();
			}

			if (this.bo() && this.bB) {
				this.bC++;
				if (this.bC > 5 && this.bC % 3 == 0) {
					this.a(cbh5);
				} else if (this.bC <= 5) {
					this.a(acl.gc, cbh5.a() * 0.15F, cbh5.b());
				}
			} else if (cbh5 == cbh.a) {
				this.a(acl.gc, cbh5.a() * 0.15F, cbh5.b());
			} else {
				this.a(acl.gb, cbh5.a() * 0.15F, cbh5.b());
			}
		}
	}

	protected void a(cbh cbh) {
		this.a(acl.fW, cbh.a() * 0.15F, cbh.b());
	}

	public static apw.a fj() {
		return aoz.p().a(apx.m).a(apx.a, 53.0).a(apx.d, 0.225F);
	}

	@Override
	public int er() {
		return 6;
	}

	public int fk() {
		return 100;
	}

	@Override
	protected float dF() {
		return 0.8F;
	}

	@Override
	public int D() {
		return 400;
	}

	public void f(bec bec) {
		if (!this.l.v && (!this.bo() || this.w(bec)) && this.eX()) {
			bec.a(this, this.by);
		}
	}

	public ang b(bec bec, bki bki) {
		boolean boolean4 = this.c(bec, bki);
		if (!bec.bJ.d) {
			bki.g(1);
		}

		if (this.l.v) {
			return ang.CONSUME;
		} else {
			return boolean4 ? ang.SUCCESS : ang.PASS;
		}
	}

	protected boolean c(bec bec, bki bki) {
		boolean boolean4 = false;
		float float5 = 0.0F;
		int integer6 = 0;
		int integer7 = 0;
		bke bke8 = bki.b();
		if (bke8 == bkk.kW) {
			float5 = 2.0F;
			integer6 = 20;
			integer7 = 3;
		} else if (bke8 == bkk.mM) {
			float5 = 1.0F;
			integer6 = 30;
			integer7 = 3;
		} else if (bke8 == bvs.gA.h()) {
			float5 = 20.0F;
			integer6 = 180;
		} else if (bke8 == bkk.ke) {
			float5 = 3.0F;
			integer6 = 60;
			integer7 = 3;
		} else if (bke8 == bkk.pc) {
			float5 = 4.0F;
			integer6 = 60;
			integer7 = 5;
			if (!this.l.v && this.eX() && this.i() == 0 && !this.eT()) {
				boolean4 = true;
				this.g(bec);
			}
		} else if (bke8 == bkk.lA || bke8 == bkk.lB) {
			float5 = 10.0F;
			integer6 = 240;
			integer7 = 10;
			if (!this.l.v && this.eX() && this.i() == 0 && !this.eT()) {
				boolean4 = true;
				this.g(bec);
			}
		}

		if (this.dj() < this.dw() && float5 > 0.0F) {
			this.b(float5);
			boolean4 = true;
		}

		if (this.x_() && integer6 > 0) {
			this.l.a(hh.E, this.d(1.0), this.cE() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
			if (!this.l.v) {
				this.a(integer6);
			}

			boolean4 = true;
		}

		if (integer7 > 0 && (boolean4 || !this.eX()) && this.fd() < this.fk()) {
			boolean4 = true;
			if (!this.l.v) {
				this.v(integer7);
			}
		}

		if (boolean4) {
			this.eM();
		}

		return boolean4;
	}

	protected void h(bec bec) {
		this.x(false);
		this.y(false);
		if (!this.l.v) {
			bec.p = this.p;
			bec.q = this.q;
			bec.m(this);
		}
	}

	@Override
	protected boolean dH() {
		return super.dH() && this.bo() && this.N_() || this.fa() || this.fb();
	}

	@Override
	public boolean k(bki bki) {
		return bF.a(bki);
	}

	private void eN() {
		this.bv = 1;
	}

	@Override
	protected void dm() {
		super.dm();
		if (this.by != null) {
			for (int integer2 = 0; integer2 < this.by.ab_(); integer2++) {
				bki bki3 = this.by.a(integer2);
				if (!bki3.a() && !bny.e(bki3)) {
					this.a(bki3);
				}
			}
		}
	}

	@Override
	public void k() {
		if (this.J.nextInt(200) == 0) {
			this.eN();
		}

		super.k();
		if (!this.l.v && this.aU()) {
			if (this.J.nextInt(900) == 0 && this.ax == 0) {
				this.b(1.0F);
			}

			if (this.fm()) {
				if (!this.fa() && !this.bo() && this.J.nextInt(300) == 0 && this.l.d_(this.cA().c()).a(bvs.i)) {
					this.x(true);
				}

				if (this.fa() && ++this.bI > 50) {
					this.bI = 0;
					this.x(false);
				}
			}

			this.fl();
		}
	}

	protected void fl() {
		if (this.fc() && this.x_() && !this.fa()) {
			aoy aoy2 = this.l.a(azm.class, bE, this, this.cC(), this.cD(), this.cG(), this.cb().g(16.0));
			if (aoy2 != null && this.h(aoy2) > 4.0) {
				this.bq.a(aoy2, 0);
			}
		}
	}

	public boolean fm() {
		return true;
	}

	@Override
	public void j() {
		super.j();
		if (this.bJ > 0 && ++this.bJ > 30) {
			this.bJ = 0;
			this.d(64, false);
		}

		if ((this.cr() || this.dR()) && this.bK > 0 && ++this.bK > 20) {
			this.bK = 0;
			this.y(false);
		}

		if (this.bv > 0 && ++this.bv > 8) {
			this.bv = 0;
		}

		if (this.bw > 0) {
			this.bw++;
			if (this.bw > 300) {
				this.bw = 0;
			}
		}

		this.bN = this.bM;
		if (this.fa()) {
			this.bM = this.bM + (1.0F - this.bM) * 0.4F + 0.05F;
			if (this.bM > 1.0F) {
				this.bM = 1.0F;
			}
		} else {
			this.bM = this.bM + ((0.0F - this.bM) * 0.4F - 0.05F);
			if (this.bM < 0.0F) {
				this.bM = 0.0F;
			}
		}

		this.bP = this.bO;
		if (this.fb()) {
			this.bM = 0.0F;
			this.bN = this.bM;
			this.bO = this.bO + (1.0F - this.bO) * 0.4F + 0.05F;
			if (this.bO > 1.0F) {
				this.bO = 1.0F;
			}
		} else {
			this.bL = false;
			this.bO = this.bO + ((0.8F * this.bO * this.bO * this.bO - this.bO) * 0.6F - 0.05F);
			if (this.bO < 0.0F) {
				this.bO = 0.0F;
			}
		}

		this.bR = this.bQ;
		if (this.t(64)) {
			this.bQ = this.bQ + (1.0F - this.bQ) * 0.7F + 0.05F;
			if (this.bQ > 1.0F) {
				this.bQ = 1.0F;
			}
		} else {
			this.bQ = this.bQ + ((0.0F - this.bQ) * 0.7F - 0.05F);
			if (this.bQ < 0.0F) {
				this.bQ = 0.0F;
			}
		}
	}

	private void eP() {
		if (!this.l.v) {
			this.bJ = 1;
			this.d(64, true);
		}
	}

	public void x(boolean boolean1) {
		this.d(16, boolean1);
	}

	public void y(boolean boolean1) {
		if (boolean1) {
			this.x(false);
		}

		this.d(32, boolean1);
	}

	private void eV() {
		if (this.cr() || this.dR()) {
			this.bK = 1;
			this.y(true);
		}
	}

	public void fn() {
		if (!this.fb()) {
			this.eV();
			ack ack2 = this.fi();
			if (ack2 != null) {
				this.a(ack2, this.dF(), this.dG());
			}
		}
	}

	public boolean i(bec bec) {
		this.b(bec.bR());
		this.u(true);
		if (bec instanceof ze) {
			aa.x.a((ze)bec, this);
		}

		this.l.a(this, (byte)7);
		return true;
	}

	@Override
	public void f(dem dem) {
		if (this.aU()) {
			if (this.bo() && this.es() && this.N_()) {
				aoy aoy3 = (aoy)this.cl();
				this.p = aoy3.p;
				this.r = this.p;
				this.q = aoy3.q * 0.5F;
				this.a(this.p, this.q);
				this.aH = this.p;
				this.aJ = this.aH;
				float float4 = aoy3.aY * 0.5F;
				float float5 = aoy3.ba;
				if (float5 <= 0.0F) {
					float5 *= 0.25F;
					this.bC = 0;
				}

				if (this.t && this.bA == 0.0F && this.fb() && !this.bL) {
					float4 = 0.0F;
					float5 = 0.0F;
				}

				if (this.bA > 0.0F && !this.eZ() && this.t) {
					double double6 = this.fg() * (double)this.bA * (double)this.al();
					double double8;
					if (this.a(aoi.h)) {
						double8 = double6 + (double)((float)(this.b(aoi.h).c() + 1) * 0.1F);
					} else {
						double8 = double6;
					}

					dem dem10 = this.cB();
					this.m(dem10.b, double8, dem10.d);
					this.v(true);
					this.ad = true;
					if (float5 > 0.0F) {
						float float11 = aec.a(this.p * (float) (Math.PI / 180.0));
						float float12 = aec.b(this.p * (float) (Math.PI / 180.0));
						this.e(this.cB().b((double)(-0.4F * float11 * this.bA), 0.0, (double)(0.4F * float12 * this.bA)));
					}

					this.bA = 0.0F;
				}

				this.aL = this.dM() * 0.1F;
				if (this.cr()) {
					this.n((float)this.b(apx.d));
					super.f(new dem((double)float4, dem.c, (double)float5));
				} else if (aoy3 instanceof bec) {
					this.e(dem.a);
				}

				if (this.t) {
					this.bA = 0.0F;
					this.v(false);
				}

				this.a(this, false);
			} else {
				this.aL = 0.02F;
				super.f(dem);
			}
		}
	}

	protected void fo() {
		this.a(acl.fY, 0.4F, 1.0F);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("EatingHaystack", this.fa());
		le.a("Bred", this.fc());
		le.b("Temper", this.fd());
		le.a("Tame", this.eX());
		if (this.eY() != null) {
			le.a("Owner", this.eY());
		}

		if (!this.by.a(0).a()) {
			le.a("SaddleItem", this.by.a(0).b(new le()));
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.x(le.q("EatingHaystack"));
		this.w(le.q("Bred"));
		this.u(le.h("Temper"));
		this.u(le.q("Tame"));
		UUID uUID3;
		if (le.b("Owner")) {
			uUID3 = le.a("Owner");
		} else {
			String string4 = le.l("Owner");
			uUID3 = abo.a(this.cg(), string4);
		}

		if (uUID3 != null) {
			this.b(uUID3);
		}

		if (le.c("SaddleItem", 10)) {
			bki bki4 = bki.a(le.p("SaddleItem"));
			if (bki4.b() == bkk.lO) {
				this.by.a(0, bki4);
			}
		}

		this.ff();
	}

	@Override
	public boolean a(ayk ayk) {
		return false;
	}

	protected boolean fp() {
		return !this.bo() && !this.bn() && this.eX() && !this.x_() && this.dj() >= this.dw() && this.eT();
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		return null;
	}

	protected void a(aok aok, azm azm) {
		double double4 = this.c(apx.a) + aok.c(apx.a) + (double)this.fq();
		azm.a(apx.a).a(double4 / 3.0);
		double double6 = this.c(apx.m) + aok.c(apx.m) + this.fr();
		azm.a(apx.m).a(double6 / 3.0);
		double double8 = this.c(apx.d) + aok.c(apx.d) + this.fs();
		azm.a(apx.d).a(double8 / 3.0);
	}

	@Override
	public boolean es() {
		return this.cl() instanceof aoy;
	}

	@Override
	public boolean Q_() {
		return this.N_();
	}

	@Override
	public void b(int integer) {
		this.bL = true;
		this.eV();
		this.fo();
	}

	@Override
	public void c() {
	}

	@Override
	public void k(aom aom) {
		super.k(aom);
		if (aom instanceof aoz) {
			aoz aoz3 = (aoz)aom;
			this.aH = aoz3.aH;
		}

		if (this.bP > 0.0F) {
			float float3 = aec.a(this.aH * (float) (Math.PI / 180.0));
			float float4 = aec.b(this.aH * (float) (Math.PI / 180.0));
			float float5 = 0.7F * this.bP;
			float float6 = 0.15F * this.bP;
			aom.d(this.cC() + (double)(float5 * float3), this.cD() + this.aY() + aom.aX() + (double)float6, this.cG() - (double)(float5 * float4));
			if (aom instanceof aoy) {
				((aoy)aom).aH = this.aH;
			}
		}
	}

	protected float fq() {
		return 15.0F + (float)this.J.nextInt(8) + (float)this.J.nextInt(9);
	}

	protected double fr() {
		return 0.4F + this.J.nextDouble() * 0.2 + this.J.nextDouble() * 0.2 + this.J.nextDouble() * 0.2;
	}

	protected double fs() {
		return (0.45F + this.J.nextDouble() * 0.3 + this.J.nextDouble() * 0.3 + this.J.nextDouble() * 0.3) * 0.25;
	}

	@Override
	public boolean c_() {
		return false;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.95F;
	}

	public boolean ft() {
		return false;
	}

	public boolean fu() {
		return !this.b(aor.CHEST).a();
	}

	public boolean l(bki bki) {
		return false;
	}

	@Override
	public boolean a_(int integer, bki bki) {
		int integer4 = integer - 400;
		if (integer4 >= 0 && integer4 < 2 && integer4 < this.by.ab_()) {
			if (integer4 == 0 && bki.b() != bkk.lO) {
				return false;
			} else if (integer4 != 1 || this.ft() && this.l(bki)) {
				this.by.a(integer4, bki);
				this.ff();
				return true;
			} else {
				return false;
			}
		} else {
			int integer5 = integer - 500 + 2;
			if (integer5 >= 2 && integer5 < this.by.ab_()) {
				this.by.a(integer5, bki);
				return true;
			} else {
				return false;
			}
		}
	}

	@Nullable
	@Override
	public aom cl() {
		return this.cm().isEmpty() ? null : (aom)this.cm().get(0);
	}

	@Nullable
	private dem a(dem dem, aoy aoy) {
		double double4 = this.cC() + dem.b;
		double double6 = this.cb().b;
		double double8 = this.cG() + dem.d;
		fu.a a10 = new fu.a();

		for (apj apj12 : aoy.ei()) {
			a10.c(double4, double6, double8);
			double double13 = this.cb().e + 0.75;

			do {
				double double15 = this.l.m(a10);
				if ((double)a10.v() + double15 > double13) {
					break;
				}

				if (bfu.a(double15)) {
					deg deg17 = aoy.f(apj12);
					dem dem18 = new dem(double4, (double)a10.v() + double15, double8);
					if (bfu.a(this.l, aoy, deg17.c(dem18))) {
						aoy.b(apj12);
						return dem18;
					}
				}

				a10.c(fz.UP);
			} while (!((double)a10.v() < double13));
		}

		return null;
	}

	@Override
	public dem c(aoy aoy) {
		dem dem3 = a((double)this.cx(), (double)aoy.cx(), this.p + (aoy.dU() == aou.RIGHT ? 90.0F : -90.0F));
		dem dem4 = this.a(dem3, aoy);
		if (dem4 != null) {
			return dem4;
		} else {
			dem dem5 = a((double)this.cx(), (double)aoy.cx(), this.p + (aoy.dU() == aou.LEFT ? 90.0F : -90.0F));
			dem dem6 = this.a(dem5, aoy);
			return dem6 != null ? dem6 : this.cz();
		}
	}

	protected void eL() {
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(0.2F);
		}

		this.eL();
		return super.a(bqc, ane, apb, apo, le);
	}
}
