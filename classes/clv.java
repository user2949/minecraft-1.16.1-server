import com.mojang.serialization.Codec;
import java.util.Random;

public class clv extends ckt<cof> {
	public clv(Codec<cof> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cof cof) {
		for (cmw<?> cmw9 : cof.b) {
			if (random.nextFloat() < cmw9.c) {
				return cmw9.a(bqu, bqq, cha, random, fu);
			}
		}

		return cof.c.a(bqu, bqq, cha, random, fu);
	}
}
