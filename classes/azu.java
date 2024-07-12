import java.util.EnumSet;
import javax.annotation.Nullable;

public class azu extends azp {
	private int bD = 47999;

	public azu(aoq<? extends azu> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected azp fA() {
		return aoq.aK.a(this.l);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("DespawnDelay", this.bD);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("DespawnDelay", 99)) {
			this.bD = le.h("DespawnDelay");
		}
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(1, new avb(this, 2.0));
		this.bs.a(1, new azu.a(this));
	}

	@Override
	protected void h(bec bec) {
		aom aom3 = this.eD();
		if (!(aom3 instanceof bdv)) {
			super.h(bec);
		}
	}

	@Override
	public void k() {
		super.k();
		if (!this.l.v) {
			this.fF();
		}
	}

	private void fF() {
		if (this.fG()) {
			this.bD = this.fH() ? ((bdv)this.eD()).eX() - 1 : this.bD - 1;
			if (this.bD <= 0) {
				this.a(true, false);
				this.aa();
			}
		}
	}

	private boolean fG() {
		return !this.eX() && !this.fI() && !this.cp();
	}

	private boolean fH() {
		return this.eD() instanceof bdv;
	}

	private boolean fI() {
		return this.eC() && !this.fH();
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apb == apb.EVENT) {
			this.c_(0);
		}

		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(false);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	public class a extends awj {
		private final azp b;
		private aoy c;
		private int d;

		public a(azp azp) {
			super(azp, false);
			this.b = azp;
			this.a(EnumSet.of(aug.a.TARGET));
		}

		@Override
		public boolean a() {
			if (!this.b.eC()) {
				return false;
			} else {
				aom aom2 = this.b.eD();
				if (!(aom2 instanceof bdv)) {
					return false;
				} else {
					bdv bdv3 = (bdv)aom2;
					this.c = bdv3.cY();
					int integer4 = bdv3.cZ();
					return integer4 != this.d && this.a(this.c, axs.a);
				}
			}
		}

		@Override
		public void c() {
			this.e.i(this.c);
			aom aom2 = this.b.eD();
			if (aom2 instanceof bdv) {
				this.d = ((bdv)aom2).cZ();
			}

			super.c();
		}
	}
}
