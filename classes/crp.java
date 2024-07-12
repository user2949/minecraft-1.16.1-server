import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crp extends csq<cry> {
	public crp(Codec<cry> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, cry cry, fu fu) {
		int integer5 = cry.b;
		int integer6 = cry.c;
		int integer7 = cry.d;
		return IntStream.range(0, integer5).mapToObj(integer5x -> {
			int integer6x = random.nextInt(16) + fu.u();
			int integer7x = random.nextInt(16) + fu.w();
			int integer8 = random.nextInt(integer7) + random.nextInt(integer7) - integer7 + integer6;
			return new fu(integer6x, integer8, integer7x);
		});
	}
}
