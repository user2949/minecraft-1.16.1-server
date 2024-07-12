import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class cdo extends cdl implements amx, ceo {
	private final gi<bki> a = gi.a(4, bki.b);
	private final int[] b = new int[4];
	private final int[] c = new int[4];

	public cdo() {
		super(cdm.F);
	}

	@Override
	public void al_() {
		boolean boolean2 = (Boolean)this.p().c(bwb.b);
		boolean boolean3 = this.d.v;
		if (boolean3) {
			if (boolean2) {
				this.j();
			}
		} else {
			if (boolean2) {
				this.h();
			} else {
				for (int integer4 = 0; integer4 < this.a.size(); integer4++) {
					if (this.b[integer4] > 0) {
						this.b[integer4] = aec.a(this.b[integer4] - 2, 0, this.c[integer4]);
					}
				}
			}
		}
	}

	private void h() {
		for (int integer2 = 0; integer2 < this.a.size(); integer2++) {
			bki bki3 = this.a.get(integer2);
			if (!bki3.a()) {
				this.b[integer2]++;
				if (this.b[integer2] >= this.c[integer2]) {
					amz amz4 = new anm(bki3);
					bki bki5 = (bki)this.d.o().a(bmx.e, amz4, this.d).map(bml -> bml.a(amz4)).orElse(bki3);
					fu fu6 = this.o();
					anc.a(this.d, (double)fu6.u(), (double)fu6.v(), (double)fu6.w(), bki5);
					this.a.set(integer2, bki.b);
					this.k();
				}
			}
		}
	}

	private void j() {
		bqb bqb2 = this.v();
		if (bqb2 != null) {
			fu fu3 = this.o();
			Random random4 = bqb2.t;
			if (random4.nextFloat() < 0.11F) {
				for (int integer5 = 0; integer5 < random4.nextInt(2) + 2; integer5++) {
					bwb.a(bqb2, fu3, (Boolean)this.p().c(bwb.c), false);
				}
			}

			int integer5 = ((fz)this.p().c(bwb.e)).d();

			for (int integer6 = 0; integer6 < this.a.size(); integer6++) {
				if (!this.a.get(integer6).a() && random4.nextFloat() < 0.2F) {
					fz fz7 = fz.b(Math.floorMod(integer6 + integer5, 4));
					float float8 = 0.3125F;
					double double9 = (double)fu3.u() + 0.5 - (double)((float)fz7.i() * 0.3125F) + (double)((float)fz7.g().i() * 0.3125F);
					double double11 = (double)fu3.v() + 0.5;
					double double13 = (double)fu3.w() + 0.5 - (double)((float)fz7.k() * 0.3125F) + (double)((float)fz7.g().k() * 0.3125F);

					for (int integer15 = 0; integer15 < 4; integer15++) {
						bqb2.a(hh.S, double9, double11, double13, 0.0, 5.0E-4, 0.0);
					}
				}
			}
		}
	}

	public gi<bki> d() {
		return this.a;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a.clear();
		ana.b(le, this.a);
		if (le.c("CookingTimes", 11)) {
			int[] arr4 = le.n("CookingTimes");
			System.arraycopy(arr4, 0, this.b, 0, Math.min(this.c.length, arr4.length));
		}

		if (le.c("CookingTotalTimes", 11)) {
			int[] arr4 = le.n("CookingTotalTimes");
			System.arraycopy(arr4, 0, this.c, 0, Math.min(this.c.length, arr4.length));
		}
	}

	@Override
	public le a(le le) {
		this.b(le);
		le.a("CookingTimes", this.b);
		le.a("CookingTotalTimes", this.c);
		return le;
	}

	private le b(le le) {
		super.a(le);
		ana.a(le, this.a, true);
		return le;
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 13, this.b());
	}

	@Override
	public le b() {
		return this.b(new le());
	}

	public Optional<bml> a(bki bki) {
		return this.a.stream().noneMatch(bki::a) ? Optional.empty() : this.d.o().a(bmx.e, new anm(bki), this.d);
	}

	public boolean a(bki bki, int integer) {
		for (int integer4 = 0; integer4 < this.a.size(); integer4++) {
			bki bki5 = this.a.get(integer4);
			if (bki5.a()) {
				this.c[integer4] = integer;
				this.b[integer4] = 0;
				this.a.set(integer4, bki.a(1));
				this.k();
				return true;
			}
		}

		return false;
	}

	private void k() {
		this.Z_();
		this.v().a(this.o(), this.p(), this.p(), 3);
	}

	@Override
	public void aa_() {
		this.a.clear();
	}

	public void f() {
		if (this.d != null) {
			if (!this.d.v) {
				anc.a(this.d, this.o(), this.d());
			}

			this.k();
		}
	}
}
