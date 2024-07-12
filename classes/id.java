import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class id implements hl {
	private static final Logger b = LogManager.getLogger();
	private static final Gson c = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final hk d;
	private final List<Pair<Supplier<Consumer<BiConsumer<uh, daw.a>>>, dcy>> e = ImmutableList.of(
		Pair.of(ib::new, dcz.e), Pair.of(hz::new, dcz.b), Pair.of(ia::new, dcz.f), Pair.of(hy::new, dcz.l), Pair.of(ie::new, dcz.h), Pair.of(ic::new, dcz.g)
	);

	public id(hk hk) {
		this.d = hk;
	}

	@Override
	public void a(hm hm) {
		Path path3 = this.d.b();
		Map<uh, daw> map4 = Maps.<uh, daw>newHashMap();
		this.e.forEach(pair -> ((Consumer)((Supplier)pair.getFirst()).get()).accept((BiConsumer)(uh, a) -> {
				if (map4.put(uh, a.a((dcy)pair.getSecond()).b()) != null) {
					throw new IllegalStateException("Duplicate loot table " + uh);
				}
			}));
		dbe dbe5 = new dbe(dcz.k, uh -> null, map4::get);

		for (uh uh8 : Sets.difference(dao.a(), map4.keySet())) {
			dbe5.a("Missing built-in table: " + uh8);
		}

		map4.forEach((uh, daw) -> dax.a(dbe5, uh, daw));
		Multimap<String, String> multimap7 = dbe5.a();
		if (!multimap7.isEmpty()) {
			multimap7.forEach((string1, string2) -> b.warn("Found validation problem in " + string1 + ": " + string2));
			throw new IllegalStateException("Failed to validate loot tables, see logs");
		} else {
			map4.forEach((uh, daw) -> {
				Path path5 = a(path3, uh);

				try {
					hl.a(c, hm, dax.a(daw), path5);
				} catch (IOException var6) {
					b.error("Couldn't save loot table {}", path5, var6);
				}
			});
		}
	}

	private static Path a(Path path, uh uh) {
		return path.resolve("data/" + uh.b() + "/loot_tables/" + uh.a() + ".json");
	}

	@Override
	public String a() {
		return "LootTables";
	}
}
