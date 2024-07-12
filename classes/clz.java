import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

public class clz {
	public static void a() {
	}

	static {
		ImmutableList<cvc> immutableList1 = ImmutableList.of(
			new cux(
				ImmutableList.of(
					new cuu(new cvf(acx.o), cue.b, bvs.a.n()),
					new cuu(new cuj(bvs.bL), cue.b, bvs.a.n()),
					new cuu(new cuj(bvs.bM), cue.b, bvs.a.n()),
					new cuu(new cuv(bvs.r, 0.2F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.gl, 0.2F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.N, 0.05F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.Z, 0.05F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.fG, 0.05F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.fJ, 0.05F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.fT, 0.05F), cue.b, bvs.aQ.n()),
					new cuu(new cuv(bvs.dJ, 0.5F), cue.b, bvs.aQ.n()),
					new cuu(
						new cul(bvs.dJ.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true))),
						cue.b,
						bvs.gh.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true))
					),
					new cuu(
						new cul(bvs.dJ.n().a(byt.b, Boolean.valueOf(true)).a(byt.d, Boolean.valueOf(true))),
						cue.b,
						bvs.gh.n().a(byt.b, Boolean.valueOf(true)).a(byt.d, Boolean.valueOf(true))
					),
					new cuu(new cuv(bvs.bW, 0.1F), cue.b, bvs.dO.n())
				)
			)
		);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/town_centers"),
					new uh("empty"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/town_centers/savanna_meeting_point_1"), 100),
						new Pair<>(new cqa("village/savanna/town_centers/savanna_meeting_point_2"), 50),
						new Pair<>(new cqa("village/savanna/town_centers/savanna_meeting_point_3"), 150),
						new Pair<>(new cqa("village/savanna/town_centers/savanna_meeting_point_4"), 150),
						new Pair<>(new cqa("village/savanna/zombie/town_centers/savanna_meeting_point_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/town_centers/savanna_meeting_point_2", immutableList1), 1),
						new Pair<>(new cqa("village/savanna/zombie/town_centers/savanna_meeting_point_3", immutableList1), 3),
						new Pair<>(new cqa("village/savanna/zombie/town_centers/savanna_meeting_point_4", immutableList1), 3)
					),
					cqf.a.RIGID
				)
			);
		ImmutableList<cvc> immutableList2 = ImmutableList.of(
			new cux(
				ImmutableList.of(
					new cuu(new cuj(bvs.iE), new cuj(bvs.A), bvs.r.n()),
					new cuu(new cuv(bvs.iE, 0.2F), cue.b, bvs.i.n()),
					new cuu(new cuj(bvs.i), new cuj(bvs.A), bvs.A.n()),
					new cuu(new cuj(bvs.j), new cuj(bvs.A), bvs.A.n())
				)
			)
		);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/streets"),
					new uh("village/savanna/terminators"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/streets/corner_01", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/corner_03", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/straight_02", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/streets/straight_04", immutableList2), 7),
						new Pair<>(new cqa("village/savanna/streets/straight_05", immutableList2), 3),
						new Pair<>(new cqa("village/savanna/streets/straight_06", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/streets/straight_08", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/streets/straight_09", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/streets/straight_10", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/streets/straight_11", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/streets/crossroad_02", immutableList2), 1),
						new Pair<>(new cqa("village/savanna/streets/crossroad_03", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/crossroad_04", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/crossroad_05", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/crossroad_06", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/crossroad_07", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/split_01", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/split_02", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/streets/turn_01", immutableList2), 3)
					),
					cqf.a.TERRAIN_MATCHING
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/zombie/streets"),
					new uh("village/savanna/zombie/terminators"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/zombie/streets/corner_01", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/corner_03", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_02", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_04", immutableList2), 7),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_05", immutableList2), 3),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_06", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_08", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_09", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_10", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/zombie/streets/straight_11", immutableList2), 4),
						new Pair<>(new cqa("village/savanna/zombie/streets/crossroad_02", immutableList2), 1),
						new Pair<>(new cqa("village/savanna/zombie/streets/crossroad_03", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/crossroad_04", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/crossroad_05", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/crossroad_06", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/crossroad_07", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/split_01", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/split_02", immutableList2), 2),
						new Pair<>(new cqa("village/savanna/zombie/streets/turn_01", immutableList2), 3)
					),
					cqf.a.TERRAIN_MATCHING
				)
			);
		ImmutableList<cvc> immutableList3 = ImmutableList.of(new cux(ImmutableList.of(new cuu(new cuv(bvs.bW, 0.1F), cue.b, bvs.dO.n()))));
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/houses"),
					new uh("village/savanna/terminators"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_2"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_3"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_4"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_5"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_6"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_7"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_house_8"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_medium_house_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_medium_house_2"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_butchers_shop_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_butchers_shop_2"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_tool_smith_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_fletcher_house_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_shepherd_1"), 7),
						new Pair<>(new cqa("village/savanna/houses/savanna_armorer_1"), 1),
						new Pair<>(new cqa("village/savanna/houses/savanna_fisher_cottage_1"), 3),
						new Pair<>(new cqa("village/savanna/houses/savanna_tannery_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_cartographer_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_library_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_mason_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_weaponsmith_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_weaponsmith_2"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_temple_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_temple_2"), 3),
						new Pair<>(new cqa("village/savanna/houses/savanna_large_farm_1", immutableList3), 4),
						new Pair<>(new cqa("village/savanna/houses/savanna_large_farm_2", immutableList3), 6),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_farm", immutableList3), 4),
						new Pair<>(new cqa("village/savanna/houses/savanna_animal_pen_1"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_animal_pen_2"), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_animal_pen_3"), 2),
						Pair.of(cpw.b, 5)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/zombie/houses"),
					new uh("village/savanna/zombie/terminators"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_2", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_3", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_4", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_5", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_6", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_7", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_small_house_8", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_medium_house_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_medium_house_2", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_butchers_shop_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_butchers_shop_2", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_tool_smith_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_fletcher_house_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_shepherd_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_armorer_1", immutableList1), 1),
						new Pair<>(new cqa("village/savanna/houses/savanna_fisher_cottage_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_tannery_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_cartographer_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_library_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_mason_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_weaponsmith_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_weaponsmith_2", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/houses/savanna_temple_1", immutableList1), 1),
						new Pair<>(new cqa("village/savanna/houses/savanna_temple_2", immutableList1), 3),
						new Pair<>(new cqa("village/savanna/houses/savanna_large_farm_1", immutableList1), 4),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_large_farm_2", immutableList1), 4),
						new Pair<>(new cqa("village/savanna/houses/savanna_small_farm", immutableList1), 4),
						new Pair<>(new cqa("village/savanna/houses/savanna_animal_pen_1", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_animal_pen_2", immutableList1), 2),
						new Pair<>(new cqa("village/savanna/zombie/houses/savanna_animal_pen_3", immutableList1), 2),
						Pair.of(cpw.b, 5)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/terminators"),
					new uh("empty"),
					ImmutableList.of(
						new Pair<>(new cqa("village/plains/terminators/terminator_01", immutableList2), 1),
						new Pair<>(new cqa("village/plains/terminators/terminator_02", immutableList2), 1),
						new Pair<>(new cqa("village/plains/terminators/terminator_03", immutableList2), 1),
						new Pair<>(new cqa("village/plains/terminators/terminator_04", immutableList2), 1),
						new Pair<>(new cqa("village/savanna/terminators/terminator_05", immutableList2), 1)
					),
					cqf.a.TERRAIN_MATCHING
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/zombie/terminators"),
					new uh("empty"),
					ImmutableList.of(
						new Pair<>(new cqa("village/plains/terminators/terminator_01", immutableList2), 1),
						new Pair<>(new cqa("village/plains/terminators/terminator_02", immutableList2), 1),
						new Pair<>(new cqa("village/plains/terminators/terminator_03", immutableList2), 1),
						new Pair<>(new cqa("village/plains/terminators/terminator_04", immutableList2), 1),
						new Pair<>(new cqa("village/savanna/zombie/terminators/terminator_05", immutableList2), 1)
					),
					cqf.a.TERRAIN_MATCHING
				)
			);
		cpz.a.a(new cqf(new uh("village/savanna/trees"), new uh("empty"), ImmutableList.of(new Pair<>(new cpx(ckt.c.b(brf.N)), 1)), cqf.a.RIGID));
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/decor"),
					new uh("empty"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/savanna_lamp_post_01"), 4),
						new Pair<>(new cpx(ckt.c.b(brf.N)), 4),
						new Pair<>(new cpx(ckt.f.b(brf.aD)), 4),
						new Pair<>(new cpx(ckt.f.b(brf.aF)), 1),
						Pair.of(cpw.b, 4)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/zombie/decor"),
					new uh("empty"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/savanna_lamp_post_01", immutableList1), 4),
						new Pair<>(new cpx(ckt.c.b(brf.N)), 4),
						new Pair<>(new cpx(ckt.f.b(brf.aD)), 4),
						new Pair<>(new cpx(ckt.f.b(brf.aF)), 1),
						Pair.of(cpw.b, 4)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/villagers"),
					new uh("empty"),
					ImmutableList.of(
						new Pair<>(new cqa("village/savanna/villagers/nitwit"), 1),
						new Pair<>(new cqa("village/savanna/villagers/baby"), 1),
						new Pair<>(new cqa("village/savanna/villagers/unemployed"), 10)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("village/savanna/zombie/villagers"),
					new uh("empty"),
					ImmutableList.of(new Pair<>(new cqa("village/savanna/zombie/villagers/nitwit"), 1), new Pair<>(new cqa("village/savanna/zombie/villagers/unemployed"), 10)),
					cqf.a.RIGID
				)
			);
	}
}
