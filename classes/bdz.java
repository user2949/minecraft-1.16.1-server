public class bdz {
	public boolean a;
	public boolean b;
	public boolean c;
	public boolean d;
	public boolean e = true;
	private float f = 0.05F;
	private float g = 0.1F;

	public void a(le le) {
		le le3 = new le();
		le3.a("invulnerable", this.a);
		le3.a("flying", this.b);
		le3.a("mayfly", this.c);
		le3.a("instabuild", this.d);
		le3.a("mayBuild", this.e);
		le3.a("flySpeed", this.f);
		le3.a("walkSpeed", this.g);
		le.a("abilities", le3);
	}

	public void b(le le) {
		if (le.c("abilities", 10)) {
			le le3 = le.p("abilities");
			this.a = le3.q("invulnerable");
			this.b = le3.q("flying");
			this.c = le3.q("mayfly");
			this.d = le3.q("instabuild");
			if (le3.c("flySpeed", 99)) {
				this.f = le3.j("flySpeed");
				this.g = le3.j("walkSpeed");
			}

			if (le3.c("mayBuild", 1)) {
				this.e = le3.q("mayBuild");
			}
		}
	}

	public float a() {
		return this.f;
	}

	public float b() {
		return this.g;
	}
}
