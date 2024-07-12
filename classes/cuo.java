import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class cuo extends cvc {
	public static final Codec<cuo> a = Codec.unit((Supplier<cuo>)(() -> cuo.b));
	public static final cuo b = new cuo();

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		fu fu8 = c5.a;
		boolean boolean9 = bqd.d_(fu8).a(bvs.B);
		return boolean9 && !bvr.a(c5.b.j(bqd, fu8)) ? new cve.c(fu8, bvs.B.n(), c5.c) : c5;
	}

	@Override
	protected cvd<?> a() {
		return cvd.i;
	}
}
