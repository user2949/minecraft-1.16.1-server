import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csd extends csc<csf> {
	public csd(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csf csf, fu fu) {
		int integer7 = random.nextInt(csf.b);
		return IntStream.range(0, integer7).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7x = bqc.a(cio.a.MOTION_BLOCKING, integer5, integer6);
			return new fu(integer5, integer7x, integer6);
		});
	}
}
