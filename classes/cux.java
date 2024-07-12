import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class cux extends cvc {
	public static final Codec<cux> a = cuu.a.listOf().fieldOf("rules").<cux>xmap(cux::new, cux -> cux.b).codec();
	private final ImmutableList<cuu> b;

	public cux(List<? extends cuu> list) {
		this.b = ImmutableList.copyOf(list);
	}

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		Random random8 = new Random(aec.a(c5.a));
		cfj cfj9 = bqd.d_(c5.a);

		for (cuu cuu11 : this.b) {
			if (cuu11.a(c5.b, cfj9, c4.a, c5.a, fu3, random8)) {
				return new cve.c(c5.a, cuu11.a(), cuu11.b());
			}
		}

		return c5;
	}

	@Override
	protected cvd<?> a() {
		return cvd.e;
	}
}
