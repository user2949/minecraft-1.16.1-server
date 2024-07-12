import com.mojang.serialization.Codec;
import java.util.Random;

public class cmd extends ckt<coo> {
	public cmd(Codec<coo> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coo coo) {
		if (coo.c.contains(bqu.d_(fu.c())) && coo.d.contains(bqu.d_(fu)) && coo.e.contains(bqu.d_(fu.b()))) {
			bqu.a(fu, coo.b, 2);
			return true;
		} else {
			return false;
		}
	}
}
