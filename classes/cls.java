import com.mojang.serialization.Codec;
import java.util.Random;

public class cls extends ckt<coe> {
	public cls(Codec<coe> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coe coe) {
		boolean boolean8 = random.nextBoolean();
		return boolean8 ? coe.b.a(bqu, bqq, cha, random, fu) : coe.c.a(bqu, bqq, cha, random, fu);
	}
}
