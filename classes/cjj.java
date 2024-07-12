import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cjj<U extends cnr> extends ckt<U> {
	public cjj(Codec<U> codec) {
		super(codec);
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, U cnr) {
		cfj cfj8 = this.b(random, fu, cnr);
		int integer9 = 0;

		for (int integer10 = 0; integer10 < this.a(cnr); integer10++) {
			fu fu11 = this.a(random, fu, cnr);
			if (bqu.w(fu11) && fu11.v() < 255 && cfj8.a(bqu, fu11) && this.a(bqu, fu11, cnr)) {
				bqu.a(fu11, cfj8, 2);
				integer9++;
			}
		}

		return integer9 > 0;
	}

	public abstract boolean a(bqc bqc, fu fu, U cnr);

	public abstract int a(U cnr);

	public abstract fu a(Random random, fu fu, U cnr);

	public abstract cfj b(Random random, fu fu, U cnr);
}
