import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csa extends csc<cnz> {
	public csa(Codec<cnz> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, cnz cnz, fu fu) {
		if (random.nextInt(700) == 0) {
			int integer7 = random.nextInt(16) + fu.u();
			int integer8 = random.nextInt(16) + fu.w();
			int integer9 = bqc.a(cio.a.MOTION_BLOCKING, integer7, integer8);
			if (integer9 > 0) {
				int integer10 = integer9 + 3 + random.nextInt(7);
				return Stream.of(new fu(integer7, integer10, integer8));
			}
		}

		return Stream.empty();
	}
}
