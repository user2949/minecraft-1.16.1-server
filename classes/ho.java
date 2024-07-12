import java.util.function.Consumer;

public class ho implements Consumer<Consumer<w>> {
	private static final bre[] a = new bre[]{
		brk.D,
		brk.i,
		brk.h,
		brk.d,
		brk.t,
		brk.I,
		brk.F,
		brk.M,
		brk.f,
		brk.A,
		brk.n,
		brk.u,
		brk.o,
		brk.N,
		brk.K,
		brk.c,
		brk.m,
		brk.H,
		brk.B,
		brk.x,
		brk.y,
		brk.q,
		brk.e,
		brk.s,
		brk.w,
		brk.r,
		brk.L,
		brk.G,
		brk.O,
		brk.E,
		brk.g,
		brk.C,
		brk.p,
		brk.J,
		brk.T,
		brk.U,
		brk.V,
		brk.X,
		brk.Y,
		brk.Z,
		brk.aw,
		brk.ax
	};
	private static final aoq<?>[] b = new aoq[]{
		aoq.f,
		aoq.i,
		aoq.m,
		aoq.q,
		aoq.r,
		aoq.t,
		aoq.u,
		aoq.v,
		aoq.w,
		aoq.D,
		aoq.F,
		aoq.G,
		aoq.I,
		aoq.S,
		aoq.ag,
		aoq.ai,
		aoq.aj,
		aoq.ao,
		aoq.ar,
		aoq.at,
		aoq.au,
		aoq.aw,
		aoq.aB,
		aoq.aD,
		aoq.aN,
		aoq.aP,
		aoq.aR,
		aoq.aT,
		aoq.aS,
		aoq.aW,
		aoq.aZ,
		aoq.aX,
		aoq.ba
	};

	public void accept(Consumer<w> consumer) {
		w w3 = w.a.a()
			.a(
				bkk.pb,
				new ne("advancements.adventure.root.title"),
				new ne("advancements.adventure.root.description"),
				new uh("textures/gui/advancements/backgrounds/adventure.png"),
				ag.TASK,
				false,
				false,
				false
			)
			.a(ah.b)
			.a("killed_something", br.a.c())
			.a("killed_by_something", br.a.d())
			.a(consumer, "adventure/root");
		w w4 = w.a.a()
			.a(w3)
			.a(bvs.aL, new ne("advancements.adventure.sleep_in_bed.title"), new ne("advancements.adventure.sleep_in_bed.description"), null, ag.TASK, true, true, false)
			.a("slept_in_bed", bv.a.c())
			.a(consumer, "adventure/sleep_in_bed");
		a(w.a.a(), a)
			.a(w4)
			.a(
				bkk.ln,
				new ne("advancements.adventure.adventuring_time.title"),
				new ne("advancements.adventure.adventuring_time.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(500))
			.a(consumer, "adventure/adventuring_time");
		w w5 = w.a.a()
			.a(w3)
			.a(bkk.oU, new ne("advancements.adventure.trade.title"), new ne("advancements.adventure.trade.description"), null, ag.TASK, true, true, false)
			.a("traded", cp.a.c())
			.a(consumer, "adventure/trade");
		w w6 = this.a(w.a.a())
			.a(w3)
			.a(bkk.ko, new ne("advancements.adventure.kill_a_mob.title"), new ne("advancements.adventure.kill_a_mob.description"), null, ag.TASK, true, true, false)
			.a(ah.b)
			.a(consumer, "adventure/kill_a_mob");
		this.a(w.a.a())
			.a(w6)
			.a(
				bkk.kx,
				new ne("advancements.adventure.kill_all_mobs.title"),
				new ne("advancements.adventure.kill_all_mobs.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(100))
			.a(consumer, "adventure/kill_all_mobs");
		w w7 = w.a.a()
			.a(w6)
			.a(bkk.kf, new ne("advancements.adventure.shoot_arrow.title"), new ne("advancements.adventure.shoot_arrow.description"), null, ag.TASK, true, true, false)
			.a("shot_arrow", cc.a.a(at.a.a().a(au.a.a().a(true).a(be.a.a().a(acy.d)))))
			.a(consumer, "adventure/shoot_arrow");
		w w8 = w.a.a()
			.a(w6)
			.a(
				bkk.qL, new ne("advancements.adventure.throw_trident.title"), new ne("advancements.adventure.throw_trident.description"), null, ag.TASK, true, true, false
			)
			.a("shot_trident", cc.a.a(at.a.a().a(au.a.a().a(true).a(be.a.a().a(aoq.aJ)))))
			.a(consumer, "adventure/throw_trident");
		w.a.a()
			.a(w8)
			.a(
				bkk.qL,
				new ne("advancements.adventure.very_very_frightening.title"),
				new ne("advancements.adventure.very_very_frightening.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("struck_villager", ap.a.a(be.a.a().a(aoq.aO).b()))
			.a(consumer, "adventure/very_very_frightening");
		w.a.a()
			.a(w5)
			.a(
				bvs.cU,
				new ne("advancements.adventure.summon_iron_golem.title"),
				new ne("advancements.adventure.summon_iron_golem.description"),
				null,
				ag.GOAL,
				true,
				true,
				false
			)
			.a("summoned_golem", cl.a.a(be.a.a().a(aoq.K)))
			.a(consumer, "adventure/summon_iron_golem");
		w.a.a()
			.a(w7)
			.a(
				bkk.kg, new ne("advancements.adventure.sniper_duel.title"), new ne("advancements.adventure.sniper_duel.description"), null, ag.CHALLENGE, true, true, false
			)
			.a(z.a.a(50))
			.a("killed_skeleton", br.a.a(be.a.a().a(aoq.au).a(aw.a(bx.c.b(50.0F))), au.a.a().a(true)))
			.a(consumer, "adventure/sniper_duel");
		w.a.a()
			.a(w6)
			.a(
				bkk.qt,
				new ne("advancements.adventure.totem_of_undying.title"),
				new ne("advancements.adventure.totem_of_undying.description"),
				null,
				ag.GOAL,
				true,
				true,
				false
			)
			.a("used_totem", cr.a.a(bkk.qt))
			.a(consumer, "adventure/totem_of_undying");
		w w9 = w.a.a()
			.a(w3)
			.a(bkk.qP, new ne("advancements.adventure.ol_betsy.title"), new ne("advancements.adventure.ol_betsy.description"), null, ag.TASK, true, true, false)
			.a("shot_crossbow", ch.a.a(bkk.qP))
			.a(consumer, "adventure/ol_betsy");
		w.a.a()
			.a(w9)
			.a(
				bkk.qP,
				new ne("advancements.adventure.whos_the_pillager_now.title"),
				new ne("advancements.adventure.whos_the_pillager_now.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("kill_pillager", bq.a.a(be.a.a().a(aoq.aj)))
			.a(consumer, "adventure/whos_the_pillager_now");
		w.a.a()
			.a(w9)
			.a(
				bkk.qP,
				new ne("advancements.adventure.two_birds_one_arrow.title"),
				new ne("advancements.adventure.two_birds_one_arrow.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				false
			)
			.a(z.a.a(65))
			.a("two_birds", bq.a.a(be.a.a().a(aoq.ag), be.a.a().a(aoq.ag)))
			.a(consumer, "adventure/two_birds_one_arrow");
		w.a.a()
			.a(w9)
			.a(bkk.qP, new ne("advancements.adventure.arbalistic.title"), new ne("advancements.adventure.arbalistic.description"), null, ag.CHALLENGE, true, true, true)
			.a(z.a.a(85))
			.a("arbalistic", bq.a.a(bx.d.a(5)))
			.a(consumer, "adventure/arbalistic");
		w w10 = w.a.a()
			.a(w3)
			.a(
				bfh.s(),
				new ne("advancements.adventure.voluntary_exile.title"),
				new ne("advancements.adventure.voluntary_exile.description"),
				null,
				ag.TASK,
				true,
				true,
				true
			)
			.a("voluntary_exile", br.a.a(be.a.a().a(acy.b).a(bb.b)))
			.a(consumer, "adventure/voluntary_exile");
		w.a.a()
			.a(w10)
			.a(
				bfh.s(),
				new ne("advancements.adventure.hero_of_the_village.title"),
				new ne("advancements.adventure.hero_of_the_village.description"),
				null,
				ag.CHALLENGE,
				true,
				true,
				true
			)
			.a(z.a.a(100))
			.a("hero_of_the_village", bv.a.d())
			.a(consumer, "adventure/hero_of_the_village");
		w.a.a()
			.a(w3)
			.a(
				bvs.ne.h(),
				new ne("advancements.adventure.honey_block_slide.title"),
				new ne("advancements.adventure.honey_block_slide.description"),
				null,
				ag.TASK,
				true,
				true,
				false
			)
			.a("honey_block_slide", cj.a.a(bvs.ne))
			.a(consumer, "adventure/honey_block_slide");
		w.a.a()
			.a(w7)
			.a(bvs.nb.h(), new ne("advancements.adventure.bullseye.title"), new ne("advancements.adventure.bullseye.description"), null, ag.CHALLENGE, true, true, false)
			.a(z.a.a(50))
			.a("bullseye", cn.a.a(bx.d.a(15), be.b.a(be.a.a().a(aw.a(bx.c.b(30.0F))).b())))
			.a(consumer, "adventure/bullseye");
	}

	private w.a a(w.a a) {
		for (aoq<?> aoq6 : b) {
			a.a(gl.al.b(aoq6).toString(), br.a.a(be.a.a().a(aoq6)));
		}

		return a;
	}

	protected static w.a a(w.a a, bre[] arr) {
		for (bre bre6 : arr) {
			a.a(gl.as.b(bre6).toString(), bv.a.a(bu.a(bre6)));
		}

		return a;
	}
}
