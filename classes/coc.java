import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class coc implements cnr {
	public static final Codec<coc> a = RecordCodecBuilder.create(
		instance -> instance.group(
					coc.a.d.fieldOf("target").forGetter(coc -> coc.b),
					cfj.b.fieldOf("state").forGetter(coc -> coc.d),
					Codec.INT.fieldOf("size").withDefault(0).forGetter(coc -> coc.c)
				)
				.apply(instance, coc::new)
	);
	public final coc.a b;
	public final int c;
	public final cfj d;

	public coc(coc.a a, cfj cfj, int integer) {
		this.c = integer;
		this.d = cfj;
		this.b = a;
	}

	public static enum a implements aeh {
		NATURAL_STONE("natural_stone", cfj -> cfj == null ? false : cfj.a(bvs.b) || cfj.a(bvs.c) || cfj.a(bvs.e) || cfj.a(bvs.g)),
		NETHERRACK("netherrack", new cfs(bvs.cL)),
		NETHER_ORE_REPLACEABLES("nether_ore_replaceables", cfj -> cfj == null ? false : cfj.a(bvs.cL) || cfj.a(bvs.cO) || cfj.a(bvs.np));

		public static final Codec<coc.a> d = aeh.a(coc.a::values, coc.a::a);
		private static final Map<String, coc.a> e = (Map<String, coc.a>)Arrays.stream(values()).collect(Collectors.toMap(coc.a::b, a -> a));
		private final String f;
		private final Predicate<cfj> g;

		private a(String string3, Predicate<cfj> predicate) {
			this.f = string3;
			this.g = predicate;
		}

		public String b() {
			return this.f;
		}

		public static coc.a a(String string) {
			return (coc.a)e.get(string);
		}

		public Predicate<cfj> c() {
			return this.g;
		}

		@Override
		public String a() {
			return this.f;
		}
	}
}
