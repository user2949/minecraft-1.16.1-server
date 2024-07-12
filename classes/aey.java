import com.mojang.datafixers.schemas.Schema;

public class aey extends aiw {
	public aey(Schema schema) {
		super(schema, false);
	}

	@Override
	protected String a(String string) {
		return string.equals("minecraft:bee_hive") ? "minecraft:beehive" : string;
	}
}
