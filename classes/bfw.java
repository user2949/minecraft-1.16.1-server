public class bfw extends bfs {
	public bfw(aoq<? extends bfw> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfw(bqb bqb, double double2, double double3, double double4) {
		super(aoq.U, double2, double3, double4, bqb);
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
		if (this.l.S().b(bpx.g)) {
			this.a(bvs.bR);
		}
	}

	@Override
	public int ab_() {
		return 27;
	}

	@Override
	public bfr.a o() {
		return bfr.a.CHEST;
	}

	@Override
	public cfj q() {
		return bvs.bR.n().a(bwh.b, fz.NORTH);
	}

	@Override
	public int s() {
		return 8;
	}

	@Override
	public bgi a(int integer, beb beb) {
		return bgp.a(integer, beb, this);
	}
}
