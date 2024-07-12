public class cdr extends cdl {
	private int a;

	public cdr() {
		super(cdm.r);
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.b("OutputSignal", this.a);
		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a = le.h("OutputSignal");
	}

	public int d() {
		return this.a;
	}

	public void a(int integer) {
		this.a = integer;
	}
}
