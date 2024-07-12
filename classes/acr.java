import java.util.Objects;
import javax.annotation.Nullable;

public class acr<T> extends dfp {
	private final acs o;
	private final T p;
	private final act<T> q;

	protected acr(act<T> act, T object, acs acs) {
		super(a(act, object));
		this.q = act;
		this.o = acs;
		this.p = object;
	}

	public static <T> String a(act<T> act, T object) {
		return a(gl.aQ.b(act)) + ":" + a(act.a().b(object));
	}

	private static <T> String a(@Nullable uh uh) {
		return uh.toString().replace(':', '.');
	}

	public act<T> a() {
		return this.q;
	}

	public T b() {
		return this.p;
	}

	public boolean equals(Object object) {
		return this == object || object instanceof acr && Objects.equals(this.c(), ((acr)object).c());
	}

	public int hashCode() {
		return this.c().hashCode();
	}

	public String toString() {
		return "Stat{name=" + this.c() + ", formatter=" + this.o + '}';
	}
}
