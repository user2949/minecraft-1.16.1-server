import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cit {
	public static final Codec<cit> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("target").forGetter(cit::a), adl.a(0, 256).fieldOf("size").forGetter(cit::b), Codec.INT.fieldOf("offset").forGetter(cit::c)
				)
				.apply(instance, cit::new)
	);
	private final int b;
	private final int c;
	private final int d;

	public cit(int integer1, int integer2, int integer3) {
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
