import com.mojang.serialization.Dynamic;

public final class bqe {
	private final String a;
	private final bpy b;
	private final boolean c;
	private final and d;
	private final boolean e;
	private final bpx f;
	private final bpn g;

	public bqe(String string, bpy bpy, boolean boolean3, and and, boolean boolean5, bpx bpx, bpn bpn) {
		this.a = string;
		this.b = bpy;
		this.c = boolean3;
		this.d = and;
		this.e = boolean5;
		this.f = bpx;
		this.g = bpn;
	}

	public static bqe a(Dynamic<?> dynamic, bpn bpn) {
		bpy bpy3 = bpy.a(dynamic.get("GameType").asInt(0));
		return new bqe(
			dynamic.get("LevelName").asString(""),
			bpy3,
			dynamic.get("hardcore").asBoolean(false),
			(and)dynamic.get("Difficulty").asNumber().map(number -> and.a(number.byteValue())).result().orElse(and.NORMAL),
			dynamic.get("allowCommands").asBoolean(bpy3 == bpy.CREATIVE),
			new bpx(dynamic.get("GameRules")),
			bpn
		);
	}

	public String a() {
		return this.a;
	}

	public bpy b() {
		return this.b;
	}

	public boolean c() {
		return this.c;
	}

	public and d() {
		return this.d;
	}

	public boolean e() {
		return this.e;
	}

	public bpx f() {
		return this.f;
	}

	public bpn g() {
		return this.g;
	}

	public bqe a(bpy bpy) {
		return new bqe(this.a, bpy, this.c, this.d, this.e, this.f, this.g);
	}

	public bqe a(and and) {
		return new bqe(this.a, this.b, this.c, and, this.e, this.f, this.g);
	}

	public bqe a(bpn bpn) {
		return new bqe(this.a, this.b, this.c, this.d, this.e, this.f, bpn);
	}

	public bqe h() {
		return new bqe(this.a, this.b, this.c, this.d, this.e, this.f.b(), this.g);
	}
}
