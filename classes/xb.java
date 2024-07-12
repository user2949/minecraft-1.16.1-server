import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class xb {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.setblock.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("setblock")
				.requires(cz -> cz.c(2))
				.then(
					da.a("pos", eh.a())
						.then(
							da.a("block", ee.a())
								.executes(commandContext -> a(commandContext.getSource(), eh.a(commandContext, "pos"), ee.a(commandContext, "block"), xb.b.REPLACE, null))
								.then(
									da.a("destroy")
										.executes(commandContext -> a(commandContext.getSource(), eh.a(commandContext, "pos"), ee.a(commandContext, "block"), xb.b.DESTROY, null))
								)
								.then(
									da.a("keep")
										.executes(
											commandContext -> a(commandContext.getSource(), eh.a(commandContext, "pos"), ee.a(commandContext, "block"), xb.b.REPLACE, cfn -> cfn.c().w(cfn.d()))
										)
								)
								.then(
									da.a("replace")
										.executes(commandContext -> a(commandContext.getSource(), eh.a(commandContext, "pos"), ee.a(commandContext, "block"), xb.b.REPLACE, null))
								)
						)
				)
		);
	}

	private static int a(cz cz, fu fu, ec ec, xb.b b, @Nullable Predicate<cfn> predicate) throws CommandSyntaxException {
		zd zd6 = cz.e();
		if (predicate != null && !predicate.test(new cfn(zd6, fu, true))) {
			throw a.create();
		} else {
			boolean boolean7;
			if (b == xb.b.DESTROY) {
				zd6.b(fu, true);
				boolean7 = !ec.a().g() || !zd6.d_(fu).g();
			} else {
				cdl cdl8 = zd6.c(fu);
				amx.a(cdl8);
				boolean7 = true;
			}

			if (boolean7 && !ec.a(zd6, fu, 2)) {
				throw a.create();
			} else {
				zd6.a(fu, ec.a().b());
				cz.a(new ne("commands.setblock.success", fu.u(), fu.v(), fu.w()), true);
				return 1;
			}
		}
	}

	public interface a {
		@Nullable
		ec filter(ctd ctd, fu fu, ec ec, zd zd);
	}

	public static enum b {
		REPLACE,
		DESTROY;
	}
}
