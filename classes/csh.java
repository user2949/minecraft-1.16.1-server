import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csh extends csc<crf> {
	public csh(Codec<crf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, crf crf, fu fu) {
		if (random.nextFloat() < 1.0F / (float)crf.b) {
			int integer7 = random.nextInt(8) + 4 + fu.u();
			int integer8 = random.nextInt(8) + 4 + fu.w();
			int integer9 = bqc.a(cio.a.MOTION_BLOCKING, integer7, integer8);
			return Stream.of(new fu(integer7, integer9, integer8));
		} else {
			return Stream.empty();
		}
	}
}
