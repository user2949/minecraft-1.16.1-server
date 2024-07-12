import java.util.function.Predicate;
import javax.annotation.Nullable;

public class avu<T extends aoz> extends aug {
	private final T a;
	private final bki b;
	private final Predicate<? super T> c;
	private final ack d;

	public avu(T aoz, bki bki, @Nullable ack ack, Predicate<? super T> predicate) {
		this.a = aoz;
		this.b = bki;
		this.d = ack;
		this.c = predicate;
	}

	@Override
	public boolean a() {
		return this.c.test(this.a);
	}

	@Override
	public boolean b() {
		return this.a.dV();
	}

	@Override
	public void c() {
		this.a.a(aor.MAINHAND, this.b.i());
		this.a.c(anf.MAIN_HAND);
	}

	@Override
	public void d() {
		this.a.a(aor.MAINHAND, bki.b);
		if (this.d != null) {
			this.a.a(this.d, 1.0F, this.a.cX().nextFloat() * 0.2F + 0.9F);
		}
	}
}
