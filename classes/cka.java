import com.mojang.serialization.Codec;
import java.util.Random;

public class cka extends ckt<coa> {
	public cka(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		if (bqu.w(fu.b()) && bqu.d_(fu).a(bvs.ee)) {
			bwi.a(bqu, fu.b(), random, 8);
			return true;
		} else {
			return false;
		}
	}
}
