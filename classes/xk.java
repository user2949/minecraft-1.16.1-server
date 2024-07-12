import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Set;

public class xk {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.tag.add.failed"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.tag.remove.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("tag")
				.requires(cz -> cz.c(2))
				.then(
					da.a("targets", dh.b())
						.then(
							da.a("add")
								.then(
									da.a("name", StringArgumentType.word())
										.executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"), StringArgumentType.getString(commandContext, "name")))
								)
						)
						.then(
							da.a("remove")
								.then(
									da.a("name", StringArgumentType.word())
										.suggests((commandContext, suggestionsBuilder) -> db.b(a(dh.b(commandContext, "targets")), suggestionsBuilder))
										.executes(commandContext -> b(commandContext.getSource(), dh.b(commandContext, "targets"), StringArgumentType.getString(commandContext, "name")))
								)
						)
						.then(da.a("list").executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"))))
				)
		);
	}

	private static Collection<String> a(Collection<? extends aom> collection) {
		Set<String> set2 = Sets.<String>newHashSet();

		for (aom aom4 : collection) {
			set2.addAll(aom4.W());
		}

		return set2;
	}

	private static int a(cz cz, Collection<? extends aom> collection, String string) throws CommandSyntaxException {
		int integer4 = 0;

		for (aom aom6 : collection) {
			if (aom6.a(string)) {
				integer4++;
			}
		}

		if (integer4 == 0) {
			throw a.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.tag.add.success.single", string, ((aom)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.tag.add.success.multiple", string, collection.size()), true);
			}

			return integer4;
		}
	}

	private static int b(cz cz, Collection<? extends aom> collection, String string) throws CommandSyntaxException {
		int integer4 = 0;

		for (aom aom6 : collection) {
			if (aom6.b(string)) {
				integer4++;
			}
		}

		if (integer4 == 0) {
			throw b.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.tag.remove.success.single", string, ((aom)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.tag.remove.success.multiple", string, collection.size()), true);
			}

			return integer4;
		}
	}

	private static int a(cz cz, Collection<? extends aom> collection) {
		Set<String> set3 = Sets.<String>newHashSet();

		for (aom aom5 : collection) {
			set3.addAll(aom5.W());
		}

		if (collection.size() == 1) {
			aom aom4 = (aom)collection.iterator().next();
			if (set3.isEmpty()) {
				cz.a(new ne("commands.tag.list.single.empty", aom4.d()), false);
			} else {
				cz.a(new ne("commands.tag.list.single.success", aom4.d(), set3.size(), ms.a(set3)), false);
			}
		} else if (set3.isEmpty()) {
			cz.a(new ne("commands.tag.list.multiple.empty", collection.size()), false);
		} else {
			cz.a(new ne("commands.tag.list.multiple.success", collection.size(), set3.size(), ms.a(set3)), false);
		}

		return set3.size();
	}
}
