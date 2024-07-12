import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crk extends csc<cnz> {
	public crk(Codec<cnz> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, cnz cnz, fu fu) {
		int integer7 = random.nextInt(5);
		return IntStream.range(0, integer7).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7x = bqc.a(cio.a.MOTION_BLOCKING, integer5, integer6);
			if (integer7x > 0) {
				int integer8 = integer7x - 1;
				return new fu(integer5, integer8, integer6);
			} else {
				return null;
			}
		}).filter(Objects::nonNull);
	}
}
