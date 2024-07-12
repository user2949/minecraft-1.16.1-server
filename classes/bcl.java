import java.util.EnumSet;
import javax.annotation.Nullable;

public abstract class bcl extends bbj {
	private static final tq<Byte> bv = tt.a(bcl.class, ts.a);
	protected int b;
	private bcl.a bw = bcl.a.NONE;

	protected bcl(aoq<? extends bcl> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, (byte)0);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.b = le.h("SpellTicks");
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("SpellTicks", this.b);
	}

	public boolean eX() {
		return this.l.v ? this.S.a(bv) > 0 : this.b > 0;
	}

	public void a(bcl.a a) {
		this.bw = a;
		this.S.b(bv, (byte)a.g);
	}

	protected bcl.a eY() {
		return !this.l.v ? this.bw : bcl.a.a(this.S.a(bv));
	}

	@Override
	protected void N() {
		super.N();
		if (this.b > 0) {
			this.b--;
		}
	}

	@Override
	public void j() {
		super.j();
		if (this.l.v && this.eX()) {
			bcl.a a2 = this.eY();
			double double3 = a2.h[0];
			double double5 = a2.h[1];
			double double7 = a2.h[2];
			float float9 = this.aH * (float) (Math.PI / 180.0) + aec.b((float)this.K * 0.6662F) * 0.25F;
			float float10 = aec.b(float9);
			float float11 = aec.a(float9);
			this.l.a(hh.u, this.cC() + (double)float10 * 0.6, this.cD() + 1.8, this.cG() + (double)float11 * 0.6, double3, double5, double7);
			this.l.a(hh.u, this.cC() - (double)float10 * 0.6, this.cD() + 1.8, this.cG() - (double)float11 * 0.6, double3, double5, double7);
		}
	}

	protected int eZ() {
		return this.b;
	}

	protected abstract ack eN();

	public static enum a {
		NONE(0, 0.0, 0.0, 0.0),
		SUMMON_VEX(1, 0.7, 0.7, 0.8),
		FANGS(2, 0.4, 0.3, 0.35),
		WOLOLO(3, 0.7, 0.5, 0.2),
		DISAPPEAR(4, 0.3, 0.3, 0.8),
		BLINDNESS(5, 0.1, 0.1, 0.2);

		private final int g;
		private final double[] h;

		private a(int integer3, double double4, double double5, double double6) {
			this.g = integer3;
			this.h = new double[]{double4, double5, double6};
		}

		public static bcl.a a(int integer) {
			for (bcl.a a5 : values()) {
				if (integer == a5.g) {
					return a5;
				}
			}

			return NONE;
		}
	}

	public class b extends aug {
		public b() {
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			return bcl.this.eZ() > 0;
		}

		@Override
		public void c() {
			super.c();
			bcl.this.bq.o();
		}

		@Override
		public void d() {
			super.d();
			bcl.this.a(bcl.a.NONE);
		}

		@Override
		public void e() {
			if (bcl.this.A() != null) {
				bcl.this.t().a(bcl.this.A(), (float)bcl.this.ep(), (float)bcl.this.eo());
			}
		}
	}

	public abstract class c extends aug {
		protected int b;
		protected int c;

		protected c() {
		}

		@Override
		public boolean a() {
			aoy aoy2 = bcl.this.A();
			if (aoy2 == null || !aoy2.aU()) {
				return false;
			} else {
				return bcl.this.eX() ? false : bcl.this.K >= this.c;
			}
		}

		@Override
		public boolean b() {
			aoy aoy2 = bcl.this.A();
			return aoy2 != null && aoy2.aU() && this.b > 0;
		}

		@Override
		public void c() {
			this.b = this.m();
			bcl.this.b = this.g();
			this.c = bcl.this.K + this.h();
			ack ack2 = this.k();
			if (ack2 != null) {
				bcl.this.a(ack2, 1.0F, 1.0F);
			}

			bcl.this.a(this.l());
		}

		@Override
		public void e() {
			this.b--;
			if (this.b == 0) {
				this.j();
				bcl.this.a(bcl.this.eN(), 1.0F, 1.0F);
			}
		}

		protected abstract void j();

		protected int m() {
			return 20;
		}

		protected abstract int g();

		protected abstract int h();

		@Nullable
		protected abstract ack k();

		protected abstract bcl.a l();
	}
}
