import java.util.List;

public class awi<T extends aoz & ape> extends aug {
	private final T a;
	private final boolean b;
	private int c;

	public awi(T aoz, boolean boolean2) {
		this.a = aoz;
		this.b = boolean2;
	}

	@Override
	public boolean a() {
		return this.a.l.S().b(bpx.G) && this.g();
	}

	private boolean g() {
		return this.a.cY() != null && this.a.cY().U() == aoq.bb && this.a.cZ() > this.c;
	}

	@Override
	public void c() {
		this.c = this.a.cZ();
		this.a.J_();
		if (this.b) {
			this.h().stream().filter(aoz -> aoz != this.a).map(aoz -> (ape)aoz).forEach(ape::J_);
		}

		super.c();
	}

	private List<aoz> h() {
		double double2 = this.a.b(apx.b);
		deg deg4 = deg.a(this.a.cz()).c(double2, 10.0, double2);
		return this.a.l.b(this.a.getClass(), deg4);
	}
}
