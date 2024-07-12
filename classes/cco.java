import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class cco extends but {
	public static final cgd a = byp.aq;
	private static final Map<fz, dfg> b = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			bvr.a(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
			fz.SOUTH,
			bvr.a(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
			fz.EAST,
			bvr.a(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
			fz.WEST,
			bvr.a(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
		)
	);

	protected cco(cay.a a, cfi.c c) {
		super(a, c);
		this.j(this.n.b().a(cco.a, fz.NORTH));
	}

	@Override
	public String i() {
		return this.h().a();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)b.get(cfj.c(a));
	}

	@Override
	public cfj a(bin bin) {
		cfj cfj3 = this.n();
		bpg bpg4 = bin.o();
		fu fu5 = bin.a();
		fz[] arr6 = bin.e();

		for (fz fz10 : arr6) {
			if (fz10.n().d()) {
				fz fz11 = fz10.f();
				cfj3 = cfj3.a(a, fz11);
				if (!bpg4.d_(fu5.a(fz10)).a(bin)) {
					return cfj3;
				}
			}
		}

		return null;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cco.a);
	}
}
