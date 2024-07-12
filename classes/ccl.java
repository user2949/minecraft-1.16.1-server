import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class ccl extends bup {
	public static final cgd a = byp.aq;
	private static final Map<fz, dfg> b = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			bvr.a(0.0, 0.0, 14.0, 16.0, 12.5, 16.0),
			fz.SOUTH,
			bvr.a(0.0, 0.0, 0.0, 16.0, 12.5, 2.0),
			fz.WEST,
			bvr.a(14.0, 0.0, 0.0, 16.0, 12.5, 16.0),
			fz.EAST,
			bvr.a(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)
		)
	);

	public ccl(bje bje, cfi.c c) {
		super(bje, c);
		this.j(this.n.b().a(a, fz.NORTH));
	}

	@Override
	public String i() {
		return this.h().a();
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.a(((fz)cfj.c(a)).f())).c().b();
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == ((fz)cfj1.c(a)).f() && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)b.get(cfj.c(a));
	}

	@Override
	public cfj a(bin bin) {
		cfj cfj3 = this.n();
		bqd bqd4 = bin.o();
		fu fu5 = bin.a();
		fz[] arr6 = bin.e();

		for (fz fz10 : arr6) {
			if (fz10.n().d()) {
				fz fz11 = fz10.f();
				cfj3 = cfj3.a(a, fz11);
				if (cfj3.a(bqd4, fu5)) {
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
		a.a(ccl.a);
	}
}
