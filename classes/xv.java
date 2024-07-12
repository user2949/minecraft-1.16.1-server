import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dn.h;
import java.util.Locale;
import java.util.function.Function;

public class xv implements xw {
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.data.block.invalid"));
	public static final Function<String, xx.c> a = string -> new xx.c() {
			@Override
			public xw a(CommandContext<cz> commandContext) throws CommandSyntaxException {
				fu fu3 = eh.a(commandContext, string + "Pos");
				cdl cdl4 = ((cz)commandContext.getSource()).e().c(fu3);
				if (cdl4 == null) {
					throw xv.b.create();
				} else {
					return new xv(cdl4, fu3);
				}
			}

			@Override
			public ArgumentBuilder<cz, ?> a(ArgumentBuilder<cz, ?> argumentBuilder, Function<ArgumentBuilder<cz, ?>, ArgumentBuilder<cz, ?>> function) {
				return argumentBuilder.then(da.a("block").then((ArgumentBuilder<cz, ?>)function.apply(da.a(string + "Pos", eh.a()))));
			}
		};
	private final cdl c;
	private final fu d;

	public xv(cdl cdl, fu fu) {
		this.c = cdl;
		this.d = fu;
	}

	@Override
	public void a(le le) {
		le.b("x", this.d.u());
		le.b("y", this.d.v());
		le.b("z", this.d.w());
		cfj cfj3 = this.c.v().d_(this.d);
		this.c.a(cfj3, le);
		this.c.Z_();
		this.c.v().a(this.d, cfj3, cfj3, 3);
	}

	@Override
	public le a() {
		return this.c.a(new le());
	}

	@Override
	public mr b() {
		return new ne("commands.data.block.modified", this.d.u(), this.d.v(), this.d.w());
	}

	@Override
	public mr a(lu lu) {
		return new ne("commands.data.block.query", this.d.u(), this.d.v(), this.d.w(), lu.l());
	}

	@Override
	public mr a(h h, double double2, int integer) {
		return new ne("commands.data.block.get", h, this.d.u(), this.d.v(), this.d.w(), String.format(Locale.ROOT, "%.2f", double2), integer);
	}
}
