import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class awo<T> {
	private final T a;
	private long b;

	public awo(T object, long long2) {
		this.a = object;
		this.b = long2;
	}

	public void a() {
		if (this.e()) {
			this.b--;
		}
	}

	public static <T> awo<T> a(T object) {
		return new awo<>(object, Long.MAX_VALUE);
	}

	public static <T> awo<T> a(T object, long long2) {
		return new awo<>(object, long2);
	}

	public T c() {
		return this.a;
	}

	public boolean d() {
		return this.b <= 0L;
	}

	public String toString() {
		return this.a.toString() + (this.e() ? " (ttl: " + this.b + ")" : "");
	}

	public boolean e() {
		return this.b != Long.MAX_VALUE;
	}

	public static <T> Codec<awo<T>> a(Codec<T> codec) {
		return RecordCodecBuilder.create(
			instance -> instance.group(
						codec.fieldOf("value").forGetter(awo -> awo.a), Codec.LONG.optionalFieldOf("ttl").forGetter(awo -> awo.e() ? Optional.of(awo.b) : Optional.empty())
					)
					.apply(instance, (object, optional) -> new awo<>(object, (Long)optional.orElse(Long.MAX_VALUE)))
		);
	}
}
