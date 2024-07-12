import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;

public class agz extends ajj {
	public agz(Schema schema, boolean boolean2) {
		super("EntityTippedArrowFix", schema, boolean2);
	}

	@Override
	protected String a(String string) {
		return Objects.equals(string, "TippedArrow") ? "Arrow" : string;
	}
}
