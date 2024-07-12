import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class bqv {
	public static final Codec<bqv> a = RecordCodecBuilder.create(
		instance -> instance.group(ack.a.fieldOf("sound").forGetter(bqv -> bqv.b), Codec.DOUBLE.fieldOf("tick_chance").forGetter(bqv -> bqv.c))
				.apply(instance, bqv::new)
	);
	private ack b;
	private double c;

	public bqv(ack ack, double double2) {
		this.b = ack;
		this.c = double2;
	}
}
