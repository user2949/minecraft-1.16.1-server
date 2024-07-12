import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csr extends csc<cnz> {
	public csr(Codec<cnz> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, cnz cnz, fu fu) {
		int integer7 = random.nextInt(16) + fu.u();
		int integer8 = random.nextInt(16) + fu.w();
		int integer9 = bqc.a(cio.a.OCEAN_FLOOR_WG, integer7, integer8);
		return Stream.of(new fu(integer7, integer9, integer8));
	}
}
