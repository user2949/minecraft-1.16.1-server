import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class aho extends DataFix {
	private static final String[] a = DataFixUtils.make(new String[128], arr -> {
		arr[0] = "minecraft:water";
		arr[1] = "minecraft:regeneration";
		arr[2] = "minecraft:swiftness";
		arr[3] = "minecraft:fire_resistance";
		arr[4] = "minecraft:poison";
		arr[5] = "minecraft:healing";
		arr[6] = "minecraft:night_vision";
		arr[7] = null;
		arr[8] = "minecraft:weakness";
		arr[9] = "minecraft:strength";
		arr[10] = "minecraft:slowness";
		arr[11] = "minecraft:leaping";
		arr[12] = "minecraft:harming";
		arr[13] = "minecraft:water_breathing";
		arr[14] = "minecraft:invisibility";
		arr[15] = null;
		arr[16] = "minecraft:awkward";
		arr[17] = "minecraft:regeneration";
		arr[18] = "minecraft:swiftness";
		arr[19] = "minecraft:fire_resistance";
		arr[20] = "minecraft:poison";
		arr[21] = "minecraft:healing";
		arr[22] = "minecraft:night_vision";
		arr[23] = null;
		arr[24] = "minecraft:weakness";
		arr[25] = "minecraft:strength";
		arr[26] = "minecraft:slowness";
		arr[27] = "minecraft:leaping";
		arr[28] = "minecraft:harming";
		arr[29] = "minecraft:water_breathing";
		arr[30] = "minecraft:invisibility";
		arr[31] = null;
		arr[32] = "minecraft:thick";
		arr[33] = "minecraft:strong_regeneration";
		arr[34] = "minecraft:strong_swiftness";
		arr[35] = "minecraft:fire_resistance";
		arr[36] = "minecraft:strong_poison";
		arr[37] = "minecraft:strong_healing";
		arr[38] = "minecraft:night_vision";
		arr[39] = null;
		arr[40] = "minecraft:weakness";
		arr[41] = "minecraft:strong_strength";
		arr[42] = "minecraft:slowness";
		arr[43] = "minecraft:strong_leaping";
		arr[44] = "minecraft:strong_harming";
		arr[45] = "minecraft:water_breathing";
		arr[46] = "minecraft:invisibility";
		arr[47] = null;
		arr[48] = null;
		arr[49] = "minecraft:strong_regeneration";
		arr[50] = "minecraft:strong_swiftness";
		arr[51] = "minecraft:fire_resistance";
		arr[52] = "minecraft:strong_poison";
		arr[53] = "minecraft:strong_healing";
		arr[54] = "minecraft:night_vision";
		arr[55] = null;
		arr[56] = "minecraft:weakness";
		arr[57] = "minecraft:strong_strength";
		arr[58] = "minecraft:slowness";
		arr[59] = "minecraft:strong_leaping";
		arr[60] = "minecraft:strong_harming";
		arr[61] = "minecraft:water_breathing";
		arr[62] = "minecraft:invisibility";
		arr[63] = null;
		arr[64] = "minecraft:mundane";
		arr[65] = "minecraft:long_regeneration";
		arr[66] = "minecraft:long_swiftness";
		arr[67] = "minecraft:long_fire_resistance";
		arr[68] = "minecraft:long_poison";
		arr[69] = "minecraft:healing";
		arr[70] = "minecraft:long_night_vision";
		arr[71] = null;
		arr[72] = "minecraft:long_weakness";
		arr[73] = "minecraft:long_strength";
		arr[74] = "minecraft:long_slowness";
		arr[75] = "minecraft:long_leaping";
		arr[76] = "minecraft:harming";
		arr[77] = "minecraft:long_water_breathing";
		arr[78] = "minecraft:long_invisibility";
		arr[79] = null;
		arr[80] = "minecraft:awkward";
		arr[81] = "minecraft:long_regeneration";
		arr[82] = "minecraft:long_swiftness";
		arr[83] = "minecraft:long_fire_resistance";
		arr[84] = "minecraft:long_poison";
		arr[85] = "minecraft:healing";
		arr[86] = "minecraft:long_night_vision";
		arr[87] = null;
		arr[88] = "minecraft:long_weakness";
		arr[89] = "minecraft:long_strength";
		arr[90] = "minecraft:long_slowness";
		arr[91] = "minecraft:long_leaping";
		arr[92] = "minecraft:harming";
		arr[93] = "minecraft:long_water_breathing";
		arr[94] = "minecraft:long_invisibility";
		arr[95] = null;
		arr[96] = "minecraft:thick";
		arr[97] = "minecraft:regeneration";
		arr[98] = "minecraft:swiftness";
		arr[99] = "minecraft:long_fire_resistance";
		arr[100] = "minecraft:poison";
		arr[101] = "minecraft:strong_healing";
		arr[102] = "minecraft:long_night_vision";
		arr[103] = null;
		arr[104] = "minecraft:long_weakness";
		arr[105] = "minecraft:strength";
		arr[106] = "minecraft:long_slowness";
		arr[107] = "minecraft:leaping";
		arr[108] = "minecraft:strong_harming";
		arr[109] = "minecraft:long_water_breathing";
		arr[110] = "minecraft:long_invisibility";
		arr[111] = null;
		arr[112] = null;
		arr[113] = "minecraft:regeneration";
		arr[114] = "minecraft:swiftness";
		arr[115] = "minecraft:long_fire_resistance";
		arr[116] = "minecraft:poison";
		arr[117] = "minecraft:strong_healing";
		arr[118] = "minecraft:long_night_vision";
		arr[119] = null;
		arr[120] = "minecraft:long_weakness";
		arr[121] = "minecraft:strength";
		arr[122] = "minecraft:long_slowness";
		arr[123] = "minecraft:leaping";
		arr[124] = "minecraft:strong_harming";
		arr[125] = "minecraft:long_water_breathing";
		arr[126] = "minecraft:long_invisibility";
		arr[127] = null;
	});

	public aho(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<?> opticFinder4 = type2.findField("tag");
		return this.fixTypeEverywhereTyped(
			"ItemPotionFix",
			type2,
			typed -> {
				Optional<Pair<String, String>> optional4 = typed.getOptional(opticFinder3);
				if (optional4.isPresent() && Objects.equals(((Pair)optional4.get()).getSecond(), "minecraft:potion")) {
					Dynamic<?> dynamic5 = typed.get(DSL.remainderFinder());
					Optional<? extends Typed<?>> optional6 = typed.getOptionalTyped(opticFinder4);
					short short7 = dynamic5.get("Damage").asShort((short)0);
					if (optional6.isPresent()) {
						Typed<?> typed8 = typed;
						Dynamic<?> dynamic9 = ((Typed)optional6.get()).get(DSL.remainderFinder());
						Optional<String> optional10 = dynamic9.get("Potion").asString().result();
						if (!optional10.isPresent()) {
							String string11 = a[short7 & 127];
							Typed<?> typed12 = ((Typed)optional6.get())
								.set(DSL.remainderFinder(), dynamic9.set("Potion", dynamic9.createString(string11 == null ? "minecraft:water" : string11)));
							typed8 = typed.set(opticFinder4, typed12);
							if ((short7 & 16384) == 16384) {
								typed8 = typed8.set(opticFinder3, Pair.of(ajb.r.typeName(), "minecraft:splash_potion"));
							}
						}
	
						if (short7 != 0) {
							dynamic5 = dynamic5.set("Damage", dynamic5.createShort((short)0));
						}
	
						return typed8.set(DSL.remainderFinder(), dynamic5);
					}
				}
	
				return typed;
			}
		);
	}
}
