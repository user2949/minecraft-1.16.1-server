import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cou implements cnr {
	public static final Codec<cou> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cpo.a.fieldOf("trunk_provider").forGetter(cou -> cou.b),
					cpo.a.fieldOf("leaves_provider").forGetter(cou -> cou.c),
					cpg.d.fieldOf("foliage_placer").forGetter(cou -> cou.f),
					cqw.c.fieldOf("trunk_placer").forGetter(cou -> cou.g),
					cow.a.fieldOf("minimum_size").forGetter(cou -> cou.h),
					cqm.c.listOf().fieldOf("decorators").forGetter(cou -> cou.d),
					Codec.INT.fieldOf("max_water_depth").withDefault(0).forGetter(cou -> cou.i),
					Codec.BOOL.fieldOf("ignore_vines").withDefault(false).forGetter(cou -> cou.j),
					cio.a.g.fieldOf("heightmap").forGetter(cou -> cou.l)
				)
				.apply(instance, cou::new)
	);
	public final cpo b;
	public final cpo c;
	public final List<cqm> d;
	public transient boolean e;
	public final cpg f;
	public final cqw g;
	public final cow h;
	public final int i;
	public final boolean j;
	public final cio.a l;

	protected cou(cpo cpo1, cpo cpo2, cpg cpg, cqw cqw, cow cow, List<cqm> list, int integer, boolean boolean8, cio.a a) {
		this.b = cpo1;
		this.c = cpo2;
		this.d = list;
		this.f = cpg;
		this.h = cow;
		this.g = cqw;
		this.i = integer;
		this.j = boolean8;
		this.l = a;
	}

	public void a() {
		this.e = true;
	}

	public cou a(List<cqm> list) {
		return new cou(this.b, this.c, this.f, this.g, this.h, list, this.i, this.j, this.l);
	}

	public static class a {
		public final cpo a;
		public final cpo b;
		private final cpg c;
		private final cqw d;
		private final cow e;
		private List<cqm> f = ImmutableList.of();
		private int g;
		private boolean h;
		private cio.a i = cio.a.OCEAN_FLOOR;

		public a(cpo cpo1, cpo cpo2, cpg cpg, cqw cqw, cow cow) {
			this.a = cpo1;
			this.b = cpo2;
			this.c = cpg;
			this.d = cqw;
			this.e = cow;
		}

		public cou.a a(List<cqm> list) {
			this.f = list;
			return this;
		}

		public cou.a a(int integer) {
			this.g = integer;
			return this;
		}

		public cou.a a() {
			this.h = true;
			return this;
		}

		public cou.a a(cio.a a) {
			this.i = a;
			return this;
		}

		public cou b() {
			return new cou(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
		}
	}
}
