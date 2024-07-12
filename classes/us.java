import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class us extends abe {
	private static final Logger a = LogManager.getLogger();
	private static final Gson b = new GsonBuilder().create();
	private x c = new x();
	private final day d;

	public us(day day) {
		super(b, "advancements");
		this.d = day;
	}

	protected void a(Map<uh, JsonElement> map, abc abc, ami ami) {
		Map<uh, w.a> map5 = Maps.<uh, w.a>newHashMap();
		map.forEach((uh, jsonElement) -> {
			try {
				JsonObject jsonObject5 = adt.m(jsonElement, "advancement");
				w.a a6 = w.a.a(jsonObject5, new av(uh, this.d));
				map5.put(uh, a6);
			} catch (IllegalArgumentException | JsonParseException var6) {
				a.error("Parsing error loading custom advancement {}: {}", uh, var6.getMessage());
			}
		});
		x x6 = new x();
		x6.a(map5);

		for (w w8 : x6.b()) {
			if (w8.c() != null) {
				ai.a(w8);
			}
		}

		this.c = x6;
	}

	@Nullable
	public w a(uh uh) {
		return this.c.a(uh);
	}

	public Collection<w> a() {
		return this.c.c();
	}
}
