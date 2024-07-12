import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class cjm extends ckt<cnj> {
	private static final ImmutableList<bvr> a = ImmutableList.of(bvs.B, bvs.z, bvs.iJ, bvs.cM, bvs.dV, bvs.dW, bvs.dX, bvs.dY, bvs.bR, bvs.bP);

	public cjm(Codec<cnj> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnj cnj) {
		int integer8 = cha.f();
		fu fu9 = a(bqu, integer8, fu.i().a(fz.a.Y, 1, bqu.I() - 1), Integer.MAX_VALUE);
		if (fu9 == null) {
			return false;
		} else {
			int integer10 = a(random, cnj);
			boolean boolean11 = random.nextFloat() < 0.9F;
			int integer12 = Math.min(integer10, boolean11 ? 5 : 8);
			int integer13 = boolean11 ? 50 : 15;
			boolean boolean14 = false;

			for (fu fu16 : fu.a(random, integer13, fu9.u() - integer12, fu9.v(), fu9.w() - integer12, fu9.u() + integer12, fu9.v(), fu9.w() + integer12)) {
				int integer17 = integer10 - fu16.k(fu9);
				if (integer17 >= 0) {
					boolean14 |= this.a(bqu, integer8, fu16, integer17, b(random, cnj));
				}
			}

			return boolean14;
		}
	}

	private boolean a(bqc bqc, int integer2, fu fu, int integer4, int integer5) {
		boolean boolean7 = false;

		for (fu fu9 : fu.b(fu.u() - integer5, fu.v(), fu.w() - integer5, fu.u() + integer5, fu.v(), fu.w() + integer5)) {
			int integer10 = fu9.k(fu);
			fu fu11 = a(bqc, integer2, fu9) ? a(bqc, integer2, fu9.i(), integer10) : a(bqc, fu9.i(), integer10);
			if (fu11 != null) {
				int integer12 = integer4 - integer10 / 2;

				for (fu.a a13 = fu11.i(); integer12 >= 0; integer12--) {
					if (a(bqc, integer2, a13)) {
						this.a(bqc, a13, bvs.cO.n());
						a13.c(fz.UP);
						boolean7 = true;
					} else {
						if (!bqc.d_(a13).a(bvs.cO)) {
							break;
						}

						a13.c(fz.UP);
					}
				}
			}
		}

		return boolean7;
	}

	@Nullable
	private static fu a(bqc bqc, int integer2, fu.a a, int integer4) {
		while (a.v() > 1 && integer4 > 0) {
			integer4--;
			if (a(bqc, integer2, a)) {
				cfj cfj5 = bqc.d_(a.c(fz.DOWN));
				a.c(fz.UP);
				if (!cfj5.g() && !cjm.a.contains(cfj5.b())) {
					return a;
				}
			}

			a.c(fz.DOWN);
		}

		return null;
	}

	@Nullable
	private static fu a(bqc bqc, fu.a a, int integer) {
		while (a.v() < bqc.I() && integer > 0) {
			integer--;
			cfj cfj4 = bqc.d_(a);
			if (cjm.a.contains(cfj4.b())) {
				return null;
			}

			if (cfj4.g()) {
				return a;
			}

			a.c(fz.UP);
		}

		return null;
	}

	private static int a(Random random, cnj cnj) {
		return cnj.d + random.nextInt(cnj.e - cnj.d + 1);
	}

	private static int b(Random random, cnj cnj) {
		return cnj.b + random.nextInt(cnj.c - cnj.b + 1);
	}

	private static boolean a(bqc bqc, int integer, fu fu) {
		cfj cfj4 = bqc.d_(fu);
		return cfj4.g() || cfj4.a(bvs.B) && fu.v() <= integer;
	}
}
