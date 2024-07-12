import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csz extends csq<cnl> {
	public csz(Codec<cnl> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, cnl cnl, fu fu) {
		int integer5 = random.nextInt(Math.max(cnl.b, 1));
		return IntStream.range(0, integer5).mapToObj(integer -> {
			int integer5x = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7 = random.nextInt(cnl.e - cnl.d) + cnl.c;
			return new fu(integer5x, integer7, integer6);
		});
	}
}
