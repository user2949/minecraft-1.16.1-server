import javax.annotation.Nullable;

public class adq<T extends Throwable> {
	@Nullable
	private T a;

	public void a(T throwable) {
		if (this.a == null) {
			this.a = throwable;
		} else {
			this.a.addSuppressed(throwable);
		}
	}

	public void a() throws T {
		if (this.a != null) {
			throw this.a;
		}
	}
}
