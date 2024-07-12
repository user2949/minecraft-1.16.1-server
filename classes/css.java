import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class css extends csc<csl> {
	public css(Codec<csl> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csl csl, fu fu) {
		double double7 = bre.f.a((double)fu.u() / csl.c, (double)fu.w() / csl.c, false);
		int integer9 = (int)Math.ceil((double7 + csl.d) * (double)csl.b);
		return IntStream.range(0, integer9).mapToObj(integer -> {
			int integer6 = random.nextInt(16) + fu.u();
			int integer7 = random.nextInt(16) + fu.w();
			int integer8 = bqc.a(csl.e, integer6, integer7);
			return new fu(integer6, integer8, integer7);
		});
	}
}
