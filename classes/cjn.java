import com.mojang.serialization.Codec;
import java.util.Random;

public class cjn extends ckt<coa> {
	public cjn(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		if (bqu.w(fu) && !bqu.w(fu.b())) {
			fu.a a8 = fu.i();
			fu.a a9 = fu.i();
			boolean boolean10 = true;
			boolean boolean11 = true;
			boolean boolean12 = true;
			boolean boolean13 = true;

			while (bqu.w(a8)) {
				if (bqb.l(a8)) {
					return true;
				}

				bqu.a(a8, bvs.cO.n(), 2);
				boolean10 = boolean10 && this.b(bqu, random, a9.a(a8, fz.NORTH));
				boolean11 = boolean11 && this.b(bqu, random, a9.a(a8, fz.SOUTH));
				boolean12 = boolean12 && this.b(bqu, random, a9.a(a8, fz.WEST));
				boolean13 = boolean13 && this.b(bqu, random, a9.a(a8, fz.EAST));
				a8.c(fz.DOWN);
			}

			a8.c(fz.UP);
			this.a(bqu, random, a9.a(a8, fz.NORTH));
			this.a(bqu, random, a9.a(a8, fz.SOUTH));
			this.a(bqu, random, a9.a(a8, fz.WEST));
			this.a(bqu, random, a9.a(a8, fz.EAST));
			a8.c(fz.DOWN);
			fu.a a14 = new fu.a();

			for (int integer15 = -3; integer15 < 4; integer15++) {
				for (int integer16 = -3; integer16 < 4; integer16++) {
					int integer17 = aec.a(integer15) * aec.a(integer16);
					if (random.nextInt(10) < 10 - integer17) {
						a14.g(a8.b(integer15, 0, integer16));
						int integer18 = 3;

						while (bqu.w(a9.a(a14, fz.DOWN))) {
							a14.c(fz.DOWN);
							if (--integer18 <= 0) {
								break;
							}
						}

						if (!bqu.w(a9.a(a14, fz.DOWN))) {
							bqu.a(a14, bvs.cO.n(), 2);
						}
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	private void a(bqc bqc, Random random, fu fu) {
		if (random.nextBoolean()) {
			bqc.a(fu, bvs.cO.n(), 2);
		}
	}

	private boolean b(bqc bqc, Random random, fu fu) {
		if (random.nextInt(10) != 0) {
			bqc.a(fu, bvs.cO.n(), 2);
			return true;
		} else {
			return false;
		}
	}
}
