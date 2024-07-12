import com.google.common.collect.Maps;
import java.util.Map;

public class buz extends bup {
	public static final cgi a = cfz.aD;
	private static final Map<bje, bvr> b = Maps.<bje, bvr>newHashMap();
	private static final dfg c = bvr.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

	public buz(bje bje, cfi.c c) {
		super(bje, c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
		b.put(bje, this);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.c()).c().b();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return c;
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, Integer.valueOf(aec.c((double)((180.0F + bin.h()) * 16.0F / 360.0F) + 0.5) & 15));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, Integer.valueOf(cap.a((Integer)cfj.c(a), 16)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(a, Integer.valueOf(bzj.a((Integer)cfj.c(a), 16)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(buz.a);
	}
}
