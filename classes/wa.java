import bpx.f;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

public class wa {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		final LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("gamerule").requires(cz -> cz.c(2));
		bpx.a(
			new bpx.c() {
				@Override
				public <T extends bpx.g<T>> void a(bpx.e<T> e, f<T> f) {
					literalArgumentBuilder2.then(
						da.a(e.a()).executes(commandContext -> wa.b(commandContext.getSource(), e)).then(f.a("value").executes(commandContext -> wa.b(commandContext, e)))
					);
				}
			}
		);
		commandDispatcher.register(literalArgumentBuilder2);
	}

	private static <T extends bpx.g<T>> int b(CommandContext<cz> commandContext, bpx.e<T> e) {
		cz cz3 = commandContext.getSource();
		T g4 = cz3.j().aJ().a(e);
		g4.b(commandContext, "value");
		cz3.a(new ne("commands.gamerule.set", e.a(), g4.toString()), true);
		return g4.c();
	}

	private static <T extends bpx.g<T>> int b(cz cz, bpx.e<T> e) {
		T g3 = cz.j().aJ().a(e);
		cz.a(new ne("commands.gamerule.query", e.a(), g3.toString()), false);
		return g3.c();
	}
}
