import com.mojang.serialization.Codec;
import java.util.Random;

public class clu extends ckt<coh> {
	public clu(Codec<coh> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coh coh) {
		int integer8 = random.nextInt(5) - 3 + coh.c;

		for (int integer9 = 0; integer9 < integer8; integer9++) {
			int integer10 = random.nextInt(coh.b.size());
			ckb<?, ?> ckb11 = (ckb<?, ?>)coh.b.get(integer10);
			ckb11.a(bqu, bqq, cha, random, fu);
		}

		return true;
	}
}
