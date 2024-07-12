import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class awc<T extends aoy> extends awj {
	protected final Class<T> a;
	protected final int b;
	protected aoy c;
	protected axs d;

	public awc(aoz aoz, Class<T> class2, boolean boolean3) {
		this(aoz, class2, boolean3, false);
	}

	public awc(aoz aoz, Class<T> class2, boolean boolean3, boolean boolean4) {
		this(aoz, class2, 10, boolean3, boolean4, null);
	}

	public awc(aoz aoz, Class<T> class2, int integer, boolean boolean4, boolean boolean5, @Nullable Predicate<aoy> predicate) {
		super(aoz, boolean4, boolean5);
		this.a = class2;
		this.b = integer;
		this.a(EnumSet.of(aug.a.TARGET));
		this.d = new axs().a(this.k()).a(predicate);
	}

	@Override
	public boolean a() {
		if (this.b > 0 && this.e.cX().nextInt(this.b) != 0) {
			return false;
		} else {
			this.g();
			return this.c != null;
		}
	}

	protected deg a(double double1) {
		return this.e.cb().c(double1, 4.0, double1);
	}

	protected void g() {
		if (this.a != bec.class && this.a != ze.class) {
			this.c = this.e.l.b(this.a, this.d, this.e, this.e.cC(), this.e.cF(), this.e.cG(), this.a(this.k()));
		} else {
			this.c = this.e.l.a(this.d, this.e, this.e.cC(), this.e.cF(), this.e.cG());
		}
	}

	@Override
	public void c() {
		this.e.i(this.c);
		super.c();
	}

	public void a(@Nullable aoy aoy) {
		this.c = aoy;
	}
}
