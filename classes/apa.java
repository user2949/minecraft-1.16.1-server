import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum apa implements aeh {
	MONSTER("monster", 70, false, false, 128),
	CREATURE("creature", 10, true, true, 128),
	AMBIENT("ambient", 15, true, false, 128),
	WATER_CREATURE("water_creature", 5, true, false, 128),
	WATER_AMBIENT("water_ambient", 20, true, false, 64),
	MISC("misc", -1, true, true, 128);

	public static final Codec<apa> g = aeh.a(apa::values, apa::a);
	private static final Map<String, apa> h = (Map<String, apa>)Arrays.stream(values()).collect(Collectors.toMap(apa::b, apa -> apa));
	private final int i;
	private final boolean j;
	private final boolean k;
	private final String l;
	private final int m = 32;
	private final int n;

	private apa(String string3, int integer4, boolean boolean5, boolean boolean6, int integer7) {
		this.l = string3;
		this.i = integer4;
		this.j = boolean5;
		this.k = boolean6;
		this.n = integer7;
	}

	public String b() {
		return this.l;
	}

	@Override
	public String a() {
		return this.l;
	}

	public static apa a(String string) {
		return (apa)h.get(string);
	}

	public int c() {
		return this.i;
	}

	public boolean d() {
		return this.j;
	}

	public boolean e() {
		return this.k;
	}

	public int f() {
		return this.n;
	}

	public int g() {
		return 32;
	}
}
