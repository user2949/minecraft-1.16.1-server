import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;

public class cvr extends cvw<cvx> {
	private static final cfj b = bvs.lb.n();
	protected long a;
	private cwe c;

	public cvr(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		int integer16 = integer10;
		int integer17 = integer4 & 15;
		int integer18 = integer5 & 15;
		double double19 = this.c.a((double)integer4 * 0.1, (double)integer10, (double)integer5 * 0.1);
		boolean boolean21 = double19 > 0.15 + random.nextDouble() * 0.35;
		double double22 = this.c.a((double)integer4 * 0.1, 109.0, (double)integer5 * 0.1);
		boolean boolean24 = double22 > 0.25 + random.nextDouble() * 0.9;
		int integer25 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		fu.a a26 = new fu.a();
		int integer27 = -1;
		cfj cfj28 = cvx.b();

		for (int integer29 = 127; integer29 >= 0; integer29--) {
			a26.d(integer17, integer29, integer18);
			cfj cfj30 = cvx.a();
			cfj cfj31 = cgy.d_(a26);
			if (cfj31.g()) {
				integer27 = -1;
			} else if (cfj31.a(cfj8.b())) {
				if (integer27 == -1) {
					boolean boolean32 = false;
					if (integer25 <= 0) {
						boolean32 = true;
						cfj28 = cvx.b();
					}

					if (boolean21) {
						cfj30 = cvx.b();
					} else if (boolean24) {
						cfj30 = cvx.c();
					}

					if (integer29 < integer16 && boolean32) {
						cfj30 = cfj9;
					}

					integer27 = integer25;
					if (integer29 >= integer16 - 1) {
						cgy.a(a26, cfj30, false);
					} else {
						cgy.a(a26, cfj28, false);
					}
				} else if (integer27 > 0) {
					integer27--;
					cgy.a(a26, cfj28, false);
				}
			}
		}
	}

	@Override
	public void a(long long1) {
		if (this.a != long1 || this.c == null) {
			this.c = new cwe(new ciy(long1), ImmutableList.of(0));
		}

		this.a = long1;
	}
}
