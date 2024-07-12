import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class cnq implements cnr {
	public static final Codec<cnq> a = RecordCodecBuilder.create(
		instance -> instance.group(fu.a.optionalFieldOf("exit").forGetter(cnq -> cnq.b), Codec.BOOL.fieldOf("exact").forGetter(cnq -> cnq.c))
				.apply(instance, cnq::new)
	);
	private final Optional<fu> b;
	private final boolean c;

	private cnq(Optional<fu> optional, boolean boolean2) {
		this.b = optional;
		this.c = boolean2;
	}

	public static cnq a(fu fu, boolean boolean2) {
		return new cnq(Optional.of(fu), boolean2);
	}

	public static cnq a() {
		return new cnq(Optional.empty(), false);
	}

	public Optional<fu> b() {
		return this.b;
	}

	public boolean c() {
		return this.c;
	}
}
