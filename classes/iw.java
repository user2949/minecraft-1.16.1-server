import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class iw {
	private final Optional<uh> a;
	private final Set<iz> b;
	private Optional<String> c;

	public iw(Optional<uh> optional1, Optional<String> optional2, iz... arr) {
		this.a = optional1;
		this.c = optional2;
		this.b = ImmutableSet.copyOf(arr);
	}

	public uh a(bvr bvr, iy iy, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
		return this.a(iv.a(bvr, (String)this.c.orElse("")), iy, biConsumer);
	}

	public uh a(bvr bvr, String string, iy iy, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
		return this.a(iv.a(bvr, string + (String)this.c.orElse("")), iy, biConsumer);
	}

	public uh b(bvr bvr, String string, iy iy, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
		return this.a(iv.a(bvr, string), iy, biConsumer);
	}

	public uh a(uh uh, iy iy, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
		Map<iz, uh> map5 = this.a(iy);
		biConsumer.accept(uh, (Supplier)() -> {
			JsonObject jsonObject3 = new JsonObject();
			this.a.ifPresent(uhx -> jsonObject3.addProperty("parent", uhx.toString()));
			if (!map5.isEmpty()) {
				JsonObject jsonObject4 = new JsonObject();
				map5.forEach((iz, uhx) -> jsonObject4.addProperty(iz.a(), uhx.toString()));
				jsonObject3.add("textures", jsonObject4);
			}

			return jsonObject3;
		});
		return uh;
	}

	private Map<iz, uh> a(iy iy) {
		return (Map<iz, uh>)Streams.concat(this.b.stream(), iy.a()).collect(ImmutableMap.toImmutableMap(Function.identity(), iy::a));
	}
}
