import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class bqw {
	public static final Codec<bqw> a = RecordCodecBuilder.create(
		instance -> instance.group(
					ack.a.fieldOf("sound").forGetter(bqw -> bqw.c),
					Codec.INT.fieldOf("tick_delay").forGetter(bqw -> bqw.d),
					Codec.INT.fieldOf("block_search_extent").forGetter(bqw -> bqw.e),
					Codec.DOUBLE.fieldOf("offset").forGetter(bqw -> bqw.f)
				)
				.apply(instance, bqw::new)
	);
	public static final bqw b = new bqw(acl.a, 6000, 8, 2.0);
	private ack c;
	private int d;
	private int e;
	private double f;

	public bqw(ack ack, int integer2, int integer3, double double4) {
		this.c = ack;
		this.d = integer2;
		this.e = integer3;
		this.f = double4;
	}
}
