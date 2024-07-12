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
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ed implements ArgumentType<ed.b> {
	private static final Collection<String> a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("arguments.block.tag.unknown", object));

	public static ed a() {
		return new ed();
	}

	public ed.b parse(StringReader stringReader) throws CommandSyntaxException {
		ef ef3 = new ef(stringReader, true).a(true);
		if (ef3.b() != null) {
			ed.a a4 = new ed.a(ef3.b(), ef3.a().keySet(), ef3.c());
			return adh -> a4;
		} else {
			uh uh4 = ef3.d();
			return adh -> {
				adf<bvr> adf4 = adh.a().a(uh4);
				if (adf4 == null) {
					throw b.create(uh4.toString());
				} else {
					return new ed.c(adf4, ef3.j(), ef3.c());
				}
			};
		}
	}

	public static Predicate<cfn> a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<ed.b>getArgument(string, ed.b.class).create(commandContext.getSource().j().aE());
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
		stringReader4.setCursor(suggestionsBuilder.getStart());
		ef ef5 = new ef(stringReader4, true);

		try {
			ef5.a(true);
		} catch (CommandSyntaxException var6) {
		}

		return ef5.a(suggestionsBuilder, acx.b());
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	static class a implements Predicate<cfn> {
		private final cfj a;
		private final Set<cgl<?>> b;
		@Nullable
		private final le c;

		public a(cfj cfj, Set<cgl<?>> set, @Nullable le le) {
			this.a = cfj;
			this.b = set;
			this.c = le;
		}

		public boolean test(cfn cfn) {
			cfj cfj3 = cfn.a();
			if (!cfj3.a(this.a.b())) {
				return false;
			} else {
				for (cgl<?> cgl5 : this.b) {
					if (cfj3.c(cgl5) != this.a.c(cgl5)) {
						return false;
					}
				}

				if (this.c == null) {
					return true;
				} else {
					cdl cdl4 = cfn.b();
					return cdl4 != null && lq.a(this.c, cdl4.a(new le()), true);
				}
			}
		}
	}

	public interface b {
		Predicate<cfn> create(adh adh) throws CommandSyntaxException;
	}

	static class c implements Predicate<cfn> {
		private final adf<bvr> a;
		@Nullable
		private final le b;
		private final Map<String, String> c;

		private c(adf<bvr> adf, Map<String, String> map, @Nullable le le) {
			this.a = adf;
			this.c = map;
			this.b = le;
		}

		public boolean test(cfn cfn) {
			cfj cfj3 = cfn.a();
			if (!cfj3.a(this.a)) {
				return false;
			} else {
				for (Entry<String, String> entry5 : this.c.entrySet()) {
					cgl<?> cgl6 = cfj3.b().m().a((String)entry5.getKey());
					if (cgl6 == null) {
						return false;
					}

					Comparable<?> comparable7 = (Comparable<?>)cgl6.b((String)entry5.getValue()).orElse(null);
					if (comparable7 == null) {
						return false;
					}

					if (cfj3.c(cgl6) != comparable7) {
						return false;
					}
				}

				if (this.b == null) {
					return true;
				} else {
					cdl cdl4 = cfn.b();
					return cdl4 != null && lq.a(this.b, cdl4.a(new le()), true);
				}
			}
		}
	}
}
