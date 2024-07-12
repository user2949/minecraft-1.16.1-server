import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.UUID;
import javax.annotation.Nullable;

public class bcv extends bcu implements bdr {
	private static final tq<Boolean> b = tt.a(bcv.class, ts.i);
	private static final tq<bdq> c = tt.a(bcv.class, ts.q);
	private int d;
	private UUID bv;
	private lu bw;
	private le bx;
	private int by;

	public bcv(aoq<? extends bcv> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(this.eY().a(gl.aS.a(this.J)));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, false);
		this.S.a(c, new bdq(bdu.c, bds.a, 1));
	}

	@Override
	public void b(le le) {
		super.b(le);
		bdq.a.encodeStart(lp.a, this.eY()).resultOrPartial(h::error).ifPresent(lu -> le.a("VillagerData", lu));
		if (this.bx != null) {
			le.a("Offers", this.bx);
		}

		if (this.bw != null) {
			le.a("Gossips", this.bw);
		}

		le.b("ConversionTime", this.eX() ? this.d : -1);
		if (this.bv != null) {
			le.a("ConversionPlayer", this.bv);
		}

		le.b("Xp", this.by);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("VillagerData", 10)) {
			DataResult<bdq> dataResult3 = bdq.a.parse(new Dynamic<>(lp.a, le.c("VillagerData")));
			dataResult3.resultOrPartial(h::error).ifPresent(this::a);
		}

		if (le.c("Offers", 10)) {
			this.bx = le.p("Offers");
		}

		if (le.c("Gossips", 10)) {
			this.bw = le.d("Gossips", 10);
		}

		if (le.c("ConversionTime", 99) && le.h("ConversionTime") > -1) {
			this.a(le.b("ConversionPlayer") ? le.a("ConversionPlayer") : null, le.h("ConversionTime"));
		}

		if (le.c("Xp", 3)) {
			this.by = le.h("Xp");
		}
	}

	@Override
	public void j() {
		if (!this.l.v && this.aU() && this.eX()) {
			int integer2 = this.fa();
			this.d -= integer2;
			if (this.d <= 0) {
				this.b((zd)this.l);
			}
		}

		super.j();
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.lA) {
			if (this.a(aoi.r)) {
				if (!bec.bJ.d) {
					bki4.g(1);
				}

				if (!this.l.v) {
					this.a(bec.bR(), this.J.nextInt(2401) + 3600);
				}

				return ang.SUCCESS;
			} else {
				return ang.CONSUME;
			}
		} else {
			return super.b(bec, anf);
		}
	}

	@Override
	protected boolean eO() {
		return false;
	}

	@Override
	public boolean h(double double1) {
		return !this.eX() && this.by == 0;
	}

	public boolean eX() {
		return this.Y().a(b);
	}

	private void a(@Nullable UUID uUID, int integer) {
		this.bv = uUID;
		this.d = integer;
		this.Y().b(b, true);
		this.d(aoi.r);
		this.c(new aog(aoi.e, integer, Math.min(this.l.ac().a() - 1, 0)));
		this.l.a(this, (byte)16);
	}

	private void b(zd zd) {
		bdp bdp3 = aoq.aO.a(zd);

		for (aor aor7 : aor.values()) {
			bki bki8 = this.b(aor7);
			if (!bki8.a()) {
				if (bny.d(bki8)) {
					bdp3.a_(aor7.b() + 300, bki8);
				} else {
					double double9 = (double)this.e(aor7);
					if (double9 > 1.0) {
						this.a(bki8);
					}
				}
			}
		}

		bdp3.u(this);
		bdp3.a(this.eY());
		if (this.bw != null) {
			bdp3.a(this.bw);
		}

		if (this.bx != null) {
			bdp3.b(new bpa(this.bx));
		}

		bdp3.u(this.by);
		bdp3.a(zd, zd.d(bdp3.cA()), apb.CONVERSION, null, null);
		if (this.x_()) {
			bdp3.c_(-24000);
		}

		this.aa();
		bdp3.q(this.eE());
		if (this.Q()) {
			bdp3.a(this.R());
			bdp3.n(this.bW());
		}

		if (this.ev()) {
			bdp3.et();
		}

		bdp3.m(this.bI());
		zd.c(bdp3);
		if (this.bv != null) {
			bec bec4 = zd.b(this.bv);
			if (bec4 instanceof ze) {
				aa.r.a((ze)bec4, this, bdp3);
				zd.a(axw.a, bec4, bdp3);
			}
		}

		bdp3.c(new aog(aoi.i, 200, 0));
		if (!this.av()) {
			zd.a(null, 1027, this.cA(), 0);
		}
	}

	private int fa() {
		int integer2 = 1;
		if (this.J.nextFloat() < 0.01F) {
			int integer3 = 0;
			fu.a a4 = new fu.a();

			for (int integer5 = (int)this.cC() - 4; integer5 < (int)this.cC() + 4 && integer3 < 14; integer5++) {
				for (int integer6 = (int)this.cD() - 4; integer6 < (int)this.cD() + 4 && integer3 < 14; integer6++) {
					for (int integer7 = (int)this.cG() - 4; integer7 < (int)this.cG() + 4 && integer3 < 14; integer7++) {
						bvr bvr8 = this.l.d_(a4.d(integer5, integer6, integer7)).b();
						if (bvr8 == bvs.dH || bvr8 instanceof bvm) {
							if (this.J.nextFloat() < 0.3F) {
								integer2++;
							}

							integer3++;
						}
					}
				}
			}
		}

		return integer2;
	}

	@Override
	protected float dG() {
		return this.x_() ? (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 2.0F : (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F;
	}

	@Override
	public ack I() {
		return acl.rR;
	}

	@Override
	public ack e(anw anw) {
		return acl.rV;
	}

	@Override
	public ack dp() {
		return acl.rU;
	}

	@Override
	public ack eM() {
		return acl.rW;
	}

	@Override
	protected bki eN() {
		return bki.b;
	}

	public void g(le le) {
		this.bx = le;
	}

	public void a(lu lu) {
		this.bw = lu;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.a(this.eY().a(bdu.a(bqc.v(this.cA()))));
		return super.a(bqc, ane, apb, apo, le);
	}

	public void a(bdq bdq) {
		bdq bdq3 = this.eY();
		if (bdq3.b() != bdq.b()) {
			this.bx = null;
		}

		this.S.b(c, bdq);
	}

	@Override
	public bdq eY() {
		return this.S.a(c);
	}

	@Override
	public void a(int integer) {
		this.by = integer;
	}
}
