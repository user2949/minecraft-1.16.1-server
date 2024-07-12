public class czt {
	private final fu a;
	private final int b;
	private final int c;

	public czt(fu fu, int integer2, int integer3) {
		this.a = fu;
		this.b = integer2;
		this.c = integer3;
	}

	public static czt a(le le) {
		fu fu2 = lq.b(le.p("Pos"));
		int integer3 = le.h("Rotation");
		int integer4 = le.h("EntityId");
		return new czt(fu2, integer3, integer4);
	}

	public le a() {
		le le2 = new le();
		le2.a("Pos", lq.a(this.a));
		le2.b("Rotation", this.b);
		le2.b("EntityId", this.c);
		return le2;
	}

	public fu b() {
		return this.a;
	}

	public int c() {
		return this.b;
	}

	public int d() {
		return this.c;
	}

	public String e() {
		return a(this.a);
	}

	public static String a(fu fu) {
		return "frame-" + fu.u() + "," + fu.v() + "," + fu.w();
	}
}
