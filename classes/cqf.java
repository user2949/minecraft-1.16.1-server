import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cqf {
	private static final Logger d = LogManager.getLogger();
	public static final Codec<cqf> a = RecordCodecBuilder.create(
		instance -> instance.group(
					uh.a.fieldOf("name").forGetter(cqf::b),
					uh.a.fieldOf("fallback").forGetter(cqf::a),
					Codec.mapPair(cqd.e.fieldOf("element"), Codec.INT.fieldOf("weight"))
						.codec()
						.listOf()
						.promotePartial(v.a("Pool element: ", d::error))
						.fieldOf("elements")
						.forGetter(cqf -> cqf.f),
					cqf.a.c.fieldOf("projection").forGetter(cqf -> cqf.i)
				)
				.apply(instance, cqf::new)
	);
	public static final cqf b = new cqf(new uh("empty"), new uh("empty"), ImmutableList.of(), cqf.a.RIGID);
	public static final cqf c = new cqf(new uh("invalid"), new uh("invalid"), ImmutableList.of(), cqf.a.RIGID);
	private final uh e;
	private final ImmutableList<Pair<cqd, Integer>> f;
	private final List<cqd> g;
	private final uh h;
	private final cqf.a i;
	private int j = Integer.MIN_VALUE;

	public cqf(uh uh1, uh uh2, List<Pair<cqd, Integer>> list, cqf.a a) {
		this.e = uh1;
		this.f = ImmutableList.copyOf(list);
		this.g = Lists.<cqd>newArrayList();

		for (Pair<cqd, Integer> pair7 : list) {
			for (int integer8 = 0; integer8 < pair7.getSecond(); integer8++) {
				this.g.add(pair7.getFirst().a(a));
			}
		}

		this.h = uh2;
		this.i = a;
	}

	public int a(cva cva) {
		if (this.j == Integer.MIN_VALUE) {
			this.j = this.g.stream().mapToInt(cqd -> cqd.a(cva, fu.b, cap.NONE).e()).max().orElse(0);
		}

		return this.j;
	}

	public uh a() {
		return this.h;
	}

	public cqd a(Random random) {
		return (cqd)this.g.get(random.nextInt(this.g.size()));
	}

	public List<cqd> b(Random random) {
		return ImmutableList.copyOf(ObjectArrays.shuffle(this.g.toArray(new cqd[0]), random));
	}

	public uh b() {
		return this.e;
	}

	public int c() {
		return this.g.size();
	}

	public static enum a implements aeh {
		TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new cum(cio.a.WORLD_SURFACE_WG, -1))),
		RIGID("rigid", ImmutableList.of());

		public static final Codec<cqf.a> c = aeh.a(cqf.a::values, cqf.a::a);
		private static final Map<String, cqf.a> d = (Map<String, cqf.a>)Arrays.stream(values()).collect(Collectors.toMap(cqf.a::b, a -> a));
		private final String e;
		private final ImmutableList<cvc> f;

		private a(String string3, ImmutableList<cvc> immutableList) {
			this.e = string3;
			this.f = immutableList;
		}

		public String b() {
			return this.e;
		}

		public static cqf.a a(String string) {
			return (cqf.a)d.get(string);
		}

		public ImmutableList<cvc> c() {
			return this.f;
		}

		@Override
		public String a() {
			return this.e;
		}
	}
}
