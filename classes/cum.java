import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;

public class cum extends cvc {
	public static final Codec<cum> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cio.a.g.fieldOf("heightmap").withDefault(cio.a.WORLD_SURFACE_WG).forGetter(cum -> cum.b),
					Codec.INT.fieldOf("offset").withDefault(0).forGetter(cum -> cum.c)
				)
				.apply(instance, cum::new)
	);
	private final cio.a b;
	private final int c;

	public cum(cio.a a, int integer) {
		this.b = a;
		this.c = integer;
	}

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		cio.a a8;
		if (bqd instanceof zd) {
			if (this.b == cio.a.WORLD_SURFACE_WG) {
				a8 = cio.a.WORLD_SURFACE;
			} else if (this.b == cio.a.OCEAN_FLOOR_WG) {
				a8 = cio.a.OCEAN_FLOOR;
			} else {
				a8 = this.b;
			}
		} else {
			a8 = this.b;
		}

		int integer9 = bqd.a(a8, c5.a.u(), c5.a.w()) + this.c;
		int integer10 = c4.a.v();
		return new cve.c(new fu(c5.a.u(), integer9 + integer10, c5.a.w()), c5.b, c5.c);
	}

	@Override
	protected cvd<?> a() {
		return cvd.c;
	}
}
