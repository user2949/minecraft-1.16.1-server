import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

public class blm extends blp implements blw {
	private final float a;
	private final Multimap<aps, apv> b;

	public blm(blo blo, int integer, float float3, bke.a a) {
		super(blo, a);
		this.a = (float)integer + blo.c();
		Builder<aps, apv> builder6 = ImmutableMultimap.builder();
		builder6.put(apx.f, new apv(f, "Weapon modifier", (double)this.a, apv.a.ADDITION));
		builder6.put(apx.h, new apv(g, "Weapon modifier", (double)float3, apv.a.ADDITION));
		this.b = builder6.build();
	}

	public float f() {
		return this.a;
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, bec bec) {
		return !bec.b_();
	}

	@Override
	public float a(bki bki, cfj cfj) {
		if (cfj.a(bvs.aQ)) {
			return 15.0F;
		} else {
			cxd cxd4 = cfj.c();
			return cxd4 != cxd.e && cxd4 != cxd.g && cxd4 != cxd.N && !cfj.a(acx.H) && cxd4 != cxd.O ? 1.0F : 1.5F;
		}
	}

	@Override
	public boolean a(bki bki, aoy aoy2, aoy aoy3) {
		bki.a(1, aoy3, aoy -> aoy.c(aor.MAINHAND));
		return true;
	}

	@Override
	public boolean a(bki bki, bqb bqb, cfj cfj, fu fu, aoy aoy) {
		if (cfj.h(bqb, fu) != 0.0F) {
			bki.a(2, aoy, aoyx -> aoyx.c(aor.MAINHAND));
		}

		return true;
	}

	@Override
	public boolean b(cfj cfj) {
		return cfj.a(bvs.aQ);
	}

	@Override
	public Multimap<aps, apv> a(aor aor) {
		return aor == aor.MAINHAND ? this.b : super.a(aor);
	}
}
