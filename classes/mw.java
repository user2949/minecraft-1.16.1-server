import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class mw extends mn {
	private static Function<String, Supplier<mr>> d = string -> () -> new nd(string);
	private final String e;
	private Supplier<mr> f;

	public mw(String string) {
		this.e = string;
	}

	private mr i() {
		if (this.f == null) {
			this.f = (Supplier<mr>)d.apply(this.e);
		}

		return (mr)this.f.get();
	}

	@Override
	public <T> Optional<T> b(mu.a<T> a) {
		return this.i().a(a);
	}

	public mw f() {
		return new mw(this.e);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof mw)) {
			return false;
		} else {
			mw mw3 = (mw)object;
			return this.e.equals(mw3.e) && super.equals(object);
		}
	}

	@Override
	public String toString() {
		return "KeybindComponent{keybind='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
	}

	public String h() {
		return this.e;
	}
}
