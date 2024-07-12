import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;

public class clp extends ckt<coc> {
	public clp(Codec<coc> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coc coc) {
		float float8 = random.nextFloat() * (float) Math.PI;
		float float9 = (float)coc.c / 8.0F;
		int integer10 = aec.f(((float)coc.c / 16.0F * 2.0F + 1.0F) / 2.0F);
		double double11 = (double)((float)fu.u() + aec.a(float8) * float9);
		double double13 = (double)((float)fu.u() - aec.a(float8) * float9);
		double double15 = (double)((float)fu.w() + aec.b(float8) * float9);
		double double17 = (double)((float)fu.w() - aec.b(float8) * float9);
		int integer19 = 2;
		double double20 = (double)(fu.v() + random.nextInt(3) - 2);
		double double22 = (double)(fu.v() + random.nextInt(3) - 2);
		int integer24 = fu.u() - aec.f(float9) - integer10;
		int integer25 = fu.v() - 2 - integer10;
		int integer26 = fu.w() - aec.f(float9) - integer10;
		int integer27 = 2 * (aec.f(float9) + integer10);
		int integer28 = 2 * (2 + integer10);

		for (int integer29 = integer24; integer29 <= integer24 + integer27; integer29++) {
			for (int integer30 = integer26; integer30 <= integer26 + integer27; integer30++) {
				if (integer25 <= bqu.a(cio.a.OCEAN_FLOOR_WG, integer29, integer30)) {
					return this.a(bqu, random, coc, double11, double13, double15, double17, double20, double22, integer24, integer25, integer26, integer27, integer28);
				}
			}
		}

		return false;
	}

	protected boolean a(
		bqc bqc,
		Random random,
		coc coc,
		double double4,
		double double5,
		double double6,
		double double7,
		double double8,
		double double9,
		int integer10,
		int integer11,
		int integer12,
		int integer13,
		int integer14
	) {
		int integer22 = 0;
		BitSet bitSet23 = new BitSet(integer13 * integer14 * integer13);
		fu.a a24 = new fu.a();
		double[] arr25 = new double[coc.c * 4];

		for (int integer26 = 0; integer26 < coc.c; integer26++) {
			float float27 = (float)integer26 / (float)coc.c;
			double double28 = aec.d((double)float27, double4, double5);
			double double30 = aec.d((double)float27, double8, double9);
			double double32 = aec.d((double)float27, double6, double7);
			double double34 = random.nextDouble() * (double)coc.c / 16.0;
			double double36 = ((double)(aec.a((float) Math.PI * float27) + 1.0F) * double34 + 1.0) / 2.0;
			arr25[integer26 * 4 + 0] = double28;
			arr25[integer26 * 4 + 1] = double30;
			arr25[integer26 * 4 + 2] = double32;
			arr25[integer26 * 4 + 3] = double36;
		}

		for (int integer26 = 0; integer26 < coc.c - 1; integer26++) {
			if (!(arr25[integer26 * 4 + 3] <= 0.0)) {
				for (int integer27 = integer26 + 1; integer27 < coc.c; integer27++) {
					if (!(arr25[integer27 * 4 + 3] <= 0.0)) {
						double double28 = arr25[integer26 * 4 + 0] - arr25[integer27 * 4 + 0];
						double double30 = arr25[integer26 * 4 + 1] - arr25[integer27 * 4 + 1];
						double double32 = arr25[integer26 * 4 + 2] - arr25[integer27 * 4 + 2];
						double double34 = arr25[integer26 * 4 + 3] - arr25[integer27 * 4 + 3];
						if (double34 * double34 > double28 * double28 + double30 * double30 + double32 * double32) {
							if (double34 > 0.0) {
								arr25[integer27 * 4 + 3] = -1.0;
							} else {
								arr25[integer26 * 4 + 3] = -1.0;
							}
						}
					}
				}
			}
		}

		for (int integer26x = 0; integer26x < coc.c; integer26x++) {
			double double27 = arr25[integer26x * 4 + 3];
			if (!(double27 < 0.0)) {
				double double29 = arr25[integer26x * 4 + 0];
				double double31 = arr25[integer26x * 4 + 1];
				double double33 = arr25[integer26x * 4 + 2];
				int integer35 = Math.max(aec.c(double29 - double27), integer10);
				int integer36 = Math.max(aec.c(double31 - double27), integer11);
				int integer37 = Math.max(aec.c(double33 - double27), integer12);
				int integer38 = Math.max(aec.c(double29 + double27), integer35);
				int integer39 = Math.max(aec.c(double31 + double27), integer36);
				int integer40 = Math.max(aec.c(double33 + double27), integer37);

				for (int integer41 = integer35; integer41 <= integer38; integer41++) {
					double double42 = ((double)integer41 + 0.5 - double29) / double27;
					if (double42 * double42 < 1.0) {
						for (int integer44 = integer36; integer44 <= integer39; integer44++) {
							double double45 = ((double)integer44 + 0.5 - double31) / double27;
							if (double42 * double42 + double45 * double45 < 1.0) {
								for (int integer47 = integer37; integer47 <= integer40; integer47++) {
									double double48 = ((double)integer47 + 0.5 - double33) / double27;
									if (double42 * double42 + double45 * double45 + double48 * double48 < 1.0) {
										int integer50 = integer41 - integer10 + (integer44 - integer11) * integer13 + (integer47 - integer12) * integer13 * integer14;
										if (!bitSet23.get(integer50)) {
											bitSet23.set(integer50);
											a24.d(integer41, integer44, integer47);
											if (coc.b.c().test(bqc.d_(a24))) {
												bqc.a(a24, coc.d, 2);
												integer22++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return integer22 > 0;
	}
}
