import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dax extends abe {
	private static final Logger a = LogManager.getLogger();
	private static final Gson b = daq.c().create();
	private Map<uh, daw> c = ImmutableMap.of();
	private final day d;

	public dax(day day) {
		super(b, "loot_tables");
		this.d = day;
	}

	public daw a(uh uh) {
		return (daw)this.c.getOrDefault(uh, daw.a);
	}

	protected void a(Map<uh, JsonElement> map, abc abc, ami ami) {
		Builder<uh, daw> builder5 = ImmutableMap.builder();
		JsonElement jsonElement6 = (JsonElement)map.remove(dao.a);
		if (jsonElement6 != null) {
			a.warn("Datapack tried to redefine {} loot table, ignoring", dao.a);
		}

		map.forEach((uh, jsonElement) -> {
			try {
				daw daw4 = b.fromJson(jsonElement, daw.class);
				builder5.put(uh, daw4);
			} catch (Exception var4x) {
				a.error("Couldn't parse loot table {}", uh, var4x);
			}
		});
		builder5.put(dao.a, daw.a);
		ImmutableMap<uh, daw> immutableMap7 = builder5.build();
		dbe dbe8 = new dbe(dcz.k, this.d::a, immutableMap7::get);
		immutableMap7.forEach((uh, daw) -> a(dbe8, uh, daw));
		dbe8.a().forEach((string1, string2) -> a.warn("Found validation problem in " + string1 + ": " + string2));
		this.c = immutableMap7;
	}

	public static void a(dbe dbe, uh uh, daw daw) {
		daw.a(dbe.a(daw.a()).a("{" + uh + "}", uh));
	}

	public static JsonElement a(daw daw) {
		return b.toJsonTree(daw);
	}

	public Set<uh> a() {
		return this.c.keySet();
	}
}
