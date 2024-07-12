import com.mojang.serialization.Codec;
import java.util.Random;

public class cla extends cjk {
	public cla(Codec<cnt> codec) {
		super(codec);
	}

	@Override
	protected void a(bqc bqc, Random random, fu fu, int integer, fu.a a, cnt cnt) {
		for (int integer8 = integer - 3; integer8 <= integer; integer8++) {
			int integer9 = integer8 < integer ? cnt.d : cnt.d - 1;
			int integer10 = cnt.d - 2;

			for (int integer11 = -integer9; integer11 <= integer9; integer11++) {
				for (int integer12 = -integer9; integer12 <= integer9; integer12++) {
					boolean boolean13 = integer11 == -integer9;
					boolean boolean14 = integer11 == integer9;
					boolean boolean15 = integer12 == -integer9;
					boolean boolean16 = integer12 == integer9;
					boolean boolean17 = boolean13 || boolean14;
					boolean boolean18 = boolean15 || boolean16;
					if (integer8 >= integer || boolean17 != boolean18) {
						a.a(fu, integer11, integer8, integer12);
						if (!bqc.d_(a).i(bqc, a)) {
							this.a(
								bqc,
								a,
								cnt.b
									.a(random, fu)
									.a(byq.e, Boolean.valueOf(integer8 >= integer - 1))
									.a(byq.d, Boolean.valueOf(integer11 < -integer10))
									.a(byq.b, Boolean.valueOf(integer11 > integer10))
									.a(byq.a, Boolean.valueOf(integer12 < -integer10))
									.a(byq.c, Boolean.valueOf(integer12 > integer10))
							);
						}
					}
				}
			}
		}
	}

	@Override
	protected int a(int integer1, int integer2, int integer3, int integer4) {
		int integer6 = 0;
		if (integer4 < integer2 && integer4 >= integer2 - 3) {
			integer6 = integer3;
		} else if (integer4 == integer2) {
			integer6 = integer3;
		}

		return integer6;
	}
}
