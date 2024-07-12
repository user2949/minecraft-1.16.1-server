public class bzy extends bwv {
	private static final dfg[] a = new dfg[]{
		bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
		bvr.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
	};

	public bzy(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a[cfj.c(this.c())];
	}
}
