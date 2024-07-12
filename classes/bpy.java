public enum bpy {
	NOT_SET(-1, ""),
	SURVIVAL(0, "survival"),
	CREATIVE(1, "creative"),
	ADVENTURE(2, "adventure"),
	SPECTATOR(3, "spectator");

	private final int f;
	private final String g;

	private bpy(int integer3, String string4) {
		this.f = integer3;
		this.g = string4;
	}

	public int a() {
		return this.f;
	}

	public String b() {
		return this.g;
	}

	public mr c() {
		return new ne("gameMode." + this.g);
	}

	public void a(bdz bdz) {
		if (this == CREATIVE) {
			bdz.c = true;
			bdz.d = true;
			bdz.a = true;
		} else if (this == SPECTATOR) {
			bdz.c = true;
			bdz.d = false;
			bdz.a = true;
			bdz.b = true;
		} else {
			bdz.c = false;
			bdz.d = false;
			bdz.a = false;
			bdz.b = false;
		}

		bdz.e = !this.d();
	}

	public boolean d() {
		return this == ADVENTURE || this == SPECTATOR;
	}

	public boolean e() {
		return this == CREATIVE;
	}

	public boolean f() {
		return this == SURVIVAL || this == ADVENTURE;
	}

	public static bpy a(int integer) {
		return a(integer, SURVIVAL);
	}

	public static bpy a(int integer, bpy bpy) {
		for (bpy bpy6 : values()) {
			if (bpy6.f == integer) {
				return bpy6;
			}
		}

		return bpy;
	}

	public static bpy a(String string) {
		return a(string, SURVIVAL);
	}

	public static bpy a(String string, bpy bpy) {
		for (bpy bpy6 : values()) {
			if (bpy6.g.equals(string)) {
				return bpy6;
			}
		}

		return bpy;
	}
}
