import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class cvh extends cvw<cvx> {
	private static final cfj ah = bvs.fF.n();
	private static final cfj ai = bvs.fG.n();
	private static final cfj aj = bvs.gR.n();
	private static final cfj ak = bvs.fJ.n();
	private static final cfj al = bvs.fR.n();
	private static final cfj am = bvs.fT.n();
	private static final cfj an = bvs.fN.n();
	protected cfj[] a;
	protected long b;
	protected cwf c;
	protected cwf d;
	protected cwf e;

	public cvh(Codec<cvx> codec) {
		super(codec);
	}

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
						if (integer26 >= integer10 - 1) {
							if (integer26 <= integer10 + 3 + integer20) {
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
						} else {
							cgy.a(a25, cfj19, false);
							bvr bvr28 = cfj19.b();
							if (bvr28 == bvs.fF
								|| bvr28 == bvs.fG
								|| bvr28 == bvs.fH
								|| bvr28 == bvs.fI
								|| bvr28 == bvs.fJ
								|| bvr28 == bvs.fK
								|| bvr28 == bvs.fL
								|| bvr28 == bvs.fM
								|| bvr28 == bvs.fN
								|| bvr28 == bvs.fO
								|| bvr28 == bvs.fP
								|| bvr28 == bvs.fQ
								|| bvr28 == bvs.fR
								|| bvr28 == bvs.fS
								|| bvr28 == bvs.fT
								|| bvr28 == bvs.fU) {
								cgy.a(a25, ai, false);
							}
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

	@Override
	public void a(long long1) {
		if (this.b != long1 || this.a == null) {
			this.b(long1);
		}

		if (this.b != long1 || this.c == null || this.d == null) {
			ciy ciy4 = new ciy(long1);
			this.c = new cwf(ciy4, IntStream.rangeClosed(-3, 0));
			this.d = new cwf(ciy4, ImmutableList.of(0));
		}

		this.b = long1;
	}

	protected void b(long long1) {
		this.a = new cfj[64];
		Arrays.fill(this.a, aj);
		ciy ciy4 = new ciy(long1);
		this.e = new cwf(ciy4, ImmutableList.of(0));

		for (int integer5 = 0; integer5 < 64; integer5++) {
			integer5 += ciy4.nextInt(5) + 1;
			if (integer5 < 64) {
				this.a[integer5] = ai;
			}
		}

		int integer5x = ciy4.nextInt(4) + 2;

		for (int integer6 = 0; integer6 < integer5x; integer6++) {
			int integer7 = ciy4.nextInt(3) + 1;
			int integer8 = ciy4.nextInt(64);

			for (int integer9 = 0; integer8 + integer9 < 64 && integer9 < integer7; integer9++) {
				this.a[integer8 + integer9] = ak;
			}
		}

		int integer6 = ciy4.nextInt(4) + 2;

		for (int integer7 = 0; integer7 < integer6; integer7++) {
			int integer8 = ciy4.nextInt(3) + 2;
			int integer9 = ciy4.nextInt(64);

			for (int integer10 = 0; integer9 + integer10 < 64 && integer10 < integer8; integer10++) {
				this.a[integer9 + integer10] = al;
			}
		}

		int integer7 = ciy4.nextInt(4) + 2;

		for (int integer8 = 0; integer8 < integer7; integer8++) {
			int integer9 = ciy4.nextInt(3) + 1;
			int integer10 = ciy4.nextInt(64);

			for (int integer11 = 0; integer10 + integer11 < 64 && integer11 < integer9; integer11++) {
				this.a[integer10 + integer11] = am;
			}
		}

		int integer8 = ciy4.nextInt(3) + 3;
		int integer9 = 0;

		for (int integer10 = 0; integer10 < integer8; integer10++) {
			int integer11 = 1;
			integer9 += ciy4.nextInt(16) + 4;

			for (int integer12 = 0; integer9 + integer12 < 64 && integer12 < 1; integer12++) {
				this.a[integer9 + integer12] = ah;
				if (integer9 + integer12 > 1 && ciy4.nextBoolean()) {
					this.a[integer9 + integer12 - 1] = an;
				}

				if (integer9 + integer12 < 63 && ciy4.nextBoolean()) {
					this.a[integer9 + integer12 + 1] = an;
				}
			}
		}
	}

	protected cfj a(int integer1, int integer2, int integer3) {
		int integer5 = (int)Math.round(this.e.a((double)integer1 / 512.0, (double)integer3 / 512.0, false) * 2.0);
		return this.a[(integer2 + integer5 + 64) % 64];
	}
}
