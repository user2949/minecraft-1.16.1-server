import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bmv extends abe {
	private static final Gson a = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static final Logger b = LogManager.getLogger();
	private Map<bmx<?>, Map<uh, bmu<?>>> c = ImmutableMap.of();
	private boolean d;

	public bmv() {
		super(a, "recipes");
	}

	protected void a(Map<uh, JsonElement> map, abc abc, ami ami) {
		this.d = false;
		Map<bmx<?>, Builder<uh, bmu<?>>> map5 = Maps.<bmx<?>, Builder<uh, bmu<?>>>newHashMap();

		for (Entry<uh, JsonElement> entry7 : map.entrySet()) {
			uh uh8 = (uh)entry7.getKey();

			try {
				bmu<?> bmu9 = a(uh8, adt.m((JsonElement)entry7.getValue(), "top element"));
				((Builder)map5.computeIfAbsent(bmu9.g(), bmx -> ImmutableMap.builder())).put(uh8, bmu9);
			} catch (IllegalArgumentException | JsonParseException var9) {
				b.error("Parsing error loading recipe {}", uh8, var9);
			}
		}

		this.c = (Map<bmx<?>, Map<uh, bmu<?>>>)map5.entrySet()
			.stream()
			.collect(ImmutableMap.toImmutableMap(Entry::getKey, entry -> ((Builder)entry.getValue()).build()));
		b.info("Loaded {} recipes", map5.size());
	}

	public <C extends amz, T extends bmu<C>> Optional<T> a(bmx<T> bmx, C amz, bqb bqb) {
		return this.b(bmx).values().stream().flatMap(bmu -> v.a(bmx.a(bmu, bqb, amz))).findFirst();
	}

	public <C extends amz, T extends bmu<C>> List<T> a(bmx<T> bmx) {
		return (List<T>)this.b(bmx).values().stream().map(bmu -> bmu).collect(Collectors.toList());
	}

	public <C extends amz, T extends bmu<C>> List<T> b(bmx<T> bmx, C amz, bqb bqb) {
		return (List<T>)this.b(bmx)
			.values()
			.stream()
			.flatMap(bmu -> v.a(bmx.a(bmu, bqb, amz)))
			.sorted(Comparator.comparing(bmu -> bmu.c().j()))
			.collect(Collectors.toList());
	}

	private <C extends amz, T extends bmu<C>> Map<uh, bmu<C>> b(bmx<T> bmx) {
		return (Map<uh, bmu<C>>)this.c.getOrDefault(bmx, Collections.emptyMap());
	}

	public <C extends amz, T extends bmu<C>> gi<bki> c(bmx<T> bmx, C amz, bqb bqb) {
		Optional<T> optional5 = this.a(bmx, amz, bqb);
		if (optional5.isPresent()) {
			return ((bmu)optional5.get()).b(amz);
		} else {
			gi<bki> gi6 = gi.a(amz.ab_(), bki.b);

			for (int integer7 = 0; integer7 < gi6.size(); integer7++) {
				gi6.set(integer7, amz.a(integer7));
			}

			return gi6;
		}
	}

	public Optional<? extends bmu<?>> a(uh uh) {
		return this.c.values().stream().map(map -> (bmu)map.get(uh)).filter(Objects::nonNull).findFirst();
	}

	public Collection<bmu<?>> b() {
		return (Collection<bmu<?>>)this.c.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toSet());
	}

	public Stream<uh> d() {
		return this.c.values().stream().flatMap(map -> map.keySet().stream());
	}

	public static bmu<?> a(uh uh, JsonObject jsonObject) {
		String string3 = adt.h(jsonObject, "type");
		return ((bmw)gl.aO.b(new uh(string3)).orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + string3 + "'"))).a(uh, jsonObject);
	}
}
