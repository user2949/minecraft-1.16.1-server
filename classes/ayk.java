import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class ayk extends aok {
	private int bv;
	private UUID bw;

	protected ayk(aoq<? extends ayk> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.DANGER_FIRE, 16.0F);
		this.a(czb.DAMAGE_FIRE, -1.0F);
	}

	@Override
	protected void N() {
		if (this.i() != 0) {
			this.bv = 0;
		}

		super.N();
	}

	@Override
	public void k() {
		super.k();
		if (this.i() != 0) {
			this.bv = 0;
		}

		if (this.bv > 0) {
			this.bv--;
			if (this.bv % 10 == 0) {
				double double2 = this.J.nextGaussian() * 0.02;
				double double4 = this.J.nextGaussian() * 0.02;
				double double6 = this.J.nextGaussian() * 0.02;
				this.l.a(hh.G, this.d(1.0), this.cE() + 0.5, this.g(1.0), double2, double4, double6);
			}
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			this.bv = 0;
			return super.a(anw, float2);
		}
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return bqd.d_(fu.c()).a(bvs.i) ? 10.0F : bqd.y(fu) - 0.5F;
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("InLove", this.bv);
		if (this.bw != null) {
			le.a("LoveCause", this.bw);
		}
	}

	@Override
	public double aX() {
		return 0.14;
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.bv = le.h("InLove");
		this.bw = le.b("LoveCause") ? le.a("LoveCause") : null;
	}

	public static boolean b(aoq<? extends ayk> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.d_(fu.c()).a(bvs.i) && bqc.b(fu, 0) > 8;
	}

	@Override
	public int D() {
		return 120;
	}

	@Override
	public boolean h(double double1) {
		return false;
	}

	@Override
	protected int d(bec bec) {
		return 1 + this.l.t.nextInt(3);
	}

	public boolean k(bki bki) {
		return bki.b() == bkk.kW;
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (this.k(bki4)) {
			int integer5 = this.i();
			if (!this.l.v && integer5 == 0 && this.eQ()) {
				this.a(bec, bki4);
				this.g(bec);
				return ang.SUCCESS;
			}

			if (this.x_()) {
				this.a(bec, bki4);
				this.a((int)((float)(-integer5 / 20) * 0.1F), true);
				return ang.a(this.l.v);
			}

			if (this.l.v) {
				return ang.CONSUME;
			}
		}

		return super.b(bec, anf);
	}

	protected void a(bec bec, bki bki) {
		if (!bec.bJ.d) {
			bki.g(1);
		}
	}

	public boolean eQ() {
		return this.bv <= 0;
	}

	public void g(@Nullable bec bec) {
		this.bv = 600;
		if (bec != null) {
			this.bw = bec.bR();
		}

		this.l.a(this, (byte)18);
	}

	public void s(int integer) {
		this.bv = integer;
	}

	public int eR() {
		return this.bv;
	}

	@Nullable
	public ze eS() {
		if (this.bw == null) {
			return null;
		} else {
			bec bec2 = this.l.b(this.bw);
			return bec2 instanceof ze ? (ze)bec2 : null;
		}
	}

	public boolean eT() {
		return this.bv > 0;
	}

	public void eU() {
		this.bv = 0;
	}

	public boolean a(ayk ayk) {
		if (ayk == this) {
			return false;
		} else {
			return ayk.getClass() != this.getClass() ? false : this.eT() && ayk.eT();
		}
	}

	public void a(bqb bqb, ayk ayk) {
		aok aok4 = this.a((aok)ayk);
		if (aok4 != null) {
			ze ze5 = this.eS();
			if (ze5 == null && ayk.eS() != null) {
				ze5 = ayk.eS();
			}

			if (ze5 != null) {
				ze5.a(acu.O);
				aa.o.a(ze5, this, ayk, aok4);
			}

			this.c_(6000);
			ayk.c_(6000);
			this.eU();
			ayk.eU();
			aok4.a(true);
			aok4.b(this.cC(), this.cD(), this.cG(), 0.0F, 0.0F);
			bqb.c(aok4);
			bqb.a(this, (byte)18);
			if (bqb.S().b(bpx.e)) {
				bqb.c(new aos(bqb, this.cC(), this.cD(), this.cG(), this.cX().nextInt(7) + 1));
			}
		}
	}
}
