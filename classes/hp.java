import java.util.function.Consumer;

public class hp implements Consumer<Consumer<w>> {
	private static final aoq<?>[] a = new aoq[]{
		aoq.H, aoq.o, aoq.aa, aoq.aq, aoq.l, aoq.ab, aoq.ah, aoq.j, aoq.aV, aoq.ac, aoq.an, aoq.Q, aoq.h, aoq.ae, aoq.C, aoq.e, aoq.G, aoq.aE
	};
	private static final bke[] b = new bke[]{bkk.ml, bkk.mn, bkk.mo, bkk.mm};
	private static final bke[] c = new bke[]{bkk.lW, bkk.lX, bkk.lU, bkk.lV};
	private static final bke[] d = new bke[]{
		bkk.ke,
		bkk.kD,
		bkk.kX,
		bkk.lx,
		bkk.ly,
		bkk.lA,
		bkk.lB,
		bkk.ml,
		bkk.mm,
		bkk.mn,
		bkk.mo,
		bkk.mp,
		bkk.mq,
		bkk.ne,
		bkk.nh,
		bkk.nl,
		bkk.nm,
		bkk.nn,
		bkk.no,
		bkk.np,
		bkk.nx,
		bkk.oX,
		bkk.oY,
		bkk.oZ,
		bkk.pa,
		bkk.pc,
		bkk.pm,
		bkk.pw,
		bkk.px,
		bkk.py,
		bkk.pJ,
		bkk.pK,
		bkk.qc,
		bkk.qe,
		bkk.qg,
		bkk.ni,
		bkk.qQ,
		bkk.rl,
		bkk.rs
	};

	public void accept(Consumer<w> consumer) {
		w w3 = w.a.a()
			.a(
				bvs.gA,
				new ne("advancements.husbandry.root.title"),
				new ne("advancements.husbandry.root.description"),
				new uh("textures/gui/advancements/backgrounds/husbandry.png"),
				ag.TASK,
				false,
				false,
				false
			)
			.a("consumed_item", ar.a.c())
			.a(consumer, "husbandry/root");
		w w4 = w.a.a()
			.a(w3)
			.a(bkk.kW, new ne("advancements.husbandry.plant_seed.title"), new ne("advancements.husbandry.plant_seed.description"), null, ag.TASK, true, true, false)
			.a(ah.b)
			.a("wheat", cb.a.a(bvs.bW))
			.a("pumpkin_stem", cb.a.a(bvs.dN))
			.a("melon_stem", cb.a.a(bvs.dO))
			.a("beetroots", cb.a.a(bvs.iD))
			.a("nether_wart", cb.a.a(bvs.dY))
			.a(consumer, "husbandry/plant_seed");
		w w5 = w.a.a()
			.a(w3)
			.a(
				bkk.kW,
				new ne("advancements.husbandry.breed_an_animal.title"),
				new ne("advancements.husbandry.breed_an_animal.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a(ah.b)
			.a("bred", am.a.c())
			.a(consumer, "husbandry/breed_an_animal");
		this.a(w.a.a())
			.a(w4)
			.a(
				bkk.ke,
				new ne("advancements.husbandry.balanced_diet.title"),
				new ne("advancements.husbandry.balanced_diet.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(100))
			.a(consumer, "husbandry/balanced_diet");
		w.a.a()
			.a(w4)
			.a(
				bkk.kU,
				new ne("advancements.husbandry.netherite_hoe.title"),
				new ne("advancements.husbandry.netherite_hoe.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(100))
			.a("netherite_hoe", bl.a.a(bkk.kU))
			.a(consumer, "husbandry/obtain_netherite_hoe");
		w w6 = w.a.a()
			.a(w3)
			.a(
				bkk.pG,
				new ne("advancements.husbandry.tame_an_animal.title"),
				new ne("advancements.husbandry.tame_an_animal.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("tamed_animal", cm.a.c())
			.a(consumer, "husbandry/tame_an_animal");
		this.b(w.a.a())
			.a(w5)
			.a(
				bkk.pc,
				new ne("advancements.husbandry.breed_all_animals.title"),
				new ne("advancements.husbandry.breed_all_animals.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(100))
			.a(consumer, "husbandry/bred_all_animals");
		w w7 = this.d(w.a.a())
			.a(w3)
			.a(ah.b)
			.a(
				bkk.mi,
				new ne("advancements.husbandry.fishy_business.title"),
				new ne("advancements.husbandry.fishy_business.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a(consumer, "husbandry/fishy_business");
		this.c(w.a.a())
			.a(w7)
			.a(ah.b)
			.a(
				bkk.lU,
				new ne("advancements.husbandry.tactical_fishing.title"),
				new ne("advancements.husbandry.tactical_fishing.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a(consumer, "husbandry/tactical_fishing");
		this.e(w.a.a())
			.a(w6)
			.a(
				bkk.ml,
				new ne("advancements.husbandry.complete_catalogue.title"),
				new ne("advancements.husbandry.complete_catalogue.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(50))
			.a(consumer, "husbandry/complete_catalogue");
		w.a.a()
			.a(w3)
			.a("safely_harvest_honey", bp.a.a(bu.a.a().a(al.a.a().a(acx.ai).b()).a(true), bo.a.a().a(bkk.nw)))
			.a(
				bkk.rs,
				new ne("advancements.husbandry.safely_harvest_honey.title"),
				new ne("advancements.husbandry.safely_harvest_honey.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a(consumer, "husbandry/safely_harvest_honey");
		w.a.a()
			.a(w3)
			.a("silk_touch_nest", ak.a.a(bvs.nc, bo.a.a().a(new az(boa.u, bx.d.b(1))), bx.d.a(3)))
			.a(
				bvs.nc,
				new ne("advancements.husbandry.silk_touch_nest.title"),
				new ne("advancements.husbandry.silk_touch_nest.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a(consumer, "husbandry/silk_touch_nest");
	}

	private w.a a(w.a a) {
		for (bke bke6 : d) {
			a.a(gl.am.b(bke6).a(), ar.a.a(bke6));
		}

		return a;
	}

	private w.a b(w.a a) {
		for (aoq<?> aoq6 : hp.a) {
			a.a(aoq.a(aoq6).toString(), am.a.a(be.a.a().a(aoq6)));
		}

		a.a(aoq.a(aoq.aM).toString(), am.a.a(be.a.a().a(aoq.aM).b(), be.a.a().a(aoq.aM).b(), be.a));
		return a;
	}

	private w.a c(w.a a) {
		for (bke bke6 : c) {
			a.a(gl.am.b(bke6).a(), bg.a.a(bo.a.a().a(bke6).b()));
		}

		return a;
	}

	private w.a d(w.a a) {
		for (bke bke6 : b) {
			a.a(gl.am.b(bke6).a(), bi.a.a(bo.a, be.a, bo.a.a().a(bke6).b()));
		}

		return a;
	}

	private w.a e(w.a a) {
		aym.bx.forEach((integer, uh) -> a.a(uh.a(), cm.a.a(be.a.a().a(uh).b())));
		return a;
	}
}
