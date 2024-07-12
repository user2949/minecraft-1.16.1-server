import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class cun extends cvc {
	public static final Codec<cun> a = Codec.unit((Supplier<cun>)(() -> cun.b));
	public static final cun b = new cun();

	private cun() {
	}

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		cfj cfj8 = c5.b;
		if (cfj8.a(bvs.mZ)) {
			String string9 = c5.c.l("final_state");
			ef ef10 = new ef(new StringReader(string9), false);

			try {
				ef10.a(true);
			} catch (CommandSyntaxException var11) {
				throw new RuntimeException(var11);
			}

			return ef10.b().a(bvs.iN) ? null : new cve.c(c5.a, ef10.b(), null);
		} else {
			return c5;
		}
	}

	@Override
	protected cvd<?> a() {
		return cvd.d;
	}
}
