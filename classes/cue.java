import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class cue extends cuy {
	public static final Codec<cue> a = Codec.unit((Supplier<cue>)(() -> cue.b));
	public static final cue b = new cue();

	private cue() {
	}

	@Override
	public boolean a(cfj cfj, Random random) {
		return true;
	}

	@Override
	protected cuz<?> a() {
		return cuz.a;
	}
}
