import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class buw extends bvx {
	public static final cgd a = byp.aq;
	private final cbq b;
	private static final Map<fz, dfg> c = Maps.newEnumMap(
		ImmutableMap.of(
			fz.SOUTH,
			bvr.a(6.0, 0.0, 6.0, 10.0, 10.0, 16.0),
			fz.WEST,
			bvr.a(0.0, 0.0, 6.0, 10.0, 10.0, 10.0),
			fz.NORTH,
			bvr.a(6.0, 0.0, 0.0, 10.0, 10.0, 10.0),
			fz.EAST,
			bvr.a(6.0, 0.0, 6.0, 16.0, 10.0, 10.0)
		)
	);

	protected buw(cbq cbq, cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH));
		this.b = cbq;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (dfg)c.get(cfj.c(a));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return !cfj3.a(this.b) && fz == cfj1.c(a) ? this.b.c().n().a(cbp.a, Integer.valueOf(7)) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return cfj.a(bvs.bX);
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
		a.a(buw.a);
	}
}
