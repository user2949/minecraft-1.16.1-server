import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class cnb extends cmy {
	public static final Codec<cnb> b = Codec.unit((Supplier<cnb>)(() -> cnb.c));
	public static final cnb c = new cnb();

	@Override
	protected cmz<?> a() {
		return cmz.b;
	}

	@Override
	public void a(bqc bqc, fu fu, cfj cfj, Random random) {
		((bxg)cfj.b()).a(bqc, fu, 2);
	}
}
