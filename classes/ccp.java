import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class ccp extends ccc {
	public static final cgd a = byp.aq;
	private static final Map<fz, dfg> b = Maps.newEnumMap(
		ImmutableMap.of(
			fz.NORTH,
			bvr.a(5.5, 3.0, 11.0, 10.5, 13.0, 16.0),
			fz.SOUTH,
			bvr.a(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
			fz.WEST,
			bvr.a(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
			fz.EAST,
			bvr.a(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)
		)
	);

	protected ccp(cfi.c c, hf hf) {
		super(c, hf);
		this.j(this.n.b().a(a, fz.NORTH));
	}

	@Override
	public String i() {
		return this.h().a();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return h(cfj);
	}

	public static dfg h(cfj cfj) {
		return (dfg)b.get(cfj.c(a));
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
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz.f() == cfj1.c(a) && !cfj1.a(bqc, fu5) ? bvs.a.n() : cfj1;
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
		a.a(ccp.a);
	}
}
