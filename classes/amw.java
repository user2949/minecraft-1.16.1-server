import java.util.UUID;

public abstract class amw {
	private final UUID h;
	protected mr a;
	protected float b;
	protected amw.a c;
	protected amw.b d;
	protected boolean e;
	protected boolean f;
	protected boolean g;

	public amw(UUID uUID, mr mr, amw.a a, amw.b b) {
		this.h = uUID;
		this.a = mr;
		this.c = a;
		this.d = b;
		this.b = 1.0F;
	}

	public UUID i() {
		return this.h;
	}

	public mr j() {
		return this.a;
	}

	public void a(mr mr) {
		this.a = mr;
	}

	public float k() {
		return this.b;
	}

	public void a(float float1) {
		this.b = float1;
	}

	public amw.a l() {
		return this.c;
	}

	public void a(amw.a a) {
		this.c = a;
	}

	public amw.b m() {
		return this.d;
	}

	public void a(amw.b b) {
		this.d = b;
	}

	public boolean n() {
		return this.e;
	}

	public amw a(boolean boolean1) {
		this.e = boolean1;
		return this;
	}

	public boolean o() {
		return this.f;
	}

	public amw b(boolean boolean1) {
		this.f = boolean1;
		return this;
	}

	public amw c(boolean boolean1) {
		this.g = boolean1;
		return this;
	}

	public boolean p() {
		return this.g;
	}

	public static enum a {
		PINK("pink", i.RED),
		BLUE("blue", i.BLUE),
		RED("red", i.DARK_RED),
		GREEN("green", i.GREEN),
		YELLOW("yellow", i.YELLOW),
		PURPLE("purple", i.DARK_BLUE),
		WHITE("white", i.WHITE);

		private final String h;
		private final i i;

		private a(String string3, i i) {
			this.h = string3;
			this.i = i;
		}

		public i a() {
			return this.i;
		}

		public String b() {
			return this.h;
		}

		public static amw.a a(String string) {
			for (amw.a a5 : values()) {
				if (a5.h.equals(string)) {
					return a5;
				}
			}

			return WHITE;
		}
	}

	public static enum b {
		PROGRESS("progress"),
		NOTCHED_6("notched_6"),
		NOTCHED_10("notched_10"),
		NOTCHED_12("notched_12"),
		NOTCHED_20("notched_20");

		private final String f;

		private b(String string3) {
			this.f = string3;
		}

		public String a() {
			return this.f;
		}

		public static amw.b a(String string) {
			for (amw.b b5 : values()) {
				if (b5.f.equals(string)) {
					return b5;
				}
			}

			return PROGRESS;
		}
	}
}
