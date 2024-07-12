import java.util.Random;

public class bzu extends bvr {
	public bzu(cfi.c c) {
		super(c);
	}

	protected int a(Random random) {
		if (this == bvs.H) {
			return aec.a(random, 0, 2);
		} else if (this == bvs.bT) {
			return aec.a(random, 3, 7);
		} else if (this == bvs.ej) {
			return aec.a(random, 3, 7);
		} else if (this == bvs.aq) {
			return aec.a(random, 2, 5);
		} else if (this == bvs.fx) {
			return aec.a(random, 2, 5);
		} else {
			return this == bvs.I ? aec.a(random, 0, 1) : 0;
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bki bki) {
		super.a(cfj, bqb, fu, bki);
		if (bny.a(boa.u, bki) == 0) {
			int integer6 = this.a(bqb.t);
			if (integer6 > 0) {
				this.a(bqb, fu, integer6);
			}
		}
	}
}
