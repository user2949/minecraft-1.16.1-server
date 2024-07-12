import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class cno implements cnr {
	public static final Codec<cno> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cfj.b.fieldOf("contents").forGetter(cno -> cno.b),
					cfj.b.fieldOf("rim").forGetter(cno -> cno.c),
					Codec.INT.fieldOf("minimum_radius").forGetter(cno -> cno.d),
					Codec.INT.fieldOf("maximum_radius").forGetter(cno -> cno.e),
					Codec.INT.fieldOf("maximum_rim").forGetter(cno -> cno.f)
				)
				.apply(instance, cno::new)
	);
	public final cfj b;
	public final cfj c;
	public final int d;
	public final int e;
	public final int f;

	public cno(cfj cfj1, cfj cfj2, int integer3, int integer4, int integer5) {
		this.b = cfj1;
		this.c = cfj2;
		this.d = integer3;
		this.e = integer4;
		this.f = integer5;
	}

	public static class a {
		Optional<cfj> a = Optional.empty();
		Optional<cfj> b = Optional.empty();
		int c;
		int d;
		int e;

		public cno.a a(int integer1, int integer2) {
			this.c = integer1;
			this.d = integer2;
			return this;
		}

		public cno.a a(cfj cfj) {
			this.a = Optional.of(cfj);
			return this;
		}

		public cno.a a(cfj cfj, int integer) {
			this.b = Optional.of(cfj);
			this.e = integer;
			return this;
		}

		public cno a() {
			if (!this.a.isPresent()) {
				throw new IllegalArgumentException("Missing contents");
			} else if (!this.b.isPresent()) {
				throw new IllegalArgumentException("Missing rim");
			} else if (this.c > this.d) {
				throw new IllegalArgumentException("Minimum radius cannot be greater than maximum radius");
			} else {
				return new cno((cfj)this.a.get(), (cfj)this.b.get(), this.c, this.d, this.e);
			}
		}
	}
}
