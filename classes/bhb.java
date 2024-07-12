public class bhb extends bhw {
	private final bec a;
	private int b;

	public bhb(bec bec, amz amz, int integer3, int integer4, int integer5) {
		super(amz, integer3, integer4, integer5);
		this.a = bec;
	}

	@Override
	public boolean a(bki bki) {
		return false;
	}

	@Override
	public bki a(int integer) {
		if (this.f()) {
			this.b = this.b + Math.min(integer, this.e().E());
		}

		return super.a(integer);
	}

	@Override
	public bki a(bec bec, bki bki) {
		this.c(bki);
		super.a(bec, bki);
		return bki;
	}

	@Override
	protected void a(bki bki, int integer) {
		this.b += integer;
		this.c(bki);
	}

	@Override
	protected void c(bki bki) {
		bki.a(this.a.l, this.a, this.b);
		if (!this.a.l.v && this.c instanceof cdb) {
			((cdb)this.c).d(this.a);
		}

		this.b = 0;
	}
}
