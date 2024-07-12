import com.mojang.serialization.Codec;
import java.util.Random;

public class clh extends ckt<cng> {
	private static final cfj a = bvs.lb.n();

	public clh(Codec<cng> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cng cng) {
		while (fu.v() > 5 && bqu.w(fu)) {
			fu = fu.c();
		}

		if (fu.v() <= 4) {
			return false;
		} else {
			fu = fu.c(4);
			if (bqq.a(go.a(fu), cml.q).findAny().isPresent()) {
				return false;
			} else {
				boolean[] arr8 = new boolean[2048];
				int integer9 = random.nextInt(4) + 4;

				for (int integer10 = 0; integer10 < integer9; integer10++) {
					double double11 = random.nextDouble() * 6.0 + 3.0;
					double double13 = random.nextDouble() * 4.0 + 2.0;
					double double15 = random.nextDouble() * 6.0 + 3.0;
					double double17 = random.nextDouble() * (16.0 - double11 - 2.0) + 1.0 + double11 / 2.0;
					double double19 = random.nextDouble() * (8.0 - double13 - 4.0) + 2.0 + double13 / 2.0;
					double double21 = random.nextDouble() * (16.0 - double15 - 2.0) + 1.0 + double15 / 2.0;

					for (int integer23 = 1; integer23 < 15; integer23++) {
						for (int integer24 = 1; integer24 < 15; integer24++) {
							for (int integer25 = 1; integer25 < 7; integer25++) {
								double double26 = ((double)integer23 - double17) / (double11 / 2.0);
								double double28 = ((double)integer25 - double19) / (double13 / 2.0);
								double double30 = ((double)integer24 - double21) / (double15 / 2.0);
								double double32 = double26 * double26 + double28 * double28 + double30 * double30;
								if (double32 < 1.0) {
									arr8[(integer23 * 16 + integer24) * 8 + integer25] = true;
								}
							}
						}
					}
				}

				for (int integer10 = 0; integer10 < 16; integer10++) {
					for (int integer11 = 0; integer11 < 16; integer11++) {
						for (int integer12 = 0; integer12 < 8; integer12++) {
							boolean boolean13 = !arr8[(integer10 * 16 + integer11) * 8 + integer12]
								&& (
									integer10 < 15 && arr8[((integer10 + 1) * 16 + integer11) * 8 + integer12]
										|| integer10 > 0 && arr8[((integer10 - 1) * 16 + integer11) * 8 + integer12]
										|| integer11 < 15 && arr8[(integer10 * 16 + integer11 + 1) * 8 + integer12]
										|| integer11 > 0 && arr8[(integer10 * 16 + (integer11 - 1)) * 8 + integer12]
										|| integer12 < 7 && arr8[(integer10 * 16 + integer11) * 8 + integer12 + 1]
										|| integer12 > 0 && arr8[(integer10 * 16 + integer11) * 8 + (integer12 - 1)]
								);
							if (boolean13) {
								cxd cxd14 = bqu.d_(fu.b(integer10, integer12, integer11)).c();
								if (integer12 >= 4 && cxd14.a()) {
									return false;
								}

								if (integer12 < 4 && !cxd14.b() && bqu.d_(fu.b(integer10, integer12, integer11)) != cng.b) {
									return false;
								}
							}
						}
					}
				}

				for (int integer10 = 0; integer10 < 16; integer10++) {
					for (int integer11 = 0; integer11 < 16; integer11++) {
						for (int integer12x = 0; integer12x < 8; integer12x++) {
							if (arr8[(integer10 * 16 + integer11) * 8 + integer12x]) {
								bqu.a(fu.b(integer10, integer12x, integer11), integer12x >= 4 ? a : cng.b, 2);
							}
						}
					}
				}

				for (int integer10 = 0; integer10 < 16; integer10++) {
					for (int integer11 = 0; integer11 < 16; integer11++) {
						for (int integer12xx = 4; integer12xx < 8; integer12xx++) {
							if (arr8[(integer10 * 16 + integer11) * 8 + integer12xx]) {
								fu fu13 = fu.b(integer10, integer12xx - 1, integer11);
								if (b(bqu.d_(fu13).b()) && bqu.a(bqi.SKY, fu.b(integer10, integer12xx, integer11)) > 0) {
									bre bre14 = bqu.v(fu13);
									if (bre14.A().a().a(bvs.dT)) {
										bqu.a(fu13, bvs.dT.n(), 2);
									} else {
										bqu.a(fu13, bvs.i.n(), 2);
									}
								}
							}
						}
					}
				}

				if (cng.b.c() == cxd.k) {
					for (int integer10 = 0; integer10 < 16; integer10++) {
						for (int integer11 = 0; integer11 < 16; integer11++) {
							for (int integer12xxx = 0; integer12xxx < 8; integer12xxx++) {
								boolean boolean13 = !arr8[(integer10 * 16 + integer11) * 8 + integer12xxx]
									&& (
										integer10 < 15 && arr8[((integer10 + 1) * 16 + integer11) * 8 + integer12xxx]
											|| integer10 > 0 && arr8[((integer10 - 1) * 16 + integer11) * 8 + integer12xxx]
											|| integer11 < 15 && arr8[(integer10 * 16 + integer11 + 1) * 8 + integer12xxx]
											|| integer11 > 0 && arr8[(integer10 * 16 + (integer11 - 1)) * 8 + integer12xxx]
											|| integer12xxx < 7 && arr8[(integer10 * 16 + integer11) * 8 + integer12xxx + 1]
											|| integer12xxx > 0 && arr8[(integer10 * 16 + integer11) * 8 + (integer12xxx - 1)]
									);
								if (boolean13 && (integer12xxx < 4 || random.nextInt(2) != 0) && bqu.d_(fu.b(integer10, integer12xxx, integer11)).c().b()) {
									bqu.a(fu.b(integer10, integer12xxx, integer11), bvs.b.n(), 2);
								}
							}
						}
					}
				}

				if (cng.b.c() == cxd.i) {
					for (int integer10 = 0; integer10 < 16; integer10++) {
						for (int integer11 = 0; integer11 < 16; integer11++) {
							int integer12xxxx = 4;
							fu fu13 = fu.b(integer10, 4, integer11);
							if (bqu.v(fu13).a(bqu, fu13, false)) {
								bqu.a(fu13, bvs.cD.n(), 2);
							}
						}
					}
				}

				return true;
			}
		}
	}
}
