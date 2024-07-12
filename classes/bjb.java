import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.Set;

public class bjb extends blp implements blw {
	private final Set<bvr> a;
	protected final float b;
	private final float c;
	private final Multimap<aps, apv> d;

	protected bjb(float float1, float float2, blo blo, Set<bvr> set, bke.a a) {
		super(blo, a);
		this.a = set;
		this.b = blo.b();
		this.c = float1 + blo.c();
		Builder<aps, apv> builder7 = ImmutableMultimap.builder();
		builder7.put(apx.f, new apv(f, "Tool modifier", (double)this.c, apv.a.ADDITION));
		builder7.put(apx.h, new apv(g, "Tool modifier", (double)float2, apv.a.ADDITION));
		this.d = builder7.build();
	}

	@Override
	public float a(bki bki, cfj cfj) {
		return this.a.contains(cfj.b()) ? this.b : 1.0F;
	}

	@Override
	public boolean a(bki bki, aoy aoy2, aoy aoy3) {
		bki.a(2, aoy3, aoy -> aoy.c(aor.MAINHAND));
		return true;
	}

	@Override
	public boolean a(bki bki, bqb bqb, cfj cfj, fu fu, aoy aoy) {
		if (!bqb.v && cfj.h(bqb, fu) != 0.0F) {
			bki.a(1, aoy, aoyx -> aoyx.c(aor.MAINHAND));
		}

		return true;
	}

	@Override
	public Multimap<aps, apv> a(aor aor) {
		return aor == aor.MAINHAND ? this.d : super.a(aor);
	}

	public float d() {
		return this.c;
	}
}
