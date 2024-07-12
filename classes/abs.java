import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public abstract class abs<T> {
	@Nullable
	private final T a;

	public abs(@Nullable T object) {
		this.a = object;
	}

	@Nullable
	T g() {
		return this.a;
	}

	boolean f() {
		return false;
	}

	protected abstract void a(JsonObject jsonObject);
}
