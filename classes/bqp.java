public class bqp extends aen.a {
	private final le b;

	public bqp() {
		super(1);
		this.b = new le();
		this.b.a("id", "minecraft:pig");
	}

	public bqp(le le) {
		this(le.c("Weight", 99) ? le.h("Weight") : 1, le.p("Entity"));
	}

	public bqp(int integer, le le) {
		super(integer);
		this.b = le;
		uh uh4 = uh.a(le.l("id"));
		if (uh4 != null) {
			le.a("id", uh4.toString());
		} else {
			le.a("id", "minecraft:pig");
		}
	}

	public le a() {
		le le2 = new le();
		le2.a("Entity", this.b);
		le2.b("Weight", this.a);
		return le2;
	}

	public le b() {
		return this.b;
	}
}
