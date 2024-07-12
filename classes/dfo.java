import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class dfo {
	public boolean a(@Nullable dfo dfo) {
		return dfo == null ? false : this == dfo;
	}

	public abstract String b();

	public abstract mx d(mr mr);

	public abstract boolean h();

	public abstract Collection<String> g();

	public abstract dfo.b k();

	public abstract dfo.a l();

	public static enum a {
		ALWAYS("always", 0),
		NEVER("never", 1),
		PUSH_OTHER_TEAMS("pushOtherTeams", 2),
		PUSH_OWN_TEAM("pushOwnTeam", 3);

		private static final Map<String, dfo.a> g = (Map<String, dfo.a>)Arrays.stream(values()).collect(Collectors.toMap(a -> a.e, a -> a));
		public final String e;
		public final int f;

		@Nullable
		public static dfo.a a(String string) {
			return (dfo.a)g.get(string);
		}

		private a(String string3, int integer4) {
			this.e = string3;
			this.f = integer4;
		}

		public mr b() {
			return new ne("team.collision." + this.e);
		}
	}

	public static enum b {
		ALWAYS("always", 0),
		NEVER("never", 1),
		HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
		HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

		private static final Map<String, dfo.b> g = (Map<String, dfo.b>)Arrays.stream(values()).collect(Collectors.toMap(b -> b.e, b -> b));
		public final String e;
		public final int f;

		@Nullable
		public static dfo.b a(String string) {
			return (dfo.b)g.get(string);
		}

		private b(String string3, int integer4) {
			this.e = string3;
			this.f = integer4;
		}

		public mr b() {
			return new ne("team.visibility." + this.e);
		}
	}
}
