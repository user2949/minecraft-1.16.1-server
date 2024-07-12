import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class xj {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.summon.failed"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.summon.invalidPosition"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("summon")
				.requires(cz -> cz.c(2))
				.then(
					da.a("entity", di.a())
						.suggests(fj.e)
						.executes(commandContext -> a(commandContext.getSource(), di.a(commandContext, "entity"), commandContext.getSource().d(), new le(), true))
						.then(
							da.a("pos", eo.a())
								.executes(commandContext -> a(commandContext.getSource(), di.a(commandContext, "entity"), eo.a(commandContext, "pos"), new le(), true))
								.then(
									da.a("nbt", de.a())
										.executes(
											commandContext -> a(commandContext.getSource(), di.a(commandContext, "entity"), eo.a(commandContext, "pos"), de.a(commandContext, "nbt"), false)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, uh uh, dem dem, le le, boolean boolean5) throws CommandSyntaxException {
		fu fu6 = new fu(dem);
		if (!bqb.k(fu6)) {
			throw b.create();
		} else {
			le le7 = le.g();
			le7.a("id", uh.toString());
			zd zd8 = cz.e();
			aom aom9 = aoq.a(le7, zd8, aom -> {
				aom.b(dem.b, dem.c, dem.d, aom.p, aom.q);
				return !zd8.d(aom) ? null : aom;
			});
			if (aom9 == null) {
				throw a.create();
			} else {
				if (boolean5 && aom9 instanceof aoz) {
					((aoz)aom9).a(cz.e(), cz.e().d(aom9.cA()), apb.COMMAND, null, null);
				}

				cz.a(new ne("commands.summon.success", aom9.d()), true);
				return 1;
			}
		}
	}
}
