import java.util.Random;

public abstract class ctu extends cty {
	protected final int a;
	protected final int b;
	protected final int c;
	protected int d = -1;

	protected ctu(cmm cmm, Random random, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
		super(cmm, 0);
		this.a = integer6;
		this.b = integer7;
		this.c = integer8;
		this.a(fz.c.HORIZONTAL.a(random));
		if (this.i().n() == fz.a.Z) {
			this.n = new ctd(integer3, integer4, integer5, integer3 + integer6 - 1, integer4 + integer7 - 1, integer5 + integer8 - 1);
		} else {
			this.n = new ctd(integer3, integer4, integer5, integer3 + integer8 - 1, integer4 + integer7 - 1, integer5 + integer6 - 1);
		}
	}

	protected ctu(cmm cmm, le le) {
		super(cmm, le);
		this.a = le.h("Width");
		this.b = le.h("Height");
		this.c = le.h("Depth");
		this.d = le.h("HPos");
	}

	@Override
	protected void a(le le) {
		le.b("Width", this.a);
		le.b("Height", this.b);
		le.b("Depth", this.c);
		le.b("HPos", this.d);
	}

	protected boolean a(bqc bqc, ctd ctd, int integer) {
		if (this.d >= 0) {
			return true;
		} else {
			int integer5 = 0;
			int integer6 = 0;
			fu.a a7 = new fu.a();

			for (int integer8 = this.n.c; integer8 <= this.n.f; integer8++) {
				for (int integer9 = this.n.a; integer9 <= this.n.d; integer9++) {
					a7.d(integer9, 64, integer8);
					if (ctd.b(a7)) {
						integer5 += bqc.a(cio.a.MOTION_BLOCKING_NO_LEAVES, a7).v();
						integer6++;
					}
				}
			}

			if (integer6 == 0) {
				return false;
			} else {
				this.d = integer5 / integer6;
				this.n.a(0, this.d - this.n.b + integer, 0);
				return true;
			}
		}
	}
}
