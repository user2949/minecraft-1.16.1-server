import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class aci {
	public static final Codec<aci> a = RecordCodecBuilder.create(
		instance -> instance.group(
					ack.a.fieldOf("sound").forGetter(aci -> aci.b),
					Codec.INT.fieldOf("min_delay").forGetter(aci -> aci.c),
					Codec.INT.fieldOf("max_delay").forGetter(aci -> aci.d),
					Codec.BOOL.fieldOf("replace_current_music").forGetter(aci -> aci.e)
				)
				.apply(instance, aci::new)
	);
	private final ack b;
	private final int c;
	private final int d;
	private final boolean e;

	public aci(ack ack, int integer2, int integer3, boolean boolean4) {
		this.b = ack;
		this.c = integer2;
		this.d = integer3;
		this.e = boolean4;
	}
}
