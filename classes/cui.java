import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.Nullable;

public class cui extends cvc {
	public static final Codec<cui> a = cfj.b.xmap(cfi.a::b, bvr::n).listOf().fieldOf("blocks").<cui>xmap(cui::new, cui -> cui.e).codec();
	public static final cui b = new cui(ImmutableList.of(bvs.mY));
	public static final cui c = new cui(ImmutableList.of(bvs.a));
	public static final cui d = new cui(ImmutableList.of(bvs.a, bvs.mY));
	private final ImmutableList<bvr> e;

	public cui(List<bvr> list) {
		this.e = ImmutableList.copyOf(list);
	}

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		return this.e.contains(c5.b.b()) ? null : c5;
	}

	@Override
	protected cvd<?> a() {
		return cvd.a;
	}
}
