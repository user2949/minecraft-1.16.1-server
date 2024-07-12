import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;

public class dfp {
	public static final Map<String, dfp> a = Maps.<String, dfp>newHashMap();
	public static final dfp b = new dfp("dummy");
	public static final dfp c = new dfp("trigger");
	public static final dfp d = new dfp("deathCount");
	public static final dfp e = new dfp("playerKillCount");
	public static final dfp f = new dfp("totalKillCount");
	public static final dfp g = new dfp("health", true, dfp.a.HEARTS);
	public static final dfp h = new dfp("food", true, dfp.a.INTEGER);
	public static final dfp i = new dfp("air", true, dfp.a.INTEGER);
	public static final dfp j = new dfp("armor", true, dfp.a.INTEGER);
	public static final dfp k = new dfp("xp", true, dfp.a.INTEGER);
	public static final dfp l = new dfp("level", true, dfp.a.INTEGER);
	public static final dfp[] m = new dfp[]{
		new dfp("teamkill." + i.BLACK.f()),
		new dfp("teamkill." + i.DARK_BLUE.f()),
		new dfp("teamkill." + i.DARK_GREEN.f()),
		new dfp("teamkill." + i.DARK_AQUA.f()),
		new dfp("teamkill." + i.DARK_RED.f()),
		new dfp("teamkill." + i.DARK_PURPLE.f()),
		new dfp("teamkill." + i.GOLD.f()),
		new dfp("teamkill." + i.GRAY.f()),
		new dfp("teamkill." + i.DARK_GRAY.f()),
		new dfp("teamkill." + i.BLUE.f()),
		new dfp("teamkill." + i.GREEN.f()),
		new dfp("teamkill." + i.AQUA.f()),
		new dfp("teamkill." + i.RED.f()),
		new dfp("teamkill." + i.LIGHT_PURPLE.f()),
		new dfp("teamkill." + i.YELLOW.f()),
		new dfp("teamkill." + i.WHITE.f())
	};
	public static final dfp[] n = new dfp[]{
		new dfp("killedByTeam." + i.BLACK.f()),
		new dfp("killedByTeam." + i.DARK_BLUE.f()),
		new dfp("killedByTeam." + i.DARK_GREEN.f()),
		new dfp("killedByTeam." + i.DARK_AQUA.f()),
		new dfp("killedByTeam." + i.DARK_RED.f()),
		new dfp("killedByTeam." + i.DARK_PURPLE.f()),
		new dfp("killedByTeam." + i.GOLD.f()),
		new dfp("killedByTeam." + i.GRAY.f()),
		new dfp("killedByTeam." + i.DARK_GRAY.f()),
		new dfp("killedByTeam." + i.BLUE.f()),
		new dfp("killedByTeam." + i.GREEN.f()),
		new dfp("killedByTeam." + i.AQUA.f()),
		new dfp("killedByTeam." + i.RED.f()),
		new dfp("killedByTeam." + i.LIGHT_PURPLE.f()),
		new dfp("killedByTeam." + i.YELLOW.f()),
		new dfp("killedByTeam." + i.WHITE.f())
	};
	private final String o;
	private final boolean p;
	private final dfp.a q;

	public dfp(String string) {
		this(string, false, dfp.a.INTEGER);
	}

	protected dfp(String string, boolean boolean2, dfp.a a) {
		this.o = string;
		this.p = boolean2;
		this.q = a;
		dfp.a.put(string, this);
	}

	public static Optional<dfp> a(String string) {
		if (a.containsKey(string)) {
			return Optional.of(a.get(string));
		} else {
			int integer2 = string.indexOf(58);
			return integer2 < 0 ? Optional.empty() : gl.aQ.b(uh.a(string.substring(0, integer2), '.')).flatMap(act -> a(act, uh.a(string.substring(integer2 + 1), '.')));
		}
	}

	private static <T> Optional<dfp> a(act<T> act, uh uh) {
		return act.a().b(uh).map(act::b);
	}

	public String c() {
		return this.o;
	}

	public boolean d() {
		return this.p;
	}

	public dfp.a e() {
		return this.q;
	}

	public static enum a {
		INTEGER("integer"),
		HEARTS("hearts");

		private final String c;
		private static final Map<String, dfp.a> d;

		private a(String string3) {
			this.c = string3;
		}

		public String a() {
			return this.c;
		}

		public static dfp.a a(String string) {
			return (dfp.a)d.getOrDefault(string, INTEGER);
		}

		static {
			Builder<String, dfp.a> builder1 = ImmutableMap.builder();

			for (dfp.a a5 : values()) {
				builder1.put(a5.c, a5);
			}

			d = builder1.build();
		}
	}
}
