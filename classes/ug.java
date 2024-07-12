import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class ug<T> {
	private static final Map<String, ug<?>> a = Collections.synchronizedMap(Maps.newIdentityHashMap());
	private final uh b;
	private final uh c;

	public static <T> ug<T> a(ug<gl<T>> ug, uh uh) {
		return a(ug.c, uh);
	}

	public static <T> ug<gl<T>> a(uh uh) {
		return a(gl.f, uh);
	}

	private static <T> ug<T> a(uh uh1, uh uh2) {
		String string3 = (uh1 + ":" + uh2).intern();
		return (ug<T>)a.computeIfAbsent(string3, string -> new ug(uh1, uh2));
	}

	private ug(uh uh1, uh uh2) {
		this.b = uh1;
		this.c = uh2;
	}

	public String toString() {
		return "ResourceKey[" + this.b + " / " + this.c + ']';
	}

	public uh a() {
		return this.c;
	}

	public static <T> Function<uh, ug<T>> a(ug<gl<T>> ug) {
		return uh -> a(ug, uh);
	}
}
