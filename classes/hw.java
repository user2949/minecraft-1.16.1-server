import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;

public class hw implements hl {
	private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
	private final hk c;

	public hw(hk hk) {
		this.c = hk;
	}

	@Override
	public void a(hm hm) throws IOException {
		JsonObject jsonObject3 = new JsonObject();
		gl.h.b().forEach(uh -> jsonObject3.add(uh.toString(), a((gl<?>)gl.h.a(uh))));
		Path path4 = this.c.b().resolve("reports/registries.json");
		hl.a(b, hm, jsonObject3, path4);
	}

	private static <T> JsonElement a(gl<T> gl) {
		JsonObject jsonObject2 = new JsonObject();
		if (gl instanceof fy) {
			uh uh3 = ((fy)gl).a();
			jsonObject2.addProperty("default", uh3.toString());
		}

		int integer3 = gl.h.a(gl);
		jsonObject2.addProperty("protocol_id", integer3);
		JsonObject jsonObject4 = new JsonObject();

		for (uh uh6 : gl.b()) {
			T object7 = gl.a(uh6);
			int integer8 = gl.a(object7);
			JsonObject jsonObject9 = new JsonObject();
			jsonObject9.addProperty("protocol_id", integer8);
			jsonObject4.add(uh6.toString(), jsonObject9);
		}

		jsonObject2.add("entries", jsonObject4);
		return jsonObject2;
	}

	@Override
	public String a() {
		return "Registry Dump";
	}
}
