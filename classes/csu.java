import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class csu extends csq<cni> {
	public csu(Codec<cni> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, cni cni, fu fu) {
		if (random.nextFloat() < cni.b) {
			int integer5 = random.nextInt(16) + fu.u();
			int integer6 = random.nextInt(16) + fu.w();
			int integer7 = random.nextInt(cni.e - cni.d) + cni.c;
			return Stream.of(new fu(integer5, integer7, integer6));
		} else {
			return Stream.empty();
		}
	}
}
