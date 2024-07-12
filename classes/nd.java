import javax.annotation.Nullable;

public class nd extends mn {
	public static final mr d = new nd("");
	private final String e;
	@Nullable
	private kz f;
	private String g;

	public nd(String string) {
		this.e = string;
		this.g = string;
	}

	public String g() {
		return this.e;
	}

	@Override
	public String a() {
		if (this.e.isEmpty()) {
			return this.e;
		} else {
			kz kz2 = kz.a();
			if (this.f != kz2) {
				this.g = kz2.a(this.e, false);
				this.f = kz2;
			}

			return this.g;
		}
	}

	public nd f() {
		return new nd(this.e);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof nd)) {
			return false;
		} else {
			nd nd3 = (nd)object;
			return this.e.equals(nd3.g()) && super.equals(object);
		}
	}

	@Override
	public String toString() {
		return "TextComponent{text='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
	}
}
