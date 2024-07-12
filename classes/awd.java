import java.util.function.Predicate;
import javax.annotation.Nullable;

public class awd<T extends aoy> extends awc<T> {
	private boolean i = true;

	public awd(bfi bfi, Class<T> class2, int integer, boolean boolean4, boolean boolean5, @Nullable Predicate<aoy> predicate) {
		super(bfi, class2, integer, boolean4, boolean5, predicate);
	}

	public void a(boolean boolean1) {
		this.i = boolean1;
	}

	@Override
	public boolean a() {
		return this.i && super.a();
	}
}
