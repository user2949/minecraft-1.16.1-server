import com.google.gson.JsonObject;
import java.util.function.Function;

public class bne<T extends bmu<?>> implements bmw<T> {
	private final Function<uh, T> v;

	public bne(Function<uh, T> function) {
		this.v = function;
	}

	@Override
	public T a(uh uh, JsonObject jsonObject) {
		return (T)this.v.apply(uh);
	}

	@Override
	public T a(uh uh, mg mg) {
		return (T)this.v.apply(uh);
	}

	@Override
	public void a(mg mg, T bmu) {
	}
}
