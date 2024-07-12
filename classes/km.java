import com.google.common.collect.Lists;
import java.util.Collection;

public class km {
	public static final km a = new km();
	private final Collection<kg> b = Lists.<kg>newCopyOnWriteArrayList();

	public void a(kg kg) {
		this.b.add(kg);
	}

	public void a() {
		this.b.clear();
	}

	public void b() {
		this.b.forEach(kg::b);
		this.b.removeIf(kg::k);
	}
}
