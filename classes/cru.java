import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cru extends csc<csf> {
	public cru(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csf csf, fu fu) {
		return IntStream.range(0, csf.b).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7 = bqc.a(cio.a.OCEAN_FLOOR_WG, integer5, integer6);
			return new fu(integer5, integer7, integer6);
		});
	}
}
