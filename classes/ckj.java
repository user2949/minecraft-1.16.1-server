import com.mojang.serialization.Codec;
import java.util.Random;

public class ckj extends cjj<cog> {
	public ckj(Codec<cog> codec) {
		super(codec);
	}

	public boolean a(bqc bqc, fu fu, cog cog) {
		return !cog.e.contains(bqc.d_(fu));
	}

	public int a(cog cog) {
		return cog.f;
	}

	public fu a(Random random, fu fu, cog cog) {
		return fu.b(random.nextInt(cog.g) - random.nextInt(cog.g), random.nextInt(cog.h) - random.nextInt(cog.h), random.nextInt(cog.i) - random.nextInt(cog.i));
	}

	public cfj b(Random random, fu fu, cog cog) {
		return cog.b.a(random, fu);
	}
}
