import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class cow {
	public static final Codec<cow> a = gl.ay.dispatch(cow::b, cox::a);
	protected final OptionalInt b;

	protected static <S extends cow> RecordCodecBuilder<S, OptionalInt> a() {
		return Codec.INT
			.optionalFieldOf("min_clipped_height")
			.<OptionalInt>xmap(
				optional -> (OptionalInt)optional.map(OptionalInt::of).orElse(OptionalInt.empty()),
				optionalInt -> optionalInt.isPresent() ? Optional.of(optionalInt.getAsInt()) : Optional.empty()
			)
			.forGetter(cow -> cow.b);
	}

	public cow(OptionalInt optionalInt) {
		this.b = optionalInt;
	}

	protected abstract cox<?> b();

	public abstract int a(int integer1, int integer2);

	public OptionalInt c() {
		return this.b;
	}
}
