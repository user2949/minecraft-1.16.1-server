import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class bvf extends bvc {
	public static final cgd a = byp.aq;
	private static final Map<fz, dfg> c = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			bvr.a(0.0, 4.0, 5.0, 16.0, 12.0, 16.0),
			fz.SOUTH,
			bvr.a(0.0, 4.0, 0.0, 16.0, 12.0, 11.0),
			fz.WEST,
			bvr.a(5.0, 4.0, 0.0, 16.0, 12.0, 16.0),
			fz.EAST,
			bvr.a(0.0, 4.0, 0.0, 11.0, 12.0, 16.0)
		)
	);

	protected bvf(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(true)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)c.get(cfj.c(a));
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
		a.a(bvf.a, b);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(b)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return fz.f() == cfj1.c(a) && !cfj1.a(bqc, fu5) ? bvs.a.n() : cfj1;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fz fz5 = cfj.c(a);
		fu fu6 = fu.a(fz5.f());
		cfj cfj7 = bqd.d_(fu6);
		return cfj7.d(bqd, fu6, fz5);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = super.a(bin);
		bqd bqd4 = bin.o();
		fu fu5 = bin.a();
		fz[] arr6 = bin.e();

		for (fz fz10 : arr6) {
			if (fz10.n().d()) {
				cfj3 = cfj3.a(a, fz10.f());
				if (cfj3.a(bqd4, fu5)) {
					return cfj3;
				}
			}
		}

		return null;
	}
}
