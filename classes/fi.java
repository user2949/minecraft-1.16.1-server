import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;

public class fi<T extends ArgumentType<?>> implements fg<T> {
	private final Supplier<T> a;

	public fi(Supplier<T> supplier) {
		this.a = supplier;
	}

	@Override
	public void a(T argumentType, mg mg) {
	}

	@Override
	public T b(mg mg) {
		return (T)this.a.get();
	}

	@Override
	public void a(T argumentType, JsonObject jsonObject) {
	}
}
