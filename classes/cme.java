import com.mojang.serialization.Codec;
import java.util.Random;

public class cme extends ckt<cop> {
	public cme(Codec<cop> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cop cop) {
		int integer8 = random.nextInt(cop.b.size());
		ckb<?, ?> ckb9 = (ckb<?, ?>)cop.b.get(integer8);
		return ckb9.a(bqu, bqq, cha, random, fu);
	}
}
