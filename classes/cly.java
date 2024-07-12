import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class cly extends cml<cok> {
	private static final String[] u = new String[]{
		"ruined_portal/portal_1",
		"ruined_portal/portal_2",
		"ruined_portal/portal_3",
		"ruined_portal/portal_4",
		"ruined_portal/portal_5",
		"ruined_portal/portal_6",
		"ruined_portal/portal_7",
		"ruined_portal/portal_8",
		"ruined_portal/portal_9",
		"ruined_portal/portal_10"
	};
	private static final String[] v = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};

	public cly(Codec<cok> codec) {
		super(codec);
	}

	@Override
	public cml.a<cok> a() {
		return cly.a::new;
	}

	private static boolean b(fu fu, bre bre) {
		return bre.b(fu) < 0.15F;
	}

	private static int b(Random random, cha cha, ctt.b b, boolean boolean4, int integer5, int integer6, ctd ctd) {
		int integer8;
		if (b == ctt.b.IN_NETHER) {
			if (boolean4) {
				integer8 = a(random, 32, 100);
			} else if (random.nextFloat() < 0.5F) {
				integer8 = a(random, 27, 29);
			} else {
				integer8 = a(random, 29, 100);
			}
		} else if (b == ctt.b.IN_MOUNTAIN) {
			int integer9 = integer5 - integer6;
			integer8 = b(random, 70, integer9);
		} else if (b == ctt.b.UNDERGROUND) {
			int integer9 = integer5 - integer6;
			integer8 = b(random, 15, integer9);
		} else if (b == ctt.b.PARTLY_BURIED) {
			integer8 = integer5 - integer6 + a(random, 2, 8);
		} else {
			integer8 = integer5;
		}

		List<fu> list9 = ImmutableList.of(new fu(ctd.a, 0, ctd.c), new fu(ctd.d, 0, ctd.c), new fu(ctd.a, 0, ctd.f), new fu(ctd.d, 0, ctd.f));
		List<bpg> list10 = (List<bpg>)list9.stream().map(fu -> cha.a(fu.u(), fu.w())).collect(Collectors.toList());
		cio.a a11 = b == ctt.b.ON_OCEAN_FLOOR ? cio.a.OCEAN_FLOOR_WG : cio.a.WORLD_SURFACE_WG;
		fu.a a12 = new fu.a();

		int integer13;
		for (integer13 = integer8; integer13 > 15; integer13--) {
			int integer14 = 0;
			a12.d(0, integer13, 0);

			for (bpg bpg16 : list10) {
				cfj cfj17 = bpg16.d_(a12);
				if (cfj17 != null && a11.e().test(cfj17)) {
					if (++integer14 == 3) {
						return integer13;
					}
				}
			}
		}

		return integer13;
	}

	private static int a(Random random, int integer2, int integer3) {
		return random.nextInt(integer3 - integer2 + 1) + integer2;
	}

	private static int b(Random random, int integer2, int integer3) {
		return integer2 < integer3 ? a(random, integer2, integer3) : integer3;
	}

	public static class a extends ctz<cok> {
		protected a(cml<cok> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cok cok) {
			ctt.a a9 = new ctt.a();
			ctt.b b8;
			if (cok.b == cly.b.DESERT) {
				b8 = ctt.b.PARTLY_BURIED;
				a9.d = false;
				a9.c = 0.0F;
			} else if (cok.b == cly.b.JUNGLE) {
				b8 = ctt.b.ON_LAND_SURFACE;
				a9.d = this.d.nextFloat() < 0.5F;
				a9.c = 0.8F;
				a9.e = true;
				a9.f = true;
			} else if (cok.b == cly.b.SWAMP) {
				b8 = ctt.b.ON_OCEAN_FLOOR;
				a9.d = false;
				a9.c = 0.5F;
				a9.f = true;
			} else if (cok.b == cly.b.MOUNTAIN) {
				boolean boolean10 = this.d.nextFloat() < 0.5F;
				b8 = boolean10 ? ctt.b.IN_MOUNTAIN : ctt.b.ON_LAND_SURFACE;
				a9.d = boolean10 || this.d.nextFloat() < 0.5F;
			} else if (cok.b == cly.b.OCEAN) {
				b8 = ctt.b.ON_OCEAN_FLOOR;
				a9.d = false;
				a9.c = 0.8F;
			} else if (cok.b == cly.b.NETHER) {
				b8 = ctt.b.IN_NETHER;
				a9.d = this.d.nextFloat() < 0.5F;
				a9.c = 0.0F;
				a9.g = true;
			} else {
				boolean boolean10 = this.d.nextFloat() < 0.5F;
				b8 = boolean10 ? ctt.b.UNDERGROUND : ctt.b.ON_LAND_SURFACE;
				a9.d = boolean10 || this.d.nextFloat() < 0.5F;
			}

			uh uh10;
			if (this.d.nextFloat() < 0.05F) {
				uh10 = new uh(cly.v[this.d.nextInt(cly.v.length)]);
			} else {
				uh10 = new uh(cly.u[this.d.nextInt(cly.u.length)]);
			}

			cve cve11 = cva.a(uh10);
			cap cap12 = v.a(cap.values(), this.d);
			bzj bzj13 = this.d.nextFloat() < 0.5F ? bzj.NONE : bzj.FRONT_BACK;
			fu fu14 = new fu(cve11.a().u() / 2, 0, cve11.a().w() / 2);
			fu fu15 = new bph(integer3, integer4).l();
			ctd ctd16 = cve11.a(fu15, cap12, fu14, bzj13);
			gr gr17 = ctd16.g();
			int integer18 = gr17.u();
			int integer19 = gr17.w();
			int integer20 = cha.a(integer18, integer19, ctt.a(b8)) - 1;
			int integer21 = cly.b(this.d, cha, b8, a9.d, integer20, ctd16.e(), ctd16);
			fu fu22 = new fu(fu15.u(), integer21, fu15.w());
			if (cok.b == cly.b.MOUNTAIN || cok.b == cly.b.OCEAN || cok.b == cly.b.STANDARD) {
				a9.b = cly.b(fu22, bre);
			}

			this.b.add(new ctt(fu22, b8, a9, uh10, cve11, cap12, bzj13, fu14));
			this.b();
		}
	}

	public static enum b implements aeh {
		STANDARD("standard"),
		DESERT("desert"),
		JUNGLE("jungle"),
		SWAMP("swamp"),
		MOUNTAIN("mountain"),
		OCEAN("ocean"),
		NETHER("nether");

		public static final Codec<cly.b> h = aeh.a(cly.b::values, cly.b::a);
		private static final Map<String, cly.b> i = (Map<String, cly.b>)Arrays.stream(values()).collect(Collectors.toMap(cly.b::b, b -> b));
		private final String j;

		private b(String string3) {
			this.j = string3;
		}

		public String b() {
			return this.j;
		}

		public static cly.b a(String string) {
			return (cly.b)i.get(string);
		}

		@Override
		public String a() {
			return this.j;
		}
	}
}
