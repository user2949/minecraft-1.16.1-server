import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dn.h;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

public class xy implements xw {
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.data.entity.invalid"));
	public static final Function<String, xx.c> a = string -> new xx.c() {
			@Override
			public xw a(CommandContext<cz> commandContext) throws CommandSyntaxException {
				return new xy(dh.a(commandContext, string));
			}

			@Override
			public ArgumentBuilder<cz, ?> a(ArgumentBuilder<cz, ?> argumentBuilder, Function<ArgumentBuilder<cz, ?>, ArgumentBuilder<cz, ?>> function) {
				return argumentBuilder.then(da.a("entity").then((ArgumentBuilder<cz, ?>)function.apply(da.a(string, dh.a()))));
			}
		};
	private final aom c;

	public xy(aom aom) {
		this.c = aom;
	}

	@Override
	public void a(le le) throws CommandSyntaxException {
		if (this.c instanceof bec) {
			throw b.create();
		} else {
			UUID uUID3 = this.c.bR();
			this.c.f(le);
			this.c.a_(uUID3);
		}
	}

	@Override
	public le a() {
		return bz.b(this.c);
	}

	@Override
	public mr b() {
		return new ne("commands.data.entity.modified", this.c.d());
	}

	@Override
	public mr a(lu lu) {
		return new ne("commands.data.entity.query", this.c.d(), lu.l());
	}

	@Override
	public mr a(h h, double double2, int integer) {
		return new ne("commands.data.entity.get", h, this.c.d(), String.format(Locale.ROOT, "%.2f", double2), integer);
	}
}
