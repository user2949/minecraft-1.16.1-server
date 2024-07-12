import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;

public interface fg<T extends ArgumentType<?>> {
	void a(T argumentType, mg mg);

	T b(mg mg);

	void a(T argumentType, JsonObject jsonObject);
}
