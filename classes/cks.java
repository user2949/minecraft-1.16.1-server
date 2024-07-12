import java.util.Random;

public class cks extends ckt<coa> {
	public static final fu a = fu.b;
	private final boolean ac;

	public cks(boolean boolean1) {
		super(coa.a);
		this.ac = boolean1;
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		for (fu fu9 : fu.a(new fu(fu.u() - 4, fu.v() - 1, fu.w() - 4), new fu(fu.u() + 4, fu.v() + 32, fu.w() + 4))) {
			boolean boolean10 = fu9.a(fu, 2.5);
			if (boolean10 || fu9.a(fu, 3.5)) {
				if (fu9.v() < fu.v()) {
					if (boolean10) {
						this.a(bqu, fu9, bvs.z.n());
					} else if (fu9.v() < fu.v()) {
						this.a(bqu, fu9, bvs.ee.n());
					}
				} else if (fu9.v() > fu.v()) {
					this.a(bqu, fu9, bvs.a.n());
				} else if (!boolean10) {
					this.a(bqu, fu9, bvs.z.n());
				} else if (this.ac) {
					this.a(bqu, new fu(fu9), bvs.ec.n());
				} else {
					this.a(bqu, new fu(fu9), bvs.a.n());
				}
			}
		}

		for (int integer8 = 0; integer8 < 4; integer8++) {
			this.a(bqu, fu.b(integer8), bvs.z.n());
		}

		fu fu8 = fu.b(2);

		for (fz fz10 : fz.c.HORIZONTAL) {
			this.a(bqu, fu8.a(fz10), bvs.bM.n().a(ccp.a, fz10));
		}

		return true;
	}
}
