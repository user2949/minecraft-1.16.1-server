import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class bti extends brh {
	public static final Codec<bti> e = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.LONG.fieldOf("seed").stable().forGetter(bti -> bti.h),
					Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", Boolean.valueOf(false), Lifecycle.stable()).forGetter(bti -> bti.i),
					Codec.BOOL.fieldOf("large_biomes").withDefault(false).stable().forGetter(bti -> bti.j)
				)
				.apply(instance, instance.stable(bti::new))
	);
	private final cxy f;
	private static final List<bre> g = ImmutableList.of(
		brk.a,
		brk.c,
		brk.d,
		brk.e,
		brk.f,
		brk.g,
		brk.h,
		brk.i,
		brk.l,
		brk.m,
		brk.n,
		brk.o,
		brk.p,
		brk.q,
		brk.r,
		brk.s,
		brk.t,
		brk.u,
		brk.v,
		brk.w,
		brk.x,
		brk.y,
		brk.z,
		brk.A,
		brk.B,
		brk.C,
		brk.D,
		brk.E,
		brk.F,
		brk.G,
		brk.H,
		brk.I,
		brk.J,
		brk.K,
		brk.L,
		brk.M,
		brk.N,
		brk.O,
		brk.T,
		brk.U,
		brk.V,
		brk.W,
		brk.X,
		brk.Y,
		brk.Z,
		brk.ab,
		brk.ac,
		brk.ad,
		brk.ae,
		brk.af,
		brk.ag,
		brk.ah,
		brk.ai,
		brk.aj,
		brk.ak,
		brk.al,
		brk.am,
		brk.an,
		brk.ao,
		brk.ap,
		brk.aq,
		brk.ar,
		brk.as,
		brk.at,
		brk.au,
		brk.av
	);
	private final long h;
	private final boolean i;
	private final boolean j;

	public bti(long long1, boolean boolean2, boolean boolean3) {
		super(g);
		this.h = long1;
		this.i = boolean2;
		this.j = boolean3;
		this.f = cxz.a(long1, boolean2, boolean3 ? 6 : 4, 4);
	}

	@Override
	protected Codec<? extends brh> a() {
		return e;
	}

	@Override
	public bre b(int integer1, int integer2, int integer3) {
		return this.f.a(integer1, integer3);
	}
}
