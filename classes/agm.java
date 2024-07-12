import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class agm extends DataFix {
	private static final int[][] a = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

	public agm(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private Dynamic<?> a(Dynamic<?> dynamic, boolean boolean2, boolean boolean3) {
		if ((boolean2 || boolean3) && !dynamic.get("Facing").asNumber().result().isPresent()) {
			int integer5;
			if (dynamic.get("Direction").asNumber().result().isPresent()) {
				integer5 = dynamic.get("Direction").asByte((byte)0) % a.length;
				int[] arr6 = a[integer5];
				dynamic = dynamic.set("TileX", dynamic.createInt(dynamic.get("TileX").asInt(0) + arr6[0]));
				dynamic = dynamic.set("TileY", dynamic.createInt(dynamic.get("TileY").asInt(0) + arr6[1]));
				dynamic = dynamic.set("TileZ", dynamic.createInt(dynamic.get("TileZ").asInt(0) + arr6[2]));
				dynamic = dynamic.remove("Direction");
				if (boolean3 && dynamic.get("ItemRotation").asNumber().result().isPresent()) {
					dynamic = dynamic.set("ItemRotation", dynamic.createByte((byte)(dynamic.get("ItemRotation").asByte((byte)0) * 2)));
				}
			} else {
				integer5 = dynamic.get("Dir").asByte((byte)0) % a.length;
				dynamic = dynamic.remove("Dir");
			}

			dynamic = dynamic.set("Facing", dynamic.createByte((byte)integer5));
		}

		return dynamic;
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getChoiceType(ajb.p, "Painting");
		OpticFinder<?> opticFinder3 = DSL.namedChoice("Painting", type2);
		Type<?> type4 = this.getInputSchema().getChoiceType(ajb.p, "ItemFrame");
		OpticFinder<?> opticFinder5 = DSL.namedChoice("ItemFrame", type4);
		Type<?> type6 = this.getInputSchema().getType(ajb.p);
		TypeRewriteRule typeRewriteRule7 = this.fixTypeEverywhereTyped(
			"EntityPaintingFix",
			type6,
			typed -> typed.updateTyped(opticFinder3, type2, typedx -> typedx.update(DSL.remainderFinder(), dynamic -> this.a(dynamic, true, false)))
		);
		TypeRewriteRule typeRewriteRule8 = this.fixTypeEverywhereTyped(
			"EntityItemFrameFix",
			type6,
			typed -> typed.updateTyped(opticFinder5, type4, typedx -> typedx.update(DSL.remainderFinder(), dynamic -> this.a(dynamic, false, true)))
		);
		return TypeRewriteRule.seq(typeRewriteRule7, typeRewriteRule8);
	}
}
