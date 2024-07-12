import java.util.Random;
import java.util.Set;

public abstract class axn<E extends aoy> {
	private static final Random a = new Random();
	private final int b;
	private long c;

	public axn(int integer) {
		this.b = integer;
		this.c = (long)a.nextInt(integer);
	}

	public axn() {
		this(20);
	}

	public final void b(zd zd, E aoy) {
		if (--this.c <= 0L) {
			this.c = (long)this.b;
			this.a(zd, aoy);
		}
	}

	protected abstract void a(zd zd, E aoy);

	public abstract Set<awp<?>> a();
}
