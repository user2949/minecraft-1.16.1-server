import java.util.EnumSet;
import java.util.function.Predicate;

public class aty extends aug {
	private static final Predicate<cfj> a = cft.a(bvs.aR);
	private final aoz b;
	private final bqb c;
	private int d;

	public aty(aoz aoz) {
		this.b = aoz;
		this.c = aoz.l;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK, aug.a.JUMP));
	}

	@Override
	public boolean a() {
		if (this.b.cX().nextInt(this.b.x_() ? 50 : 1000) != 0) {
			return false;
		} else {
			fu fu2 = this.b.cA();
			return a.test(this.c.d_(fu2)) ? true : this.c.d_(fu2.c()).a(bvs.i);
		}
	}

	@Override
	public void c() {
		this.d = 40;
		this.c.a(this.b, (byte)10);
		this.b.x().o();
	}

	@Override
	public void d() {
		this.d = 0;
	}

	@Override
	public boolean b() {
		return this.d > 0;
	}

	public int g() {
		return this.d;
	}

	@Override
	public void e() {
		this.d = Math.max(0, this.d - 1);
		if (this.d == 4) {
			fu fu2 = this.b.cA();
			if (a.test(this.c.d_(fu2))) {
				if (this.c.S().b(bpx.b)) {
					this.c.b(fu2, false);
				}

				this.b.B();
			} else {
				fu fu3 = fu2.c();
				if (this.c.d_(fu3).a(bvs.i)) {
					if (this.c.S().b(bpx.b)) {
						this.c.c(2001, fu3, bvr.i(bvs.i.n()));
						this.c.a(fu3, bvs.j.n(), 2);
					}

					this.b.B();
				}
			}
		}
	}
}
