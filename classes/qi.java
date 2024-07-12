import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;

public class qi implements ni<nl> {
	private String a = "";
	@Nullable
	private String b;
	private int c;
	private ux.a d;

	public qi() {
	}

	public qi(ux.a a, @Nullable String string2, String string3, int integer) {
		if (a != ux.a.REMOVE && string2 == null) {
			throw new IllegalArgumentException("Need an objective name");
		} else {
			this.a = string3;
			this.b = string2;
			this.c = integer;
			this.d = a;
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(40);
		this.d = mg.a(ux.a.class);
		String string3 = mg.e(16);
		this.b = Objects.equals(string3, "") ? null : string3;
		if (this.d != ux.a.REMOVE) {
			this.c = mg.i();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.d);
		mg.a(this.b == null ? "" : this.b);
		if (this.d != ux.a.REMOVE) {
			mg.d(this.c);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
