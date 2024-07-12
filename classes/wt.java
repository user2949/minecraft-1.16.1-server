import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.List;

public class wt {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.replaceitem.block.failed"));
	public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.replaceitem.slot.inapplicable", object));
	public static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.replaceitem.entity.failed", object1, object2)
	);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("replaceitem")
				.requires(cz -> cz.c(2))
				.then(
					da.a("block")
						.then(
							da.a("pos", eh.a())
								.then(
									da.a("slot", dy.a())
										.then(
											da.a("item", et.a())
												.executes(
													commandContext -> a(
															commandContext.getSource(), eh.a(commandContext, "pos"), dy.a(commandContext, "slot"), et.a(commandContext, "item").a(1, false)
														)
												)
												.then(
													da.a("count", IntegerArgumentType.integer(1, 64))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	eh.a(commandContext, "pos"),
																	dy.a(commandContext, "slot"),
																	et.a(commandContext, "item").a(IntegerArgumentType.getInteger(commandContext, "count"), true)
																)
														)
												)
										)
								)
						)
				)
				.then(
					da.a("entity")
						.then(
							da.a("targets", dh.b())
								.then(
									da.a("slot", dy.a())
										.then(
											da.a("item", et.a())
												.executes(
													commandContext -> a(
															commandContext.getSource(), dh.b(commandContext, "targets"), dy.a(commandContext, "slot"), et.a(commandContext, "item").a(1, false)
														)
												)
												.then(
													da.a("count", IntegerArgumentType.integer(1, 64))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dh.b(commandContext, "targets"),
																	dy.a(commandContext, "slot"),
																	et.a(commandContext, "item").a(IntegerArgumentType.getInteger(commandContext, "count"), true)
																)
														)
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, fu fu, int integer, bki bki) throws CommandSyntaxException {
		cdl cdl5 = cz.e().c(fu);
		if (!(cdl5 instanceof amz)) {
			throw a.create();
		} else {
			amz amz6 = (amz)cdl5;
			if (integer >= 0 && integer < amz6.ab_()) {
				amz6.a(integer, bki);
				cz.a(new ne("commands.replaceitem.block.success", fu.u(), fu.v(), fu.w(), bki.C()), true);
				return 1;
			} else {
				throw b.create(integer);
			}
		}
	}

	private static int a(cz cz, Collection<? extends aom> collection, int integer, bki bki) throws CommandSyntaxException {
		List<aom> list5 = Lists.<aom>newArrayListWithCapacity(collection.size());

		for (aom aom7 : collection) {
			if (aom7 instanceof ze) {
				((ze)aom7).bv.c();
			}

			if (aom7.a_(integer, bki.i())) {
				list5.add(aom7);
				if (aom7 instanceof ze) {
					((ze)aom7).bv.c();
				}
			}
		}

		if (list5.isEmpty()) {
			throw c.create(bki.C(), integer);
		} else {
			if (list5.size() == 1) {
				cz.a(new ne("commands.replaceitem.entity.success.single", ((aom)list5.iterator().next()).d(), bki.C()), true);
			} else {
				cz.a(new ne("commands.replaceitem.entity.success.multiple", list5.size(), bki.C()), true);
			}

			return list5.size();
		}
	}
}
