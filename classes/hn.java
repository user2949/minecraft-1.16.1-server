import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class hn implements hl {
	private static final Logger b = LogManager.getLogger();
	private static final Gson c = new GsonBuilder().setPrettyPrinting().create();
	private final hk d;
	private final List<Consumer<Consumer<w>>> e = ImmutableList.of(new hs(), new hp(), new ho(), new hq(), new hr());

	public hn(hk hk) {
		this.d = hk;
	}

	@Override
	public void a(hm hm) throws IOException {
		Path path3 = this.d.b();
		Set<uh> set4 = Sets.<uh>newHashSet();
		Consumer<w> consumer5 = w -> {
			if (!set4.add(w.h())) {
				throw new IllegalStateException("Duplicate advancement " + w.h());
			} else {
				Path path5 = a(path3, w);

				try {
					hl.a(c, hm, w.a().b(), path5);
				} catch (IOException var6x) {
					b.error("Couldn't save advancement {}", path5, var6x);
				}
			}
		};

		for (Consumer<Consumer<w>> consumer7 : this.e) {
			consumer7.accept(consumer5);
		}
	}

	private static Path a(Path path, w w) {
		return path.resolve("data/" + w.h().b() + "/advancements/" + w.h().a() + ".json");
	}

	@Override
	public String a() {
		return "Advancements";
	}
}
