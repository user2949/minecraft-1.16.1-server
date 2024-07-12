import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;

public class bei extends beg {
	private static final tq<Integer> f = tt.a(bei.class, ts.b);
	private bmb g = bme.a;
	private final Set<aog> an = Sets.<aog>newHashSet();
	private boolean ao;

	public bei(aoq<? extends bei> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bei(bqb bqb, double double2, double double3, double double4) {
		super(aoq.c, double2, double3, double4, bqb);
	}

	public bei(bqb bqb, aoy aoy) {
		super(aoq.c, aoy, bqb);
	}

	public void b(bki bki) {
		if (bki.b() == bkk.qk) {
			this.g = bmd.d(bki);
			Collection<aog> collection3 = bmd.b(bki);
			if (!collection3.isEmpty()) {
				for (aog aog5 : collection3) {
					this.an.add(new aog(aog5));
				}
			}

			int integer4 = c(bki);
			if (integer4 == -1) {
				this.z();
			} else {
				this.c(integer4);
			}
		} else if (bki.b() == bkk.kg) {
			this.g = bme.a;
			this.an.clear();
			this.S.b(f, -1);
		}
	}

	public static int c(bki bki) {
		le le2 = bki.o();
		return le2 != null && le2.c("CustomPotionColor", 99) ? le2.h("CustomPotionColor") : -1;
	}

	private void z() {
		this.ao = false;
		if (this.g == bme.a && this.an.isEmpty()) {
			this.S.b(f, -1);
		} else {
			this.S.b(f, bmd.a(bmd.a(this.g, this.an)));
		}
	}

	public void a(aog aog) {
		this.an.add(aog);
		this.Y().b(f, bmd.a(bmd.a(this.g, this.an)));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(f, -1);
	}

	@Override
	public void j() {
		super.j();
		if (this.l.v) {
			if (this.b) {
				if (this.c % 5 == 0) {
					this.b(1);
				}
			} else {
				this.b(2);
			}
		} else if (this.b && this.c != 0 && !this.an.isEmpty() && this.c >= 600) {
			this.l.a(this, (byte)0);
			this.g = bme.a;
			this.an.clear();
			this.S.b(f, -1);
		}
	}

	private void b(int integer) {
		int integer3 = this.u();
		if (integer3 != -1 && integer > 0) {
			double double4 = (double)(integer3 >> 16 & 0xFF) / 255.0;
			double double6 = (double)(integer3 >> 8 & 0xFF) / 255.0;
			double double8 = (double)(integer3 >> 0 & 0xFF) / 255.0;

			for (int integer10 = 0; integer10 < integer; integer10++) {
				this.l.a(hh.u, this.d(0.5), this.cE(), this.g(0.5), double4, double6, double8);
			}
		}
	}

	public int u() {
		return this.S.a(f);
	}

	private void c(int integer) {
		this.ao = true;
		this.S.b(f, integer);
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.g != bme.a && this.g != null) {
			le.a("Potion", gl.an.b(this.g).toString());
		}

		if (this.ao) {
			le.b("Color", this.u());
		}

		if (!this.an.isEmpty()) {
			lk lk3 = new lk();

			for (aog aog5 : this.an) {
				lk3.add(aog5.a(new le()));
			}

			le.a("CustomPotionEffects", lk3);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("Potion", 8)) {
			this.g = bmd.c(le);
		}

		for (aog aog4 : bmd.b(le)) {
			this.a(aog4);
		}

		if (le.c("Color", 99)) {
			this.c(le.h("Color"));
		} else {
			this.z();
		}
	}

	@Override
	protected void a(aoy aoy) {
		super.a(aoy);

		for (aog aog4 : this.g.a()) {
			aoy.c(new aog(aog4.a(), Math.max(aog4.b() / 8, 1), aog4.c(), aog4.d(), aog4.e()));
		}

		if (!this.an.isEmpty()) {
			for (aog aog4 : this.an) {
				aoy.c(aog4);
			}
		}
	}

	@Override
	protected bki m() {
		if (this.an.isEmpty() && this.g == bme.a) {
			return new bki(bkk.kg);
		} else {
			bki bki2 = new bki(bkk.qk);
			bmd.a(bki2, this.g);
			bmd.a(bki2, this.an);
			if (this.ao) {
				bki2.p().b("CustomPotionColor", this.u());
			}

			return bki2;
		}
	}
}
