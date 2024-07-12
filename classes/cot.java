import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;

public class cot {
	public static final Codec<cot> a = RecordCodecBuilder.create(
			instance -> instance.group(
						adl.a(0, 4096).fieldOf("spacing").forGetter(cot -> cot.b),
						adl.a(0, 4096).fieldOf("separation").forGetter(cot -> cot.c),
						adl.a(0, Integer.MAX_VALUE).fieldOf("salt").forGetter(cot -> cot.d)
					)
					.apply(instance, cot::new)
		)
		.comapFlatMap(cot -> cot.b <= cot.c ? DataResult.error("Spacing has to be smaller than separation") : DataResult.success(cot), Function.identity());
	private final int b;
	private final int c;
	private final int d;

	public cot(int integer1, int integer2, int integer3) {
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
