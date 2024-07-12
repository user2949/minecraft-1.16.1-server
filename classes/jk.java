import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class jk {
	private final bne<?> a;

	public jk(bne<?> bne) {
		this.a = bne;
	}

	public static jk a(bne<?> bne) {
		return new jk(bne);
	}

	public void a(Consumer<je> consumer, String string) {
		consumer.accept(new je() {
			@Override
			public void a(JsonObject jsonObject) {
			}

			@Override
			public bmw<?> c() {
				return jk.this.a;
			}

			@Override
			public uh b() {
				return new uh(string);
			}

			@Nullable
			@Override
			public JsonObject d() {
				return null;
			}

			@Override
			public uh e() {
				return new uh("");
			}
		});
	}
}
