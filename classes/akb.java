import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class akb extends Schema {
	public akb(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static TypeTemplate a(Schema schema) {
		return DSL.optionalFields("ArmorItems", DSL.list(ajb.l.in(schema)), "HandItems", DSL.list(ajb.l.in(schema)));
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> a(schema)));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		a(schema, map3, "ArmorStand");
		a(schema, map3, "Creeper");
		a(schema, map3, "Skeleton");
		a(schema, map3, "Spider");
		a(schema, map3, "Giant");
		a(schema, map3, "Zombie");
		a(schema, map3, "Slime");
		a(schema, map3, "Ghast");
		a(schema, map3, "PigZombie");
		schema.register(map3, "Enderman", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("carried", ajb.q.in(schema), a(schema))));
		a(schema, map3, "CaveSpider");
		a(schema, map3, "Silverfish");
		a(schema, map3, "Blaze");
		a(schema, map3, "LavaSlime");
		a(schema, map3, "EnderDragon");
		a(schema, map3, "WitherBoss");
		a(schema, map3, "Bat");
		a(schema, map3, "Witch");
		a(schema, map3, "Endermite");
		a(schema, map3, "Guardian");
		a(schema, map3, "Pig");
		a(schema, map3, "Sheep");
		a(schema, map3, "Cow");
		a(schema, map3, "Chicken");
		a(schema, map3, "Squid");
		a(schema, map3, "Wolf");
		a(schema, map3, "MushroomCow");
		a(schema, map3, "SnowMan");
		a(schema, map3, "Ozelot");
		a(schema, map3, "VillagerGolem");
		schema.register(
			map3,
			"EntityHorse",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Items", DSL.list(ajb.l.in(schema)), "ArmorItem", ajb.l.in(schema), "SaddleItem", ajb.l.in(schema), a(schema)
				))
		);
		a(schema, map3, "Rabbit");
		schema.register(
			map3,
			"Villager",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Inventory",
					DSL.list(ajb.l.in(schema)),
					"Offers",
					DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", ajb.l.in(schema), "buyB", ajb.l.in(schema), "sell", ajb.l.in(schema)))),
					a(schema)
				))
		);
		a(schema, map3, "Shulker");
		schema.registerSimple(map3, "AreaEffectCloud");
		schema.registerSimple(map3, "ShulkerBullet");
		return map3;
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(
			false,
			ajb.f,
			() -> DSL.optionalFields(
					"entities",
					DSL.list(DSL.optionalFields("nbt", ajb.o.in(schema))),
					"blocks",
					DSL.list(DSL.optionalFields("nbt", ajb.k.in(schema))),
					"palette",
					DSL.list(ajb.m.in(schema))
				)
		);
		schema.registerType(false, ajb.m, DSL::remainder);
	}
}
