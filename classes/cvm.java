import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;

public class cvm extends cvw<cvx> {
	protected static final cfj a = bvs.gT.n();
	protected static final cfj b = bvs.cE.n();
	private static final cfj c = bvs.a.n();
	private static final cfj d = bvs.E.n();
	private static final cfj e = bvs.cD.n();
	private cwf ah;
	private cwf ai;
	private long aj;

	public cvm(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		double double16 = 0.0;
		double double18 = 0.0;
		fu.a a20 = new fu.a();
		float float21 = bre.b(a20.d(integer4, 63, integer5));
		double double22 = Math.min(Math.abs(double7), this.ah.a((double)integer4 * 0.1, (double)integer5 * 0.1, false) * 15.0);
		if (double22 > 1.8) {
			double double24 = 0.09765625;
			double double26 = Math.abs(this.ai.a((double)integer4 * 0.09765625, (double)integer5 * 0.09765625, false));
			double16 = double22 * double22 * 1.2;
			double double28 = Math.ceil(double26 * 40.0) + 14.0;
			if (double16 > double28) {
				double16 = double28;
			}

			if (float21 > 0.1F) {
				double16 -= 2.0;
			}

			if (double16 > 2.0) {
				double18 = (double)integer10 - double16 - 7.0;
				double16 += (double)integer10;
			} else {
				double16 = 0.0;
			}
		}

		int integer24 = integer4 & 15;
		int integer25 = integer5 & 15;
		cfj cfj26 = bre.A().b();
		cfj cfj27 = bre.A().a();
		int integer28 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		int integer29 = -1;
		int integer30 = 0;
		int integer31 = 2 + random.nextInt(4);
		int integer32 = integer10 + 18 + random.nextInt(10);

		for (int integer33 = Math.max(integer6, (int)double16 + 1); integer33 >= 0; integer33--) {
			a20.d(integer24, integer33, integer25);
			if (cgy.d_(a20).g() && integer33 < (int)double16 && random.nextDouble() > 0.01) {
				cgy.a(a20, a, false);
			} else if (cgy.d_(a20).c() == cxd.i && integer33 > (int)double18 && integer33 < integer10 && double18 != 0.0 && random.nextDouble() > 0.15) {
				cgy.a(a20, a, false);
			}

			cfj cfj34 = cgy.d_(a20);
			if (cfj34.g()) {
				integer29 = -1;
			} else if (cfj34.a(cfj8.b())) {
				if (integer29 == -1) {
					if (integer28 <= 0) {
						cfj27 = c;
						cfj26 = cfj8;
					} else if (integer33 >= integer10 - 4 && integer33 <= integer10 + 1) {
						cfj27 = bre.A().a();
						cfj26 = bre.A().b();
					}

					if (integer33 < integer10 && (cfj27 == null || cfj27.g())) {
						if (bre.b(a20.d(integer4, integer33, integer5)) < 0.15F) {
							cfj27 = e;
						} else {
							cfj27 = cfj9;
						}
					}

					integer29 = integer28;
					if (integer33 >= integer10 - 1) {
						cgy.a(a20, cfj27, false);
					} else if (integer33 < integer10 - 7 - integer28) {
						cfj27 = c;
						cfj26 = cfj8;
						cgy.a(a20, d, false);
					} else {
						cgy.a(a20, cfj26, false);
					}
				} else if (integer29 > 0) {
					integer29--;
					cgy.a(a20, cfj26, false);
					if (integer29 == 0 && cfj26.a(bvs.C) && integer28 > 1) {
						integer29 = random.nextInt(4) + Math.max(0, integer33 - 63);
						cfj26 = cfj26.a(bvs.D) ? bvs.hG.n() : bvs.at.n();
					}
				}
			} else if (cfj34.a(bvs.gT) && integer30 <= integer31 && integer33 > integer32) {
				cgy.a(a20, b, false);
				integer30++;
			}
		}
	}

	@Override
	public void a(long long1) {
		if (this.aj != long1 || this.ah == null || this.ai == null) {
			ciy ciy4 = new ciy(long1);
			this.ah = new cwf(ciy4, IntStream.rangeClosed(-3, 0));
			this.ai = new cwf(ciy4, ImmutableList.of(0));
		}

		this.aj = long1;
	}
}
