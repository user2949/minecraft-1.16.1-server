import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class awb extends awj {
	private static final axs a = new axs().c().e();
	private boolean b;
	private int c;
	private final Class<?>[] d;
	private Class<?>[] i;

	public awb(apg apg, Class<?>... arr) {
		super(apg, true);
		this.d = arr;
		this.a(EnumSet.of(aug.a.TARGET));
	}

	@Override
	public boolean a() {
		int integer2 = this.e.cZ();
		aoy aoy3 = this.e.cY();
		if (integer2 != this.c && aoy3 != null) {
			if (aoy3.U() == aoq.bb && this.e.l.S().b(bpx.G)) {
				return false;
			} else {
				for (Class<?> class7 : this.d) {
					if (class7.isAssignableFrom(aoy3.getClass())) {
						return false;
					}
				}

				return this.a(aoy3, a);
			}
		} else {
			return false;
		}
	}

	public awb a(Class<?>... arr) {
		this.b = true;
		this.i = arr;
		return this;
	}

	@Override
	public void c() {
		this.e.i(this.e.cY());
		this.g = this.e.A();
		this.c = this.e.cZ();
		this.h = 300;
		if (this.b) {
			this.g();
		}

		super.c();
	}

	protected void g() {
		double double2 = this.k();
		deg deg4 = deg.a(this.e.cz()).c(double2, 10.0, double2);
		List<aoz> list5 = this.e.l.b(this.e.getClass(), deg4);
		Iterator var5 = list5.iterator();

		while (true) {
			aoz aoz7;
			while (true) {
				if (!var5.hasNext()) {
					return;
				}

				aoz7 = (aoz)var5.next();
				if (this.e != aoz7 && aoz7.A() == null && (!(this.e instanceof apq) || ((apq)this.e).eO() == ((apq)aoz7).eO()) && !aoz7.r(this.e.cY())) {
					if (this.i == null) {
						break;
					}

					boolean boolean8 = false;

					for (Class<?> class12 : this.i) {
						if (aoz7.getClass() == class12) {
							boolean8 = true;
							break;
						}
					}

					if (!boolean8) {
						break;
					}
				}
			}

			this.a(aoz7, this.e.cY());
		}
	}

	protected void a(aoz aoz, aoy aoy) {
		aoz.i(aoy);
	}
}
