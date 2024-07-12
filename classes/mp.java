import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class mp {
	private final mp.a a;
	private final String b;

	public mp(mp.a a, String string) {
		this.a = a;
		this.b = string;
	}

	public mp.a a() {
		return this.a;
	}

	public String b() {
		return this.b;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			mp mp3 = (mp)object;
			if (this.a != mp3.a) {
				return false;
			} else {
				return this.b != null ? this.b.equals(mp3.b) : mp3.b == null;
			}
		} else {
			return false;
		}
	}

	public String toString() {
		return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
	}

	public int hashCode() {
		int integer2 = this.a.hashCode();
		return 31 * integer2 + (this.b != null ? this.b.hashCode() : 0);
	}

	public static enum a {
		OPEN_URL("open_url", true),
		OPEN_FILE("open_file", false),
		RUN_COMMAND("run_command", true),
		SUGGEST_COMMAND("suggest_command", true),
		CHANGE_PAGE("change_page", true),
		COPY_TO_CLIPBOARD("copy_to_clipboard", true);

		private static final Map<String, mp.a> g = (Map<String, mp.a>)Arrays.stream(values()).collect(Collectors.toMap(mp.a::b, a -> a));
		private final boolean h;
		private final String i;

		private a(String string3, boolean boolean4) {
			this.i = string3;
			this.h = boolean4;
		}

		public boolean a() {
			return this.h;
		}

		public String b() {
			return this.i;
		}

		public static mp.a a(String string) {
			return (mp.a)g.get(string);
		}
	}
}
