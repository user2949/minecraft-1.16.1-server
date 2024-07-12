import com.mojang.serialization.Codec;
import java.util.Random;

public class cjw extends ckt<cnf> {
	public cjw(Codec<cnf> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnf cnf) {
		if (fu.v() < 5) {
			return false;
		} else {
			int integer8 = 2 + random.nextInt(2);
			int integer9 = 2 + random.nextInt(2);

			for (fu fu11 : fu.a(fu.b(-integer8, 0, -integer9), fu.b(integer8, 1, integer9))) {
				int integer12 = fu.u() - fu11.u();
				int integer13 = fu.w() - fu11.w();
				if ((float)(integer12 * integer12 + integer13 * integer13) <= random.nextFloat() * 10.0F - random.nextFloat() * 6.0F) {
					this.a(bqu, fu11, random, cnf);
				} else if ((double)random.nextFloat() < 0.031) {
					this.a(bqu, fu11, random, cnf);
				}
			}

			return true;
		}
	}

	private boolean a(bqc bqc, fu fu, Random random) {
		fu fu5 = fu.c();
		cfj cfj6 = bqc.d_(fu5);
		return cfj6.a(bvs.iE) ? random.nextBoolean() : cfj6.d(bqc, fu5, fz.UP);
	}

	private void a(bqc bqc, fu fu, Random random, cnf cnf) {
		if (bqc.w(fu) && this.a(bqc, fu, random)) {
			bqc.a(fu, cnf.b.a(random, fu), 4);
		}
	}
}
