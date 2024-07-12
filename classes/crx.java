import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crx extends csc<cnz> {
	public crx(Codec<cnz> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, cnz cnz, fu fu) {
		return IntStream.range(0, 16).mapToObj(integer -> {
			int integer5 = integer / 4;
			int integer6 = integer % 4;
			int integer7 = integer5 * 4 + 1 + random.nextInt(3) + fu.u();
			int integer8 = integer6 * 4 + 1 + random.nextInt(3) + fu.w();
			int integer9 = bqc.a(cio.a.MOTION_BLOCKING, integer7, integer8);
			return new fu(integer7, integer9, integer8);
		});
	}
}
