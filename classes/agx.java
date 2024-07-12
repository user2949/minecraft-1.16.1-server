import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.UUID;

public class agx extends DataFix {
	public agx(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"EntityStringUuidFix",
			this.getInputSchema().getType(ajb.p),
			typed -> typed.update(
					DSL.remainderFinder(),
					dynamic -> {
						Optional<String> optional2 = dynamic.get("UUID").asString().result();
						if (optional2.isPresent()) {
							UUID uUID3 = UUID.fromString((String)optional2.get());
							return dynamic.remove("UUID")
								.set("UUIDMost", dynamic.createLong(uUID3.getMostSignificantBits()))
								.set("UUIDLeast", dynamic.createLong(uUID3.getLeastSignificantBits()));
						} else {
							return dynamic;
						}
					}
				)
		);
	}
}
