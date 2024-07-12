import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class ccn extends caw {
	public static final cgd c = byp.aq;
	private static final Map<fz, dfg> d = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			bvr.a(0.0, 4.5, 14.0, 16.0, 12.5, 16.0),
			fz.SOUTH,
			bvr.a(0.0, 4.5, 0.0, 16.0, 12.5, 2.0),
			fz.EAST,
			bvr.a(0.0, 4.5, 0.0, 2.0, 12.5, 16.0),
			fz.WEST,
			bvr.a(14.0, 4.5, 0.0, 16.0, 12.5, 16.0)
		)
	);

	public ccn(cfi.c c, cgs cgs) {
		super(c, cgs);
		this.j(this.n.b().a(ccn.c, fz.NORTH).a(a, Boolean.valueOf(false)));
	}

	@Override
	public String i() {
		return this.h().a();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)d.get(cfj.c(c));
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.a(((fz)cfj.c(c)).f())).c().b();
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = this.n();
		cxa cxa4 = bin.o().b(bin.a());
		bqd bqd5 = bin.o();
		fu fu6 = bin.a();
		fz[] arr7 = bin.e();

		for (fz fz11 : arr7) {
			if (fz11.n().d()) {
				fz fz12 = fz11.f();
				cfj3 = cfj3.a(c, fz12);
				if (cfj3.a(bqd5, fu6)) {
					return cfj3.a(a, Boolean.valueOf(cxa4.a() == cxb.c));
				}
			}
		}

		return null;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz.f() == cfj1.c(c) && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(c, cap.a(cfj.c(c)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(c)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(c, ccn.a);
	}
}
