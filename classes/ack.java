import com.mojang.serialization.Codec;

public class ack {
	public static final Codec<ack> a = uh.a.xmap(ack::new, ack -> ack.b);
	private final uh b;

	public ack(uh uh) {
		this.b = uh;
	}
}
