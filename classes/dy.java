import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class dy implements ArgumentType<Integer> {
	private static final Collection<String> a = Arrays.asList("container.5", "12", "weapon");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("slot.unknown", object));
	private static final Map<String, Integer> c = v.a(Maps.<String, Integer>newHashMap(), hashMap -> {
		for (int integer2 = 0; integer2 < 54; integer2++) {
			hashMap.put("container." + integer2, integer2);
		}

		for (int integer2 = 0; integer2 < 9; integer2++) {
			hashMap.put("hotbar." + integer2, integer2);
		}

		for (int integer2 = 0; integer2 < 27; integer2++) {
			hashMap.put("inventory." + integer2, 9 + integer2);
		}

		for (int integer2 = 0; integer2 < 27; integer2++) {
			hashMap.put("enderchest." + integer2, 200 + integer2);
		}

		for (int integer2 = 0; integer2 < 8; integer2++) {
			hashMap.put("villager." + integer2, 300 + integer2);
		}

		for (int integer2 = 0; integer2 < 15; integer2++) {
			hashMap.put("horse." + integer2, 500 + integer2);
		}

		hashMap.put("weapon", 98);
		hashMap.put("weapon.mainhand", 98);
		hashMap.put("weapon.offhand", 99);
		hashMap.put("armor.head", 100 + aor.HEAD.b());
		hashMap.put("armor.chest", 100 + aor.CHEST.b());
		hashMap.put("armor.legs", 100 + aor.LEGS.b());
		hashMap.put("armor.feet", 100 + aor.FEET.b());
		hashMap.put("horse.saddle", 400);
		hashMap.put("horse.armor", 401);
		hashMap.put("horse.chest", 499);
	});

	public static dy a() {
		return new dy();
	}

	public static int a(CommandContext<cz> commandContext, String string) {
		return commandContext.<Integer>getArgument(string, Integer.class);
	}

	public Integer parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.readUnquotedString();
		if (!c.containsKey(string3)) {
			throw b.create(string3);
		} else {
			return (Integer)c.get(string3);
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.b(c.keySet(), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
