import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cna extends cmy {
	public static final Codec<cna> b = RecordCodecBuilder.create(
		instance -> instance.group(Codec.INT.fieldOf("min_size").forGetter(cna -> cna.c), Codec.INT.fieldOf("extra_size").forGetter(cna -> cna.d))
				.apply(instance, cna::new)
	);
	private final int c;
	private final int d;

	public cna(int integer1, int integer2) {
		this.c = integer1;
		this.d = integer2;
	}

	@Override
	protected cmz<?> a() {
		return cmz.c;
	}

	@Override
	public void a(bqc bqc, fu fu, cfj cfj, Random random) {
		fu.a a6 = fu.i();
		int integer7 = this.c + random.nextInt(random.nextInt(this.d + 1) + 1);

		for (int integer8 = 0; integer8 < integer7; integer8++) {
			bqc.a(a6, cfj, 2);
			a6.c(fz.UP);
		}
	}
}
