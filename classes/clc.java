import com.mojang.serialization.Codec;
import java.util.Random;

public class clc extends ckt<coa> {
	public clc(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		while (bqu.w(fu) && fu.v() > 2) {
			fu = fu.c();
		}

		if (!bqu.d_(fu).a(bvs.cE)) {
			return false;
		} else {
			fu = fu.b(random.nextInt(4));
			int integer8 = random.nextInt(4) + 7;
			int integer9 = integer8 / 4 + random.nextInt(2);
			if (integer9 > 1 && random.nextInt(60) == 0) {
				fu = fu.b(10 + random.nextInt(30));
			}

			for (int integer10 = 0; integer10 < integer8; integer10++) {
				float float11 = (1.0F - (float)integer10 / (float)integer8) * (float)integer9;
				int integer12 = aec.f(float11);

				for (int integer13 = -integer12; integer13 <= integer12; integer13++) {
					float float14 = (float)aec.a(integer13) - 0.25F;

					for (int integer15 = -integer12; integer15 <= integer12; integer15++) {
						float float16 = (float)aec.a(integer15) - 0.25F;
						if ((integer13 == 0 && integer15 == 0 || !(float14 * float14 + float16 * float16 > float11 * float11))
							&& (integer13 != -integer12 && integer13 != integer12 && integer15 != -integer12 && integer15 != integer12 || !(random.nextFloat() > 0.75F))) {
							cfj cfj17 = bqu.d_(fu.b(integer13, integer10, integer15));
							bvr bvr18 = cfj17.b();
							if (cfj17.g() || b(bvr18) || bvr18 == bvs.cE || bvr18 == bvs.cD) {
								this.a(bqu, fu.b(integer13, integer10, integer15), bvs.gT.n());
							}

							if (integer10 != 0 && integer12 > 1) {
								cfj17 = bqu.d_(fu.b(integer13, -integer10, integer15));
								bvr18 = cfj17.b();
								if (cfj17.g() || b(bvr18) || bvr18 == bvs.cE || bvr18 == bvs.cD) {
									this.a(bqu, fu.b(integer13, -integer10, integer15), bvs.gT.n());
								}
							}
						}
					}
				}
			}

			int integer10 = integer9 - 1;
			if (integer10 < 0) {
				integer10 = 0;
			} else if (integer10 > 1) {
				integer10 = 1;
			}

			for (int integer11 = -integer10; integer11 <= integer10; integer11++) {
				for (int integer12 = -integer10; integer12 <= integer10; integer12++) {
					fu fu13 = fu.b(integer11, -1, integer12);
					int integer14 = 50;
					if (Math.abs(integer11) == 1 && Math.abs(integer12) == 1) {
						integer14 = random.nextInt(5);
					}

					while (fu13.v() > 50) {
						cfj cfj15 = bqu.d_(fu13);
						bvr bvr16 = cfj15.b();
						if (!cfj15.g() && !b(bvr16) && bvr16 != bvs.cE && bvr16 != bvs.cD && bvr16 != bvs.gT) {
							break;
						}

						this.a(bqu, fu13, bvs.gT.n());
						fu13 = fu13.c();
						if (--integer14 <= 0) {
							fu13 = fu13.c(random.nextInt(5) + 1);
							integer14 = random.nextInt(5);
						}
					}
				}
			}

			return true;
		}
	}
}
