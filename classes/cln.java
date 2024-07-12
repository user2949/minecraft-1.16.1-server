import com.mojang.serialization.Codec;
import java.util.Random;

public class cln extends ckt<coc> {
	cln(Codec<coc> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coc coc) {
		int integer8 = random.nextInt(coc.c + 1);
		fu.a a9 = new fu.a();

		for (int integer10 = 0; integer10 < integer8; integer10++) {
			this.a(a9, random, fu, Math.min(integer10, 7));
			if (coc.b.c().test(bqu.d_(a9)) && !this.a(bqu, a9)) {
				bqu.a(a9, coc.d, 2);
			}
		}

		return true;
	}

	private void a(fu.a a, Random random, fu fu, int integer) {
		int integer6 = this.a(random, integer);
		int integer7 = this.a(random, integer);
		int integer8 = this.a(random, integer);
		a.a(fu, integer6, integer7, integer8);
	}

	private int a(Random random, int integer) {
		return Math.round((random.nextFloat() - random.nextFloat()) * (float)integer);
	}

	private boolean a(bqc bqc, fu fu) {
		fu.a a4 = new fu.a();

		for (fz fz8 : fz.values()) {
			a4.a(fu, fz8);
			if (bqc.d_(a4).g()) {
				return true;
			}
		}

		return false;
	}
}
