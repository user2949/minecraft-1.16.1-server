import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class cmi extends ckt<coq> {
	private static final LoadingCache<Long, List<cmi.a>> a = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new cmi.b());

	public cmi(Codec<coq> codec) {
		super(codec);
	}

	public static List<cmi.a> a(bqu bqu) {
		Random random2 = new Random(bqu.B());
		long long3 = random2.nextLong() & 65535L;
		return a.getUnchecked(long3);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coq coq) {
		List<cmi.a> list8 = coq.b();
		if (list8.isEmpty()) {
			list8 = a(bqu);
		}

		for (cmi.a a10 : list8) {
			if (a10.a(fu)) {
				this.a(bqu, random, coq, a10);
			}
		}

		return true;
	}

	private void a(bqc bqc, Random random, coq coq, cmi.a a) {
		int integer6 = a.c();

		for (fu fu8 : fu.a(new fu(a.a() - integer6, 0, a.b() - integer6), new fu(a.a() + integer6, a.d() + 10, a.b() + integer6))) {
			if (fu8.a((double)a.a(), (double)fu8.v(), (double)a.b(), false) <= (double)(integer6 * integer6 + 1) && fu8.v() < a.d()) {
				this.a(bqc, fu8, bvs.bK.n());
			} else if (fu8.v() > 65) {
				this.a(bqc, fu8, bvs.a.n());
			}
		}

		if (a.e()) {
			int integer7 = -2;
			int integer8 = 2;
			int integer9 = 3;
			fu.a a10 = new fu.a();

			for (int integer11 = -2; integer11 <= 2; integer11++) {
				for (int integer12 = -2; integer12 <= 2; integer12++) {
					for (int integer13 = 0; integer13 <= 3; integer13++) {
						boolean boolean14 = aec.a(integer11) == 2;
						boolean boolean15 = aec.a(integer12) == 2;
						boolean boolean16 = integer13 == 3;
						if (boolean14 || boolean15 || boolean16) {
							boolean boolean17 = integer11 == -2 || integer11 == 2 || boolean16;
							boolean boolean18 = integer12 == -2 || integer12 == 2 || boolean16;
							cfj cfj19 = bvs.dH
								.n()
								.a(byt.a, Boolean.valueOf(boolean17 && integer12 != -2))
								.a(byt.c, Boolean.valueOf(boolean17 && integer12 != 2))
								.a(byt.d, Boolean.valueOf(boolean18 && integer11 != -2))
								.a(byt.b, Boolean.valueOf(boolean18 && integer11 != 2));
							this.a(bqc, a10.d(a.a() + integer11, a.d() + integer13, a.b() + integer12), cfj19);
						}
					}
				}
			}
		}

		bab bab7 = aoq.s.a(bqc.n());
		bab7.a(coq.c());
		bab7.m(coq.a());
		bab7.b((double)a.a() + 0.5, (double)(a.d() + 1), (double)a.b() + 0.5, random.nextFloat() * 360.0F, 0.0F);
		bqc.c(bab7);
		this.a(bqc, new fu(a.a(), a.d(), a.b()), bvs.z.n());
	}

	public static class a {
		public static final Codec<cmi.a> a = RecordCodecBuilder.create(
			instance -> instance.group(
						Codec.INT.fieldOf("centerX").withDefault(0).forGetter(a -> a.b),
						Codec.INT.fieldOf("centerZ").withDefault(0).forGetter(a -> a.c),
						Codec.INT.fieldOf("radius").withDefault(0).forGetter(a -> a.d),
						Codec.INT.fieldOf("height").withDefault(0).forGetter(a -> a.e),
						Codec.BOOL.fieldOf("guarded").withDefault(false).forGetter(a -> a.f)
					)
					.apply(instance, cmi.a::new)
		);
		private final int b;
		private final int c;
		private final int d;
		private final int e;
		private final boolean f;
		private final deg g;

		public a(int integer1, int integer2, int integer3, int integer4, boolean boolean5) {
			this.b = integer1;
			this.c = integer2;
			this.d = integer3;
			this.e = integer4;
			this.f = boolean5;
			this.g = new deg((double)(integer1 - integer3), 0.0, (double)(integer2 - integer3), (double)(integer1 + integer3), 256.0, (double)(integer2 + integer3));
		}

		public boolean a(fu fu) {
			return fu.u() >> 4 == this.b >> 4 && fu.w() >> 4 == this.c >> 4;
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

		public int d() {
			return this.e;
		}

		public boolean e() {
			return this.f;
		}

		public deg f() {
			return this.g;
		}
	}

	static class b extends CacheLoader<Long, List<cmi.a>> {
		private b() {
		}

		public List<cmi.a> load(Long long1) {
			List<Integer> list3 = (List<Integer>)IntStream.range(0, 10).boxed().collect(Collectors.toList());
			Collections.shuffle(list3, new Random(long1));
			List<cmi.a> list4 = Lists.<cmi.a>newArrayList();

			for (int integer5 = 0; integer5 < 10; integer5++) {
				int integer6 = aec.c(42.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 10) * (double)integer5)));
				int integer7 = aec.c(42.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 10) * (double)integer5)));
				int integer8 = (Integer)list3.get(integer5);
				int integer9 = 2 + integer8 / 3;
				int integer10 = 76 + integer8 * 3;
				boolean boolean11 = integer8 == 1 || integer8 == 2;
				list4.add(new cmi.a(integer6, integer7, integer9, integer10, boolean11));
			}

			return list4;
		}
	}
}
