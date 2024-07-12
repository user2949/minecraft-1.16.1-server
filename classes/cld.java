import com.mojang.serialization.Codec;
import java.util.Random;

public class cld extends ckt<cng> {
	public cld(Codec<cng> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cng cng) {
		fu = new fu(fu.u(), cha.f(), fu.w());
		boolean boolean8 = random.nextDouble() > 0.7;
		cfj cfj9 = cng.b;
		double double10 = random.nextDouble() * 2.0 * Math.PI;
		int integer12 = 11 - random.nextInt(5);
		int integer13 = 3 + random.nextInt(3);
		boolean boolean14 = random.nextDouble() > 0.7;
		int integer15 = 11;
		int integer16 = boolean14 ? random.nextInt(6) + 6 : random.nextInt(15) + 3;
		if (!boolean14 && random.nextDouble() > 0.9) {
			integer16 += random.nextInt(19) + 7;
		}

		int integer17 = Math.min(integer16 + random.nextInt(11), 18);
		int integer18 = Math.min(integer16 + random.nextInt(7) - random.nextInt(5), 11);
		int integer19 = boolean14 ? integer12 : 11;

		for (int integer20 = -integer19; integer20 < integer19; integer20++) {
			for (int integer21 = -integer19; integer21 < integer19; integer21++) {
				for (int integer22 = 0; integer22 < integer16; integer22++) {
					int integer23 = boolean14 ? this.b(integer22, integer16, integer18) : this.a(random, integer22, integer16, integer18);
					if (boolean14 || integer20 < integer23) {
						this.a(bqu, random, fu, integer16, integer20, integer22, integer21, integer23, integer19, boolean14, integer13, double10, boolean8, cfj9);
					}
				}
			}
		}

		this.a(bqu, fu, integer18, integer16, boolean14, integer12);

		for (int integer20 = -integer19; integer20 < integer19; integer20++) {
			for (int integer21 = -integer19; integer21 < integer19; integer21++) {
				for (int integer22x = -1; integer22x > -integer17; integer22x--) {
					int integer23 = boolean14 ? aec.f((float)integer19 * (1.0F - (float)Math.pow((double)integer22x, 2.0) / ((float)integer17 * 8.0F))) : integer19;
					int integer24 = this.b(random, -integer22x, integer17, integer18);
					if (integer20 < integer24) {
						this.a(bqu, random, fu, integer17, integer20, integer22x, integer21, integer24, integer23, boolean14, integer13, double10, boolean8, cfj9);
					}
				}
			}
		}

		boolean boolean20 = boolean14 ? random.nextDouble() > 0.1 : random.nextDouble() > 0.7;
		if (boolean20) {
			this.a(random, bqu, integer18, integer16, fu, boolean14, integer12, double10, integer13);
		}

		return true;
	}

	private void a(Random random, bqc bqc, int integer3, int integer4, fu fu, boolean boolean6, int integer7, double double8, int integer9) {
		int integer12 = random.nextBoolean() ? -1 : 1;
		int integer13 = random.nextBoolean() ? -1 : 1;
		int integer14 = random.nextInt(Math.max(integer3 / 2 - 2, 1));
		if (random.nextBoolean()) {
			integer14 = integer3 / 2 + 1 - random.nextInt(Math.max(integer3 - integer3 / 2 - 1, 1));
		}

		int integer15 = random.nextInt(Math.max(integer3 / 2 - 2, 1));
		if (random.nextBoolean()) {
			integer15 = integer3 / 2 + 1 - random.nextInt(Math.max(integer3 - integer3 / 2 - 1, 1));
		}

		if (boolean6) {
			integer14 = integer15 = random.nextInt(Math.max(integer7 - 5, 1));
		}

		fu fu16 = new fu(integer12 * integer14, 0, integer13 * integer15);
		double double17 = boolean6 ? double8 + (Math.PI / 2) : random.nextDouble() * 2.0 * Math.PI;

		for (int integer19 = 0; integer19 < integer4 - 3; integer19++) {
			int integer20 = this.a(random, integer19, integer4, integer3);
			this.a(integer20, integer19, fu, bqc, false, double17, fu16, integer7, integer9);
		}

		for (int integer19 = -1; integer19 > -integer4 + random.nextInt(5); integer19--) {
			int integer20 = this.b(random, -integer19, integer4, integer3);
			this.a(integer20, integer19, fu, bqc, true, double17, fu16, integer7, integer9);
		}
	}

	private void a(int integer1, int integer2, fu fu3, bqc bqc, boolean boolean5, double double6, fu fu7, int integer8, int integer9) {
		int integer12 = integer1 + 1 + integer8 / 3;
		int integer13 = Math.min(integer1 - 3, 3) + integer9 / 2 - 1;

		for (int integer14 = -integer12; integer14 < integer12; integer14++) {
			for (int integer15 = -integer12; integer15 < integer12; integer15++) {
				double double16 = this.a(integer14, integer15, fu7, integer12, integer13, double6);
				if (double16 < 0.0) {
					fu fu18 = fu3.b(integer14, integer2, integer15);
					bvr bvr19 = bqc.d_(fu18).b();
					if (this.c(bvr19) || bvr19 == bvs.cE) {
						if (boolean5) {
							this.a(bqc, fu18, bvs.A.n());
						} else {
							this.a(bqc, fu18, bvs.a.n());
							this.a(bqc, fu18);
						}
					}
				}
			}
		}
	}

	private void a(bqc bqc, fu fu) {
		if (bqc.d_(fu.b()).a(bvs.cC)) {
			this.a(bqc, fu.b(), bvs.a.n());
		}
	}

	private void a(
		bqc bqc,
		Random random,
		fu fu,
		int integer4,
		int integer5,
		int integer6,
		int integer7,
		int integer8,
		int integer9,
		boolean boolean10,
		int integer11,
		double double12,
		boolean boolean13,
		cfj cfj
	) {
		double double17 = boolean10
			? this.a(integer5, integer7, fu.b, integer9, this.a(integer6, integer4, integer11), double12)
			: this.a(integer5, integer7, fu.b, integer8, random);
		if (double17 < 0.0) {
			fu fu19 = fu.b(integer5, integer6, integer7);
			double double20 = boolean10 ? -0.5 : (double)(-6 - random.nextInt(3));
			if (double17 > double20 && random.nextDouble() > 0.9) {
				return;
			}

			this.a(fu19, bqc, random, integer4 - integer6, integer4, boolean10, boolean13, cfj);
		}
	}

	private void a(fu fu, bqc bqc, Random random, int integer4, int integer5, boolean boolean6, boolean boolean7, cfj cfj) {
		cfj cfj10 = bqc.d_(fu);
		if (cfj10.c() == cxd.a || cfj10.a(bvs.cE) || cfj10.a(bvs.cD) || cfj10.a(bvs.A)) {
			boolean boolean11 = !boolean6 || random.nextDouble() > 0.05;
			int integer12 = boolean6 ? 3 : 2;
			if (boolean7 && !cfj10.a(bvs.A) && (double)integer4 <= (double)random.nextInt(Math.max(1, integer5 / integer12)) + (double)integer5 * 0.6 && boolean11) {
				this.a(bqc, fu, bvs.cE.n());
			} else {
				this.a(bqc, fu, cfj);
			}
		}
	}

	private int a(int integer1, int integer2, int integer3) {
		int integer5 = integer3;
		if (integer1 > 0 && integer2 - integer1 <= 3) {
			integer5 = integer3 - (4 - (integer2 - integer1));
		}

		return integer5;
	}

	private double a(int integer1, int integer2, fu fu, int integer4, Random random) {
		float float7 = 10.0F * aec.a(random.nextFloat(), 0.2F, 0.8F) / (float)integer4;
		return (double)float7 + Math.pow((double)(integer1 - fu.u()), 2.0) + Math.pow((double)(integer2 - fu.w()), 2.0) - Math.pow((double)integer4, 2.0);
	}

	private double a(int integer1, int integer2, fu fu, int integer4, int integer5, double double6) {
		return Math.pow(((double)(integer1 - fu.u()) * Math.cos(double6) - (double)(integer2 - fu.w()) * Math.sin(double6)) / (double)integer4, 2.0)
			+ Math.pow(((double)(integer1 - fu.u()) * Math.sin(double6) + (double)(integer2 - fu.w()) * Math.cos(double6)) / (double)integer5, 2.0)
			- 1.0;
	}

	private int a(Random random, int integer2, int integer3, int integer4) {
		float float6 = 3.5F - random.nextFloat();
		float float7 = (1.0F - (float)Math.pow((double)integer2, 2.0) / ((float)integer3 * float6)) * (float)integer4;
		if (integer3 > 15 + random.nextInt(5)) {
			int integer8 = integer2 < 3 + random.nextInt(6) ? integer2 / 2 : integer2;
			float7 = (1.0F - (float)integer8 / ((float)integer3 * float6 * 0.4F)) * (float)integer4;
		}

		return aec.f(float7 / 2.0F);
	}

	private int b(int integer1, int integer2, int integer3) {
		float float5 = 1.0F;
		float float6 = (1.0F - (float)Math.pow((double)integer1, 2.0) / ((float)integer2 * 1.0F)) * (float)integer3;
		return aec.f(float6 / 2.0F);
	}

	private int b(Random random, int integer2, int integer3, int integer4) {
		float float6 = 1.0F + random.nextFloat() / 2.0F;
		float float7 = (1.0F - (float)integer2 / ((float)integer3 * float6)) * (float)integer4;
		return aec.f(float7 / 2.0F);
	}

	private boolean c(bvr bvr) {
		return bvr == bvs.gT || bvr == bvs.cE || bvr == bvs.kV;
	}

	private boolean a(bpg bpg, fu fu) {
		return bpg.d_(fu.c()).c() == cxd.a;
	}

	private void a(bqc bqc, fu fu, int integer3, int integer4, boolean boolean5, int integer6) {
		int integer8 = boolean5 ? integer6 : integer3 / 2;

		for (int integer9 = -integer8; integer9 <= integer8; integer9++) {
			for (int integer10 = -integer8; integer10 <= integer8; integer10++) {
				for (int integer11 = 0; integer11 <= integer4; integer11++) {
					fu fu12 = fu.b(integer9, integer11, integer10);
					bvr bvr13 = bqc.d_(fu12).b();
					if (this.c(bvr13) || bvr13 == bvs.cC) {
						if (this.a((bpg)bqc, fu12)) {
							this.a(bqc, fu12, bvs.a.n());
							this.a(bqc, fu12.b(), bvs.a.n());
						} else if (this.c(bvr13)) {
							bvr[] arr14 = new bvr[]{bqc.d_(fu12.f()).b(), bqc.d_(fu12.g()).b(), bqc.d_(fu12.d()).b(), bqc.d_(fu12.e()).b()};
							int integer15 = 0;

							for (bvr bvr19 : arr14) {
								if (!this.c(bvr19)) {
									integer15++;
								}
							}

							if (integer15 >= 3) {
								this.a(bqc, fu12, bvs.a.n());
							}
						}
					}
				}
			}
		}
	}
}
