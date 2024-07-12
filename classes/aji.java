import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public abstract class aji extends ags {
	public aji(String string, Schema schema, boolean boolean3) {
		super(string, schema, boolean3);
	}

	@Override
	protected Pair<String, Typed<?>> a(String string, Typed<?> typed) {
		Pair<String, Dynamic<?>> pair4 = this.a(string, typed.getOrCreate(DSL.remainderFinder()));
		return Pair.of(pair4.getFirst(), typed.set(DSL.remainderFinder(), pair4.getSecond()));
	}

	protected abstract Pair<String, Dynamic<?>> a(String string, Dynamic<?> dynamic);
}
