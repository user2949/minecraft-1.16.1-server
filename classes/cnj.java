import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnj implements cnr {
	public static final Codec<cnj> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("minimum_reach").forGetter(cnj -> cnj.b),
					Codec.INT.fieldOf("maximum_reach").forGetter(cnj -> cnj.c),
					Codec.INT.fieldOf("minimum_height").forGetter(cnj -> cnj.d),
					Codec.INT.fieldOf("maximum_height").forGetter(cnj -> cnj.e)
				)
				.apply(instance, cnj::new)
	);
	public final int b;
	public final int c;
	public final int d;
	public final int e;

	public cnj(int integer1, int integer2, int integer3, int integer4) {
		this.b = integer1;
		this.c = integer2;
		this.d = integer3;
		this.e = integer4;
	}

	public static class a {
		private int a;
		private int b;
		private int c;
		private int d;

		public cnj.a a(int integer) {
			this.a = integer;
			this.b = integer;
			return this;
		}

		public cnj.a a(int integer1, int integer2) {
			this.a = integer1;
			this.b = integer2;
			return this;
		}

		public cnj.a b(int integer1, int integer2) {
			this.c = integer1;
			this.d = integer2;
			return this;
		}

		public cnj a() {
			if (this.c < 1) {
				throw new IllegalArgumentException("Minimum height cannot be less than 1");
			} else if (this.a < 0) {
				throw new IllegalArgumentException("Minimum reach cannot be negative");
			} else if (this.a <= this.b && this.c <= this.d) {
				return new cnj(this.a, this.b, this.c, this.d);
			} else {
				throw new IllegalArgumentException("Minimum reach/height cannot be greater than maximum width/height");
			}
		}
	}
}
