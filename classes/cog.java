import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class cog implements cnr {
	public static final Codec<cog> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cpo.a.fieldOf("state_provider").forGetter(cog -> cog.b),
					cmy.a.fieldOf("block_placer").forGetter(cog -> cog.c),
					cfj.b.listOf().fieldOf("whitelist").forGetter(cog -> (List)cog.d.stream().map(bvr::n).collect(Collectors.toList())),
					cfj.b.listOf().fieldOf("blacklist").forGetter(cog -> ImmutableList.copyOf(cog.e)),
					Codec.INT.fieldOf("tries").withDefault(128).forGetter(cog -> cog.f),
					Codec.INT.fieldOf("xspread").withDefault(7).forGetter(cog -> cog.g),
					Codec.INT.fieldOf("yspread").withDefault(3).forGetter(cog -> cog.h),
					Codec.INT.fieldOf("zspread").withDefault(7).forGetter(cog -> cog.i),
					Codec.BOOL.fieldOf("can_replace").withDefault(false).forGetter(cog -> cog.j),
					Codec.BOOL.fieldOf("project").withDefault(true).forGetter(cog -> cog.l),
					Codec.BOOL.fieldOf("need_water").withDefault(false).forGetter(cog -> cog.m)
				)
				.apply(instance, cog::new)
	);
	public final cpo b;
	public final cmy c;
	public final Set<bvr> d;
	public final Set<cfj> e;
	public final int f;
	public final int g;
	public final int h;
	public final int i;
	public final boolean j;
	public final boolean l;
	public final boolean m;

	private cog(
		cpo cpo,
		cmy cmy,
		List<cfj> list3,
		List<cfj> list4,
		int integer5,
		int integer6,
		int integer7,
		int integer8,
		boolean boolean9,
		boolean boolean10,
		boolean boolean11
	) {
		this(
			cpo,
			cmy,
			(Set<bvr>)list3.stream().map(cfi.a::b).collect(Collectors.toSet()),
			ImmutableSet.copyOf(list4),
			integer5,
			integer6,
			integer7,
			integer8,
			boolean9,
			boolean10,
			boolean11
		);
	}

	private cog(
		cpo cpo,
		cmy cmy,
		Set<bvr> set3,
		Set<cfj> set4,
		int integer5,
		int integer6,
		int integer7,
		int integer8,
		boolean boolean9,
		boolean boolean10,
		boolean boolean11
	) {
		this.b = cpo;
		this.c = cmy;
		this.d = set3;
		this.e = set4;
		this.f = integer5;
		this.g = integer6;
		this.h = integer7;
		this.i = integer8;
		this.j = boolean9;
		this.l = boolean10;
		this.m = boolean11;
	}

	public static class a {
		private final cpo a;
		private final cmy b;
		private Set<bvr> c = ImmutableSet.of();
		private Set<cfj> d = ImmutableSet.of();
		private int e = 64;
		private int f = 7;
		private int g = 3;
		private int h = 7;
		private boolean i;
		private boolean j = true;
		private boolean k = false;

		public a(cpo cpo, cmy cmy) {
			this.a = cpo;
			this.b = cmy;
		}

		public cog.a a(Set<bvr> set) {
			this.c = set;
			return this;
		}

		public cog.a b(Set<cfj> set) {
			this.d = set;
			return this;
		}

		public cog.a a(int integer) {
			this.e = integer;
			return this;
		}

		public cog.a b(int integer) {
			this.f = integer;
			return this;
		}

		public cog.a c(int integer) {
			this.g = integer;
			return this;
		}

		public cog.a d(int integer) {
			this.h = integer;
			return this;
		}

		public cog.a a() {
			this.i = true;
			return this;
		}

		public cog.a b() {
			this.j = false;
			return this;
		}

		public cog.a c() {
			this.k = true;
			return this;
		}

		public cog d() {
			return new cog(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
		}
	}
}
