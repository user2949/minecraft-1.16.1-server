import com.mojang.serialization.Codec;
import java.util.Random;

public class cvk extends cvw<cvx> {
	public cvk(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		this.a(random, cgy, bre, integer4, integer5, integer6, double7, cfj8, cfj9, cvx.a(), cvx.b(), cvx.c(), integer10);
	}

	protected void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, cfj cfj10, cfj cfj11, cfj cfj12, int integer13
	) {
		cfj cfj16 = cfj10;
		cfj cfj17 = cfj11;
		fu.a a18 = new fu.a();
		int integer19 = -1;
		int integer20 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		int integer21 = integer4 & 15;
		int integer22 = integer5 & 15;

		for (int integer23 = integer6; integer23 >= 0; integer23--) {
			a18.d(integer21, integer23, integer22);
			cfj cfj24 = cgy.d_(a18);
			if (cfj24.g()) {
				integer19 = -1;
			} else if (cfj24.a(cfj8.b())) {
				if (integer19 == -1) {
					if (integer20 <= 0) {
						cfj16 = bvs.a.n();
						cfj17 = cfj8;
					} else if (integer23 >= integer13 - 4 && integer23 <= integer13 + 1) {
						cfj16 = cfj10;
						cfj17 = cfj11;
					}

					if (integer23 < integer13 && (cfj16 == null || cfj16.g())) {
						if (bre.b(a18.d(integer4, integer23, integer5)) < 0.15F) {
							cfj16 = bvs.cD.n();
						} else {
							cfj16 = cfj9;
						}

						a18.d(integer21, integer23, integer22);
					}

					integer19 = integer20;
					if (integer23 >= integer13 - 1) {
						cgy.a(a18, cfj16, false);
					} else if (integer23 < integer13 - 7 - integer20) {
						cfj16 = bvs.a.n();
						cfj17 = cfj8;
						cgy.a(a18, cfj12, false);
					} else {
						cgy.a(a18, cfj17, false);
					}
				} else if (integer19 > 0) {
					integer19--;
					cgy.a(a18, cfj17, false);
					if (integer19 == 0 && cfj17.a(bvs.C) && integer20 > 1) {
						integer19 = random.nextInt(4) + Math.max(0, integer23 - 63);
						cfj17 = cfj17.a(bvs.D) ? bvs.hG.n() : bvs.at.n();
					}
				}
			}
		}
	}
}
