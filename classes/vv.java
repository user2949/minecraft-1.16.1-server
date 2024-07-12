import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;

public class vv {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.experience.set.points.invalid"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralCommandNode<cz> literalCommandNode2 = commandDispatcher.register(
			da.a("experience")
				.requires(cz -> cz.c(2))
				.then(
					da.a("add")
						.then(
							da.a("targets", dh.d())
								.then(
									da.a("amount", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(
													commandContext.getSource(), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), vv.a.POINTS
												)
										)
										.then(
											da.a("points")
												.executes(
													commandContext -> a(
															commandContext.getSource(), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), vv.a.POINTS
														)
												)
										)
										.then(
											da.a("levels")
												.executes(
													commandContext -> a(
															commandContext.getSource(), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), vv.a.LEVELS
														)
												)
										)
								)
						)
				)
				.then(
					da.a("set")
						.then(
							da.a("targets", dh.d())
								.then(
									da.a("amount", IntegerArgumentType.integer(0))
										.executes(
											commandContext -> b(
													commandContext.getSource(), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), vv.a.POINTS
												)
										)
										.then(
											da.a("points")
												.executes(
													commandContext -> b(
															commandContext.getSource(), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), vv.a.POINTS
														)
												)
										)
										.then(
											da.a("levels")
												.executes(
													commandContext -> b(
															commandContext.getSource(), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), vv.a.LEVELS
														)
												)
										)
								)
						)
				)
				.then(
					da.a("query")
						.then(
							da.a("targets", dh.c())
								.then(da.a("points").executes(commandContext -> a(commandContext.getSource(), dh.e(commandContext, "targets"), vv.a.POINTS)))
								.then(da.a("levels").executes(commandContext -> a(commandContext.getSource(), dh.e(commandContext, "targets"), vv.a.LEVELS)))
						)
				)
		);
		commandDispatcher.register(da.a("xp").requires(cz -> cz.c(2)).redirect(literalCommandNode2));
	}

	private static int a(cz cz, ze ze, vv.a a) {
		int integer4 = a.f.applyAsInt(ze);
		cz.a(new ne("commands.experience.query." + a.e, ze.d(), integer4), false);
		return integer4;
	}

	private static int a(cz cz, Collection<? extends ze> collection, int integer, vv.a a) {
		for (ze ze6 : collection) {
			a.c.accept(ze6, integer);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.experience.add." + a.e + ".success.single", integer, ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.experience.add." + a.e + ".success.multiple", integer, collection.size()), true);
		}

		return collection.size();
	}

	private static int b(cz cz, Collection<? extends ze> collection, int integer, vv.a a) throws CommandSyntaxException {
		int integer5 = 0;

		for (ze ze7 : collection) {
			if (a.d.test(ze7, integer)) {
				integer5++;
			}
		}

		if (integer5 == 0) {
			throw vv.a.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.experience.set." + a.e + ".success.single", integer, ((ze)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.experience.set." + a.e + ".success.multiple", integer, collection.size()), true);
			}

			return collection.size();
		}
	}

	static enum a {
		POINTS("points", bec::d, (ze, integer) -> {
			if (integer >= ze.eG()) {
				return false;
			} else {
				ze.a(integer);
				return true;
			}
		}, ze -> aec.d(ze.bM * (float)ze.eG())),
		LEVELS("levels", ze::c, (ze, integer) -> {
			ze.b(integer);
			return true;
		}, ze -> ze.bK);

		public final BiConsumer<ze, Integer> c;
		public final BiPredicate<ze, Integer> d;
		public final String e;
		private final ToIntFunction<ze> f;

		private a(String string3, BiConsumer<ze, Integer> biConsumer, BiPredicate<ze, Integer> biPredicate, ToIntFunction<ze> toIntFunction) {
			this.c = biConsumer;
			this.e = string3;
			this.d = biPredicate;
			this.f = toIntFunction;
		}
	}
}
