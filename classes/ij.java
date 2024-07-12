import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ij implements hl {
	private static final Logger b = LogManager.getLogger();
	private static final Gson c = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final hk d;

	public ij(hk hk) {
		this.d = hk;
	}

	@Override
	public void a(hm hm) {
		Path path3 = this.d.b();
		Map<bvr, ik> map4 = Maps.<bvr, ik>newHashMap();
		Consumer<ik> consumer5 = ik -> {
			bvr bvr3 = ik.a();
			ik ik4 = (ik)map4.put(bvr3, ik);
			if (ik4 != null) {
				throw new IllegalStateException("Duplicate blockstate definition for " + bvr3);
			}
		};
		Map<uh, Supplier<JsonElement>> map6 = Maps.<uh, Supplier<JsonElement>>newHashMap();
		Set<bke> set7 = Sets.<bke>newHashSet();
		BiConsumer<uh, Supplier<JsonElement>> biConsumer8 = (uh, supplier) -> {
			Supplier<JsonElement> supplier4 = (Supplier<JsonElement>)map6.put(uh, supplier);
			if (supplier4 != null) {
				throw new IllegalStateException("Duplicate model definition for " + uh);
			}
		};
		Consumer<bke> consumer9 = set7::add;
		new ih(consumer5, biConsumer8, consumer9).a();
		new ii(biConsumer8).a();
		List<bvr> list10 = (List<bvr>)gl.aj.e().filter(bvr -> !map4.containsKey(bvr)).collect(Collectors.toList());
		if (!list10.isEmpty()) {
			throw new IllegalStateException("Missing blockstate definitions for: " + list10);
		} else {
			gl.aj.forEach(bvr -> {
				bke bke4 = (bke)bke.e.get(bvr);
				if (bke4 != null) {
					if (set7.contains(bke4)) {
						return;
					}

					uh uh5 = iv.a(bke4);
					if (!map6.containsKey(uh5)) {
						map6.put(uh5, new iu(iv.a(bvr)));
					}
				}
			});
			this.a(hm, path3, map4, ij::a);
			this.a(hm, path3, map6, ij::a);
		}
	}

	private <T> void a(hm hm, Path path, Map<T, ? extends Supplier<JsonElement>> map, BiFunction<Path, T, Path> biFunction) {
		map.forEach((object, supplier) -> {
			Path path6 = (Path)biFunction.apply(path, object);

			try {
				hl.a(c, hm, (JsonElement)supplier.get(), path6);
			} catch (Exception var7) {
				b.error("Couldn't save {}", path6, var7);
			}
		});
	}

	private static Path a(Path path, bvr bvr) {
		uh uh3 = gl.aj.b(bvr);
		return path.resolve("assets/" + uh3.b() + "/blockstates/" + uh3.a() + ".json");
	}

	private static Path a(Path path, uh uh) {
		return path.resolve("assets/" + uh.b() + "/models/" + uh.a() + ".json");
	}

	@Override
	public String a() {
		return "Block State Definitions";
	}
}
