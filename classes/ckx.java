import com.mojang.serialization.Codec;
import java.util.Random;

public class ckx extends cjk {
	public ckx(Codec<cnt> codec) {
		super(codec);
	}

	@Override
	protected void a(bqc bqc, Random random, fu fu, int integer, fu.a a, cnt cnt) {
		int integer8 = cnt.d;

		for (int integer9 = -integer8; integer9 <= integer8; integer9++) {
			for (int integer10 = -integer8; integer10 <= integer8; integer10++) {
				boolean boolean11 = integer9 == -integer8;
				boolean boolean12 = integer9 == integer8;
				boolean boolean13 = integer10 == -integer8;
				boolean boolean14 = integer10 == integer8;
				boolean boolean15 = boolean11 || boolean12;
				boolean boolean16 = boolean13 || boolean14;
				if (!boolean15 || !boolean16) {
					a.a(fu, integer9, integer, integer10);
					if (!bqc.d_(a).i(bqc, a)) {
						boolean boolean17 = boolean11 || boolean16 && integer9 == 1 - integer8;
						boolean boolean18 = boolean12 || boolean16 && integer9 == integer8 - 1;
						boolean boolean19 = boolean13 || boolean15 && integer10 == 1 - integer8;
						boolean boolean20 = boolean14 || boolean15 && integer10 == integer8 - 1;
						this.a(
							bqc,
							a,
							cnt.b
								.a(random, fu)
								.a(byq.d, Boolean.valueOf(boolean17))
								.a(byq.b, Boolean.valueOf(boolean18))
								.a(byq.a, Boolean.valueOf(boolean19))
								.a(byq.c, Boolean.valueOf(boolean20))
						);
					}
				}
			}
		}
	}

	@Override
	protected int a(int integer1, int integer2, int integer3, int integer4) {
		return integer4 <= 3 ? 0 : integer3;
	}
}
