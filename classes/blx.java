public class blx extends bim {
	public blx(bvr bvr, bke.a a) {
		super(bvr, a);
	}

	@Override
	public ang a(blv blv) {
		return ang.PASS;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		deh deh5 = a(bqb, bec, bpj.b.SOURCE_ONLY);
		deh deh6 = deh5.a(deh5.a().b());
		ang ang7 = super.a(new blv(bec, anf, deh6));
		return new anh<>(ang7, bec.b(anf));
	}
}
