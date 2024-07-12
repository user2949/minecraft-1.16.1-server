import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class agn extends aij {
	private static final Map<String, String> a = DataFixUtils.make(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("donkeykong", "donkey_kong");
		hashMap.put("burningskull", "burning_skull");
		hashMap.put("skullandroses", "skull_and_roses");
	});

	public agn(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityPaintingMotiveFix", ajb.p, "minecraft:painting");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<String> optional3 = dynamic.get("Motive").asString().result();
		if (optional3.isPresent()) {
			String string4 = ((String)optional3.get()).toLowerCase(Locale.ROOT);
			return dynamic.set("Motive", dynamic.createString(new uh((String)a.getOrDefault(string4, string4)).toString()));
		} else {
			return dynamic;
		}
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
