import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class dw implements ArgumentType<dw.a> {
	public static final SuggestionProvider<cz> a = (commandContext, suggestionsBuilder) -> {
		StringReader stringReader3 = new StringReader(suggestionsBuilder.getInput());
		stringReader3.setCursor(suggestionsBuilder.getStart());
		fa fa4 = new fa(stringReader3);

		try {
			fa4.t();
		} catch (CommandSyntaxException var5) {
		}

		return fa4.a(suggestionsBuilder, suggestionsBuilderx -> db.b(commandContext.getSource().l(), suggestionsBuilderx));
	};
	private static final Collection<String> b = Arrays.asList("Player", "0123", "*", "@e");
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("argument.scoreHolder.empty"));
	private final boolean d;

	public dw(boolean boolean1) {
		this.d = boolean1;
	}

	public static String a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return (String)b(commandContext, string).iterator().next();
	}

	public static Collection<String> b(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return a(commandContext, string, Collections::emptyList);
	}

	public static Collection<String> c(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return a(commandContext, string, commandContext.getSource().j().aF()::e);
	}

	public static Collection<String> a(CommandContext<cz> commandContext, String string, Supplier<Collection<String>> supplier) throws CommandSyntaxException {
		Collection<String> collection4 = commandContext.<dw.a>getArgument(string, dw.a.class).getNames(commandContext.getSource(), supplier);
		if (collection4.isEmpty()) {
			throw dh.d.create();
		} else {
			return collection4;
		}
	}

	public static dw a() {
		return new dw(false);
	}

	public static dw b() {
		return new dw(true);
	}

	public dw.a parse(StringReader stringReader) throws CommandSyntaxException {
		if (stringReader.canRead() && stringReader.peek() == '@') {
			fa fa3 = new fa(stringReader);
			ez ez4 = fa3.t();
			if (!this.d && ez4.a() > 1) {
				throw dh.a.create();
			} else {
				return new dw.b(ez4);
			}
		} else {
			int integer3 = stringReader.getCursor();

			while (stringReader.canRead() && stringReader.peek() != ' ') {
				stringReader.skip();
			}

			String string4 = stringReader.getString().substring(integer3, stringReader.getCursor());
			if (string4.equals("*")) {
				return (cz, supplier) -> {
					Collection<String> collection3 = (Collection<String>)supplier.get();
					if (collection3.isEmpty()) {
						throw c.create();
					} else {
						return collection3;
					}
				};
			} else {
				Collection<String> collection5 = Collections.singleton(string4);
				return (cz, supplier) -> collection5;
			}
		}
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}

	@FunctionalInterface
	public interface a {
		Collection<String> getNames(cz cz, Supplier<Collection<String>> supplier) throws CommandSyntaxException;
	}

	public static class b implements dw.a {
		private final ez a;

		public b(ez ez) {
			this.a = ez;
		}

		@Override
		public Collection<String> getNames(cz cz, Supplier<Collection<String>> supplier) throws CommandSyntaxException {
			List<? extends aom> list4 = this.a.b(cz);
			if (list4.isEmpty()) {
				throw dh.d.create();
			} else {
				List<String> list5 = Lists.<String>newArrayList();

				for (aom aom7 : list4) {
					list5.add(aom7.bT());
				}

				return list5;
			}
		}
	}

	public static class c implements fg<dw> {
		public void a(dw dw, mg mg) {
			byte byte4 = 0;
			if (dw.d) {
				byte4 = (byte)(byte4 | 1);
			}

			mg.writeByte(byte4);
		}

		public dw b(mg mg) {
			byte byte3 = mg.readByte();
			boolean boolean4 = (byte3 & 1) != 0;
			return new dw(boolean4);
		}

		public void a(dw dw, JsonObject jsonObject) {
			jsonObject.addProperty("amount", dw.d ? "multiple" : "single");
		}
	}
}
