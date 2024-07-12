import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class cur extends cus {
	public static final Codec<cur> a = Codec.unit((Supplier<cur>)(() -> cur.b));
	public static final cur b = new cur();

	private cur() {
	}

	@Override
	public boolean a(fu fu1, fu fu2, fu fu3, Random random) {
		return true;
	}

	@Override
	protected cut<?> a() {
		return cut.a;
	}
}
