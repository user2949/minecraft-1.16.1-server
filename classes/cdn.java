import java.util.Arrays;
import javax.annotation.Nullable;

public class cdn extends cdf implements anq, ceo {
	private static final int[] b = new int[]{3};
	private static final int[] c = new int[]{0, 1, 2, 3};
	private static final int[] g = new int[]{0, 1, 2, 4};
	private gi<bki> h = gi.a(5, bki.b);
	private int i;
	private boolean[] j;
	private bke k;
	private int l;
	protected final bgr a = new bgr() {
		@Override
		public int a(int integer) {
			switch (integer) {
				case 0:
					return cdn.this.i;
				case 1:
					return cdn.this.l;
				default:
					return 0;
			}
		}

		@Override
		public void a(int integer1, int integer2) {
			switch (integer1) {
				case 0:
					cdn.this.i = integer2;
					break;
				case 1:
					cdn.this.l = integer2;
			}
		}

		@Override
		public int a() {
			return 2;
		}
	};

	public cdn() {
		super(cdm.k);
	}

	@Override
	protected mr g() {
		return new ne("container.brewing");
	}

	@Override
	public int ab_() {
		return this.h.size();
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.h) {
			if (!bki3.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void al_() {
		bki bki2 = this.h.get(4);
		if (this.l <= 0 && bki2.b() == bkk.nz) {
			this.l = 20;
			bki2.g(1);
			this.Z_();
		}

		boolean boolean3 = this.h();
		boolean boolean4 = this.i > 0;
		bki bki5 = this.h.get(3);
		if (boolean4) {
			this.i--;
			boolean boolean6 = this.i == 0;
			if (boolean6 && boolean3) {
				this.j();
				this.Z_();
			} else if (!boolean3) {
				this.i = 0;
				this.Z_();
			} else if (this.k != bki5.b()) {
				this.i = 0;
				this.Z_();
			}
		} else if (boolean3 && this.l > 0) {
			this.l--;
			this.i = 400;
			this.k = bki5.b();
			this.Z_();
		}

		if (!this.d.v) {
			boolean[] arr6 = this.f();
			if (!Arrays.equals(arr6, this.j)) {
				this.j = arr6;
				cfj cfj7 = this.d.d_(this.o());
				if (!(cfj7.b() instanceof bvu)) {
					return;
				}

				for (int integer8 = 0; integer8 < bvu.a.length; integer8++) {
					cfj7 = cfj7.a(bvu.a[integer8], Boolean.valueOf(arr6[integer8]));
				}

				this.d.a(this.e, cfj7, 2);
			}
		}
	}

	public boolean[] f() {
		boolean[] arr2 = new boolean[3];

		for (int integer3 = 0; integer3 < 3; integer3++) {
			if (!this.h.get(integer3).a()) {
				arr2[integer3] = true;
			}
		}

		return arr2;
	}

	private boolean h() {
		bki bki2 = this.h.get(3);
		if (bki2.a()) {
			return false;
		} else if (!bmc.a(bki2)) {
			return false;
		} else {
			for (int integer3 = 0; integer3 < 3; integer3++) {
				bki bki4 = this.h.get(integer3);
				if (!bki4.a() && bmc.a(bki4, bki2)) {
					return true;
				}
			}

			return false;
		}
	}

	private void j() {
		bki bki2 = this.h.get(3);

		for (int integer3 = 0; integer3 < 3; integer3++) {
			this.h.set(integer3, bmc.d(bki2, this.h.get(integer3)));
		}

		bki2.g(1);
		fu fu3 = this.o();
		if (bki2.b().p()) {
			bki bki4 = new bki(bki2.b().o());
			if (bki2.a()) {
				bki2 = bki4;
			} else if (!this.d.v) {
				anc.a(this.d, (double)fu3.u(), (double)fu3.v(), (double)fu3.w(), bki4);
			}
		}

		this.h.set(3, bki2);
		this.d.c(1035, fu3, 0);
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.h = gi.a(this.ab_(), bki.b);
		ana.b(le, this.h);
		this.i = le.g("BrewTime");
		this.l = le.f("Fuel");
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("BrewTime", (short)this.i);
		ana.a(le, this.h);
		le.a("Fuel", (byte)this.l);
		return le;
	}

	@Override
	public bki a(int integer) {
		return integer >= 0 && integer < this.h.size() ? this.h.get(integer) : bki.b;
	}

	@Override
	public bki a(int integer1, int integer2) {
		return ana.a(this.h, integer1, integer2);
	}

	@Override
	public bki b(int integer) {
		return ana.a(this.h, integer);
	}

	@Override
	public void a(int integer, bki bki) {
		if (integer >= 0 && integer < this.h.size()) {
			this.h.set(integer, bki);
		}
	}

	@Override
	public boolean a(bec bec) {
		return this.d.c(this.e) != this ? false : !(bec.g((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) > 64.0);
	}

	@Override
	public boolean b(int integer, bki bki) {
		if (integer == 3) {
			return bmc.a(bki);
		} else {
			bke bke4 = bki.b();
			return integer == 4 ? bke4 == bkk.nz : (bke4 == bkk.nv || bke4 == bkk.qi || bke4 == bkk.ql || bke4 == bkk.nw) && this.a(integer).a();
		}
	}

	@Override
	public int[] a(fz fz) {
		if (fz == fz.UP) {
			return b;
		} else {
			return fz == fz.DOWN ? c : g;
		}
	}

	@Override
	public boolean a(int integer, bki bki, @Nullable fz fz) {
		return this.b(integer, bki);
	}

	@Override
	public boolean b(int integer, bki bki, fz fz) {
		return integer == 3 ? bki.b() == bkk.nw : true;
	}

	@Override
	public void aa_() {
		this.h.clear();
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return new bgn(integer, beb, this, this.a);
	}
}
