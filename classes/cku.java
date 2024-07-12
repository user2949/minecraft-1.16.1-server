import com.mojang.serialization.Codec;
import java.util.Random;

public class cku extends ckt<cnv> {
	public cku(Codec<cnv> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnv cnv) {
		fu.a a8 = new fu.a();

		for (int integer9 = 0; integer9 < 16; integer9++) {
			for (int integer10 = 0; integer10 < 16; integer10++) {
				int integer11 = fu.u() + integer9;
				int integer12 = fu.w() + integer10;
				int integer13 = cnv.b;
				a8.d(integer11, integer13, integer12);
				if (bqu.d_(a8).g()) {
					bqu.a(a8, cnv.c, 2);
				}
			}
		}

		return true;
	}
}
