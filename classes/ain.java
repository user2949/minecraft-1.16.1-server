import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ain extends aij {
	public ain(Schema schema, boolean boolean2) {
		super(schema, boolean2, "OminousBannerBlockEntityRenameFix", ajb.k, "minecraft:banner");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<String> optional3 = dynamic.get("CustomName").asString().result();
		if (optional3.isPresent()) {
			String string4 = (String)optional3.get();
			string4 = string4.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
			return dynamic.set("CustomName", dynamic.createString(string4));
		} else {
			return dynamic;
		}
	}
}
