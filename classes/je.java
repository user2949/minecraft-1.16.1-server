import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public interface je {
	void a(JsonObject jsonObject);

	default JsonObject a() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("type", gl.aO.b(this.c()).toString());
		this.a(jsonObject2);
		return jsonObject2;
	}

	uh b();

	bmw<?> c();

	@Nullable
	JsonObject d();

	@Nullable
	uh e();
}
