import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crr extends csc<csf> {
	public crr(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, csf csf, fu fu) {
		return IntStream.range(0, csf.b).mapToObj(integer -> {
			int integer4 = random.nextInt(16) + fu.u();
			int integer5 = random.nextInt(16) + fu.w();
			int integer6 = 64;
			return new fu(integer4, 64, integer5);
		});
	}
}
