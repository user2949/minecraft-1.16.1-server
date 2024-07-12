import java.util.function.Consumer;

public class hs implements Consumer<Consumer<w>> {
	public void accept(Consumer<w> consumer) {
		w w3 = w.a.a()
			.a(
				bvs.ee,
				new ne("advancements.end.root.title"),
				new ne("advancements.end.root.description"),
				new uh("textures/gui/advancements/backgrounds/end.png"),
				ag.TASK,
				false,
				false,
				false
			)
			.a("entered_end", ao.a.a(bqb.i))
			.a(consumer, "end/root");
		w w4 = w.a.a()
			.a(w3)
			.a(bvs.fm, new ne("advancements.end.kill_dragon.title"), new ne("advancements.end.kill_dragon.description"), null, ag.TASK, true, true, false)
			.a("killed_dragon", br.a.a(be.a.a().a(aoq.t)))
			.a(consumer, "end/kill_dragon");
		w w5 = w.a.a()
			.a(w4)
			.a(bkk.nq, new ne("advancements.end.enter_end_gateway.title"), new ne("advancements.end.enter_end_gateway.description"), null, ag.TASK, true, true, false)
			.a("entered_end_gateway", ba.a.a(bvs.iF))
			.a(consumer, "end/enter_end_gateway");
		w.a.a()
			.a(w4)
			.a(bkk.qb, new ne("advancements.end.respawn_dragon.title"), new ne("advancements.end.respawn_dragon.description"), null, ag.GOAL, true, true, false)
			.a("summoned_dragon", cl.a.a(be.a.a().a(aoq.t)))
			.a(consumer, "end/respawn_dragon");
		w w6 = w.a.a()
			.a(w5)
			.a(bvs.iz, new ne("advancements.end.find_end_city.title"), new ne("advancements.end.find_end_city.description"), null, ag.TASK, true, true, false)
			.a("in_city", bv.a.a(bu.a(cml.o)))
			.a(consumer, "end/find_end_city");
		w.a.a()
			.a(w4)
			.a(bkk.qh, new ne("advancements.end.dragon_breath.title"), new ne("advancements.end.dragon_breath.description"), null, ag.GOAL, true, true, false)
			.a("dragon_breath", bl.a.a(bkk.qh))
			.a(consumer, "end/dragon_breath");
		w.a.a()
			.a(w6)
			.a(bkk.qu, new ne("advancements.end.levitate.title"), new ne("advancements.end.levitate.description"), null, ag.CHALLENGE, true, true, false)
			.a(z.a.a(50))
			.a("levitated", bs.a.a(aw.b(bx.c.b(50.0F))))
			.a(consumer, "end/levitate");
		w.a.a()
			.a(w6)
			.a(bkk.qn, new ne("advancements.end.elytra.title"), new ne("advancements.end.elytra.description"), null, ag.GOAL, true, true, false)
			.a("elytra", bl.a.a(bkk.qn))
			.a(consumer, "end/elytra");
		w.a.a()
			.a(w4)
			.a(bvs.ef, new ne("advancements.end.dragon_egg.title"), new ne("advancements.end.dragon_egg.description"), null, ag.GOAL, true, true, false)
			.a("dragon_egg", bl.a.a(bvs.ef))
			.a(consumer, "end/dragon_egg");
	}
}
