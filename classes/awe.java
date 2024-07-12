import java.util.function.Predicate;
import javax.annotation.Nullable;

public class awe<T extends aoy> extends awc<T> {
	private int i = 0;

	public awe(bfi bfi, Class<T> class2, boolean boolean3, @Nullable Predicate<aoy> predicate) {
		super(bfi, class2, 500, boolean3, false, predicate);
	}

	public int h() {
		return this.i;
	}

	public void j() {
		this.i--;
	}

	@Override
	public boolean a() {
		if (this.i > 0 || !this.e.cX().nextBoolean()) {
			return false;
		} else if (!((bfi)this.e).fc()) {
			return false;
		} else {
			this.g();
			return this.c != null;
		}
	}

	@Override
	public void c() {
		this.i = 200;
		super.c();
	}
}
