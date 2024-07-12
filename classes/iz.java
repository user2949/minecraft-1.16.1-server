import javax.annotation.Nullable;

public final class iz {
	public static final iz a = a("all");
	public static final iz b = a("texture", a);
	public static final iz c = a("particle", b);
	public static final iz d = a("end", a);
	public static final iz e = a("bottom", d);
	public static final iz f = a("top", d);
	public static final iz g = a("front", a);
	public static final iz h = a("back", a);
	public static final iz i = a("side", a);
	public static final iz j = a("north", i);
	public static final iz k = a("south", i);
	public static final iz l = a("east", i);
	public static final iz m = a("west", i);
	public static final iz n = a("up");
	public static final iz o = a("down");
	public static final iz p = a("cross");
	public static final iz q = a("plant");
	public static final iz r = a("wall", a);
	public static final iz s = a("rail");
	public static final iz t = a("wool");
	public static final iz u = a("pattern");
	public static final iz v = a("pane");
	public static final iz w = a("edge");
	public static final iz x = a("fan");
	public static final iz y = a("stem");
	public static final iz z = a("upperstem");
	public static final iz A = a("crop");
	public static final iz B = a("dirt");
	public static final iz C = a("fire");
	public static final iz D = a("lantern");
	public static final iz E = a("platform");
	public static final iz F = a("unsticky");
	public static final iz G = a("torch");
	public static final iz H = a("layer0");
	public static final iz I = a("lit_log");
	private final String J;
	@Nullable
	private final iz K;

	private static iz a(String string) {
		return new iz(string, null);
	}

	private static iz a(String string, iz iz) {
		return new iz(string, iz);
	}

	private iz(String string, @Nullable iz iz) {
		this.J = string;
		this.K = iz;
	}

	public String a() {
		return this.J;
	}

	@Nullable
	public iz b() {
		return this.K;
	}

	public String toString() {
		return "#" + this.J;
	}
}
