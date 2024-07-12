import com.google.common.collect.Maps;
import java.util.Map;

public class bxy extends bvr {
	private static final Map<bvr, bvr> b = Maps.<bvr, bvr>newHashMap();
	protected static final dfg a = bvr.a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
	private final bvr c;

	public bxy(bvr bvr, cfi.c c) {
		super(c);
		this.c = bvr;
		b.put(bvr, this);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		bke bke9 = bki8.b();
		bvr bvr10 = bke9 instanceof bim ? (bvr)b.getOrDefault(((bim)bke9).e(), bvs.a) : bvs.a;
		boolean boolean11 = bvr10 == bvs.a;
		boolean boolean12 = this.c == bvs.a;
		if (boolean11 != boolean12) {
			if (boolean12) {
				bqb.a(fu, bvr10.n(), 3);
				bec.a(acu.ag);
				if (!bec.bJ.d) {
					bki8.g(1);
				}
			} else {
				bki bki13 = new bki(this.c);
				if (bki8.a()) {
					bec.a(anf, bki13);
				} else if (!bec.g(bki13)) {
					bec.a(bki13, false);
				}

				bqb.a(fu, bvs.ev.n(), 3);
			}

			return ang.a(bqb.v);
		} else {
			return ang.CONSUME;
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	public bvr c() {
		return this.c;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
