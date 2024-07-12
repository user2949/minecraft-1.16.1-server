import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cst extends csc<csp> {
	public cst(Codec<csp> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csp csp, fu fu) {
		int integer7 = random.nextInt(csp.c - csp.b) + csp.b;
		return IntStream.range(0, integer7).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7x = bqc.a(cio.a.OCEAN_FLOOR_WG, integer5, integer6);
			return new fu(integer5, integer7x, integer6);
		});
	}
}
