import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cos {
	public static final Codec<cos> a = RecordCodecBuilder.create(
		instance -> instance.group(
					adl.a(0, 1023).fieldOf("distance").forGetter(cos::a),
					adl.a(0, 1023).fieldOf("spread").forGetter(cos::b),
					adl.a(1, 4095).fieldOf("count").forGetter(cos::c)
				)
				.apply(instance, cos::new)
	);
	private final int b;
	private final int c;
	private final int d;

	public cos(int integer1, int integer2, int integer3) {
		this.b = integer1;
		this.c = integer2;
		this.d = integer3;
	}

	public int a() {
		return this.b;
	}

	public int b() {
		return this.c;
	}

	public int c() {
		return this.d;
	}
}
