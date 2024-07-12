public class cay extends but {
	public static final cgi a = cfz.aD;
	protected static final dfg b = bvr.a(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

	protected cay(cay.a a, cfi.c c) {
		super(a, c);
		this.j(this.n.b().a(cay.a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public dfg d(cfj cfj, bpg bpg, fu fu) {
		return dfd.a();
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, Integer.valueOf(aec.c((double)(bin.h() * 16.0F / 360.0F) + 0.5) & 15));
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
		a.a(cay.a);
	}

	public interface a {
	}

	public static enum b implements cay.a {
		SKELETON,
		WITHER_SKELETON,
		PLAYER,
		ZOMBIE,
		CREEPER,
		DRAGON;
	}
}
