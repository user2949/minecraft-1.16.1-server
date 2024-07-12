import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bag extends bae {
	private static final Logger b = LogManager.getLogger();
	private dem c;
	private int d;

	public bag(bac bac) {
		super(bac);
	}

	@Override
	public void c() {
		if (this.c == null) {
			b.warn("Aborting charge player as no target was set.");
			this.a.eL().a(bas.a);
		} else if (this.d > 0 && this.d++ >= 10) {
			this.a.eL().a(bas.a);
		} else {
			double double2 = this.c.c(this.a.cC(), this.a.cD(), this.a.cG());
			if (double2 < 100.0 || double2 > 22500.0 || this.a.u || this.a.v) {
				this.d++;
			}
		}
	}

	@Override
	public void d() {
		this.c = null;
		this.d = 0;
	}

	public void a(dem dem) {
		this.c = dem;
	}

	@Override
	public float f() {
		return 3.0F;
	}

	@Nullable
	@Override
	public dem g() {
		return this.c;
	}

	@Override
	public bas<bag> i() {
		return bas.i;
	}
}
