import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csk extends csc<crf> {
	public csk(Codec<crf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, crf crf, fu fu) {
		int integer7 = crf.b;
		return IntStream.range(0, integer7).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7x = random.nextInt(cha.e());
			return new fu(integer5, integer7x, integer6);
		});
	}
}
