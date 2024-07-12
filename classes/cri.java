import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cri extends csq<crf> {
	public cri(Codec<crf> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, crf crf, fu fu) {
		return random.nextFloat() < 1.0F / (float)crf.b ? Stream.of(fu) : Stream.empty();
	}
}
