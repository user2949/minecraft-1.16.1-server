import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class crh extends csc<crf> {
	public crh(Codec<crf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, crf crf, fu fu) {
		if (random.nextFloat() < 1.0F / (float)crf.b) {
			int integer7 = random.nextInt(16) + fu.u();
			int integer8 = random.nextInt(16) + fu.w();
			int integer9 = bqc.a(cio.a.MOTION_BLOCKING, integer7, integer8) * 2;
			return integer9 <= 0 ? Stream.empty() : Stream.of(new fu(integer7, random.nextInt(integer9), integer8));
		} else {
			return Stream.empty();
		}
	}
}
