import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;

public class hu implements hl {
	private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
	private final hk c;

	public hu(hk hk) {
		this.c = hk;
	}

	@Override
	public void a(hm hm) throws IOException {
		JsonObject jsonObject3 = new JsonObject();

		for (bvr bvr5 : gl.aj) {
			uh uh6 = gl.aj.b(bvr5);
			JsonObject jsonObject7 = new JsonObject();
			cfk<bvr, cfj> cfk8 = bvr5.m();
			if (!cfk8.d().isEmpty()) {
				JsonObject jsonObject9 = new JsonObject();

				for (cgl<?> cgl11 : cfk8.d()) {
					JsonArray jsonArray12 = new JsonArray();

					for (Comparable<?> comparable14 : cgl11.a()) {
						jsonArray12.add(v.a(cgl11, comparable14));
					}

					jsonObject9.add(cgl11.f(), jsonArray12);
				}

				jsonObject7.add("properties", jsonObject9);
			}

			JsonArray jsonArray9 = new JsonArray();

			for (cfj cfj11 : cfk8.a()) {
				JsonObject jsonObject12 = new JsonObject();
				JsonObject jsonObject13 = new JsonObject();

				for (cgl<?> cgl15 : cfk8.d()) {
					jsonObject13.addProperty(cgl15.f(), v.a(cgl15, cfj11.c(cgl15)));
				}

				if (jsonObject13.size() > 0) {
					jsonObject12.add("properties", jsonObject13);
				}

				jsonObject12.addProperty("id", bvr.i(cfj11));
				if (cfj11 == bvr5.n()) {
					jsonObject12.addProperty("default", true);
				}

				jsonArray9.add(jsonObject12);
			}

			jsonObject7.add("states", jsonArray9);
			jsonObject3.add(uh6.toString(), jsonObject7);
		}

		Path path4 = this.c.b().resolve("reports/blocks.json");
		hl.a(b, hm, jsonObject3, path4);
	}

	@Override
	public String a() {
		return "Block List";
	}
}
