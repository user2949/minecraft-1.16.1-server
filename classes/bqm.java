import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bqm {
	private final zd a;
	private final Random b;

	public bqm(zd zd) {
		this.a = zd;
		this.b = new Random(zd.B());
	}

	public boolean a(aom aom, float float2) {
		dem dem4 = aom.bN();
		fz fz5 = aom.bO();
		cfo.c c6 = this.a(aom.cA(), aom.cB(), fz5, dem4.b, dem4.c, aom instanceof bec);
		if (c6 == null) {
			return false;
		} else {
			dem dem7 = c6.a;
			dem dem8 = c6.b;
			aom.e(dem8);
			aom.p = float2 + (float)c6.c;
			aom.b(dem7.b, dem7.c, dem7.d);
			return true;
		}
	}

	@Nullable
	public cfo.c a(fu fu, dem dem, fz fz, double double4, double double5, boolean boolean6) {
		axz axz10 = this.a.x();
		axz10.a(this.a, fu, 128);
		List<aya> list11 = (List<aya>)axz10.b(ayc -> ayc == ayc.v, fu, 128, axz.b.ANY).collect(Collectors.toList());
		Optional<aya> optional12 = list11.stream().min(Comparator.comparingDouble(aya -> aya.f().j(fu)).thenComparingInt(aya -> aya.f().v()));
		return (cfo.c)optional12.map(aya -> {
			fu fu9 = aya.f();
			this.a.i().a(zi.f, new bph(fu9), 3, fu9);
			cfo.b b10 = bzm.c(this.a, fu9);
			return b10.a(fz, fu9, double5, dem, double4);
		}).orElse(null);
	}

	public boolean a(aom aom) {
		int integer3 = 16;
		double double4 = -1.0;
		int integer6 = aec.c(aom.cC());
		int integer7 = aec.c(aom.cD());
		int integer8 = aec.c(aom.cG());
		int integer9 = integer6;
		int integer10 = integer7;
		int integer11 = integer8;
		int integer12 = 0;
		int integer13 = this.b.nextInt(4);
		fu.a a14 = new fu.a();

		for (int integer15 = integer6 - 16; integer15 <= integer6 + 16; integer15++) {
			double double16 = (double)integer15 + 0.5 - aom.cC();

			for (int integer18 = integer8 - 16; integer18 <= integer8 + 16; integer18++) {
				double double19 = (double)integer18 + 0.5 - aom.cG();

				label279:
				for (int integer21 = this.a.ad() - 1; integer21 >= 0; integer21--) {
					if (this.a.w(a14.d(integer15, integer21, integer18))) {
						while (integer21 > 0 && this.a.w(a14.d(integer15, integer21 - 1, integer18))) {
							integer21--;
						}

						for (int integer22 = integer13; integer22 < integer13 + 4; integer22++) {
							int integer23 = integer22 % 2;
							int integer24 = 1 - integer23;
							if (integer22 % 4 >= 2) {
								integer23 = -integer23;
								integer24 = -integer24;
							}

							for (int integer25 = 0; integer25 < 3; integer25++) {
								for (int integer26 = 0; integer26 < 4; integer26++) {
									for (int integer27 = -1; integer27 < 4; integer27++) {
										int integer28 = integer15 + (integer26 - 1) * integer23 + integer25 * integer24;
										int integer29 = integer21 + integer27;
										int integer30 = integer18 + (integer26 - 1) * integer24 - integer25 * integer23;
										a14.d(integer28, integer29, integer30);
										if (integer27 < 0 && !this.a.d_(a14).c().b() || integer27 >= 0 && !this.a.w(a14)) {
											continue label279;
										}
									}
								}
							}

							double double25 = (double)integer21 + 0.5 - aom.cD();
							double double27 = double16 * double16 + double25 * double25 + double19 * double19;
							if (double4 < 0.0 || double27 < double4) {
								double4 = double27;
								integer9 = integer15;
								integer10 = integer21;
								integer11 = integer18;
								integer12 = integer22 % 4;
							}
						}
					}
				}
			}
		}

		if (double4 < 0.0) {
			for (int integer15 = integer6 - 16; integer15 <= integer6 + 16; integer15++) {
				double double16 = (double)integer15 + 0.5 - aom.cC();

				for (int integer18 = integer8 - 16; integer18 <= integer8 + 16; integer18++) {
					double double19 = (double)integer18 + 0.5 - aom.cG();

					label216:
					for (int integer21x = this.a.ad() - 1; integer21x >= 0; integer21x--) {
						if (this.a.w(a14.d(integer15, integer21x, integer18))) {
							while (integer21x > 0 && this.a.w(a14.d(integer15, integer21x - 1, integer18))) {
								integer21x--;
							}

							for (int integer22 = integer13; integer22 < integer13 + 2; integer22++) {
								int integer23x = integer22 % 2;
								int integer24x = 1 - integer23x;

								for (int integer25 = 0; integer25 < 4; integer25++) {
									for (int integer26 = -1; integer26 < 4; integer26++) {
										int integer27x = integer15 + (integer25 - 1) * integer23x;
										int integer28 = integer21x + integer26;
										int integer29 = integer18 + (integer25 - 1) * integer24x;
										a14.d(integer27x, integer28, integer29);
										if (integer26 < 0 && !this.a.d_(a14).c().b() || integer26 >= 0 && !this.a.w(a14)) {
											continue label216;
										}
									}
								}

								double double25 = (double)integer21x + 0.5 - aom.cD();
								double double27 = double16 * double16 + double25 * double25 + double19 * double19;
								if (double4 < 0.0 || double27 < double4) {
									double4 = double27;
									integer9 = integer15;
									integer10 = integer21x;
									integer11 = integer18;
									integer12 = integer22 % 2;
								}
							}
						}
					}
				}
			}
		}

		int integer16 = integer9;
		int integer17 = integer10;
		int integer18 = integer11;
		int integer19 = integer12 % 2;
		int integer20 = 1 - integer19;
		if (integer12 % 4 >= 2) {
			integer19 = -integer19;
			integer20 = -integer20;
		}

		if (double4 < 0.0) {
			integer10 = aec.a(integer10, 70, this.a.ad() - 10);
			integer17 = integer10;

			for (int integer21xx = -1; integer21xx <= 1; integer21xx++) {
				for (int integer22 = 1; integer22 < 3; integer22++) {
					for (int integer23x = -1; integer23x < 3; integer23x++) {
						int integer24x = integer16 + (integer22 - 1) * integer19 + integer21xx * integer20;
						int integer25 = integer17 + integer23x;
						int integer26x = integer18 + (integer22 - 1) * integer20 - integer21xx * integer19;
						boolean boolean27 = integer23x < 0;
						a14.d(integer24x, integer25, integer26x);
						this.a.a(a14, boolean27 ? bvs.bK.n() : bvs.a.n());
					}
				}
			}
		}

		for (int integer21xx = -1; integer21xx < 3; integer21xx++) {
			for (int integer22 = -1; integer22 < 4; integer22++) {
				if (integer21xx == -1 || integer21xx == 2 || integer22 == -1 || integer22 == 3) {
					a14.d(integer16 + integer21xx * integer19, integer17 + integer22, integer18 + integer21xx * integer20);
					this.a.a(a14, bvs.bK.n(), 3);
				}
			}
		}

		cfj cfj21 = bvs.cT.n().a(bzm.a, integer19 == 0 ? fz.a.Z : fz.a.X);

		for (int integer22x = 0; integer22x < 2; integer22x++) {
			for (int integer23x = 0; integer23x < 3; integer23x++) {
				a14.d(integer16 + integer22x * integer19, integer17 + integer23x, integer18 + integer22x * integer20);
				this.a.a(a14, cfj21, 18);
			}
		}

		return true;
	}
}
