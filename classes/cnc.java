import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class cnc extends cmy {
	public static final Codec<cnc> b = Codec.unit((Supplier<cnc>)(() -> cnc.c));
	public static final cnc c = new cnc();

	@Override
	protected cmz<?> a() {
		return cmz.a;
	}

	@Override
	public void a(bqc bqc, fu fu, cfj cfj, Random random) {
		bqc.a(fu, cfj, 2);
	}
}
