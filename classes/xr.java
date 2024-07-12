import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class xr {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.trigger.failed.unprimed"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.trigger.failed.invalid"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("trigger")
				.then(
					da.a("objective", dq.a())
						.suggests((commandContext, suggestionsBuilder) -> a(commandContext.getSource(), suggestionsBuilder))
						.executes(commandContext -> a(commandContext.getSource(), a(commandContext.getSource().h(), dq.a(commandContext, "objective"))))
						.then(
							da.a("add")
								.then(
									da.a("value", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													a(commandContext.getSource().h(), dq.a(commandContext, "objective")),
													IntegerArgumentType.getInteger(commandContext, "value")
												)
										)
								)
						)
						.then(
							da.a("set")
								.then(
									da.a("value", IntegerArgumentType.integer())
										.executes(
											commandContext -> b(
													commandContext.getSource(),
													a(commandContext.getSource().h(), dq.a(commandContext, "objective")),
													IntegerArgumentType.getInteger(commandContext, "value")
												)
										)
								)
						)
				)
		);
	}

	public static CompletableFuture<Suggestions> a(cz cz, SuggestionsBuilder suggestionsBuilder) {
		aom aom3 = cz.f();
		List<String> list4 = Lists.<String>newArrayList();
		if (aom3 != null) {
			dfm dfm5 = cz.j().aF();
			String string6 = aom3.bT();

			for (dfj dfj8 : dfm5.c()) {
				if (dfj8.c() == dfp.c && dfm5.b(string6, dfj8)) {
					dfl dfl9 = dfm5.c(string6, dfj8);
					if (!dfl9.g()) {
						list4.add(dfj8.b());
					}
				}
			}
		}

		return db.b(list4, suggestionsBuilder);
	}

	private static int a(cz cz, dfl dfl, int integer) {
		dfl.a(integer);
		cz.a(new ne("commands.trigger.add.success", dfl.d().e(), integer), true);
		return dfl.b();
	}

	private static int b(cz cz, dfl dfl, int integer) {
		dfl.c(integer);
		cz.a(new ne("commands.trigger.set.success", dfl.d().e(), integer), true);
		return integer;
	}

	private static int a(cz cz, dfl dfl) {
		dfl.a(1);
		cz.a(new ne("commands.trigger.simple.success", dfl.d().e()), true);
		return dfl.b();
	}

	private static dfl a(ze ze, dfj dfj) throws CommandSyntaxException {
		if (dfj.c() != dfp.c) {
			throw b.create();
		} else {
			dfm dfm3 = ze.eM();
			String string4 = ze.bT();
			if (!dfm3.b(string4, dfj)) {
				throw a.create();
			} else {
				dfl dfl5 = dfm3.c(string4, dfj);
				if (dfl5.g()) {
					throw a.create();
				} else {
					dfl5.a(true);
					return dfl5;
				}
			}
		}
	}
}
