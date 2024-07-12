import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public final class nc {
	private static final Map<i, nc> a = (Map<i, nc>)Stream.of(i.values())
		.filter(i::d)
		.collect(ImmutableMap.toImmutableMap(Function.identity(), i -> new nc(i.e(), i.f())));
	private static final Map<String, nc> b = (Map<String, nc>)a.values().stream().collect(ImmutableMap.toImmutableMap(nc -> nc.d, Function.identity()));
	private final int c;
	@Nullable
	private final String d;

	private nc(int integer, String string) {
		this.c = integer;
		this.d = string;
	}

	private nc(int integer) {
		this.c = integer;
		this.d = null;
	}

	public String b() {
		return this.d != null ? this.d : this.c();
	}

	private String c() {
		return String.format("#%06X", this.c);
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			nc nc3 = (nc)object;
			return this.c == nc3.c;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.c, this.d});
	}

	public String toString() {
		return this.d != null ? this.d : this.c();
	}

	@Nullable
	public static nc a(i i) {
		return (nc)a.get(i);
	}

	public static nc a(int integer) {
		return new nc(integer);
	}

	@Nullable
	public static nc a(String string) {
		if (string.startsWith("#")) {
			try {
				int integer2 = Integer.parseInt(string.substring(1), 16);
				return a(integer2);
			} catch (NumberFormatException var2) {
				return null;
			}
		} else {
			return (nc)b.get(string);
		}
	}
}
