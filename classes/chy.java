public class chy {
	public static chy.a a(le le) {
		int integer2 = le.h("xPos");
		int integer3 = le.h("zPos");
		chy.a a4 = new chy.a(integer2, integer3);
		a4.g = le.m("Blocks");
		a4.f = new chn(le.m("Data"), 7);
		a4.e = new chn(le.m("SkyLight"), 7);
		a4.d = new chn(le.m("BlockLight"), 7);
		a4.c = le.m("HeightMap");
		a4.b = le.q("TerrainPopulated");
		a4.h = le.d("Entities", 10);
		a4.i = le.d("TileEntities", 10);
		a4.j = le.d("TileTicks", 10);

		try {
			a4.a = le.i("LastUpdate");
		} catch (ClassCastException var5) {
			a4.a = (long)le.h("LastUpdate");
		}

		return a4;
	}

	public static void a(chy.a a, le le, brh brh) {
		le.b("xPos", a.k);
		le.b("zPos", a.l);
		le.a("LastUpdate", a.a);
		int[] arr4 = new int[a.c.length];

		for (int integer5 = 0; integer5 < a.c.length; integer5++) {
			arr4[integer5] = a.c[integer5];
		}

		le.a("HeightMap", arr4);
		le.a("TerrainPopulated", a.b);
		lk lk5 = new lk();

		for (int integer6 = 0; integer6 < 8; integer6++) {
			boolean boolean7 = true;

			for (int integer8 = 0; integer8 < 16 && boolean7; integer8++) {
				for (int integer9 = 0; integer9 < 16 && boolean7; integer9++) {
					for (int integer10 = 0; integer10 < 16; integer10++) {
						int integer11 = integer8 << 11 | integer10 << 7 | integer9 + (integer6 << 4);
						int integer12 = a.g[integer11];
						if (integer12 != 0) {
							boolean7 = false;
							break;
						}
					}
				}
			}

			if (!boolean7) {
				byte[] arr8 = new byte[4096];
				chd chd9 = new chd();
				chd chd10 = new chd();
				chd chd11 = new chd();

				for (int integer12 = 0; integer12 < 16; integer12++) {
					for (int integer13 = 0; integer13 < 16; integer13++) {
						for (int integer14 = 0; integer14 < 16; integer14++) {
							int integer15 = integer12 << 11 | integer14 << 7 | integer13 + (integer6 << 4);
							int integer16 = a.g[integer15];
							arr8[integer13 << 8 | integer14 << 4 | integer12] = (byte)(integer16 & 0xFF);
							chd9.a(integer12, integer13, integer14, a.f.a(integer12, integer13 + (integer6 << 4), integer14));
							chd10.a(integer12, integer13, integer14, a.e.a(integer12, integer13 + (integer6 << 4), integer14));
							chd11.a(integer12, integer13, integer14, a.d.a(integer12, integer13 + (integer6 << 4), integer14));
						}
					}
				}

				le le12 = new le();
				le12.a("Y", (byte)(integer6 & 0xFF));
				le12.a("Blocks", arr8);
				le12.a("Data", chd9.a());
				le12.a("SkyLight", chd10.a());
				le12.a("BlockLight", chd11.a());
				lk5.add(le12);
			}
		}

		le.a("Sections", lk5);
		le.a("Biomes", new cgz(new bph(a.k, a.l), brh).a());
		le.a("Entities", a.h);
		le.a("TileEntities", a.i);
		if (a.j != null) {
			le.a("TileTicks", a.j);
		}

		le.a("convertedFromAlphaFormat", true);
	}

	public static class a {
		public long a;
		public boolean b;
		public byte[] c;
		public chn d;
		public chn e;
		public chn f;
		public byte[] g;
		public lk h;
		public lk i;
		public lk j;
		public final int k;
		public final int l;

		public a(int integer1, int integer2) {
			this.k = integer1;
			this.l = integer2;
		}
	}
}
