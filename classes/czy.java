import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;

public class czy {
	private final Map<String, czy.a> a = Maps.<String, czy.a>newHashMap();
	private final daa b;

	public czy(daa daa) {
		this.b = daa;
	}

	private czy.a a(String string1, String string2) {
		czy.a a4 = new czy.a(string2);
		this.a.put(string1, a4);
		return a4;
	}

	public le a(uh uh) {
		String string3 = uh.b();
		String string4 = a(string3);
		czy.a a5 = this.b.b(() -> this.a(string3, string4), string4);
		return a5 != null ? a5.a(uh.a()) : new le();
	}

	public void a(uh uh, le le) {
		String string4 = uh.b();
		String string5 = a(string4);
		this.b.<czy.a>a(() -> this.a(string4, string5), string5).a(uh.a(), le);
	}

	public Stream<uh> a() {
		return this.a.entrySet().stream().flatMap(entry -> ((czy.a)entry.getValue()).b((String)entry.getKey()));
	}

	private static String a(String string) {
		return "command_storage_" + string;
	}

	static class a extends czq {
		private final Map<String, le> a = Maps.<String, le>newHashMap();

		public a(String string) {
			super(string);
		}

		@Override
		public void a(le le) {
			le le3 = le.p("contents");

			for (String string5 : le3.d()) {
				this.a.put(string5, le3.p(string5));
			}
		}

		@Override
		public le b(le le) {
			le le3 = new le();
			this.a.forEach((string, le3x) -> le3.a(string, le3x.g()));
			le.a("contents", le3);
			return le;
		}

		public le a(String string) {
			le le3 = (le)this.a.get(string);
			return le3 != null ? le3 : new le();
		}

		public void a(String string, le le) {
			if (le.isEmpty()) {
				this.a.remove(string);
			} else {
				this.a.put(string, le);
			}

			this.b();
		}

		public Stream<uh> b(String string) {
			return this.a.keySet().stream().map(string2 -> new uh(string, string2));
		}
	}
}
