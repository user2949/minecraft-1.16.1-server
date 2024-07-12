import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ahl extends DataFix {
	public ahl(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<? extends Dynamic<?>> optional3 = dynamic.get("display").result();
		if (optional3.isPresent()) {
			Dynamic<?> dynamic4 = (Dynamic<?>)optional3.get();
			Optional<String> optional5 = dynamic4.get("Name").asString().result();
			if (optional5.isPresent()) {
				dynamic4 = dynamic4.set("Name", dynamic4.createString(mr.a.a(new nd((String)optional5.get()))));
			} else {
				Optional<String> optional6 = dynamic4.get("LocName").asString().result();
				if (optional6.isPresent()) {
					dynamic4 = dynamic4.set("Name", dynamic4.createString(mr.a.a(new ne((String)optional6.get()))));
					dynamic4 = dynamic4.remove("LocName");
				}
			}

			return dynamic.set("display", dynamic4);
		} else {
			return dynamic;
		}
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<?> opticFinder3 = type2.findField("tag");
		return this.fixTypeEverywhereTyped(
			"ItemCustomNameToComponentFix", type2, typed -> typed.updateTyped(opticFinder3, typedx -> typedx.update(DSL.remainderFinder(), this::a))
		);
	}
}
