import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class vt {
	private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("commands.enchant.failed.entity", object));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.enchant.failed.itemless", object));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("commands.enchant.failed.incompatible", object));
	private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.enchant.failed.level", object1, object2)
	);
	private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("commands.enchant.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("enchant")
				.requires(cz -> cz.c(2))
				.then(
					da.a("targets", dh.b())
						.then(
							da.a("enchantment", dk.a())
								.executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"), dk.a(commandContext, "enchantment"), 1))
								.then(
									da.a("level", IntegerArgumentType.integer(0))
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													dh.b(commandContext, "targets"),
													dk.a(commandContext, "enchantment"),
													IntegerArgumentType.getInteger(commandContext, "level")
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, Collection<? extends aom> collection, bnw bnw, int integer) throws CommandSyntaxException {
		if (integer > bnw.a()) {
			throw d.create(integer, bnw.a());
		} else {
			int integer5 = 0;

			for (aom aom7 : collection) {
				if (aom7 instanceof aoy) {
					aoy aoy8 = (aoy)aom7;
					bki bki9 = aoy8.dC();
					if (!bki9.a()) {
						if (bnw.a(bki9) && bny.a(bny.a(bki9).keySet(), bnw)) {
							bki9.a(bnw, integer);
							integer5++;
						} else if (collection.size() == 1) {
							throw c.create(bki9.b().h(bki9).getString());
						}
					} else if (collection.size() == 1) {
						throw b.create(aoy8.P().getString());
					}
				} else if (collection.size() == 1) {
					throw a.create(aom7.P().getString());
				}
			}

			if (integer5 == 0) {
				throw e.create();
			} else {
				if (collection.size() == 1) {
					cz.a(new ne("commands.enchant.success.single", bnw.d(integer), ((aom)collection.iterator().next()).d()), true);
				} else {
					cz.a(new ne("commands.enchant.success.multiple", bnw.d(integer), collection.size()), true);
				}

				return integer5;
			}
		}
	}
}
