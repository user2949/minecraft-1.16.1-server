public class bfl {
	public static final bfl a = a("core");
	public static final bfl b = a("idle");
	public static final bfl c = a("work");
	public static final bfl d = a("play");
	public static final bfl e = a("rest");
	public static final bfl f = a("meet");
	public static final bfl g = a("panic");
	public static final bfl h = a("raid");
	public static final bfl i = a("pre_raid");
	public static final bfl j = a("hide");
	public static final bfl k = a("fight");
	public static final bfl l = a("celebrate");
	public static final bfl m = a("admire_item");
	public static final bfl n = a("avoid");
	public static final bfl o = a("ride");
	private final String p;
	private final int q;

	private bfl(String string) {
		this.p = string;
		this.q = string.hashCode();
	}

	public String a() {
		return this.p;
	}

	private static bfl a(String string) {
		return gl.a(gl.aX, string, new bfl(string));
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			bfl bfl3 = (bfl)object;
			return this.p.equals(bfl3.p);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.q;
	}

	public String toString() {
		return this.a();
	}
}
