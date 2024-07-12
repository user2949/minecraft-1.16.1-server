import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class cuk extends cvc {
	public static final Codec<cuk> a = Codec.FLOAT.fieldOf("integrity").withDefault(1.0F).<cuk>xmap(cuk::new, cuk -> cuk.b).codec();
	private final float b;

	public cuk(float float1) {
		this.b = float1;
	}

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		Random random8 = cvb.b(c5.a);
		return !(this.b >= 1.0F) && !(random8.nextFloat() <= this.b) ? null : c5;
	}

	@Override
	protected cvd<?> a() {
		return cvd.b;
	}
}
