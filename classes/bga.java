public class bga extends bfr {
	private final bpd b = new bpd() {
		@Override
		public void a(int integer) {
			bga.this.l.a(bga.this, (byte)integer);
		}

		@Override
		public bqb a() {
			return bga.this.l;
		}

		@Override
		public fu b() {
			return bga.this.cA();
		}
	};

	public bga(aoq<? extends bga> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bga(bqb bqb, double double2, double double3, double double4) {
		super(aoq.Y, bqb, double2, double3, double4);
	}

	@Override
	public bfr.a o() {
		return bfr.a.SPAWNER;
	}

	@Override
	public cfj q() {
		return bvs.bP.n();
	}

	@Override
	protected void a(le le) {
		super.a(le);
		this.b.a(le);
	}

	@Override
	protected void b(le le) {
		super.b(le);
		this.b.b(le);
	}

	@Override
	public void j() {
		super.j();
		this.b.c();
	}

	@Override
	public boolean ci() {
		return true;
	}
}
