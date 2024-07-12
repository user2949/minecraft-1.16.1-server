import java.util.List;
import javax.annotation.Nullable;

public class bbb extends baz {
	public bbb(aoq<? extends bbb> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bbb(bqb bqb, fu fu) {
		super(aoq.O, bqb, fu);
		this.d((double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5);
		float float4 = 0.125F;
		float float5 = 0.1875F;
		float float6 = 0.25F;
		this.a(new deg(this.cC() - 0.1875, this.cD() - 0.25 + 0.125, this.cG() - 0.1875, this.cC() + 0.1875, this.cD() + 0.25 + 0.125, this.cG() + 0.1875));
		this.k = true;
	}

	@Override
	public void d(double double1, double double2, double double3) {
		super.d((double)aec.c(double1) + 0.5, (double)aec.c(double2) + 0.5, (double)aec.c(double3) + 0.5);
	}

	@Override
	protected void g() {
		this.n((double)this.c.u() + 0.5, (double)this.c.v() + 0.5, (double)this.c.w() + 0.5);
	}

	@Override
	public void a(fz fz) {
	}

	@Override
	public int i() {
		return 9;
	}

	@Override
	public int k() {
		return 9;
	}

	@Override
	protected float a(apj apj, aon aon) {
		return -0.0625F;
	}

	@Override
	public void a(@Nullable aom aom) {
		this.a(acl.gZ, 1.0F, 1.0F);
	}

	@Override
	public void b(le le) {
	}

	@Override
	public void a(le le) {
	}

	@Override
	public ang a(bec bec, anf anf) {
		if (this.l.v) {
			return ang.SUCCESS;
		} else {
			boolean boolean4 = false;
			double double5 = 7.0;
			List<aoz> list7 = this.l.a(aoz.class, new deg(this.cC() - 7.0, this.cD() - 7.0, this.cG() - 7.0, this.cC() + 7.0, this.cD() + 7.0, this.cG() + 7.0));

			for (aoz aoz9 : list7) {
				if (aoz9.eD() == bec) {
					aoz9.b(this, true);
					boolean4 = true;
				}
			}

			if (!boolean4) {
				this.aa();
				if (bec.bJ.d) {
					for (aoz aoz9x : list7) {
						if (aoz9x.eC() && aoz9x.eD() == this) {
							aoz9x.a(true, false);
						}
					}
				}
			}

			return ang.CONSUME;
		}
	}

	@Override
	public boolean h() {
		return this.l.d_(this.c).b().a(acx.L);
	}

	public static bbb a(bqb bqb, fu fu) {
		int integer3 = fu.u();
		int integer4 = fu.v();
		int integer5 = fu.w();

		for (bbb bbb8 : bqb.a(
			bbb.class,
			new deg((double)integer3 - 1.0, (double)integer4 - 1.0, (double)integer5 - 1.0, (double)integer3 + 1.0, (double)integer4 + 1.0, (double)integer5 + 1.0)
		)) {
			if (bbb8.n().equals(fu)) {
				return bbb8;
			}
		}

		bbb bbb7 = new bbb(bqb, fu);
		bqb.c(bbb7);
		bbb7.m();
		return bbb7;
	}

	@Override
	public void m() {
		this.a(acl.ha, 1.0F, 1.0F);
	}

	@Override
	public ni<?> O() {
		return new nm(this, this.U(), 0, this.n());
	}
}
