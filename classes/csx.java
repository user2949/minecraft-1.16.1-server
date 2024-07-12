import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class csx extends csq<csf> {
	public csx(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, csf csf, fu fu) {
		return IntStream.range(0, random.nextInt(random.nextInt(csf.b) + 1)).mapToObj(integer -> {
			int integer4 = random.nextInt(16) + fu.u();
			int integer5 = random.nextInt(16) + fu.w();
			int integer6 = random.nextInt(120) + 4;
			return new fu(integer4, integer6, integer5);
		});
	}
}
