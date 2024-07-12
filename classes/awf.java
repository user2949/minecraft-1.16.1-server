import java.util.function.Predicate;
import javax.annotation.Nullable;

public class awf<T extends aoy> extends awc<T> {
	private final apq i;

	public awf(apq apq, Class<T> class2, boolean boolean3, @Nullable Predicate<aoy> predicate) {
		super(apq, class2, 10, boolean3, false, predicate);
		this.i = apq;
	}

	@Override
	public boolean a() {
		return !this.i.eL() && super.a();
	}

	@Override
	public boolean b() {
		return this.d != null ? this.d.a(this.e, this.c) : super.b();
	}
}
