import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csy extends csc<csf> {
	public csy(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csf csf, fu fu) {
		int integer7 = bqc.t_() / 2 + 1;
		return IntStream.range(0, csf.b).mapToObj(integer4 -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7x = integer7 - 5 + random.nextInt(10);
			return new fu(integer5, integer7x, integer6);
		});
	}
}
