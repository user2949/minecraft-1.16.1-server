import com.mojang.serialization.Codec;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class clj extends ckt<coa> {
	private static final Logger a = LogManager.getLogger();
	private static final aoq<?>[] ac = new aoq[]{aoq.au, aoq.aX, aoq.aX, aoq.aB};
	private static final cfj ad = bvs.lb.n();

	public clj(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		int integer8 = 3;
		int integer9 = random.nextInt(2) + 2;
		int integer10 = -integer9 - 1;
		int integer11 = integer9 + 1;
		int integer12 = -1;
		int integer13 = 4;
		int integer14 = random.nextInt(2) + 2;
		int integer15 = -integer14 - 1;
		int integer16 = integer14 + 1;
		int integer17 = 0;

		for (int integer18 = integer10; integer18 <= integer11; integer18++) {
			for (int integer19 = -1; integer19 <= 4; integer19++) {
				for (int integer20 = integer15; integer20 <= integer16; integer20++) {
					fu fu21 = fu.b(integer18, integer19, integer20);
					cxd cxd22 = bqu.d_(fu21).c();
					boolean boolean23 = cxd22.b();
					if (integer19 == -1 && !boolean23) {
						return false;
					}

					if (integer19 == 4 && !boolean23) {
						return false;
					}

					if ((integer18 == integer10 || integer18 == integer11 || integer20 == integer15 || integer20 == integer16)
						&& integer19 == 0
						&& bqu.w(fu21)
						&& bqu.w(fu21.b())) {
						integer17++;
					}
				}
			}
		}

		if (integer17 >= 1 && integer17 <= 5) {
			for (int integer18 = integer10; integer18 <= integer11; integer18++) {
				for (int integer19 = 3; integer19 >= -1; integer19--) {
					for (int integer20 = integer15; integer20 <= integer16; integer20++) {
						fu fu21x = fu.b(integer18, integer19, integer20);
						cfj cfj22 = bqu.d_(fu21x);
						if (integer18 != integer10 && integer19 != -1 && integer20 != integer15 && integer18 != integer11 && integer19 != 4 && integer20 != integer16) {
							if (!cfj22.a(bvs.bR) && !cfj22.a(bvs.bP)) {
								bqu.a(fu21x, ad, 2);
							}
						} else if (fu21x.v() >= 0 && !bqu.d_(fu21x.c()).c().b()) {
							bqu.a(fu21x, ad, 2);
						} else if (cfj22.c().b() && !cfj22.a(bvs.bR)) {
							if (integer19 == -1 && random.nextInt(4) != 0) {
								bqu.a(fu21x, bvs.bJ.n(), 2);
							} else {
								bqu.a(fu21x, bvs.m.n(), 2);
							}
						}
					}
				}
			}

			for (int integer18 = 0; integer18 < 2; integer18++) {
				for (int integer19 = 0; integer19 < 3; integer19++) {
					int integer20x = fu.u() + random.nextInt(integer9 * 2 + 1) - integer9;
					int integer21 = fu.v();
					int integer22 = fu.w() + random.nextInt(integer14 * 2 + 1) - integer14;
					fu fu23 = new fu(integer20x, integer21, integer22);
					if (bqu.w(fu23)) {
						int integer24 = 0;

						for (fz fz26 : fz.c.HORIZONTAL) {
							if (bqu.d_(fu23.a(fz26)).c().b()) {
								integer24++;
							}
						}

						if (integer24 == 1) {
							bqu.a(fu23, cty.a(bqu, fu23, bvs.bR.n()), 2);
							cef.a(bqu, random, fu23, dao.d);
							break;
						}
					}
				}
			}

			bqu.a(fu, bvs.bP.n(), 2);
			cdl cdl18 = bqu.c(fu);
			if (cdl18 instanceof cek) {
				((cek)cdl18).d().a(this.a(random));
			} else {
				a.error("Failed to fetch mob spawner entity at ({}, {}, {})", fu.u(), fu.v(), fu.w());
			}

			return true;
		} else {
			return false;
		}
	}

	private aoq<?> a(Random random) {
		return v.a(ac, random);
	}
}
