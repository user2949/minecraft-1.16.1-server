import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class dh implements ArgumentType<ez> {
	private static final Collection<String> g = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.entity.toomany"));
	public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("argument.player.toomany"));
	public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("argument.player.entities"));
	public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("argument.entity.notfound.entity"));
	public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("argument.entity.notfound.player"));
	public static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new ne("argument.entity.selector.not_allowed"));
	private final boolean h;
	private final boolean i;

	protected dh(boolean boolean1, boolean boolean2) {
		this.h = boolean1;
		this.i = boolean2;
	}

	public static dh a() {
		return new dh(true, false);
	}

	public static aom a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<ez>getArgument(string, ez.class).a(commandContext.getSource());
	}

	public static dh b() {
		return new dh(false, false);
	}

	public static Collection<? extends aom> b(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		Collection<? extends aom> collection3 = c(commandContext, string);
		if (collection3.isEmpty()) {
			throw d.create();
		} else {
			return collection3;
		}
	}

	public static Collection<? extends aom> c(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<ez>getArgument(string, ez.class).b(commandContext.getSource());
	}

	public static Collection<ze> d(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<ez>getArgument(string, ez.class).d(commandContext.getSource());
	}

	public static dh c() {
		return new dh(true, true);
	}

	public static ze e(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<ez>getArgument(string, ez.class).c(commandContext.getSource());
	}

	public static dh d() {
		return new dh(false, true);
	}

	public static Collection<ze> f(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		List<ze> list3 = commandContext.<ez>getArgument(string, ez.class).d(commandContext.getSource());
		if (list3.isEmpty()) {
			throw e.create();
		} else {
			return list3;
		}
	}

	public ez parse(StringReader stringReader) throws CommandSyntaxException {
		int integer3 = 0;
		fa fa4 = new fa(stringReader);
		ez ez5 = fa4.t();
		if (ez5.a() > 1 && this.h) {
			if (this.i) {
				stringReader.setCursor(0);
				throw b.createWithContext(stringReader);
			} else {
				stringReader.setCursor(0);
				throw a.createWithContext(stringReader);
			}
		} else if (ez5.b() && this.i && !ez5.c()) {
			stringReader.setCursor(0);
			throw c.createWithContext(stringReader);
		} else {
			return ez5;
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		if (commandContext.getSource() instanceof db) {
			StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
			stringReader4.setCursor(suggestionsBuilder.getStart());
			db db5 = (db)commandContext.getSource();
			fa fa6 = new fa(stringReader4, db5.c(2));

			try {
				fa6.t();
			} catch (CommandSyntaxException var7) {
			}

			return fa6.a(suggestionsBuilder, suggestionsBuilderx -> {
				Collection<String> collection4 = db5.l();
				Iterable<String> iterable5 = (Iterable<String>)(this.i ? collection4 : Iterables.concat(collection4, db5.r()));
				db.b(iterable5, suggestionsBuilderx);
			});
		} else {
			return Suggestions.empty();
		}
	}

	@Override
	public Collection<String> getExamples() {
		return g;
	}

	public static class a implements fg<dh> {
		public void a(dh dh, mg mg) {
			byte byte4 = 0;
			if (dh.h) {
				byte4 = (byte)(byte4 | 1);
			}

			if (dh.i) {
				byte4 = (byte)(byte4 | 2);
			}

			mg.writeByte(byte4);
		}

		public dh b(mg mg) {
			byte byte3 = mg.readByte();
			return new dh((byte3 & 1) != 0, (byte3 & 2) != 0);
		}

		public void a(dh dh, JsonObject jsonObject) {
			jsonObject.addProperty("amount", dh.h ? "single" : "multiple");
			jsonObject.addProperty("type", dh.i ? "players" : "entities");
		}
	}
}
