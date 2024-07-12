import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.stream.Stream;

public final class yx {
	private final Object2BooleanMap<ze> a = new Object2BooleanOpenHashMap<>();

	public Stream<ze> a(long long1) {
		return this.a.keySet().stream();
	}

	public void a(long long1, ze ze, boolean boolean3) {
		this.a.put(ze, boolean3);
	}

	public void a(long long1, ze ze) {
		this.a.removeBoolean(ze);
	}

	public void a(ze ze) {
		this.a.replace(ze, true);
	}

	public void b(ze ze) {
		this.a.replace(ze, false);
	}

	public boolean c(ze ze) {
		return this.a.getOrDefault(ze, true);
	}

	public boolean d(ze ze) {
		return this.a.getBoolean(ze);
	}

	public void a(long long1, long long2, ze ze) {
	}
}
