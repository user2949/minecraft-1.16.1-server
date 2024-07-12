import java.util.function.Consumer;

public class hr implements Consumer<Consumer<w>> {
	public void accept(Consumer<w> consumer) {
		w w3 = w.a.a()
			.a(
				bvs.i,
				new ne("advancements.story.root.title"),
				new ne("advancements.story.root.description"),
				new uh("textures/gui/advancements/backgrounds/stone.png"),
				ag.TASK,
				false,
				false,
				false
			)
			.a("crafting_table", bl.a.a(bvs.bV))
			.a(consumer, "story/root");
		w w4 = w.a.a()
			.a(w3)
			.a(bkk.kr, new ne("advancements.story.mine_stone.title"), new ne("advancements.story.mine_stone.description"), null, ag.TASK, true, true, false)
			.a("get_stone", bl.a.a(bo.a.a().a(ada.aa).b()))
			.a(consumer, "story/mine_stone");
		w w5 = w.a.a()
			.a(w4)
			.a(bkk.kv, new ne("advancements.story.upgrade_tools.title"), new ne("advancements.story.upgrade_tools.description"), null, ag.TASK, true, true, false)
			.a("stone_pickaxe", bl.a.a(bkk.kv))
			.a(consumer, "story/upgrade_tools");
		w w6 = w.a.a()
			.a(w5)
			.a(bkk.kk, new ne("advancements.story.smelt_iron.title"), new ne("advancements.story.smelt_iron.description"), null, ag.TASK, true, true, false)
			.a("iron", bl.a.a(bkk.kk))
			.a(consumer, "story/smelt_iron");
		w w7 = w.a.a()
			.a(w6)
			.a(bkk.kb, new ne("advancements.story.iron_tools.title"), new ne("advancements.story.iron_tools.description"), null, ag.TASK, true, true, false)
			.a("iron_pickaxe", bl.a.a(bkk.kb))
			.a(consumer, "story/iron_tools");
		w w8 = w.a.a()
			.a(w7)
			.a(bkk.kj, new ne("advancements.story.mine_diamond.title"), new ne("advancements.story.mine_diamond.description"), null, ag.TASK, true, true, false)
			.a("diamond", bl.a.a(bkk.kj))
			.a(consumer, "story/mine_diamond");
		w w9 = w.a.a()
			.a(w6)
			.a(bkk.lM, new ne("advancements.story.lava_bucket.title"), new ne("advancements.story.lava_bucket.description"), null, ag.TASK, true, true, false)
			.a("lava_bucket", bl.a.a(bkk.lM))
			.a(consumer, "story/lava_bucket");
		w w10 = w.a.a()
			.a(w6)
			.a(bkk.lh, new ne("advancements.story.obtain_armor.title"), new ne("advancements.story.obtain_armor.description"), null, ag.TASK, true, true, false)
			.a(ah.b)
			.a("iron_helmet", bl.a.a(bkk.lg))
			.a("iron_chestplate", bl.a.a(bkk.lh))
			.a("iron_leggings", bl.a.a(bkk.li))
			.a("iron_boots", bl.a.a(bkk.lj))
			.a(consumer, "story/obtain_armor");
		w.a.a()
			.a(w8)
			.a(bkk.pp, new ne("advancements.story.enchant_item.title"), new ne("advancements.story.enchant_item.description"), null, ag.TASK, true, true, false)
			.a("enchanted_item", ay.a.c())
			.a(consumer, "story/enchant_item");
		w w11 = w.a.a()
			.a(w9)
			.a(bvs.bK, new ne("advancements.story.form_obsidian.title"), new ne("advancements.story.form_obsidian.description"), null, ag.TASK, true, true, false)
			.a("obsidian", bl.a.a(bvs.bK))
			.a(consumer, "story/form_obsidian");
		w.a.a()
			.a(w10)
			.a(bkk.qm, new ne("advancements.story.deflect_arrow.title"), new ne("advancements.story.deflect_arrow.description"), null, ag.TASK, true, true, false)
			.a("deflected_projectile", bd.a.a(at.a.a().a(au.a.a().a(true)).a(true)))
			.a(consumer, "story/deflect_arrow");
		w.a.a()
			.a(w8)
			.a(bkk.ll, new ne("advancements.story.shiny_gear.title"), new ne("advancements.story.shiny_gear.description"), null, ag.TASK, true, true, false)
			.a(ah.b)
			.a("diamond_helmet", bl.a.a(bkk.lk))
			.a("diamond_chestplate", bl.a.a(bkk.ll))
			.a("diamond_leggings", bl.a.a(bkk.lm))
			.a("diamond_boots", bl.a.a(bkk.ln))
			.a(consumer, "story/shiny_gear");
		w w12 = w.a.a()
			.a(w11)
			.a(bkk.kd, new ne("advancements.story.enter_the_nether.title"), new ne("advancements.story.enter_the_nether.description"), null, ag.TASK, true, true, false)
			.a("entered_nether", ao.a.a(bqb.h))
			.a(consumer, "story/enter_the_nether");
		w.a.a()
			.a(w12)
			.a(
				bkk.lA,
				new ne("advancements.story.cure_zombie_villager.title"),
				new ne("advancements.story.cure_zombie_villager.description"),
				null,
				ag.GOAL,
				true,
				true,
				false
			)
			.a("cured_zombie", as.a.c())
			.a(consumer, "story/cure_zombie_villager");
		w w13 = w.a.a()
			.a(w12)
			.a(bkk.nD, new ne("advancements.story.follow_ender_eye.title"), new ne("advancements.story.follow_ender_eye.description"), null, ag.TASK, true, true, false)
			.a("in_stronghold", bv.a.a(bu.a(cml.k)))
			.a(consumer, "story/follow_ender_eye");
		w.a.a()
			.a(w13)
			.a(bvs.ee, new ne("advancements.story.enter_the_end.title"), new ne("advancements.story.enter_the_end.description"), null, ag.TASK, true, true, false)
			.a("entered_end", ao.a.a(bqb.i))
			.a(consumer, "story/enter_the_end");
	}
}
