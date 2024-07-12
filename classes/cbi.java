public class cbi extends bvg {
	protected cbi(cfi.c c) {
		super(c);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cek();
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bki bki) {
		super.a(cfj, bqb, fu, bki);
		int integer6 = 15 + bqb.t.nextInt(15) + bqb.t.nextInt(15);
		this.a(bqb, fu, integer6);
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}
}
