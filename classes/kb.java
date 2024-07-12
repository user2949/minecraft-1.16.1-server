import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class kb {
	private final String a;
	private final Collection<kv> b;
	@Nullable
	private final Consumer<zd> c;

	public kb(String string, Collection<kv> collection, @Nullable Consumer<zd> consumer) {
		if (collection.isEmpty()) {
			throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
		} else {
			this.a = string;
			this.b = collection;
			this.c = consumer;
		}
	}

	public String a() {
		return this.a;
	}

	public Collection<kv> b() {
		return this.b;
	}

	public void a(zd zd) {
		if (this.c != null) {
			this.c.accept(zd);
		}
	}
}
