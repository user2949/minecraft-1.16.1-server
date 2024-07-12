import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public enum bje implements aeh {
	WHITE(0, "white", 16383998, cxe.j, 15790320, 16777215),
	ORANGE(1, "orange", 16351261, cxe.q, 15435844, 16738335),
	MAGENTA(2, "magenta", 13061821, cxe.r, 12801229, 16711935),
	LIGHT_BLUE(3, "light_blue", 3847130, cxe.s, 6719955, 10141901),
	YELLOW(4, "yellow", 16701501, cxe.t, 14602026, 16776960),
	LIME(5, "lime", 8439583, cxe.u, 4312372, 12582656),
	PINK(6, "pink", 15961002, cxe.v, 14188952, 16738740),
	GRAY(7, "gray", 4673362, cxe.w, 4408131, 8421504),
	LIGHT_GRAY(8, "light_gray", 10329495, cxe.x, 11250603, 13882323),
	CYAN(9, "cyan", 1481884, cxe.y, 2651799, 65535),
	PURPLE(10, "purple", 8991416, cxe.z, 8073150, 10494192),
	BLUE(11, "blue", 3949738, cxe.A, 2437522, 255),
	BROWN(12, "brown", 8606770, cxe.B, 5320730, 9127187),
	GREEN(13, "green", 6192150, cxe.C, 3887386, 65280),
	RED(14, "red", 11546150, cxe.D, 11743532, 16711680),
	BLACK(15, "black", 1908001, cxe.E, 1973019, 0);

	private static final bje[] q = (bje[])Arrays.stream(values()).sorted(Comparator.comparingInt(bje::b)).toArray(bje[]::new);
	private static final Int2ObjectOpenHashMap<bje> r = new Int2ObjectOpenHashMap<>(
		(Map<? extends Integer, ? extends bje>)Arrays.stream(values()).collect(Collectors.toMap(bje -> bje.y, bje -> bje))
	);
	private final int s;
	private final String t;
	private final cxe u;
	private final int v;
	private final int w;
	private final float[] x;
	private final int y;
	private final int z;

	private bje(int integer3, String string4, int integer5, cxe cxe, int integer7, int integer8) {
		this.s = integer3;
		this.t = string4;
		this.v = integer5;
		this.u = cxe;
		this.z = integer8;
		int integer10 = (integer5 & 0xFF0000) >> 16;
		int integer11 = (integer5 & 0xFF00) >> 8;
		int integer12 = (integer5 & 0xFF) >> 0;
		this.w = integer12 << 16 | integer11 << 8 | integer10 << 0;
		this.x = new float[]{(float)integer10 / 255.0F, (float)integer11 / 255.0F, (float)integer12 / 255.0F};
		this.y = integer7;
	}

	public int b() {
		return this.s;
	}

	public String c() {
		return this.t;
	}

	public float[] e() {
		return this.x;
	}

	public cxe f() {
		return this.u;
	}

	public int g() {
		return this.y;
	}

	public static bje a(int integer) {
		if (integer < 0 || integer >= q.length) {
			integer = 0;
		}

		return q[integer];
	}

	public static bje a(String string, bje bje) {
		for (bje bje6 : values()) {
			if (bje6.t.equals(string)) {
				return bje6;
			}
		}

		return bje;
	}

	public String toString() {
		return this.t;
	}

	@Override
	public String a() {
		return this.t;
	}
}
