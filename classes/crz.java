import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crz extends csq<cnz> {
	public crz(Codec<cnz> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, cnz cnz, fu fu) {
		int integer5 = 3 + random.nextInt(6);
		return IntStream.range(0, integer5).mapToObj(integer -> {
			int integer4 = random.nextInt(16) + fu.u();
			int integer5x = random.nextInt(16) + fu.w();
			int integer6 = random.nextInt(28) + 4;
			return new fu(integer4, integer6, integer5x);
		});
	}
}
