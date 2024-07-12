import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;

public class bdq {
	private static final int[] b = new int[]{0, 10, 70, 150, 250};
	public static final Codec<bdq> a = RecordCodecBuilder.create(
		instance -> instance.group(
					gl.aR.fieldOf("type").withDefault((Supplier<? extends bdu>)(() -> bdu.c)).forGetter(bdq -> bdq.c),
					gl.aS.fieldOf("profession").withDefault((Supplier<? extends bds>)(() -> bds.a)).forGetter(bdq -> bdq.d),
					Codec.INT.fieldOf("level").withDefault(1).forGetter(bdq -> bdq.e)
				)
				.apply(instance, bdq::new)
	);
	private final bdu c;
	private final bds d;
	private final int e;

	public bdq(bdu bdu, bds bds, int integer) {
		this.c = bdu;
		this.d = bds;
		this.e = Math.max(1, integer);
	}

	public bdu a() {
		return this.c;
	}

	public bds b() {
		return this.d;
	}

	public int c() {
		return this.e;
	}

	public bdq a(bdu bdu) {
		return new bdq(bdu, this.d, this.e);
	}

	public bdq a(bds bds) {
		return new bdq(this.c, bds, this.e);
	}

	public bdq a(int integer) {
		return new bdq(this.c, this.d, integer);
	}

	public static int c(int integer) {
		return d(integer) ? b[integer] : 0;
	}

	public static boolean d(int integer) {
		return integer >= 1 && integer < 5;
	}
}
