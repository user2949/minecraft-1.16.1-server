import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class x {
	private static final Logger a = LogManager.getLogger();
	private final Map<uh, w> b = Maps.<uh, w>newHashMap();
	private final Set<w> c = Sets.<w>newLinkedHashSet();
	private final Set<w> d = Sets.<w>newLinkedHashSet();
	private x.a e;

	public void a(Map<uh, w.a> map) {
		Function<uh, w> function3 = Functions.forMap(this.b, null);

		while (!map.isEmpty()) {
			boolean boolean4 = false;
			Iterator<Entry<uh, w.a>> iterator5 = map.entrySet().iterator();

			while (iterator5.hasNext()) {
				Entry<uh, w.a> entry6 = (Entry<uh, w.a>)iterator5.next();
				uh uh7 = (uh)entry6.getKey();
				w.a a8 = (w.a)entry6.getValue();
				if (a8.a(function3)) {
					w w9 = a8.b(uh7);
					this.b.put(uh7, w9);
					boolean4 = true;
					iterator5.remove();
					if (w9.b() == null) {
						this.c.add(w9);
						if (this.e != null) {
							this.e.a(w9);
						}
					} else {
						this.d.add(w9);
						if (this.e != null) {
							this.e.c(w9);
						}
					}
				}
			}

			if (!boolean4) {
				for (Entry<uh, w.a> entry6 : map.entrySet()) {
					a.error("Couldn't load advancement {}: {}", entry6.getKey(), entry6.getValue());
				}
				break;
			}
		}

		a.info("Loaded {} advancements", this.b.size());
	}

	public Iterable<w> b() {
		return this.c;
	}

	public Collection<w> c() {
		return this.b.values();
	}

	@Nullable
	public w a(uh uh) {
		return (w)this.b.get(uh);
	}

	public interface a {
		void a(w w);

		void c(w w);
	}
}
