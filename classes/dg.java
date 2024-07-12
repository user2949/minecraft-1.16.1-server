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
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class dg implements ArgumentType<dg.a> {
	private static final Collection<String> a = Arrays.asList("eyes", "feet");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("argument.anchor.invalid", object));

	public static dg.a a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, dg.a.class);
	}

	public static dg a() {
		return new dg();
	}

	public dg.a parse(StringReader stringReader) throws CommandSyntaxException {
		int integer3 = stringReader.getCursor();
		String string4 = stringReader.readUnquotedString();
		dg.a a5 = dg.a.a(string4);
		if (a5 == null) {
			stringReader.setCursor(integer3);
			throw b.createWithContext(stringReader, string4);
		} else {
			return a5;
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.b(dg.a.c.keySet(), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	public static enum a {
		FEET("feet", (dem, aom) -> dem),
		EYES("eyes", (dem, aom) -> new dem(dem.b, dem.c + (double)aom.cd(), dem.d));

		private static final Map<String, dg.a> c = v.a(Maps.<String, dg.a>newHashMap(), hashMap -> {
			for (dg.a a5 : values()) {
				hashMap.put(a5.d, a5);
			}
		});
		private final String d;
		private final BiFunction<dem, aom, dem> e;

		private a(String string3, BiFunction<dem, aom, dem> biFunction) {
			this.d = string3;
			this.e = biFunction;
		}

		@Nullable
		public static dg.a a(String string) {
			return (dg.a)c.get(string);
		}

		public dem a(aom aom) {
			return (dem)this.e.apply(aom.cz(), aom);
		}

		public dem a(cz cz) {
			aom aom3 = cz.f();
			return aom3 == null ? cz.d() : (dem)this.e.apply(cz.d(), aom3);
		}
	}
}
