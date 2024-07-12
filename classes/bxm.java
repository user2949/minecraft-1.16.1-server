import com.google.common.base.Predicates;

public class bxm extends bvr {
	public static final cgd a = byp.aq;
	public static final cga b = cfz.h;
	protected static final dfg c = bvr.a(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
	protected static final dfg d = bvr.a(4.0, 13.0, 4.0, 12.0, 16.0, 12.0);
	protected static final dfg e = dfd.a(c, d);
	private static cfo f;

	public bxm(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return cfj.c(b) ? e : c;
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.f().f()).a(b, Boolean.valueOf(false));
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return cfj.c(b) ? 15 : 0;
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
		a.a(bxm.a, b);
	}

	public static cfo c() {
		if (f == null) {
			f = cfp.a()
				.a("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
				.a('?', cfn.a(cft.a))
				.a('^', cfn.a(cft.a(bvs.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(fz.SOUTH))))
				.a('>', cfn.a(cft.a(bvs.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(fz.WEST))))
				.a('v', cfn.a(cft.a(bvs.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(fz.NORTH))))
				.a('<', cfn.a(cft.a(bvs.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(fz.EAST))))
				.b();
		}

		return f;
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
