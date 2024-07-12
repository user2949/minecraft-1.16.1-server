import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cjk extends ckt<cnt> {
	public cjk(Codec<cnt> codec) {
		super(codec);
	}

	protected void a(bqc bqc, Random random, fu fu, cnt cnt, int integer, fu.a a) {
		for (int integer8 = 0; integer8 < integer; integer8++) {
			a.g(fu).c(fz.UP, integer8);
			if (!bqc.d_(a).i(bqc, a)) {
				this.a(bqc, a, cnt.c.a(random, fu));
			}
		}
	}

	protected int a(Random random) {
		int integer3 = random.nextInt(3) + 4;
		if (random.nextInt(12) == 0) {
			integer3 *= 2;
		}

		return integer3;
	}

	protected boolean a(bqc bqc, fu fu, int integer, fu.a a, cnt cnt) {
		int integer7 = fu.v();
		if (integer7 >= 1 && integer7 + integer + 1 < 256) {
			bvr bvr8 = bqc.d_(fu.c()).b();
			if (!b(bvr8)) {
				return false;
			} else {
				for (int integer9 = 0; integer9 <= integer; integer9++) {
					int integer10 = this.a(-1, -1, cnt.d, integer9);

					for (int integer11 = -integer10; integer11 <= integer10; integer11++) {
						for (int integer12 = -integer10; integer12 <= integer10; integer12++) {
							cfj cfj13 = bqc.d_(a.a(fu, integer11, integer9, integer12));
							if (!cfj13.g() && !cfj13.a(acx.H)) {
								return false;
							}
						}
					}
				}

				return true;
			}
		} else {
			return false;
		}
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnt cnt) {
		int integer8 = this.a(random);
		fu.a a9 = new fu.a();
		if (!this.a(bqu, fu, integer8, a9, cnt)) {
			return false;
		} else {
			this.a(bqu, random, fu, integer8, a9, cnt);
			this.a(bqu, random, fu, cnt, integer8, a9);
			return true;
		}
	}

	protected abstract int a(int integer1, int integer2, int integer3, int integer4);

	protected abstract void a(bqc bqc, Random random, fu fu, int integer, fu.a a, cnt cnt);
}
