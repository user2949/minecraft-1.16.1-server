import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chv {
	private static final Logger a = LogManager.getLogger();

	public static chr a(zd zd, cva cva, axz axz, bph bph, le le) {
		cha cha6 = zd.i().g();
		brh brh7 = cha6.d();
		le le8 = le.p("Level");
		bph bph9 = new bph(le8.h("xPos"), le8.h("zPos"));
		if (!Objects.equals(bph, bph9)) {
			a.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", bph, bph, bph9);
		}

		cgz cgz10 = new cgz(bph, brh7, le8.c("Biomes", 11) ? le8.n("Biomes") : null);
		cht cht11 = le8.c("UpgradeData", 10) ? new cht(le8.p("UpgradeData")) : cht.a;
		chs<bvr> chs12 = new chs<>(bvr -> bvr == null || bvr.n().g(), bph, le8.d("ToBeTicked", 9));
		chs<cwz> chs13 = new chs<>(cwz -> cwz == null || cwz == cxb.a, bph, le8.d("LiquidsToBeTicked", 9));
		boolean boolean14 = le8.q("isLightOn");
		lk lk15 = le8.d("Sections", 10);
		int integer16 = 16;
		chk[] arr17 = new chk[16];
		boolean boolean18 = zd.m().d();
		chb chb19 = zd.i();
		cwr cwr20 = chb19.l();
		if (boolean14) {
			cwr20.b(bph, true);
		}

		for (int integer21 = 0; integer21 < lk15.size(); integer21++) {
			le le22 = lk15.a(integer21);
			int integer23 = le22.f("Y");
			if (le22.c("Palette", 9) && le22.c("BlockStates", 12)) {
				chk chk24 = new chk(integer23 << 4);
				chk24.i().a(le22.d("Palette", 10), le22.o("BlockStates"));
				chk24.h();
				if (!chk24.c()) {
					arr17[integer23] = chk24;
				}

				axz.a(bph, chk24);
			}

			if (boolean14) {
				if (le22.c("BlockLight", 7)) {
					cwr20.a(bqi.BLOCK, go.a(bph, integer23), new chd(le22.m("BlockLight")), true);
				}

				if (boolean18 && le22.c("SkyLight", 7)) {
					cwr20.a(bqi.SKY, go.a(bph, integer23), new chd(le22.m("SkyLight")), true);
				}
			}
		}

		long long21 = le8.i("InhabitedTime");
		chc.a a23 = a(le);
		cgy cgy24;
		if (a23 == chc.a.LEVELCHUNK) {
			bqr<bvr> bqr25;
			if (le8.c("TileTicks", 9)) {
				bqr25 = bpi.a(le8.d("TileTicks", 10), gl.aj::b, gl.aj::a);
			} else {
				bqr25 = chs12;
			}

			bqr<cwz> bqr26;
			if (le8.c("LiquidTicks", 9)) {
				bqr26 = bpi.a(le8.d("LiquidTicks", 10), gl.ah::b, gl.ah::a);
			} else {
				bqr26 = chs13;
			}

			cgy24 = new chj(zd.n(), bph, cgz10, cht11, bqr25, bqr26, long21, arr17, chj -> a(le8, chj));
		} else {
			chr chr25 = new chr(bph, cht11, arr17, chs12, chs13);
			chr25.a(cgz10);
			cgy24 = chr25;
			chr25.b(long21);
			chr25.a(chc.a(le8.l("Status")));
			if (chr25.k().b(chc.i)) {
				chr25.a(cwr20);
			}

			if (!boolean14 && chr25.k().b(chc.j)) {
				for (fu fu27 : fu.b(bph.d(), 0, bph.e(), bph.f(), 255, bph.g())) {
					if (cgy24.d_(fu27).f() != 0) {
						chr25.j(fu27);
					}
				}
			}
		}

		cgy24.b(boolean14);
		le le25 = le8.p("Heightmaps");
		EnumSet<cio.a> enumSet26 = EnumSet.noneOf(cio.a.class);

		for (cio.a a28 : cgy24.k().h()) {
			String string29 = a28.b();
			if (le25.c(string29, 12)) {
				cgy24.a(a28, le25.o(string29));
			} else {
				enumSet26.add(a28);
			}
		}

		cio.a(cgy24, enumSet26);
		le le27 = le8.p("Structures");
		cgy24.a(a(cva, le27, zd.B()));
		cgy24.b(a(bph, le27));
		if (le8.q("shouldSave")) {
			cgy24.a(true);
		}

		lk lk28 = le8.d("PostProcessing", 9);

		for (int integer29 = 0; integer29 < lk28.size(); integer29++) {
			lk lk30 = lk28.b(integer29);

			for (int integer31 = 0; integer31 < lk30.size(); integer31++) {
				cgy24.a(lk30.d(integer31), integer29);
			}
		}

		if (a23 == chc.a.LEVELCHUNK) {
			return new chi((chj)cgy24);
		} else {
			chr chr29 = (chr)cgy24;
			lk lk30 = le8.d("Entities", 10);

			for (int integer31 = 0; integer31 < lk30.size(); integer31++) {
				chr29.b(lk30.a(integer31));
			}

			lk lk31 = le8.d("TileEntities", 10);

			for (int integer32 = 0; integer32 < lk31.size(); integer32++) {
				le le33 = lk31.a(integer32);
				cgy24.a(le33);
			}

			lk lk32 = le8.d("Lights", 9);

			for (int integer33 = 0; integer33 < lk32.size(); integer33++) {
				lk lk34 = lk32.b(integer33);

				for (int integer35 = 0; integer35 < lk34.size(); integer35++) {
					chr29.b(lk34.d(integer35), integer33);
				}
			}

			le le33 = le8.p("CarvingMasks");

			for (String string35 : le33.d()) {
				cin.a a36 = cin.a.valueOf(string35);
				chr29.a(a36, BitSet.valueOf(le33.m(string35)));
			}

			return chr29;
		}
	}

	public static le a(zd zd, cgy cgy) {
		bph bph3 = cgy.g();
		le le4 = new le();
		le le5 = new le();
		le4.b("DataVersion", u.a().getWorldVersion());
		le4.a("Level", le5);
		le5.b("xPos", bph3.b);
		le5.b("zPos", bph3.c);
		le5.a("LastUpdate", zd.Q());
		le5.a("InhabitedTime", cgy.q());
		le5.a("Status", cgy.k().d());
		cht cht6 = cgy.p();
		if (!cht6.a()) {
			le5.a("UpgradeData", cht6.b());
		}

		chk[] arr7 = cgy.d();
		lk lk8 = new lk();
		cwr cwr9 = zd.i().a();
		boolean boolean10 = cgy.r();

		for (int integer11 = -1; integer11 < 17; integer11++) {
			int integer12 = integer11;
			chk chk13 = (chk)Arrays.stream(arr7).filter(chk -> chk != null && chk.g() >> 4 == integer12).findFirst().orElse(chj.a);
			chd chd14 = cwr9.a(bqi.BLOCK).a(go.a(bph3, integer12));
			chd chd15 = cwr9.a(bqi.SKY).a(go.a(bph3, integer12));
			if (chk13 != chj.a || chd14 != null || chd15 != null) {
				le le16 = new le();
				le16.a("Y", (byte)(integer12 & 0xFF));
				if (chk13 != chj.a) {
					chk13.i().a(le16, "Palette", "BlockStates");
				}

				if (chd14 != null && !chd14.c()) {
					le16.a("BlockLight", chd14.a());
				}

				if (chd15 != null && !chd15.c()) {
					le16.a("SkyLight", chd15.a());
				}

				lk8.add(le16);
			}
		}

		le5.a("Sections", lk8);
		if (boolean10) {
			le5.a("isLightOn", true);
		}

		cgz cgz11 = cgy.i();
		if (cgz11 != null) {
			le5.a("Biomes", cgz11.a());
		}

		lk lk12 = new lk();

		for (fu fu14 : cgy.c()) {
			le le15 = cgy.i(fu14);
			if (le15 != null) {
				lk12.add(le15);
			}
		}

		le5.a("TileEntities", lk12);
		lk lk13 = new lk();
		if (cgy.k().g() == chc.a.LEVELCHUNK) {
			chj chj14 = (chj)cgy;
			chj14.d(false);

			for (int integer15 = 0; integer15 < chj14.z().length; integer15++) {
				for (aom aom17 : chj14.z()[integer15]) {
					le le18 = new le();
					if (aom17.d(le18)) {
						chj14.d(true);
						lk13.add(le18);
					}
				}
			}
		} else {
			chr chr14 = (chr)cgy;
			lk13.addAll(chr14.y());
			le5.a("Lights", a(chr14.w()));
			le le15 = new le();

			for (cin.a a19 : cin.a.values()) {
				BitSet bitSet20 = chr14.a(a19);
				if (bitSet20 != null) {
					le15.a(a19.toString(), bitSet20.toByteArray());
				}
			}

			le5.a("CarvingMasks", le15);
		}

		le5.a("Entities", lk13);
		bqr<bvr> bqr14 = cgy.n();
		if (bqr14 instanceof chs) {
			le5.a("ToBeTicked", ((chs)bqr14).b());
		} else if (bqr14 instanceof bpi) {
			le5.a("TileTicks", ((bpi)bqr14).b());
		} else {
			le5.a("TileTicks", zd.j().a(bph3));
		}

		bqr<cwz> bqr15 = cgy.o();
		if (bqr15 instanceof chs) {
			le5.a("LiquidsToBeTicked", ((chs)bqr15).b());
		} else if (bqr15 instanceof bpi) {
			le5.a("LiquidTicks", ((bpi)bqr15).b());
		} else {
			le5.a("LiquidTicks", zd.k().a(bph3));
		}

		le5.a("PostProcessing", a(cgy.l()));
		le le16x = new le();

		for (Entry<cio.a, cio> entry18 : cgy.f()) {
			if (cgy.k().h().contains(entry18.getKey())) {
				le16x.a(((cio.a)entry18.getKey()).b(), new ll(((cio)entry18.getValue()).a()));
			}
		}

		le5.a("Heightmaps", le16x);
		le5.a("Structures", a(bph3, cgy.h(), cgy.v()));
		return le4;
	}

	public static chc.a a(@Nullable le le) {
		if (le != null) {
			chc chc2 = chc.a(le.p("Level").l("Status"));
			if (chc2 != null) {
				return chc2.g();
			}
		}

		return chc.a.PROTOCHUNK;
	}

	private static void a(le le, chj chj) {
		lk lk3 = le.d("Entities", 10);
		bqb bqb4 = chj.x();

		for (int integer5 = 0; integer5 < lk3.size(); integer5++) {
			le le6 = lk3.a(integer5);
			aoq.a(le6, bqb4, aom -> {
				chj.a(aom);
				return aom;
			});
			chj.d(true);
		}

		lk lk5 = le.d("TileEntities", 10);

		for (int integer6 = 0; integer6 < lk5.size(); integer6++) {
			le le7 = lk5.a(integer6);
			boolean boolean8 = le7.q("keepPacked");
			if (boolean8) {
				chj.a(le7);
			} else {
				fu fu9 = new fu(le7.h("x"), le7.h("y"), le7.h("z"));
				cdl cdl10 = cdl.b(chj.d_(fu9), le7);
				if (cdl10 != null) {
					chj.a(cdl10);
				}
			}
		}
	}

	private static le a(bph bph, Map<cml<?>, ctz<?>> map2, Map<cml<?>, LongSet> map3) {
		le le4 = new le();
		le le5 = new le();

		for (Entry<cml<?>, ctz<?>> entry7 : map2.entrySet()) {
			le5.a(((cml)entry7.getKey()).i(), ((ctz)entry7.getValue()).a(bph.b, bph.c));
		}

		le4.a("Starts", le5);
		le le6 = new le();

		for (Entry<cml<?>, LongSet> entry8 : map3.entrySet()) {
			le6.a(((cml)entry8.getKey()).i(), new ll((LongSet)entry8.getValue()));
		}

		le4.a("References", le6);
		return le4;
	}

	private static Map<cml<?>, ctz<?>> a(cva cva, le le, long long3) {
		Map<cml<?>, ctz<?>> map5 = Maps.<cml<?>, ctz<?>>newHashMap();
		le le6 = le.p("Starts");

		for (String string8 : le6.d()) {
			String string9 = string8.toLowerCase(Locale.ROOT);
			cml<?> cml10 = (cml<?>)cml.a.get(string9);
			if (cml10 == null) {
				a.error("Unknown structure start: {}", string9);
			} else {
				ctz<?> ctz11 = cml.a(cva, le6.p(string8), long3);
				if (ctz11 != null) {
					map5.put(cml10, ctz11);
				}
			}
		}

		return map5;
	}

	private static Map<cml<?>, LongSet> a(bph bph, le le) {
		Map<cml<?>, LongSet> map3 = Maps.<cml<?>, LongSet>newHashMap();
		le le4 = le.p("References");

		for (String string6 : le4.d()) {
			map3.put(cml.a.get(string6.toLowerCase(Locale.ROOT)), new LongOpenHashSet(Arrays.stream(le4.o(string6)).filter(long3 -> {
				bph bph5 = new bph(long3);
				if (bph5.a(bph) > 8) {
					a.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", string6, bph5, bph);
					return false;
				} else {
					return true;
				}
			}).toArray()));
		}

		return map3;
	}

	public static lk a(ShortList[] arr) {
		lk lk2 = new lk();

		for (ShortList shortList6 : arr) {
			lk lk7 = new lk();
			if (shortList6 != null) {
				for (Short short9 : shortList6) {
					lk7.add(ls.a(short9));
				}
			}

			lk2.add(lk7);
		}

		return lk2;
	}
}
