import com.mojang.serialization.Codec;
import java.util.Random;

public class cwa extends cvh {
	private static final cfj ah = bvs.fF.n();
	private static final cfj ai = bvs.fG.n();
	private static final cfj aj = bvs.gR.n();

	public cwa(Codec<cvx> codec) {
		super(codec);
	}

	@Override
	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		int integer16 = integer4 & 15;
		int integer17 = integer5 & 15;
		cfj cfj18 = ah;
		cfj cfj19 = bre.A().b();
		int integer20 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		boolean boolean21 = Math.cos(double7 / 3.0 * Math.PI) > 0.0;
		int integer22 = -1;
		boolean boolean23 = false;
		int integer24 = 0;
		fu.a a25 = new fu.a();

		for (int integer26 = integer6; integer26 >= 0; integer26--) {
			if (integer24 < 15) {
				a25.d(integer16, integer26, integer17);
				cfj cfj27 = cgy.d_(a25);
				if (cfj27.g()) {
					integer22 = -1;
				} else if (cfj27.a(cfj8.b())) {
					if (integer22 == -1) {
						boolean23 = false;
						if (integer20 <= 0) {
							cfj18 = bvs.a.n();
							cfj19 = cfj8;
						} else if (integer26 >= integer10 - 4 && integer26 <= integer10 + 1) {
							cfj18 = ah;
							cfj19 = bre.A().b();
						}

						if (integer26 < integer10 && (cfj18 == null || cfj18.g())) {
							cfj18 = cfj9;
						}

						integer22 = integer20 + Math.max(0, integer26 - integer10);
						if (integer26 < integer10 - 1) {
							cgy.a(a25, cfj19, false);
							if (cfj19 == ah) {
								cgy.a(a25, ai, false);
							}
						} else if (integer26 > 86 + integer20 * 2) {
							if (boolean21) {
								cgy.a(a25, bvs.k.n(), false);
							} else {
								cgy.a(a25, bvs.i.n(), false);
							}
						} else if (integer26 <= integer10 + 3 + integer20) {
							cgy.a(a25, bre.A().a(), false);
							boolean23 = true;
						} else {
							cfj cfj28;
							if (integer26 < 64 || integer26 > 127) {
								cfj28 = ai;
							} else if (boolean21) {
								cfj28 = aj;
							} else {
								cfj28 = this.a(integer4, integer26, integer5);
							}

							cgy.a(a25, cfj28, false);
						}
					} else if (integer22 > 0) {
						integer22--;
						if (boolean23) {
							cgy.a(a25, ai, false);
						} else {
							cgy.a(a25, this.a(integer4, integer26, integer5), false);
						}
					}

					integer24++;
				}
			}
		}
	}
}
