import javax.annotation.Nullable;

public class caj extends cai {
	public static final cgd b = byp.aq;
	public static final cga c = cai.a;

	protected caj(cfi.c c) {
		super(c);
		this.j(this.n.b().a(b, fz.NORTH).a(caj.c, Boolean.valueOf(true)));
	}

	@Override
	public String i() {
		return this.h().a();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return ccp.h(cfj);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bvs.bM.a(cfj, bqd, fu);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return bvs.bM.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = bvs.bM.a(bin);
		return cfj3 == null ? null : this.n().a(b, cfj3.c(b));
	}

	@Override
	protected boolean a(bqb bqb, fu fu, cfj cfj) {
		fz fz5 = ((fz)cfj.c(b)).f();
		return bqb.a(fu.a(fz5), fz5);
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(c) && cfj.c(b) != fz ? 15 : 0;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return bvs.bM.a(cfj, cap);
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return bvs.bM.a(cfj, bzj);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b, c);
	}
}
