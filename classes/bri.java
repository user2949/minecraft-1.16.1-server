import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;

public class bri {
	public static final Codec<bri> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.INT.fieldOf("fog_color").forGetter(bri -> bri.b),
					Codec.INT.fieldOf("water_color").forGetter(bri -> bri.c),
					Codec.INT.fieldOf("water_fog_color").forGetter(bri -> bri.d),
					bqx.a.optionalFieldOf("particle").forGetter(bri -> bri.e),
					ack.a.optionalFieldOf("ambient_sound").forGetter(bri -> bri.f),
					bqw.a.optionalFieldOf("mood_sound").forGetter(bri -> bri.g),
					bqv.a.optionalFieldOf("additions_sound").forGetter(bri -> bri.h),
					aci.a.optionalFieldOf("music").forGetter(bri -> bri.i)
				)
				.apply(instance, bri::new)
	);
	private final int b;
	private final int c;
	private final int d;
	private final Optional<bqx> e;
	private final Optional<ack> f;
	private final Optional<bqw> g;
	private final Optional<bqv> h;
	private final Optional<aci> i;

	private bri(
		int integer1,
		int integer2,
		int integer3,
		Optional<bqx> optional4,
		Optional<ack> optional5,
		Optional<bqw> optional6,
		Optional<bqv> optional7,
		Optional<aci> optional8
	) {
		this.b = integer1;
		this.c = integer2;
		this.d = integer3;
		this.e = optional4;
		this.f = optional5;
		this.g = optional6;
		this.h = optional7;
		this.i = optional8;
	}

	public static class a {
		private OptionalInt a = OptionalInt.empty();
		private OptionalInt b = OptionalInt.empty();
		private OptionalInt c = OptionalInt.empty();
		private Optional<bqx> d = Optional.empty();
		private Optional<ack> e = Optional.empty();
		private Optional<bqw> f = Optional.empty();
		private Optional<bqv> g = Optional.empty();
		private Optional<aci> h = Optional.empty();

		public bri.a a(int integer) {
			this.a = OptionalInt.of(integer);
			return this;
		}

		public bri.a b(int integer) {
			this.b = OptionalInt.of(integer);
			return this;
		}

		public bri.a c(int integer) {
			this.c = OptionalInt.of(integer);
			return this;
		}

		public bri.a a(bqx bqx) {
			this.d = Optional.of(bqx);
			return this;
		}

		public bri.a a(ack ack) {
			this.e = Optional.of(ack);
			return this;
		}

		public bri.a a(bqw bqw) {
			this.f = Optional.of(bqw);
			return this;
		}

		public bri.a a(bqv bqv) {
			this.g = Optional.of(bqv);
			return this;
		}

		public bri.a a(aci aci) {
			this.h = Optional.of(aci);
			return this;
		}

		public bri a() {
			return new bri(
				this.a.orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")),
				this.b.orElseThrow(() -> new IllegalStateException("Missing 'water' color.")),
				this.c.orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")),
				this.d,
				this.e,
				this.f,
				this.g,
				this.h
			);
		}
	}
}
