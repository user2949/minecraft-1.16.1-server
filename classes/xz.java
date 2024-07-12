import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dn.h;
import java.util.Locale;
import java.util.function.Function;

public class xz implements xw {
	private static final SuggestionProvider<cz> b = (commandContext, suggestionsBuilder) -> db.a(b(commandContext).a(), suggestionsBuilder);
	public static final Function<String, xx.c> a = string -> new xx.c() {
			@Override
			public xw a(CommandContext<cz> commandContext) {
				return new xz(xz.b(commandContext), dv.e(commandContext, string));
			}

			@Override
			public ArgumentBuilder<cz, ?> a(ArgumentBuilder<cz, ?> argumentBuilder, Function<ArgumentBuilder<cz, ?>, ArgumentBuilder<cz, ?>> function) {
				return argumentBuilder.then(da.a("storage").then((ArgumentBuilder<cz, ?>)function.apply(da.a(string, dv.a()).suggests(xz.b))));
			}
		};
	private final czy c;
	private final uh d;

	private static czy b(CommandContext<cz> commandContext) {
		return commandContext.getSource().j().aG();
	}

	private xz(czy czy, uh uh) {
		this.c = czy;
		this.d = uh;
	}

	@Override
	public void a(le le) {
		this.c.a(this.d, le);
	}

	@Override
	public le a() {
		return this.c.a(this.d);
	}

	@Override
	public mr b() {
		return new ne("commands.data.storage.modified", this.d);
	}

	@Override
	public mr a(lu lu) {
		return new ne("commands.data.storage.query", this.d, lu.l());
	}

	@Override
	public mr a(h h, double double2, int integer) {
		return new ne("commands.data.storage.get", h, this.d, String.format(Locale.ROOT, "%.2f", double2), integer);
	}
}
