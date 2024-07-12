public class dfj {
	private final dfm a;
	private final String b;
	private final dfp c;
	private mr d;
	private mr e;
	private dfp.a f;

	public dfj(dfm dfm, String string, dfp dfp, mr mr, dfp.a a) {
		this.a = dfm;
		this.b = string;
		this.c = dfp;
		this.d = mr;
		this.e = this.g();
		this.f = a;
	}

	public String b() {
		return this.b;
	}

	public dfp c() {
		return this.c;
	}

	public mr d() {
		return this.d;
	}

	private mr g() {
		return ms.a((mr)this.d.e().a(nb -> nb.a(new mv(mv.a.a, new nd(this.b)))));
	}

	public mr e() {
		return this.e;
	}

	public void a(mr mr) {
		this.d = mr;
		this.e = this.g();
		this.a.b(this);
	}

	public dfp.a f() {
		return this.f;
	}

	public void a(dfp.a a) {
		this.f = a;
		this.a.b(this);
	}
}
