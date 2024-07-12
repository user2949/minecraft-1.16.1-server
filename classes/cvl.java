import com.mojang.serialization.Codec;
import java.util.Random;

public class cvl extends cvh {
	private static final cfj ah = bvs.fF.n();
	private static final cfj ai = bvs.fG.n();
	private static final cfj aj = bvs.gR.n();

	public cvl(Codec<cvx> codec) {
		super(codec);
	}

	@Override
	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		double double16 = 0.0;
		double double18 = Math.min(Math.abs(double7), this.c.a((double)integer4 * 0.25, (double)integer5 * 0.25, false) * 15.0);
		if (double18 > 0.0) {
			double double20 = 0.001953125;
			double double22 = Math.abs(this.d.a((double)integer4 * 0.001953125, (double)integer5 * 0.001953125, false));
			double16 = double18 * double18 * 2.5;
			double double24 = Math.ceil(double22 * 50.0) + 14.0;
			if (double16 > double24) {
				double16 = double24;
			}

			double16 += 64.0;
		}

		int integer20 = integer4 & 15;
		int integer21 = integer5 & 15;
		cfj cfj22 = ah;
		cfj cfj23 = bre.A().b();
		int integer24 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		boolean boolean25 = Math.cos(double7 / 3.0 * Math.PI) > 0.0;
		int integer26 = -1;
		boolean boolean27 = false;
		fu.a a28 = new fu.a();

		for (int integer29 = Math.max(integer6, (int)double16 + 1); integer29 >= 0; integer29--) {
			a28.d(integer20, integer29, integer21);
			if (cgy.d_(a28).g() && integer29 < (int)double16) {
				cgy.a(a28, cfj8, false);
			}

			cfj cfj30 = cgy.d_(a28);
			if (cfj30.g()) {
				integer26 = -1;
			} else if (cfj30.a(cfj8.b())) {
				if (integer26 == -1) {
					boolean27 = false;
					if (integer24 <= 0) {
						cfj22 = bvs.a.n();
						cfj23 = cfj8;
					} else if (integer29 >= integer10 - 4 && integer29 <= integer10 + 1) {
						cfj22 = ah;
						cfj23 = bre.A().b();
					}

					if (integer29 < integer10 && (cfj22 == null || cfj22.g())) {
						cfj22 = cfj9;
					}

					integer26 = integer24 + Math.max(0, integer29 - integer10);
					if (integer29 >= integer10 - 1) {
						if (integer29 > integer10 + 3 + integer24) {
							cfj cfj31;
							if (integer29 < 64 || integer29 > 127) {
								cfj31 = ai;
							} else if (boolean25) {
								cfj31 = aj;
							} else {
								cfj31 = this.a(integer4, integer29, integer5);
							}

							cgy.a(a28, cfj31, false);
						} else {
							cgy.a(a28, bre.A().a(), false);
							boolean27 = true;
						}
					} else {
						cgy.a(a28, cfj23, false);
						bvr bvr31 = cfj23.b();
						if (bvr31 == bvs.fF
							|| bvr31 == bvs.fG
							|| bvr31 == bvs.fH
							|| bvr31 == bvs.fI
							|| bvr31 == bvs.fJ
							|| bvr31 == bvs.fK
							|| bvr31 == bvs.fL
							|| bvr31 == bvs.fM
							|| bvr31 == bvs.fN
							|| bvr31 == bvs.fO
							|| bvr31 == bvs.fP
							|| bvr31 == bvs.fQ
							|| bvr31 == bvs.fR
							|| bvr31 == bvs.fS
							|| bvr31 == bvs.fT
							|| bvr31 == bvs.fU) {
							cgy.a(a28, ai, false);
						}
					}
				} else if (integer26 > 0) {
					integer26--;
					if (boolean27) {
						cgy.a(a28, ai, false);
					} else {
						cgy.a(a28, this.a(integer4, integer29, integer5), false);
					}
				}
			}
		}
	}
}
