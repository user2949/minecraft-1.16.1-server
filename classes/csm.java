import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csm extends csc<cny> {
	public csm(Codec<cny> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, cny cny, fu fu) {
		double double7 = bre.f.a((double)fu.u() / 200.0, (double)fu.w() / 200.0, false);
		int integer9 = double7 < cny.b ? cny.c : cny.d;
		return IntStream.range(0, integer9).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7 = bqc.a(cio.a.MOTION_BLOCKING, integer5, integer6) + 32;
			return integer7 <= 0 ? null : new fu(integer5, random.nextInt(integer7), integer6);
		}).filter(Objects::nonNull);
	}
}
