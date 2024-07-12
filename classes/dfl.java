import java.util.Comparator;
import javax.annotation.Nullable;

public class dfl {
	public static final Comparator<dfl> a = (dfl1, dfl2) -> {
		if (dfl1.b() > dfl2.b()) {
			return 1;
		} else {
			return dfl1.b() < dfl2.b() ? -1 : dfl2.e().compareToIgnoreCase(dfl1.e());
		}
	};
	private final dfm b;
	@Nullable
	private final dfj c;
	private final String d;
	private int e;
	private boolean f;
	private boolean g;

	public dfl(dfm dfm, dfj dfj, String string) {
		this.b = dfm;
		this.c = dfj;
		this.d = string;
		this.f = true;
		this.g = true;
	}

	public void a(int integer) {
		if (this.c.c().d()) {
			throw new IllegalStateException("Cannot modify read-only score");
		} else {
			this.c(this.b() + integer);
		}
	}

	public void a() {
		this.a(1);
	}

	public int b() {
		return this.e;
	}

	public void c() {
		this.c(0);
	}

	public void c(int integer) {
		int integer3 = this.e;
		this.e = integer;
		if (integer3 != integer || this.g) {
			this.g = false;
			this.f().a(this);
		}
	}

	@Nullable
	public dfj d() {
		return this.c;
	}

	public String e() {
		return this.d;
	}

	public dfm f() {
		return this.b;
	}

	public boolean g() {
		return this.f;
	}

	public void a(boolean boolean1) {
		this.f = boolean1;
	}
}
