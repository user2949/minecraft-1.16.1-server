public class bxx extends bvx {
	protected static final dfg a = bvr.a(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
	private final aoe b;
	private final int c;

	public bxx(aoe aoe, int integer, cfi.c c) {
		super(c);
		this.b = aoe;
		if (aoe.a()) {
			this.c = integer;
		} else {
			this.c = integer * 20;
		}
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		dem dem6 = cfj.n(bpg, fu);
		return a.a(dem6.b, dem6.c, dem6.d);
	}

	@Override
	public cfi.b aj_() {
		return cfi.b.XZ;
	}

	public aoe c() {
		return this.b;
	}

	public int d() {
		return this.c;
	}
}
