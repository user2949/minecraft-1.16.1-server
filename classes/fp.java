import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;

public class fp implements fg<StringArgumentType> {
	public void a(StringArgumentType stringArgumentType, mg mg) {
		mg.a(stringArgumentType.getType());
	}

	public StringArgumentType b(mg mg) {
		StringType stringType3 = mg.a(StringType.class);
		switch (stringType3) {
			case SINGLE_WORD:
				return StringArgumentType.word();
			case QUOTABLE_PHRASE:
				return StringArgumentType.string();
			case GREEDY_PHRASE:
			default:
				return StringArgumentType.greedyString();
		}
	}

	public void a(StringArgumentType stringArgumentType, JsonObject jsonObject) {
		switch (stringArgumentType.getType()) {
			case SINGLE_WORD:
				jsonObject.addProperty("type", "word");
				break;
			case QUOTABLE_PHRASE:
				jsonObject.addProperty("type", "phrase");
				break;
			case GREEDY_PHRASE:
			default:
				jsonObject.addProperty("type", "greedy");
		}
	}
}
