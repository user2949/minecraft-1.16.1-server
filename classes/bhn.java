public class bhn extends bhw {
	private final bhl a;
	private final bec b;
	private int g;
	private final boy h;

	public bhn(bec bec, boy boy, bhl bhl, int integer4, int integer5, int integer6) {
		super(bhl, integer4, integer5, integer6);
		this.b = bec;
		this.h = boy;
		this.a = bhl;
	}

	@Override
	public boolean a(bki bki) {
		return false;
	}

	@Override
	public bki a(int integer) {
		if (this.f()) {
			this.g = this.g + Math.min(integer, this.e().E());
		}

		return super.a(integer);
	}

	@Override
	protected void a(bki bki, int integer) {
		this.g += integer;
		this.c(bki);
	}

	@Override
	protected void c(bki bki) {
		bki.a(this.b.l, this.b, this.g);
		this.g = 0;
	}

	@Override
	public bki a(bec bec, bki bki) {
		this.c(bki);
		boz boz4 = this.a.g();
		if (boz4 != null) {
			bki bki5 = this.a.a(0);
			bki bki6 = this.a.a(1);
			if (boz4.b(bki5, bki6) || boz4.b(bki6, bki5)) {
				this.h.a(boz4);
				bec.a(acu.S);
				this.a.a(0, bki5);
				this.a.a(1, bki6);
			}

			this.h.t(this.h.eM() + boz4.o());
		}

		return bki;
	}
}
