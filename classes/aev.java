import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;

public class aev extends DataFix {
	private static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("generic.maxHealth", "generic.max_health")
		.put("Max Health", "generic.max_health")
		.put("zombie.spawnReinforcements", "zombie.spawn_reinforcements")
		.put("Spawn Reinforcements Chance", "zombie.spawn_reinforcements")
		.put("horse.jumpStrength", "horse.jump_strength")
		.put("Jump Strength", "horse.jump_strength")
		.put("generic.followRange", "generic.follow_range")
		.put("Follow Range", "generic.follow_range")
		.put("generic.knockbackResistance", "generic.knockback_resistance")
		.put("Knockback Resistance", "generic.knockback_resistance")
		.put("generic.movementSpeed", "generic.movement_speed")
		.put("Movement Speed", "generic.movement_speed")
		.put("generic.flyingSpeed", "generic.flying_speed")
		.put("Flying Speed", "generic.flying_speed")
		.put("generic.attackDamage", "generic.attack_damage")
		.put("generic.attackKnockback", "generic.attack_knockback")
		.put("generic.attackSpeed", "generic.attack_speed")
		.put("generic.armorToughness", "generic.armor_toughness")
		.build();

	public aev(Schema schema) {
		super(schema, false);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<?> opticFinder3 = type2.findField("tag");
		return TypeRewriteRule.seq(
			this.fixTypeEverywhereTyped("Rename ItemStack Attributes", type2, typed -> typed.updateTyped(opticFinder3, aev::a)),
			this.fixTypeEverywhereTyped("Rename Entity Attributes", this.getInputSchema().getType(ajb.p), aev::b),
			this.fixTypeEverywhereTyped("Rename Player Attributes", this.getInputSchema().getType(ajb.b), aev::b)
		);
	}

	private static Dynamic<?> a(Dynamic<?> dynamic) {
		return DataFixUtils.orElse(dynamic.asString().result().map(string -> (String)a.getOrDefault(string, string)).map(dynamic::createString), dynamic);
	}

	private static Typed<?> a(Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			dynamic -> dynamic.update(
					"AttributeModifiers",
					dynamicx -> DataFixUtils.orElse(
							dynamicx.asStreamOpt().result().map(stream -> stream.map(dynamicxx -> dynamicxx.update("AttributeName", aev::a))).map(dynamicx::createList), dynamicx
						)
				)
		);
	}

	private static Typed<?> b(Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			dynamic -> dynamic.update(
					"Attributes",
					dynamicx -> DataFixUtils.orElse(
							dynamicx.asStreamOpt().result().map(stream -> stream.map(dynamicxx -> dynamicxx.update("Name", aev::a))).map(dynamicx::createList), dynamicx
						)
				)
		);
	}
}
