import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class csw extends csq<csf> {
	public csw(Codec<csf> codec) {
		super(codec);
	}

	public Stream<fu> a(Random random, csf csf, fu fu) {
		List<fu> list5 = Lists.<fu>newArrayList();

		for (int integer6 = 0; integer6 < random.nextInt(random.nextInt(csf.b) + 1) + 1; integer6++) {
			int integer7 = random.nextInt(16) + fu.u();
			int integer8 = random.nextInt(16) + fu.w();
			int integer9 = random.nextInt(120) + 4;
			list5.add(new fu(integer7, integer9, integer8));
		}

		return list5.stream();
	}
}
