import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;

public class cdj extends cdl implements ceo {
	private long g;
	public int a;
	public boolean b;
	public fz c;
	private List<aoy> h;
	private boolean i;
	private int j;

	public cdj() {
		super(cdm.D);
	}

	@Override
	public boolean a_(int integer1, int integer2) {
		if (integer1 == 1) {
			this.f();
			this.j = 0;
			this.c = fz.a(integer2);
			this.a = 0;
			this.b = true;
			return true;
		} else {
			return super.a_(integer1, integer2);
		}
	}

	@Override
	public void al_() {
		if (this.b) {
			this.a++;
		}

		if (this.a >= 50) {
			this.b = false;
			this.a = 0;
		}

		if (this.a >= 5 && this.j == 0 && this.h()) {
			this.i = true;
			this.d();
		}

		if (this.i) {
			if (this.j < 40) {
				this.j++;
			} else {
				this.a(this.d);
				this.b(this.d);
				this.i = false;
			}
		}
	}

	private void d() {
		this.d.a(null, this.o(), acl.aK, acm.BLOCKS, 1.0F, 1.0F);
	}

	public void a(fz fz) {
		fu fu3 = this.o();
		this.c = fz;
		if (this.b) {
			this.a = 0;
		} else {
			this.b = true;
		}

		this.d.a(fu3, this.p().b(), 1, fz.c());
	}

	private void f() {
		fu fu2 = this.o();
		if (this.d.Q() > this.g + 60L || this.h == null) {
			this.g = this.d.Q();
			deg deg3 = new deg(fu2).g(48.0);
			this.h = this.d.a(aoy.class, deg3);
		}

		if (!this.d.v) {
			for (aoy aoy4 : this.h) {
				if (aoy4.aU() && !aoy4.y && fu2.a(aoy4.cz(), 32.0)) {
					aoy4.cI().a(awp.C, this.d.Q());
				}
			}
		}
	}

	private boolean h() {
		fu fu2 = this.o();

		for (aoy aoy4 : this.h) {
			if (aoy4.aU() && !aoy4.y && fu2.a(aoy4.cz(), 32.0) && aoy4.U().a(acy.b)) {
				return true;
			}
		}

		return false;
	}

	private void a(bqb bqb) {
		if (!bqb.v) {
			this.h.stream().filter(this::a).forEach(this::b);
		}
	}

	private void b(bqb bqb) {
		if (bqb.v) {
			fu fu3 = this.o();
			MutableInt mutableInt4 = new MutableInt(16700985);
			int integer5 = (int)this.h.stream().filter(aoy -> fu3.a(aoy.cz(), 48.0)).count();
			this.h.stream().filter(this::a).forEach(aoy -> {
				float float6 = 1.0F;
				float float7 = aec.a((aoy.cC() - (double)fu3.u()) * (aoy.cC() - (double)fu3.u()) + (aoy.cG() - (double)fu3.w()) * (aoy.cG() - (double)fu3.w()));
				double double8 = (double)((float)fu3.u() + 0.5F) + (double)(1.0F / float7) * (aoy.cC() - (double)fu3.u());
				double double10 = (double)((float)fu3.w() + 0.5F) + (double)(1.0F / float7) * (aoy.cG() - (double)fu3.w());
				int integer12 = aec.a((integer5 - 21) / -2, 3, 15);

				for (int integer13 = 0; integer13 < integer12; integer13++) {
					int integer14 = mutableInt4.addAndGet(5);
					double double15 = (double)adr.a.b(integer14) / 255.0;
					double double17 = (double)adr.a.c(integer14) / 255.0;
					double double19 = (double)adr.a.d(integer14) / 255.0;
					bqb.a(hh.u, double8, (double)((float)fu3.v() + 0.5F), double10, double15, double17, double19);
				}
			});
		}
	}

	private boolean a(aoy aoy) {
		return aoy.aU() && !aoy.y && this.o().a(aoy.cz(), 48.0) && aoy.U().a(acy.b);
	}

	private void b(aoy aoy) {
		aoy.c(new aog(aoi.x, 60));
	}
}
