import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csv extends csq<cnl> {
	public csv(Codec<cnl> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, cnl cnl, fu fu) {
		return IntStream.range(0, cnl.b).mapToObj(integer -> {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7 = random.nextInt(cnl.e - cnl.d) + cnl.c;
			return new fu(integer5, integer7, integer6);
		});
	}
}
