import java.util.Random;

public abstract class cwz {
	public static final ge<cxa> c = new ge<>();
	protected final cfk<cwz, cxa> d;
	private cxa a;

	protected cwz() {
		cfk.a<cwz, cxa> a2 = new cfk.a<>(this);
		this.a(a2);
		this.d = a2.a(cwz::h, cxa::new);
		this.f(this.d.b());
	}

	protected void a(cfk.a<cwz, cxa> a) {
	}

	public cfk<cwz, cxa> g() {
		return this.d;
	}

	protected final void f(cxa cxa) {
		this.a = cxa;
	}

	public final cxa h() {
		return this.a;
	}

	public abstract bke a();

	protected void a(bqb bqb, fu fu, cxa cxa) {
	}

	protected void b(bqb bqb, fu fu, cxa cxa, Random random) {
	}

	protected abstract boolean a(cxa cxa, bpg bpg, fu fu, cwz cwz, fz fz);

	protected abstract dem a(bpg bpg, fu fu, cxa cxa);

	public abstract int a(bqd bqd);

	protected boolean j() {
		return false;
	}

	protected boolean b() {
		return false;
	}

	protected abstract float c();

	public abstract float a(cxa cxa, bpg bpg, fu fu);

	public abstract float a(cxa cxa);

	protected abstract cfj b(cxa cxa);

	public abstract boolean c(cxa cxa);

	public abstract int d(cxa cxa);

	public boolean a(cwz cwz) {
		return cwz == this;
	}

	public boolean a(adf<cwz> adf) {
		return adf.a(this);
	}

	public abstract dfg b(cxa cxa, bpg bpg, fu fu);
}
