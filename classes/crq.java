import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crq extends csc<csf> {
	public crq(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csf csf, fu fu) {
		return IntStream.range(0, csf.b).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7 = bqc.a(cio.a.MOTION_BLOCKING, integer5, integer6) * 2;
			return integer7 <= 0 ? null : new fu(integer5, random.nextInt(integer7), integer6);
		}).filter(Objects::nonNull);
	}
}
