import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class coy extends cow {
	public static final Codec<coy> c = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("limit").withDefault(1).forGetter(coy -> coy.d),
					Codec.INT.fieldOf("upper_limit").withDefault(1).forGetter(coy -> coy.e),
					Codec.INT.fieldOf("lower_size").withDefault(0).forGetter(coy -> coy.f),
					Codec.INT.fieldOf("middle_size").withDefault(1).forGetter(coy -> coy.g),
					Codec.INT.fieldOf("upper_size").withDefault(1).forGetter(coy -> coy.h),
					a()
				)
				.apply(instance, coy::new)
	);
	private final int d;
	private final int e;
	private final int f;
	private final int g;
	private final int h;

	public coy(int integer1, int integer2, int integer3, int integer4, int integer5, OptionalInt optionalInt) {
		super(optionalInt);
		this.d = integer1;
		this.e = integer2;
		this.f = integer3;
		this.g = integer4;
		this.h = integer5;
	}

	@Override
	protected cox<?> b() {
		return cox.b;
	}

	@Override
	public int a(int integer1, int integer2) {
		if (integer2 < this.d) {
			return this.f;
		} else {
			return integer2 >= integer1 - this.e ? this.h : this.g;
		}
	}
}
