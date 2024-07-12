import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;

public class cvs extends cvw<cvx> {
	private static final cfj c = bvs.lb.n();
	private static final cfj d = bvs.E.n();
	private static final cfj e = bvs.cM.n();
	protected long a;
	protected cwe b;

	public cvs(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		int integer16 = integer10;
		int integer17 = integer4 & 15;
		int integer18 = integer5 & 15;
		double double19 = 0.03125;
		boolean boolean21 = this.b.a((double)integer4 * 0.03125, (double)integer5 * 0.03125, 0.0) * 75.0 + random.nextDouble() > 0.0;
		boolean boolean22 = this.b.a((double)integer4 * 0.03125, 109.0, (double)integer5 * 0.03125) * 75.0 + random.nextDouble() > 0.0;
		int integer23 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		fu.a a24 = new fu.a();
		int integer25 = -1;
		cfj cfj26 = cvx.a();
		cfj cfj27 = cvx.b();

		for (int integer28 = 127; integer28 >= 0; integer28--) {
			a24.d(integer17, integer28, integer18);
			cfj cfj29 = cgy.d_(a24);
			if (cfj29.g()) {
				integer25 = -1;
			} else if (cfj29.a(cfj8.b())) {
				if (integer25 == -1) {
					boolean boolean30 = false;
					if (integer23 <= 0) {
						boolean30 = true;
						cfj27 = cvx.b();
					} else if (integer28 >= integer16 - 4 && integer28 <= integer16 + 1) {
						cfj26 = cvx.a();
						cfj27 = cvx.b();
						if (boolean22) {
							cfj26 = d;
							cfj27 = cvx.b();
						}

						if (boolean21) {
							cfj26 = e;
							cfj27 = e;
						}
					}

					if (integer28 < integer16 && boolean30) {
						cfj26 = cfj9;
					}

					integer25 = integer23;
					if (integer28 >= integer16 - 1) {
						cgy.a(a24, cfj26, false);
					} else {
						cgy.a(a24, cfj27, false);
					}
				} else if (integer25 > 0) {
					integer25--;
					cgy.a(a24, cfj27, false);
				}
			}
		}
	}

	@Override
	public void a(long long1) {
		if (this.a != long1 || this.b == null) {
			this.b = new cwe(new ciy(long1), IntStream.rangeClosed(-3, 0));
		}

		this.a = long1;
	}
}
