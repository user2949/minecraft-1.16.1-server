import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class act<T> implements Iterable<acr<T>> {
	private final gl<T> a;
	private final Map<T, acr<T>> b = new IdentityHashMap();

	public act(gl<T> gl) {
		this.a = gl;
	}

	public acr<T> a(T object, acs acs) {
		return (acr<T>)this.b.computeIfAbsent(object, objectx -> new acr<>(this, (T)objectx, acs));
	}

	public gl<T> a() {
		return this.a;
	}

	public Iterator<acr<T>> iterator() {
		return this.b.values().iterator();
	}

	public acr<T> b(T object) {
		return this.a(object, acs.b);
	}
}
