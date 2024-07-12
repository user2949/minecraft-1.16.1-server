import com.mojang.serialization.Codec;
import java.util.Random;

public class cmu extends ckt<coa> {
	private static final fu a = new fu(8, 3, 8);
	private static final bph ac = new bph(a);

	public cmu(Codec<coa> codec) {
		super(codec);
	}

	private static int a(int integer1, int integer2, int integer3, int integer4) {
		return Math.max(Math.abs(integer1 - integer3), Math.abs(integer2 - integer4));
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		bph bph8 = new bph(fu);
		if (a(bph8.b, bph8.c, ac.b, ac.c) > 1) {
			return true;
		} else {
			fu.a a9 = new fu.a();

			for (int integer10 = bph8.e(); integer10 <= bph8.g(); integer10++) {
				for (int integer11 = bph8.d(); integer11 <= bph8.f(); integer11++) {
					if (a(a.u(), a.w(), integer11, integer10) <= 16) {
						a9.d(integer11, a.v(), integer10);
						if (a9.equals(a)) {
							bqu.a(a9, bvs.m.n(), 2);
						} else {
							bqu.a(a9, bvs.b.n(), 2);
						}
					}
				}
			}

			return true;
		}
	}
}
