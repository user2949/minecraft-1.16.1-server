public class bbc {
	public static final bbc a = a("kebab", 16, 16);
	public static final bbc b = a("aztec", 16, 16);
	public static final bbc c = a("alban", 16, 16);
	public static final bbc d = a("aztec2", 16, 16);
	public static final bbc e = a("bomb", 16, 16);
	public static final bbc f = a("plant", 16, 16);
	public static final bbc g = a("wasteland", 16, 16);
	public static final bbc h = a("pool", 32, 16);
	public static final bbc i = a("courbet", 32, 16);
	public static final bbc j = a("sea", 32, 16);
	public static final bbc k = a("sunset", 32, 16);
	public static final bbc l = a("creebet", 32, 16);
	public static final bbc m = a("wanderer", 16, 32);
	public static final bbc n = a("graham", 16, 32);
	public static final bbc o = a("match", 32, 32);
	public static final bbc p = a("bust", 32, 32);
	public static final bbc q = a("stage", 32, 32);
	public static final bbc r = a("void", 32, 32);
	public static final bbc s = a("skull_and_roses", 32, 32);
	public static final bbc t = a("wither", 32, 32);
	public static final bbc u = a("fighters", 64, 32);
	public static final bbc v = a("pointer", 64, 64);
	public static final bbc w = a("pigscene", 64, 64);
	public static final bbc x = a("burning_skull", 64, 64);
	public static final bbc y = a("skeleton", 64, 48);
	public static final bbc z = a("donkey_kong", 64, 48);
	private final int A;
	private final int B;

	private static bbc a(String string, int integer2, int integer3) {
		return gl.a(gl.aD, string, new bbc(integer2, integer3));
	}

	public bbc(int integer1, int integer2) {
		this.A = integer1;
		this.B = integer2;
	}

	public int a() {
		return this.A;
	}

	public int b() {
		return this.B;
	}
}
