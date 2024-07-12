import javax.annotation.concurrent.Immutable;

@Immutable
public class ani {
	public static final ani a = new ani("");
	private final String b;

	public ani(String string) {
		this.b = string;
	}

	public boolean a(bki bki) {
		return this.b.isEmpty() || !bki.a() && bki.t() && this.b.equals(bki.r().getString());
	}

	public void a(le le) {
		if (!this.b.isEmpty()) {
			le.a("Lock", this.b);
		}
	}

	public static ani b(le le) {
		return le.c("Lock", 8) ? new ani(le.l("Lock")) : a;
	}
}
