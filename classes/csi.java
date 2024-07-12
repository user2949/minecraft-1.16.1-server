import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csi extends csc<crf> {
	public csi(Codec<crf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, crf crf, fu fu) {
		if (random.nextInt(crf.b / 10) == 0) {
			int integer7 = random.nextInt(16) + fu.u();
			int integer8 = random.nextInt(16) + fu.w();
			int integer9 = random.nextInt(random.nextInt(cha.e() - 8) + 8);
			if (integer9 < bqc.t_() || random.nextInt(crf.b / 8) == 0) {
				return Stream.of(new fu(integer7, integer9, integer8));
			}
		}

		return Stream.empty();
	}
}
