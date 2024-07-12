import java.util.function.Consumer;

public class hq implements Consumer<Consumer<w>> {
	private static final bre[] a = new bre[]{brk.j, brk.ay, brk.aA, brk.az, brk.aB};
	private static final be.b b = be.b.a(
		ddp.a(dat.c.THIS, be.a.a().a(bb.a.a().a(bo.a.a().a(bkk.lo).b()).b())).a().build(),
		ddp.a(dat.c.THIS, be.a.a().a(bb.a.a().b(bo.a.a().a(bkk.lp).b()).b())).a().build(),
		ddp.a(dat.c.THIS, be.a.a().a(bb.a.a().c(bo.a.a().a(bkk.lq).b()).b())).a().build(),
		ddp.a(dat.c.THIS, be.a.a().a(bb.a.a().d(bo.a.a().a(bkk.lr).b()).b())).a().build()
	);

	public void accept(Consumer<w> consumer) {
		w w3 = w.a.a()
			.a(
				bvs.iL,
				new ne("advancements.nether.root.title"),
				new ne("advancements.nether.root.description"),
				new uh("textures/gui/advancements/backgrounds/nether.png"),
				ag.TASK,
				false,
				false,
				false
			)
			.a("entered_nether", ao.a.a(bqb.h))
			.a(consumer, "nether/root");
		w w4 = w.a.a()
			.a(w3)
			.a(
				bkk.oR,
				new ne("advancements.nether.return_to_sender.title"),
				new ne("advancements.nether.return_to_sender.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(50))
			.a("killed_ghast", br.a.a(be.a.a().a(aoq.D), au.a.a().a(true).a(be.a.a().a(aoq.N))))
			.a(consumer, "nether/return_to_sender");
		w w5 = w.a.a()
			.a(w3)
			.a(bvs.dV, new ne("advancements.nether.find_fortress.title"), new ne("advancements.nether.find_fortress.description"), null, ag.TASK, true, true, false)
			.a("fortress", bv.a.a(bu.a(cml.n)))
			.a(consumer, "nether/find_fortress");
		w.a.a()
			.a(w3)
			.a(bkk.pb, new ne("advancements.nether.fast_travel.title"), new ne("advancements.nether.fast_travel.description"), null, ag.CHALLENGE, true, true, false)
			.a(z.a.a(100))
			.a("travelled", ca.a.a(aw.a(bx.c.b(7000.0F))))
			.a(consumer, "nether/fast_travel");
		w.a.a()
			.a(w4)
			.a(
				bkk.ns,
				new ne("advancements.nether.uneasy_alliance.title"),
				new ne("advancements.nether.uneasy_alliance.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(100))
			.a("killed_ghast", br.a.a(be.a.a().a(aoq.D).a(bu.a(bqb.g))))
			.a(consumer, "nether/uneasy_alliance");
		w w6 = w.a.a()
			.a(w5)
			.a(
				bvs.fe, new ne("advancements.nether.get_wither_skull.title"), new ne("advancements.nether.get_wither_skull.description"), null, ag.TASK, true, true, false
			)
			.a("wither_skull", bl.a.a(bvs.fe))
			.a(consumer, "nether/get_wither_skull");
		w w7 = w.a.a()
			.a(w6)
			.a(bkk.pl, new ne("advancements.nether.summon_wither.title"), new ne("advancements.nether.summon_wither.description"), null, ag.TASK, true, true, false)
			.a("summoned", cl.a.a(be.a.a().a(aoq.aS)))
			.a(consumer, "nether/summon_wither");
		w w8 = w.a.a()
			.a(w5)
			.a(
				bkk.nr, new ne("advancements.nether.obtain_blaze_rod.title"), new ne("advancements.nether.obtain_blaze_rod.description"), null, ag.TASK, true, true, false
			)
			.a("blaze_rod", bl.a.a(bkk.nr))
			.a(consumer, "nether/obtain_blaze_rod");
		w w9 = w.a.a()
			.a(w7)
			.a(bvs.es, new ne("advancements.nether.create_beacon.title"), new ne("advancements.nether.create_beacon.description"), null, ag.TASK, true, true, false)
			.a("beacon", aq.a.a(bx.d.b(1)))
			.a(consumer, "nether/create_beacon");
		w.a.a()
			.a(w9)
			.a(
				bvs.es,
				new ne("advancements.nether.create_full_beacon.title"),
				new ne("advancements.nether.create_full_beacon.description"),
				null,
				ag.GOAL,
				true,
				true,
				false
			)
			.a("beacon", aq.a.a(bx.d.a(4)))
			.a(consumer, "nether/create_full_beacon");
		w w10 = w.a.a()
			.a(w8)
			.a(bkk.nv, new ne("advancements.nether.brew_potion.title"), new ne("advancements.nether.brew_potion.description"), null, ag.TASK, true, true, false)
			.a("potion", an.a.c())
			.a(consumer, "nether/brew_potion");
		w w11 = w.a.a()
			.a(w10)
			.a(bkk.lT, new ne("advancements.nether.all_potions.title"), new ne("advancements.nether.all_potions.description"), null, ag.CHALLENGE, true, true, false)
			.a(z.a.a(100))
			.a("all_effects", ax.a.a(by.a().a(aoi.a).a(aoi.b).a(aoi.e).a(aoi.h).a(aoi.j).a(aoi.l).a(aoi.m).a(aoi.n).a(aoi.p).a(aoi.r).a(aoi.s).a(aoi.B).a(aoi.k)))
			.a(consumer, "nether/all_potions");
		w.a.a()
			.a(w11)
			.a(bkk.lK, new ne("advancements.nether.all_effects.title"), new ne("advancements.nether.all_effects.description"), null, ag.CHALLENGE, true, true, true)
			.a(z.a.a(1000))
			.a(
				"all_effects",
				ax.a.a(
					by.a()
						.a(aoi.a)
						.a(aoi.b)
						.a(aoi.e)
						.a(aoi.h)
						.a(aoi.j)
						.a(aoi.l)
						.a(aoi.m)
						.a(aoi.n)
						.a(aoi.p)
						.a(aoi.r)
						.a(aoi.s)
						.a(aoi.t)
						.a(aoi.c)
						.a(aoi.d)
						.a(aoi.y)
						.a(aoi.x)
						.a(aoi.v)
						.a(aoi.q)
						.a(aoi.i)
						.a(aoi.k)
						.a(aoi.B)
						.a(aoi.C)
						.a(aoi.D)
						.a(aoi.o)
						.a(aoi.E)
						.a(aoi.F)
				)
			)
			.a(consumer, "nether/all_effects");
		w w12 = w.a.a()
			.a(w3)
			.a(
				bkk.rx,
				new ne("advancements.nether.obtain_ancient_debris.title"),
				new ne("advancements.nether.obtain_ancient_debris.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("ancient_debris", bl.a.a(bkk.rx))
			.a(consumer, "nether/obtain_ancient_debris");
		w.a.a()
			.a(w12)
			.a(
				bkk.lt,
				new ne("advancements.nether.netherite_armor.title"),
				new ne("advancements.nether.netherite_armor.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(100))
			.a("netherite_armor", bl.a.a(bkk.ls, bkk.lt, bkk.lu, bkk.lv))
			.a(consumer, "nether/netherite_armor");
		w.a.a()
			.a(w12)
			.a(bkk.rv, new ne("advancements.nether.use_lodestone.title"), new ne("advancements.nether.use_lodestone.description"), null, ag.TASK, true, true, false)
			.a("use_lodestone", bp.a.a(bu.a.a().a(al.a.a().a(bvs.no).b()), bo.a.a().a(bkk.mh)))
			.a(consumer, "nether/use_lodestone");
		w w13 = w.a.a()
			.a(w3)
			.a(
				bkk.rz,
				new ne("advancements.nether.obtain_crying_obsidian.title"),
				new ne("advancements.nether.obtain_crying_obsidian.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("crying_obsidian", bl.a.a(bkk.rz))
			.a(consumer, "nether/obtain_crying_obsidian");
		w.a.a()
			.a(w13)
			.a(
				bkk.rM,
				new ne("advancements.nether.charge_respawn_anchor.title"),
				new ne("advancements.nether.charge_respawn_anchor.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("charge_respawn_anchor", bp.a.a(bu.a.a().a(al.a.a().a(bvs.nj).a(ck.a.a().a(cam.a, 4).b()).b()), bo.a.a().a(bvs.cS)))
			.a(consumer, "nether/charge_respawn_anchor");
		w w14 = w.a.a()
			.a(w3)
			.a(bkk.pk, new ne("advancements.nether.ride_strider.title"), new ne("advancements.nether.ride_strider.description"), null, ag.TASK, true, true, false)
			.a("used_warped_fungus_on_a_stick", bm.a.a(be.b.a(be.a.a().a(be.a.a().a(aoq.aE).b()).b()), bo.a.a().a(bkk.pk).b(), bx.d.e))
			.a(consumer, "nether/ride_strider");
		ho.a(w.a.a(), a)
			.a(w14)
			.a(
				bkk.lv, new ne("advancements.nether.explore_nether.title"), new ne("advancements.nether.explore_nether.description"), null, ag.CHALLENGE, true, true, false
			)
			.a(z.a.a(500))
			.a(consumer, "nether/explore_nether");
		w w15 = w.a.a()
			.a(w3)
			.a(bkk.rI, new ne("advancements.nether.find_bastion.title"), new ne("advancements.nether.find_bastion.description"), null, ag.TASK, true, true, false)
			.a("bastion", bv.a.a(bu.a(cml.s)))
			.a(consumer, "nether/find_bastion");
		w.a.a()
			.a(w15)
			.a(bvs.bR, new ne("advancements.nether.loot_bastion.title"), new ne("advancements.nether.loot_bastion.description"), null, ag.TASK, true, true, false)
			.a(ah.b)
			.a("loot_bastion_other", bw.a.a(new uh("minecraft:chests/bastion_other")))
			.a("loot_bastion_treasure", bw.a.a(new uh("minecraft:chests/bastion_treasure")))
			.a("loot_bastion_hoglin_stable", bw.a.a(new uh("minecraft:chests/bastion_hoglin_stable")))
			.a("loot_bastion_bridge", bw.a.a(new uh("minecraft:chests/bastion_bridge")))
			.a(consumer, "nether/loot_bastion");
		w.a.a()
			.a(w3)
			.a(ah.b)
			.a(bkk.kl, new ne("advancements.nether.distract_piglin.title"), new ne("advancements.nether.distract_piglin.description"), null, ag.TASK, true, true, false)
			.a("distract_piglin", bn.a.a(b, bo.a.a().a(ada.N), be.b.a(be.a.a().a(aoq.ai).a(bc.a.a().e(false).b()).b())))
			.a("distract_piglin_directly", cd.a.a(b, bo.a.a().a(bdd.a), be.b.a(be.a.a().a(aoq.ai).a(bc.a.a().e(false).b()).b())))
			.a(consumer, "nether/distract_piglin");
	}
}
