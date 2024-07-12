import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csb extends csq<cnz> {
	public csb(Codec<cnz> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, cnz cnz, fu fu) {
		Stream<fu> stream5 = Stream.empty();
		if (random.nextInt(14) == 0) {
			stream5 = Stream.concat(stream5, Stream.of(fu.b(random.nextInt(16), 55 + random.nextInt(16), random.nextInt(16))));
			if (random.nextInt(4) == 0) {
				stream5 = Stream.concat(stream5, Stream.of(fu.b(random.nextInt(16), 55 + random.nextInt(16), random.nextInt(16))));
			}

			return stream5;
		} else {
			return Stream.empty();
		}
	}
}
