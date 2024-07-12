import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class avc<T extends bfi> extends aug {
	private final T a;

	public avc(T bfi) {
		this.a = bfi;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		return this.a.A() == null && !this.a.bo() && this.a.fc() && !this.a.fb().a() && !((zd)this.a.l).b_(this.a.cA());
	}

	@Override
	public boolean b() {
		return this.a.fc() && !this.a.fb().a() && this.a.l instanceof zd && !((zd)this.a.l).b_(this.a.cA());
	}

	@Override
	public void e() {
		if (this.a.fc()) {
			bfh bfh2 = this.a.fb();
			if (this.a.K % 20 == 0) {
				this.a(bfh2);
			}

			if (!this.a.eJ()) {
				dem dem3 = axu.b(this.a, 15, 4, dem.c(bfh2.t()));
				if (dem3 != null) {
					this.a.x().a(dem3.b, dem3.c, dem3.d, 1.0);
				}
			}
		}
	}

	private void a(bfh bfh) {
		if (bfh.v()) {
			Set<bfi> set3 = Sets.<bfi>newHashSet();
			List<bfi> list4 = this.a.l.a(bfi.class, this.a.cb().g(16.0), bfi -> !bfi.fc() && bfj.a(bfi, bfh));
			set3.addAll(list4);

			for (bfi bfi6 : set3) {
				bfh.a(bfh.k(), bfi6, null, true);
			}
		}
	}
}
