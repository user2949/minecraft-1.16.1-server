import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class ckk extends ckt<cno> {
	private static final ImmutableList<bvr> a = ImmutableList.of(bvs.z, bvs.dV, bvs.dW, bvs.dX, bvs.dY, bvs.bR, bvs.bP);
	private static final fz[] ac = fz.values();

	private static int a(Random random, cno cno) {
		return cno.d + random.nextInt(cno.e - cno.d + 1);
	}

	private static int b(Random random, cno cno) {
		return random.nextInt(cno.f + 1);
	}

	public ckk(Codec<cno> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cno cno) {
		fu fu8 = a(bqu, fu.i().a(fz.a.Y, 1, bqu.I() - 1));
		if (fu8 == null) {
			return false;
		} else {
			boolean boolean9 = false;
			boolean boolean10 = random.nextDouble() < 0.9;
			int integer11 = boolean10 ? b(random, cno) : 0;
			int integer12 = boolean10 ? b(random, cno) : 0;
			boolean boolean13 = boolean10 && integer11 != 0 && integer12 != 0;
			int integer14 = a(random, cno);
			int integer15 = a(random, cno);
			int integer16 = Math.max(integer14, integer15);

			for (fu fu18 : fu.a(fu8, integer14, 0, integer15)) {
				if (fu18.k(fu8) > integer16) {
					break;
				}

				if (a(bqu, fu18, cno)) {
					if (boolean13) {
						boolean9 = true;
						this.a(bqu, fu18, cno.c);
					}

					fu fu19 = fu18.b(integer11, 0, integer12);
					if (a(bqu, fu19, cno)) {
						boolean9 = true;
						this.a(bqu, fu19, cno.b);
					}
				}
			}

			return boolean9;
		}
	}

	private static boolean a(bqc bqc, fu fu, cno cno) {
		cfj cfj4 = bqc.d_(fu);
		if (cfj4.a(cno.b.b())) {
			return false;
		} else if (a.contains(cfj4.b())) {
			return false;
		} else {
			for (fz fz8 : ac) {
				boolean boolean9 = bqc.d_(fu.a(fz8)).g();
				if (boolean9 && fz8 != fz.UP || !boolean9 && fz8 == fz.UP) {
					return false;
				}
			}

			return true;
		}
	}

	@Nullable
	private static fu a(bqc bqc, fu.a a) {
		while (a.v() > 1) {
			if (bqc.d_(a).g()) {
				cfj cfj3 = bqc.d_(a.c(fz.DOWN));
				a.c(fz.UP);
				if (!cfj3.a(bvs.B) && !cfj3.a(bvs.z) && !cfj3.g()) {
					return a;
				}
			}

			a.c(fz.DOWN);
		}

		return null;
	}
}
