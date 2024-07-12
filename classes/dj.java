import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class dj implements ArgumentType<dj.a> {
	private static final Collection<String> b = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.player.unknown"));

	public static Collection<GameProfile> a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<dj.a>getArgument(string, dj.a.class).getNames(commandContext.getSource());
	}

	public static dj a() {
		return new dj();
	}

	public dj.a parse(StringReader stringReader) throws CommandSyntaxException {
		if (stringReader.canRead() && stringReader.peek() == '@') {
			fa fa3 = new fa(stringReader);
			ez ez4 = fa3.t();
			if (ez4.b()) {
				throw dh.c.create();
			} else {
				return new dj.b(ez4);
			}
		} else {
			int integer3 = stringReader.getCursor();

			while (stringReader.canRead() && stringReader.peek() != ' ') {
				stringReader.skip();
			}

			String string4 = stringReader.getString().substring(integer3, stringReader.getCursor());
			return cz -> {
				GameProfile gameProfile3 = cz.j().ap().a(string4);
				if (gameProfile3 == null) {
					throw a.create();
				} else {
					return Collections.singleton(gameProfile3);
				}
			};
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		if (commandContext.getSource() instanceof db) {
			StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
			stringReader4.setCursor(suggestionsBuilder.getStart());
			fa fa5 = new fa(stringReader4);

			try {
				fa5.t();
			} catch (CommandSyntaxException var6) {
			}

			return fa5.a(suggestionsBuilder, suggestionsBuilderx -> db.b(((db)commandContext.getSource()).l(), suggestionsBuilderx));
		} else {
			return Suggestions.empty();
		}
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}

	@FunctionalInterface
	public interface a {
		Collection<GameProfile> getNames(cz cz) throws CommandSyntaxException;
	}

	public static class b implements dj.a {
		private final ez a;

		public b(ez ez) {
			this.a = ez;
		}

		@Override
		public Collection<GameProfile> getNames(cz cz) throws CommandSyntaxException {
			List<ze> list3 = this.a.d(cz);
			if (list3.isEmpty()) {
				throw dh.e.create();
			} else {
				List<GameProfile> list4 = Lists.<GameProfile>newArrayList();

				for (ze ze6 : list3) {
					list4.add(ze6.ez());
				}

				return list4;
			}
		}
	}
}
