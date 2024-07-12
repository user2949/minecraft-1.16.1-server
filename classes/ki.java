import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ki {
	private static final Collection<kv> a = Lists.<kv>newArrayList();
	private static final Set<String> b = Sets.<String>newHashSet();
	private static final Map<String, Consumer<zd>> c = Maps.<String, Consumer<zd>>newHashMap();
	private static final Collection<kv> d = Sets.<kv>newHashSet();

	public static Collection<kv> a(String string) {
		return (Collection<kv>)a.stream().filter(kv -> a(kv, string)).collect(Collectors.toList());
	}

	public static Collection<kv> a() {
		return a;
	}

	public static Collection<String> b() {
		return b;
	}

	public static boolean b(String string) {
		return b.contains(string);
	}

	@Nullable
	public static Consumer<zd> c(String string) {
		return (Consumer<zd>)c.get(string);
	}

	public static Optional<kv> d(String string) {
		return a().stream().filter(kv -> kv.a().equalsIgnoreCase(string)).findFirst();
	}

	public static kv e(String string) {
		Optional<kv> optional2 = d(string);
		if (!optional2.isPresent()) {
			throw new IllegalArgumentException("Can't find the test function for " + string);
		} else {
			return (kv)optional2.get();
		}
	}

	private static boolean a(kv kv, String string) {
		return kv.a().toLowerCase().startsWith(string.toLowerCase() + ".");
	}

	public static Collection<kv> c() {
		return d;
	}

	public static void a(kv kv) {
		d.add(kv);
	}

	public static void d() {
		d.clear();
	}
}
