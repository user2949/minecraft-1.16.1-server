import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ea implements ArgumentType<Integer> {
	private static final Collection<String> a = Arrays.asList("0d", "0s", "0t", "0");
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("argument.time.invalid_unit"));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("argument.time.invalid_tick_count", object));
	private static final Object2IntMap<String> d = new Object2IntOpenHashMap<>();

	public static ea a() {
		return new ea();
	}

	public Integer parse(StringReader stringReader) throws CommandSyntaxException {
		float float3 = stringReader.readFloat();
		String string4 = stringReader.readUnquotedString();
		int integer5 = d.getOrDefault(string4, 0);
		if (integer5 == 0) {
			throw b.create();
		} else {
			int integer6 = Math.round(float3 * (float)integer5);
			if (integer6 < 0) {
				throw c.create(integer6);
			} else {
				return integer6;
			}
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		StringReader stringReader4 = new StringReader(suggestionsBuilder.getRemaining());

		try {
			stringReader4.readFloat();
		} catch (CommandSyntaxException var5) {
			return suggestionsBuilder.buildFuture();
		}

		return db.b(d.keySet(), suggestionsBuilder.createOffset(suggestionsBuilder.getStart() + stringReader4.getCursor()));
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	static {
		d.put("d", 24000);
		d.put("s", 20);
		d.put("t", 1);
		d.put("", 1);
	}
}
