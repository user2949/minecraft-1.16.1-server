import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

public class avo extends aug {
	private final apg a;
	private final int b;
	@Nullable
	private fu c;

	public avo(apg apg, int integer) {
		this.a = apg;
		this.b = integer;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (this.a.bo()) {
			return false;
		} else if (this.a.l.J()) {
			return false;
		} else if (this.a.cX().nextInt(this.b) != 0) {
			return false;
		} else {
			zd zd2 = (zd)this.a.l;
			fu fu3 = this.a.cA();
			if (!zd2.a(fu3, 6)) {
				return false;
			} else {
				dem dem4 = axu.a(this.a, 15, 7, fu -> (double)(-zd2.b(go.a(fu))));
				this.c = dem4 == null ? null : new fu(dem4);
				return this.c != null;
			}
		}
	}

	@Override
	public boolean b() {
		return this.c != null && !this.a.x().m() && this.a.x().h().equals(this.c);
	}

	@Override
	public void e() {
		if (this.c != null) {
			awv awv2 = this.a.x();
			if (awv2.m() && !this.c.a(this.a.cz(), 10.0)) {
				dem dem3 = dem.c(this.c);
				dem dem4 = this.a.cz();
				dem dem5 = dem4.d(dem3);
				dem3 = dem5.a(0.4).e(dem3);
				dem dem6 = dem3.d(dem4).d().a(10.0).e(dem4);
				fu fu7 = new fu(dem6);
				fu7 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, fu7);
				if (!awv2.a((double)fu7.u(), (double)fu7.v(), (double)fu7.w(), 1.0)) {
					this.g();
				}
			}
		}
	}

	private void g() {
		Random random2 = this.a.cX();
		fu fu3 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, this.a.cA().b(-8 + random2.nextInt(16), 0, -8 + random2.nextInt(16)));
		this.a.x().a((double)fu3.u(), (double)fu3.v(), (double)fu3.w(), 1.0);
	}
}
