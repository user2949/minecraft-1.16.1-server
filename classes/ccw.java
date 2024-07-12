public class ccw extends bxx {
	public ccw(aoe aoe, cfi.c c) {
		super(aoe, 8, c);
	}

	@Override
	protected boolean c(cfj cfj, bpg bpg, fu fu) {
		return super.c(cfj, bpg, fu) || cfj.a(bvs.cL) || cfj.a(bvs.cM) || cfj.a(bvs.cN);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!bqb.v && bqb.ac() != and.PEACEFUL) {
			if (aom instanceof aoy) {
				aoy aoy6 = (aoy)aom;
				if (!aoy6.b(anw.p)) {
					aoy6.c(new aog(aoi.t, 40));
				}
			}
		}
	}
}
