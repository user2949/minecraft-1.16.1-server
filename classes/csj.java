import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csj extends csc<crf> {
	public csj(Codec<crf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, crf crf, fu fu) {
		if (random.nextInt(crf.b) == 0) {
			int integer7 = random.nextInt(16) + fu.u();
			int integer8 = random.nextInt(16) + fu.w();
			int integer9 = random.nextInt(cha.e());
			return Stream.of(new fu(integer7, integer9, integer8));
		} else {
			return Stream.empty();
		}
	}
}
