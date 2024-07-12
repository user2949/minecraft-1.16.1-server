import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Map;
import java.util.function.Supplier;

public class cug extends cvc {
	public static final Codec<cug> a = Codec.unit((Supplier<cug>)(() -> cug.b));
	public static final cug b = new cug();
	private final Map<bvr, bvr> c = v.a(Maps.<bvr, bvr>newHashMap(), hashMap -> {
		hashMap.put(bvs.m, bvs.np);
		hashMap.put(bvs.bJ, bvs.np);
		hashMap.put(bvs.b, bvs.nt);
		hashMap.put(bvs.du, bvs.nu);
		hashMap.put(bvs.dv, bvs.nu);
		hashMap.put(bvs.ci, bvs.nq);
		hashMap.put(bvs.lh, bvs.nq);
		hashMap.put(bvs.lj, bvs.nB);
		hashMap.put(bvs.dS, bvs.ny);
		hashMap.put(bvs.lf, bvs.ny);
		hashMap.put(bvs.hV, bvs.ns);
		hashMap.put(bvs.lv, bvs.ns);
		hashMap.put(bvs.hR, bvs.nC);
		hashMap.put(bvs.hQ, bvs.nC);
		hashMap.put(bvs.hX, bvs.nx);
		hashMap.put(bvs.lt, bvs.nx);
		hashMap.put(bvs.lJ, bvs.nz);
		hashMap.put(bvs.lH, bvs.nz);
		hashMap.put(bvs.et, bvs.nr);
		hashMap.put(bvs.eu, bvs.nr);
		hashMap.put(bvs.dx, bvs.nw);
		hashMap.put(bvs.dw, bvs.nv);
		hashMap.put(bvs.dH, bvs.dI);
	});

	private cug() {
	}

	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		bvr bvr8 = (bvr)this.c.get(c5.b.b());
		if (bvr8 == null) {
			return c5;
		} else {
			cfj cfj9 = c5.b;
			cfj cfj10 = bvr8.n();
			if (cfj9.b(cbn.a)) {
				cfj10 = cfj10.a(cbn.a, cfj9.c(cbn.a));
			}

			if (cfj9.b(cbn.b)) {
				cfj10 = cfj10.a(cbn.b, cfj9.c(cbn.b));
			}

			if (cfj9.b(caz.a)) {
				cfj10 = cfj10.a(caz.a, cfj9.c(caz.a));
			}

			return new cve.c(c5.a, cfj10, c5.c);
		}
	}

	@Override
	protected cvd<?> a() {
		return cvd.h;
	}
}
