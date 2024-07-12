import java.util.function.Predicate;
import javax.annotation.Nullable;

public class axs {
	public static final axs a = new axs();
	private double b = -1.0;
	private boolean c;
	private boolean d;
	private boolean e;
	private boolean f;
	private boolean g = true;
	private Predicate<aoy> h;

	public axs a(double double1) {
		this.b = double1;
		return this;
	}

	public axs a() {
		this.c = true;
		return this;
	}

	public axs b() {
		this.d = true;
		return this;
	}

	public axs c() {
		this.e = true;
		return this;
	}

	public axs d() {
		this.f = true;
		return this;
	}

	public axs e() {
		this.g = false;
		return this;
	}

	public axs a(@Nullable Predicate<aoy> predicate) {
		this.h = predicate;
		return this;
	}

	public boolean a(@Nullable aoy aoy1, aoy aoy2) {
		if (aoy1 == aoy2) {
			return false;
		} else if (aoy2.a_()) {
			return false;
		} else if (!aoy2.aU()) {
			return false;
		} else if (!this.c && aoy2.bI()) {
			return false;
		} else if (this.h != null && !this.h.test(aoy2)) {
			return false;
		} else {
			if (aoy1 != null) {
				if (!this.f) {
					if (!aoy1.d(aoy2)) {
						return false;
					}

					if (!aoy1.a(aoy2.U())) {
						return false;
					}
				}

				if (!this.d && aoy1.r(aoy2)) {
					return false;
				}

				if (this.b > 0.0) {
					double double4 = this.g ? aoy2.A(aoy1) : 1.0;
					double double6 = this.b * double4;
					double double8 = aoy1.g(aoy2.cC(), aoy2.cD(), aoy2.cG());
					if (double8 > double6 * double6) {
						return false;
					}
				}

				if (!this.e && aoy1 instanceof aoz && !((aoz)aoy1).z().a(aoy2)) {
					return false;
				}
			}

			return true;
		}
	}
}
