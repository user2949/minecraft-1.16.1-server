import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class aha extends aer {
	private static final Set<String> c = Sets.<String>newHashSet();
	private static final Set<String> d = Sets.<String>newHashSet();
	private static final Set<String> e = Sets.<String>newHashSet();
	private static final Set<String> f = Sets.<String>newHashSet();
	private static final Set<String> g = Sets.<String>newHashSet();
	private static final Set<String> h = Sets.<String>newHashSet();

	public aha(Schema schema) {
		super(schema, ajb.p);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.b), typed -> {
			typed = typed.update(DSL.remainderFinder(), aha::c);

			for (String string4 : c) {
				typed = this.a(typed, string4, aha::l);
			}

			for (String string4 : d) {
				typed = this.a(typed, string4, aha::l);
			}

			for (String string4 : e) {
				typed = this.a(typed, string4, aha::m);
			}

			for (String string4 : f) {
				typed = this.a(typed, string4, aha::n);
			}

			for (String string4 : g) {
				typed = this.a(typed, string4, aha::b);
			}

			for (String string4 : h) {
				typed = this.a(typed, string4, aha::o);
			}

			typed = this.a(typed, "minecraft:bee", aha::k);
			typed = this.a(typed, "minecraft:zombified_piglin", aha::k);
			typed = this.a(typed, "minecraft:fox", aha::j);
			typed = this.a(typed, "minecraft:item", aha::i);
			typed = this.a(typed, "minecraft:shulker_bullet", aha::h);
			typed = this.a(typed, "minecraft:area_effect_cloud", aha::g);
			typed = this.a(typed, "minecraft:zombie_villager", aha::f);
			typed = this.a(typed, "minecraft:evoker_fangs", aha::e);
			return this.a(typed, "minecraft:piglin", aha::d);
		});
	}

	private static Dynamic<?> d(Dynamic<?> dynamic) {
		return dynamic.update(
			"Brain",
			dynamicx -> dynamicx.update(
					"memories", dynamicxx -> dynamicxx.update("minecraft:angry_at", dynamicxxx -> (Dynamic)a(dynamicxxx, "value", "value").orElseGet(() -> {
								a.warn("angry_at has no value.");
								return dynamicxxx;
							}))
				)
		);
	}

	private static Dynamic<?> e(Dynamic<?> dynamic) {
		return (Dynamic<?>)c(dynamic, "OwnerUUID", "Owner").orElse(dynamic);
	}

	private static Dynamic<?> f(Dynamic<?> dynamic) {
		return (Dynamic<?>)c(dynamic, "ConversionPlayer", "ConversionPlayer").orElse(dynamic);
	}

	private static Dynamic<?> g(Dynamic<?> dynamic) {
		return (Dynamic<?>)c(dynamic, "OwnerUUID", "Owner").orElse(dynamic);
	}

	private static Dynamic<?> h(Dynamic<?> dynamic) {
		dynamic = (Dynamic<?>)b(dynamic, "Owner", "Owner").orElse(dynamic);
		return (Dynamic<?>)b(dynamic, "Target", "Target").orElse(dynamic);
	}

	private static Dynamic<?> i(Dynamic<?> dynamic) {
		dynamic = (Dynamic<?>)b(dynamic, "Owner", "Owner").orElse(dynamic);
		return (Dynamic<?>)b(dynamic, "Thrower", "Thrower").orElse(dynamic);
	}

	private static Dynamic<?> j(Dynamic<?> dynamic) {
		Optional<Dynamic<?>> optional2 = dynamic.get("TrustedUUIDs")
			.result()
			.map(dynamic2 -> dynamic.createList(dynamic2.asStream().map(dynamicx -> (Dynamic)a(dynamicx).orElseGet(() -> {
						a.warn("Trusted contained invalid data.");
						return dynamicx;
					}))));
		return DataFixUtils.orElse(optional2.map(dynamic2 -> dynamic.remove("TrustedUUIDs").set("Trusted", dynamic2)), dynamic);
	}

	private static Dynamic<?> k(Dynamic<?> dynamic) {
		return (Dynamic<?>)a(dynamic, "HurtBy", "HurtBy").orElse(dynamic);
	}

	private static Dynamic<?> l(Dynamic<?> dynamic) {
		Dynamic<?> dynamic2 = m(dynamic);
		return (Dynamic<?>)a(dynamic2, "OwnerUUID", "Owner").orElse(dynamic2);
	}

	private static Dynamic<?> m(Dynamic<?> dynamic) {
		Dynamic<?> dynamic2 = n(dynamic);
		return (Dynamic<?>)c(dynamic2, "LoveCause", "LoveCause").orElse(dynamic2);
	}

	private static Dynamic<?> n(Dynamic<?> dynamic) {
		return b(dynamic).update("Leash", dynamicx -> (Dynamic)c(dynamicx, "UUID", "UUID").orElse(dynamicx));
	}

	public static Dynamic<?> b(Dynamic<?> dynamic) {
		return dynamic.update(
			"Attributes",
			dynamic2 -> dynamic.createList(
					dynamic2.asStream()
						.map(
							dynamicx -> dynamicx.update(
									"Modifiers", dynamic2x -> dynamicx.createList(dynamic2x.asStream().map(dynamicxx -> (Dynamic)c(dynamicxx, "UUID", "UUID").orElse(dynamicxx)))
								)
						)
				)
		);
	}

	private static Dynamic<?> o(Dynamic<?> dynamic) {
		return DataFixUtils.orElse(dynamic.get("OwnerUUID").result().map(dynamic2 -> dynamic.remove("OwnerUUID").set("Owner", dynamic2)), dynamic);
	}

	public static Dynamic<?> c(Dynamic<?> dynamic) {
		return (Dynamic<?>)c(dynamic, "UUID", "UUID").orElse(dynamic);
	}

	static {
		c.add("minecraft:donkey");
		c.add("minecraft:horse");
		c.add("minecraft:llama");
		c.add("minecraft:mule");
		c.add("minecraft:skeleton_horse");
		c.add("minecraft:trader_llama");
		c.add("minecraft:zombie_horse");
		d.add("minecraft:cat");
		d.add("minecraft:parrot");
		d.add("minecraft:wolf");
		e.add("minecraft:bee");
		e.add("minecraft:chicken");
		e.add("minecraft:cow");
		e.add("minecraft:fox");
		e.add("minecraft:mooshroom");
		e.add("minecraft:ocelot");
		e.add("minecraft:panda");
		e.add("minecraft:pig");
		e.add("minecraft:polar_bear");
		e.add("minecraft:rabbit");
		e.add("minecraft:sheep");
		e.add("minecraft:turtle");
		e.add("minecraft:hoglin");
		f.add("minecraft:bat");
		f.add("minecraft:blaze");
		f.add("minecraft:cave_spider");
		f.add("minecraft:cod");
		f.add("minecraft:creeper");
		f.add("minecraft:dolphin");
		f.add("minecraft:drowned");
		f.add("minecraft:elder_guardian");
		f.add("minecraft:ender_dragon");
		f.add("minecraft:enderman");
		f.add("minecraft:endermite");
		f.add("minecraft:evoker");
		f.add("minecraft:ghast");
		f.add("minecraft:giant");
		f.add("minecraft:guardian");
		f.add("minecraft:husk");
		f.add("minecraft:illusioner");
		f.add("minecraft:magma_cube");
		f.add("minecraft:pufferfish");
		f.add("minecraft:zombified_piglin");
		f.add("minecraft:salmon");
		f.add("minecraft:shulker");
		f.add("minecraft:silverfish");
		f.add("minecraft:skeleton");
		f.add("minecraft:slime");
		f.add("minecraft:snow_golem");
		f.add("minecraft:spider");
		f.add("minecraft:squid");
		f.add("minecraft:stray");
		f.add("minecraft:tropical_fish");
		f.add("minecraft:vex");
		f.add("minecraft:villager");
		f.add("minecraft:iron_golem");
		f.add("minecraft:vindicator");
		f.add("minecraft:pillager");
		f.add("minecraft:wandering_trader");
		f.add("minecraft:witch");
		f.add("minecraft:wither");
		f.add("minecraft:wither_skeleton");
		f.add("minecraft:zombie");
		f.add("minecraft:zombie_villager");
		f.add("minecraft:phantom");
		f.add("minecraft:ravager");
		f.add("minecraft:piglin");
		g.add("minecraft:armor_stand");
		h.add("minecraft:arrow");
		h.add("minecraft:dragon_fireball");
		h.add("minecraft:firework_rocket");
		h.add("minecraft:fireball");
		h.add("minecraft:llama_spit");
		h.add("minecraft:small_fireball");
		h.add("minecraft:snowball");
		h.add("minecraft:spectral_arrow");
		h.add("minecraft:egg");
		h.add("minecraft:ender_pearl");
		h.add("minecraft:experience_bottle");
		h.add("minecraft:potion");
		h.add("minecraft:trident");
		h.add("minecraft:wither_skull");
	}
}
