import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class coz extends cow {
	public static final Codec<coz> c = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("limit").withDefault(1).forGetter(coz -> coz.d),
					Codec.INT.fieldOf("lower_size").withDefault(0).forGetter(coz -> coz.e),
					Codec.INT.fieldOf("upper_size").withDefault(1).forGetter(coz -> coz.f),
					a()
				)
				.apply(instance, coz::new)
	);
	private final int d;
	private final int e;
	private final int f;

	public coz(int integer1, int integer2, int integer3) {
		this(integer1, integer2, integer3, OptionalInt.empty());
	}

	public coz(int integer1, int integer2, int integer3, OptionalInt optionalInt) {
		super(optionalInt);
		this.d = integer1;
		this.e = integer2;
		this.f = integer3;
	}

	@Override
	protected cox<?> b() {
		return cox.a;
	}

	@Override
	public int a(int integer1, int integer2) {
		return integer2 < this.d ? this.e : this.f;
	}
}
