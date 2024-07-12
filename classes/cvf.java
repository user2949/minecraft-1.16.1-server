import com.mojang.serialization.Codec;
import java.util.Random;

public class cvf extends cuy {
	public static final Codec<cvf> a = adf.a(adb.e()::a).fieldOf("tag").<cvf>xmap(cvf::new, cvf -> cvf.b).codec();
	private final adf<bvr> b;

	public cvf(adf<bvr> adf) {
		this.b = adf;
	}

	@Override
	public boolean a(cfj cfj, Random random) {
		return cfj.a(this.b);
	}

	@Override
	protected cuz<?> a() {
		return cuz.d;
	}
}
