import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;

public class xu {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.worldborder.center.failed"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.worldborder.set.failed.nochange"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.worldborder.set.failed.small."));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.worldborder.set.failed.big."));
	private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("commands.worldborder.warning.time.failed"));
	private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new ne("commands.worldborder.warning.distance.failed"));
	private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ne("commands.worldborder.damage.buffer.failed"));
	private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new ne("commands.worldborder.damage.amount.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("worldborder")
				.requires(cz -> cz.c(2))
				.then(
					da.a("add")
						.then(
							da.a("distance", FloatArgumentType.floatArg(-6.0E7F, 6.0E7F))
								.executes(
									commandContext -> a(
											commandContext.getSource(), commandContext.getSource().e().f().i() + (double)FloatArgumentType.getFloat(commandContext, "distance"), 0L
										)
								)
								.then(
									da.a("time", IntegerArgumentType.integer(0))
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													commandContext.getSource().e().f().i() + (double)FloatArgumentType.getFloat(commandContext, "distance"),
													commandContext.getSource().e().f().j() + (long)IntegerArgumentType.getInteger(commandContext, "time") * 1000L
												)
										)
								)
						)
				)
				.then(
					da.a("set")
						.then(
							da.a("distance", FloatArgumentType.floatArg(-6.0E7F, 6.0E7F))
								.executes(commandContext -> a(commandContext.getSource(), (double)FloatArgumentType.getFloat(commandContext, "distance"), 0L))
								.then(
									da.a("time", IntegerArgumentType.integer(0))
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													(double)FloatArgumentType.getFloat(commandContext, "distance"),
													(long)IntegerArgumentType.getInteger(commandContext, "time") * 1000L
												)
										)
								)
						)
				)
				.then(da.a("center").then(da.a("pos", en.a()).executes(commandContext -> a(commandContext.getSource(), en.a(commandContext, "pos")))))
				.then(
					da.a("damage")
						.then(
							da.a("amount")
								.then(
									da.a("damagePerBlock", FloatArgumentType.floatArg(0.0F))
										.executes(commandContext -> b(commandContext.getSource(), FloatArgumentType.getFloat(commandContext, "damagePerBlock")))
								)
						)
						.then(
							da.a("buffer")
								.then(
									da.a("distance", FloatArgumentType.floatArg(0.0F))
										.executes(commandContext -> a(commandContext.getSource(), FloatArgumentType.getFloat(commandContext, "distance")))
								)
						)
				)
				.then(da.a("get").executes(commandContext -> a(commandContext.getSource())))
				.then(
					da.a("warning")
						.then(
							da.a("distance")
								.then(
									da.a("distance", IntegerArgumentType.integer(0))
										.executes(commandContext -> b(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "distance")))
								)
						)
						.then(
							da.a("time")
								.then(
									da.a("time", IntegerArgumentType.integer(0))
										.executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time")))
								)
						)
				)
		);
	}

	private static int a(cz cz, float float2) throws CommandSyntaxException {
		cgw cgw3 = cz.e().f();
		if (cgw3.n() == (double)float2) {
			throw g.create();
		} else {
			cgw3.b((double)float2);
			cz.a(new ne("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", float2)), true);
			return (int)float2;
		}
	}

	private static int b(cz cz, float float2) throws CommandSyntaxException {
		cgw cgw3 = cz.e().f();
		if (cgw3.o() == (double)float2) {
			throw h.create();
		} else {
			cgw3.c((double)float2);
			cz.a(new ne("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", float2)), true);
			return (int)float2;
		}
	}

	private static int a(cz cz, int integer) throws CommandSyntaxException {
		cgw cgw3 = cz.e().f();
		if (cgw3.q() == integer) {
			throw e.create();
		} else {
			cgw3.b(integer);
			cz.a(new ne("commands.worldborder.warning.time.success", integer), true);
			return integer;
		}
	}

	private static int b(cz cz, int integer) throws CommandSyntaxException {
		cgw cgw3 = cz.e().f();
		if (cgw3.r() == integer) {
			throw f.create();
		} else {
			cgw3.c(integer);
			cz.a(new ne("commands.worldborder.warning.distance.success", integer), true);
			return integer;
		}
	}

	private static int a(cz cz) {
		double double2 = cz.e().f().i();
		cz.a(new ne("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", double2)), false);
		return aec.c(double2 + 0.5);
	}

	private static int a(cz cz, del del) throws CommandSyntaxException {
		cgw cgw3 = cz.e().f();
		if (cgw3.a() == (double)del.i && cgw3.b() == (double)del.j) {
			throw a.create();
		} else {
			cgw3.c((double)del.i, (double)del.j);
			cz.a(new ne("commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", del.i), String.format("%.2f", del.j)), true);
			return 0;
		}
	}

	private static int a(cz cz, double double2, long long3) throws CommandSyntaxException {
		cgw cgw6 = cz.e().f();
		double double7 = cgw6.i();
		if (double7 == double2) {
			throw b.create();
		} else if (double2 < 1.0) {
			throw c.create();
		} else if (double2 > 6.0E7) {
			throw d.create();
		} else {
			if (long3 > 0L) {
				cgw6.a(double7, double2, long3);
				if (double2 > double7) {
					cz.a(new ne("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", double2), Long.toString(long3 / 1000L)), true);
				} else {
					cz.a(new ne("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", double2), Long.toString(long3 / 1000L)), true);
				}
			} else {
				cgw6.a(double2);
				cz.a(new ne("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", double2)), true);
			}

			return (int)(double2 - double7);
		}
	}
}
